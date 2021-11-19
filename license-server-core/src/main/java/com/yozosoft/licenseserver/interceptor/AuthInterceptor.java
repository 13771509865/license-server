package com.yozosoft.licenseserver.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.yozosoft.licenseserver.constant.EnumProduct;
import com.yozosoft.licenseserver.constant.EnumResultCode;
import com.yozosoft.licenseserver.exception.LicenseException;
import com.yozosoft.licenseserver.util.XAuthUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Component
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String date = request.getHeader("Date");
        String contentMd5 = request.getHeader("Content-Md5");
        String xAuth = request.getHeader("X-Auth");
        if (StringUtils.isBlank(date) || StringUtils.isBlank(contentMd5) || StringUtils.isBlank(xAuth)) {
            throw new LicenseException(EnumResultCode.E_INVALID_HEADER, HttpStatus.BAD_REQUEST);
        }
        String bodyStr = StreamUtils.copyToString(request.getInputStream(), Charset.forName("UTF-8"));
        String requestMd5 = DigestUtils.md5Hex(JSON.parseObject(bodyStr, Feature.OrderedField).toJSONString());
        if (!contentMd5.equals(requestMd5)) {
            log.error("输入request body内容md5不匹配");
            throw new LicenseException(EnumResultCode.E_ILLEGAL_REQUEST, HttpStatus.FORBIDDEN);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        Date parse = simpleDateFormat.parse(date);
        String requestAuth = XAuthUtils.buildYozoAuth(parse, EnumProduct.E_OFFICE.getSecret(), requestMd5);
        if (!xAuth.equals(requestAuth)) {
            log.error("输入request auth不匹配");
            throw new LicenseException(EnumResultCode.E_ILLEGAL_REQUEST, HttpStatus.FORBIDDEN);
        }
        return true;
    }
}
