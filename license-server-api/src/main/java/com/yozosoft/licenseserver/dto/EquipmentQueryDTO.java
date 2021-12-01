package com.yozosoft.licenseserver.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EquipmentQueryDTO {

    @NotNull
    private Long cdkeyId;

    private String mac;

    private String cpuId;

    private String biosId;

    private String ip;
}
