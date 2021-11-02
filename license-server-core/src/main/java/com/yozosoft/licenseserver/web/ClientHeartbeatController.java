package com.yozosoft.licenseserver.web;

import com.yozosoft.licenseserver.dto.ClientHeartbeatDTO;
import com.yozosoft.licenseserver.dto.ClientHeartbeatResultDTO;
import com.yozosoft.licenseserver.service.heartbeat.ClientHeartbeatManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/client")
public class ClientHeartbeatController {

    @Autowired
    ClientHeartbeatManager clientHeartbeatManager;

    @PostMapping("/heartbeat")
    public ResponseEntity clientHeartbeat(@RequestBody @Valid ClientHeartbeatDTO clientHeartbeatDTO) {
        ClientHeartbeatResultDTO clientHeartbeatResultDTO = clientHeartbeatManager.clientHeartbeat(clientHeartbeatDTO);
        return ResponseEntity.ok(clientHeartbeatResultDTO);
    }
}
