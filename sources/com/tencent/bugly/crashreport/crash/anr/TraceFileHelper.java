package com.tencent.bugly.crashreport.crash.anr;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.tencent.bugly.proguard.an;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/* compiled from: BUGLY */
public class TraceFileHelper {

    /* compiled from: BUGLY */
    public static class a {
        public long a;
        public String b;
        public long c;
        public Map<String, String[]> d;
    }

    /* compiled from: BUGLY */
    public interface b {
        boolean a(long j);

        boolean a(long j, long j2, String str);

        boolean a(String str, int i, String str2, String str3, boolean z);

        boolean a(String str, long j, long j2);
    }

    public static a readTargetDumpInfo(String procName, String traceFilePath, final boolean needThread) {
        if (procName == null || traceFilePath == null) {
            return null;
        }
        final a aVar = new a();
        readTraceFile(traceFilePath, new b() {
            public boolean a(String str, long j, long j2) {
                return true;
            }

            public boolean a(String str, int i, String str2, String str3, boolean z) {
                an.c("new thread %s", str);
                if (aVar.a > 0 && aVar.c > 0 && aVar.b != null) {
                    if (aVar.d == null) {
                        aVar.d = new HashMap();
                    }
                    aVar.d.put(str, new String[]{str2, str3, "" + i});
                }
                return true;
            }

            public boolean a(long j, long j2, String str) {
                an.c("new process %s", str);
                if (!str.equals(str)) {
                    return true;
                }
                aVar.a = j;
                aVar.b = str;
                aVar.c = j2;
                if (!needThread) {
                    return false;
                }
                return true;
            }

            public boolean a(long j) {
                an.c("process end %d", Long.valueOf(j));
                if (aVar.a <= 0 || aVar.c <= 0 || aVar.b == null) {
                    return true;
                }
                return false;
            }
        });
        if (aVar.a <= 0 || aVar.c <= 0 || aVar.b == null) {
            return null;
        }
        return aVar;
    }

