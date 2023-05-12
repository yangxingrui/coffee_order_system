package edu.whut.yangxingrui.coffeeordersystem.handler;

import cn.hutool.crypto.SecureUtil;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;


public class EncryptHandler{

    private static final byte[] KEYS = "12345678abcdefgh".getBytes(StandardCharsets.UTF_8);

    /**
     * 加密
     */
    public String encrypt(String parameter) {
        if (parameter == null || parameter.length() == 0) {
            return "error";
        }
        return SecureUtil.aes(KEYS).encryptHex(parameter);
    }

    /**
     * 解密
     */
    public String decrypt(String value) {
        if (null == value) {
            return null;
        }
        return SecureUtil.aes(KEYS).decryptStr(value);
    }
}
