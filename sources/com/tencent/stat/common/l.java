package com.tencent.stat.common;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

class l {
    l() {
    }

    static int a() {
        try {
            return new File("/sys/devices/system/cpu/").listFiles(new m()).length;
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return 1;
        }
    }

    static int b() {
        int i = 0;
        String str = "";
        try {
            InputStream inputStream = new ProcessBuilder(new String[]{"/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"}).start().getInputStream();
            byte[] bArr = new byte[24];
            while (inputStream.read(bArr) != -1) {
                str = str + new String(bArr);
            }
            inputStream.close();
            String trim = str.trim();
            if (trim.length() > 0) {
                i = Integer.valueOf(trim).intValue();
            }
        } catch (Exception e) {
            k.f.e(e);
        }
        return i * 1000;
    }

    static int c() {
        int i = 0;
        String str = "";
        try {
            InputStream inputStream = new ProcessBuilder(new String[]{"/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq"}).start().getInputStream();
            byte[] bArr = new byte[24];
            while (inputStream.read(bArr) != -1) {
                str = str + new String(bArr);
            }
            inputStream.close();
            String trim = str.trim();
            if (trim.length() > 0) {
                i = Integer.valueOf(trim).intValue();
            }
        } catch (IOException e) {
            k.f.e((Exception) e);
        }
        return i * 1000;
    }

    static String d() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/proc/cpuinfo"));
            String[] split = bufferedReader.readLine().split(":\\s+", 2);
            for (int i = 0; i < split.length; i++) {
            }
            bufferedReader.close();
            return split[1];
        } catch (Throwable th) {
            k.f.e((Object) th);
            return "";
        }
    }
}
