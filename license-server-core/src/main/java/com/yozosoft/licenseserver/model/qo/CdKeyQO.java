package com.yozosoft.licenseserver.model.qo;

import lombok.Data;

import java.util.Date;

@Data
public class CdKeyQO {

    private Long id;

    private Byte product;

    private String version;

    private Byte cdkType;

    private String cdkey;

    private Byte status;

    private Date cdkCreateTimeStart;

    private Date cdkCreateTimeEnd;
}
