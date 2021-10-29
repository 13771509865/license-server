package com.yozosoft.licenseserver.common.util;

import com.yozosoft.licenseserver.constant.EnumResultCode;
import com.yozosoft.licenseserver.dto.ErrorResultDTO;
import org.springframework.http.HttpStatus;

/**
 * @author zhouf
 */
public class ResultUtils {

    public static ErrorResultDTO buildErrorResult(Integer errorCode, String errorMessage){
        return buildErrorResult(errorCode, errorMessage, null);
    }

    public static ErrorResultDTO buildErrorResult(Integer errorCode, String errorMessage, Object data){
        ErrorResultDTO errorResultDTO = new ErrorResultDTO();
        errorResultDTO.setErrorCode(errorCode);
        errorResultDTO.setErrorMessage(errorMessage);
        errorResultDTO.setData(data);
        return errorResultDTO;
    }

    public static ErrorResultDTO buildErrorResult(HttpStatus httpStatus){
        ErrorResultDTO errorResultDTO = new ErrorResultDTO();
        errorResultDTO.setErrorCode(httpStatus.value());
        errorResultDTO.setErrorMessage(httpStatus.getReasonPhrase());
        return errorResultDTO;
    }

    public static ErrorResultDTO buildErrorResult(EnumResultCode resultCode){
        ErrorResultDTO errorResultDTO = new ErrorResultDTO();
        errorResultDTO.setErrorCode(resultCode.getValue());
        errorResultDTO.setErrorMessage(resultCode.getInfo());
        return errorResultDTO;
    }
}
