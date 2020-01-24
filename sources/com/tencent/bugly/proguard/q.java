package com.tencent.bugly.proguard;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.tencent.bugly.beta.download.DownloadListener;
import com.tencent.bugly.beta.download.DownloadTask;
import com.tencent.bugly.beta.download.a;
import com.tencent.bugly.beta.global.e;
import com.tencent.bugly.beta.tinker.TinkerManager;
import com.tencent.bugly.beta.upgrade.BetaGrayStrategy;
import java.io.File;

/* compiled from: BUGLY */
public class q {
    public static q a = new q();
    public BetaGrayStrategy b;
    public DownloadTask c;
    public final Handler d = new Handler(Looper.getMainLooper());
    private DownloadListener e = new a(4, this, Integer.valueOf(0));

    public synchronized void a(int i, y yVar, boolean z) {
        this.b = a(yVar);
        if (i == 0 && this.b != null) {
            if (!(this.b == null || this.b.a == null || this.b.a.f == null)) {
                File file = e.E.G;
                if (file == null || !file.exists() || !com.tencent.bugly.beta.global.a.a(file, this.b.a.f.a, "SHA")) {
                    if (e.E.W != null) {
                        final u b2 = this.b.a.b();
                        if (b2 != null) {
                            this.d.post(new Runnable() {
                                public void run() {
                                    e.E.W.onPatchReceived(b2.a());
                                }
                            });
                        }
                    }
                    if ((e.E.Y || z) && e.E.Z) {
                        a();
                    }
                } else {
                    an.a("patch has downloaded!", new Object[0]);
                    if (!e.E.N && e.E.O <= 3) {
                        an.a("patch has downloaded but not patched execute patch!", new Object[0]);
                        e eVar = e.E;
                        int i2 = eVar.O;
                        eVar.O = i2 + 1;
                        com.tencent.bugly.beta.global.a.a("PATCH_MAX_TIMES", String.valueOf(i2));
                        com.tencent.bugly.beta.global.a.a(file, e.E.H);
                        TinkerManager.getInstance().onDownloadSuccess(e.E.H.getAbsolutePath(), e.E.V);
                    }
                }
            }
        }
    }

    public BetaGrayStrategy a(y yVar) {
        BetaGrayStrategy betaGrayStrategy;
        BetaGrayStrategy betaGrayStrategy2;
        BetaGrayStrategy betaGrayStrategy3;
        BetaGrayStrategy betaGrayStrategy4 = (BetaGrayStrategy) com.tencent.bugly.beta.global.a.a("st.bch", BetaGrayStrategy.CREATOR);
        if (betaGrayStrategy4 == null || betaGrayStrategy4.a == null) {
            com.tencent.bugly.beta.global.a.a("st.bch");
            betaGrayStrategy = null;
        } else {
            betaGrayStrategy = betaGrayStrategy4;
        }
        if (yVar != null) {
            if (yVar.n == 1 || betaGrayStrategy == null || TextUtils.isEmpty(betaGrayStrategy.a.m) || !TextUtils.equals(yVar.m, betaGrayStrategy.a.m)) {
                betaGrayStrategy3 = betaGrayStrategy;
            } else {
                p.a.a(new w("recall", System.currentTimeMillis(), 0, 0, null, betaGrayStrategy.a.m, betaGrayStrategy.a.p, null));
                com.tencent.bugly.beta.global.a.a("st.bch");
                e.E.p.a(betaGrayStrategy.a.f.b, e.E.I.getAbsolutePath(), null, betaGrayStrategy.a.f.a).delete(true);
                File file = e.E.H;
                if (file != null && file.exists() && file.delete()) {
                    an.a("delete tmpPatchFile", new Object[0]);
                }
                File file2 = e.E.G;
                if (file2 != null && file2.exists() && file2.delete()) {
                    an.a("delete patchFile", new Object[0]);
                }
                betaGrayStrategy3 = null;
                com.tencent.bugly.beta.global.a.a("IS_PATCH_ROLL_BACK", true);
                an.a("patch rollback", new Object[0]);
                if (!ap.b(e.E.s)) {
                    TinkerManager.getInstance().onPatchRollback(true);
                } else {
                    TinkerManager.getInstance().onPatchRollback(false);
                }
            }
            if (yVar.n != 1) {
                yVar = null;
                betaGrayStrategy2 = betaGrayStrategy3;
            } else {
                betaGrayStrategy2 = betaGrayStrategy3;
            }
        } else {
            betaGrayStrategy2 = betaGrayStrategy;
        }
        if (yVar != null) {
            BetaGrayStrategy betaGrayStrategy5 = new BetaGrayStrategy();
            betaGrayStrategy5.a = yVar;
            betaGrayStrategy5.e = System.currentTimeMillis();
            if (betaGrayStrategy2 != null && (!TextUtils.equals(betaGrayStrategy2.a.f.b, yVar.f.b) || (yVar.l != null && TextUtils.equals((CharSequence) yVar.l.get("H1"), "false")))) {
                e.E.M = (String) yVar.l.get("H2");
                e.E.p.a(betaGrayStrategy2.a.f.b, e.E.I.getAbsolutePath(), null, null).delete(true);
                if (betaGrayStrategy2.a.p == 3) {
                    File file3 = e.E.H;
                    if (file3 != null && file3.exists() && file3.delete()) {
                        an.a("delete tmpPatchFile", new Object[0]);
                    }
                    File file4 = e.E.G;
                    if (file4 != null && file4.exists() && file4.delete()) {
                        e.E.L = "";
                        an.a("delete patchFile", new Object[0]);
                    }
                }
            }
            com.tencent.bugly.beta.global.a.a("st.bch", betaGrayStrategy5);
            an.a("onUpgradeReceived: %s [type: %d]", yVar, Integer.valueOf(yVar.p));
            return betaGrayStrategy5;
        } else if (betaGrayStrategy2 == null || betaGrayStrategy2.a == null || betaGrayStrategy2.a.p != 3) {
            return null;
        } else {
            return betaGrayStrategy2;
        }
    }

    private void a() {
        if (this.b != null) {
            if (this.c == null) {
                this.c = e.E.p.a(this.b.a.f.b, e.E.I.getAbsolutePath(), null, this.b.a.f.a);
            }
            if (this.c != null) {
                this.c.addListener(this.e);
                this.c.setNeededNotify(false);
                this.c.download();
            }
        }
    }
}
