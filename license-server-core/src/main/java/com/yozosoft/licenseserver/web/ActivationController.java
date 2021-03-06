package com.yozosoft.licenseserver.web;

import com.github.pagehelper.PageInfo;
import com.yozosoft.licenseserver.common.util.IResult;
import com.yozosoft.licenseserver.constant.EnumResultCode;
import com.yozosoft.licenseserver.dto.ActivationDTO;
import com.yozosoft.licenseserver.dto.ActivationQueryDTO;
import com.yozosoft.licenseserver.exception.LicenseException;
import com.yozosoft.licenseserver.model.dto.PageDTO;
import com.yozosoft.licenseserver.model.po.CdKeyPO;
import com.yozosoft.licenseserver.service.activation.ActivationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 激活码管理
 *
 * @author zhouf
 */
@RestController
@RequestMapping("/api/admin")
public class ActivationController {

    @Autowired
    ActivationManager activationManager;

    @PostMapping("/activation")
    public ResponseEntity addActivation(@RequestBody @Valid ActivationDTO activationDTO) {
        IResult<Integer> addResult = activationManager.addActivation(activationDTO);
        if (!addResult.isSuccess()) {
            throw new LicenseException(addResult.getCode(), addResult.getMessage());
        }
        return ResponseEntity.ok(EnumResultCode.E_SUCCESS.getInfo());
    }

    @GetMapping("/activation")
    public ResponseEntity getActivation(PageDTO pageDTO, ActivationQueryDTO activationQueryDTO) {
        IResult<PageInfo<CdKeyPO>> getResult = activationManager.getActivationsByQuery(pageDTO, activationQueryDTO);
        if (!getResult.isSuccess()) {
            throw new LicenseException(EnumResultCode.E_ACTIVATION_GET_ERROR);
        }
        return ResponseEntity.ok(getResult.getData());
    }

    @DeleteMapping("/activation")
    public ResponseEntity deleteActivation(@RequestParam Long id) {
        IResult<Integer> deleteResult = activationManager.deleteActivation(id);
        if (!deleteResult.isSuccess()) {
            throw new LicenseException(EnumResultCode.E_ACTIVATION_DELETE_ERROR);
        }
        return ResponseEntity.ok(EnumResultCode.E_SUCCESS.getInfo());
    }
}
