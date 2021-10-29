package com.yozosoft.licenseserver.service.register.impl;

import com.yozosoft.licenseserver.common.util.DefaultResult;
import com.yozosoft.licenseserver.common.util.IResult;
import com.yozosoft.licenseserver.dao.ClientInfoPOMapper;
import com.yozosoft.licenseserver.model.po.ClientInfoPO;
import com.yozosoft.licenseserver.model.qo.ClientInfoQO;
import com.yozosoft.licenseserver.service.register.ClientRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientRegisterServiceImpl implements ClientRegisterService {

    @Autowired
    ClientInfoPOMapper clientInfoPOMapper;

    @Override
    public IResult<Integer> insertClientInfo(ClientInfoPO clientInfoPO) {
        int i = clientInfoPOMapper.insertSelective(clientInfoPO);
        return i > 0 ? DefaultResult.successResult() : DefaultResult.failResult();
    }

    @Override
    public IResult<List<ClientInfoPO>> selectClientInfoByQuery(ClientInfoQO clientInfoQO) {
        List<ClientInfoPO> clientInfoPOs = clientInfoPOMapper.selectByQuery(clientInfoQO);
        return DefaultResult.successResult(clientInfoPOs);
    }

    @Override
    public IResult<Integer> deleteClientInfoById(Long id) {
        int i = clientInfoPOMapper.deleteByPrimaryKey(id);
        return i > 0 ? DefaultResult.successResult() : DefaultResult.failResult();
    }

    @Override
    public IResult<Integer> updateClientInfo(ClientInfoPO clientInfoPO) {
        int i = clientInfoPOMapper.updateByPrimaryKeySelective(clientInfoPO);
        return i > 0 ? DefaultResult.successResult() : DefaultResult.failResult();
    }

    @Override
    public IResult<ClientInfoPO> selectClientInfoById(Long id) {
        ClientInfoPO clientInfoPO = clientInfoPOMapper.selectByPrimaryKey(id);
        return clientInfoPO != null ? DefaultResult.successResult(clientInfoPO) : DefaultResult.failResult();
    }
}
