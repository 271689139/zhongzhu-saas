package com.zhongzhu.core.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * @author admin
 * aes 加密解密
 */
public class AesUtil {

    /**
     * 加密
     *
     * @param key  密钥
     * @param data 加密数据
     * @return 密文
     */
    public static byte[] encode(byte[] key, byte[] data) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        return cipher.doFinal(data);
    }

    /**
     * 解密
     *
     * @param key  密钥
     * @param data 密文
     * @return 解密后的数据
     */
    public static byte[] decode(byte[] key, byte[] data) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        return cipher.doFinal(data);
    }

    public static void main(String[] args) throws Exception{
        // 生成随机密钥
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256, new SecureRandom());
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] key = secretKey.getEncoded();
        System.out.println("AES 密钥：" + Base64.getEncoder().encodeToString(key));

        String content = "root";
        System.out.println("原文：" + content);

        byte[] ret = encode(key, content.getBytes());
        System.out.println("加密后的密文：" + Base64.getEncoder().encodeToString(ret));

        byte[] raw = decode(key, ret);
        System.out.println("解密后的原文：" + new String(raw));

    }
}
