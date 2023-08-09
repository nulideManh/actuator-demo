package com.karlz.redisactuator.service;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

@SuppressWarnings("unchecked")
public abstract class GenericCacheBase<T> {
    private final RedisTemplate redisTemplate;


    public GenericCacheBase(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    protected abstract String buildEntityKey();
    protected abstract String buildRedisKey(T from);

    public T findById (String id) {
        T data = (T) redisTemplate.opsForHash().get(buildEntityKey(), id);
        return data;
    }

    public T save (T data, String key) {
        redisTemplate.opsForHash().put(buildEntityKey(), buildRedisKey(data), data);
        return data;
    }

    public List<T> findAll() {
        List<T> res = (List<T>) redisTemplate.opsForHash().values(buildEntityKey());
        return res;
    }

    public String deleteById(String id) {
        String.valueOf(redisTemplate.opsForHash().delete(buildEntityKey(), id));
        return "Done";
    }

    public T findAndDelete(String id) {
        T data = (T)redisTemplate.opsForHash().get(buildEntityKey(), id);
        redisTemplate.opsForHash().delete(buildEntityKey(), id);
        return data;
    }
}
