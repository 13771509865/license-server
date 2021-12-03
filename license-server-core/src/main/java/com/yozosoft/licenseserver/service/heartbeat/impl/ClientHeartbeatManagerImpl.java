package com.yozosoft.licenseserver.service.heartbeat.impl;

import com.yozosoft.licenseserver.common.util.IResult;
import com.yozosoft.licenseserver.constant.EnumActivationStatus;
import com.yozosoft.licenseserver.constant.EnumResultCode;
import com.yozosoft.licenseserver.dto.ClientHeartbeatDTO;
import com.yozosoft.licenseserver.dto.ClientHeartbeatResultDTO;
import com.yozosoft.licenseserver.exception.LicenseException;
import com.yozosoft.licenseserver.model.dto.CdKeyClientDTO;
import com.yozosoft.licenseserver.service.heartbeat.ClientHeartbeatManager;
import com.yozosoft.licenseserver.service.heartbeat.ClientHeartbeatService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
@Slf4j
public class ClientHeartbeatManagerImpl implements ClientHeartbeatManager {

    @Autowired
    ClientHeartbeatService clientHeartbeatService;

    @Override
    public ClientHeartbeatResultDTO clientHeartbeat(ClientHeartbeatDTO clientHeartbeatDTO) {
        Long activationId = clientHeartbeatDTO.getActivationId();
        IResult<CdKeyClientDTO> getResult = clientHeartbeatService.selectCdKeyClientById(activationId);
        if (!getResult.isSuccess()) {
            log.error("心跳验证查询CdKeyClientInfo失败,activationId为:" + activationId);
            throw new LicenseException(EnumResultCode.E_HEARTBEAT_ERROR);
        }
        CdKeyClientDTO cdKeyClientDTO = getResult.getData();
        checkHeartbeat(clientHeartbeatDTO, cdKeyClientDTO);
        return buildClientHeartbeatResultDTO(cdKeyClientDTO);
    }

    private ClientHeartbeatResultDTO buildClientHeartbeatResultDTO(CdKeyClientDTO cdKeyClientDTO) {
        ClientHeartbeatResultDTO clientHeartbeatResultDTO = new ClientHeartbeatResultDTO();
        clientHeartbeatResultDTO.setActivationId(cdKeyClientDTO.getId());
        clientHeartbeatResultDTO.setHeartRateNum(cdKeyClientDTO.getHeartRateNum());
        clientHeartbeatResultDTO.setHeartRateUnit(cdKeyClientDTO.getHeartRateUnit());
        clientHeartbeatResultDTO.setCdkType(cdKeyClientDTO.getCdkType());
        clientHeartbeatResultDTO.setExpireDate(cdKeyClientDTO.getExpireDate());
        return clientHeartbeatResultDTO;
    }

    private void checkHeartbeat(ClientHeartbeatDTO clientHeartbeatDTO, CdKeyClientDTO cdKeyClientDTO) {
        Boolean boolBios = cdKeyClientDTO.getBiosId().equals(clientHeartbeatDTO.getBiosId());
        Boolean boolCpu = cdKeyClientDTO.getCpuId().equals(clientHeartbeatDTO.getCpuId());
        if (!(boolBios && boolCpu)) {
            throw new LicenseException(EnumResultCode.E_HEARTBEAT_MACHINE_MISMATCH);
        }
        Boolean boolProduct = cdKeyClientDTO.getProduct().equals(clientHeartbeatDTO.getProduct());
        if (!boolProduct) {
            throw new LicenseException(EnumResultCode.E_HEARTBEAT_PRODUCT_MISMATCH);
        }
        if (!(cdKeyClientDTO.getStatus().equals((byte) 1) || cdKeyClientDTO.getStatus().equals(EnumActivationStatus.E_REACTIVE.getValue()))) {
            log.error("心跳验证时,status状态非正常");
            throw new LicenseException(EnumResultCode.E_HEARTBEAT_ERROR);
        }
        if (DateUtils.truncatedCompareTo(cdKeyClientDTO.getExpireDate(), new Date(), Calendar.MILLISECOND) <= 0) {
            throw new LicenseException(EnumResultCode.E_HEARTBEAT_EXPIRED);
        }
    }
}
