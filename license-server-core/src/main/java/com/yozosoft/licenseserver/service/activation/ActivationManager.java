package com.yozosoft.licenseserver.service.activation;

import com.github.pagehelper.PageInfo;
import com.yozosoft.licenseserver.common.util.IResult;
import com.yozosoft.licenseserver.dto.ActivationDTO;
import com.yozosoft.licenseserver.dto.ActivationQueryDTO;
import com.yozosoft.licenseserver.model.dto.PageDTO;
import com.yozosoft.licenseserver.model.po.CdKeyPO;

public interface ActivationManager {

    IResult<Integer> addActivation(ActivationDTO activationDTO);

    IResult<PageInfo<CdKeyPO>> getActivationsByQuery(PageDTO pageDTO, ActivationQueryDTO activationQueryDTO);
}
