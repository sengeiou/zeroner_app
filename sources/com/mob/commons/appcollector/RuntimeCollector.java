package com.mob.commons.appcollector;

import android.content.Context;
import android.os.Build.VERSION;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.mob.commons.a;
import com.mob.commons.c;
import com.mob.commons.d;
import com.mob.commons.e;
import com.mob.tools.MobLog;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.R;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.litepal.util.Const.TableSchema;

public class RuntimeCollector {
    private static final String a = (VERSION.SDK_INT >= 16 ? "^u\\d+_a\\d+" : "^app_\\d+");
    private static RuntimeCollector b;
    /* access modifiers changed from: private */
    public Context c;
    private DeviceHelper d;

    public static synchronized void startCollector(Context context, String str) {
        synchronized (RuntimeCollector.class) {
            startCollector(context);
        }
    }

    public static synchronized void startCollector(Context context) {
        synchronized (RuntimeCollector.class) {
            if (b == null) {
                b = new RuntimeCollector(context);
                b.a();
            }
        }
    }

    private RuntimeCollector(Context context) {
        this.c = context.getApplicationContext();
        this.d = DeviceHelper.getInstance(context);
    }

    private void a() {
        AnonymousClass1 r0 = new Thread() {
            public void run() {
                e.a(new File(R.getCacheRoot(RuntimeCollector.this.c), "comm/locks/.rc_lock"), new Runnable() {
                    public void run() {
                        if (!d.a(RuntimeCollector.this.c, "comm/tags/.rcTag")) {
                            RuntimeCollector.this.b();
                        }
                    }
                });
            }
        };
        r0.setPriority(1);
        r0.start();
    }

