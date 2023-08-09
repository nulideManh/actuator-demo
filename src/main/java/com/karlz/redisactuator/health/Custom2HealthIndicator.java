package com.karlz.redisactuator.health;


import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

@Component
public class Custom2HealthIndicator implements HealthIndicator {

    private final RedisConnectionFactory connectionFactory;

    public Custom2HealthIndicator(RedisConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public Health health() {
        try {
            RedisConnection connection = connectionFactory.getConnection();
            long startTime = System.currentTimeMillis();
            connection.ping();
            long endTime = System.currentTimeMillis();
            connection.close();

            long responseTime = endTime - startTime;
            return Health.up().withDetail("Redis", "Response time: " + responseTime + " ms").build();
        } catch (Exception e) {
            return Health.down().withDetail("Redis", "Connection failed").withException(e).build();
        }
    }
}
