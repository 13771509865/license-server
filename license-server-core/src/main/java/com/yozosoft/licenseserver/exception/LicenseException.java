package com.yozosoft.licenseserver.exception;

import com.yozosoft.licenseserver.constant.EnumResultCode;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class LicenseException extends RuntimeException{

    private Integer errorCode;

    private String errorMessage;

    private Object data;

    private HttpStatus httpStatus;

    public LicenseException(Integer errorCode, String errorMessage){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = HttpStatus.EXPECTATION_FAILED;
    }

    public LicenseException(Integer errorCode, String errorMessage, Object data){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.data = data;
        this.httpStatus = HttpStatus.EXPECTATION_FAILED;
    }

    public LicenseException(EnumResultCode enumResultCode){
        this.errorCode = enumResultCode.getValue();
        this.errorMessage = enumResultCode.getInfo();
        this.httpStatus = HttpStatus.EXPECTATION_FAILED;
    }

    public LicenseException(EnumResultCode enumResultCode, HttpStatus httpStatus){
        this.errorCode = enumResultCode.getValue();
        this.errorMessage = enumResultCode.getInfo();
        this.httpStatus = httpStatus;
    }

    public LicenseException(Integer errorCode, String errorMessage, Object data, HttpStatus httpStatus){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.data = data;
        this.httpStatus = httpStatus;
    }

    public LicenseException(Integer errorCode, String errorMessage, HttpStatus httpStatus){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }
}
