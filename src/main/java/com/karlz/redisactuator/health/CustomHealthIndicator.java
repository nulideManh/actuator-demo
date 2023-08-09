package com.karlz.redisactuator.health;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.redis.RedisHealthIndicator;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Component
public class CustomHealthIndicator extends AbstractHealthIndicator {
    private final Custom1HealthIndicator custom1HealthIndicator;
    private final Custom2HealthIndicator custom2HealthIndicator;
    private final Custom3HealthIndicator custom3HealthIndicator;

    public CustomHealthIndicator(Custom1HealthIndicator custom1HealthIndicator,
                                 Custom2HealthIndicator custom2HealthIndicator,
                                 Custom3HealthIndicator custom3HealthIndicator) {
        this.custom1HealthIndicator = custom1HealthIndicator;
        this.custom2HealthIndicator = custom2HealthIndicator;
        this.custom3HealthIndicator = custom3HealthIndicator;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        // Perform health checks for each indicator
        builder.up().withDetail("Connection", custom1HealthIndicator.health());
        builder.up().withDetail("Response Time", custom2HealthIndicator.health());
        builder.up().withDetail("Used memory", custom3HealthIndicator.health());
    }
}