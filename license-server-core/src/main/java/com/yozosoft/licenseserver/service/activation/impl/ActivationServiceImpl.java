package com.yozosoft.licenseserver.service.activation.impl;

import com.yozosoft.licenseserver.common.util.DefaultResult;
import com.yozosoft.licenseserver.common.util.IResult;
import com.yozosoft.licenseserver.dao.ActivationNumPOMapper;
import com.yozosoft.licenseserver.dao.CdKeyPOMapper;
import com.yozosoft.licenseserver.model.po.ActivationNumPO;
import com.yozosoft.licenseserver.model.po.CdKeyPO;
import com.yozosoft.licenseserver.model.qo.CdKeyQO;
import com.yozosoft.licenseserver.service.activation.ActivationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivationServiceImpl implements ActivationService {

    @Autowired
    CdKeyPOMapper cdKeyPOMapper;

    @Autowired
    ActivationNumPOMapper activationNumPOMapper;

    @Override
    public IResult<Integer> insertCdKey(CdKeyPO cdKeyPO) {
        int i = cdKeyPOMapper.insertSelective(cdKeyPO);
        return i > 0 ? DefaultResult.successResult() : DefaultResult.failResult();
    }

    @Override
    public IResult<List<CdKeyPO>> selectCdKeyByQuery(CdKeyQO cdKeyQO) {
        List<CdKeyPO> cdKeyPOs = cdKeyPOMapper.selectByQuery(cdKeyQO);
        return DefaultResult.successResult(cdKeyPOs);
    }

    @Override
    public IResult<CdKeyPO> selectCdKeyById(Long id) {
        CdKeyPO cdKeyPO = cdKeyPOMapper.selectByPrimaryKey(id);
        return cdKeyPO != null ? DefaultResult.successResult(cdKeyPO) : DefaultResult.failResult();
    }

    @Override
    public IResult<CdKeyPO> selectCdKeyByCdk(String cdk) {
        CdKeyPO cdKeyPO = cdKeyPOMapper.selectByCdk(cdk);
        return cdKeyPO != null ? DefaultResult.successResult(cdKeyPO) : DefaultResult.failResult();
    }

    @Override
    public IResult<Integer> updateCdKey(CdKeyPO cdKeyPO) {
        int i = cdKeyPOMapper.updateByPrimaryKeySelective(cdKeyPO);
        return i > 0 ? DefaultResult.successResult() : DefaultResult.failResult();
    }

    @Override
    public IResult<Integer> insertActivationNum(ActivationNumPO activationNumPO) {
        int i = activationNumPOMapper.insertSelective(activationNumPO);
        return i > 0 ? DefaultResult.successResult() : DefaultResult.failResult();
    }

    @Override
    public IResult<Integer> updateActivationNum(ActivationNumPO activationNumPO) {
        int i = activationNumPOMapper.updateByPrimaryKeySelective(activationNumPO);
        return i > 0 ? DefaultResult.successResult() : DefaultResult.failResult();
    }

    @Override
    public IResult<Integer> reduceActivationNum(Long cdkId) {
        int i = activationNumPOMapper.reduceNum(cdkId);
        return i > 0 ? DefaultResult.successResult() : DefaultResult.failResult();
    }

    @Override
    public IResult<Integer> increaseActivationNum(Long cdkId) {
        int i = activationNumPOMapper.increaseNum(cdkId);
        return i > 0 ? DefaultResult.successResult() : DefaultResult.failResult();
    }
}
