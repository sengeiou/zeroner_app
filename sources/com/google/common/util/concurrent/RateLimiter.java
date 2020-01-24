package com.google.common.util.concurrent;

import com.github.mikephil.charting.utils.Utils;
import com.google.common.annotations.Beta;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.base.Ticker;
import java.util.concurrent.TimeUnit;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
@Beta
public abstract class RateLimiter {
    double maxPermits;
    private final Object mutex;
    private long nextFreeTicketMicros;
    private final long offsetNanos;
    volatile double stableIntervalMicros;
    double storedPermits;
    private final SleepingTicker ticker;

    private static class Bursty extends RateLimiter {
        final double maxBurstSeconds;

        Bursty(SleepingTicker ticker, double maxBurstSeconds2) {
            super(ticker);
            this.maxBurstSeconds = maxBurstSeconds2;
        }

        /* access modifiers changed from: 0000 */
        public void doSetRate(double permitsPerSecond, double stableIntervalMicros) {
            double d = Utils.DOUBLE_EPSILON;
            double oldMaxPermits = this.maxPermits;
            this.maxPermits = this.maxBurstSeconds * permitsPerSecond;
            if (oldMaxPermits != Utils.DOUBLE_EPSILON) {
                d = (this.storedPermits * this.maxPermits) / oldMaxPermits;
            }
            this.storedPermits = d;
        }

        /* access modifiers changed from: 0000 */
        public long storedPermitsToWaitTime(double storedPermits, double permitsToTake) {
            return 0;
        }
    }

    @VisibleForTesting
    static abstract class SleepingTicker extends Ticker {
        static final SleepingTicker SYSTEM_TICKER = new SleepingTicker() {
            public long read() {
                return systemTicker().read();
            }

            public void sleepMicrosUninterruptibly(long micros) {
                if (micros > 0) {
                    Uninterruptibles.sleepUninterruptibly(micros, TimeUnit.MICROSECONDS);
                }
            }
        };

        /* access modifiers changed from: 0000 */
        public abstract void sleepMicrosUninterruptibly(long j);

        SleepingTicker() {
        }
    }

    private static class WarmingUp extends RateLimiter {
        private double halfPermits;
        private double slope;
        final long warmupPeriodMicros;

        WarmingUp(SleepingTicker ticker, long warmupPeriod, TimeUnit timeUnit) {
            super(ticker);
            this.warmupPeriodMicros = timeUnit.toMicros(warmupPeriod);
        }

        /* access modifiers changed from: 0000 */
        public void doSetRate(double permitsPerSecond, double stableIntervalMicros) {
            double oldMaxPermits = this.maxPermits;
            this.maxPermits = ((double) this.warmupPeriodMicros) / stableIntervalMicros;
            this.halfPermits = this.maxPermits / 2.0d;
            this.slope = ((stableIntervalMicros * 3.0d) - stableIntervalMicros) / this.halfPermits;
            if (oldMaxPermits == Double.POSITIVE_INFINITY) {
                this.storedPermits = Utils.DOUBLE_EPSILON;
            } else {
                this.storedPermits = oldMaxPermits == Utils.DOUBLE_EPSILON ? this.maxPermits : (this.storedPermits * this.maxPermits) / oldMaxPermits;
            }
        }

        /* access modifiers changed from: 0000 */
        public long storedPermitsToWaitTime(double storedPermits, double permitsToTake) {
            double availablePermitsAboveHalf = storedPermits - this.halfPermits;
            long micros = 0;
            if (availablePermitsAboveHalf > Utils.DOUBLE_EPSILON) {
                double permitsAboveHalfToTake = Math.min(availablePermitsAboveHalf, permitsToTake);
                micros = (long) (((permitsToTime(availablePermitsAboveHalf) + permitsToTime(availablePermitsAboveHalf - permitsAboveHalfToTake)) * permitsAboveHalfToTake) / 2.0d);
                permitsToTake -= permitsAboveHalfToTake;
            }
            return (long) (((double) micros) + (this.stableIntervalMicros * permitsToTake));
        }

        private double permitsToTime(double permits) {
            return this.stableIntervalMicros + (this.slope * permits);
        }
    }

    /* access modifiers changed from: 0000 */
    public abstract void doSetRate(double d, double d2);

    /* access modifiers changed from: 0000 */
    public abstract long storedPermitsToWaitTime(double d, double d2);

    public static RateLimiter create(double permitsPerSecond) {
        return create(SleepingTicker.SYSTEM_TICKER, permitsPerSecond);
    }

    @VisibleForTesting
    static RateLimiter create(SleepingTicker ticker2, double permitsPerSecond) {
        RateLimiter rateLimiter = new Bursty(ticker2, 1.0d);
        rateLimiter.setRate(permitsPerSecond);
        return rateLimiter;
    }

