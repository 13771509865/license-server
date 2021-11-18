package com.yozosoft.licenseserver.service.activation.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yozosoft.licenseserver.common.util.DefaultResult;
import com.yozosoft.licenseserver.common.util.IResult;
import com.yozosoft.licenseserver.constant.EnumResultCode;
import com.yozosoft.licenseserver.constant.EnumTimeUnit;
import com.yozosoft.licenseserver.dto.ActivationDTO;
import com.yozosoft.licenseserver.dto.ActivationQueryDTO;
import com.yozosoft.licenseserver.model.dto.PageDTO;
import com.yozosoft.licenseserver.model.po.CdKeyPO;
import com.yozosoft.licenseserver.model.qo.CdKeyQO;
import com.yozosoft.licenseserver.service.activation.ActivationManager;
import com.yozosoft.licenseserver.service.activation.ActivationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.core.strategy.keygen.SnowflakeShardingKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class ActivationManagerImpl implements ActivationManager {

    @Autowired
    ActivationService activationService;

    @Autowired
    SnowflakeShardingKeyGenerator snowflakeShardingKeyGenerator;

    @Override
    public IResult<Integer> addActivation(ActivationDTO activationDTO) {
        CdKeyPO cdKeyPO = buildCdKeyPO(activationDTO);
        try {
            IResult<Integer> insertResult = activationService.insertCdKey(cdKeyPO);
            if(insertResult.isSuccess()){
                return insertResult;
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("录入激活码插入数据库失败", e);
        }
        return new DefaultResult<>(EnumResultCode.E_ACTIVATION_ADD_ERROR);
    }

    @Override
    public IResult<PageInfo<CdKeyPO>> getActivationsByQuery(PageDTO pageDTO, ActivationQueryDTO activationQueryDTO) {
        CdKeyQO cdKeyQO = buildCdKeyQO(activationQueryDTO);
        PageInfo<CdKeyPO> cdKeyPOs = PageHelper.startPage(pageDTO.getPageNum(), pageDTO.getPageSize()).doSelectPageInfo(() -> activationService.selectCdKeyByQuery(cdKeyQO));
        return DefaultResult.successResult(cdKeyPOs);
    }

    @Override
    public IResult<Integer> deleteActivation(Long id) {
        IResult<Integer> deleteResult = activationService.deleteCdKey(id);
        return deleteResult;
    }

    private CdKeyQO buildCdKeyQO(ActivationQueryDTO activationQueryDTO){
        CdKeyQO cdKeyQO = new CdKeyQO();
        cdKeyQO.setCdkey(activationQueryDTO.getCdkey());
        cdKeyQO.setProduct(activationQueryDTO.getProduct());
        cdKeyQO.setVersion(activationQueryDTO.getVersion());
        cdKeyQO.setCdkType(activationQueryDTO.getCdkType());
        cdKeyQO.setCdkCreateTimeStart(activationQueryDTO.getCdkCreateTimeStart());
        cdKeyQO.setCdkCreateTimeEnd(activationQueryDTO.getCdkCreateTimeEnd());
        cdKeyQO.setStatus(activationQueryDTO.getStatus());
        return cdKeyQO;
    }

    private CdKeyPO buildCdKeyPO(ActivationDTO activationDTO) {
        CdKeyPO cdKeyPO = new CdKeyPO();
        Long snowId = Long.valueOf(snowflakeShardingKeyGenerator.generateKey().toString());
        cdKeyPO.setId(snowId);
        Date date = new Date();
        cdKeyPO.setCreateTime(date);
        cdKeyPO.setUpdateTime(date);
        cdKeyPO.setStatus((byte) 1);
        cdKeyPO.setCdkey(activationDTO.getCdkey());
        cdKeyPO.setCdkCreateTime(activationDTO.getCdkCreateTime());
        cdKeyPO.setMachine(activationDTO.getMachine());
        cdKeyPO.setProducer(activationDTO.getProducer());
        cdKeyPO.setProduct(activationDTO.getProduct());
        cdKeyPO.setVersion(activationDTO.getVersion());
        cdKeyPO.setRegion(activationDTO.getRegion());
        cdKeyPO.setCdkType(activationDTO.getCdkType());
        cdKeyPO.setMode(activationDTO.getMode());
        cdKeyPO.setPermitNum(0);
        cdKeyPO.setHeartRateNum(0);
        cdKeyPO.setHeartRateUnit(EnumTimeUnit.E_DAY_UNIT.getValue());
        return cdKeyPO;
    }
}
