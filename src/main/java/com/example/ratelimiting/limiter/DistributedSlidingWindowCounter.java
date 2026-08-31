package com.example.ratelimiting.limiter;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class DistributedSlidingWindowCounter {
    private final StringRedisTemplate redisTemplate;
    private final RedisScript<Long> redisScript;
    public DistributedSlidingWindowCounter(StringRedisTemplate redisTemplate, RedisScript<Long> redisScript){
        this.redisTemplate = redisTemplate;
        this.redisScript = redisScript;
    }

    public boolean tryConsume(String ip, int limit, int windowSize){
        String key = "ratelimiting:"+ip;
        long time = System.currentTimeMillis() / 1000;
        Long result = redisTemplate.execute(
                redisScript,
                Collections.singletonList(key),
                String.valueOf(limit),
                String.valueOf(windowSize),
                String.valueOf(time)
        );
        return result!= null && result ==1;
    }
}
