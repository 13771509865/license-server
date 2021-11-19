package com.yozosoft.licenseserver.service.register.impl;

import com.yozosoft.licenseserver.common.util.IResult;
import com.yozosoft.licenseserver.constant.EnumActivationMode;
import com.yozosoft.licenseserver.constant.EnumActivationStatus;
import com.yozosoft.licenseserver.constant.EnumResultCode;
import com.yozosoft.licenseserver.dto.ClientRegisterConfirmDTO;
import com.yozosoft.licenseserver.dto.ClientRegisterDTO;
import com.yozosoft.licenseserver.dto.ClientRegisterResultDTO;
import com.yozosoft.licenseserver.exception.LicenseException;
import com.yozosoft.licenseserver.model.po.CdKeyPO;
import com.yozosoft.licenseserver.model.po.ClientInfoPO;
import com.yozosoft.licenseserver.model.qo.ClientInfoQO;
import com.yozosoft.licenseserver.service.activation.ActivationService;
import com.yozosoft.licenseserver.service.delayjob.DelayJobService;
import com.yozosoft.licenseserver.service.register.ClientRegisterManager;
import com.yozosoft.licenseserver.service.register.ClientRegisterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.shardingsphere.core.strategy.keygen.SnowflakeShardingKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ClientRegisterManagerImpl implements ClientRegisterManager {

    @Autowired
    ClientRegisterService clientRegisterService;

    @Autowired
    ActivationService activationService;

    @Autowired
    SnowflakeShardingKeyGenerator snowflakeShardingKeyGenerator;

    @Autowired
    DelayJobService delayJobService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ClientRegisterResultDTO clientRegister(ClientRegisterDTO clientRegisterDTO) {
        /**
         * 0、检查cdkey status 适用产品等是否可用
         * 1、校验是否是重装激活
         * 2、生成activationId
         * 4、cdkey剩余次数-1
         * 5、不可用直接返回对应异常
         * 5、根据mode计算到期时间等信息
         * 6、将临时数据插入redis延迟队列中
         */
        CdKeyPO cdKeyPO = checkCdKey(clientRegisterDTO);
        ClientRegisterResultDTO clientRegisterResultDTO = checkReRegister(clientRegisterDTO, cdKeyPO);
        if (clientRegisterResultDTO != null) {
            return clientRegisterResultDTO;
        }
        IResult<Integer> reduceResult = activationService.reduceActivationNum(cdKeyPO.getId());
        if (!reduceResult.isSuccess()) {
            throw new LicenseException(EnumResultCode.E_PERMIT_NUM_LACK);
        }
        Date expireDate = calculationExpireDate(cdKeyPO);
        if (expireDate == null) {
            log.error("计算过期时间失败");
            throw new LicenseException(EnumResultCode.E_ACTIVATION_ERROR);
        }
        Long activationId = Long.valueOf(snowflakeShardingKeyGenerator.generateKey().toString());
        ClientInfoPO clientInfoPO = buildClientInfoPO(activationId, clientRegisterDTO, expireDate, cdKeyPO.getId());
        IResult<Integer> insertResult = clientRegisterService.insertClientInfo(clientInfoPO);
        if (!insertResult.isSuccess()) {
            log.error("记录clientInfo失败,失败cdkey为:" + clientRegisterDTO.getCdkey());
            throw new LicenseException(EnumResultCode.E_ACTIVATION_ERROR);
        }
        clientRegisterResultDTO = buildClientRegisterResultDTO(cdKeyPO, expireDate, activationId);
        delayJobService.sendRegisterDelayJob(activationId);
        return clientRegisterResultDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void clientRegisterConfirm(ClientRegisterConfirmDTO clientRegisterConfirmDTO) {
        Long activationId = clientRegisterConfirmDTO.getActivationId();
        Byte activationResult = clientRegisterConfirmDTO.getActivationResult();
        IResult<Integer> removeResult = delayJobService.removeRegisterDelayJob(activationId);
        if (activationResult.equals((byte) 0)) {
            //客户端激活失败
            cancelRegister(activationId);
        } else {
            ClientInfoPO clientInfoPO = new ClientInfoPO();
            clientInfoPO.setId(activationId);
            clientInfoPO.setStatus((byte) 1);
            IResult<Integer> updateResult = clientRegisterService.updateClientInfo(clientInfoPO);
            if (!updateResult.isSuccess()) {
                log.error("confirm确认成功时,更新clientInfo失败");
                throw new LicenseException(EnumResultCode.E_ACTIVATION_CONFIRM_ERROR);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelRegister(Long activationId) {
        IResult<ClientInfoPO> getResult = clientRegisterService.selectClientInfoById(activationId);
        if (!getResult.isSuccess()) {
            log.error("confirm确认失败时,查询clientInfo失败");
            throw new LicenseException(EnumResultCode.E_ACTIVATION_CONFIRM_ERROR);
        }
        ClientInfoPO clientInfoPO = getResult.getData();
        if (!clientInfoPO.getStatus().equals((byte) 1)) {
            IResult<Integer> increaseResult = activationService.increaseActivationNum(clientInfoPO.getCdkeyId());
            if (!increaseResult.isSuccess()) {
                log.error("confirm确认失败时,增长激活次数失败");
                throw new LicenseException(EnumResultCode.E_ACTIVATION_CONFIRM_ERROR);
            }
            IResult<Integer> deleteResult = clientRegisterService.deleteClientInfoById(activationId);
            if (!deleteResult.isSuccess()) {
                log.error("confirm确认失败时,删除clientInfo失败");
                throw new LicenseException(EnumResultCode.E_ACTIVATION_CONFIRM_ERROR);
            }
        }
    }

    private ClientInfoPO buildClientInfoPO(Long activationId, ClientRegisterDTO clientRegisterDTO, Date expireDate, Long cdkId) {
        ClientInfoPO clientInfoPO = new ClientInfoPO();
        clientInfoPO.setId(activationId);
        Date date = new Date();
        clientInfoPO.setCreateTime(date);
        clientInfoPO.setUpdateTime(date);
        clientInfoPO.setStatus(EnumActivationStatus.E_ACTIVATING.getValue());
        clientInfoPO.setCdkeyId(cdkId);
        clientInfoPO.setBiosId(clientRegisterDTO.getBiosId());
        clientInfoPO.setCpuId(clientRegisterDTO.getCpuId());
        clientInfoPO.setMac(clientRegisterDTO.getMac());
        //TODO ip转换还没做
//        clientInfoPO.setIp(clientInfoPO.getIp());
        clientInfoPO.setExpireDate(expireDate);
        return clientInfoPO;
    }

    private Date calculationExpireDate(CdKeyPO cdKeyPO) {
        Date expireDate = null;
        Byte mode = cdKeyPO.getMode();
        if (mode.equals(EnumActivationMode.E_RELATIVE_MODE.getValue())) {
            Date now = new Date();
            Integer expireNum = cdKeyPO.getExpireNum();
            switch (cdKeyPO.getExpireUnit()) {
                case (byte) 0:
                    expireDate = DateUtils.addDays(now, expireNum);
                    break;
                case (byte) 1:
                    expireDate = DateUtils.addWeeks(now, expireNum);
                    break;
                case (byte) 2:
                    expireDate = DateUtils.addMonths(now, expireNum);
                    break;
                case (byte) 3:
                    expireDate = DateUtils.addYears(now, expireNum);
                    break;
            }
        } else if (mode.equals(EnumActivationMode.E_ABSOLUTELY_MODE.getValue())) {
            expireDate = cdKeyPO.getExpireDate();
        }
        return expireDate;
    }

    private CdKeyPO checkCdKey(ClientRegisterDTO clientRegisterDTO) {
        IResult<CdKeyPO> getResult = activationService.selectCdKeyByCdk(clientRegisterDTO.getCdkey());
        if (!getResult.isSuccess()) {
            throw new LicenseException(EnumResultCode.E_ACTIVATION_NOT_EXIST);
        }
        CdKeyPO cdKeyPO = getResult.getData();
        if (!cdKeyPO.getProduct().equals(clientRegisterDTO.getProduct())) {
            throw new LicenseException(EnumResultCode.E_ACTIVATION_PRODUCT_MISMATCH);
        }
        if (cdKeyPO.getStatus() < 2) {
            throw new LicenseException(EnumResultCode.E_ACTIVATION_STATUS_ILLEGAL);
        }
        if (cdKeyPO.getExpireDate() != null && DateUtils.truncatedCompareTo(cdKeyPO.getExpireDate(), new Date(), Calendar.MILLISECOND) <= 0) {
            throw new LicenseException(EnumResultCode.E_ACTIVATION_EXPIRED);
        }
        if (cdKeyPO.getStatus().equals(EnumActivationStatus.E_NOT_ACTIVE.getValue())) {
            cdKeyPO.setStatus(EnumActivationStatus.E_ACTIVE.getValue());
            IResult<Integer> updateResult = activationService.updateCdKey(cdKeyPO);
            if (!updateResult.isSuccess()) {
                log.error("激活时更新cdkey status状态失败");
                throw new LicenseException(EnumResultCode.E_ACTIVATION_ERROR);
            }
        }
        return cdKeyPO;
    }

    private ClientRegisterResultDTO checkReRegister(ClientRegisterDTO clientRegisterDTO, CdKeyPO cdKeyPO) {
        ClientInfoQO clientInfoQO = new ClientInfoQO();
        clientInfoQO.setBiosId(clientRegisterDTO.getBiosId());
        clientInfoQO.setCpuId(clientRegisterDTO.getCpuId());
        clientInfoQO.setCdkeyId(cdKeyPO.getId());
        IResult<List<ClientInfoPO>> getResult = clientRegisterService.selectClientInfoByQuery(clientInfoQO);
        List<ClientInfoPO> clientInfoPOs = getResult.getData();
        if (getResult.isSuccess() && clientInfoPOs != null && clientInfoPOs.size() == 1) {
            //查询到已激活过
            ClientInfoPO clientInfoPO = clientInfoPOs.get(0);
            if (DateUtils.truncatedCompareTo(clientInfoPO.getExpireDate(), new Date(), Calendar.MILLISECOND) > 0) {
                if (clientInfoPO.getStatus().equals((byte) 1)) {
                    clientInfoPO.setStatus(EnumActivationStatus.E_REACTIVE.getValue());
                    IResult<Integer> updateResult = clientRegisterService.updateClientInfo(clientInfoPO);
                    if (!updateResult.isSuccess()) {
                        log.error("重装激活时,更新clientInfo表status状态失败");
                    }
                    return buildClientRegisterResultDTO(clientInfoPO, cdKeyPO);
                } else if (clientInfoPO.getStatus().equals(EnumActivationStatus.E_REACTIVE.getValue())) {
                    return buildClientRegisterResultDTO(clientInfoPO, cdKeyPO);
                }
            }
            throw new LicenseException(EnumResultCode.E_REPEAT_ACTIVATION_ERROR);
        }
        return null;
    }

    private ClientRegisterResultDTO buildClientRegisterResultDTO(ClientInfoPO clientInfoPO, CdKeyPO cdKeyPO) {
        ClientRegisterResultDTO clientRegisterResultDTO = new ClientRegisterResultDTO();
        clientRegisterResultDTO.setActivationId(clientInfoPO.getId());
        clientRegisterResultDTO.setHeartRateNum(cdKeyPO.getHeartRateNum());
        clientRegisterResultDTO.setHeartRateUnit(cdKeyPO.getHeartRateUnit());
        clientRegisterResultDTO.setExpireDate(clientInfoPO.getExpireDate());
        clientRegisterResultDTO.setCdkType(cdKeyPO.getCdkType());
        clientRegisterResultDTO.setProduct(cdKeyPO.getProduct());
        clientRegisterResultDTO.setVersion(cdKeyPO.getVersion());
        return clientRegisterResultDTO;
    }

    private ClientRegisterResultDTO buildClientRegisterResultDTO(CdKeyPO cdKeyPO, Date expireDate, Long activationId) {
        ClientRegisterResultDTO clientRegisterResultDTO = new ClientRegisterResultDTO();
        clientRegisterResultDTO.setActivationId(activationId);
        clientRegisterResultDTO.setHeartRateNum(cdKeyPO.getHeartRateNum());
        clientRegisterResultDTO.setHeartRateUnit(cdKeyPO.getHeartRateUnit());
        clientRegisterResultDTO.setExpireDate(expireDate);
        clientRegisterResultDTO.setCdkType(cdKeyPO.getCdkType());
        clientRegisterResultDTO.setProduct(cdKeyPO.getProduct());
        clientRegisterResultDTO.setVersion(cdKeyPO.getVersion());
        return clientRegisterResultDTO;
    }
}
