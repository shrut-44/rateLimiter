package com.example.ratelimiting.limiter;

import org.springframework.http.ResponseEntity;

import java.time.Duration;
import java.time.LocalDateTime;

import static java.lang.Math.min;

public class tokenbucket implements RateLimitingAlgorithm {
    private final int capacity;
    private int token;
    private LocalDateTime lastRefillTime;
    public tokenbucket(int capacity, int token){
        this.capacity = capacity;
        this.token = token;
        this.lastRefillTime = LocalDateTime.now();
    }
    @Override
    synchronized public boolean tryConsume(){
        this.refill();
        if(token==0){
            return false;
        }else{
            token--;
            return true;
        }
    }
    public void refill(){
        long tokensToAdd = Duration.between(lastRefillTime, LocalDateTime.now()).getSeconds();
        int toAdd = Math.min((int) tokensToAdd, capacity-token);
        token += toAdd;
        lastRefillTime = LocalDateTime.now();
    }
}
