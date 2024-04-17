package com.zhongzhu.druid.callback;

import com.alibaba.druid.util.DruidPasswordCallback;
import com.zhongzhu.core.utils.AesUtil;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Base64;
import java.util.Properties;

/**
 * 解析秘文密码
 *
 * @author shihao.liu
 */
@Slf4j
public class AesDruidPasswordCallback extends DruidPasswordCallback {

    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
        // 从druid的connectProperties中获取自定义的配置信息
        String pwd = properties.getProperty("pwd");
        String key = properties.getProperty("key");
        char[] p = null;
        try {
            if (StringUtils.isNotBlank(pwd) && StringUtils.isNotBlank(key)) {
                // 解密密码
                String newPassword = new String(AesUtil.decode(Base64.getDecoder().decode(key),
                        Base64.getDecoder().decode(pwd)));
                p = newPassword.toCharArray();
            }
        } catch (Exception ex) {
            log.error("[AesDruidPasswordCallback]解密失败：{}", ex.getMessage(), ex);
        }
        super.setPassword(p);
    }
}
