package com.yozosoft.licenseserver.constant;

import lombok.Getter;

/**
 * 激活码授权类型
 *
 * @author zhouf
 */
@Getter
public enum EnumActivationType {

    E_TRIAL_TYPE((byte)0, "试用类型"),
    E_OFFICIAL_TYPE((byte)1, "正式类型");

    private Byte value;

    private String info;

    EnumActivationType(Byte value, String info) {
        this.value = value;
        this.info = info;
    }
}
