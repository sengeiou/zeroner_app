package com.iwown.lib_common.file;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.util.Log;
import com.google.common.base.Ascii;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.socks.library.KLog;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

public class FileUtils {
    private static final String LINE_SEP = System.getProperty("line.separator");
    private static final char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public interface OnReplaceListener {
        boolean onReplace();
    }

    private FileUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static File getFileByPath(String filePath) {
        if (isSpace(filePath)) {
            return null;
        }
        return new File(filePath);
    }

    public static boolean isFileExists(String filePath) {
        return isFileExists(getFileByPath(filePath));
    }

    public static boolean createDirectory(String directoryName) {
        if (directoryName.equals("")) {
            return false;
        }
        boolean mkdir = new File(Environment.getExternalStorageDirectory().toString() + directoryName).mkdir();
        return true;
    }

    public static File creatSDDir(String dirName) {
        File dir = new File(Environment.getExternalStorageDirectory() + "/" + dirName);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    public static File creatSDFile(String fileName) throws IOException {
        File file = new File(Environment.getExternalStorageDirectory() + "/" + fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    public static void createFileDir(Context context, String fileName) {
        if (!checkSaveLocationExists() || checkFileExists("Zeroner")) {
            String path = getSDCardPath();
            boolean exists = checkFilePathExists(path + File.separator + "Zeroner");
            KLog.e("是否存在sd卡文件" + exists);
            if (!exists) {
                KLog.e("创建存在sd卡文件");
                try {
                    mkdirFile(path + "/" + fileName);
                } catch (IOException e) {
                    ThrowableExtension.printStackTrace(e);
                }
            }
        } else if (VERSION.SDK_INT >= 19) {
            createFileDirFor19(context, fileName);
        }
        String path2 = getSDCardPath();
        boolean exists2 = checkFilePathExists(path2 + File.separator + "Zeroner");
        KLog.e("是否存在sd卡文件" + exists2);
        if (!exists2) {
            KLog.e("创建存在sd卡文件" + exists2);
            try {
                mkdirFile(path2 + "/" + fileName);
            } catch (IOException e2) {
                ThrowableExtension.printStackTrace(e2);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x00b8 A[SYNTHETIC, Splitter:B:30:0x00b8] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00f1 A[SYNTHETIC, Splitter:B:43:0x00f1] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getSDCardPath() {
        /*
            r13 = 1
            java.lang.String r2 = "cat /proc/mounts"
            r7 = 0
            java.lang.Runtime r6 = java.lang.Runtime.getRuntime()
            r0 = 0
            java.lang.Process r5 = r6.exec(r2)     // Catch:{ Exception -> 0x00fd }
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ Exception -> 0x00fd }
            java.io.InputStreamReader r10 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x00fd }
            java.io.BufferedInputStream r11 = new java.io.BufferedInputStream     // Catch:{ Exception -> 0x00fd }
            java.io.InputStream r12 = r5.getInputStream()     // Catch:{ Exception -> 0x00fd }
            r11.<init>(r12)     // Catch:{ Exception -> 0x00fd }
            r10.<init>(r11)     // Catch:{ Exception -> 0x00fd }
            r1.<init>(r10)     // Catch:{ Exception -> 0x00fd }
        L_0x0021:
            java.lang.String r4 = r1.readLine()     // Catch:{ Exception -> 0x00b1, all -> 0x00fa }
            if (r4 == 0) goto L_0x00dc
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00b1, all -> 0x00fa }
            r10.<init>()     // Catch:{ Exception -> 0x00b1, all -> 0x00fa }
            java.lang.String r11 = "proc/mounts:   "
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ Exception -> 0x00b1, all -> 0x00fa }
            java.lang.StringBuilder r10 = r10.append(r4)     // Catch:{ Exception -> 0x00b1, all -> 0x00fa }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x00b1, all -> 0x00fa }
            com.socks.library.KLog.i(r10)     // Catch:{ Exception -> 0x00b1, all -> 0x00fa }
            java.lang.String r10 = "sdcard"
            boolean r10 = r4.contains(r10)     // Catch:{ Exception -> 0x00b1, all -> 0x00fa }
            if (r10 == 0) goto L_0x008c
            java.lang.String r10 = ".android_secure"
            boolean r10 = r4.contains(r10)     // Catch:{ Exception -> 0x00b1, all -> 0x00fa }
            if (r10 == 0) goto L_0x008c
            java.lang.String r10 = " "
            java.lang.String[] r9 = r4.split(r10)     // Catch:{ Exception -> 0x00b1, all -> 0x00fa }
            int r10 = r9.length     // Catch:{ Exception -> 0x00b1, all -> 0x00fa }
            r11 = 5
            if (r10 < r11) goto L_0x008c
            r10 = 1
            r10 = r9[r10]     // Catch:{ Exception -> 0x00b1, all -> 0x00fa }
            java.lang.String r11 = "/.android_secure"
            java.lang.String r12 = ""
            java.lang.String r7 = r10.replace(r11, r12)     // Catch:{ Exception -> 0x00b1, all -> 0x00fa }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00b1, all -> 0x00fa }
            r10.<init>()     // Catch:{ Exception -> 0x00b1, all -> 0x00fa }
            java.lang.String r11 = "find sd card path:   "
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ Exception -> 0x00b1, all -> 0x00fa }
            java.lang.StringBuilder r10 = r10.append(r7)     // Catch:{ Exception -> 0x00b1, all -> 0x00fa }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x00b1, all -> 0x00fa }
            com.socks.library.KLog.i(r10)     // Catch:{ Exception -> 0x00b1, all -> 0x00fa }
            if (r1 == 0) goto L_0x0084
            r1.close()     // Catch:{ IOException -> 0x0087 }
        L_0x0084:
            r0 = r1
            r8 = r7
        L_0x0086:
            return r8
        L_0x0087:
            r3 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r3)
            goto L_0x0084
        L_0x008c:
            int r10 = r5.waitFor()     // Catch:{ Exception -> 0x00b1, all -> 0x00fa }
            if (r10 == 0) goto L_0x0021
            int r10 = r5.exitValue()     // Catch:{ Exception -> 0x00b1, all -> 0x00fa }
            if (r10 != r13) goto L_0x0021
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00b1, all -> 0x00fa }
            r10.<init>()     // Catch:{ Exception -> 0x00b1, all -> 0x00fa }
            java.lang.StringBuilder r10 = r10.append(r2)     // Catch:{ Exception -> 0x00b1, all -> 0x00fa }
            java.lang.String r11 = " 命令执行失败"
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ Exception -> 0x00b1, all -> 0x00fa }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x00b1, all -> 0x00fa }
            com.socks.library.KLog.e(r10)     // Catch:{ Exception -> 0x00b1, all -> 0x00fa }
            goto L_0x0021
        L_0x00b1:
            r3 = move-exception
            r0 = r1
        L_0x00b3:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r3)     // Catch:{ all -> 0x00ee }
            if (r0 == 0) goto L_0x00bb
            r0.close()     // Catch:{ IOException -> 0x00e9 }
        L_0x00bb:
            java.io.File r10 = android.os.Environment.getExternalStorageDirectory()
            java.lang.String r7 = r10.getPath()
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = "not find sd card path return default:   "
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.StringBuilder r10 = r10.append(r7)
            java.lang.String r10 = r10.toString()
            com.socks.library.KLog.i(r10)
            r8 = r7
            goto L_0x0086
        L_0x00dc:
            if (r1 == 0) goto L_0x00e1
            r1.close()     // Catch:{ IOException -> 0x00e3 }
        L_0x00e1:
            r0 = r1
            goto L_0x00bb
        L_0x00e3:
            r3 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r3)
            r0 = r1
            goto L_0x00bb
        L_0x00e9:
            r3 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r3)
            goto L_0x00bb
        L_0x00ee:
            r10 = move-exception
        L_0x00ef:
            if (r0 == 0) goto L_0x00f4
            r0.close()     // Catch:{ IOException -> 0x00f5 }
        L_0x00f4:
            throw r10
        L_0x00f5:
            r3 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r3)
            goto L_0x00f4
        L_0x00fa:
            r10 = move-exception
            r0 = r1
            goto L_0x00ef
        L_0x00fd:
            r3 = move-exception
            goto L_0x00b3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.iwown.lib_common.file.FileUtils.getSDCardPath():java.lang.String");
    }

    @TargetApi(19)
    public static void createFileDirFor19(Context context, String fileName) {
        if (!createDirectory("Zeroner")) {
            String[] sdPath = getVolumePaths(context);
            if (sdPath.length > 1) {
                for (int i = 0; i < sdPath.length; i++) {
                    if (sdPath[i] != null) {
                        try {
                            if (Environment.getStorageState(new File(sdPath[i] + "/Zeroner")).equals("mounted")) {
                                mkdirFile(sdPath[i] + "/" + fileName);
                            }
                        } catch (IOException e) {
                            ThrowableExtension.printStackTrace(e);
                        }
                    }
                }
            }
        }
    }

    public static synchronized void mkdirFile(String path) throws IOException {
        synchronized (FileUtils.class) {
            if (!TextUtils.isEmpty(path)) {
                File fileTemp = new File(new File(path).getParent());
                if (!fileTemp.exists()) {
                    fileTemp.mkdirs();
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0040 A[SYNTHETIC, Splitter:B:17:0x0040] */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x004c A[SYNTHETIC, Splitter:B:23:0x004c] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean write2SDFromString_1(java.lang.String r6, java.lang.String r7, java.lang.String r8) {
        /*
            r4 = 1
            r1 = 0
            r2 = 0
            creatSDDir(r6)     // Catch:{ Exception -> 0x0039 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0039 }
            r5.<init>()     // Catch:{ Exception -> 0x0039 }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Exception -> 0x0039 }
            java.lang.StringBuilder r5 = r5.append(r7)     // Catch:{ Exception -> 0x0039 }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x0039 }
            java.io.File r1 = createSDFile(r5)     // Catch:{ Exception -> 0x0039 }
            java.io.FileWriter r3 = new java.io.FileWriter     // Catch:{ Exception -> 0x0039 }
            r5 = 1
            r3.<init>(r1, r5)     // Catch:{ Exception -> 0x0039 }
            r3.write(r8)     // Catch:{ Exception -> 0x0058, all -> 0x0055 }
            java.lang.String r5 = "\r\n"
            r3.write(r5)     // Catch:{ Exception -> 0x0058, all -> 0x0055 }
            r3.flush()     // Catch:{ Exception -> 0x0058, all -> 0x0055 }
            if (r3 == 0) goto L_0x0032
            r3.close()     // Catch:{ Exception -> 0x0034 }
        L_0x0032:
            r2 = r3
        L_0x0033:
            return r4
        L_0x0034:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x0032
        L_0x0039:
            r0 = move-exception
        L_0x003a:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)     // Catch:{ all -> 0x0049 }
            r4 = 0
            if (r2 == 0) goto L_0x0033
            r2.close()     // Catch:{ Exception -> 0x0044 }
            goto L_0x0033
        L_0x0044:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x0033
        L_0x0049:
            r4 = move-exception
        L_0x004a:
            if (r2 == 0) goto L_0x004f
            r2.close()     // Catch:{ Exception -> 0x0050 }
        L_0x004f:
            throw r4
        L_0x0050:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x004f
        L_0x0055:
            r4 = move-exception
            r2 = r3
            goto L_0x004a
        L_0x0058:
            r0 = move-exception
            r2 = r3
            goto L_0x003a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.iwown.lib_common.file.FileUtils.write2SDFromString_1(java.lang.String, java.lang.String, java.lang.String):boolean");
    }

    public static File createSDFile(String fileName) throws IOException {
        File file = new File(Environment.getExternalStorageDirectory() + "/" + fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    public static boolean isFileExists(File file) {
        return file != null && file.exists();
    }

    public static boolean rename(String filePath, String newName) {
        return rename(getFileByPath(filePath), newName);
    }

    public static boolean checkSaveLocationExists() {
        if (Environment.getExternalStorageState().equals("mounted")) {
            return true;
        }
        return false;
    }

    public static boolean checkFilePathExists(String path) {
        return new File(path).exists();
    }

    public static String[] getVolumePaths(Context context) {
        String[] paths = null;
        Method mMethodGetPaths = null;
        StorageManager mStorageManager = (StorageManager) context.getSystemService("storage");
        try {
            mMethodGetPaths = mStorageManager.getClass().getMethod("getVolumePaths", new Class[0]);
        } catch (NoSuchMethodException e) {
            ThrowableExtension.printStackTrace(e);
        }
        try {
            return (String[]) mMethodGetPaths.invoke(mStorageManager, new Object[0]);
        } catch (IllegalArgumentException e2) {
            ThrowableExtension.printStackTrace(e2);
            return paths;
        } catch (IllegalAccessException e3) {
            ThrowableExtension.printStackTrace(e3);
            return paths;
        } catch (InvocationTargetException e4) {
            ThrowableExtension.printStackTrace(e4);
            return paths;
        }
    }

    public static boolean rename(File file, String newName) {
        boolean z = true;
        if (file == null || !file.exists() || isSpace(newName)) {
            return false;
        }
        if (newName.equals(file.getName())) {
            return true;
        }
        File newFile = new File(file.getParent() + File.separator + newName);
        if (newFile.exists() || !file.renameTo(newFile)) {
            z = false;
        }
        return z;
    }

    public static boolean isDir(String dirPath) {
        return isDir(getFileByPath(dirPath));
    }

    public static boolean isDir(File file) {
        return file != null && file.exists() && file.isDirectory();
    }

    public static boolean isFile(String filePath) {
        return isFile(getFileByPath(filePath));
    }

    public static boolean isFile(File file) {
        return file != null && file.exists() && file.isFile();
    }

    public static boolean createOrExistsDir(String dirPath) {
        return createOrExistsDir(getFileByPath(dirPath));
    }

    public static boolean createOrExistsDir(File file) {
        return file != null && (!file.exists() ? file.mkdirs() : file.isDirectory());
    }

    public static boolean createOrExistsFile(String filePath) {
        return createOrExistsFile(getFileByPath(filePath));
    }

    public static boolean createOrExistsFile(File file) {
        boolean z = false;
        if (file == null) {
            return z;
        }
        if (file.exists()) {
            return file.isFile();
        }
        if (!createOrExistsDir(file.getParentFile())) {
            return z;
        }
        try {
            return file.createNewFile();
        } catch (IOException e) {
            ThrowableExtension.printStackTrace(e);
            return z;
        }
    }

    public static boolean createFileByDeleteOldFile(String filePath) {
        return createFileByDeleteOldFile(getFileByPath(filePath));
    }

    public static boolean createFileByDeleteOldFile(File file) {
        boolean z = false;
        if (file == null) {
            return z;
        }
        if ((file.exists() && !file.delete()) || !createOrExistsDir(file.getParentFile())) {
            return z;
        }
        try {
            return file.createNewFile();
        } catch (IOException e) {
            ThrowableExtension.printStackTrace(e);
            return z;
        }
    }

    private static boolean copyOrMoveDir(String srcDirPath, String destDirPath, OnReplaceListener listener, boolean isMove) {
        return copyOrMoveDir(getFileByPath(srcDirPath), getFileByPath(destDirPath), listener, isMove);
    }

    private static boolean copyOrMoveDir(File srcDir, File destDir, OnReplaceListener listener, boolean isMove) {
        File[] files;
        if (srcDir == null || destDir == null) {
            return false;
        }
        String destPath = destDir.getPath() + File.separator;
        if (destPath.contains(srcDir.getPath() + File.separator) || !srcDir.exists() || !srcDir.isDirectory()) {
            return false;
        }
        if (destDir.exists()) {
            if (!listener.onReplace()) {
                return true;
            }
            if (!deleteAllInDir(destDir)) {
                return false;
            }
        }
        if (!createOrExistsDir(destDir)) {
            return false;
        }
        for (File file : srcDir.listFiles()) {
            File oneDestFile = new File(destPath + file.getName());
            if (file.isFile()) {
                if (!copyOrMoveFile(file, oneDestFile, listener, isMove)) {
                    return false;
                }
            } else if (file.isDirectory() && !copyOrMoveDir(file, oneDestFile, listener, isMove)) {
                return false;
            }
        }
        if (!isMove || deleteDir(srcDir)) {
            return true;
        }
        return false;
    }

    private static boolean copyOrMoveFile(String srcFilePath, String destFilePath, OnReplaceListener listener, boolean isMove) {
        return copyOrMoveFile(getFileByPath(srcFilePath), getFileByPath(destFilePath), listener, isMove);
    }

    private static boolean copyOrMoveFile(File srcFile, File destFile, OnReplaceListener listener, boolean isMove) {
        boolean z = true;
        if (srcFile == null || destFile == null || srcFile.equals(destFile) || !srcFile.exists() || !srcFile.isFile()) {
            return false;
        }
        if (destFile.exists()) {
            if (!listener.onReplace()) {
                return true;
            }
            if (!destFile.delete()) {
                return false;
            }
        }
        if (!createOrExistsDir(destFile.getParentFile())) {
            return false;
        }
        try {
            if (!FileIOUtils.writeFileFromIS(destFile, (InputStream) new FileInputStream(srcFile), false) || (isMove && !deleteFile(srcFile))) {
                z = false;
            }
            return z;
        } catch (FileNotFoundException e) {
            ThrowableExtension.printStackTrace(e);
            return false;
        }
    }

    public static boolean copyDir(String srcDirPath, String destDirPath, OnReplaceListener listener) {
        return copyDir(getFileByPath(srcDirPath), getFileByPath(destDirPath), listener);
    }

    public static boolean copyDir(File srcDir, File destDir, OnReplaceListener listener) {
        return copyOrMoveDir(srcDir, destDir, listener, false);
    }

    public static boolean copyFile(String srcFilePath, String destFilePath, OnReplaceListener listener) {
        return copyFile(getFileByPath(srcFilePath), getFileByPath(destFilePath), listener);
    }

    public static boolean copyFile(File srcFile, File destFile, OnReplaceListener listener) {
        return copyOrMoveFile(srcFile, destFile, listener, false);
    }

    public static boolean moveDir(String srcDirPath, String destDirPath, OnReplaceListener listener) {
        return moveDir(getFileByPath(srcDirPath), getFileByPath(destDirPath), listener);
    }

    public static boolean moveDir(File srcDir, File destDir, OnReplaceListener listener) {
        return copyOrMoveDir(srcDir, destDir, listener, true);
    }

    public static boolean moveFile(String srcFilePath, String destFilePath, OnReplaceListener listener) {
        return moveFile(getFileByPath(srcFilePath), getFileByPath(destFilePath), listener);
    }

    public static boolean moveFile(File srcFile, File destFile, OnReplaceListener listener) {
        return copyOrMoveFile(srcFile, destFile, listener, true);
    }

    public static boolean deleteDir(String dirPath) {
        return deleteDir(getFileByPath(dirPath));
    }

    public static boolean deleteDir(File dir) {
        if (dir == null) {
            return false;
        }
        if (!dir.exists()) {
            return true;
        }
        if (!dir.isDirectory()) {
            return false;
        }
        File[] files = dir.listFiles();
        if (!(files == null || files.length == 0)) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!file.delete()) {
                        return false;
                    }
                } else if (file.isDirectory() && !deleteDir(file)) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    public static boolean deleteFile(String fileName) {
        SecurityManager checker = new SecurityManager();
        if (fileName.equals("")) {
            return false;
        }
        File newPath = new File(Environment.getExternalStorageDirectory().toString() + fileName);
        checker.checkDelete(newPath.toString());
        if (!newPath.isFile()) {
            return false;
        }
        try {
            KLog.i("DirectoryManager deleteFile", fileName);
            newPath.delete();
            return true;
        } catch (SecurityException se) {
            ThrowableExtension.printStackTrace(se);
            return false;
        }
    }

    public static boolean deleteFile(File file) {
        return file != null && (!file.exists() || (file.isFile() && file.delete()));
    }

    public static boolean deleteAllInDir(String dirPath) {
        return deleteAllInDir(getFileByPath(dirPath));
    }

    public static boolean deleteAllInDir(File dir) {
        return deleteFilesInDirWithFilter(dir, (FileFilter) new FileFilter() {
            public boolean accept(File pathname) {
                return true;
            }
        });
    }

    public static boolean deleteFilesInDir(String dirPath) {
        return deleteFilesInDir(getFileByPath(dirPath));
    }

    public static boolean deleteFilesInDir(File dir) {
        return deleteFilesInDirWithFilter(dir, (FileFilter) new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.isFile();
            }
        });
    }

    public static boolean deleteFilesInDirWithFilter(String dirPath, FileFilter filter) {
        return deleteFilesInDirWithFilter(getFileByPath(dirPath), filter);
    }

    public static boolean deleteFilesInDirWithFilter(File dir, FileFilter filter) {
        if (dir == null) {
            return false;
        }
        if (!dir.exists()) {
            return true;
        }
        if (!dir.isDirectory()) {
            return false;
        }
        File[] files = dir.listFiles();
        if (!(files == null || files.length == 0)) {
            for (File file : files) {
                if (filter.accept(file)) {
                    if (file.isFile()) {
                        if (!file.delete()) {
                            return false;
                        }
                    } else if (file.isDirectory() && !deleteDir(file)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static List<File> listFilesInDir(String dirPath) {
        return listFilesInDir(dirPath, false);
    }

    public static List<File> listFilesInDir(File dir) {
        return listFilesInDir(dir, false);
    }

    public static List<File> listFilesInDir(String dirPath, boolean isRecursive) {
        return listFilesInDir(getFileByPath(dirPath), isRecursive);
    }

    public static List<File> listFilesInDir(File dir, boolean isRecursive) {
        return listFilesInDirWithFilter(dir, (FileFilter) new FileFilter() {
            public boolean accept(File pathname) {
                return true;
            }
        }, isRecursive);
    }

    public static List<File> listFilesInDirWithFilter(String dirPath, FileFilter filter) {
        return listFilesInDirWithFilter(getFileByPath(dirPath), filter, false);
    }

    public static List<File> listFilesInDirWithFilter(File dir, FileFilter filter) {
        return listFilesInDirWithFilter(dir, filter, false);
    }

    public static List<File> listFilesInDirWithFilter(String dirPath, FileFilter filter, boolean isRecursive) {
        return listFilesInDirWithFilter(getFileByPath(dirPath), filter, isRecursive);
    }

    public static List<File> listFilesInDirWithFilter(File dir, FileFilter filter, boolean isRecursive) {
        if (!isDir(dir)) {
            return null;
        }
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            return list;
        }
        for (File file : files) {
            if (filter.accept(file)) {
                list.add(file);
            }
            if (isRecursive && file.isDirectory()) {
                list.addAll(listFilesInDirWithFilter(file, filter, true));
            }
        }
        return list;
    }

    public static long getFileLastModified(String filePath) {
        return getFileLastModified(getFileByPath(filePath));
    }

    public static long getFileLastModified(File file) {
        if (file == null) {
            return -1;
        }
        return file.lastModified();
    }

    public static String getFileCharsetSimple(String filePath) {
        return getFileCharsetSimple(getFileByPath(filePath));
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x003e  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0042  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0046  */
    /* JADX WARNING: Removed duplicated region for block: B:7:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getFileCharsetSimple(java.io.File r8) {
        /*
            r7 = 1
            r6 = 0
            r3 = 0
            r1 = 0
            java.io.BufferedInputStream r2 = new java.io.BufferedInputStream     // Catch:{ IOException -> 0x0029 }
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch:{ IOException -> 0x0029 }
            r4.<init>(r8)     // Catch:{ IOException -> 0x0029 }
            r2.<init>(r4)     // Catch:{ IOException -> 0x0029 }
            int r4 = r2.read()     // Catch:{ IOException -> 0x004d, all -> 0x004a }
            int r4 = r4 << 8
            int r5 = r2.read()     // Catch:{ IOException -> 0x004d, all -> 0x004a }
            int r3 = r4 + r5
            java.io.Closeable[] r4 = new java.io.Closeable[r7]
            r4[r6] = r2
            com.iwown.lib_common.file.CloseUtils.closeIO(r4)
            r1 = r2
        L_0x0022:
            switch(r3) {
                case 61371: goto L_0x003e;
                case 65279: goto L_0x0046;
                case 65534: goto L_0x0042;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.String r4 = "GBK"
        L_0x0028:
            return r4
        L_0x0029:
            r0 = move-exception
        L_0x002a:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)     // Catch:{ all -> 0x0035 }
            java.io.Closeable[] r4 = new java.io.Closeable[r7]
            r4[r6] = r1
            com.iwown.lib_common.file.CloseUtils.closeIO(r4)
            goto L_0x0022
        L_0x0035:
            r4 = move-exception
        L_0x0036:
            java.io.Closeable[] r5 = new java.io.Closeable[r7]
            r5[r6] = r1
            com.iwown.lib_common.file.CloseUtils.closeIO(r5)
            throw r4
        L_0x003e:
            java.lang.String r4 = "UTF-8"
            goto L_0x0028
        L_0x0042:
            java.lang.String r4 = "Unicode"
            goto L_0x0028
        L_0x0046:
            java.lang.String r4 = "UTF-16BE"
            goto L_0x0028
        L_0x004a:
            r4 = move-exception
            r1 = r2
            goto L_0x0036
        L_0x004d:
            r0 = move-exception
            r1 = r2
            goto L_0x002a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.iwown.lib_common.file.FileUtils.getFileCharsetSimple(java.io.File):java.lang.String");
    }

    public static int getFileLines(String filePath) {
        return getFileLines(getFileByPath(filePath));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0028, code lost:
        if (r3 >= r6) goto L_0x001e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x002e, code lost:
        if (r0[r3] != 10) goto L_0x0032;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0030, code lost:
        r1 = r1 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0032, code lost:
        r3 = r3 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0035, code lost:
        r6 = r5.read(r0, 0, 1024);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x003c, code lost:
        if (r6 == -1) goto L_0x004c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x003e, code lost:
        r3 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x003f, code lost:
        if (r3 >= r6) goto L_0x0035;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0045, code lost:
        if (r0[r3] != 13) goto L_0x0049;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0047, code lost:
        r1 = r1 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0049, code lost:
        r3 = r3 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x004c, code lost:
        com.iwown.lib_common.file.CloseUtils.closeIO(r5);
        r4 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x001c, code lost:
        if (LINE_SEP.endsWith("\n") != false) goto L_0x001e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x001e, code lost:
        r6 = r5.read(r0, 0, 1024);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0025, code lost:
        if (r6 == -1) goto L_0x004c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0027, code lost:
        r3 = 0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int getFileLines(java.io.File r12) {
        /*
            r11 = -1
            r10 = 1
            r9 = 0
            r1 = 1
            r4 = 0
            java.io.BufferedInputStream r5 = new java.io.BufferedInputStream     // Catch:{ IOException -> 0x0055 }
            java.io.FileInputStream r7 = new java.io.FileInputStream     // Catch:{ IOException -> 0x0055 }
            r7.<init>(r12)     // Catch:{ IOException -> 0x0055 }
            r5.<init>(r7)     // Catch:{ IOException -> 0x0055 }
            r7 = 1024(0x400, float:1.435E-42)
            byte[] r0 = new byte[r7]     // Catch:{ IOException -> 0x006d, all -> 0x006a }
            java.lang.String r7 = LINE_SEP     // Catch:{ IOException -> 0x006d, all -> 0x006a }
            java.lang.String r8 = "\n"
            boolean r7 = r7.endsWith(r8)     // Catch:{ IOException -> 0x006d, all -> 0x006a }
            if (r7 == 0) goto L_0x0035
        L_0x001e:
            r7 = 0
            r8 = 1024(0x400, float:1.435E-42)
            int r6 = r5.read(r0, r7, r8)     // Catch:{ IOException -> 0x006d, all -> 0x006a }
            if (r6 == r11) goto L_0x004c
            r3 = 0
        L_0x0028:
            if (r3 >= r6) goto L_0x001e
            byte r7 = r0[r3]     // Catch:{ IOException -> 0x006d, all -> 0x006a }
            r8 = 10
            if (r7 != r8) goto L_0x0032
            int r1 = r1 + 1
        L_0x0032:
            int r3 = r3 + 1
            goto L_0x0028
        L_0x0035:
            r7 = 0
            r8 = 1024(0x400, float:1.435E-42)
            int r6 = r5.read(r0, r7, r8)     // Catch:{ IOException -> 0x006d, all -> 0x006a }
            if (r6 == r11) goto L_0x004c
            r3 = 0
        L_0x003f:
            if (r3 >= r6) goto L_0x0035
            byte r7 = r0[r3]     // Catch:{ IOException -> 0x006d, all -> 0x006a }
            r8 = 13
            if (r7 != r8) goto L_0x0049
            int r1 = r1 + 1
        L_0x0049:
            int r3 = r3 + 1
            goto L_0x003f
        L_0x004c:
            java.io.Closeable[] r7 = new java.io.Closeable[r10]
            r7[r9] = r5
            com.iwown.lib_common.file.CloseUtils.closeIO(r7)
            r4 = r5
        L_0x0054:
            return r1
        L_0x0055:
            r2 = move-exception
        L_0x0056:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r2)     // Catch:{ all -> 0x0061 }
            java.io.Closeable[] r7 = new java.io.Closeable[r10]
            r7[r9] = r4
            com.iwown.lib_common.file.CloseUtils.closeIO(r7)
            goto L_0x0054
        L_0x0061:
            r7 = move-exception
        L_0x0062:
            java.io.Closeable[] r8 = new java.io.Closeable[r10]
            r8[r9] = r4
            com.iwown.lib_common.file.CloseUtils.closeIO(r8)
            throw r7
        L_0x006a:
            r7 = move-exception
            r4 = r5
            goto L_0x0062
        L_0x006d:
            r2 = move-exception
            r4 = r5
            goto L_0x0056
        */
        throw new UnsupportedOperationException("Method not decompiled: com.iwown.lib_common.file.FileUtils.getFileLines(java.io.File):int");
    }

    public static String getDirSize(String dirPath) {
        return getDirSize(getFileByPath(dirPath));
    }

    public static String getDirSize(File dir) {
        long len = getDirLength(dir);
        return len == -1 ? "" : byte2FitMemorySize(len);
    }

    public static String getFileSize(String filePath) {
        return getFileSize(getFileByPath(filePath));
    }

    public static String getFileSize(File file) {
        long len = getFileLength(file);
        return len == -1 ? "" : byte2FitMemorySize(len);
    }

    public static long getFileSize2(File file) {
        return getFileLength(file);
    }

    public static long getDirLength(String dirPath) {
        return getDirLength(getFileByPath(dirPath));
    }

    public static long getDirLength(File dir) {
        long length;
        if (!isDir(dir)) {
            return -1;
        }
        long len = 0;
        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            return 0;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                length = getDirLength(file);
            } else {
                length = file.length();
            }
            len += length;
        }
        return len;
    }

    public static long getFileLength(String filePath) {
        return getFileLength(getFileByPath(filePath));
    }

    public static long getFileLength(File file) {
        if (!isFile(file)) {
            return -1;
        }
        return file.length();
    }

    public static String getFileMD5ToString(String filePath) {
        return getFileMD5ToString(isSpace(filePath) ? null : new File(filePath));
    }

    public static String getFileMD5ToString(File file) {
        return bytes2HexString(getFileMD5(file));
    }

    public static byte[] getFileMD5(String filePath) {
        return getFileMD5(getFileByPath(filePath));
    }

    public static byte[] getFileMD5(File file) {
        Exception e;
        byte[] bArr = null;
        if (file != null) {
            DigestInputStream dis = null;
            try {
                DigestInputStream dis2 = new DigestInputStream(new FileInputStream(file), MessageDigest.getInstance(MessageDigestAlgorithms.MD5));
                try {
                    do {
                    } while (dis2.read(new byte[262144]) > 0);
                    bArr = dis2.getMessageDigest().digest();
                    CloseUtils.closeIO(dis2);
                } catch (NoSuchAlgorithmException e2) {
                    e = e2;
                    dis = dis2;
                    e = e;
                    try {
                        ThrowableExtension.printStackTrace(e);
                        CloseUtils.closeIO(dis);
                        return bArr;
                    } catch (Throwable th) {
                        th = th;
                        CloseUtils.closeIO(dis);
                        throw th;
                    }
                } catch (IOException e3) {
                    e = e3;
                    dis = dis2;
                    e = e;
                    ThrowableExtension.printStackTrace(e);
                    CloseUtils.closeIO(dis);
                    return bArr;
                } catch (Throwable th2) {
                    th = th2;
                    dis = dis2;
                    CloseUtils.closeIO(dis);
                    throw th;
                }
            } catch (NoSuchAlgorithmException e4) {
                e = e4;
                e = e;
                ThrowableExtension.printStackTrace(e);
                CloseUtils.closeIO(dis);
                return bArr;
            } catch (IOException e5) {
                e = e5;
                e = e;
                ThrowableExtension.printStackTrace(e);
                CloseUtils.closeIO(dis);
                return bArr;
            }
        }
        return bArr;
    }

    public static String getDirName(File file) {
        if (file == null) {
            return null;
        }
        return getDirName(file.getPath());
    }

    public static String getDirName(String filePath) {
        if (isSpace(filePath)) {
            return filePath;
        }
        int lastSep = filePath.lastIndexOf(File.separator);
        return lastSep == -1 ? "" : filePath.substring(0, lastSep + 1);
    }

    public static String getFileName(File file) {
        if (file == null) {
            return null;
        }
        return getFileName(file.getPath());
    }

    public static String getFileName(String filePath) {
        if (isSpace(filePath)) {
            return filePath;
        }
        int lastSep = filePath.lastIndexOf(File.separator);
        return lastSep != -1 ? filePath.substring(lastSep + 1) : filePath;
    }

    public static String getFileNameNoExtension(File file) {
        if (file == null) {
            return null;
        }
        return getFileNameNoExtension(file.getPath());
    }

    public static String getFileNameNoExtension(String filePath) {
        if (isSpace(filePath)) {
            return filePath;
        }
        int lastPoi = filePath.lastIndexOf(46);
        int lastSep = filePath.lastIndexOf(File.separator);
        if (lastSep == -1) {
            if (lastPoi != -1) {
                return filePath.substring(0, lastPoi);
            }
            return filePath;
        } else if (lastPoi == -1 || lastSep > lastPoi) {
            return filePath.substring(lastSep + 1);
        } else {
            return filePath.substring(lastSep + 1, lastPoi);
        }
    }

    public static String getFileExtension(File file) {
        if (file == null) {
            return null;
        }
        return getFileExtension(file.getPath());
    }

    public static String getFileExtension(String filePath) {
        if (isSpace(filePath)) {
            return filePath;
        }
        int lastPoi = filePath.lastIndexOf(46);
        int lastSep = filePath.lastIndexOf(File.separator);
        if (lastPoi == -1 || lastSep >= lastPoi) {
            return "";
        }
        return filePath.substring(lastPoi + 1);
    }

    private static String bytes2HexString(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        int len = bytes.length;
        if (len <= 0) {
            return null;
        }
        char[] ret = new char[(len << 1)];
        int j = 0;
        for (int i = 0; i < len; i++) {
            int j2 = j + 1;
            ret[j] = hexDigits[(bytes[i] >>> 4) & 15];
            j = j2 + 1;
            ret[j2] = hexDigits[bytes[i] & Ascii.SI];
        }
        return new String(ret);
    }

    @SuppressLint({"DefaultLocale"})
    private static String byte2FitMemorySize(long byteNum) {
        if (byteNum < 0) {
            return "shouldn't be less than zero!";
        }
        if (byteNum < 1024) {
            return String.format("%.3fB", new Object[]{Double.valueOf((double) byteNum)});
        } else if (byteNum < PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED) {
            return String.format("%.3fKB", new Object[]{Double.valueOf(((double) byteNum) / 1024.0d)});
        } else if (byteNum < 1073741824) {
            return String.format("%.3fMB", new Object[]{Double.valueOf(((double) byteNum) / 1048576.0d)});
        } else {
            return String.format("%.3fGB", new Object[]{Double.valueOf(((double) byteNum) / 1.073741824E9d)});
        }
    }

    private static boolean isSpace(String s) {
        if (s == null) {
            return true;
        }
        int len = s.length();
        for (int i = 0; i < len; i++) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static void clearInfoForFile(String fileName, String path) {
        try {
            File file = new File(Environment.getExternalStorageDirectory() + "/" + fileName);
            if (!file.exists()) {
                creatSDDir(path);
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    public static boolean checkFileExists(String name) {
        if (name.equals("")) {
            return false;
        }
        return new File(Environment.getExternalStorageDirectory().toString() + name).exists();
    }

    public static boolean checkFileExistsTwo(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        return new File(path).exists();
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x0099 A[SYNTHETIC, Splitter:B:30:0x0099] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x009e A[Catch:{ IOException -> 0x00af }] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00a6 A[Catch:{ IOException -> 0x00af }] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00ab A[Catch:{ IOException -> 0x00af }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean writeInputStreamToDisk(java.lang.String r13, java.lang.String r14, java.io.InputStream r15) {
        /*
            r8 = 1
            r9 = 0
            java.lang.String r10 = "licl"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r12 = "path>>>>>"
            java.lang.StringBuilder r11 = r11.append(r12)
            java.lang.StringBuilder r11 = r11.append(r13)
            java.lang.String r11 = r11.toString()
            android.util.Log.e(r10, r11)
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.io.File r11 = android.os.Environment.getExternalStorageDirectory()
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.StringBuilder r10 = r10.append(r13)
            java.lang.String r13 = r10.toString()
            java.io.File r4 = new java.io.File     // Catch:{ IOException -> 0x00af }
            r4.<init>(r13)     // Catch:{ IOException -> 0x00af }
            boolean r10 = r4.exists()     // Catch:{ IOException -> 0x00af }
            if (r10 != 0) goto L_0x003f
            r4.mkdirs()     // Catch:{ IOException -> 0x00af }
        L_0x003f:
            java.io.File r4 = new java.io.File     // Catch:{ IOException -> 0x00af }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00af }
            r10.<init>()     // Catch:{ IOException -> 0x00af }
            java.lang.StringBuilder r10 = r10.append(r13)     // Catch:{ IOException -> 0x00af }
            java.lang.StringBuilder r10 = r10.append(r14)     // Catch:{ IOException -> 0x00af }
            java.lang.String r10 = r10.toString()     // Catch:{ IOException -> 0x00af }
            r4.<init>(r10)     // Catch:{ IOException -> 0x00af }
            boolean r10 = r4.exists()     // Catch:{ IOException -> 0x00af }
            if (r10 == 0) goto L_0x005e
            r4.delete()     // Catch:{ IOException -> 0x00af }
        L_0x005e:
            r4.createNewFile()     // Catch:{ IOException -> 0x00af }
            r5 = 0
            r10 = 1024(0x400, float:1.435E-42)
            byte[] r1 = new byte[r10]     // Catch:{ IOException -> 0x008a }
            r2 = 0
            java.io.FileOutputStream r6 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x008a }
            r10 = 1
            r6.<init>(r4, r10)     // Catch:{ IOException -> 0x008a }
        L_0x006e:
            int r7 = r15.read(r1)     // Catch:{ IOException -> 0x00c1, all -> 0x00be }
            r10 = -1
            if (r7 != r10) goto L_0x0083
            r6.flush()     // Catch:{ IOException -> 0x00c1, all -> 0x00be }
            if (r15 == 0) goto L_0x007d
            r15.close()     // Catch:{ IOException -> 0x00af }
        L_0x007d:
            if (r6 == 0) goto L_0x0082
            r6.close()     // Catch:{ IOException -> 0x00af }
        L_0x0082:
            return r8
        L_0x0083:
            r10 = 0
            r6.write(r1, r10, r7)     // Catch:{ IOException -> 0x00c1, all -> 0x00be }
            long r10 = (long) r7
            long r2 = r2 + r10
            goto L_0x006e
        L_0x008a:
            r0 = move-exception
        L_0x008b:
            java.lang.String r8 = "licl"
            java.lang.String r10 = "path>>>>>1"
            android.util.Log.e(r8, r10)     // Catch:{ all -> 0x00a3 }
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)     // Catch:{ all -> 0x00a3 }
            if (r15 == 0) goto L_0x009c
            r15.close()     // Catch:{ IOException -> 0x00af }
        L_0x009c:
            if (r5 == 0) goto L_0x00a1
            r5.close()     // Catch:{ IOException -> 0x00af }
        L_0x00a1:
            r8 = r9
            goto L_0x0082
        L_0x00a3:
            r8 = move-exception
        L_0x00a4:
            if (r15 == 0) goto L_0x00a9
            r15.close()     // Catch:{ IOException -> 0x00af }
        L_0x00a9:
            if (r5 == 0) goto L_0x00ae
            r5.close()     // Catch:{ IOException -> 0x00af }
        L_0x00ae:
            throw r8     // Catch:{ IOException -> 0x00af }
        L_0x00af:
            r0 = move-exception
            java.lang.String r8 = "licl"
            java.lang.String r10 = "path>>>>>2"
            android.util.Log.e(r8, r10)
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            r8 = r9
            goto L_0x0082
        L_0x00be:
            r8 = move-exception
            r5 = r6
            goto L_0x00a4
        L_0x00c1:
            r0 = move-exception
            r5 = r6
            goto L_0x008b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.iwown.lib_common.file.FileUtils.writeInputStreamToDisk(java.lang.String, java.lang.String, java.io.InputStream):boolean");
    }

    public static void writeInputStreamToDisk2(String path, String fileName, InputStream inputStream) throws Exception {
        String path2 = Environment.getExternalStorageDirectory() + path;
        Log.e("licl", "path>>>>>" + path2);
        Log.e("licl", "filt_name>>>>>" + fileName);
        File futureStudioIconFile = new File(path2);
        if (!futureStudioIconFile.exists()) {
            futureStudioIconFile.mkdirs();
        }
        File futureStudioIconFile2 = new File(path2 + fileName);
        if (futureStudioIconFile2.exists()) {
            futureStudioIconFile2.delete();
        }
        futureStudioIconFile2.createNewFile();
        byte[] fileReader = new byte[1024];
        long fileSizeDownloaded = 0;
        OutputStream outputStream = new FileOutputStream(futureStudioIconFile2, true);
        while (true) {
            int read = inputStream.read(fileReader);
            if (read == -1) {
                outputStream.flush();
                return;
            } else {
                outputStream.write(fileReader, 0, read);
                fileSizeDownloaded += (long) read;
            }
        }
    }
}
