package com.yozosoft.licenseserver.dto;

import lombok.Data;

@Data
public class AuthHeaderDTO {

    private String contentMd5;

    private String xAuth;

    private String date;
}
