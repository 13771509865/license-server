package com.yozosoft.licenseserver.dao;

import com.yozosoft.licenseserver.model.po.CdKeyPO;
import com.yozosoft.licenseserver.model.qo.CdKeyQO;

import java.util.List;

public interface CdKeyPOMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(CdKeyPO record);

    CdKeyPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CdKeyPO record);

    List<CdKeyPO> selectByQuery(CdKeyQO cdKeyQO);

    CdKeyPO selectByCdk(String cdkey);
}