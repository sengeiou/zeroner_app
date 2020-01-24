package com.mob.commons.appcollector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.TextUtils;
import com.mob.commons.a;
import com.mob.commons.c;
import com.mob.commons.e;
import com.mob.tools.MobHandlerThread;
import com.mob.tools.MobLog;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.Hashon;
import com.mob.tools.utils.R;
import com.tencent.tinker.loader.hotplug.EnvConsts;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import no.nordicsemi.android.dfu.internal.scanner.BootloaderScanner;

public class PackageCollector {
    private static PackageCollector b;
    private final String[] a = {"android.intent.action.PACKAGE_ADDED", "android.intent.action.PACKAGE_CHANGED", "android.intent.action.PACKAGE_REMOVED", "android.intent.action.PACKAGE_REPLACED"};
    /* access modifiers changed from: private */
    public Context c;
    private DeviceHelper d;
    private Hashon e;
    /* access modifiers changed from: private */
    public Handler f;

    public static synchronized boolean register(Context context, String str) {
        synchronized (PackageCollector.class) {
            startCollector(context);
        }
        return true;
    }

    public static synchronized void startCollector(Context context) {
        synchronized (PackageCollector.class) {
            if (b == null) {
                b = new PackageCollector(context);
                b.a();
            }
        }
    }

    private PackageCollector(Context context) {
        this.c = context.getApplicationContext();
        this.d = DeviceHelper.getInstance(this.c);
        this.e = new Hashon();
    }

    private void a() {
        AnonymousClass1 r0 = new MobHandlerThread() {
            public void run() {
                e.a(new File(R.getCacheRoot(PackageCollector.this.c), "comm/locks/.pkg_lock"), new Runnable() {
                    public void run() {
                        if (a.d(PackageCollector.this.c)) {
                            PackageCollector.this.b();
                        }
                        PackageCollector.this.e();
                        AnonymousClass1.this.a();
                    }
                });
            }

            /* access modifiers changed from: private */
            public void a() {
                super.run();
            }
        };
        r0.start();
        this.f = new Handler(r0.getLooper(), new Callback() {
            public boolean handleMessage(Message message) {
                PackageCollector.this.f();
                return false;
            }
        });
    }

    /* access modifiers changed from: private */
    public void b() {
        ArrayList c2 = c();
        if (c2 == null || c2.isEmpty()) {
            ArrayList installedApp = this.d.getInstalledApp(false);
            a("APPS_ALL", installedApp);
            a(installedApp);
            a(a.a(this.c) + (a.e(this.c) * 1000));
            return;
        }
        long a2 = a.a(this.c);
        if (a2 >= d()) {
            ArrayList installedApp2 = this.d.getInstalledApp(false);
            a("APPS_ALL", installedApp2);
            a(installedApp2);
            a(a2 + (a.e(this.c) * 1000));
            return;
        }
        f();
    }

    private ArrayList<HashMap<String, String>> c() {
        File file = new File(R.getCacheRoot(this.c), "comm/dbs/.al");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (file.exists()) {
            try {
                ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(file)), "utf-8"));
                for (String readLine = bufferedReader.readLine(); readLine != null; readLine = bufferedReader.readLine()) {
                    HashMap fromJson = this.e.fromJson(readLine);
                    if (fromJson != null) {
                        arrayList.add(fromJson);
                    }
                }
                bufferedReader.close();
                return arrayList;
            } catch (Throwable th) {
                MobLog.getInstance().d(th);
            }
        }
        return new ArrayList<>();
    }

    private void a(String str, ArrayList<HashMap<String, String>> arrayList) {
        HashMap hashMap = new HashMap();
        hashMap.put("type", str);
        hashMap.put("list", arrayList);
        hashMap.put("datetime", Long.valueOf(a.a(this.c)));
        c.a(this.c).a(hashMap);
    }

    private void a(ArrayList<HashMap<String, String>> arrayList) {
        File file = new File(R.getCacheRoot(this.c), "comm/dbs/.al");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new GZIPOutputStream(new FileOutputStream(file)), "utf-8");
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                outputStreamWriter.append(this.e.fromHashMap((HashMap) it.next())).append(10);
            }
            outputStreamWriter.flush();
            outputStreamWriter.close();
        } catch (Throwable th) {
            MobLog.getInstance().d(th);
        }
    }

    private void a(long j) {
        File file = new File(R.getCacheRoot(this.c), "comm/dbs/.nulal");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(file));
            dataOutputStream.writeLong(j);
            dataOutputStream.flush();
            dataOutputStream.close();
        } catch (Throwable th) {
            MobLog.getInstance().d(th);
        }
    }

    private long d() {
        File file = new File(R.getCacheRoot(this.c), "comm/dbs/.nulal");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (file.exists()) {
            try {
                DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file));
                long readLong = dataInputStream.readLong();
                dataInputStream.close();
                return readLong;
            } catch (Throwable th) {
                MobLog.getInstance().d(th);
            }
        }
        return 0;
    }

    /* access modifiers changed from: private */
    public void e() {
        IntentFilter intentFilter = new IntentFilter();
        for (String addAction : b.a) {
            intentFilter.addAction(addAction);
        }
        intentFilter.addDataScheme(EnvConsts.PACKAGE_MANAGER_SRVNAME);
        this.c.registerReceiver(new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String str = null;
                if (intent != null) {
                    str = intent.getAction();
                }
                if (PackageCollector.this.a(str) && PackageCollector.this.f != null) {
                    PackageCollector.this.f.removeMessages(1);
                    PackageCollector.this.f.sendEmptyMessageDelayed(1, BootloaderScanner.TIMEOUT);
                }
            }
        }, intentFilter);
    }

    /* access modifiers changed from: private */
    public boolean a(String str) {
        for (String equals : this.a) {
            if (equals.equals(str)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: private */
    public void f() {
        ArrayList c2 = c();
        ArrayList installedApp = this.d.getInstalledApp(false);
        if (c2 == null || c2.isEmpty()) {
            a("APPS_ALL", installedApp);
            a(installedApp);
            a(a.a(this.c) + (a.e(this.c) * 1000));
            return;
        }
        ArrayList a2 = a(installedApp, c2);
        if (!a2.isEmpty()) {
            a("APPS_INCR", a2);
        }
        ArrayList a3 = a(c2, installedApp);
        if (!a3.isEmpty()) {
            a("UNINSTALL", a3);
        }
        a(installedApp);
        a(a.a(this.c) + (a.e(this.c) * 1000));
    }

    private ArrayList<HashMap<String, String>> a(ArrayList<HashMap<String, String>> arrayList, ArrayList<HashMap<String, String>> arrayList2) {
        boolean z;
        ArrayList<HashMap<String, String>> arrayList3 = new ArrayList<>();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            HashMap hashMap = (HashMap) it.next();
            String str = (String) hashMap.get("pkg");
            if (!TextUtils.isEmpty(str)) {
                Iterator it2 = arrayList2.iterator();
                while (true) {
                    if (it2.hasNext()) {
                        if (str.equals(((HashMap) it2.next()).get("pkg"))) {
                            z = true;
                            break;
                        }
                    } else {
                        z = false;
                        break;
                    }
                }
                if (!z) {
                    arrayList3.add(hashMap);
                }
            }
        }
        return arrayList3;
    }
}
