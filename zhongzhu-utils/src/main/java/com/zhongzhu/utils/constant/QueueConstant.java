package com.zhongzhu.utils.constant;

/**
 * rabbitMQ
 */
public interface QueueConstant {

    /**
     * 死信队列
     */
    String DEAD_LETTER_EXCHANGE = "exchange.dlx";

    /**
     * 交换机ttl
     */
    String EXCHANGE_TTL = "exchange.ttl";


    /**
     * 交换机
     */
    String EXCHANGE_TEST_BUSINESS = "exchange.test.business";

    /**
     * 测试队列
     */
    String QUEUE_TEST = "queue.test";

    /**
     * 路由key
     */
    String TEST_ROUTE_KEY = "test.key";


}
