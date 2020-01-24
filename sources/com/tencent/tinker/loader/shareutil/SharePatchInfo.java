package com.tencent.tinker.loader.shareutil;

import android.util.Log;
import com.tencent.tinker.loader.TinkerRuntimeException;
import java.io.File;
import java.io.IOException;

public class SharePatchInfo {
    public static final String DEFAULT_DIR = "odex";
    public static final String FINGER_PRINT = "print";
    public static final int MAX_EXTRACT_ATTEMPTS = 2;
    public static final String NEW_VERSION = "new";
    public static final String OAT_DIR = "dir";
    public static final String OLD_VERSION = "old";
    private static final String TAG = "Tinker.PatchInfo";
    public String fingerPrint;
    public String newVersion;
    public String oatDir;
    public String oldVersion;

    public SharePatchInfo(String oldVer, String newVew, String finger, String oatDir2) {
        this.oldVersion = oldVer;
        this.newVersion = newVew;
        this.fingerPrint = finger;
        this.oatDir = oatDir2;
    }

    public static SharePatchInfo readAndCheckPropertyWithLock(File pathInfoFile, File lockFile) {
        if (pathInfoFile == null || lockFile == null) {
            return null;
        }
        File lockParentFile = lockFile.getParentFile();
        if (!lockParentFile.exists()) {
            lockParentFile.mkdirs();
        }
        ShareFileLockHelper fileLock = null;
        try {
            fileLock = ShareFileLockHelper.getFileLock(lockFile);
            SharePatchInfo readAndCheckProperty = readAndCheckProperty(pathInfoFile);
            if (fileLock == null) {
                return readAndCheckProperty;
            }
            try {
                fileLock.close();
                return readAndCheckProperty;
            } catch (IOException e) {
                Log.w(TAG, "releaseInfoLock error", e);
                return readAndCheckProperty;
            }
        } catch (Exception e2) {
            throw new TinkerRuntimeException("readAndCheckPropertyWithLock fail", e2);
        } catch (Throwable th) {
            if (fileLock != null) {
                try {
                    fileLock.close();
                } catch (IOException e3) {
                    Log.w(TAG, "releaseInfoLock error", e3);
                }
            }
            throw th;
        }
    }

