package com.yozosoft.licenseserver.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Date;

/**
 * X-Auth签名头信息工具类
 * @author zhouf
 */
public class XAuthUtils {

    public static String buildYozoAuth(Date date, String secret, String contentMd5){
        String str = secret + contentMd5 + date.toString();
        String sha1 = DigestUtils.sha1Hex(str);
        return sha1;
    }
}
