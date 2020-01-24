package com.tencent.bugly.beta;

import com.tencent.bugly.proguard.y;

/* compiled from: BUGLY */
public class UpgradeInfo {
    public String apkMd5;
    public String apkUrl;
    public long fileSize;
    public String id = "";
    public String imageUrl;
    public String newFeature = "";
    public long popInterval = 0;
    public int popTimes = 0;
    public long publishTime = 0;
    public int publishType = 0;
    public String title = "";
    public int updateType;
    public int upgradeType = 1;
    public int versionCode;
    public String versionName = "";

    public UpgradeInfo(y detail) {
        if (detail != null) {
            this.id = detail.m;
            this.title = detail.a;
            this.newFeature = detail.b;
            this.publishTime = detail.c;
            this.publishType = detail.d;
            this.upgradeType = detail.g;
            this.popTimes = detail.h;
            this.popInterval = detail.i;
            this.versionCode = detail.e.c;
            this.versionName = detail.e.d;
            this.apkMd5 = detail.e.i;
            this.apkUrl = detail.f.b;
            this.fileSize = detail.f.d;
            this.imageUrl = (String) detail.l.get("IMG_title");
            this.updateType = detail.p;
        }
    }
}
