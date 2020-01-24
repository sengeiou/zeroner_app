package com.google.api.client.testing.util;

import com.google.api.client.util.BackOff;
import com.google.api.client.util.Beta;
import com.google.api.client.util.Preconditions;
import java.io.IOException;

@Beta
public class MockBackOff implements BackOff {
    private long backOffMillis;
    private int maxTries = 10;
    private int numTries;

    public void reset() throws IOException {
        this.numTries = 0;
    }

    public long nextBackOffMillis() throws IOException {
        if (this.numTries >= this.maxTries || this.backOffMillis == -1) {
            return -1;
        }
        this.numTries++;
        return this.backOffMillis;
    }

    public MockBackOff setBackOffMillis(long backOffMillis2) {
        Preconditions.checkArgument(backOffMillis2 == -1 || backOffMillis2 >= 0);
        this.backOffMillis = backOffMillis2;
        return this;
    }

    public MockBackOff setMaxTries(int maxTries2) {
        Preconditions.checkArgument(maxTries2 >= 0);
        this.maxTries = maxTries2;
        return this;
    }

    public final int getMaxTries() {
        return this.numTries;
    }

    public final int getNumberOfTries() {
        return this.numTries;
    }
}
