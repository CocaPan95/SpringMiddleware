package com.coca.assemble.redisDelayer.config;

import com.coca.assemble.redisDelayer.context.DelayedRedisClientSupport;
import com.coca.assemble.redisDelayer.context.DelayedThreadPoolExecutor;
import com.coca.assemble.redisDelayer.context.DelayedThreadPoolSupport;
import com.coca.assemble.redisDelayer.laucher.DelayedBootstrapInitializer;
import com.coca.assemble.redisDelayer.redis.DelayedRedissionClientTool;
import com.coca.assemble.redisDelayer.redis.RedissonClientTool;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@ComponentScan(basePackages = "com.coca.assemble.redisDelayer")
public class DelayedQueueConfiguration {
    /**
     * redission客户端的实现
     * @return
     */
    @Bean
    public DelayedRedissionClientTool delayedRedissionClientTool(){
        return new DelayedRedissionClientTool();
    }


    /**
     * 执行操作处理机制（考虑是IO密集型或者混合密集型机制） - 循环监控线程机制
     * @return
     */
    @Bean("delayedExecuteThreadPoolExecutor")
    public Executor delayedExecuteThreadPoolExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor =
                DelayedThreadPoolExecutor.initParameter("delayedExecuteThreadPoolExecutor");
        // 因为可以定制化线程数量机制，是否考虑延迟机制，待议 TODO
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }



    /**
     * 执行操作处理机制（考虑是IO密集型或者混合密集型机制） 异步 执行线程机制
     * @return
     */
    @Bean("delayedExecuteThreadPoolCycle")
    public Executor delayedExecuteThreadPoolCycle() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor =
                DelayedThreadPoolExecutor.initParameter("delayedExecuteThreadPoolCycle");
        threadPoolTaskExecutor.setQueueCapacity(0);
        // 系统暂时仅仅支持核心书个组，直接执行，不会存放队列数据信息
        threadPoolTaskExecutor.setMaxPoolSize(threadPoolTaskExecutor.getMaxPoolSize());
        threadPoolTaskExecutor.setCorePoolSize(threadPoolTaskExecutor.getCorePoolSize());
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }


    /**
     * 延迟线程池支持机制
     * @return
     */
    @Bean
    public DelayedThreadPoolSupport delayedThreadPoolSupport(@Autowired @Qualifier("delayedExecuteThreadPoolExecutor") Executor execute,
                                                             @Autowired @Qualifier("delayedExecuteThreadPoolCycle") Executor recycle){
        return new DelayedThreadPoolSupport(execute,recycle);
    }


    /**
     * 延迟队列机制支持Redis客户端
     * @return
     */
    @Bean
    public DelayedRedisClientSupport delayedRedisClientSupport(){
        return new DelayedRedisClientSupport(delayedRedissionClientTool());
    }


    /**
     * 线程池的构建和初始化
     * @return
     */
    @Bean(initMethod = "init")
    public DelayedBootstrapInitializer delayedThreadPoolExecutor(){
        return new DelayedBootstrapInitializer();
    }



    @Bean
    @ConditionalOnMissingBean(RedissonClientTool.class)
    public RedissonClientTool redissonClientTool(RedissonClient redissonClient) {
        return new RedissonClientTool(redissonClient);
    }
}
