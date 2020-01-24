package com.iwown.device_module.device_message_push.bean;

import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import java.io.Serializable;

public class AppInfo implements Serializable {
    public Bitmap appBitmapIcon = null;
    public Drawable appIcon = null;
    public String appName = "";
    public boolean check;
    public String packageName = "";
    private ResolveInfo resolveInfo;
    public String setUpTime = "";
    public int versionCode = 0;
    public String versionName = "";

    public ResolveInfo getResolveInfo() {
        return this.resolveInfo;
    }

    public void setResolveInfo(ResolveInfo resolveInfo2) {
        this.resolveInfo = resolveInfo2;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName2) {
        this.appName = appName2;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName2) {
        this.packageName = packageName2;
    }

    public String getVersionName() {
        return this.versionName;
    }

    public void setVersionName(String versionName2) {
        this.versionName = versionName2;
    }

    public int getVersionCode() {
        return this.versionCode;
    }

    public void setVersionCode(int versionCode2) {
        this.versionCode = versionCode2;
    }

    public Drawable getAppIcon() {
        return this.appIcon;
    }

    public void setAppIcon(Drawable appIcon2) {
        this.appIcon = appIcon2;
    }

    public Bitmap getAppBitmapIcon() {
        return this.appBitmapIcon;
    }

    public void setAppBitmapIcon(Bitmap appBitmapIcon2) {
        this.appBitmapIcon = appBitmapIcon2;
    }

    public String getSetUpTime() {
        return this.setUpTime;
    }

    public void setSetUpTime(String setUpTime2) {
        this.setUpTime = setUpTime2;
    }

    public boolean isCheck() {
        return this.check;
    }

    public void setCheck(boolean check2) {
        this.check = check2;
    }
}
