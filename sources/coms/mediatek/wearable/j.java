package coms.mediatek.wearable;

import android.text.TextUtils;
import android.util.Log;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

class j {
    private static j a;
    /* access modifiers changed from: private */
    public a b;
    /* access modifiers changed from: private */
    public CopyOnWriteArrayList<i> c = new CopyOnWriteArrayList<>();
    /* access modifiers changed from: private */
    public a d = WearableManager.getInstance().b;

    private class a extends Thread {
        private a() {
        }

        public void run() {
            boolean z = false;
            while (!z) {
                i a2 = j.this.d();
                if (a2 == null) {
                    Log.e("[wearable]SessionManager", "[SessionThread] empty SessionList");
                    return;
                }
                Log.d("[wearable]SessionManager", "[SessionThread] running, total_size=" + j.this.c.size());
                while (WearableManager.getInstance().a == 1) {
                    try {
                        Log.d("[wearable]SessionManager", "[SessionThread] Thread.sleep 50");
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        Log.e("[wearable]SessionManager", "[SessionThread] Thread.sleep InterruptedException");
                    }
                }
                if (WearableManager.getInstance().getConnectState() != 3) {
                    Log.d("[wearable]SessionManager", "[SessionThread] not STATE_CONNECTED, break");
                    j.this.c.clear();
                    j.this.d.a(0);
                    j.this.b = null;
                    return;
                }
                a2.a(true);
                WearableManager.getInstance().a(a2);
                if (!WearableManager.getInstance().a()) {
                    Log.e("[wearable]SessionManager", "[SessionThread] waitAckForSPP timeout");
                    j.this.b = null;
                    return;
                }
                synchronized (j.this.c) {
                    if (!a2.c()) {
                        j.this.c.remove(a2);
                    } else {
                        j.this.c.remove(a2);
                    }
                    j.this.d.a(j.this.c());
                    Log.d("[wearable]SessionManager", "[SessionThread] remove, remain size=" + j.this.c.size());
                    if (j.this.c.isEmpty()) {
                        j.this.b = null;
                        Log.d("[wearable]SessionManager", "[SessionThread] end, start");
                        Log.d("[wearable]SessionManager", "[SessionThread] end, tobeStopped=true");
                        z = true;
                    }
                }
            }
        }
    }

    private j() {
    }

    public static synchronized j a() {
        j jVar;
        synchronized (j.class) {
            if (a == null) {
                a = new j();
            }
            jVar = a;
        }
        return jVar;
    }

    /* access modifiers changed from: private */
    public int c() {
        return this.c.size();
    }

    /* access modifiers changed from: private */
    public i d() {
        if (this.c.size() == 0) {
            return null;
        }
        return (i) this.c.get(0);
    }

    /* access modifiers changed from: 0000 */
    public synchronized void a(i iVar) {
        Log.d("[wearable]SessionManager", "[addSession] session: " + iVar.d());
        if (WearableManager.getInstance().isAvailable() || iVar.d().equals("SyncTime")) {
            synchronized (this.c) {
                if (iVar.f() != 2) {
                    this.c.add(iVar);
                } else if (this.c.size() > 0) {
                    this.c.add(1, iVar);
                } else {
                    this.c.add(iVar);
                }
                Log.d("[wearable]SessionManager", "[addSession] session: " + this.c.size());
                this.d.a(c());
                if (this.b != null && this.c.size() == 1) {
                    Log.e("[wearable]SessionManager", "[addSession] exception");
                }
                if (this.b == null && this.c.size() == 1) {
                    this.b = new a();
                    this.b.start();
                    Log.d("[wearable]SessionManager", "[addSession] mSessionThread start");
                }
            }
        } else {
            Log.d("[wearable]SessionManager", "[addSession] return");
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(String str) {
        Log.d("[wearable]SessionManager", "clear tag=" + str);
        if (TextUtils.isEmpty(str)) {
            b();
            return;
        }
        String lowerCase = str.toLowerCase();
        synchronized (this.c) {
            Log.d("[wearable]SessionManager", "clear start " + this.c.size());
            Iterator it = this.c.iterator();
            while (it.hasNext()) {
                i iVar = (i) it.next();
                String lowerCase2 = iVar.d().toLowerCase();
                if (!iVar.a() && lowerCase2.contains(lowerCase)) {
                    this.c.remove(iVar);
                }
            }
            Log.d("[wearable]SessionManager", "clear end " + this.c.size());
            Iterator it2 = this.c.iterator();
            while (it2.hasNext()) {
                Log.d("[wearable]SessionManager", "clear end session: " + ((i) it2.next()).d());
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void b() {
        this.c.clear();
    }
}
