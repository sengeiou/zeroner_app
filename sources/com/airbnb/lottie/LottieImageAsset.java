package com.airbnb.lottie;

import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;

public class LottieImageAsset {
    private final String dirName;
    private final String fileName;
    private final int height;
    private final String id;
    private final int width;

    @RestrictTo({Scope.LIBRARY})
    public LottieImageAsset(int width2, int height2, String id2, String fileName2, String dirName2) {
        this.width = width2;
        this.height = height2;
        this.id = id2;
        this.fileName = fileName2;
        this.dirName = dirName2;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public String getId() {
        return this.id;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getDirName() {
        return this.dirName;
    }
}
