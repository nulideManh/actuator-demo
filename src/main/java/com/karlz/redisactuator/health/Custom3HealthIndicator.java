package com.karlz.redisactuator.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class Custom3HealthIndicator implements HealthIndicator {
    private final RedisConnectionFactory connectionFactory;

    public Custom3HealthIndicator(RedisConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public Health health() {
        try {
            RedisConnection connection = connectionFactory.getConnection();
            Properties info = connection.info();
            connection.close();

            String usedMemory = info.getProperty("used_memory");
            return Health.up().withDetail("Redis", "Used memory: " + usedMemory).build();
        } catch (Exception e) {
            return Health.down().withDetail("Redis", "Connection failed").withException(e).build();
        }
    }
}
