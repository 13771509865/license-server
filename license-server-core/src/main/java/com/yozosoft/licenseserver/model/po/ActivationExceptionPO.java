package com.yozosoft.licenseserver.model.po;

import lombok.Data;

import java.util.Date;

@Data
public class ActivationExceptionPO {
    private Long id;

    private Date createTime;

    private Date updateTime;

    private Long cdkeyId;

    private String cdkey;

    private Byte ip;

    private String mac;

    private String cpuId;

    private String biosId;

    private Byte exceptionId;

    private String remark;
}