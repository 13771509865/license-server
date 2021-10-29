package com.yozosoft.licenseserver.service.authorization;

import com.github.pagehelper.PageInfo;
import com.yozosoft.licenseserver.common.util.IResult;
import com.yozosoft.licenseserver.dto.AuthorizationDTO;
import com.yozosoft.licenseserver.dto.AuthorizationQueryDTO;
import com.yozosoft.licenseserver.model.dto.AuthorizationInfoDTO;
import com.yozosoft.licenseserver.model.dto.PageDTO;

public interface AuthorizationManager {

    IResult<Integer> addAuthorization(AuthorizationDTO authorizationDTO);

    IResult<PageInfo<AuthorizationInfoDTO>> getAuthorizationInfoByQuery(PageDTO pageDTO, AuthorizationQueryDTO authorizationQueryDTO);

    IResult<Integer> updateAuthorization(AuthorizationDTO authorizationDTO);

    IResult<Integer> checkUpdateParam(AuthorizationDTO authorizationDTO);
}