package com.example.dcbot.Repository;

import java.util.Map;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisHashRepository {
    private final StringRedisTemplate redisTemplate;

    public RedisHashRepository(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void hset(String key, Map<String, Object> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    public Boolean hashExists(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public Object getValue(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }
}
