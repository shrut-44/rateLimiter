package com.example.ratelimiting.limiter;

import org.springframework.http.ResponseEntity;

import java.time.Duration;
import java.time.LocalDateTime;

import static java.lang.Math.min;

public class tokenbucket {
    private int capacity;
    private int token;
    private LocalDateTime lastRefillTime;
    public tokenbucket(){
        this.capacity = 10;
        this.token = 10;
        this.lastRefillTime = LocalDateTime.now();
    }
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
