package com.yozosoft.licenseserver.service.authorization.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yozosoft.licenseserver.common.util.DefaultResult;
import com.yozosoft.licenseserver.common.util.IResult;
import com.yozosoft.licenseserver.constant.EnumActivationStatus;
import com.yozosoft.licenseserver.constant.EnumResultCode;
import com.yozosoft.licenseserver.dto.AuthorizationDTO;
import com.yozosoft.licenseserver.dto.AuthorizationQueryDTO;
import com.yozosoft.licenseserver.exception.LicenseException;
import com.yozosoft.licenseserver.model.dto.AuthorizationInfoDTO;
import com.yozosoft.licenseserver.model.dto.PageDTO;
import com.yozosoft.licenseserver.model.po.ActivationNumPO;
import com.yozosoft.licenseserver.model.po.AuthorizationPO;
import com.yozosoft.licenseserver.model.po.CdKeyPO;
import com.yozosoft.licenseserver.model.qo.AuthorizationInfoQO;
import com.yozosoft.licenseserver.service.activation.ActivationService;
import com.yozosoft.licenseserver.service.authorization.AuthorizationManager;
import com.yozosoft.licenseserver.service.authorization.AuthorizationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.core.strategy.keygen.SnowflakeShardingKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;

@Service
@Slf4j
public class AuthorizationManagerImpl implements AuthorizationManager {

    @Autowired
    AuthorizationService authorizationService;

    @Autowired
    ActivationService activationService;

    @Autowired
    SnowflakeShardingKeyGenerator snowflakeShardingKeyGenerator;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IResult<Integer> addAuthorization(AuthorizationDTO authorizationDTO) {
        //TODO mode与其他参数的匹配校验没做
        AuthorizationPO authorizationPO = buildAuthorizationPO(authorizationDTO);
        //插入授权相关信息
        IResult<Integer> insertResult = authorizationService.insertAuthorization(authorizationPO);
        if (insertResult.isSuccess()) {
            CdKeyPO cdKeyPO = buildUpdateCdKeyPO(authorizationDTO, true);
            //更新cdkey设置
            IResult<Integer> updateResult = activationService.updateCdKey(cdKeyPO);
            if (updateResult.isSuccess()) {
                //插入激活数量表
                ActivationNumPO activationNumPO = buildActivationNumPO(authorizationDTO, true);
                IResult<Integer> numResult = activationService.insertActivationNum(activationNumPO);
                if (numResult.isSuccess()) {
                    return DefaultResult.successResult();
                }
            }
        }
        throw new LicenseException(EnumResultCode.E_AUTHORIZATION_ADD_ERROR);
//        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//        return new DefaultResult<>(EnumResultCode.E_AUTHORIZATION_ADD_ERROR);
    }

