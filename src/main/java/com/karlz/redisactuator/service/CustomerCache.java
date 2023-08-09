package com.karlz.redisactuator.service;

import com.karlz.redisactuator.entity.Customer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerCache extends GenericCacheBase<Customer> {

    public CustomerCache(RedisTemplate redisTemplate) {
        super(redisTemplate);
    }

    @Override
    protected String buildEntityKey() {
        return "Customer";
    }

    @Override
    protected String buildRedisKey(Customer from) {
        return from.getId();
    }

}
