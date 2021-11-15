package com.yozosoft.licenseserver.web;

import com.github.pagehelper.PageInfo;
import com.yozosoft.licenseserver.common.util.IResult;
import com.yozosoft.licenseserver.constant.EnumResultCode;
import com.yozosoft.licenseserver.dto.AuthorizationDTO;
import com.yozosoft.licenseserver.dto.AuthorizationQueryDTO;
import com.yozosoft.licenseserver.exception.LicenseException;
import com.yozosoft.licenseserver.model.dto.AuthorizationInfoDTO;
import com.yozosoft.licenseserver.model.dto.PageDTO;
import com.yozosoft.licenseserver.model.po.ClientInfoPO;
import com.yozosoft.licenseserver.service.authorization.AuthorizationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AuthorizationController {

    @Autowired
    AuthorizationManager authorizationManager;

    @PostMapping("/authorization")
    public ResponseEntity addAuthorization(@RequestBody @Valid AuthorizationDTO authorizationDTO) {
        authorizationDTO.setId(null);
        IResult<Integer> addResult = authorizationManager.addAuthorization(authorizationDTO);
        if (!addResult.isSuccess()) {
            throw new LicenseException(addResult.getCode(), addResult.getMessage());
        }
        return ResponseEntity.ok(EnumResultCode.E_SUCCESS.getInfo());
    }

    @GetMapping("/authorization")
    public ResponseEntity getAuthorization(@Valid PageDTO pageDTO, AuthorizationQueryDTO authorizationQueryDTO) {
        IResult<PageInfo<AuthorizationInfoDTO>> getResult = authorizationManager.getAuthorizationInfoByQuery(pageDTO, authorizationQueryDTO);
        if (!getResult.isSuccess()) {
            throw new LicenseException(EnumResultCode.E_AUTHORIZATION_GET_ERROR);
        }
        return ResponseEntity.ok(getResult.getData());
    }

    @PutMapping("/authorization")
    public ResponseEntity updateAuthorization(@RequestBody @Valid AuthorizationDTO authorizationDTO) {
        IResult<Integer> checkResult = authorizationManager.checkUpdateParam(authorizationDTO);
        if (!checkResult.isSuccess()) {
            throw new LicenseException(checkResult.getCode(), checkResult.getMessage(), HttpStatus.BAD_REQUEST);
        }
        IResult<Integer> updateResult = authorizationManager.updateAuthorization(authorizationDTO);
        if (!updateResult.isSuccess()) {
            throw new LicenseException(updateResult.getCode(), updateResult.getMessage());
        }
        return ResponseEntity.ok(EnumResultCode.E_SUCCESS.getInfo());
    }

    @GetMapping("/equipment")
    public ResponseEntity getEquipmentDetail(@RequestParam Long cdkeyId) {
        IResult<List<ClientInfoPO>> getResult = authorizationManager.selectEquipmentDetail(cdkeyId);
        if (!getResult.isSuccess()) {
            throw new LicenseException(EnumResultCode.E_CLIENT_INFO_GET_ERROR);
        }
        return ResponseEntity.ok(getResult.getData());
    }
}