    /* JADX WARNING: type inference failed for: r2v0 */
    /* JADX WARNING: type inference failed for: r1v2 */
    /* JADX WARNING: type inference failed for: r4v0 */
    /* JADX WARNING: type inference failed for: r1v3 */
    /* JADX WARNING: type inference failed for: r4v1 */
    /* JADX WARNING: type inference failed for: r2v2 */
    /* JADX WARNING: type inference failed for: r4v2, types: [java.lang.Process] */
    /* JADX WARNING: type inference failed for: r2v3 */
    /* JADX WARNING: type inference failed for: r5v0, types: [java.io.OutputStream] */
    /* JADX WARNING: type inference failed for: r4v3 */
    /* JADX WARNING: type inference failed for: r2v5 */
    /* JADX WARNING: type inference failed for: r4v4 */
    /* JADX WARNING: type inference failed for: r2v6 */
    /* JADX WARNING: type inference failed for: r2v7 */
    /* JADX WARNING: type inference failed for: r2v8 */
    /* JADX WARNING: type inference failed for: r4v5 */
    /* JADX WARNING: type inference failed for: r2v9 */
    /* JADX WARNING: type inference failed for: r2v12 */
    /* JADX WARNING: type inference failed for: r1v14 */
    /* JADX WARNING: type inference failed for: r4v6 */
    /* JADX WARNING: type inference failed for: r2v13 */
    /* JADX WARNING: type inference failed for: r4v7 */
    /* JADX WARNING: type inference failed for: r1v16 */
    /* JADX WARNING: type inference failed for: r1v17 */
    /* JADX WARNING: type inference failed for: r4v8 */
    /* JADX WARNING: type inference failed for: r1v18 */
    /* JADX WARNING: type inference failed for: r2v14 */
    /* JADX WARNING: type inference failed for: r2v15 */
    /* JADX WARNING: type inference failed for: r1v22, types: [java.lang.Process] */
    /* JADX WARNING: type inference failed for: r5v3, types: [java.io.OutputStream] */
    /* JADX WARNING: type inference failed for: r4v9 */
    /* JADX WARNING: type inference failed for: r1v23 */
    /* JADX WARNING: type inference failed for: r1v26, types: [java.lang.Process] */
    /* JADX WARNING: type inference failed for: r2v18, types: [java.io.OutputStream] */
    /* JADX WARNING: type inference failed for: r4v10 */
    /* JADX WARNING: type inference failed for: r2v19 */
    /* JADX WARNING: type inference failed for: r4v11 */
    /* JADX WARNING: type inference failed for: r2v20 */
    /* JADX WARNING: type inference failed for: r4v12 */
    /* JADX WARNING: type inference failed for: r4v13 */
    /* JADX WARNING: type inference failed for: r4v14 */
    /* JADX WARNING: type inference failed for: r4v15 */
    /* JADX WARNING: type inference failed for: r2v21 */
    /* JADX WARNING: type inference failed for: r4v16 */
    /* JADX WARNING: type inference failed for: r2v22 */
    /* JADX WARNING: type inference failed for: r4v17 */
    /* JADX WARNING: type inference failed for: r4v18 */
    /* JADX WARNING: type inference failed for: r2v23 */
    /* JADX WARNING: type inference failed for: r2v24 */
    /* JADX WARNING: type inference failed for: r4v19 */
    /* JADX WARNING: type inference failed for: r4v20 */
    /* JADX WARNING: type inference failed for: r1v27 */
    /* JADX WARNING: type inference failed for: r1v28 */
    /* JADX WARNING: type inference failed for: r2v25 */
    /* access modifiers changed from: private */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r4v1
      assigns: []
      uses: []
      mth insns count: 175
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1507)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1507)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1507)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0054 A[Catch:{ Throwable -> 0x018b }] */
    /* JADX WARNING: Unknown variable types count: 22 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void b() {
        /*
            r15 = this;
            r2 = 0
            r8 = 1
            java.io.File r9 = new java.io.File     // Catch:{ Throwable -> 0x0199 }
            android.content.Context r0 = r15.c     // Catch:{ Throwable -> 0x0199 }
            java.lang.String r0 = com.mob.tools.utils.R.getCacheRoot(r0)     // Catch:{ Throwable -> 0x0199 }
            java.lang.String r1 = "comm/dbs/.plst"
            r9.<init>(r0, r1)     // Catch:{ Throwable -> 0x0199 }
            java.io.File r0 = r9.getParentFile()     // Catch:{ Throwable -> 0x0199 }
            boolean r0 = r0.exists()     // Catch:{ Throwable -> 0x0199 }
            if (r0 != 0) goto L_0x0021
            java.io.File r0 = r9.getParentFile()     // Catch:{ Throwable -> 0x0199 }
            r0.mkdirs()     // Catch:{ Throwable -> 0x0199 }
        L_0x0021:
            java.lang.String r10 = r9.getAbsolutePath()     // Catch:{ Throwable -> 0x0199 }
            long r6 = r15.c()     // Catch:{ Throwable -> 0x0199 }
            android.content.Context r0 = r15.c     // Catch:{ Throwable -> 0x0199 }
            java.lang.String r1 = "comm/tags/.rcTag"
            com.mob.commons.d.b(r0, r1)     // Catch:{ Throwable -> 0x0199 }
            java.lang.Runtime r0 = java.lang.Runtime.getRuntime()     // Catch:{ Throwable -> 0x013a }
            java.lang.String r1 = "sh"
            java.lang.Process r1 = r0.exec(r1)     // Catch:{ Throwable -> 0x013a }
            java.io.OutputStream r2 = r1.getOutputStream()     // Catch:{ Throwable -> 0x01ad }
            r4 = r1
        L_0x0041:
            android.content.Context r0 = r15.c     // Catch:{ Throwable -> 0x0199 }
            java.lang.String r1 = "comm/tags/.rcTag"
            com.mob.commons.d.c(r0, r1)     // Catch:{ Throwable -> 0x0199 }
            r1 = r8
            r5 = r2
            r2 = r6
        L_0x004c:
            android.content.Context r0 = r15.c     // Catch:{ Throwable -> 0x018b }
            boolean r0 = com.mob.commons.a.b(r0)     // Catch:{ Throwable -> 0x018b }
            if (r0 == 0) goto L_0x01b6
            boolean r0 = r9.exists()     // Catch:{ Throwable -> 0x018b }
            if (r0 != 0) goto L_0x005d
            r9.createNewFile()     // Catch:{ Throwable -> 0x018b }
        L_0x005d:
            android.content.Context r0 = r15.c     // Catch:{ Throwable -> 0x018b }
            long r6 = com.mob.commons.a.a(r0)     // Catch:{ Throwable -> 0x018b }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x018b }
            r0.<init>()     // Catch:{ Throwable -> 0x018b }
            java.lang.String r11 = "top -d 0 -n 1 | grep -E -v 'root|shell|system' >> "
            java.lang.StringBuilder r0 = r0.append(r11)     // Catch:{ Throwable -> 0x018b }
            java.lang.StringBuilder r0 = r0.append(r10)     // Catch:{ Throwable -> 0x018b }
            java.lang.String r11 = " && echo \""
            java.lang.StringBuilder r0 = r0.append(r11)     // Catch:{ Throwable -> 0x018b }
            java.lang.String r11 = "======================"
            java.lang.StringBuilder r0 = r0.append(r11)     // Catch:{ Throwable -> 0x018b }
            java.lang.String r11 = "\" >> "
            java.lang.StringBuilder r0 = r0.append(r11)     // Catch:{ Throwable -> 0x018b }
            java.lang.StringBuilder r0 = r0.append(r10)     // Catch:{ Throwable -> 0x018b }
            java.lang.String r11 = "\n"
            java.lang.StringBuilder r0 = r0.append(r11)     // Catch:{ Throwable -> 0x018b }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x018b }
            java.lang.String r11 = "ascii"
            byte[] r0 = r0.getBytes(r11)     // Catch:{ Throwable -> 0x018b }
            r5.write(r0)     // Catch:{ Throwable -> 0x018b }
            if (r1 == 0) goto L_0x0146
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x018b }
            r0.<init>()     // Catch:{ Throwable -> 0x018b }
            java.lang.String r11 = "echo \""
            java.lang.StringBuilder r0 = r0.append(r11)     // Catch:{ Throwable -> 0x018b }
            java.lang.StringBuilder r0 = r0.append(r6)     // Catch:{ Throwable -> 0x018b }
            java.lang.String r11 = "_0\" >> "
            java.lang.StringBuilder r0 = r0.append(r11)     // Catch:{ Throwable -> 0x018b }
            java.lang.StringBuilder r0 = r0.append(r10)     // Catch:{ Throwable -> 0x018b }
            java.lang.String r11 = "\n"
            java.lang.StringBuilder r0 = r0.append(r11)     // Catch:{ Throwable -> 0x018b }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x018b }
            r1 = 0
        L_0x00ca:
            java.lang.String r11 = "ascii"
            byte[] r0 = r0.getBytes(r11)     // Catch:{ Throwable -> 0x018b }
            r5.write(r0)     // Catch:{ Throwable -> 0x018b }
            r5.flush()     // Catch:{ Throwable -> 0x018b }
            int r0 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r0 < 0) goto L_0x01b6
            java.lang.String r0 = "exit\n"
            java.lang.String r6 = "ascii"
            byte[] r0 = r0.getBytes(r6)     // Catch:{ Throwable -> 0x018b }
            r5.write(r0)     // Catch:{ Throwable -> 0x018b }
            r5.flush()     // Catch:{ Throwable -> 0x018b }
            r5.close()     // Catch:{ Throwable -> 0x018b }
            r4.waitFor()     // Catch:{ Throwable -> 0x018b }
            r4.destroy()     // Catch:{ Throwable -> 0x018b }
            boolean r0 = r15.a(r10)     // Catch:{ Throwable -> 0x018b }
            if (r0 == 0) goto L_0x01b2
            long r0 = r15.d()     // Catch:{ Throwable -> 0x018b }
            r6 = 0
            int r6 = (r0 > r6 ? 1 : (r0 == r6 ? 0 : -1))
            if (r6 <= 0) goto L_0x01af
        L_0x0104:
            r2 = r8
            r6 = r0
        L_0x0106:
            android.content.Context r0 = r15.c     // Catch:{ Throwable -> 0x019b }
            java.lang.String r1 = "comm/tags/.rcTag"
            com.mob.commons.d.b(r0, r1)     // Catch:{ Throwable -> 0x019b }
            java.lang.Runtime r0 = java.lang.Runtime.getRuntime()     // Catch:{ Throwable -> 0x017f }
            java.lang.String r1 = "sh"
            java.lang.Process r1 = r0.exec(r1)     // Catch:{ Throwable -> 0x017f }
            java.io.OutputStream r5 = r1.getOutputStream()     // Catch:{ Throwable -> 0x01ab }
            r4 = r1
            r1 = r5
        L_0x011f:
            android.content.Context r0 = r15.c     // Catch:{ Throwable -> 0x01a4 }
            java.lang.String r3 = "comm/tags/.rcTag"
            com.mob.commons.d.c(r0, r3)     // Catch:{ Throwable -> 0x01a4 }
            r14 = r2
            r2 = r1
            r1 = r14
        L_0x012a:
            android.content.Context r0 = r15.c     // Catch:{ Throwable -> 0x01a9 }
            int r0 = com.mob.commons.a.c(r0)     // Catch:{ Throwable -> 0x01a9 }
            int r0 = r0 * 1000
            long r12 = (long) r0     // Catch:{ Throwable -> 0x01a9 }
            java.lang.Thread.sleep(r12)     // Catch:{ Throwable -> 0x01a9 }
            r5 = r2
            r2 = r6
            goto L_0x004c
        L_0x013a:
            r0 = move-exception
            r1 = r2
        L_0x013c:
            com.mob.tools.log.NLog r3 = com.mob.tools.MobLog.getInstance()     // Catch:{ Throwable -> 0x0199 }
            r3.w(r0)     // Catch:{ Throwable -> 0x0199 }
            r4 = r1
            goto L_0x0041
        L_0x0146:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x018b }
            r0.<init>()     // Catch:{ Throwable -> 0x018b }
            java.lang.String r11 = "echo \""
            java.lang.StringBuilder r0 = r0.append(r11)     // Catch:{ Throwable -> 0x018b }
            java.lang.StringBuilder r0 = r0.append(r6)     // Catch:{ Throwable -> 0x018b }
            java.lang.String r11 = "_"
            java.lang.StringBuilder r0 = r0.append(r11)     // Catch:{ Throwable -> 0x018b }
            android.content.Context r11 = r15.c     // Catch:{ Throwable -> 0x018b }
            int r11 = com.mob.commons.a.c(r11)     // Catch:{ Throwable -> 0x018b }
            java.lang.StringBuilder r0 = r0.append(r11)     // Catch:{ Throwable -> 0x018b }
            java.lang.String r11 = "\" >> "
            java.lang.StringBuilder r0 = r0.append(r11)     // Catch:{ Throwable -> 0x018b }
            java.lang.StringBuilder r0 = r0.append(r10)     // Catch:{ Throwable -> 0x018b }
            java.lang.String r11 = "\n"
            java.lang.StringBuilder r0 = r0.append(r11)     // Catch:{ Throwable -> 0x018b }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x018b }
            goto L_0x00ca
        L_0x017f:
            r0 = move-exception
            r1 = r4
        L_0x0181:
            com.mob.tools.log.NLog r3 = com.mob.tools.MobLog.getInstance()     // Catch:{ Throwable -> 0x019f }
            r3.w(r0)     // Catch:{ Throwable -> 0x019f }
            r4 = r1
            r1 = r5
            goto L_0x011f
        L_0x018b:
            r0 = move-exception
            r6 = r2
            r2 = r5
        L_0x018e:
            com.mob.tools.log.NLog r3 = com.mob.tools.MobLog.getInstance()     // Catch:{ Throwable -> 0x0199 }
            r3.d(r0)     // Catch:{ Throwable -> 0x0199 }
            r5 = r2
            r2 = r6
            goto L_0x004c
        L_0x0199:
            r0 = move-exception
            return
        L_0x019b:
            r0 = move-exception
            r1 = r2
            r2 = r5
            goto L_0x018e
        L_0x019f:
            r0 = move-exception
            r4 = r1
            r1 = r2
            r2 = r5
            goto L_0x018e
        L_0x01a4:
            r0 = move-exception
            r14 = r2
            r2 = r1
            r1 = r14
            goto L_0x018e
        L_0x01a9:
            r0 = move-exception
            goto L_0x018e
        L_0x01ab:
            r0 = move-exception
            goto L_0x0181
        L_0x01ad:
            r0 = move-exception
            goto L_0x013c
        L_0x01af:
            r0 = r2
            goto L_0x0104
        L_0x01b2:
            r6 = r2
            r2 = r1
            goto L_0x0106
        L_0x01b6:
            r6 = r2
            r2 = r5
            goto L_0x012a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mob.commons.appcollector.RuntimeCollector.b():void");
    }

    private long c() {
        long a2 = a.a(this.c);
        try {
            File file = new File(R.getCacheRoot(this.c), "comm/dbs/.nulplt");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (file.exists()) {
                DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file));
                long readLong = dataInputStream.readLong();
                dataInputStream.close();
                return readLong;
            }
            file.createNewFile();
            d();
            return a2 + 86400000;
        } catch (Throwable th) {
            MobLog.getInstance().d(th);
            return a2 + 86400000;
        }
    }

    private long d() {
        long a2 = a.a(this.c) + 86400000;
        try {
            File file = new File(R.getCacheRoot(this.c), "comm/dbs/.nulplt");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(file));
            dataOutputStream.writeLong(a2);
            dataOutputStream.flush();
            dataOutputStream.close();
            return a2;
        } catch (Throwable th) {
            MobLog.getInstance().d(th);
            return 0;
        }
    }

    private boolean a(String str) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        a(str, arrayList, arrayList2);
        a(a((HashMap<String, String>[][]) a(a(arrayList), arrayList), arrayList2), arrayList2);
        return b(str);
    }

    private void a(String str, ArrayList<ArrayList<HashMap<String, String>>> arrayList, ArrayList<long[]> arrayList2) {
        try {
            HashMap e = e();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(str), "utf-8"));
            String readLine = bufferedReader.readLine();
            for (int i = 0; i < 7; i++) {
                readLine = bufferedReader.readLine();
            }
            ArrayList arrayList3 = new ArrayList();
            while (readLine != null) {
                if ("======================".equals(readLine)) {
                    try {
                        String[] split = bufferedReader.readLine().split("_");
                        long[] jArr = {Long.valueOf(split[0]).longValue(), Long.valueOf(split[1]).longValue()};
                        arrayList.add(arrayList3);
                        arrayList2.add(jArr);
                    } catch (Throwable th) {
                    }
                    arrayList3 = new ArrayList();
                    for (int i2 = 0; i2 < 7; i2++) {
                        bufferedReader.readLine();
                    }
                } else if (readLine.length() > 0) {
                    a(readLine, e, arrayList3);
                }
                readLine = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (Throwable th2) {
            MobLog.getInstance().d(th2);
        }
    }

    private void a(String str, HashMap<String, String[]> hashMap, ArrayList<HashMap<String, String>> arrayList) {
        String[] split = str.replaceAll(" +", MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).split(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
        if (split != null && split.length >= 10) {
            String str2 = split[split.length - 1];
            if (split[split.length - 2].matches(a) && !"top".equals(str2)) {
                String[] strArr = (String[]) hashMap.get(str2);
                if (strArr != null) {
                    HashMap hashMap2 = new HashMap();
                    hashMap2.put("pkg", str2);
                    hashMap2.put(TableSchema.COLUMN_NAME, strArr[0]);
                    hashMap2.put("version", strArr[1]);
                    hashMap2.put("pcy", split[split.length - 3]);
                    arrayList.add(hashMap2);
                }
            }
        }
    }

    private HashMap<String, String[]> e() {
        ArrayList installedApp = this.d.getInstalledApp(false);
        HashMap<String, String[]> hashMap = new HashMap<>();
        Iterator it = installedApp.iterator();
        while (it.hasNext()) {
            HashMap hashMap2 = (HashMap) it.next();
            hashMap.put((String) hashMap2.get("pkg"), new String[]{(String) hashMap2.get(TableSchema.COLUMN_NAME), (String) hashMap2.get("version")});
        }
        return hashMap;
    }

    private HashMap<String, Integer> a(ArrayList<ArrayList<HashMap<String, String>>> arrayList) {
        HashMap<String, Integer> hashMap = new HashMap<>();
        Iterator it = arrayList.iterator();
        int i = 0;
        while (it.hasNext()) {
            Iterator it2 = ((ArrayList) it.next()).iterator();
            int i2 = i;
            while (it2.hasNext()) {
                HashMap hashMap2 = (HashMap) it2.next();
                String str = ((String) hashMap2.get("pkg")) + ":" + ((String) hashMap2.get("version"));
                if (!hashMap.containsKey(str)) {
                    hashMap.put(str, Integer.valueOf(i2));
                    i2++;
                }
            }
            i = i2;
        }
        return hashMap;
    }

    private HashMap<String, String>[][] a(HashMap<String, Integer> hashMap, ArrayList<ArrayList<HashMap<String, String>>> arrayList) {
        HashMap<String, String>[][] hashMapArr = (HashMap[][]) Array.newInstance(HashMap.class, new int[]{hashMap.size(), arrayList.size()});
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            ArrayList arrayList2 = (ArrayList) arrayList.get(i);
            int size2 = arrayList2.size();
            for (int i2 = 0; i2 < size2; i2++) {
                HashMap<String, String> hashMap2 = (HashMap) arrayList2.get(i2);
                hashMapArr[((Integer) hashMap.get(((String) hashMap2.get("pkg")) + ":" + ((String) hashMap2.get("version")))).intValue()][i] = hashMap2;
            }
        }
        return hashMapArr;
    }

    private ArrayList<HashMap<String, Object>> a(HashMap<String, String>[][] hashMapArr, ArrayList<long[]> arrayList) {
        ArrayList<HashMap<String, Object>> arrayList2 = new ArrayList<>(hashMapArr.length);
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= hashMapArr.length) {
                return arrayList2;
            }
            HashMap hashMap = new HashMap();
            hashMap.put("runtimes", Long.valueOf(0));
            hashMap.put("fg", Integer.valueOf(0));
            hashMap.put("bg", Integer.valueOf(0));
            hashMap.put("empty", Integer.valueOf(0));
            arrayList2.add(hashMap);
            HashMap<String, String>[] hashMapArr2 = hashMapArr[i2];
            int length = hashMapArr2.length - 1;
            while (length >= 0) {
                if (hashMapArr2[length] != null) {
                    hashMap.put("runtimes", Long.valueOf((length == 0 ? 0 : ((long[]) arrayList.get(length))[1]) + ((Long) R.forceCast(hashMap.get("runtimes"), Long.valueOf(0))).longValue()));
                    if ("fg".equals(hashMapArr2[length].get("pcy"))) {
                        hashMap.put("fg", Integer.valueOf(((Integer) R.forceCast(hashMap.get("fg"), Integer.valueOf(0))).intValue() + 1));
                    } else if ("bg".equals(hashMapArr2[length].get("pcy"))) {
                        hashMap.put("bg", Integer.valueOf(((Integer) R.forceCast(hashMap.get("bg"), Integer.valueOf(0))).intValue() + 1));
                    } else {
                        hashMap.put("empty", Integer.valueOf(((Integer) R.forceCast(hashMap.get("empty"), Integer.valueOf(0))).intValue() + 1));
                    }
                    hashMap.put("pkg", hashMapArr2[length].get("pkg"));
                    hashMap.put(TableSchema.COLUMN_NAME, hashMapArr2[length].get(TableSchema.COLUMN_NAME));
                    hashMap.put("version", hashMapArr2[length].get("version"));
                }
                length--;
            }
            i = i2 + 1;
        }
    }

    private void a(ArrayList<HashMap<String, Object>> arrayList, ArrayList<long[]> arrayList2) {
        HashMap hashMap = new HashMap();
        hashMap.put("type", "APP_RUNTIMES");
        hashMap.put("list", arrayList);
        hashMap.put("datatime", Long.valueOf(a.a(this.c)));
        hashMap.put("recordat", Long.valueOf(((long[]) arrayList2.get(0))[0]));
        long j = 0;
        for (int i = 1; i < arrayList2.size(); i++) {
            j += ((long[]) arrayList2.get(i))[1];
        }
        hashMap.put("sdk_runtime_len", Long.valueOf(j));
        hashMap.put("top_count", Integer.valueOf(arrayList2.size()));
        c.a(this.c).a(hashMap);
    }

    private boolean b(String str) {
        try {
            File file = new File(str);
            file.delete();
            file.createNewFile();
            return true;
        } catch (Throwable th) {
            MobLog.getInstance().d(th);
            return false;
        }
    }
}
