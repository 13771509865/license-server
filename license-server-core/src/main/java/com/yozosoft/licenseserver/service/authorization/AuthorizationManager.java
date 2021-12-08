package com.yozosoft.licenseserver.service.authorization;

import com.github.pagehelper.PageInfo;
import com.yozosoft.licenseserver.common.util.IResult;
import com.yozosoft.licenseserver.dto.AuthorizationDTO;
import com.yozosoft.licenseserver.dto.AuthorizationQueryDTO;
import com.yozosoft.licenseserver.dto.EquipmentQueryDTO;
import com.yozosoft.licenseserver.dto.EquipmentResultDTO;
import com.yozosoft.licenseserver.model.dto.AuthorizationInfoDTO;
import com.yozosoft.licenseserver.model.dto.PageDTO;
import com.yozosoft.licenseserver.model.po.CdKeyPO;

public interface AuthorizationManager {

    IResult<Integer> addAuthorization(AuthorizationDTO authorizationDTO);

    IResult<PageInfo<AuthorizationInfoDTO>> getAuthorizationInfoByQuery(PageDTO pageDTO, AuthorizationQueryDTO authorizationQueryDTO);

    IResult<Integer> updateAuthorization(AuthorizationDTO authorizationDTO, CdKeyPO oldCdKeyPO);

    IResult<CdKeyPO> checkUpdateParam(AuthorizationDTO authorizationDTO);

    IResult<PageInfo<EquipmentResultDTO>> selectEquipmentDetail(EquipmentQueryDTO equipmentQueryDTO, PageDTO pageDTO);
}
