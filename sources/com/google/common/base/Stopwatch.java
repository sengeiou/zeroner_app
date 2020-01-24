package com.google.common.base;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import java.util.concurrent.TimeUnit;

@GwtCompatible(emulated = true)
@Beta
public final class Stopwatch {
    private long elapsedNanos;
    private boolean isRunning;
    private long startTick;
    private final Ticker ticker;

    /* renamed from: com.google.common.base.Stopwatch$1 reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$java$util$concurrent$TimeUnit = new int[TimeUnit.values().length];

        static {
            try {
                $SwitchMap$java$util$concurrent$TimeUnit[TimeUnit.NANOSECONDS.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$java$util$concurrent$TimeUnit[TimeUnit.MICROSECONDS.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$java$util$concurrent$TimeUnit[TimeUnit.MILLISECONDS.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$java$util$concurrent$TimeUnit[TimeUnit.SECONDS.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public static Stopwatch createUnstarted() {
        return new Stopwatch();
    }

    public static Stopwatch createUnstarted(Ticker ticker2) {
        return new Stopwatch(ticker2);
    }

    public static Stopwatch createStarted() {
        return new Stopwatch().start();
    }

    public static Stopwatch createStarted(Ticker ticker2) {
        return new Stopwatch(ticker2).start();
    }

    @Deprecated
    Stopwatch() {
        this(Ticker.systemTicker());
    }

    @Deprecated
    Stopwatch(Ticker ticker2) {
        this.ticker = (Ticker) Preconditions.checkNotNull(ticker2, "ticker");
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public Stopwatch start() {
        Preconditions.checkState(!this.isRunning, "This stopwatch is already running.");
        this.isRunning = true;
        this.startTick = this.ticker.read();
        return this;
    }

    public Stopwatch stop() {
        long tick = this.ticker.read();
        Preconditions.checkState(this.isRunning, "This stopwatch is already stopped.");
        this.isRunning = false;
        this.elapsedNanos += tick - this.startTick;
        return this;
    }

    public Stopwatch reset() {
        this.elapsedNanos = 0;
        this.isRunning = false;
        return this;
    }

    private long elapsedNanos() {
        return this.isRunning ? (this.ticker.read() - this.startTick) + this.elapsedNanos : this.elapsedNanos;
    }

    public long elapsed(TimeUnit desiredUnit) {
        return desiredUnit.convert(elapsedNanos(), TimeUnit.NANOSECONDS);
    }

    @GwtIncompatible("String.format()")
    public String toString() {
        long nanos = elapsedNanos();
        TimeUnit unit = chooseUnit(nanos);
        return String.format("%.4g %s", new Object[]{Double.valueOf(((double) nanos) / ((double) TimeUnit.NANOSECONDS.convert(1, unit))), abbreviate(unit)});
    }

    private static TimeUnit chooseUnit(long nanos) {
        if (TimeUnit.SECONDS.convert(nanos, TimeUnit.NANOSECONDS) > 0) {
            return TimeUnit.SECONDS;
        }
        if (TimeUnit.MILLISECONDS.convert(nanos, TimeUnit.NANOSECONDS) > 0) {
            return TimeUnit.MILLISECONDS;
        }
        if (TimeUnit.MICROSECONDS.convert(nanos, TimeUnit.NANOSECONDS) > 0) {
            return TimeUnit.MICROSECONDS;
        }
        return TimeUnit.NANOSECONDS;
    }

    private static String abbreviate(TimeUnit unit) {
        switch (AnonymousClass1.$SwitchMap$java$util$concurrent$TimeUnit[unit.ordinal()]) {
            case 1:
                return "ns";
            case 2:
                return "μs";
            case 3:
                return "ms";
            case 4:
                return "s";
            default:
                throw new AssertionError();
        }
    }
}
