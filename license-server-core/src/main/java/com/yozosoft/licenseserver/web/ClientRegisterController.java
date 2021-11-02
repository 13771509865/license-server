package com.yozosoft.licenseserver.web;

import com.yozosoft.licenseserver.constant.EnumResultCode;
import com.yozosoft.licenseserver.dto.AuthHeaderDTO;
import com.yozosoft.licenseserver.dto.ClientRegisterConfirmDTO;
import com.yozosoft.licenseserver.dto.ClientRegisterDTO;
import com.yozosoft.licenseserver.dto.ClientRegisterResultDTO;
import com.yozosoft.licenseserver.service.register.ClientRegisterManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/client")
public class ClientRegisterController {

    @Autowired
    ClientRegisterManager clientRegisterManager;

    @PostMapping("/register")
    public ResponseEntity clientRegister(@RequestBody @Valid ClientRegisterDTO clientRegisterDTO, @RequestHeader AuthHeaderDTO authHeaderDTO) {
        ClientRegisterResultDTO clientRegisterResultDTO = clientRegisterManager.clientRegister(clientRegisterDTO);
        return ResponseEntity.ok(clientRegisterResultDTO);
    }

    @PutMapping("/register")
    public ResponseEntity clientRegisterConfirm(@RequestBody @Valid ClientRegisterConfirmDTO clientRegisterConfirmDTO) {
        clientRegisterManager.clientRegisterConfirm(clientRegisterConfirmDTO);
        return ResponseEntity.ok(EnumResultCode.E_SUCCESS.getInfo());
    }
}
