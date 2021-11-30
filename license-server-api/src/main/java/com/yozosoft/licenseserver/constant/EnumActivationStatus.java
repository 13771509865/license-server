package com.yozosoft.licenseserver.constant;

import lombok.Getter;

@Getter
public enum EnumActivationStatus {

    E_NOT_ACTIVE((byte)2, "未激活"),
    E_ACTIVE((byte)3, "已激活"),
    E_ACTIVATING((byte)4, "激活中"),
    E_REACTIVE((byte)5, "重新激活"),
    E_EXPIRED((byte)6, "已到期");

    private Byte value;

    private String info;

    EnumActivationStatus(Byte value, String info){
        this.value = value;
        this.info = info;
    }
}
