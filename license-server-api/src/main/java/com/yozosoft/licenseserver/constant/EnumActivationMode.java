package com.yozosoft.licenseserver.constant;

import lombok.Getter;

/**
 * 授权激活模式
 * @author zhouf
 */
@Getter
public enum EnumActivationMode {

    E_RELATIVE_MODE((byte)0, "按激活时间开始计"),
    E_ABSOLUTELY_MODE((byte)1, "按固定过期时间计");

    private Byte value;

    private String info;

    EnumActivationMode(Byte value, String info) {
        this.value = value;
        this.info = info;
    }
}
