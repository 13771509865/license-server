package com.yozosoft.licenseserver.service.heartbeat;

import com.yozosoft.licenseserver.common.util.IResult;
import com.yozosoft.licenseserver.model.dto.CdKeyClientDTO;

public interface ClientHeartbeatService {

    IResult<CdKeyClientDTO> selectCdKeyClientById(Long id);
}
