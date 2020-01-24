package com.loc;

import com.google.common.primitives.UnsignedBytes;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.loc.v.a;
import pub.devrel.easypermissions.BuildConfig;

/* compiled from: ConstConfig */
public final class ae {
    static String a = "CUlNBL0VDQi9QS0NTMVBhZGRpbmc";
    public static String b;
    public static String c;
    static byte[] d = null;
    private static String e = "ADgAIwBbAA8AagAIAHIAEwCFAD8AxABDAQcAIQEoADgBYAA8AZwAnwI7APADKwAHAzIADAM+AA9LWVc1a2NtOXBaQzV2Y3k1VFpYSjJhV05sVFdGdVlXZGxjZ1FaMlYwVTJWeWRtbGpaUUljR2h2Ym1VVWFYQm9iMjVsYzNWaWFXNW1id01ZMjl0TG1GdVpISnZhV1F1YVc1MFpYSnVZV3d1ZEdWc1pYQm9iMjU1TGtsVVpXeGxjR2h2Ym5ra1UzUjFZZ1FZMjl0TG1GdVpISnZhV1F1YVc1MFpYSnVZV3d1ZEdWc1pYQm9iMjU1TGtsUWFHOXVaVk4xWWtsdVptOGtVM1IxWWdHVkZKQlRsTkJRMVJKVDA1ZloyVjBSR1YyYVdObFNXUT1FWTI5dExtRnVaSEp2YVdRdWFXNTBaWEp1WVd3dWRHVnNaWEJvYjI1NUxrbFVaV3hsY0dodmJua0lZMjl0TG1GdVpISnZhV1F1YVc1MFpYSnVZV3d1ZEdWc1pYQm9iMjU1TGtsUWFHOXVaVk4xWWtsdVptOEVJbXRsZVNJNklpVnpJaXdpY0d4aGRHWnZjbTBpT2lKaGJtUnliMmxrSWl3aVpHbDFJam9pSlhNaUxDSndhMmNpT2lJbGN5SXNJbTF2WkdWc0lqb2lKWE1pTENKaGNIQnVZVzFsSWpvaUpYTWlMQ0poY0hCMlpYSnphVzl1SWpvaUpYTWlMQ0p6ZVhOMlpYSnphVzl1SWpvaUpYTWlMQUdJbXRsZVNJNklpVnpJaXdpY0d4aGRHWnZjbTBpT2lKaGJtUnliMmxrSWl3aVpHbDFJam9pSlhNaUxDSnRZV01pT2lJbGN5SXNJblJwWkNJNklpVnpJaXdpZFcxcFpIUWlPaUlsY3lJc0ltMWhiblZtWVdOMGRYSmxJam9pSlhNaUxDSmtaWFpwWTJVaU9pSWxjeUlzSW5OcGJTSTZJaVZ6SWl3aWNHdG5Jam9pSlhNaUxDSnRiMlJsYkNJNklpVnpJaXdpWVhCd2RtVnljMmx2YmlJNklpVnpJaXdpWVhCd2JtRnRaU0k2SWlWeklnPUlZV2xrUFFNZkhObGNtbGhiRDBRWVc1a2NtOXBaRjlwWkE=";

    static {
        b = "9aj&#k81";
        c = "IaHR0cDovL2xvZ3MuYW1hcC5jb20vd3MvbG9nL3VwbG9hZD9wcm9kdWN0PSVzJnR5cGU9JXMmcGxhdGZvcm09JXMmY2hhbm5lbD0lcyZzaWduPSVz";
        b = "9aj&#k81";
        c = "IaHR0cDovL2xvZ3MuYW1hcC5jb20vd3MvbG9nL3VwbG9hZD9wcm9kdWN0PSVzJnR5cGU9JXMmcGxhdGZvcm09JXMmY2hhbm5lbD0lcyZzaWduPSVz";
    }

    public static v a() {
        try {
            return new a("aiu", BuildConfig.VERSION_NAME, "aiu").a(new String[0]).a();
        } catch (k e2) {
            ThrowableExtension.printStackTrace(e2);
            return null;
        }
    }

    public static byte[] a(int i) {
        if (d == null) {
            d = q.b(e);
        }
        byte[] bArr = new byte[4];
        System.arraycopy(d, i * 4, bArr, 0, 4);
        int i2 = ((bArr[0] & UnsignedBytes.MAX_VALUE) * 256) + (bArr[1] & UnsignedBytes.MAX_VALUE);
        int i3 = (bArr[3] & UnsignedBytes.MAX_VALUE) + ((bArr[2] & UnsignedBytes.MAX_VALUE) * 256);
        byte[] bArr2 = new byte[i3];
        System.arraycopy(d, i2, bArr2, 0, i3);
        return bArr2;
    }
}
