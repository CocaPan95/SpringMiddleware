package com.coca.assemble.redisDelayer.listener;

@FunctionalInterface
public interface DelayedExceptionHandler {
    /**
     * 捕获异常信息
     * @param e
     * @param currentThread
     */
    void catchException(Throwable e,Thread currentThread);
}
