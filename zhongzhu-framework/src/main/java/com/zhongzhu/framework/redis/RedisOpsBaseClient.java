package com.zhongzhu.framework.redis;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author admin
 */
@Slf4j
public class RedisOpsBaseClient {

    @Value("${spring.application.name}")
    private String prefix;

    protected String wrapperKey(String key) {
        if (StringUtils.isEmpty(key)) {
            log.error("The key is empty>>>>>>>>>>>>>");
            return null;
        }
        return prefix + "_" + key;
    }


    protected Collection<String> wrapperKeys(Collection<String> keys) {
        if (CollectionUtils.isEmpty(keys)) {
            log.error("The key is empty>>>>>>>>>>>>>");
            return null;
        }
        List<String> opsKeys = new ArrayList<>();
        for (String key : keys) {
            opsKeys.add(prefix + "_" + key);
        }
        return opsKeys;
    }

    protected Map<String, Object> wrapperKeys(Map<String, Object> paramMap) {
        if (CollectionUtils.isEmpty(paramMap)) {
            log.error("The paramMap is empty>>>>>>>>>>>>>");
            return null;
        }
        Map<String, Object> opsMap = new HashMap<>(8);
        for (String key : paramMap.keySet()) {
            opsMap.put(prefix + "_" + key, paramMap.get(key));
        }
        return opsMap;
    }
}
