package com.example.ratelimiting.limiter;

import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Deque;

public class SlidingLogWindow implements RateLimitingAlgorithm{
    private final int limit;
    private final int windowSize;
    private Deque<LocalDateTime> timeStamps;
    public SlidingLogWindow(int limit, int windowSize){
        this.limit = limit;
        this.windowSize = windowSize;
        this.timeStamps = new ArrayDeque<>();
    }

    @Override
    public synchronized boolean tryConsume() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime cutoff = now.minusSeconds(windowSize);
        while (!timeStamps.isEmpty() &&
                timeStamps.getFirst().isBefore(cutoff)) {
            timeStamps.removeFirst();
        }
        if (timeStamps.size() >= limit) {
            return false;
        }
        timeStamps.addLast(now);
        return true;
    }
}
