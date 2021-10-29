package com.yozosoft.licenseserver.model.po;

import lombok.Data;
import java.util.Date;

@Data
public class CdKeyPO {
    private Long id;

    private Date createTime;

    private Date updateTime;

    private Byte status;

    private String cdkey;

    private Date cdkCreateTime;

    private String machine;

    private String producer;

    private Byte product;

    private String version;

    private String region;

    private Byte cdkType;

    private Byte mode;

    private Integer expireNum;

    private Byte expireUnit;

    private Date expireDate;

    private Integer permitNum;

    private Integer heartRateNum;

    private Byte heartRateUnit;

    private String remark;
}