package com.coca.assemble.redisDelayer.laucher;

import com.coca.assemble.redisDelayer.anno.DelayedQueueExceptionHandler;
import com.coca.assemble.redisDelayer.anno.DelayedQueueListener;
import com.coca.assemble.redisDelayer.context.DelayedBootstrapRunnable;
import com.coca.assemble.redisDelayer.context.DelayedThreadPoolSupport;
import com.coca.assemble.redisDelayer.listener.DelayedExceptionHandler;
import com.coca.assemble.redisDelayer.listener.EventExecutableInvokerListener;
import com.coca.assemble.redisDelayer.listener.ExecutableExceptionHandler;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.util.Strings;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

@Slf4j
public class DelayedBootstrapInitializer {
    @Setter
    @Getter
    @DelayedQueueListener(value="delayedListenerContextMap")
    Map<String, EventExecutableInvokerListener> delayedListenerContextMap = Maps.newHashMap();



    @Setter
    @Getter
    @DelayedQueueExceptionHandler(value="delayedExceptionHandlerMap")
    Map<String, DelayedExceptionHandler> delayedExceptionHandlerMap = Maps.newHashMap();

    /**
     * 初始化操作机制控制
     */
    public void init(){
        log.info("启动初始化加载并完成所有相关延迟启动初始化加载并完成所有相关延迟" +
                "系统中用于侦听上下文接口服务数据的队列 : {}",delayedListenerContextMap);
        log.info("开始完成线程任务分配，并为每个组的侦听器和任务队列分配资源");
        if(MapUtils.isEmpty(delayedListenerContextMap)){
            log.info("未找到任务侦听信息。在springcontext管理的上下文中，" +
                    "请检查是否有关于实现的接口" +
                    "EventExecutableInvokerListener，以及相关@DelayedQueueListener");
            return ;
        }
        log.info("启动与生产相关的侦听绑定机制");
        Map<String, List<EventExecutableInvokerListener>> getAnnotationMetadataGroup =
                delayedListenerContextMap.values().stream().collect(Collectors.groupingBy(DelayedBootstrapInitializer::getAnnotationMetadataGroupListener));

        log.info("开始初始化相关的异常信息处理机制");
        Map<String,List<DelayedExceptionHandler>> delayedExceptionHandlerMapGroup =
                delayedExceptionHandlerMap.values().stream().collect(Collectors.groupingBy(DelayedBootstrapInitializer::getAnnotationMetadataGroupExceptionHandler));


        if(MapUtils.isNotEmpty(getAnnotationMetadataGroup)){
            Executor executor = DelayedThreadPoolSupport.getTaskRecycleThread();
            log.info("启动资源分配机制");
            //推荐同一个组里面采用一个线程池进行处理机制
            getAnnotationMetadataGroup.entrySet().forEach(param->{
                log.info("初始化线程机制 {}：",param.getValue());
                executor.execute(new DelayedBootstrapRunnable(param.getKey(),param.getValue(),
                        DelayedBootstrapInitializer.getExecutorByGroup(param.getValue()),
                        new ExecutableExceptionHandler(delayedExceptionHandlerMapGroup.get(param.getKey()))));
            });
        }else{
            log.warn("资源转换失败！无法执行资源执行机制");
        }
    }
    public static String getAnnotationMetadataGroupListener(EventExecutableInvokerListener eventExecutableInvokerListener){
        return getAnnotationMetadataGroup(eventExecutableInvokerListener,DelayedQueueListener.class);
    }


    public static String getAnnotationMetadataGroupExceptionHandler( DelayedExceptionHandler delayedExceptionHandler){
        return getAnnotationMetadataGroup(delayedExceptionHandler,DelayedQueueExceptionHandler.class);
    }
    /**
     * 获取相关的组信息
     * @param object
     * @return
     */
    public static String getAnnotationMetadataGroup(Object object,Class delayedQueueListenerClass){
        Object annotationInstance = object.getClass().getAnnotation(delayedQueueListenerClass);
        if(annotationInstance instanceof DelayedQueueListener) {
            DelayedQueueListener delayedQueueListener = (DelayedQueueListener)annotationInstance;
            if(Objects.isNull(annotationInstance)){
                return Strings.EMPTY;
            }else{
                return delayedQueueListener.group();
            }
        }
        else if(annotationInstance instanceof DelayedQueueExceptionHandler) {
            DelayedQueueExceptionHandler delayedExceptionHandler = (DelayedQueueExceptionHandler)annotationInstance;
            if(Objects.isNull(annotationInstance)){
                return Strings.EMPTY;
            }else{
                return delayedExceptionHandler.group();
            }
        }
        return Strings.EMPTY;
    }


    /**
     * 执行线程组机制
     * @return
     */
    public static Executor getExecutorByGroup(List<EventExecutableInvokerListener> eventExecutableInvokerListeners){
        return eventExecutableInvokerListeners.stream().map(EventExecutableInvokerListener::getExecutor).
                filter(Objects::nonNull).findAny().orElse(null);
    }
}
