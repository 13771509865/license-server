package com.yozosoft.licenseserver.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AuthorizationInfoDTO {

    private Long id;

    private Byte status;

    private String customerName;

    private String customerRegion;

    private Date customerDate;

    private String customerRemark;

    private String salerName;

    private String salerMail;

    private String salerPhone;

    private Long cdkeyId;

    private Byte cdkStatus;

    private String cdkey;

    private Date cdkCreateTime;

    private String machine;

    private String producer;

    private Byte product;

    private String version;

    private String region;

    private Byte cdkType;

    private Byte mode;

    private Integer expireNum;

    private Byte expireUnit;

    private Date expireDate;

    private Integer permitNum;

    private Integer heartRateNum;

    private Byte heartRateUnit;

    private Integer surplus;
}
