package com.karlz.redisactuator.config;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
public class RedisConfig {
    /**
     * Đánh dấu redisTemplate là 1 bean String
     * @param redisConnectionFactory
     * @return RedisTemplate object được cấu hình để sử dụng String làm key và Object làm value
     */
    @Bean(name = "redisTemplate")
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){

        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(keySerializer());
        redisTemplate.setHashKeySerializer(keySerializer());
        redisTemplate.setValueSerializer(valueSerializer());
        redisTemplate.setHashValueSerializer(valueSerializer());
        return redisTemplate;
    }

    /**
     * Được đánh dấu là 1 bean và được ưu tiên nếu có nhiều CacheManager
     * @param redisConnectionFactory
     * @return RedisCacheManager object được cấu hình với một số thiết lập cho cache như
     * thời gian timeout mặc định của các entry trong cache,
     * cách serialize key và value.RedisCacheWriter.nonLockingRedisCacheWriter được sử dụng để khởi tạo
     * một RedisCacheWriter không khóa (non-locking) để viết vào cache.
     */
    @Primary
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory){
        // cache configuration object
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();

        redisCacheConfiguration = redisCacheConfiguration.entryTtl(Duration.ofMinutes(30L)) //Set the default timeout for caching: 30 minutes
                .disableCachingNullValues()             //Nếu nó null thì không cache
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))         //Set key serializer
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer((valueSerializer())));  //Set the value serializer

        return RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(redisCacheConfiguration).build();
    }


    /**
     * Đây là một RedisSerializer đã được xây dựng sẵn trong Spring Data Redis
     * để serialize các key thành chuỗi String
     * @return một đối tượng RedisSerializer đặc biệt là StringRedisSerializer
     */
    private RedisSerializer<String> keySerializer() {
        return new StringRedisSerializer();
    }

    /**
     * Đây là một RedisSerializer chung để serialize các value thành một JSON object
     * bằng cách sử dụng Jackson ObjectMapper của Spring
     * @return một đối tượng RedisSerializer khác, là GenericJackson2JsonRedisSerializer
     */
    private RedisSerializer<Object> valueSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }
}
