package com.yozosoft.licenseserver.util;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

/**
 * X-Auth签名头信息工具类
 *
 * @author zhouf
 */
public class XAuthUtils {

    public static String buildYozoAuth(String date, String secret, String contentMd5) {
        String str = contentMd5 + date;
        String hmacSha1 = Hashing.hmacSha1(secret.getBytes(StandardCharsets.UTF_8)).hashString(str, StandardCharsets.UTF_8).toString();
        return hmacSha1;
    }
}
