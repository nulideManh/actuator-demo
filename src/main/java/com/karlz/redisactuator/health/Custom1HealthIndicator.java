package com.karlz.redisactuator.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

@Component
public class Custom1HealthIndicator implements HealthIndicator {

    private final RedisConnectionFactory connectionFactory;

    public Custom1HealthIndicator(RedisConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public Health health() {
        try(RedisConnection connection = connectionFactory.getConnection()) {
            connection.close();
            return Health.up().withDetail("Redis", "Connection successful").build();
        } catch (Exception e) {
            return Health.down().withDetail("Redis", "Connection failed").withException(e).build();
        }
    }

}
