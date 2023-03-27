package com.coca.assemble.redisDelayer.context;

import com.coca.assemble.redisDelayer.listener.EventExecutableInvokerListener;
import com.coca.assemble.redisDelayer.listener.ExecutableExceptionHandler;
import com.coca.assemble.redisDelayer.model.ExecuteInvokerEvent;
import com.coca.assemble.redisDelayer.redis.DelayedRedissionClientTool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

@RequiredArgsConstructor
@Slf4j
public class DelayedBootstrapRunnable implements Runnable{

    /**
     * 直接传递相关的执行客户端访问器
     */
    public DelayedRedissionClientTool delayedRedissionClientTool = DelayedRedisClientSupport.getDelayedRedissionClientTool();


    /**
     * 绑定的线程组，只会执行相关的线程组之间的关系机制
     */
    public final String bizGroup;

    /**
     * 注入参数进入
     */
    public final List<EventExecutableInvokerListener> eventExecutableInvokerListeners;

    /**
     * 执行线程池
     */
    public final Executor executorThreadPool;


    /**
     * 异常信息控制
     */
    public final ExecutableExceptionHandler exceptionHandlers;


    /**
     * 启动服务处理机制
     */
    @Override
    public void run() {
        try {
            RBlockingQueue<ExecuteInvokerEvent> blockingQueue = delayedRedissionClientTool.takeBlockingQueue(new ExecuteInvokerEvent(bizGroup));
            Executor executor = Objects.isNull(executorThreadPool) ? DelayedThreadPoolSupport.getTaskExecuteThread() : executorThreadPool;
            Thread.currentThread().setUncaughtExceptionHandler(exceptionHandlers);
            for(;;) {
                try{
                    ExecuteInvokerEvent data =  delayedRedissionClientTool.poll(blockingQueue);
                    log.info("侦听队列任务组：{},获得值:{}", bizGroup, data);
                    log.info(MessageFormat.format("【1】Execute parse complete call: the execution time should be：{0,date,yyyy-MM-dd HH:mm:ss}，" +
                                    "Actual execution time：{1,date,yyyy-MM-dd HH:mm:ss},createTime：{2,date,yyyy-MM-dd HH:mm:ss}",
                            data.getFiredTime(), new Date(),new Date(data.getCreateTime())));
                    executor.execute(() -> {
                        for(EventExecutableInvokerListener eventExecutableInvokerListener : eventExecutableInvokerListeners){
                            eventExecutableInvokerListener.handle(data);
                        }
                    });
                }catch (Exception e){
                    log.error("无法执行处理",e);
                }
            }
        } catch (Exception e) {
            log.error("无法执行处理",e);
//            throw new RuntimeException(e);
        }
    }

}
