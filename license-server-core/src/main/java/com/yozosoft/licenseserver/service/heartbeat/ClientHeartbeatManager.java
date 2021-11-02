package com.yozosoft.licenseserver.service.heartbeat;

import com.yozosoft.licenseserver.dto.ClientHeartbeatDTO;
import com.yozosoft.licenseserver.dto.ClientHeartbeatResultDTO;

public interface ClientHeartbeatManager {

    ClientHeartbeatResultDTO clientHeartbeat(ClientHeartbeatDTO clientHeartbeatDTO);
}
