package com.yozosoft.licenseserver.service.delayjob;

import com.yozosoft.licenseserver.common.util.IResult;

public interface DelayJobService {

    void sendRegisterDelayJob(Long activationId);

    IResult<Integer> removeRegisterDelayJob(Long activationId);

    void handlerRegisterDelayJob();
}
