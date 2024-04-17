package com.zhongzhu.property.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.zhongzhu.core.i18n.RabbitMqConstants;
import com.zhongzhu.mq.transmitter.TransMessageTransmitter;
import com.zhongzhu.property.bean.SystemVersionDTO;
import com.zhongzhu.property.domain.SystemVersion;
import com.zhongzhu.property.mapper.SystemVersionMapper;
import com.zhongzhu.property.service.SystemVersionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.zhongzhu.core.i18n.RabbitMqConstants.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yuxiang.dai
 * @since 2024-03-12
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SystemVersionServiceImpl extends ServiceImpl<SystemVersionMapper, SystemVersion> implements SystemVersionService {

    private final TransMessageTransmitter transmitter;
    @Override
    public SystemVersionDTO getById(Long id) {
        SystemVersionDTO dto = new SystemVersionDTO();
        BeanUtils.copyProperties(this.getBaseMapper().selectById(id), dto);
        log.info("查询完毕");
        // 发送Mq消息
        for (int i=0;i<10;i++){
            Map<String,String> param = Maps.newHashMap();
            param.put("message", "雅蠛蝶:" + i);
            transmitter.send(EXCHANGE_TEST_BUSINESS,TEST_ROUTE_KEY,param);
        }
        return dto;
    }
}
