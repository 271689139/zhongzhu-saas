package com.zhongzhu.kafka;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.Serializable;
import java.util.Properties;

/**
 * @author shihao.liu
 */
public class KafkaProducerFactory implements PooledObjectFactory<KafkaProducer<String, String>>, Serializable {
    private Properties properties = new Properties();

    public KafkaProducerFactory(String hosts) {
        // 集群地址
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, hosts);
        // 消息序列化方式
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        //提交延时
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 0);
        //重试，0为不启用重试机制
        properties.put(ProducerConfig.RETRIES_CONFIG, 0);
        properties.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 5);

    }

    public PooledObject<KafkaProducer<String, String>> makeObject() {
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
        return new DefaultPooledObject<>(producer);
    }

    public void destroyObject(PooledObject<KafkaProducer<String, String>> pooledObject) {
        pooledObject.getObject().close();
    }

    public boolean validateObject(PooledObject pooledObject) {
        return true;
    }

    public void activateObject(PooledObject pooledObject) {
    }

    public void passivateObject(PooledObject pooledObject) throws Exception {
    }
}
