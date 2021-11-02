package com.yozosoft.licenseserver.constant;

import lombok.Getter;

/**
 * 试用产品
 *
 * @author zhouf
 */
@Getter
public enum EnumProduct {

    E_OFFICE((byte) 0, "office海外版", "yozoOffice");

    private Byte value;

    private String info;

    private String secret;

    EnumProduct(Byte value, String info, String secret) {
        this.value = value;
        this.info = info;
        this.secret = secret;
    }
}
