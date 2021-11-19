package com.yozosoft.licenseserver.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ClientRegisterConfirmDTO {

    @NotNull(message = "activationId is null")
    private Long activationId;

    @NotNull(message = "activation result is null")
    private Byte activationResult;
}
