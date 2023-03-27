package com.coca.assemble.redisDelayer.model;

import lombok.Data;

@Data
public class ExecuteDelayedQueue {

    private String queueName;

    private String queueGroup;

}