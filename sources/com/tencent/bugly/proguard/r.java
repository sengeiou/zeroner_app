package com.tencent.bugly.proguard;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.bugly.beta.global.a;
import com.tencent.bugly.beta.global.e;
import com.tencent.bugly.beta.tinker.TinkerManager;
import com.tencent.bugly.beta.tinker.TinkerManager.TinkerListener;
import com.tencent.bugly.beta.ui.g;
import com.tencent.bugly.beta.upgrade.BetaGrayStrategy;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import java.io.File;
import java.util.Iterator;

/* compiled from: BUGLY */
public class r {
    public static synchronized void a(Context context) {
        boolean z;
        String str;
        synchronized (r.class) {
            final e eVar = e.E;
            String absolutePath = context.getDir("dex", 0).getAbsolutePath();
            String absolutePath2 = context.getDir("tmpPatch", 32768).getAbsolutePath();
            eVar.G = new File(absolutePath, TinkerManager.PATCH_NAME);
            if (TextUtils.isEmpty(a.b("PatchFile", ""))) {
                a.a("PatchFile", eVar.G.getAbsolutePath());
            }
            eVar.H = new File(absolutePath2, "tmpPatch.apk");
            if (eVar.G != null && TextUtils.isEmpty(a.b("PatchFile", ""))) {
                a.a("PatchFile", eVar.G.getAbsolutePath());
            }
            eVar.I = context.getDir("tmpPatch", 0);
            if (eVar.I != null && TextUtils.isEmpty(a.b("PatchTmpDir", ""))) {
                a.a("PatchTmpDir", eVar.G.getAbsolutePath());
            }
            BetaGrayStrategy betaGrayStrategy = (BetaGrayStrategy) a.a("st.bch", BetaGrayStrategy.CREATOR);
            if (betaGrayStrategy == null || betaGrayStrategy.a == null || betaGrayStrategy.a.p != 3) {
                if (TinkerManager.getInstance().getPatchDirectory(eVar.s) != null && !TinkerManager.getInstance().getPatchDirectory(eVar.s).exists()) {
                    a.a("IS_PATCH_ROLL_BACK", false);
                }
                if (a.b("IS_PATCH_ROLL_BACK", false)) {
                    TinkerManager.getInstance().onPatchRollback(false);
                }
            } else {
                if (betaGrayStrategy.a.l != null) {
                    eVar.M = (String) betaGrayStrategy.a.l.get("H2");
                    if (!TextUtils.isEmpty(eVar.M)) {
                        ap.b("G15", eVar.M);
                    }
                }
                eVar.N = a.b("PatchResult", false);
                eVar.O = Integer.valueOf(a.b("PATCH_MAX_TIMES", "0")).intValue();
                if (!eVar.N) {
                    an.a("[patch] inject failure", new Object[0]);
                    if (!a.b("UPLOAD_PATCH_RESULT", false)) {
                        a.a("UPLOAD_PATCH_RESULT", true);
                        if (p.a.a(new w("active", System.currentTimeMillis(), 1, 0, null, betaGrayStrategy.a.m, betaGrayStrategy.a.p, null))) {
                            an.a("save patch failed event success!", new Object[0]);
                        } else {
                            an.e("save patch failed event failed!", new Object[0]);
                        }
                        if (e.E.G != null && e.E.G.exists() && e.E.G.delete()) {
                            an.a("[patch] delete patch.apk success", new Object[0]);
                        }
                    }
                } else {
                    an.a("[patch] inject success", new Object[0]);
                    if (!a.b("UPLOAD_PATCH_RESULT", false)) {
                        a.a("UPLOAD_PATCH_RESULT", true);
                        if (p.a.a(new w("active", System.currentTimeMillis(), 0, 0, null, betaGrayStrategy.a.m, betaGrayStrategy.a.p, null))) {
                            an.a("save patch success event success!", new Object[0]);
                        } else {
                            an.e("save patch success event failed!", new Object[0]);
                        }
                    }
                }
            }
            File file = e.E.H;
            if (file != null && file.exists() && file.delete()) {
                an.a("[patch] delete tmpPatch.apk success", new Object[0]);
            }
            File file2 = e.E.G;
            if (file2 != null && file2.exists()) {
                String a = ap.a(file2, "SHA");
                if (a != null) {
                    e.E.L = a;
                }
            }
            if (TinkerManager.isTinkerManagerInstalled()) {
                if (TextUtils.isEmpty(eVar.J)) {
                    eVar.J = TinkerManager.getTinkerId();
                }
                an.a("TINKER_ID:" + eVar.J, new Object[0]);
                eVar.K = TinkerManager.getNewTinkerId();
                an.a("NEW_TINKER_ID:" + eVar.K, new Object[0]);
                TinkerManager.getInstance().setTinkerListener(new TinkerListener() {
                    public void onDownloadSuccess(String msg) {
                        if (eVar.W != null) {
                            eVar.W.onDownloadSuccess(msg);
                        }
                    }

                    public void onDownloadFailure(String msg) {
                        if (eVar.W != null) {
                            eVar.W.onDownloadFailure(msg);
                        }
                    }

                    public void onPatchStart() {
                        eVar.ac = true;
                        a.a("IS_PATCHING", true);
                    }

                    public void onApplySuccess(String msg) {
                        eVar.ac = false;
                        eVar.N = true;
                        a.a("IS_PATCHING", false);
                        a.a("PatchResult", true);
                        an.a("Tinker patch success, result: " + msg, new Object[0]);
                        if (eVar.X) {
                            g.a(new com.tencent.bugly.beta.ui.e(), true);
                        }
                        if (eVar.W != null) {
                            eVar.W.onApplySuccess(msg);
                        }
                    }

                    public void onApplyFailure(String msg) {
                        eVar.N = false;
                        a.a("PatchResult", false);
                        a.a("IS_PATCHING", false);
                        an.a("Tinker patch failure, result: " + msg, new Object[0]);
                        if (eVar.W != null) {
                            eVar.W.onApplyFailure(msg);
                        }
                    }

                    public void onPatchRollback() {
                        an.a("patch rollback callback.", new Object[0]);
                        if (eVar.W != null) {
                            eVar.W.onPatchRollback();
                        }
                        if (TinkerManager.getInstance().getPatchDirectory(eVar.s) != null && !TinkerManager.getInstance().getPatchDirectory(eVar.s).exists()) {
                            a.a("IS_PATCH_ROLL_BACK", false);
                        }
                    }
                });
            }
            if (TextUtils.isEmpty(a.b("BaseArchName", ""))) {
                String str2 = eVar.s.getApplicationInfo().nativeLibraryDir;
                if (str2 != null) {
                    File[] listFiles = new File(str2).listFiles();
                    boolean z2 = false;
                    if (listFiles != null && listFiles.length > 0) {
                        int length = listFiles.length;
                        int i = 0;
                        while (i < length) {
                            File file3 = listFiles[i];
                            if (file3.getName().endsWith(".so")) {
                                String replace = file3.getName().replace(".so", "");
                                if (replace.startsWith(ShareConstants.SO_PATH)) {
                                    str = replace.substring(replace.indexOf(ShareConstants.SO_PATH) + 3);
                                } else {
                                    str = replace;
                                }
                                an.a("libName:" + str, new Object[0]);
                                String absolutePath3 = file3.getAbsolutePath();
                                an.a("soFilePath:" + absolutePath3, new Object[0]);
                                Iterator it = eVar.aa.iterator();
                                while (true) {
                                    if (it.hasNext()) {
                                        if (str.equals((String) it.next())) {
                                            z = true;
                                            break;
                                        }
                                    } else {
                                        z = z2;
                                        break;
                                    }
                                }
                                if (!z) {
                                    z = false;
                                    String b = a.b(absolutePath3);
                                    an.a("archName:" + b, new Object[0]);
                                    if (b != null && b.equals("armeabi-v5te")) {
                                        b = "armeabi";
                                    }
                                    a.a(str, b);
                                    if (TextUtils.isEmpty(a.b("BaseArchName", ""))) {
                                        a.a("BaseArchName", b);
                                    }
                                }
                            } else {
                                z = z2;
                            }
                            i++;
                            z2 = z;
                        }
                    }
                }
            }
        }
    }
}
