package com.coca.assemble.redisDelayer.anno;

import com.coca.assemble.redisDelayer.config.DelayedQueueConfiguration;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD})
@SpringBootConfiguration
@Import(DelayedQueueConfiguration.class)
public @interface EnableDelayedQueue {
}
