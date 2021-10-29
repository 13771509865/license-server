package com.yozosoft.licenseserver.service.register;

import com.yozosoft.licenseserver.dto.ClientRegisterConfirmDTO;
import com.yozosoft.licenseserver.dto.ClientRegisterDTO;
import com.yozosoft.licenseserver.dto.ClientRegisterResultDTO;

public interface ClientRegisterManager {

    ClientRegisterResultDTO clientRegister(ClientRegisterDTO clientRegisterDTO);

    void clientRegisterConfirm(ClientRegisterConfirmDTO clientRegisterConfirmDTO);

    void cancelRegister(Long activationId);
}
