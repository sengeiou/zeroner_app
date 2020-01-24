package com.tencent.open.a;

import android.text.format.Time;
import android.util.Log;
import com.iwown.device_module.device_camera.exif.ExifInterface.GpsLongitudeRef;
import com.iwown.device_module.device_camera.exif.ExifInterface.GpsStatus;
import org.apache.commons.cli.HelpFormatter;

/* compiled from: ProGuard */
public final class h {
    public static final h a = new h();

    public final String a(int i) {
        switch (i) {
            case 1:
                return GpsStatus.INTEROPERABILITY;
            case 2:
                return "D";
            case 4:
                return "I";
            case 8:
                return GpsLongitudeRef.WEST;
            case 16:
                return GpsLongitudeRef.EAST;
            case 32:
                return GpsStatus.IN_PROGRESS;
            default:
                return HelpFormatter.DEFAULT_OPT_PREFIX;
        }
    }

    public String a(int i, Thread thread, long j, String str, String str2, Throwable th) {
        long j2 = j % 1000;
        Time time = new Time();
        time.set(j);
        StringBuilder sb = new StringBuilder();
        sb.append(a(i)).append('/').append(time.format("%Y-%m-%d %H:%M:%S")).append('.');
        if (j2 < 10) {
            sb.append("00");
        } else if (j2 < 100) {
            sb.append('0');
        }
        sb.append(j2).append(' ').append('[');
        if (thread == null) {
            sb.append("N/A");
        } else {
            sb.append(thread.getName());
        }
        sb.append(']').append('[').append(str).append(']').append(' ').append(str2).append(10);
        if (th != null) {
            sb.append("* Exception : \n").append(Log.getStackTraceString(th)).append(10);
        }
        return sb.toString();
    }
}