    @Override
    public IResult<PageInfo<AuthorizationInfoDTO>> getAuthorizationInfoByQuery(PageDTO pageDTO, AuthorizationQueryDTO authorizationQueryDTO) {
        AuthorizationInfoQO authorizationInfoQO = buildAuthorizationInfoQO(authorizationQueryDTO);
        PageInfo<AuthorizationInfoDTO> authInfos = PageHelper.startPage(pageDTO.getPageNum(), pageDTO.getPageSize()).doSelectPageInfo(() -> authorizationService.selectAuthorizationInfo(authorizationInfoQO));
        return DefaultResult.successResult(authInfos);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IResult<Integer> updateAuthorization(AuthorizationDTO authorizationDTO) {
        AuthorizationPO authorizationPO = buildAuthorizationPO(authorizationDTO);
        IResult<Integer> updateResult = authorizationService.updateAuthorization(authorizationPO);
        if (updateResult.isSuccess()) {
            CdKeyPO cdKeyPO = buildUpdateCdKeyPO(authorizationDTO, false);
            IResult<Integer> cdKeyResult = activationService.updateCdKey(cdKeyPO);
            if (cdKeyResult.isSuccess()) {
                ActivationNumPO activationNumPO = buildActivationNumPO(authorizationDTO, false);
                IResult<Integer> numResult = activationService.updateActivationNum(activationNumPO);
                if (numResult.isSuccess()) {
                    return DefaultResult.successResult();
                }
            }
        }
        throw new LicenseException(EnumResultCode.E_AUTHORIZATION_UPDATE_ERROR);
//        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//        return new DefaultResult<>(EnumResultCode.E_AUTHORIZATION_UPDATE_ERROR);
    }

    @Override
    public IResult<Integer> checkUpdateParam(AuthorizationDTO authorizationDTO) {
        Long id = authorizationDTO.getId();
        if (id == null || id <= 0) {
            return new DefaultResult<>(EnumResultCode.E_INVALID_PARAM.getValue(), "id is null");
        }
        Boolean checkResult = checkPermitNum(authorizationDTO.getCdkeyId(), authorizationDTO.getPermitNum());
        if (!checkResult) {
            return new DefaultResult<>(EnumResultCode.E_INVALID_PARAM.getValue(), "purchase quantity is error");
        }
        return DefaultResult.successResult();
    }

    private Boolean checkPermitNum(Long cdKeyId, Integer permitNum) {
        IResult<CdKeyPO> getResult = activationService.selectCdKeyById(cdKeyId);
        if (getResult.isSuccess()) {
            CdKeyPO cdKeyPO = getResult.getData();
            Integer oldPermitNum = cdKeyPO.getPermitNum();
            if (permitNum >= oldPermitNum) {
                return true;
            }
        }
        return false;
    }

    private AuthorizationInfoQO buildAuthorizationInfoQO(AuthorizationQueryDTO authorizationQueryDTO) {
        AuthorizationInfoQO authorizationInfoQO = new AuthorizationInfoQO();
        authorizationInfoQO.setId(authorizationQueryDTO.getId());
        authorizationInfoQO.setCustomerName(authorizationQueryDTO.getCustomerName());
        authorizationInfoQO.setSalerName(authorizationQueryDTO.getSalerName());
        authorizationInfoQO.setCdkey(authorizationQueryDTO.getCdkey());
        return authorizationInfoQO;
    }

    private ActivationNumPO buildActivationNumPO(AuthorizationDTO authorizationDTO, Boolean isInit) {
        ActivationNumPO activationNumPO = new ActivationNumPO();
        activationNumPO.setCdkeyId(authorizationDTO.getCdkeyId());
        Date date = new Date();
        if (isInit) {
            activationNumPO.setCreateTime(date);
            activationNumPO.setStatus((byte) 1);
            activationNumPO.setSurplus(authorizationDTO.getPermitNum());
        }
        activationNumPO.setUpdateTime(date);
        activationNumPO.setPermitNum(authorizationDTO.getPermitNum());
        return activationNumPO;
    }

    private CdKeyPO buildUpdateCdKeyPO(AuthorizationDTO authorizationDTO, Boolean isInit) {
        CdKeyPO cdKeyPO = new CdKeyPO();
        cdKeyPO.setId(authorizationDTO.getCdkeyId());
        cdKeyPO.setUpdateTime(new Date());
        cdKeyPO.setPermitNum(authorizationDTO.getPermitNum());
        cdKeyPO.setHeartRateNum(authorizationDTO.getHeartRateNum());
        cdKeyPO.setHeartRateUnit(authorizationDTO.getHeartRateUnit());
        cdKeyPO.setExpireNum(authorizationDTO.getExpireNum());
        cdKeyPO.setExpireUnit(authorizationDTO.getExpireUnit());
        cdKeyPO.setExpireDate(authorizationDTO.getExpireDate());
        if (isInit) {
            cdKeyPO.setStatus(EnumActivationStatus.E_NOT_ACTIVE.getValue());
        }
        return cdKeyPO;
    }

    private AuthorizationPO buildAuthorizationPO(AuthorizationDTO authorizationDTO) {
        AuthorizationPO authorizationPO = new AuthorizationPO();
        Long id = authorizationDTO.getId();
        Date date = new Date();
        if (id != null && id > 0) {
            authorizationPO.setId(id);
        } else {
            Long snowId = Long.valueOf(snowflakeShardingKeyGenerator.generateKey().toString());
            authorizationPO.setId(snowId);
            authorizationPO.setCreateTime(date);
        }
        authorizationPO.setUpdateTime(date);
        authorizationPO.setStatus((byte) 1);
        authorizationPO.setCustomerName(authorizationDTO.getCustomerName());
        authorizationPO.setCustomerRegion(authorizationDTO.getCustomerRegion());
        authorizationPO.setCustomerDate(authorizationDTO.getCustomerDate());
        authorizationPO.setCustomerRemark(authorizationDTO.getCustomerRemark());
        authorizationPO.setSalerName(authorizationDTO.getSalerName());
        authorizationPO.setSalerMail(authorizationDTO.getSalerMail());
        authorizationPO.setSalerPhone(authorizationDTO.getSalerPhone());
        authorizationPO.setCdkeyId(authorizationDTO.getCdkeyId());
        return authorizationPO;
    }
}