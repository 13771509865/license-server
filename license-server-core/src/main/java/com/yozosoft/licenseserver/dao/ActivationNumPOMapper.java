package com.yozosoft.licenseserver.dao;

import com.yozosoft.licenseserver.model.po.ActivationNumPO;

import java.util.Date;

public interface ActivationNumPOMapper {
    int deleteByPrimaryKey(Long cdkeyId);

    int insertSelective(ActivationNumPO record);

    ActivationNumPO selectByPrimaryKey(Long cdkeyId);

    int updateByPrimaryKeySelective(ActivationNumPO record);

    int reduceNum(Long cdkeyId);

    int increaseNum(Long cdkeyId);

    int increaseActivationNumByOLock(Long cdkeyId, Integer increase, Date updateTime);
}