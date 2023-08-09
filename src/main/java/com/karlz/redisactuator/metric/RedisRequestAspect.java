package com.karlz.redisactuator.metric;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RedisRequestAspect {
    @Autowired
    private MeterRegistry meterRegistry;

    @Around("execution(* com.karlz.redisactuator.service.*.*(..))")
    public Object monitorRedis(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Timer timer = Timer.builder("redis.requests")
                .description("Redis requests")
                .tag("type", "redis")
                .tag("method", methodName)
                .register(meterRegistry);

        Timer.Sample sample = Timer.start();

        try {
            return joinPoint.proceed();
        } finally {
            sample.stop(timer);
        }
    }
}