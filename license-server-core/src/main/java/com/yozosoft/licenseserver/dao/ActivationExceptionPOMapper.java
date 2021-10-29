package com.yozosoft.licenseserver.dao;

import com.yozosoft.licenseserver.model.po.ActivationExceptionPO;

public interface ActivationExceptionPOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ActivationExceptionPO record);

    int insertSelective(ActivationExceptionPO record);

    ActivationExceptionPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ActivationExceptionPO record);

    int updateByPrimaryKey(ActivationExceptionPO record);
}