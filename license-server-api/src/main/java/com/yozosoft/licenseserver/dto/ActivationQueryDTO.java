package com.yozosoft.licenseserver.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class ActivationQueryDTO {

    private Long id;

    private Byte product;

    private String version;

    private Byte cdkType;

    private String cdkey;

    private Byte status;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cdkCreateTimeStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cdkCreateTimeEnd;
}
