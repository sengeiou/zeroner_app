package com.tencent.bugly;

import java.util.Map;

/* compiled from: BUGLY */
public class BuglyStrategy {
    private String a;
    private String b;
    private String c;
    private long d;
    private String e;
    private String f;
    private boolean g = true;
    private boolean h = true;
    private boolean i = true;
    private Class<?> j = null;
    private boolean k = true;
    private boolean l = true;
    private boolean m = true;
    private boolean n = false;
    private a o;

    /* compiled from: BUGLY */
    public static class a {
        public static final int CRASHTYPE_ANR = 4;
        public static final int CRASHTYPE_BLOCK = 7;
        public static final int CRASHTYPE_COCOS2DX_JS = 5;
        public static final int CRASHTYPE_COCOS2DX_LUA = 6;
        public static final int CRASHTYPE_JAVA_CATCH = 1;
        public static final int CRASHTYPE_JAVA_CRASH = 0;
        public static final int CRASHTYPE_NATIVE = 2;
        public static final int CRASHTYPE_U3D = 3;
        public static final int MAX_USERDATA_KEY_LENGTH = 100;
        public static final int MAX_USERDATA_VALUE_LENGTH = 30000;

        public synchronized Map<String, String> onCrashHandleStart(int crashType, String errorType, String errorMessage, String errorStack) {
            return null;
        }

        public synchronized byte[] onCrashHandleStart2GetExtraDatas(int crashType, String errorType, String errorMessage, String errorStack) {
            return null;
        }
    }

    public synchronized BuglyStrategy setBuglyLogUpload(boolean isBuglyLogUpload) {
        this.k = isBuglyLogUpload;
        return this;
    }

    public synchronized BuglyStrategy setRecordUserInfoOnceADay(boolean recordUserInfoOnceADay) {
        this.n = recordUserInfoOnceADay;
        return this;
    }

    public synchronized BuglyStrategy setUploadProcess(boolean isUploadProcess) {
        this.m = isUploadProcess;
        return this;
    }

    public synchronized boolean isUploadProcess() {
        return this.m;
    }

    public synchronized boolean isBuglyLogUpload() {
        return this.k;
    }

    public synchronized boolean recordUserInfoOnceADay() {
        return this.n;
    }

    public boolean isReplaceOldChannel() {
        return this.l;
    }

    public void setReplaceOldChannel(boolean replaceOldChannel) {
        this.l = replaceOldChannel;
    }

    public synchronized String getAppVersion() {
        return this.a == null ? com.tencent.bugly.crashreport.common.info.a.b().o : this.a;
    }

    public synchronized BuglyStrategy setAppVersion(String appVersion) {
        this.a = appVersion;
        return this;
    }

    public synchronized BuglyStrategy setUserInfoActivity(Class<?> userInfoActivity) {
        this.j = userInfoActivity;
        return this;
    }

    public synchronized Class<?> getUserInfoActivity() {
        return this.j;
    }

    public synchronized String getAppChannel() {
        return this.b == null ? com.tencent.bugly.crashreport.common.info.a.b().q : this.b;
    }

    public synchronized BuglyStrategy setAppChannel(String appChannel) {
        this.b = appChannel;
        return this;
    }

    public synchronized String getAppPackageName() {
        return this.c == null ? com.tencent.bugly.crashreport.common.info.a.b().d : this.c;
    }

    public synchronized BuglyStrategy setAppPackageName(String appPackageName) {
        this.c = appPackageName;
        return this;
    }

    public synchronized long getAppReportDelay() {
        return this.d;
    }

    public synchronized BuglyStrategy setAppReportDelay(long appReportDelay) {
        this.d = appReportDelay;
        return this;
    }

    public synchronized String getLibBuglySOFilePath() {
        return this.e;
    }

    public synchronized BuglyStrategy setLibBuglySOFilePath(String customerBuglySOFilePath) {
        this.e = customerBuglySOFilePath;
        return this;
    }

    public synchronized String getDeviceID() {
        return this.f;
    }

    public synchronized BuglyStrategy setDeviceID(String deviceId) {
        this.f = deviceId;
        return this;
    }

    public synchronized boolean isEnableNativeCrashMonitor() {
        return this.g;
    }

    public synchronized BuglyStrategy setEnableNativeCrashMonitor(boolean enableNativeCrashMonitor) {
        this.g = enableNativeCrashMonitor;
        return this;
    }

    public synchronized BuglyStrategy setEnableUserInfo(boolean enableUserInfo) {
        this.i = enableUserInfo;
        return this;
    }

    public synchronized boolean isEnableUserInfo() {
        return this.i;
    }

    public synchronized boolean isEnableANRCrashMonitor() {
        return this.h;
    }

    public synchronized BuglyStrategy setEnableANRCrashMonitor(boolean enableANRCrashMonitor) {
        this.h = enableANRCrashMonitor;
        return this;
    }

    public synchronized a getCrashHandleCallback() {
        return this.o;
    }

    public synchronized BuglyStrategy setCrashHandleCallback(a crashHandleCallback) {
        this.o = crashHandleCallback;
        return this;
    }
}
