package com.tencent.tinker.lib.util;

import android.content.Context;
import android.content.Intent;
import com.tencent.tinker.commons.util.StreamUtil;
import com.tencent.tinker.lib.service.TinkerPatchService;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.shareutil.SharePatchFileUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class UpgradePatchRetry {
    private static final String RETRY_COUNT_PROPERTY = "times";
    private static final String RETRY_FILE_MD5_PROPERTY = "md5";
    private static final String RETRY_INFO_NAME = "patch.retry";
    private static final int RETRY_MAX_COUNT = 5;
    private static final String TAG = "Tinker.UpgradePatchRetry";
    private static final String TEMP_PATCH_NAME = "temp.apk";
    private static UpgradePatchRetry sInstance;
    private Context context = null;
    private boolean isRetryEnable = true;
    private int maxRetryCount = 5;
    private File retryInfoFile = null;
    private File tempPatchFile = null;

    static class RetryInfo {
        String md5;
        String times;

        RetryInfo(String md52, String times2) {
            this.md5 = md52;
            this.times = times2;
        }

        static RetryInfo readRetryProperty(File infoFile) {
            String md52 = null;
            String times2 = null;
            Properties properties = new Properties();
            FileInputStream inputStream = null;
            try {
                FileInputStream inputStream2 = new FileInputStream(infoFile);
                try {
                    properties.load(inputStream2);
                    md52 = properties.getProperty(UpgradePatchRetry.RETRY_FILE_MD5_PROPERTY);
                    times2 = properties.getProperty(UpgradePatchRetry.RETRY_COUNT_PROPERTY);
                    StreamUtil.closeQuietly(inputStream2);
                    FileInputStream fileInputStream = inputStream2;
                } catch (IOException e) {
                    e = e;
                    inputStream = inputStream2;
                    try {
                        TinkerLog.e(UpgradePatchRetry.TAG, "fail to readRetryProperty:" + e, new Object[0]);
                        StreamUtil.closeQuietly(inputStream);
                        return new RetryInfo(md52, times2);
                    } catch (Throwable th) {
                        th = th;
                        StreamUtil.closeQuietly(inputStream);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    inputStream = inputStream2;
                    StreamUtil.closeQuietly(inputStream);
                    throw th;
                }
            } catch (IOException e2) {
                e = e2;
                TinkerLog.e(UpgradePatchRetry.TAG, "fail to readRetryProperty:" + e, new Object[0]);
                StreamUtil.closeQuietly(inputStream);
                return new RetryInfo(md52, times2);
            }
            return new RetryInfo(md52, times2);
        }

        static void writeRetryProperty(File infoFile, RetryInfo info) {
            if (info != null) {
                File parentFile = infoFile.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                Properties newProperties = new Properties();
                newProperties.put(UpgradePatchRetry.RETRY_FILE_MD5_PROPERTY, info.md5);
                newProperties.put(UpgradePatchRetry.RETRY_COUNT_PROPERTY, info.times);
                FileOutputStream outputStream = null;
                try {
                    FileOutputStream outputStream2 = new FileOutputStream(infoFile, false);
                    try {
                        newProperties.store(outputStream2, null);
                        StreamUtil.closeQuietly(outputStream2);
                        FileOutputStream fileOutputStream = outputStream2;
                    } catch (Exception e) {
                        e = e;
                        outputStream = outputStream2;
                        try {
                            TinkerLog.printErrStackTrace(UpgradePatchRetry.TAG, e, "retry write property fail", new Object[0]);
                            StreamUtil.closeQuietly(outputStream);
                        } catch (Throwable th) {
                            th = th;
                            StreamUtil.closeQuietly(outputStream);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        outputStream = outputStream2;
                        StreamUtil.closeQuietly(outputStream);
                        throw th;
                    }
                } catch (Exception e2) {
                    e = e2;
                    TinkerLog.printErrStackTrace(UpgradePatchRetry.TAG, e, "retry write property fail", new Object[0]);
                    StreamUtil.closeQuietly(outputStream);
                }
            }
        }
    }

    public UpgradePatchRetry(Context context2) {
        this.context = context2;
        this.retryInfoFile = new File(SharePatchFileUtil.getPatchTempDirectory(context2), RETRY_INFO_NAME);
        this.tempPatchFile = new File(SharePatchFileUtil.getPatchTempDirectory(context2), TEMP_PATCH_NAME);
    }

    public static UpgradePatchRetry getInstance(Context context2) {
        if (sInstance == null) {
            sInstance = new UpgradePatchRetry(context2);
        }
        return sInstance;
    }

    public void setRetryEnable(boolean enable) {
        this.isRetryEnable = enable;
    }

    public void setMaxRetryCount(int count) {
        if (count <= 0) {
            TinkerLog.e(TAG, "max count must large than 0", new Object[0]);
        } else {
            this.maxRetryCount = count;
        }
    }

    public boolean onPatchRetryLoad() {
        if (!this.isRetryEnable) {
            TinkerLog.w(TAG, "onPatchRetryLoad retry disabled, just return", new Object[0]);
            return false;
        } else if (!Tinker.with(this.context).isMainProcess()) {
            TinkerLog.w(TAG, "onPatchRetryLoad retry is not main process, just return", new Object[0]);
            return false;
        } else if (!this.retryInfoFile.exists()) {
            TinkerLog.w(TAG, "onPatchRetryLoad retry info not exist, just return", new Object[0]);
            return false;
        } else if (TinkerServiceInternals.isTinkerPatchServiceRunning(this.context)) {
            TinkerLog.w(TAG, "onPatchRetryLoad tinker service is running, just return", new Object[0]);
            return false;
        } else {
            String path = this.tempPatchFile.getAbsolutePath();
            if (path == null || !new File(path).exists()) {
                TinkerLog.w(TAG, "onPatchRetryLoad patch file: %s is not exist, just return", path);
                return false;
            }
            TinkerLog.w(TAG, "onPatchRetryLoad patch file: %s is exist, retry to patch", path);
            TinkerInstaller.onReceiveUpgradePatch(this.context, path);
            return true;
        }
    }

    public void onPatchServiceStart(Intent intent) {
        RetryInfo retryInfo;
        if (!this.isRetryEnable) {
            TinkerLog.w(TAG, "onPatchServiceStart retry disabled, just return", new Object[0]);
        } else if (intent == null) {
            TinkerLog.e(TAG, "onPatchServiceStart intent is null, just return", new Object[0]);
        } else {
            String path = TinkerPatchService.getPatchPathExtra(intent);
            if (path == null) {
                TinkerLog.w(TAG, "onPatchServiceStart patch path is null, just return", new Object[0]);
                return;
            }
            File patchFile = new File(path);
            String patchMd5 = SharePatchFileUtil.getMD5(patchFile);
            if (patchMd5 == null) {
                TinkerLog.w(TAG, "onPatchServiceStart patch md5 is null, just return", new Object[0]);
                return;
            }
            if (this.retryInfoFile.exists()) {
                retryInfo = RetryInfo.readRetryProperty(this.retryInfoFile);
                if (retryInfo.md5 == null || retryInfo.times == null || !patchMd5.equals(retryInfo.md5)) {
                    copyToTempFile(patchFile);
                    retryInfo.md5 = patchMd5;
                    retryInfo.times = "1";
                } else {
                    int nowTimes = Integer.parseInt(retryInfo.times);
                    if (nowTimes >= this.maxRetryCount) {
                        SharePatchFileUtil.safeDeleteFile(this.tempPatchFile);
                        TinkerLog.w(TAG, "onPatchServiceStart retry more than max count, delete retry info file!", new Object[0]);
                        return;
                    }
                    retryInfo.times = String.valueOf(nowTimes + 1);
                }
            } else {
                copyToTempFile(patchFile);
                retryInfo = new RetryInfo(patchMd5, "1");
            }
            RetryInfo.writeRetryProperty(this.retryInfoFile, retryInfo);
        }
    }

    public boolean onPatchListenerCheck(String md5) {
        if (!this.isRetryEnable) {
            TinkerLog.w(TAG, "onPatchListenerCheck retry disabled, just return", new Object[0]);
            return true;
        } else if (!this.retryInfoFile.exists()) {
            TinkerLog.w(TAG, "onPatchListenerCheck retry file is not exist, just return", new Object[0]);
            return true;
        } else if (md5 == null) {
            TinkerLog.w(TAG, "onPatchListenerCheck md5 is null, just return", new Object[0]);
            return true;
        } else {
            RetryInfo retryInfo = RetryInfo.readRetryProperty(this.retryInfoFile);
            if (!md5.equals(retryInfo.md5)) {
                return true;
            }
            int nowTimes = Integer.parseInt(retryInfo.times);
            if (nowTimes < this.maxRetryCount) {
                return true;
            }
            TinkerLog.w(TAG, "onPatchListenerCheck, retry count %d must exceed than max retry count", Integer.valueOf(nowTimes));
            SharePatchFileUtil.safeDeleteFile(this.tempPatchFile);
            return false;
        }
    }

    public boolean onPatchResetMaxCheck(String md5) {
        if (!this.isRetryEnable) {
            TinkerLog.w(TAG, "onPatchResetMaxCheck retry disabled, just return", new Object[0]);
        } else if (!this.retryInfoFile.exists()) {
            TinkerLog.w(TAG, "onPatchResetMaxCheck retry file is not exist, just return", new Object[0]);
        } else if (md5 == null) {
            TinkerLog.w(TAG, "onPatchResetMaxCheck md5 is null, just return", new Object[0]);
        } else {
            RetryInfo retryInfo = RetryInfo.readRetryProperty(this.retryInfoFile);
            if (md5.equals(retryInfo.md5)) {
                TinkerLog.i(TAG, "onPatchResetMaxCheck, reset max check to 1", new Object[0]);
                retryInfo.times = "1";
                RetryInfo.writeRetryProperty(this.retryInfoFile, retryInfo);
            }
        }
        return true;
    }

    public void onPatchServiceResult() {
        if (!this.isRetryEnable) {
            TinkerLog.w(TAG, "onPatchServiceResult retry disabled, just return", new Object[0]);
        } else if (this.tempPatchFile.exists()) {
            SharePatchFileUtil.safeDeleteFile(this.tempPatchFile);
        }
    }

    private void copyToTempFile(File patchFile) {
        if (!patchFile.getAbsolutePath().equals(this.tempPatchFile.getAbsolutePath())) {
            TinkerLog.w(TAG, "try copy file: %s to %s", patchFile.getAbsolutePath(), this.tempPatchFile.getAbsolutePath());
            try {
                SharePatchFileUtil.copyFileUsingStream(patchFile, this.tempPatchFile);
            } catch (IOException e) {
                TinkerLog.e(TAG, "fail to copy file: %s to %s", patchFile.getAbsolutePath(), this.tempPatchFile.getAbsolutePath());
            }
        }
    }
}
