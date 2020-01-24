package com.google.api.client.util;

import com.github.mikephil.charting.utils.Utils;
import java.io.IOException;

public class ExponentialBackOff implements BackOff {
    public static final int DEFAULT_INITIAL_INTERVAL_MILLIS = 500;
    public static final int DEFAULT_MAX_ELAPSED_TIME_MILLIS = 900000;
    public static final int DEFAULT_MAX_INTERVAL_MILLIS = 60000;
    public static final double DEFAULT_MULTIPLIER = 1.5d;
    public static final double DEFAULT_RANDOMIZATION_FACTOR = 0.5d;
    private int currentIntervalMillis;
    private final int initialIntervalMillis;
    private final int maxElapsedTimeMillis;
    private final int maxIntervalMillis;
    private final double multiplier;
    private final NanoClock nanoClock;
    private final double randomizationFactor;
    long startTimeNanos;

    public static class Builder {
        int initialIntervalMillis = 500;
        int maxElapsedTimeMillis = 900000;
        int maxIntervalMillis = 60000;
        double multiplier = 1.5d;
        NanoClock nanoClock = NanoClock.SYSTEM;
        double randomizationFactor = 0.5d;

        public ExponentialBackOff build() {
            return new ExponentialBackOff(this);
        }

        public final int getInitialIntervalMillis() {
            return this.initialIntervalMillis;
        }

        public Builder setInitialIntervalMillis(int initialIntervalMillis2) {
            this.initialIntervalMillis = initialIntervalMillis2;
            return this;
        }

        public final double getRandomizationFactor() {
            return this.randomizationFactor;
        }

        public Builder setRandomizationFactor(double randomizationFactor2) {
            this.randomizationFactor = randomizationFactor2;
            return this;
        }

        public final double getMultiplier() {
            return this.multiplier;
        }

        public Builder setMultiplier(double multiplier2) {
            this.multiplier = multiplier2;
            return this;
        }

        public final int getMaxIntervalMillis() {
            return this.maxIntervalMillis;
        }

        public Builder setMaxIntervalMillis(int maxIntervalMillis2) {
            this.maxIntervalMillis = maxIntervalMillis2;
            return this;
        }

        public final int getMaxElapsedTimeMillis() {
            return this.maxElapsedTimeMillis;
        }

        public Builder setMaxElapsedTimeMillis(int maxElapsedTimeMillis2) {
            this.maxElapsedTimeMillis = maxElapsedTimeMillis2;
            return this;
        }

        public final NanoClock getNanoClock() {
            return this.nanoClock;
        }

        public Builder setNanoClock(NanoClock nanoClock2) {
            this.nanoClock = (NanoClock) Preconditions.checkNotNull(nanoClock2);
            return this;
        }
    }

    public ExponentialBackOff() {
        this(new Builder());
    }

    protected ExponentialBackOff(Builder builder) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4 = true;
        this.initialIntervalMillis = builder.initialIntervalMillis;
        this.randomizationFactor = builder.randomizationFactor;
        this.multiplier = builder.multiplier;
        this.maxIntervalMillis = builder.maxIntervalMillis;
        this.maxElapsedTimeMillis = builder.maxElapsedTimeMillis;
        this.nanoClock = builder.nanoClock;
        Preconditions.checkArgument(this.initialIntervalMillis > 0);
        if (Utils.DOUBLE_EPSILON > this.randomizationFactor || this.randomizationFactor >= 1.0d) {
            z = false;
        } else {
            z = true;
        }
        Preconditions.checkArgument(z);
        if (this.multiplier >= 1.0d) {
            z2 = true;
        } else {
            z2 = false;
        }
        Preconditions.checkArgument(z2);
        if (this.maxIntervalMillis >= this.initialIntervalMillis) {
            z3 = true;
        } else {
            z3 = false;
        }
        Preconditions.checkArgument(z3);
        if (this.maxElapsedTimeMillis <= 0) {
            z4 = false;
        }
        Preconditions.checkArgument(z4);
        reset();
    }

    public final void reset() {
        this.currentIntervalMillis = this.initialIntervalMillis;
        this.startTimeNanos = this.nanoClock.nanoTime();
    }

    public long nextBackOffMillis() throws IOException {
        if (getElapsedTimeMillis() > ((long) this.maxElapsedTimeMillis)) {
            return -1;
        }
        int randomizedInterval = getRandomValueFromInterval(this.randomizationFactor, Math.random(), this.currentIntervalMillis);
        incrementCurrentInterval();
        return (long) randomizedInterval;
    }

    static int getRandomValueFromInterval(double randomizationFactor2, double random, int currentIntervalMillis2) {
        double delta = randomizationFactor2 * ((double) currentIntervalMillis2);
        double minInterval = ((double) currentIntervalMillis2) - delta;
        return (int) (((((((double) currentIntervalMillis2) + delta) - minInterval) + 1.0d) * random) + minInterval);
    }

    public final int getInitialIntervalMillis() {
        return this.initialIntervalMillis;
    }

    public final double getRandomizationFactor() {
        return this.randomizationFactor;
    }

    public final int getCurrentIntervalMillis() {
        return this.currentIntervalMillis;
    }

    public final double getMultiplier() {
        return this.multiplier;
    }

    public final int getMaxIntervalMillis() {
        return this.maxIntervalMillis;
    }

    public final int getMaxElapsedTimeMillis() {
        return this.maxElapsedTimeMillis;
    }

    public final long getElapsedTimeMillis() {
        return (this.nanoClock.nanoTime() - this.startTimeNanos) / 1000000;
    }

    private void incrementCurrentInterval() {
        if (((double) this.currentIntervalMillis) >= ((double) this.maxIntervalMillis) / this.multiplier) {
            this.currentIntervalMillis = this.maxIntervalMillis;
        } else {
            this.currentIntervalMillis = (int) (((double) this.currentIntervalMillis) * this.multiplier);
        }
    }
}
