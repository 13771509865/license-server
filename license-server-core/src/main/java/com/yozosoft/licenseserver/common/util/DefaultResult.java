package com.yozosoft.licenseserver.common.util;

import com.yozosoft.licenseserver.constant.EnumResultCode;

public class DefaultResult<T> implements IResult<T> {

    private Integer code;

    private String message;

    private T data;

    public DefaultResult(Integer code, String message, T data){
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public DefaultResult(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    public DefaultResult(EnumResultCode resultCode){
        this.code = resultCode.getValue();
        this.message = resultCode.getInfo();
    }

    public static <T> DefaultResult<T> successResult(){
        return new DefaultResult(0, "operation success");
    }

    public static <T> DefaultResult<T> successResult(T data){
        return new DefaultResult(0, "operation success", data);
    }

    public static <T> DefaultResult<T> failResult(){
        return new DefaultResult(1, "operation fail");
    }

    public static <T> DefaultResult<T> failResult(T data){
        return new DefaultResult(1, "operation fail", data);
    }

    @Override
    public Boolean isSuccess() {
        return code!=null && code.equals(0) ;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public T getData() {
        return data;
    }

    @Override
    public void setData(T obj) {
        this.data = obj;
    }
}
