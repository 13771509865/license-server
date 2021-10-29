package com.yozosoft.licenseserver.constant;

import lombok.Getter;

@Getter
public enum EnumTimeUnit {

    E_DAY_UNIT((byte)0,"day"),
    E_WEEK_UNIT((byte)1,"week"),
    E_MONTH_UNIT((byte)2,"month"),
    E_YEAR_UNIT((byte)3,"year");

    private Byte value;

    private String info;

    EnumTimeUnit(Byte value, String info){
        this.value = value;
        this.info = info;
    }
}
