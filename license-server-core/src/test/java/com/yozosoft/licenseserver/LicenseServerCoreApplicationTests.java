package com.yozosoft.licenseserver;

import com.yozosoft.licenseserver.dao.ClientInfoPOMapper;
import com.yozosoft.licenseserver.model.po.ClientInfoPO;
import com.yozosoft.licenseserver.util.IpUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class LicenseServerCoreApplicationTests {

    @Autowired
    private ClientInfoPOMapper clientInfoPOMapper;

    @Test
    void contextLoads() {
        ClientInfoPO clientInfoPO = new ClientInfoPO();
        clientInfoPO.setId(1L);
        clientInfoPO.setUpdateTime(new Date());
        clientInfoPO.setCreateTime(new Date());
        clientInfoPO.setExpireDate(new Date());
        clientInfoPO.setStatus((byte)1);
        clientInfoPO.setMac("mac");
        clientInfoPO.setCpuId("cpuid");
        clientInfoPO.setBiosId("biosId");
        clientInfoPO.setCdkeyId(2L);
        clientInfoPO.setIp(IpUtils.inetAton("127.0.0.1"));
        clientInfoPOMapper.insertSelective(clientInfoPO);
        System.out.println("===end");
    }

}
