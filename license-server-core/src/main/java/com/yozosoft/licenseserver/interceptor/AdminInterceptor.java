package com.yozosoft.licenseserver.interceptor;

import com.yozosoft.licenseserver.constant.EnumResultCode;
import com.yozosoft.licenseserver.exception.LicenseException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!"true".equals(request.getSession().getAttribute("loginStatus"))){
            throw new LicenseException(EnumResultCode.E_ILLEGAL_REQUEST, HttpStatus.FORBIDDEN);
        }
        return true;
    }
}
