package com.yozosoft.licenseserver.model.po;

import lombok.Data;

import java.util.Date;

@Data
public class AuthorizationPO {
    private Long id;

    private Date createTime;

    private Date updateTime;

    private Byte status;

    private String customerName;

    private String customerRegion;

    private Date customerDate;

    private String customerRemark;

    private String salerName;

    private String salerMail;

    private String salerPhone;

    private Long cdkeyId;

    private String remark;
}