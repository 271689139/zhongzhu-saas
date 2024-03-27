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
     * 消息ttl
     */
    String EXCHANGE_TTL = "60000";

    /**
     * 死信队列 key
     */
    String DEAD_QUEUE_ROUTE_KEY = "dead.queue.route";


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
