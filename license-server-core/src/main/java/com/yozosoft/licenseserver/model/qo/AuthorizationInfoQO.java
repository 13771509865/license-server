package com.yozosoft.licenseserver.model.qo;

import lombok.Data;

@Data
public class AuthorizationInfoQO {

    private Long id;

    private String customerName;

    private String salerName;

    private String cdkey;
}
