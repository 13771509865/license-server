package com.yozosoft.licenseserver.dto;

import lombok.Data;

@Data
public class AuthorizationQueryDTO {

    private Long id;

    private String customerName;

    private String salerName;

    private String cdkey;
}
