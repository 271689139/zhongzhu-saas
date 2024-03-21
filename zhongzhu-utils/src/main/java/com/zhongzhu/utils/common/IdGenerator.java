package com.zhongzhu.utils.common;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * @author admin
 * Uuid 生成
 */
@Slf4j
public class IdGenerator {

    public static String generatorStringId() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        log.info("generate String uuid, uuid = {}", uuid);
        return uuid;
    }
}
