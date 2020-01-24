package com.iwown.app.nativeinvoke;

public class NativeInvoker {
    public native int calculateSleep(String str, long j, String str2, String str3, int i, SA_SleepBufInfo sA_SleepBufInfo);

    public native int calculateSleepFile(String str, String str2, String str3, int i, SA_SleepBufInfo sA_SleepBufInfo);

    static {
        System.loadLibrary("iwownSleep");
    }
}
