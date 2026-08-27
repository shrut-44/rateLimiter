package com.example.ratelimiting.services;

import com.example.ratelimiting.limiter.tokenbucket;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimiter {
    private final Map<String, tokenbucket> mp;
    public RateLimiter(){
        this.mp = new ConcurrentHashMap<>();
    }
    public boolean allow(String ip) {
        tokenbucket bucket = mp.computeIfAbsent(ip, key -> new tokenbucket());
        return bucket.tryConsume();
    }
}
