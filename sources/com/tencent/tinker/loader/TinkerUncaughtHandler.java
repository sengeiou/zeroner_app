package com.tencent.tinker.loader;

import android.content.Context;
import android.os.Process;
import android.util.Log;
import com.tencent.tinker.loader.shareutil.SharePatchFileUtil;
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;

public class TinkerUncaughtHandler implements UncaughtExceptionHandler {
    private static final String TAG = "Tinker.UncaughtHandler";
    private final Context context;
    private final File crashFile;
    private final UncaughtExceptionHandler ueh = Thread.getDefaultUncaughtExceptionHandler();

    public TinkerUncaughtHandler(Context context2) {
        this.context = context2;
        this.crashFile = SharePatchFileUtil.getPatchLastCrashFile(context2);
    }

    public void uncaughtException(Thread thread, Throwable ex) {
        Log.e(TAG, "TinkerUncaughtHandler catch exception:" + Log.getStackTraceString(ex));
        this.ueh.uncaughtException(thread, ex);
        if (this.crashFile != null && (Thread.getDefaultUncaughtExceptionHandler() instanceof TinkerUncaughtHandler)) {
            File parentFile = this.crashFile.getParentFile();
            if (parentFile.exists() || parentFile.mkdirs()) {
                PrintWriter pw = null;
                try {
                    PrintWriter pw2 = new PrintWriter(new FileWriter(this.crashFile, false));
                    try {
                        pw2.println("process:" + ShareTinkerInternals.getProcessName(this.context));
                        pw2.println(ShareTinkerInternals.getExceptionCauseString(ex));
                        SharePatchFileUtil.closeQuietly(pw2);
                        PrintWriter printWriter = pw2;
                    } catch (IOException e) {
                        e = e;
                        pw = pw2;
                        try {
                            Log.e(TAG, "print crash file error:" + Log.getStackTraceString(e));
                            SharePatchFileUtil.closeQuietly(pw);
                            Process.killProcess(Process.myPid());
                            return;
                        } catch (Throwable th) {
                            th = th;
                            SharePatchFileUtil.closeQuietly(pw);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        pw = pw2;
                        SharePatchFileUtil.closeQuietly(pw);
                        throw th;
                    }
                } catch (IOException e2) {
                    e = e2;
                    Log.e(TAG, "print crash file error:" + Log.getStackTraceString(e));
                    SharePatchFileUtil.closeQuietly(pw);
                    Process.killProcess(Process.myPid());
                    return;
                }
                Process.killProcess(Process.myPid());
                return;
            }
            Log.e(TAG, "print crash file error: create directory fail!");
        }
    }
}
