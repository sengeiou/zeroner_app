package com.tencent.bugly.crashreport;

import android.util.Log;
import com.iwown.device_module.device_camera.exif.ExifInterface.GpsLongitudeRef;
import com.iwown.device_module.device_camera.exif.ExifInterface.GpsStatus;
import com.tencent.bugly.b;
import com.tencent.bugly.proguard.ao;

/* compiled from: BUGLY */
public class BuglyLog {
    public static void v(String tag, String content) {
        if (tag == null) {
            tag = "";
        }
        if (content == null) {
            content = "null";
        }
        if (b.c) {
            Log.v(tag, content);
        }
        ao.a(GpsStatus.INTEROPERABILITY, tag, content);
    }

    public static void d(String tag, String content) {
        if (tag == null) {
            tag = "";
        }
        if (content == null) {
            content = "null";
        }
        if (b.c) {
            Log.d(tag, content);
        }
        ao.a("D", tag, content);
    }

    public static void i(String tag, String content) {
        if (tag == null) {
            tag = "";
        }
        if (content == null) {
            content = "null";
        }
        if (b.c) {
            Log.i(tag, content);
        }
        ao.a("I", tag, content);
    }

    public static void w(String tag, String content) {
        if (tag == null) {
            tag = "";
        }
        if (content == null) {
            content = "null";
        }
        if (b.c) {
            Log.w(tag, content);
        }
        ao.a(GpsLongitudeRef.WEST, tag, content);
    }

    public static void e(String tag, String content) {
        if (tag == null) {
            tag = "";
        }
        if (content == null) {
            content = "null";
        }
        if (b.c) {
            Log.e(tag, content);
        }
        ao.a(GpsLongitudeRef.EAST, tag, content);
    }

    public static void e(String tag, String content, Throwable throwable) {
        if (tag == null) {
            tag = "";
        }
        if (content == null) {
            content = "null";
        }
        if (b.c) {
            Log.e(tag, content, throwable);
        }
        ao.a(GpsLongitudeRef.EAST, tag, throwable);
    }

    public static void setCache(int size) {
        ao.a(size);
    }
}