    public static boolean rewritePatchInfoFileWithLock(File pathInfoFile, SharePatchInfo info, File lockFile) {
        if (pathInfoFile == null || info == null || lockFile == null) {
            return false;
        }
        File lockParentFile = lockFile.getParentFile();
        if (!lockParentFile.exists()) {
            lockParentFile.mkdirs();
        }
        ShareFileLockHelper fileLock = null;
        try {
            fileLock = ShareFileLockHelper.getFileLock(lockFile);
            boolean rewritePatchInfoFile = rewritePatchInfoFile(pathInfoFile, info);
            if (fileLock == null) {
                return rewritePatchInfoFile;
            }
            try {
                fileLock.close();
                return rewritePatchInfoFile;
            } catch (IOException e) {
                Log.i(TAG, "releaseInfoLock error", e);
                return rewritePatchInfoFile;
            }
        } catch (Exception e2) {
            throw new TinkerRuntimeException("rewritePatchInfoFileWithLock fail", e2);
        } catch (Throwable th) {
            if (fileLock != null) {
                try {
                    fileLock.close();
                } catch (IOException e3) {
                    Log.i(TAG, "releaseInfoLock error", e3);
                }
            }
            throw th;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0048  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.tencent.tinker.loader.shareutil.SharePatchInfo readAndCheckProperty(java.io.File r13) {
        /*
            r3 = 0
            r6 = 0
            r8 = 0
            r5 = 0
            r4 = 0
            r7 = 0
        L_0x0006:
            r10 = 2
            if (r6 >= r10) goto L_0x009a
            if (r3 != 0) goto L_0x009a
            int r6 = r6 + 1
            java.util.Properties r9 = new java.util.Properties
            r9.<init>()
            r1 = 0
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ IOException -> 0x0073 }
            r2.<init>(r13)     // Catch:{ IOException -> 0x0073 }
            r9.load(r2)     // Catch:{ IOException -> 0x00a7, all -> 0x00a4 }
            java.lang.String r10 = "old"
            java.lang.String r8 = r9.getProperty(r10)     // Catch:{ IOException -> 0x00a7, all -> 0x00a4 }
            java.lang.String r10 = "new"
            java.lang.String r5 = r9.getProperty(r10)     // Catch:{ IOException -> 0x00a7, all -> 0x00a4 }
            java.lang.String r10 = "print"
            java.lang.String r4 = r9.getProperty(r10)     // Catch:{ IOException -> 0x00a7, all -> 0x00a4 }
            java.lang.String r10 = "dir"
            java.lang.String r7 = r9.getProperty(r10)     // Catch:{ IOException -> 0x00a7, all -> 0x00a4 }
            com.tencent.tinker.loader.shareutil.SharePatchFileUtil.closeQuietly(r2)
            r1 = r2
        L_0x003b:
            if (r8 == 0) goto L_0x0006
            if (r5 == 0) goto L_0x0006
            java.lang.String r10 = ""
            boolean r10 = r8.equals(r10)
            if (r10 != 0) goto L_0x004e
            boolean r10 = com.tencent.tinker.loader.shareutil.SharePatchFileUtil.checkIfMd5Valid(r8)
            if (r10 == 0) goto L_0x0054
        L_0x004e:
            boolean r10 = com.tencent.tinker.loader.shareutil.SharePatchFileUtil.checkIfMd5Valid(r5)
            if (r10 != 0) goto L_0x0097
        L_0x0054:
            java.lang.String r10 = "Tinker.PatchInfo"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r12 = "path info file  corrupted:"
            java.lang.StringBuilder r11 = r11.append(r12)
            java.lang.String r12 = r13.getAbsolutePath()
            java.lang.StringBuilder r11 = r11.append(r12)
            java.lang.String r11 = r11.toString()
            android.util.Log.w(r10, r11)
            goto L_0x0006
        L_0x0073:
            r0 = move-exception
        L_0x0074:
            java.lang.String r10 = "Tinker.PatchInfo"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ all -> 0x0092 }
            r11.<init>()     // Catch:{ all -> 0x0092 }
            java.lang.String r12 = "read property failed, e:"
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ all -> 0x0092 }
            java.lang.StringBuilder r11 = r11.append(r0)     // Catch:{ all -> 0x0092 }
            java.lang.String r11 = r11.toString()     // Catch:{ all -> 0x0092 }
            android.util.Log.w(r10, r11)     // Catch:{ all -> 0x0092 }
            com.tencent.tinker.loader.shareutil.SharePatchFileUtil.closeQuietly(r1)
            goto L_0x003b
        L_0x0092:
            r10 = move-exception
        L_0x0093:
            com.tencent.tinker.loader.shareutil.SharePatchFileUtil.closeQuietly(r1)
            throw r10
        L_0x0097:
            r3 = 1
            goto L_0x0006
        L_0x009a:
            if (r3 == 0) goto L_0x00a2
            com.tencent.tinker.loader.shareutil.SharePatchInfo r10 = new com.tencent.tinker.loader.shareutil.SharePatchInfo
            r10.<init>(r8, r5, r4, r7)
        L_0x00a1:
            return r10
        L_0x00a2:
            r10 = 0
            goto L_0x00a1
        L_0x00a4:
            r10 = move-exception
            r1 = r2
            goto L_0x0093
        L_0x00a7:
            r0 = move-exception
            r1 = r2
            goto L_0x0074
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.tinker.loader.shareutil.SharePatchInfo.readAndCheckProperty(java.io.File):com.tencent.tinker.loader.shareutil.SharePatchInfo");
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x00e5  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00fc  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0082 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean rewritePatchInfoFile(java.io.File r14, com.tencent.tinker.loader.shareutil.SharePatchInfo r15) {
        /*
            r9 = 1
            r10 = 0
            if (r14 == 0) goto L_0x0006
            if (r15 != 0) goto L_0x0008
        L_0x0006:
            r9 = r10
        L_0x0007:
            return r9
        L_0x0008:
            java.lang.String r11 = r15.fingerPrint
            boolean r11 = com.tencent.tinker.loader.shareutil.ShareTinkerInternals.isNullOrNil(r11)
            if (r11 == 0) goto L_0x0014
            java.lang.String r11 = android.os.Build.FINGERPRINT
            r15.fingerPrint = r11
        L_0x0014:
            java.lang.String r11 = r15.oatDir
            boolean r11 = com.tencent.tinker.loader.shareutil.ShareTinkerInternals.isNullOrNil(r11)
            if (r11 == 0) goto L_0x0021
            java.lang.String r11 = "odex"
            r15.oatDir = r11
        L_0x0021:
            java.lang.String r11 = "Tinker.PatchInfo"
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            java.lang.String r13 = "rewritePatchInfoFile file path:"
            java.lang.StringBuilder r12 = r12.append(r13)
            java.lang.String r13 = r14.getAbsolutePath()
            java.lang.StringBuilder r12 = r12.append(r13)
            java.lang.String r13 = " , oldVer:"
            java.lang.StringBuilder r12 = r12.append(r13)
            java.lang.String r13 = r15.oldVersion
            java.lang.StringBuilder r12 = r12.append(r13)
            java.lang.String r13 = ", newVer:"
            java.lang.StringBuilder r12 = r12.append(r13)
            java.lang.String r13 = r15.newVersion
            java.lang.StringBuilder r12 = r12.append(r13)
            java.lang.String r13 = ", fingerprint:"
            java.lang.StringBuilder r12 = r12.append(r13)
            java.lang.String r13 = r15.fingerPrint
            java.lang.StringBuilder r12 = r12.append(r13)
            java.lang.String r13 = ", oatDir:"
            java.lang.StringBuilder r12 = r12.append(r13)
            java.lang.String r13 = r15.oatDir
            java.lang.StringBuilder r12 = r12.append(r13)
            java.lang.String r12 = r12.toString()
            android.util.Log.i(r11, r12)
            r2 = 0
            r4 = 0
            java.io.File r7 = r14.getParentFile()
            boolean r11 = r7.exists()
            if (r11 != 0) goto L_0x0082
            r7.mkdirs()
        L_0x0082:
            r11 = 2
            if (r4 >= r11) goto L_0x0126
            if (r2 != 0) goto L_0x0126
            int r4 = r4 + 1
            java.util.Properties r3 = new java.util.Properties
            r3.<init>()
            java.lang.String r11 = "old"
            java.lang.String r12 = r15.oldVersion
            r3.put(r11, r12)
            java.lang.String r11 = "new"
            java.lang.String r12 = r15.newVersion
            r3.put(r11, r12)
            java.lang.String r11 = "print"
            java.lang.String r12 = r15.fingerPrint
            r3.put(r11, r12)
            java.lang.String r11 = "dir"
            java.lang.String r12 = r15.oatDir
            r3.put(r11, r12)
            r5 = 0
            java.io.FileOutputStream r6 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0100 }
            r11 = 0
            r6.<init>(r14, r11)     // Catch:{ Exception -> 0x0100 }
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x012e, all -> 0x012b }
            r11.<init>()     // Catch:{ Exception -> 0x012e, all -> 0x012b }
            java.lang.String r12 = "from old version:"
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ Exception -> 0x012e, all -> 0x012b }
            java.lang.String r12 = r15.oldVersion     // Catch:{ Exception -> 0x012e, all -> 0x012b }
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ Exception -> 0x012e, all -> 0x012b }
            java.lang.String r12 = " to new version:"
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ Exception -> 0x012e, all -> 0x012b }
            java.lang.String r12 = r15.newVersion     // Catch:{ Exception -> 0x012e, all -> 0x012b }
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ Exception -> 0x012e, all -> 0x012b }
            java.lang.String r0 = r11.toString()     // Catch:{ Exception -> 0x012e, all -> 0x012b }
            r3.store(r6, r0)     // Catch:{ Exception -> 0x012e, all -> 0x012b }
            com.tencent.tinker.loader.shareutil.SharePatchFileUtil.closeQuietly(r6)
            r5 = r6
        L_0x00df:
            com.tencent.tinker.loader.shareutil.SharePatchInfo r8 = readAndCheckProperty(r14)
            if (r8 == 0) goto L_0x0124
            java.lang.String r11 = r8.oldVersion
            java.lang.String r12 = r15.oldVersion
            boolean r11 = r11.equals(r12)
            if (r11 == 0) goto L_0x0124
            java.lang.String r11 = r8.newVersion
            java.lang.String r12 = r15.newVersion
            boolean r11 = r11.equals(r12)
            if (r11 == 0) goto L_0x0124
            r2 = r9
        L_0x00fa:
            if (r2 != 0) goto L_0x0082
            r14.delete()
            goto L_0x0082
        L_0x0100:
            r1 = move-exception
        L_0x0101:
            java.lang.String r11 = "Tinker.PatchInfo"
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ all -> 0x011f }
            r12.<init>()     // Catch:{ all -> 0x011f }
            java.lang.String r13 = "write property failed, e:"
            java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ all -> 0x011f }
            java.lang.StringBuilder r12 = r12.append(r1)     // Catch:{ all -> 0x011f }
            java.lang.String r12 = r12.toString()     // Catch:{ all -> 0x011f }
            android.util.Log.w(r11, r12)     // Catch:{ all -> 0x011f }
            com.tencent.tinker.loader.shareutil.SharePatchFileUtil.closeQuietly(r5)
            goto L_0x00df
        L_0x011f:
            r9 = move-exception
        L_0x0120:
            com.tencent.tinker.loader.shareutil.SharePatchFileUtil.closeQuietly(r5)
            throw r9
        L_0x0124:
            r2 = r10
            goto L_0x00fa
        L_0x0126:
            if (r2 != 0) goto L_0x0007
            r9 = r10
            goto L_0x0007
        L_0x012b:
            r9 = move-exception
            r5 = r6
            goto L_0x0120
        L_0x012e:
            r1 = move-exception
            r5 = r6
            goto L_0x0101
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.tinker.loader.shareutil.SharePatchInfo.rewritePatchInfoFile(java.io.File, com.tencent.tinker.loader.shareutil.SharePatchInfo):boolean");
    }
}
