package com.example.ratelimiting.services;

import com.example.ratelimiting.limiter.RateLimitingAlgorithm;
import com.example.ratelimiting.limiter.tokenbucket;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@Service
public class RateLimiter<T extends RateLimitingAlgorithm> {

    private final Map<String, T> mp;
    private final Function<String, T> factory;

    public RateLimiter(Function<String, T> factory) {
        this.mp = new ConcurrentHashMap<>();
        this.factory = factory;
    }

    public boolean allow(String ip) {
        T limiter = mp.computeIfAbsent(ip, factory);
        return limiter.tryConsume();
    }
}
