package com.yozosoft.licenseserver.service.authorization;

import com.yozosoft.licenseserver.common.util.IResult;
import com.yozosoft.licenseserver.model.dto.AuthorizationInfoDTO;
import com.yozosoft.licenseserver.model.po.AuthorizationPO;
import com.yozosoft.licenseserver.model.qo.AuthorizationInfoQO;

import java.util.List;

public interface AuthorizationService {

    IResult<Integer> insertAuthorization(AuthorizationPO authorizationPO);

    IResult<List<AuthorizationInfoDTO>> selectAuthorizationInfo(AuthorizationInfoQO authorizationInfoQO);

    IResult<Integer> updateAuthorization(AuthorizationPO authorizationPO);
}
