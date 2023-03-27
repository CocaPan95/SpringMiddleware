package com.coca.assemble.redisDelayer.listener;

@FunctionalInterface
public interface ExecutableInvokerListener<P,R>  {

    /**
     * 执行方法
     * @param param 返回值为以后callable使用
     * @return
     */
    R handle(P param);

}

