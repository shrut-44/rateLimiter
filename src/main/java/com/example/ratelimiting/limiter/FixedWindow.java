package com.example.ratelimiting.limiter;

import java.time.LocalDateTime;

public class FixedWindow implements RateLimitingAlgorithm{
    private int cnt;
    private LocalDateTime windowStartTime;
    private final int limit;
    private final int windowSizeInSeconds;
    public FixedWindow(int limit, int windowSizeInSeconds){
        this.cnt = 0;
        this.windowStartTime = LocalDateTime.now();
        this.limit = limit;
        this.windowSizeInSeconds = windowSizeInSeconds;
    }

    public void resetWindow(){
        this.cnt = 0;
        this.windowStartTime = LocalDateTime.now();
    }
    @Override
    synchronized public boolean tryConsume(){
        if(LocalDateTime.now().isAfter(windowStartTime.plusSeconds(windowSizeInSeconds))){
            resetWindow();
        }
        if(cnt>=limit){
            return false;
        }
        cnt++;
        return true;
    }
}
