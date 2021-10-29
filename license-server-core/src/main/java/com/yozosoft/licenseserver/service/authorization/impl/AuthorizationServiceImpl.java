package com.yozosoft.licenseserver.service.authorization.impl;

import com.yozosoft.licenseserver.common.util.DefaultResult;
import com.yozosoft.licenseserver.common.util.IResult;
import com.yozosoft.licenseserver.dao.AuthorizationPOMapper;
import com.yozosoft.licenseserver.model.dto.AuthorizationInfoDTO;
import com.yozosoft.licenseserver.model.po.AuthorizationPO;
import com.yozosoft.licenseserver.model.qo.AuthorizationInfoQO;
import com.yozosoft.licenseserver.service.authorization.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    @Autowired
    AuthorizationPOMapper authorizationPOMapper;

    @Override
    public IResult<Integer> insertAuthorization(AuthorizationPO authorizationPO) {
        int i = authorizationPOMapper.insertSelective(authorizationPO);
        return i>0? DefaultResult.successResult():DefaultResult.failResult();
    }

    @Override
    public IResult<List<AuthorizationInfoDTO>> selectAuthorizationInfo(AuthorizationInfoQO authorizationInfoQO) {
        List<AuthorizationInfoDTO> authorizationInfoDTOs = authorizationPOMapper.selectAuthorizationInfoByQuery(authorizationInfoQO);
        return DefaultResult.successResult(authorizationInfoDTOs);
    }

    @Override
    public IResult<Integer> updateAuthorization(AuthorizationPO authorizationPO) {
        int i = authorizationPOMapper.updateByPrimaryKeySelective(authorizationPO);
        return i>0? DefaultResult.successResult():DefaultResult.failResult();
    }
}
