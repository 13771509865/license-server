package com.yozosoft.licenseserver.model.qo;

import lombok.Data;

@Data
public class ClientInfoQO {

    private Long id;

    private Long cdkeyId;

    private String cpuId;

    private String biosId;

    private String mac;

    private Byte status;
}
