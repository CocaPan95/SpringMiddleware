package com.coca.assemble.redisDelayer.context;

import com.coca.assemble.redisDelayer.redis.DelayedRedissionClientTool;
import lombok.Getter;

public class DelayedRedisClientSupport {
    @Getter
    private static DelayedRedissionClientTool delayedRedissionClientTool;

    /**
     * 延迟队列控制redis服务机制
     * @param delayedRedissionClientTool
     */
    public DelayedRedisClientSupport(DelayedRedissionClientTool delayedRedissionClientTool) {
        DelayedRedisClientSupport.delayedRedissionClientTool = delayedRedissionClientTool;
    }

}
