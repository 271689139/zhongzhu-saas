package com.zhongzhu.mq.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zhongzhu.core.i18n.SysConstants;
import com.zhongzhu.mq.domain.TransMessage;
import com.zhongzhu.mq.enums.TransMessageType;
import com.zhongzhu.mq.mapper.TransMessageMapper;
import com.zhongzhu.mq.service.TransMessageService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author admin
 * MQ消息表
 */
@Service
@Slf4j
public class TransMessageServiceImpl implements TransMessageService {

    @Resource
    private TransMessageMapper transMessageMapper;

    @Value("${" + SysConstants.SPRING_APPLICATION_NAME + "}")
    private String serviceName;

    @Override
    public TransMessage messageBeforeSend(String exchange, String routingKey, String body) {
        return saveMessage(exchange, routingKey, body, TransMessageType.SEND);
    }

    @Override
    public void sendMessageSuccess(String messageId) {
        QueryWrapper<TransMessage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("message_id", messageId).ne("type", TransMessageType.LOST.name());
        TransMessage transMessage = transMessageMapper.selectOne(queryWrapper);
        if (null != transMessage) {
            log.info("Deleted: {}", transMessage);
            deleteTransMessage(messageId);
        }
    }

    @Override
    public TransMessage handleMessageReturn(String messageId, String exchange, String routingKey, String body) {
        QueryWrapper<TransMessage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("message_id", messageId);
        TransMessage transMessage = transMessageMapper.selectOne(queryWrapper);
        if (null == transMessage) {
            log.info("create lost message");
            return saveMessage(exchange, routingKey, body, TransMessageType.LOST);
        }
        transMessage.setType(TransMessageType.LOST);
        UpdateWrapper<TransMessage> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("message_id", messageId);
        transMessageMapper.update(transMessage, updateWrapper);
        return transMessage;
    }

    @Override
    public List<TransMessage> getReadyMessages() {
        QueryWrapper<TransMessage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", TransMessageType.SEND.name()).eq("service", serviceName);
        return transMessageMapper.selectList(queryWrapper);
    }

    @Override
    public void resendMessage(String messageId) {
        UpdateWrapper<TransMessage> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("message_id", messageId).eq("service", serviceName).setSql("`sequence` = `sequence` + 1");
        transMessageMapper.update(null, updateWrapper);
    }

    @Override
    public void handleMessageDead(String messageId) {
        UpdateWrapper<TransMessage> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("message_id", messageId).eq("service", serviceName).set("type", TransMessageType.DEAD);
        transMessageMapper.update(null, updateWrapper);
    }

    @Override
    public void handleMessageDead(String messageId, String exchange, String routingKey, String queue, String body) {
        TransMessage transMessage = new TransMessage();
        transMessage.setMessageId(messageId);
        transMessage.setService(serviceName);
        transMessage.setExchange(exchange);
        transMessage.setRoutingKey(routingKey);
        transMessage.setQueue(queue);
        transMessage.setPayload(body);
        transMessage.setSequence(0);
        transMessage.setType(TransMessageType.DEAD);
        transMessage.setCreateTime(new Date());
        transMessageMapper.insert(transMessage);
    }

    @Override
    public TransMessage messageBeforeConsume(String messageId, String exchange, String routingKey, String queue, String body) {
        // 查询数据库中有无该消息，若有，增加消费次数，若没有则新建
        QueryWrapper<TransMessage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("message_id", messageId).eq("service", serviceName);
        TransMessage transMessage = transMessageMapper.selectOne(queryWrapper);
        // 没有则新建
        if (null == transMessage) {
            transMessage = new TransMessage();
            transMessage.setMessageId(messageId);
            transMessage.setService(serviceName);
            transMessage.setExchange(exchange);
            transMessage.setRoutingKey(routingKey);
            transMessage.setQueue(queue);
            transMessage.setPayload(body);
            transMessage.setSequence(0);
            transMessage.setType(TransMessageType.RECEIVE);
            transMessage.setCreateTime(new Date());
            transMessageMapper.insert(transMessage);
        } else {
            UpdateWrapper<TransMessage> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", transMessage.getId());
            transMessage.setSequence(transMessage.getSequence() + 1);
            transMessageMapper.update(transMessage, updateWrapper);
        }
        return transMessage;
    }

    @Override
    public void consumeMessageSuccess(String messageId) {
        deleteTransMessage(messageId);
    }

    /**
     * 更改该消息的状态为失败
     *
     * @param messageId 消息id
     */
    @Override
    public void consumeMessageFailed(String messageId) {
        QueryWrapper<TransMessage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("message_id", messageId).eq("service", serviceName);
        TransMessage transMessage = transMessageMapper.selectOne(queryWrapper);
        if (null != transMessage) {
            UpdateWrapper<TransMessage> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("message_id", messageId).set("type", TransMessageType.ERROR.name());
            transMessageMapper.update(null, updateWrapper);
        }
    }

    private TransMessage saveMessage(String exchange, String routingKey, String body, TransMessageType type) {
        TransMessage transMessage = new TransMessage();
        transMessage.setMessageId(UUID.randomUUID().toString());
        transMessage.setService(serviceName);
        transMessage.setExchange(exchange);
        transMessage.setRoutingKey(routingKey);
        transMessage.setPayload(body);
        transMessage.setSequence(0);
        transMessage.setCreateTime(new Date());
        transMessage.setType(type);
        transMessageMapper.insert(transMessage);
        return transMessage;
    }

    private void deleteTransMessage(String messageId) {
        QueryWrapper<TransMessage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("message_id", messageId).eq("service", serviceName);
        transMessageMapper.delete(queryWrapper);
    }
}