package com.tencent.bugly.beta.global;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.tencent.bugly.beta.download.DownloadTask;
import com.tencent.bugly.beta.download.a;
import com.tencent.bugly.beta.download.b;
import com.tencent.bugly.proguard.an;
import com.tencent.bugly.proguard.p;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: BUGLY */
public class f {
    public static f a = new f();
    final Map<String, DownloadTask> b = Collections.synchronizedMap(new HashMap());
    Handler c = new Handler(Looper.getMainLooper());
    private List<Runnable> d = new ArrayList();

    public synchronized void a(Runnable runnable, int i) {
        if (this.b.size() == 0) {
            runnable.run();
        } else {
            d dVar = new d(6, Boolean.valueOf(false), runnable);
            this.c.postDelayed(dVar, (long) i);
            a(dVar);
        }
    }

    public synchronized void a(Runnable runnable) {
        if (this.b.size() == 0) {
            runnable.run();
        } else {
            this.d.add(runnable);
        }
    }

    public void a(b bVar, Map<String, String> map) {
        String[] strArr;
        DownloadTask downloadTask;
        DownloadTask downloadTask2;
        if (bVar != null) {
            if (map == null || map.isEmpty()) {
                this.b.clear();
                ResBean.a = new ResBean();
                a.a("rb.bch", ResBean.a);
                return;
            }
            for (DownloadTask delete : this.b.values()) {
                delete.delete(true);
            }
            this.b.clear();
            a aVar = new a(1, this, this.b);
            for (String str : ResBean.b) {
                if (!map.containsKey(str)) {
                    this.b.clear();
                    ResBean.a = new ResBean();
                    a.a("rb.bch", ResBean.a);
                    return;
                }
                String str2 = (String) map.get(str);
                if (!str.startsWith("IMG_") || TextUtils.isEmpty(str2)) {
                    ResBean.a.a(str, str2);
                } else {
                    ResBean.a.a(str, "");
                    Iterator it = this.b.values().iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            downloadTask = null;
                            break;
                        }
                        downloadTask = (DownloadTask) it.next();
                        if (downloadTask.getDownloadUrl().equals(str2)) {
                            break;
                        }
                    }
                    if (downloadTask == null) {
                        downloadTask2 = bVar.a(str2, e.E.r.getAbsolutePath(), null, null);
                    } else {
                        downloadTask2 = downloadTask;
                    }
                    if (downloadTask2 != null) {
                        downloadTask2.addListener(aVar);
                        downloadTask2.setNeededNotify(false);
                        this.b.put(str, downloadTask2);
                    }
                }
            }
            a.a("rb.bch", ResBean.a);
            if (!this.b.isEmpty()) {
                for (DownloadTask download : this.b.values()) {
                    download.download();
                }
            }
        }
    }

    public void a() {
        String[] strArr;
        boolean z;
        ArrayList<String> arrayList = new ArrayList<>();
        for (String str : ResBean.b) {
            String a2 = ResBean.a.a(str);
            if (str.startsWith("IMG_") && !TextUtils.isEmpty(a2)) {
                arrayList.add(a2);
            }
        }
        File[] listFiles = e.E.r.listFiles();
        if (listFiles != null && listFiles.length > 0) {
            for (File file : listFiles) {
                boolean z2 = true;
                for (String equals : arrayList) {
                    if (file.getAbsolutePath().equals(equals)) {
                        z = false;
                    } else {
                        z = z2;
                    }
                    z2 = z;
                }
                if (z2) {
                    p.a.b(file.getAbsolutePath());
                    if (!file.delete()) {
                        an.e("cannot deleteCache file:%s", file.getAbsolutePath());
                    }
                }
            }
        }
    }

    public void b() {
        synchronized (this) {
            for (Runnable run : this.d) {
                run.run();
            }
            for (DownloadTask delete : this.b.values()) {
                delete.delete(false);
            }
            this.d.clear();
            this.b.clear();
        }
    }
}
