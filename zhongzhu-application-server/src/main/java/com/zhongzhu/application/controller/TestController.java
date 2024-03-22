package com.zhongzhu.application.controller;

import com.google.common.collect.Maps;
import com.zhongzhu.business.system.domain.SystemVersion;
import com.zhongzhu.business.system.service.SystemVersionService;
import com.zhongzhu.elasticsearch.service.ZhongzhuServiceLogWrapper;
import com.zhongzhu.framework.annotations.ApiLog;
import com.zhongzhu.framework.annotations.FrequencyControl;
import com.zhongzhu.framework.redis.RedisOpsValueClient;
import com.zhongzhu.rabbit.transmitter.TransMessageTransmitter;
import com.zhongzhu.utils.constant.QueueConstant;
import com.zhongzhu.utils.request.GenericBaseRequest;
import com.zhongzhu.utils.response.GenericBaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author admin
 */
@RestController
@RequestMapping("/test")
@Tag(name = "测试")
@Slf4j
public class TestController {

    private SystemVersionService systemVersionService;

    private ZhongzhuServiceLogWrapper zhongzhuServiceLogWrapper;

    private RedisOpsValueClient<String> redisOpsValueClient;

    private TransMessageTransmitter transmitter;

    @Autowired
    public void setTransmitter(TransMessageTransmitter transmitter) {
        this.transmitter = transmitter;
    }

    @Autowired
    public void setZhongzhuServiceLogWrapper(ZhongzhuServiceLogWrapper zhongzhuServiceLogWrapper) {
        this.zhongzhuServiceLogWrapper = zhongzhuServiceLogWrapper;
    }


    @Autowired
    public void setRedisOpsValueClient(RedisOpsValueClient<String> redisOpsValueClient) {
        this.redisOpsValueClient = redisOpsValueClient;
    }

    @Autowired
    public void setSystemVersionService(SystemVersionService systemVersionService) {
        this.systemVersionService = systemVersionService;
    }


    @Operation(summary = "根据id查询信息")
    @Parameter(name = "request", description = "请求id", in = ParameterIn.QUERY)
    @RequestMapping(value = "/getById", method = RequestMethod.POST)
    public @ResponseBody GenericBaseResponse<SystemVersion> getById(@RequestBody GenericBaseRequest<Long> request) {
        log.info("这是一个正常有链路的id");
        return GenericBaseResponse.ok(systemVersionService.getById(request.getParam()));
    }


    @ApiLog(name = "根据id查询信息", level = ApiLog.LogLevel.INFO)
    @Operation(summary = "测试日志注解")
    @Parameter(name = "request", description = "请求id", in = ParameterIn.QUERY)
    @RequestMapping(value = "/testApiLog", method = RequestMethod.POST)
    public @ResponseBody GenericBaseResponse<SystemVersion> testApiLog(@RequestBody GenericBaseRequest<Long> request) {
        return GenericBaseResponse.ok(systemVersionService.getById(request.getParam()));
    }


    @FrequencyControl(time = 5, count = 3, target = FrequencyControl.Target.IP)
    @Operation(summary = "测试频控注解")
    @Parameter(name = "request", description = "请求id", in = ParameterIn.QUERY)
    @RequestMapping(value = "/frequencyControl", method = RequestMethod.POST)
    public @ResponseBody GenericBaseResponse<SystemVersion> frequencyControl(@RequestBody GenericBaseRequest<Long> request) {
        log.info("根据请求ip,每5秒中内最多请求3次");
        return GenericBaseResponse.ok(systemVersionService.getById(request.getParam()));
    }


    /**
     * 如果一个有效的partition属性数值被指定，那么在发送记录时partition属性数值就会被应用。如果没有partition属性数值被指定，
     * 而一个key属性被声明的话，一个partition会通过key的hash而被选中。
     * 如果既没有key也没有partition属性数值被声明，那么一个partition将会被分配以轮询的方式
     */

    /**
     * 普通发送
     * kafkaTemplate.send("test", "这是一条消息");
     * 指定分区发送
     * kafkaTemplate.send("test", i % 2, i + "", "这是第" + i + "条记录");
     */


    /**
     * kafka消费者
     * groupId: 指定 Kafka 消费者组的 ID，表示一组消费者共同处理消息。如果没有指定 `groupId`，则会自动生成一个唯一标识符作为 `groupId`
     * concurrency: 指定并发消费者的数量，即监听器容器的线程数。这有助于提高消息处理的吞吐量 （假设你一个主题对应两个分区，你这个项目只部署一个实例，那concurrency =2 , 一个实例上开启两个线程, 1个线程负责一个分区）
     * clientIdPrefix: 指定 Kafka 消费者的客户端 ID 前缀。
     */
//    @KafkaListener(groupId = "test-groupId", topics = "kafka-service-log", concurrency = "2", containerFactory = "batchFactory", clientIdPrefix = "test-clientId")
//    public void onMessage(List<ConsumerRecord<String, String>> list, Acknowledgment acknowledgment) {
//        for (ConsumerRecord<String, String> consumerRecord : list) {
//            List<ZhongzhuServiceLog> items = JSON.parseArray(consumerRecord.value(), ZhongzhuServiceLog.class);
//            zhongzhuServiceLogWrapper.batchInsert(items);
//        }
//        // 手动提交offset
//        acknowledgment.acknowledge();
//    }
    @Operation(summary = "发送mq消息")
    @Parameter(name = "request", description = "请求id", in = ParameterIn.QUERY)
    @RequestMapping(value = "/testSendRabbitMq", method = RequestMethod.POST)
    public @ResponseBody GenericBaseResponse<SystemVersion> testSendRabbitMq(@RequestBody GenericBaseRequest<Long> request) {
        for (int i = 0; i < 10000; i++) {
            Map<String, String> map = Maps.newHashMap();
            map.put("message", "第" + (i + 1) + "次,say hello!");
            transmitter.send(QueueConstant.EXCHANGE_TEST_BUSINESS, QueueConstant.TEST_ROUTE_KEY, map);

        }
        return GenericBaseResponse.ok(systemVersionService.getById(request.getParam()));
    }
}
