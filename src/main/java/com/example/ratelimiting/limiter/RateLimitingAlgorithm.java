package com.example.ratelimiting.limiter;

public interface RateLimitingAlgorithm {
    public boolean tryConsume();
}
