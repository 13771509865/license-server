package com.yozosoft.licenseserver.constant;

import lombok.Getter;

/**
 * 试用产品
 *
 * @author zhouf
 */
@Getter
public enum EnumProduct {

    E_OFFICE((byte) 0, "office海外版");

    private Byte value;

    private String info;

    EnumProduct(Byte value, String info) {
        this.value = value;
        this.info = info;
    }
}
