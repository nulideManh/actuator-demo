package com.karlz.redisactuator.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisMetricsConfig {

    private final MeterRegistry meterRegistry;

    @Autowired
    public RedisMetricsConfig(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Bean
    public Counter redisCommandCounter() {
        return Counter.builder("redis.commands")
                .description("Number of Redis commands executed")
                .register(meterRegistry);
    }

    @Bean
    public Counter redisCallCounter() {
        return Counter.builder("redis.calls")
                .description("Number of Redis commands executed")
                .register(meterRegistry);
    }
}
