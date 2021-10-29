package com.yozosoft.licenseserver.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ClientRegisterResultDTO {

    private Long activationId;

    private Integer heartRateNum;

    private Byte heartRateUnit;

    private Date expireDate;

    private Byte cdkType;

    private Byte product;

    private String version;
}
