package com.airbnb.lottie.model;

import android.support.annotation.CheckResult;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KeyPath {
    private final List<String> keys;
    @Nullable
    private KeyPathElement resolvedElement;

    public KeyPath(String... keys2) {
        this.keys = Arrays.asList(keys2);
    }

    private KeyPath(KeyPath keyPath) {
        this.keys = new ArrayList(keyPath.keys);
        this.resolvedElement = keyPath.resolvedElement;
    }

    @RestrictTo({Scope.LIBRARY})
    @CheckResult
    public KeyPath addKey(String key) {
        KeyPath newKeyPath = new KeyPath(this);
        newKeyPath.keys.add(key);
        return newKeyPath;
    }

    @RestrictTo({Scope.LIBRARY})
    public KeyPath resolve(KeyPathElement element) {
        KeyPath keyPath = new KeyPath(this);
        keyPath.resolvedElement = element;
        return keyPath;
    }

    @Nullable
    @RestrictTo({Scope.LIBRARY})
    public KeyPathElement getResolvedElement() {
        return this.resolvedElement;
    }

    @RestrictTo({Scope.LIBRARY})
    public boolean matches(String key, int depth) {
        if (isContainer(key)) {
            return true;
        }
        if (depth >= this.keys.size()) {
            return false;
        }
        if (((String) this.keys.get(depth)).equals(key) || ((String) this.keys.get(depth)).equals("**") || ((String) this.keys.get(depth)).equals("*")) {
            return true;
        }
        return false;
    }

    @RestrictTo({Scope.LIBRARY})
    public int incrementDepthBy(String key, int depth) {
        if (isContainer(key)) {
            return 0;
        }
        if (!((String) this.keys.get(depth)).equals("**")) {
            return 1;
        }
        if (depth == this.keys.size() - 1) {
            return 0;
        }
        return ((String) this.keys.get(depth + 1)).equals(key) ? 2 : 0;
    }

    @RestrictTo({Scope.LIBRARY})
    public boolean fullyResolvesTo(String key, int depth) {
        boolean isLastDepth;
        boolean isGlobstarButNextKeyMatches;
        boolean matches;
        if (depth >= this.keys.size()) {
            return false;
        }
        if (depth == this.keys.size() - 1) {
            isLastDepth = true;
        } else {
            isLastDepth = false;
        }
        String keyAtDepth = (String) this.keys.get(depth);
        if (!keyAtDepth.equals("**")) {
            if (keyAtDepth.equals(key) || keyAtDepth.equals("*")) {
                matches = true;
            } else {
                matches = false;
            }
            if ((isLastDepth || (depth == this.keys.size() - 2 && endsWithGlobstar())) && matches) {
                return true;
            }
            return false;
        }
        if (isLastDepth || !((String) this.keys.get(depth + 1)).equals(key)) {
            isGlobstarButNextKeyMatches = false;
        } else {
            isGlobstarButNextKeyMatches = true;
        }
        if (isGlobstarButNextKeyMatches) {
            if (depth == this.keys.size() - 2 || (depth == this.keys.size() - 3 && endsWithGlobstar())) {
                return true;
            }
            return false;
        } else if (isLastDepth) {
            return true;
        } else {
            if (depth + 1 >= this.keys.size() - 1) {
                return ((String) this.keys.get(depth + 1)).equals(key);
            }
            return false;
        }
    }

    @RestrictTo({Scope.LIBRARY})
    public boolean propagateToChildren(String key, int depth) {
        if (key.equals("__container")) {
            return true;
        }
        return depth < this.keys.size() + -1 || ((String) this.keys.get(depth)).equals("**");
    }

    private boolean isContainer(String key) {
        return key.equals("__container");
    }

    private boolean endsWithGlobstar() {
        return ((String) this.keys.get(this.keys.size() - 1)).equals("**");
    }

    public String keysToString() {
        return this.keys.toString();
    }

    public String toString() {
        return "KeyPath{keys=" + this.keys + ",resolved=" + (this.resolvedElement != null) + '}';
    }
}
