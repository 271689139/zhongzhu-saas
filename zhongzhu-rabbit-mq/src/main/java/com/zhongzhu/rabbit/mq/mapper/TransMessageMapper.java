package com.zhongzhu.rabbit.mq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhongzhu.rabbit.mq.domain.TransMessage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author admin
 * MQ 消息mapper
 */
@Mapper
@Repository
public interface TransMessageMapper extends BaseMapper<TransMessage> {
}
