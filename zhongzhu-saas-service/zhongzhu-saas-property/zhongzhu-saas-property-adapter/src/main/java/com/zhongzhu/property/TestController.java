package com.zhongzhu.property;

import com.alibaba.fastjson.JSON;
import com.zhongzhu.property.domain.SystemVersion;
import com.zhongzhu.property.service.SystemVersionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author admin
 */
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    private final SystemVersionService systemVersionService;

    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    public @ResponseBody SystemVersion get(@RequestParam("id") Long id) {
        log.info("get入参:{}", id);
        SystemVersion systemVersion = systemVersionService.getById(id);
        log.info("查询结果:{}", JSON.toJSONString(systemVersion));
        return systemVersion;
    }
}
