package com.zhongzhu.rabbit.mq.service.Impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhongzhu.rabbit.mq.domain.SecureInvokeRecord;
import com.zhongzhu.rabbit.mq.dto.SecureInvokeDTO;
import com.zhongzhu.rabbit.mq.mapper.SecureInvokeRecordMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

/**
 * @author admin
 * 安全执行处理器
 */
@Slf4j
@AllArgsConstructor
public class SecureInvokeService extends ServiceImpl<SecureInvokeRecordMapper, SecureInvokeRecord> {

    public static final double RETRY_INTERVAL_MINUTES = 2D;

    private final Executor executor;


    public void saveRecord(SecureInvokeRecord record) {
        this.getBaseMapper().insert(record);
    }

    private void retryRecord(SecureInvokeRecord record, String errorMsg) {
        Integer retryTimes = record.getRetryTimes() + 1;
        SecureInvokeRecord update = new SecureInvokeRecord();
        update.setId(record.getId());
        update.setFailReason(errorMsg);
        update.setNextRetryTime(getNextRetryTime(retryTimes));
        if (retryTimes > record.getMaxRetryTimes()) {
            update.setStatus(SecureInvokeRecord.STATUS_FAIL);
        } else {
            update.setRetryTimes(retryTimes);
        }
        getBaseMapper().updateById(update);
    }

    private Date getNextRetryTime(Integer retryTimes) {
        //重试时间指数上升 2m 4m 8m 16m
        double waitMinutes = Math.pow(RETRY_INTERVAL_MINUTES, retryTimes);
        return DateUtil.offsetMinute(new Date(), (int) waitMinutes);
    }

    private void removeRecord(Long id) {
        getBaseMapper().deleteById(id);
    }

    public void invoke(SecureInvokeRecord record, boolean async) {
        boolean inTransaction = TransactionSynchronizationManager.isActualTransactionActive();
        // 非事务状态，直接执行，不做任何保证。
        if (!inTransaction) {
            return;
        }
        // 保存执行数据
        save(record);
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @SneakyThrows
            @Override
            public void afterCommit() {
                // 事务后执行
                if (async) {
                    doAsyncInvoke(record);
                } else {
                    doInvoke(record);
                }
            }
        });
    }

    public void doAsyncInvoke(SecureInvokeRecord record) {
        executor.execute(() -> {
            System.out.println(Thread.currentThread().getName());
            doInvoke(record);
        });
    }

    public void doInvoke(SecureInvokeRecord record) {
        SecureInvokeDTO secureInvokeDTO = record.getSecureInvokeDTO();
        try {
            Class<?> beanClass = Class.forName(secureInvokeDTO.getClassName());
            Object bean = SpringUtil.getBean(beanClass);
            List<String> parameterStrings = JSONUtil.toList(secureInvokeDTO.getParameterTypes(), String.class);
            List<Class<?>> parameterClasses = getParameters(parameterStrings);
            Method method = ReflectUtil.getMethod(beanClass, secureInvokeDTO.getMethodName(), parameterClasses.toArray(new Class[]{}));
            Object[] args = secureInvokeDTO.getArgs();
            // 执行方法
            method.invoke(bean, args);
            // 执行成功更新状态
            removeRecord(record.getId());
        } catch (Throwable e) {
            log.error("SecureInvokeService invoke fail", e);
            // 执行失败，等待下次执行
            retryRecord(record, e.getMessage());
        }
    }

    @NotNull
    private List<Class<?>> getParameters(List<String> parameterStrings) {
        return parameterStrings.stream().map(name -> {
            try {
                return Class.forName(name);
            } catch (ClassNotFoundException e) {
                log.error("SecureInvokeService class not fund", e);
            }
            return null;
        }).collect(Collectors.toList());
    }

    public List<SecureInvokeRecord> getWaitRetryRecords() {
        Date now = new Date();
        //查2分钟前的失败数据。避免刚入库的数据被查出来
        DateTime afterTime = DateUtil.offsetMinute(now, (int) SecureInvokeService.RETRY_INTERVAL_MINUTES);
        LambdaQueryWrapper<SecureInvokeRecord> lambdaQueryWrapper = Wrappers.lambdaQuery(SecureInvokeRecord.class).eq(SecureInvokeRecord::getStatus, SecureInvokeRecord.STATUS_WAIT).lt(SecureInvokeRecord::getNextRetryTime, new Date()).lt(SecureInvokeRecord::getCreateTime, afterTime);
        return getBaseMapper().selectList(lambdaQueryWrapper);
    }
}
