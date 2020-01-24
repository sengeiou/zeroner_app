package com.tencent.wxop.stat.common;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

class m {
    static int a() {
        try {
            return new File("/sys/devices/system/cpu/").listFiles(new n()).length;
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
            l.k.e((Throwable) e);
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
        } catch (Throwable th) {
            l.k.e(th);
        }
        return i * 1000;
    }

    static String d() {
        String[] strArr = {"", ""};
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/proc/cpuinfo"), 8192);
            String[] split = bufferedReader.readLine().split("\\s+");
            for (int i = 2; i < split.length; i++) {
                strArr[0] = strArr[0] + split[i] + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR;
            }
            bufferedReader.close();
        } catch (IOException e) {
        }
        return strArr[0];
    }
}
