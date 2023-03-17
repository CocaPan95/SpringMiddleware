package com.coca.assemble.ratelimter.guava.aop;

import com.coca.assemble.ratelimter.guava.GuavaExecuteRateLimiterFactory;
import com.coca.assemble.ratelimter.guava.GuavaRateLimiterParam;
import com.coca.assemble.ratelimter.guava.annotation.GuavaExecuteRateLimiter;
import com.google.common.base.Joiner;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Aspect
@Component
public class GuavaExecuteRateLimterAspect {

    @Pointcut("@annotation(com.coca.assemble.ratelimter.guava.annotation.GuavaExecuteRateLimiter)")
    public void methodPointCut() {}


    @Autowired
    GuavaExecuteRateLimiterFactory executeRateLimiterFactory;


    ConcurrentHashMap<String, RateLimiter> rateLimiterConcurrentHashMap = new ConcurrentHashMap<>();



    Joiner joiner = Joiner.on("-").skipNulls();

    @Around("methodPointCut()")
    public Object doMethod(ProceedingJoinPoint proceedingJoinPoint){
        MethodSignature methodSignature = (MethodSignature)proceedingJoinPoint.getSignature();
        Method method = methodSignature.getMethod();
        GuavaExecuteRateLimiter guavaExecuteRateLimiter = method.getAnnotation(GuavaExecuteRateLimiter.class);
        GuavaRateLimiterParam guavaRateLimiterParam = GuavaRateLimiterParam.builder().
                permitsPerSecond(guavaExecuteRateLimiter.permitsPerSecond()).
                timeUnit(guavaExecuteRateLimiter.timeUnit()).
                warmupPeriod(guavaExecuteRateLimiter.warmupPeriod()).build();
        String key = joiner.join(guavaExecuteRateLimiter.permitsPerSecond(),
                guavaExecuteRateLimiter.timeUnit().toString()
                ,guavaExecuteRateLimiter.warmupPeriod());
        RateLimiter rateLimiter = rateLimiterConcurrentHashMap.
                computeIfAbsent(key,param-> executeRateLimiterFactory.create(guavaRateLimiterParam));
        try {
            double rateValue = rateLimiter.acquire();
            log.info("执行限流方法操作处理:当前qps:{} delay rate limiter value:{}",guavaExecuteRateLimiter.permitsPerSecond(),rateValue);
            return proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        } catch (Throwable e) {
            log.error("执行限流控制方法失败！",e);
            return null;
        }
    }

}
