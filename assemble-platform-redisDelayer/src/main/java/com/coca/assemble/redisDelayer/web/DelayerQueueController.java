package com.coca.assemble.redisDelayer.web;

import com.coca.assemble.common.model.http.ResultResponse;
import com.coca.assemble.redisDelayer.model.ExecuteInvokerEvent;
import com.coca.assemble.redisDelayer.redis.DelayedRedissionClientTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/delayed")
@RestController
@Api(value="延时队列服务",tags = {"延时队列服务组件"},description = "延时队列服务组件")
public class DelayerQueueController {

    @Autowired
    DelayedRedissionClientTool delayedRedissionClientTool;

    /**
     * 创建对应的延时队列对象服务
     * @param executeInvokerEvent
     * @return
     */
    @PostMapping("/publish")
    @ApiOperation(value="添加延时队列任务",notes = "添加延时队列任务")
    public ResultResponse publish(@RequestBody ExecuteInvokerEvent executeInvokerEvent){
        try {
            delayedRedissionClientTool.offer(executeInvokerEvent);
            return ResultResponse.success(executeInvokerEvent);
        }catch (Exception e){
            log.error("create delaye element is failure!",e);
            return ResultResponse.failure("create delaye element is failure!");
        }
    }
}
