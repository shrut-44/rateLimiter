package com.example.ratelimiting.bean;

import com.example.ratelimiting.limiter.FixedWindow;
import com.example.ratelimiting.limiter.SlidingLogWindow;
import com.example.ratelimiting.limiter.SlidingWindowCounter;
import com.example.ratelimiting.limiter.tokenbucket;
import com.example.ratelimiting.services.RateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class rateLimitingBeans {
    @Bean
    public RateLimiter<tokenbucket> tokenBucketRateLimiter() {
        return new RateLimiter<>(ip -> new tokenbucket(10,10));
    }

    @Bean
    public RateLimiter<FixedWindow> fixedWindowRateLimiter() {
        return new RateLimiter<>(ip -> new FixedWindow(10, 60));
    }

    @Bean
    public RateLimiter<SlidingLogWindow> slidingLogWindowRateLimiter() {
        return new RateLimiter<>(ip -> new SlidingLogWindow(10, 60));
    }

    @Bean
    public RateLimiter<SlidingWindowCounter> slidingWindowCounterRateLimiter() {
        return new RateLimiter<>(ip -> new SlidingWindowCounter(10, 60));
    }
}
