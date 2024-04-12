package com.snjx.kelimaca.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    public static String generateMd5(String input) {
        try {
            // 获取MessageDigest实例，指定使用MD5算法
            MessageDigest md = MessageDigest.getInstance("MD5");

            // 将待加密的字符串转换为字节数组
            byte[] messageDigestBytes = input.getBytes();

            // 执行MD5哈希计算
            byte[] hashBytes = md.digest(messageDigestBytes);

            // 将哈希结果转换为16进制字符串返回
            return String.format("%032x", new BigInteger(1, hashBytes));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("MD5 algorithm not available", e);
        }
    }

    // 示例用法
    public static void main(String[] args) {
        //e3bccdb7c106eb272cca5785a87779c8
        String inputString = "Your plaintext string";
        String md5Hash = generateMd5(inputString);
        System.out.println("MD5 Hash of \"" + inputString + "\": " + md5Hash);
    }
}