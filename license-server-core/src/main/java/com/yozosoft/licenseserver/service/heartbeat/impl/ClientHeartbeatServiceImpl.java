package com.yozosoft.licenseserver.service.heartbeat.impl;

import com.yozosoft.licenseserver.common.util.DefaultResult;
import com.yozosoft.licenseserver.common.util.IResult;
import com.yozosoft.licenseserver.dao.ClientInfoPOMapper;
import com.yozosoft.licenseserver.model.dto.CdKeyClientDTO;
import com.yozosoft.licenseserver.service.heartbeat.ClientHeartbeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientHeartbeatServiceImpl implements ClientHeartbeatService {

    @Autowired
    ClientInfoPOMapper clientInfoPOMapper;

    @Override
    public IResult<CdKeyClientDTO> selectCdKeyClientById(Long id) {
        CdKeyClientDTO cdKeyClientDTO = clientInfoPOMapper.selectCdKeyClientById(id);
        return cdKeyClientDTO != null ? DefaultResult.successResult(cdKeyClientDTO) : DefaultResult.failResult();
    }
}
