package com.yozosoft.licenseserver.dao;

import com.yozosoft.licenseserver.model.dto.CdKeyClientDTO;
import com.yozosoft.licenseserver.model.po.ClientInfoPO;
import com.yozosoft.licenseserver.model.qo.ClientInfoQO;

import java.util.List;

public interface ClientInfoPOMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(ClientInfoPO record);

    ClientInfoPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ClientInfoPO record);

    List<ClientInfoPO> selectByQuery(ClientInfoQO clientInfoQO);

    CdKeyClientDTO selectCdKeyClientById(Long id);
}