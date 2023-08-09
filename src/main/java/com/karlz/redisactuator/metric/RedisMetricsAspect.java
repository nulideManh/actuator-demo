package com.karlz.redisactuator.metric;

import com.karlz.redisactuator.config.RedisMetricsConfig;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Aspect
public class RedisMetricsAspect {

    private final Counter redisCallCounter;
    private final Counter redisCommandCounter;


    @Autowired
    public RedisMetricsAspect(MeterRegistry meterRegistry) {
        redisCallCounter = meterRegistry.counter("redis.calls");
        redisCommandCounter = meterRegistry.counter("redis.commands");
    }

    @Around("execution(* com.karlz.redisactuator.service.*.*(..))")
    public Object countRedisMetrics(ProceedingJoinPoint joinPoint) throws Throwable {
        redisCallCounter.increment();

        Object result = null;
        try {
            result = joinPoint.proceed();
            redisCommandCounter.increment();
        } catch (Throwable throwable) {
            throw throwable;
        }
        return result;
    }
}
