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

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class DelayJobServiceImpl implements DelayJobService {

    private static final String delayQueueName = "delayRegisterJob";

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    ClientRegisterManager clientRegisterManager;

    @PostConstruct
    public void initHandler(){
        handlerRegisterDelayJob();
    }

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

    //TODO 目前这个模式消费异常了会丢消息，后期可改为延迟的消费队列，保证数据不丢失
    @Override
    public void handlerRegisterDelayJob(){
        RBlockingQueue<Long> blockingQueue = redissonClient.getBlockingQueue(delayQueueName);
        new Thread(()->{
            while (true){
                Long activationId = null;
                try{
                    activationId = blockingQueue.take();
                    log.info("超时激活,activationId为:"+activationId);
                    clientRegisterManager.cancelRegister(activationId, null);
                }catch (Exception e){
                    log.error("延迟队列消费失败,activationId为:"+activationId,e);
                }
            }
        }).start();
    }
}
