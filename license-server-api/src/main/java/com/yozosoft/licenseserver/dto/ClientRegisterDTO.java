package com.yozosoft.licenseserver.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ClientRegisterDTO {

    @NotBlank(message = "cdkey is blank")
    private String cdkey;

    private String ip;

    private String mac;

    @NotBlank(message = "cpuId is blank")
    private String cpuId;

    @NotBlank(message = "biosId is blank")
    private String biosId;

    private String region;

    @NotNull(message = "product is null")
    private Byte product;

    @NotBlank(message = "version is blank")
    private String version;
}
