package com.yozosoft.licenseserver.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class ActivationDTO {

    @NotBlank(message = "cdkey is blank")
    private String cdkey;

    private Date cdkCreateTime;

    private String machine;

    private String producer;

    private Byte product;

    private String version;

    private String region;

    @NotNull(message = "activation type is null")
    private Byte cdkType;

    @NotNull(message = "activation mode is null")
    private Byte mode;
}
