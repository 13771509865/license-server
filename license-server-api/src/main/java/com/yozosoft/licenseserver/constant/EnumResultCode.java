package com.yozosoft.licenseserver.constant;

import lombok.Getter;

/**
 * @author zhoufeng
 * @description 返回值枚举类
 * @create 2021-10-21 15:03
 **/
@Getter
public enum EnumResultCode {
    /**
     * 返回值枚举类
     */
    E_SUCCESS(0, "operation success"),
    E_FAIL(1, "operation failed"),
    E_INVALID_PARAM(2, "Parameter verification failed"),
    E_SERVER_UNKNOWN_ERROR(3, "Server unknown error"),

    E_ACTIVATION_ADD_ERROR(10, "failed to enter activation code"),
    E_ACTIVATION_GET_ERROR(11, "failed to query activation code"),
    E_AUTHORIZATION_ADD_ERROR(12, "failed to enter authorization"),
    E_AUTHORIZATION_GET_ERROR(13, "failed to query authorization"),
    E_AUTHORIZATION_UPDATE_ERROR(14,"failed to update authorization"),

    E_ACTIVATION_NOT_EXIST(20, "activation not exist"),
    E_ACTIVATION_PRODUCT_MISMATCH(21, "activation product mismatch"),
    E_ACTIVATION_STATUS_ILLEGAL(22, "activation is illegal"),
    E_REPEAT_ACTIVATION_ERROR(23, "repeat activation failed"),
    E_PERMIT_NUM_LACK(24, "activation permit num lack"),
    E_ACTIVATION_ERROR(25, "activation failed"),

    E_ACTIVATION_CONFIRM_ERROR(30, "activation confirm failed"),
    ;

    private Integer value;
    private String info;

    EnumResultCode(Integer value, String info) {
        this.value = value;
        this.info = info;
    }

    public static EnumResultCode getEnum(Integer value) {
        for (EnumResultCode code : values()) {
            if (code.getValue().equals(value)) {
                return code;
            }
        }
        return null;
    }
}