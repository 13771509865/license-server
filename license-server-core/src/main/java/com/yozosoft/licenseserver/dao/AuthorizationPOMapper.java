package com.yozosoft.licenseserver.dao;

import com.yozosoft.licenseserver.model.dto.AuthorizationInfoDTO;
import com.yozosoft.licenseserver.model.po.AuthorizationPO;
import com.yozosoft.licenseserver.model.qo.AuthorizationInfoQO;

import java.util.List;

public interface AuthorizationPOMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(AuthorizationPO record);

    AuthorizationPO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AuthorizationPO record);

    List<AuthorizationInfoDTO> selectAuthorizationInfoByQuery(AuthorizationInfoQO authorizationInfoQO);
}