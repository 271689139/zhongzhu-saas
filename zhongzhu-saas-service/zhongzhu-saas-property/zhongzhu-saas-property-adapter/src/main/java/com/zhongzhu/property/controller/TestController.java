package com.zhongzhu.property.controller;

import com.alibaba.fastjson.JSON;
import com.zhongzhu.core.request.GenericBaseRequest;
import com.zhongzhu.core.response.GenericBaseResponse;
import com.zhongzhu.frequency.annotations.FrequencyControl;
import com.zhongzhu.property.bean.SystemVersionDTO;
import com.zhongzhu.property.service.SystemVersionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author shihao.liu
 */
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/test")
@Tag(name = "TestController", description = "测试Controller")
public class TestController {

    private final SystemVersionService systemVersionService;

    @FrequencyControl(time = 5, count = 5, target = FrequencyControl.Target.IP)
    @RequestMapping(value = "/getById", method = RequestMethod.POST)
    @Operation(summary = "测试模块", description = "根据id查询值")
    @Parameter(name = "request", description = "请求id", in = ParameterIn.QUERY)
    public @ResponseBody GenericBaseResponse<SystemVersionDTO> get(@RequestBody GenericBaseRequest<Long> request) {
        log.info("get入参:{}", request.getParam());
        SystemVersionDTO dto = systemVersionService.getById(request.getParam());
        log.info("查询结果:{}", JSON.toJSONString(dto));
        return GenericBaseResponse.ok(dto);
    }
}