    public static RateLimiter create(double permitsPerSecond, long warmupPeriod, TimeUnit unit) {
        return create(SleepingTicker.SYSTEM_TICKER, permitsPerSecond, warmupPeriod, unit);
    }

    @VisibleForTesting
    static RateLimiter create(SleepingTicker ticker2, double permitsPerSecond, long warmupPeriod, TimeUnit unit) {
        RateLimiter rateLimiter = new WarmingUp(ticker2, warmupPeriod, unit);
        rateLimiter.setRate(permitsPerSecond);
        return rateLimiter;
    }

    @VisibleForTesting
    static RateLimiter createWithCapacity(SleepingTicker ticker2, double permitsPerSecond, long maxBurstBuildup, TimeUnit unit) {
        Bursty rateLimiter = new Bursty(ticker2, ((double) unit.toNanos(maxBurstBuildup)) / 1.0E9d);
        rateLimiter.setRate(permitsPerSecond);
        return rateLimiter;
    }

    private RateLimiter(SleepingTicker ticker2) {
        this.mutex = new Object();
        this.nextFreeTicketMicros = 0;
        this.ticker = ticker2;
        this.offsetNanos = ticker2.read();
    }

    public final void setRate(double permitsPerSecond) {
        Preconditions.checkArgument(permitsPerSecond > Utils.DOUBLE_EPSILON && !Double.isNaN(permitsPerSecond), "rate must be positive");
        synchronized (this.mutex) {
            resync(readSafeMicros());
            double stableIntervalMicros2 = ((double) TimeUnit.SECONDS.toMicros(1)) / permitsPerSecond;
            this.stableIntervalMicros = stableIntervalMicros2;
            doSetRate(permitsPerSecond, stableIntervalMicros2);
        }
    }

    public final double getRate() {
        return ((double) TimeUnit.SECONDS.toMicros(1)) / this.stableIntervalMicros;
    }

    public double acquire() {
        return acquire(1);
    }

    public double acquire(int permits) {
        long microsToWait = reserve(permits);
        this.ticker.sleepMicrosUninterruptibly(microsToWait);
        return (1.0d * ((double) microsToWait)) / ((double) TimeUnit.SECONDS.toMicros(1));
    }

    /* access modifiers changed from: 0000 */
    public long reserve() {
        return reserve(1);
    }

    /* access modifiers changed from: 0000 */
    public long reserve(int permits) {
        long reserveNextTicket;
        checkPermits(permits);
        synchronized (this.mutex) {
            reserveNextTicket = reserveNextTicket((double) permits, readSafeMicros());
        }
        return reserveNextTicket;
    }

    public boolean tryAcquire(long timeout, TimeUnit unit) {
        return tryAcquire(1, timeout, unit);
    }

    public boolean tryAcquire(int permits) {
        return tryAcquire(permits, 0, TimeUnit.MICROSECONDS);
    }

    public boolean tryAcquire() {
        return tryAcquire(1, 0, TimeUnit.MICROSECONDS);
    }

    public boolean tryAcquire(int permits, long timeout, TimeUnit unit) {
        long timeoutMicros = unit.toMicros(timeout);
        checkPermits(permits);
        synchronized (this.mutex) {
            long nowMicros = readSafeMicros();
            if (this.nextFreeTicketMicros > nowMicros + timeoutMicros) {
                return false;
            }
            long microsToWait = reserveNextTicket((double) permits, nowMicros);
            this.ticker.sleepMicrosUninterruptibly(microsToWait);
            return true;
        }
    }

    private static void checkPermits(int permits) {
        Preconditions.checkArgument(permits > 0, "Requested permits must be positive");
    }

    private long reserveNextTicket(double requiredPermits, long nowMicros) {
        resync(nowMicros);
        long microsToNextFreeTicket = Math.max(0, this.nextFreeTicketMicros - nowMicros);
        double storedPermitsToSpend = Math.min(requiredPermits, this.storedPermits);
        this.nextFreeTicketMicros += storedPermitsToWaitTime(this.storedPermits, storedPermitsToSpend) + ((long) (this.stableIntervalMicros * (requiredPermits - storedPermitsToSpend)));
        this.storedPermits -= storedPermitsToSpend;
        return microsToNextFreeTicket;
    }

    private void resync(long nowMicros) {
        if (nowMicros > this.nextFreeTicketMicros) {
            this.storedPermits = Math.min(this.maxPermits, this.storedPermits + (((double) (nowMicros - this.nextFreeTicketMicros)) / this.stableIntervalMicros));
            this.nextFreeTicketMicros = nowMicros;
        }
    }

    private long readSafeMicros() {
        return TimeUnit.NANOSECONDS.toMicros(this.ticker.read() - this.offsetNanos);
    }

    public String toString() {
        return String.format("RateLimiter[stableRate=%3.1fqps]", new Object[]{Double.valueOf(1000000.0d / this.stableIntervalMicros)});
    }
}