    public static a readFirstDumpInfo(String traceFilePath, final boolean needThread) {
        if (traceFilePath == null) {
            an.e("path:%s", traceFilePath);
            return null;
        }
        final a aVar = new a();
        readTraceFile(traceFilePath, new b() {
            public boolean a(String str, long j, long j2) {
                return true;
            }

            public boolean a(String str, int i, String str2, String str3, boolean z) {
                an.c("new thread %s", str);
                if (aVar.d == null) {
                    aVar.d = new HashMap();
                }
                aVar.d.put(str, new String[]{str2, str3, "" + i});
                return true;
            }

            public boolean a(long j, long j2, String str) {
                an.c("new process %s", str);
                aVar.a = j;
                aVar.b = str;
                aVar.c = j2;
                if (!needThread) {
                    return false;
                }
                return true;
            }

            public boolean a(long j) {
                an.c("process end %d", Long.valueOf(j));
                return false;
            }
        });
        if (aVar.a > 0 && aVar.c > 0 && aVar.b != null) {
            return aVar;
        }
        an.e("first dump error %s", aVar.a + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + aVar.c + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + aVar.b);
        return null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:117:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:118:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:119:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:129:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0185, code lost:
        if (r14.a(java.lang.Long.parseLong(r0[1].toString().split("\\s")[2])) != false) goto L_0x0051;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x0187, code lost:
        if (r7 == null) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:?, code lost:
        r7.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x018e, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x0193, code lost:
        if (com.tencent.bugly.proguard.an.a(r0) == false) goto L_0x0195;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x0195, code lost:
        com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0);
     */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x01fd A[SYNTHETIC, Splitter:B:83:0x01fd] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void readTraceFile(java.lang.String r13, com.tencent.bugly.crashreport.crash.anr.TraceFileHelper.b r14) {
        /*
            if (r13 == 0) goto L_0x0004
            if (r14 != 0) goto L_0x0005
        L_0x0004:
            return
        L_0x0005:
            java.io.File r6 = new java.io.File
            r6.<init>(r13)
            boolean r0 = r6.exists()
            if (r0 == 0) goto L_0x0004
            long r2 = r6.lastModified()
            long r4 = r6.length()
            r0 = r14
            r1 = r13
            boolean r0 = r0.a(r1, r2, r4)
            if (r0 == 0) goto L_0x0004
            r1 = 0
            java.io.BufferedReader r7 = new java.io.BufferedReader     // Catch:{ Exception -> 0x01ad, all -> 0x01f9 }
            java.io.FileReader r0 = new java.io.FileReader     // Catch:{ Exception -> 0x01ad, all -> 0x01f9 }
            r0.<init>(r6)     // Catch:{ Exception -> 0x01ad, all -> 0x01f9 }
            r7.<init>(r0)     // Catch:{ Exception -> 0x01ad, all -> 0x01f9 }
            java.lang.String r0 = "-{5}\\spid\\s\\d+\\sat\\s\\d+-\\d+-\\d+\\s\\d{2}:\\d{2}:\\d{2}\\s-{5}"
            java.util.regex.Pattern r8 = java.util.regex.Pattern.compile(r0)     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            java.lang.String r0 = "-{5}\\send\\s\\d+\\s-{5}"
            java.util.regex.Pattern r9 = java.util.regex.Pattern.compile(r0)     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            java.lang.String r0 = "Cmd\\sline:\\s(\\S+)"
            java.util.regex.Pattern r10 = java.util.regex.Pattern.compile(r0)     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            java.lang.String r0 = "\".+\"\\s(daemon\\s){0,1}prio=\\d+\\stid=\\d+\\s.*"
            java.util.regex.Pattern r11 = java.util.regex.Pattern.compile(r0)     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            java.text.SimpleDateFormat r12 = new java.text.SimpleDateFormat     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            java.lang.String r0 = "yyyy-MM-dd HH:mm:ss"
            java.util.Locale r1 = java.util.Locale.US     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            r12.<init>(r0, r1)     // Catch:{ Exception -> 0x0211, all -> 0x020c }
        L_0x0051:
            r0 = 1
            java.util.regex.Pattern[] r0 = new java.util.regex.Pattern[r0]     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            r1 = 0
            r0[r1] = r8     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            java.lang.Object[] r0 = a(r7, r0)     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            if (r0 == 0) goto L_0x019a
            r1 = 1
            r0 = r0[r1]     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            java.lang.String r1 = "\\s"
            java.lang.String[] r0 = r0.split(r1)     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            r1 = 2
            r1 = r0[r1]     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            long r2 = java.lang.Long.parseLong(r1)     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            r1.<init>()     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            r4 = 4
            r4 = r0[r4]     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            java.lang.StringBuilder r1 = r1.append(r4)     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            java.lang.String r4 = " "
            java.lang.StringBuilder r1 = r1.append(r4)     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            r4 = 5
            r0 = r0[r4]     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            java.lang.StringBuilder r0 = r1.append(r0)     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            java.util.Date r0 = r12.parse(r0)     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            long r4 = r0.getTime()     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            r0 = 1
            java.util.regex.Pattern[] r0 = new java.util.regex.Pattern[r0]     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            r1 = 0
            r0[r1] = r10     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            java.lang.Object[] r0 = a(r7, r0)     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            if (r0 != 0) goto L_0x00b7
            if (r7 == 0) goto L_0x0004
            r7.close()     // Catch:{ IOException -> 0x00ab }
            goto L_0x0004
        L_0x00ab:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.an.a(r0)
            if (r1 != 0) goto L_0x0004
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x0004
        L_0x00b7:
            r1 = 1
            r0 = r0[r1]     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            java.util.regex.Matcher r0 = r10.matcher(r0)     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            r0.find()     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            r1 = 1
            r0.group(r1)     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            r1 = 1
            java.lang.String r6 = r0.group(r1)     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            r1 = r14
            boolean r0 = r1.a(r2, r4, r6)     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            if (r0 != 0) goto L_0x00e8
            if (r7 == 0) goto L_0x0004
            r7.close()     // Catch:{ IOException -> 0x00dc }
            goto L_0x0004
        L_0x00dc:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.an.a(r0)
            if (r1 != 0) goto L_0x0004
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x0004
        L_0x00e8:
            r0 = 2
            java.util.regex.Pattern[] r0 = new java.util.regex.Pattern[r0]     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            r1 = 0
            r0[r1] = r11     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            r1 = 1
            r0[r1] = r9     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            java.lang.Object[] r0 = a(r7, r0)     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            if (r0 == 0) goto L_0x0051
            r1 = 0
            r1 = r0[r1]     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            if (r1 != r11) goto L_0x016c
            r1 = 1
            r0 = r0[r1]     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            java.lang.String r1 = "\".+\""
            java.util.regex.Pattern r1 = java.util.regex.Pattern.compile(r1)     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            java.util.regex.Matcher r1 = r1.matcher(r0)     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            r1.find()     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            java.lang.String r1 = r1.group()     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            r2 = 1
            int r3 = r1.length()     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            int r3 = r3 + -1
            java.lang.String r1 = r1.substring(r2, r3)     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            java.lang.String r2 = "NATIVE"
            boolean r5 = r0.contains(r2)     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            java.lang.String r2 = "tid=\\d+"
            java.util.regex.Pattern r2 = java.util.regex.Pattern.compile(r2)     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            java.util.regex.Matcher r0 = r2.matcher(r0)     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            r0.find()     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            java.lang.String r0 = r0.group()     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            java.lang.String r2 = "="
            int r2 = r0.indexOf(r2)     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            int r2 = r2 + 1
            java.lang.String r0 = r0.substring(r2)     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            int r2 = java.lang.Integer.parseInt(r0)     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            java.lang.String r3 = a(r7)     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            java.lang.String r4 = b(r7)     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            r0 = r14
            boolean r0 = r0.a(r1, r2, r3, r4, r5)     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            if (r0 != 0) goto L_0x00e8
            if (r7 == 0) goto L_0x0004
            r7.close()     // Catch:{ IOException -> 0x0160 }
            goto L_0x0004
        L_0x0160:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.an.a(r0)
            if (r1 != 0) goto L_0x0004
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x0004
        L_0x016c:
            r1 = 1
            r0 = r0[r1]     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            java.lang.String r1 = "\\s"
            java.lang.String[] r0 = r0.split(r1)     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            r1 = 2
            r0 = r0[r1]     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            long r0 = java.lang.Long.parseLong(r0)     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            boolean r0 = r14.a(r0)     // Catch:{ Exception -> 0x0211, all -> 0x020c }
            if (r0 != 0) goto L_0x0051
            if (r7 == 0) goto L_0x0004
            r7.close()     // Catch:{ IOException -> 0x018e }
            goto L_0x0004
        L_0x018e:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.an.a(r0)
            if (r1 != 0) goto L_0x0004
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x0004
        L_0x019a:
            if (r7 == 0) goto L_0x0004
            r7.close()     // Catch:{ IOException -> 0x01a1 }
            goto L_0x0004
        L_0x01a1:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.an.a(r0)
            if (r1 != 0) goto L_0x0004
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x0004
        L_0x01ad:
            r0 = move-exception
        L_0x01ae:
            boolean r2 = com.tencent.bugly.proguard.an.a(r0)     // Catch:{ all -> 0x020e }
            if (r2 != 0) goto L_0x01b7
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)     // Catch:{ all -> 0x020e }
        L_0x01b7:
            java.lang.String r2 = "trace open fail:%s : %s"
            r3 = 2
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x020e }
            r4 = 0
            java.lang.Class r5 = r0.getClass()     // Catch:{ all -> 0x020e }
            java.lang.String r5 = r5.getName()     // Catch:{ all -> 0x020e }
            r3[r4] = r5     // Catch:{ all -> 0x020e }
            r4 = 1
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x020e }
            r5.<init>()     // Catch:{ all -> 0x020e }
            java.lang.String r6 = ""
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ all -> 0x020e }
            java.lang.String r0 = r0.getMessage()     // Catch:{ all -> 0x020e }
            java.lang.StringBuilder r0 = r5.append(r0)     // Catch:{ all -> 0x020e }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x020e }
            r3[r4] = r0     // Catch:{ all -> 0x020e }
            com.tencent.bugly.proguard.an.d(r2, r3)     // Catch:{ all -> 0x020e }
            if (r1 == 0) goto L_0x0004
            r1.close()     // Catch:{ IOException -> 0x01ed }
            goto L_0x0004
        L_0x01ed:
            r0 = move-exception
            boolean r1 = com.tencent.bugly.proguard.an.a(r0)
            if (r1 != 0) goto L_0x0004
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x0004
        L_0x01f9:
            r0 = move-exception
            r7 = r1
        L_0x01fb:
            if (r7 == 0) goto L_0x0200
            r7.close()     // Catch:{ IOException -> 0x0201 }
        L_0x0200:
            throw r0
        L_0x0201:
            r1 = move-exception
            boolean r2 = com.tencent.bugly.proguard.an.a(r1)
            if (r2 != 0) goto L_0x0200
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x0200
        L_0x020c:
            r0 = move-exception
            goto L_0x01fb
        L_0x020e:
            r0 = move-exception
            r7 = r1
            goto L_0x01fb
        L_0x0211:
            r0 = move-exception
            r1 = r7
            goto L_0x01ae
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.crash.anr.TraceFileHelper.readTraceFile(java.lang.String, com.tencent.bugly.crashreport.crash.anr.TraceFileHelper$b):void");
    }

    protected static Object[] a(BufferedReader bufferedReader, Pattern... patternArr) throws IOException {
        if (bufferedReader == null || patternArr == null) {
            return null;
        }
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine == null) {
                return null;
            }
            int length = patternArr.length;
            int i = 0;
            while (true) {
                if (i < length) {
                    Pattern pattern = patternArr[i];
                    if (pattern.matcher(readLine).matches()) {
                        return new Object[]{pattern, readLine};
                    }
                    i++;
                }
            }
        }
    }

    protected static String a(BufferedReader bufferedReader) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 3; i++) {
            String readLine = bufferedReader.readLine();
            if (readLine == null) {
                return null;
            }
            stringBuffer.append(readLine + "\n");
        }
        return stringBuffer.toString();
    }

    protected static String b(BufferedReader bufferedReader) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine != null && readLine.trim().length() > 0) {
                stringBuffer.append(readLine + "\n");
            }
        }
        return stringBuffer.toString();
    }
}
