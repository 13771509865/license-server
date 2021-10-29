package com.yozosoft.licenseserver.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class AuthorizationDTO {

    private Long id;

    @NotBlank(message = "customer name is blank")
    private String customerName;

    private String customerRegion;

    @NotNull(message = "purchase date is null")
    private Date customerDate;

    private String customerRemark;

    private String salerName;

    private String salerMail;

    private String salerPhone;

    @NotNull(message = "cdkey is null")
    private Long cdkeyId;

    private Integer expireNum;

    private Byte expireUnit;

    private Date expireDate;

    @NotNull(message = "purchase quantity is null")
    private Integer permitNum;

    @NotNull(message = "number of heart rate is null")
    private Integer heartRateNum;

    @NotNull(message = "unit of heart rate is null")
    private Byte heartRateUnit;

    @NotNull(message = "activation mode is null")
    private Byte mode;
}
