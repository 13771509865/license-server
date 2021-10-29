package com.yozosoft.licenseserver.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ActivationQueryDTO {

    private Long id;

    private Byte product;

    private String version;

    private Byte cdkType;

    private String cdkey;

    private Date cdkCreateTimeStart;

    private Date cdkCreateTimeEnd;
}