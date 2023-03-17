package com.coca.assemble.ratelimter.core;

@FunctionalInterface
/**
 * @project-name:callcenter
 * @package-name:com.hyts.callcenter.extense.limiter
 * @author:LiBo/Alex
 * @create-date:2021-12-05 5:38 下午
 * @copyright:libo-alex4java
 * @email:liboware@gmail.com
 * @description:
 */
public interface ExecuteRateLimiterFactory<P,R> {

    R create(P param);
}
