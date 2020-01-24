package com.airbnb.lottie.model;

import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;

public class MutablePair<T> {
    @Nullable
    T first;
    @Nullable
    T second;

    public void set(T first2, T second2) {
        this.first = first2;
        this.second = second2;
    }

    public boolean equals(Object o) {
        if (!(o instanceof Pair)) {
            return false;
        }
        Pair<?, ?> p = (Pair) o;
        if (!objectsEqual(p.first, this.first) || !objectsEqual(p.second, this.second)) {
            return false;
        }
        return true;
    }

    private static boolean objectsEqual(Object a, Object b) {
        return a == b || (a != null && a.equals(b));
    }

    public int hashCode() {
        int i = 0;
        int hashCode = this.first == null ? 0 : this.first.hashCode();
        if (this.second != null) {
            i = this.second.hashCode();
        }
        return hashCode ^ i;
    }

    public String toString() {
        return "Pair{" + String.valueOf(this.first) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + String.valueOf(this.second) + "}";
    }
}
