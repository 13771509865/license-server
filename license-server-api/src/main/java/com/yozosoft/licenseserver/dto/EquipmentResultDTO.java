package com.yozosoft.licenseserver.dto;

import lombok.Data;

import java.util.Date;

@Data
public class EquipmentResultDTO {

    private Long id;

    private Date createTime;

    private Date updateTime;

    private Byte status;

    private Long cdkeyId;

    private String cpuId;

    private String biosId;

    private String mac;

    private String ip;

    private Date expireDate;
}
