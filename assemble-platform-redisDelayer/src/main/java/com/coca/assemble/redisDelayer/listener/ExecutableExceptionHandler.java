package com.coca.assemble.redisDelayer.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class ExecutableExceptionHandler implements Thread.UncaughtExceptionHandler{
    final List<DelayedExceptionHandler> exceptionHandlers;

    /**
     * 操作机制控制异常处理
     * @param t
     * @param e
     */
    @Override
    public void uncaughtException(Thread t, Throwable e){
        try {
            if(CollectionUtils.isNotEmpty(exceptionHandlers)) {
                exceptionHandlers.stream().forEach(param -> {
                    param.catchException(e, t);
                });
            }
        } catch (Exception e1) {
            log.error("Failed to execute exception capture mechanism!",e);
        }
    }
}
