package com.coca.assemble.ratelimter.guava;

import com.coca.assemble.ratelimter.core.ExecuteRateLimiterFactory;
import com.google.common.util.concurrent.RateLimiter;
import org.springframework.stereotype.Component;

@Component
public class GuavaExecuteRateLimiterFactory implements ExecuteRateLimiterFactory<GuavaRateLimiterParam, RateLimiter> {

    /**
     * 创建RateLimiter对象
     * @param param
     * @return
     */
    @Override
    public RateLimiter create(GuavaRateLimiterParam param) {
        return RateLimiter.create(param.getPermitsPerSecond(),param.getWarmupPeriod(),param.getTimeUnit());
    }
}
