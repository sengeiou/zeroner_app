package com.tencent.bugly.beta.download;

import java.io.File;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: BUGLY */
public abstract class DownloadTask {
    public static final int COMPLETE = 1;
    public static final int DELETED = 4;
    public static final int DOWNLOADING = 2;
    public static final int FAILED = 5;
    public static final int INIT = 0;
    public static final int PAUSED = 3;
    public static final int TYPE_HOTFIX = 2;
    public static final int TYPE_UPGRADE = 1;
    protected String a;
    protected String b;
    protected String c;
    protected List<DownloadListener> d = new CopyOnWriteArrayList();
    protected long e;
    protected long f;
    protected boolean g = true;
    protected String h = "";
    protected int i = 0;
    protected int j = 1;

    public abstract void delete(boolean z);

    public abstract void download();

    public abstract long getCostTime();

    public abstract File getSaveFile();

    public abstract int getStatus();

    public abstract void stop();

    protected DownloadTask(String downloadUrl, String saveDir, String saveName, String md5) {
        this.a = downloadUrl;
        this.b = saveDir;
        this.c = saveName;
        this.h = md5;
    }

    public long getTotalLength() {
        return this.f;
    }

    public void setTotalLength(long totalLength) {
        this.f = totalLength;
    }

    public long getSavedLength() {
        return this.e;
    }

    public void setSavedLength(long savedLength) {
        this.e = savedLength;
    }

    public String getDownloadUrl() {
        return this.a;
    }

    public void setNeededNotify(boolean neededNotify) {
        this.g = neededNotify;
    }

    public String getMD5() {
        return this.h;
    }

    public int getDownloadType() {
        return this.j;
    }

    public void setDownloadType(int mDownloadType) {
        this.j = mDownloadType;
    }

    public void addListener(DownloadListener listener) {
        if (listener != null && !this.d.contains(listener)) {
            this.d.add(listener);
        }
    }

    public boolean removeListener(DownloadListener listener) {
        return listener != null && this.d.remove(listener);
    }

    public boolean isNeededNotify() {
        return this.g;
    }
}
