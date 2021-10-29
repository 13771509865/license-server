package com.yozosoft.licenseserver.service.delayjob.impl;

import com.yozosoft.licenseserver.common.util.DefaultResult;
import com.yozosoft.licenseserver.common.util.IResult;
import com.yozosoft.licenseserver.service.delayjob.DelayJobService;
import com.yozosoft.licenseserver.service.register.ClientRegisterManager;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class DelayJobServiceImpl implements DelayJobService {

    private static final String delayQueueName = "delayRegisterJob";

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    ClientRegisterManager clientRegisterManager;

    @Override
    public void sendRegisterDelayJob(Long activationId) {
        RBlockingQueue<Long> blockingQueue = redissonClient.getBlockingQueue(delayQueueName);
        RDelayedQueue<Long> delayedQueue = redissonClient.getDelayedQueue(blockingQueue);
        //最多保留30分钟
        delayedQueue.offer(activationId, 30, TimeUnit.MINUTES);
        //TODO 待确定
        delayedQueue.destroy();
    }

    @Override
    public IResult<Integer> removeRegisterDelayJob(Long activationId) {
        RBlockingQueue<Long> blockingQueue = redissonClient.getBlockingQueue(delayQueueName);
        RDelayedQueue<Long> delayedQueue = redissonClient.getDelayedQueue(blockingQueue);
        boolean remove = delayedQueue.remove(activationId);
        delayedQueue.destroy();
        return remove? DefaultResult.successResult():DefaultResult.failResult();
    }

    //TODO 还没做完
    @Override
    public void handlerRegisterDelayJob(){
        RBlockingQueue<Long> blockingQueue = redissonClient.getBlockingQueue(delayQueueName);
        new Thread(()->{
            while (true){
                try{
                    Long activationId = blockingQueue.take();
                    log.info("超时激活,activationId为:"+activationId);
                    clientRegisterManager.cancelRegister(activationId);
                }catch (Exception e){
                    log.error("延迟队列消费失败",e);
                }
            }
        }).start();
    }
}
