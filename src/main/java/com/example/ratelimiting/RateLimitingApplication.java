package com.example.ratelimiting;

import com.example.ratelimiting.limiter.tokenbucket;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;

@SpringBootApplication
public class RateLimitingApplication {
    public static void main(String[] args) {
        SpringApplication.run(RateLimitingApplication.class, args);
    }
}
