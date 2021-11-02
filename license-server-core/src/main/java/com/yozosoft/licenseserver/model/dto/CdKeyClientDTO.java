package com.yozosoft.licenseserver.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CdKeyClientDTO {

    private Long id;

    private Byte status;

    private Long cdkeyId;

    private String cpuId;

    private String biosId;

    private String mac;

    private Byte ip;

    private Date expireDate;

    private Byte product;

    private String version;

    private String region;

    private Byte cdkType;

    private Integer heartRateNum;

    private Byte heartRateUnit;
}
