package com.example.ratelimiting.controller;

import com.example.ratelimiting.limiter.tokenbucket;
import com.example.ratelimiting.services.RateLimiter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class maincontroller {
    private final HttpServletRequest request;
    private final RateLimiter<tokenbucket> rateLimiter;
    public maincontroller(HttpServletRequest request, RateLimiter<tokenbucket> rateLimiter){
        this.request = request;
        this.rateLimiter = rateLimiter;
    }
    @GetMapping("/unlimited")
    public String unlimited(){
        return "Unlimited lets go";
    }
    @GetMapping("/limited")
    public ResponseEntity<?> limited(){
        return (rateLimiter.allow(request.getRemoteAddr())) ? ResponseEntity.ok().body("Limited use wisely")
                : ResponseEntity.status(429).body("You are being rate limited");
    }
}
