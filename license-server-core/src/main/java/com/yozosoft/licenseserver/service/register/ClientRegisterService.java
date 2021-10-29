package com.yozosoft.licenseserver.service.register;

import com.yozosoft.licenseserver.common.util.IResult;
import com.yozosoft.licenseserver.model.po.ClientInfoPO;
import com.yozosoft.licenseserver.model.qo.ClientInfoQO;

import java.util.List;

public interface ClientRegisterService {

    IResult<Integer> insertClientInfo(ClientInfoPO clientInfoPO);

    IResult<List<ClientInfoPO>> selectClientInfoByQuery(ClientInfoQO clientInfoQO);

    IResult<ClientInfoPO> selectClientInfoById(Long id);

    IResult<Integer> deleteClientInfoById(Long id);

    IResult<Integer> updateClientInfo(ClientInfoPO clientInfoPO);

}
