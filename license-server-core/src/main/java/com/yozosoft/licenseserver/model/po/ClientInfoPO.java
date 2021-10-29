package com.yozosoft.licenseserver.model.po;

import lombok.Data;

import java.util.Date;

@Data
public class ClientInfoPO {
    private Long id;

    private Date createTime;

    private Date updateTime;

    private Byte status;

    private Long cdkeyId;

    private String cpuId;

    private String biosId;

    private String mac;

    private Byte ip;

    private Date expireDate;
}