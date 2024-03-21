package com.zhongzhu.message.kafka;

import com.alibaba.fastjson.JSON;
import com.zhongzhu.elasticsearch.domain.ZhongzhuServiceLog;
import com.zhongzhu.elasticsearch.service.ZhongzhuServiceLogWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author admin
 * 处理日志信息
 */
@Slf4j
@Component
public class ServiceLogListener {

    private ZhongzhuServiceLogWrapper zhongzhuServiceLogWrapper;

    @Autowired
    public void setZhongzhuServiceLogWrapper(ZhongzhuServiceLogWrapper zhongzhuServiceLogWrapper) {
        this.zhongzhuServiceLogWrapper = zhongzhuServiceLogWrapper;
    }

    /**
     * kafka消费者
     * groupId: 指定 Kafka 消费者组的 ID，表示一组消费者共同处理消息。如果没有指定 `groupId`，则会自动生成一个唯一标识符作为 `groupId`
     * concurrency: 指定并发消费者的数量，即监听器容器的线程数。这有助于提高消息处理的吞吐量 （假设你一个主题对应两个分区，你这个项目只部署一个实例，那concurrency =2 , 一个实例上开启两个线程, 1个线程负责一个分区）
     * clientIdPrefix: 指定 Kafka 消费者的客户端 ID 前缀。
     */
    @KafkaListener(groupId = "${kafka.logMap.groupId}", topics = "${kafka.logMap.topic}", concurrency = "${kafka.logMap.concurrency}", containerFactory = "batchFactory", clientIdPrefix = "${kafka.logMap.clientIdPrefix}")
    public void onMessage(List<ConsumerRecord<String, String>> list, Acknowledgment acknowledgment) {
        List<ZhongzhuServiceLog> lists = list.stream().map(consumerRecord -> JSON.parseObject(consumerRecord.value(), ZhongzhuServiceLog.class)).collect(Collectors.toList());
        zhongzhuServiceLogWrapper.batchInsert(lists);
        // 手动提交offset
        acknowledgment.acknowledge();
    }
}
