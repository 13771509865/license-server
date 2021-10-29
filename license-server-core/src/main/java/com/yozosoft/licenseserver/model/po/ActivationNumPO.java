package com.yozosoft.licenseserver.model.po;

import lombok.Data;

import java.util.Date;

@Data
public class ActivationNumPO {
    private Long cdkeyId;

    private Date createTime;

    private Date updateTime;

    private Byte status;

    private Integer permitNum;

    private Integer surplus;
}