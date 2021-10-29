package com.yozosoft.licenseserver.service.activation;

import com.yozosoft.licenseserver.common.util.IResult;
import com.yozosoft.licenseserver.model.po.ActivationNumPO;
import com.yozosoft.licenseserver.model.po.CdKeyPO;
import com.yozosoft.licenseserver.model.qo.CdKeyQO;

import java.util.List;

/**
 * 激活码相关service
 *
 * @author zhouf
 */
public interface ActivationService {

    IResult<Integer> insertCdKey(CdKeyPO cdKeyPO);

    IResult<List<CdKeyPO>> selectCdKeyByQuery(CdKeyQO cdKeyQO);

    IResult<CdKeyPO> selectCdKeyById(Long id);

    IResult<CdKeyPO> selectCdKeyByCdk(String cdk);

    IResult<Integer> updateCdKey(CdKeyPO cdKeyPO);

    IResult<Integer> insertActivationNum(ActivationNumPO activationNumPO);

    IResult<Integer> updateActivationNum(ActivationNumPO activationNumPO);

    IResult<Integer> reduceActivationNum(Long cdkId);

    IResult<Integer> increaseActivationNum(Long cdkId);
}
