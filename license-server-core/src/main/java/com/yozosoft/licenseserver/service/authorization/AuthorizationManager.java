package com.yozosoft.licenseserver.service.authorization;

import com.github.pagehelper.PageInfo;
import com.yozosoft.licenseserver.common.util.IResult;
import com.yozosoft.licenseserver.dto.AuthorizationDTO;
import com.yozosoft.licenseserver.dto.AuthorizationQueryDTO;
import com.yozosoft.licenseserver.model.dto.AuthorizationInfoDTO;
import com.yozosoft.licenseserver.model.dto.PageDTO;
import com.yozosoft.licenseserver.model.po.CdKeyPO;
import com.yozosoft.licenseserver.model.po.ClientInfoPO;

import java.util.List;

public interface AuthorizationManager {

    IResult<Integer> addAuthorization(AuthorizationDTO authorizationDTO);

    IResult<PageInfo<AuthorizationInfoDTO>> getAuthorizationInfoByQuery(PageDTO pageDTO, AuthorizationQueryDTO authorizationQueryDTO);

    IResult<Integer> updateAuthorization(AuthorizationDTO authorizationDTO, CdKeyPO oldCdKeyPO);

    IResult<CdKeyPO> checkUpdateParam(AuthorizationDTO authorizationDTO);

    IResult<List<ClientInfoPO>> selectEquipmentDetail(Long cdkeyId);
}
