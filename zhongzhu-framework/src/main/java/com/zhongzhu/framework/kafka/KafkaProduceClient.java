package com.zhongzhu.framework.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 * @author admin
 */
public class KafkaProduceClient {
    private static volatile KafkaProduceClient instance;

    private final KafkaProducePool pool;

    public static KafkaProduceClient getInstance(String hosts) {
        if (null == instance) {
            synchronized (KafkaProduceClient.class) {
                if (null == instance) {
                    instance = new KafkaProduceClient(hosts);
                }
            }
        }
        return instance;
    }

    private KafkaProduceClient(String hosts) {
        this.pool = new KafkaProducePool(hosts);
    }

    public void push(String topic, String message) throws Exception {
        KafkaProducer<String, String> producer = pool.get();
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);
        producer.send(record);
        pool.returnObject(producer);
    }
}
