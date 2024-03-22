package com.zhongzhu.rabbit.mq.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhongzhu.rabbit.enums.TransMessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author admin
 * mq 消息记录
 */
@Data
@Builder
@TableName(value = "trans_message")
@NoArgsConstructor
@AllArgsConstructor
public class TransMessage {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * mq 消息id
     */
    @TableField(value = "message_id")
    private String messageId;

    /**
     * 所属服务
     */
    @TableField(value = "service")
    private String service;

    /**
     * 消息类型
     */
    @TableField(value = "type")
    private TransMessageType type;

    /**
     * 目的交换机
     */
    @TableField(value = "exchange")
    private String exchange;

    /**
     * 目的路由key
     */
    @TableField(value = "routing_key")
    private String routingKey;

    /**
     * 目的队列
     */
    @TableField(value = "queue")
    private String queue;

    /**
     * 第几次发送
     */
    @TableField(value = "sequence")
    private Integer sequence;

    /**
     * 消息体
     */
    @TableField(value = "payload")
    private String payload;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date createTime;
}
