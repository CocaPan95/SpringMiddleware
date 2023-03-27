package com.coca.assemble.redisDelayer.sample;

import com.coca.assemble.redisDelayer.anno.DelayedQueueListener;
import com.coca.assemble.redisDelayer.listener.EventExecutableInvokerListener;
import com.coca.assemble.redisDelayer.model.ExecuteInvokerEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executor;

@Slf4j
@DelayedQueueListener(value="delayedQueueListener",group = "test_delayed_queue")
public class DefaultDelayedQueueListener implements EventExecutableInvokerListener<Object,Object> {
    @Override
    public Executor getExecutor() {
        return null;
    }

    @Override
    public Object handle(ExecuteInvokerEvent<Object> param) {
        log.info("input the parameter:{}",param);
        return param;
    }
}
