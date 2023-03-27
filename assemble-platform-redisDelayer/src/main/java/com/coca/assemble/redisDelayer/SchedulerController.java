package com.coca.assemble.redisDelayer;

import org.redisson.RedissonExecutorService;
import org.redisson.api.CronSchedule;
import org.redisson.api.RScheduledExecutorService;
import org.redisson.api.RScheduledFuture;
import org.redisson.api.RedissonClient;
import org.redisson.executor.RedissonExecutorRemoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;

@Component
public class SchedulerController {

    RedissonExecutorRemoteService executorRemoteService;


    RedissonExecutorService redissonExecutorService;


    @Autowired
    RedissonClient redissonClient;

    public void publicSchedulerTask(){
        RScheduledExecutorService scheduledExecutorService=
                redissonClient.getExecutorService("taskScheduler");
        RScheduledFuture rScheduledFuture = scheduledExecutorService.scheduleAsync(new Task(), CronSchedule.of("* * * * * ?"));
        try {
            System.out.println(rScheduledFuture.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static class Task implements Runnable, Serializable {
        @Override
        public void run() {
            System.out.println("execute task :{}"+System.currentTimeMillis());
        }
    };
}
