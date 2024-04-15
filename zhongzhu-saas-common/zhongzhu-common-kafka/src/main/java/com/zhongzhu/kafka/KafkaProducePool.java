package com.zhongzhu.kafka;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.kafka.clients.producer.KafkaProducer;

public class KafkaProducePool {
    private final GenericObjectPool<KafkaProducer<String, String>> pool;

    public KafkaProducePool(String hosts) {
        GenericObjectPoolConfig<KafkaProducer<String, String>> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMinIdle(1);
        poolConfig.setMaxIdle(5);
        poolConfig.setMaxTotal(10);
        pool = new GenericObjectPool<>(new KafkaProducerFactory(hosts), poolConfig);
    }

    public KafkaProducer<String, String> get() throws Exception {
        return pool.borrowObject();
    }

    public void returnObject(KafkaProducer<String, String> producer) {
        pool.returnObject(producer);
    }
}
