package com.example.ratelimiting.limiter;

import java.time.Duration;
import java.time.LocalDateTime;

public class SlidingWindowCounter implements RateLimitingAlgorithm{
    private int previousCnt;
    private int currentCnt;
    private LocalDateTime windowStartTime;
    private final int windowSize;
    private final int limit;
    public SlidingWindowCounter(int windowSize, int limit){
        this.windowSize = windowSize;
        this.limit = limit;
        this.previousCnt = 0;
        this.currentCnt = 0;
        this.windowStartTime = LocalDateTime.now();
    }
    public void reset(LocalDateTime now) {
        long elapsedTime = Duration.between(windowStartTime, now).getSeconds();
        int elapsedWindows = (int)(elapsedTime/windowSize);
        if(elapsedWindows>1){
            previousCnt = 0;
        }else{
            previousCnt = currentCnt;
        }
        currentCnt = 0;
        windowStartTime = windowStartTime.plusSeconds(elapsedWindows*windowSize);
    }

    @Override
    synchronized public boolean tryConsume() {
        LocalDateTime now = LocalDateTime.now();
        if(!now.isBefore(windowStartTime.plusSeconds(windowSize))) reset(now);
        long timeInCurrent = Duration.between(windowStartTime, now).getSeconds();
        double currentWeight = (double)timeInCurrent/windowSize;
        double previousWeight = 1.00 - currentWeight;
        double windowCnt = (currentCnt*currentWeight + previousWeight*previousCnt);
        if(windowCnt>=limit) return false;
        currentCnt++;
        return true;
    }
}
