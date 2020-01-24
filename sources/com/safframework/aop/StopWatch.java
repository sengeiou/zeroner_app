package com.safframework.aop;

public class StopWatch {
    private long elapsedTime;
    private long endTime;
    private long startTime;

    private void reset() {
        this.startTime = 0;
        this.endTime = 0;
        this.elapsedTime = 0;
    }

    public void start() {
        reset();
        this.startTime = System.nanoTime();
    }

    public void stop() {
        if (this.startTime != 0) {
            this.endTime = System.nanoTime();
            this.elapsedTime = this.endTime - this.startTime;
            return;
        }
        reset();
    }

    public long getElapsedTime() {
        return this.elapsedTime;
    }
}
