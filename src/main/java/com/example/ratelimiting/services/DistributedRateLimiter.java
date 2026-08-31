package com.example.ratelimiting.services;

import com.example.ratelimiting.limiter.DistributedSlidingWindowCounter;
import org.springframework.stereotype.Service;

@Service
public class DistributedRateLimiter {
    private final DistributedSlidingWindowCounter distributedSlidingWindowCounter;
    public DistributedRateLimiter(DistributedSlidingWindowCounter distributedSlidingWindowCounter){
        this.distributedSlidingWindowCounter = distributedSlidingWindowCounter;
    }
    public boolean allow(String ip){
        return distributedSlidingWindowCounter.tryConsume(ip, 10, 60);
    }
}
