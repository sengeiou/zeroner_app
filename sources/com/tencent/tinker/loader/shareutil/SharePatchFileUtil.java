package com.tencent.tinker.loader.shareutil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build.VERSION;
import android.util.Log;
import com.alibaba.android.arouter.utils.Consts;
import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import com.tencent.tinker.loader.TinkerRuntimeException;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

public class SharePatchFileUtil {
    private static final String TAG = "Tinker.PatchFileUtil";
    private static char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static File getPatchDirectory(Context context) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        if (applicationInfo == null) {
            return null;
        }
        return new File(applicationInfo.dataDir, ShareConstants.PATCH_DIRECTORY_NAME);
    }

    public static File getPatchTempDirectory(Context context) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        if (applicationInfo == null) {
            return null;
        }
        return new File(applicationInfo.dataDir, ShareConstants.PATCH_TEMP_DIRECTORY_NAME);
    }

    public static File getPatchLastCrashFile(Context context) {
        File tempFile = getPatchTempDirectory(context);
        if (tempFile == null) {
            return null;
        }
        return new File(tempFile, ShareConstants.PATCH_TEMP_LAST_CRASH_NAME);
    }

    public static File getPatchInfoFile(String patchDirectory) {
        return new File(patchDirectory + "/" + ShareConstants.PATCH_INFO_NAME);
    }

    public static File getPatchInfoLockFile(String patchDirectory) {
        return new File(patchDirectory + "/" + ShareConstants.PATCH_INFO_LOCK_NAME);
    }

    public static String getPatchVersionDirectory(String version) {
        if (version == null || version.length() != 32) {
            return null;
        }
        return ShareConstants.PATCH_BASE_NAME + version.substring(0, 8);
    }

    public static String getPatchVersionFile(String version) {
        if (version == null || version.length() != 32) {
            return null;
        }
        return getPatchVersionDirectory(version) + ShareConstants.PATCH_SUFFIX;
    }

    public static boolean checkIfMd5Valid(String object) {
        if (object == null || object.length() != 32) {
            return false;
        }
        return true;
    }

    public static String checkTinkerLastUncaughtCrash(Context context) {
        File crashFile = getPatchLastCrashFile(context);
        if (!isLegalFile(crashFile)) {
            return null;
        }
        StringBuffer buffer = new StringBuffer();
        BufferedReader in = null;
        try {
            BufferedReader in2 = new BufferedReader(new InputStreamReader(new FileInputStream(crashFile)));
            while (true) {
                try {
                    String line = in2.readLine();
                    if (line != null) {
                        buffer.append(line);
                        buffer.append("\n");
                    } else {
                        closeQuietly(in2);
                        return buffer.toString();
                    }
                } catch (Exception e) {
                    e = e;
                    in = in2;
                    try {
                        Log.e(TAG, "checkTinkerLastUncaughtCrash exception: " + e);
                        closeQuietly(in);
                        return null;
                    } catch (Throwable th) {
                        th = th;
                        closeQuietly(in);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    in = in2;
                    closeQuietly(in);
                    throw th;
                }
            }
        } catch (Exception e2) {
            e = e2;
            Log.e(TAG, "checkTinkerLastUncaughtCrash exception: " + e);
            closeQuietly(in);
            return null;
        }
    }

    @SuppressLint({"NewApi"})
    public static void closeQuietly(Object obj) {
        if (obj != null) {
            if (obj instanceof Closeable) {
                try {
                    ((Closeable) obj).close();
                } catch (Throwable th) {
                }
            } else if (VERSION.SDK_INT >= 19 && (obj instanceof AutoCloseable)) {
                try {
                    ((AutoCloseable) obj).close();
                } catch (Throwable th2) {
                }
            } else if (obj instanceof ZipFile) {
                try {
                    ((ZipFile) obj).close();
                } catch (Throwable th3) {
                }
            } else {
                throw new IllegalArgumentException("obj: " + obj + " cannot be closed.");
            }
        }
    }

    public static final boolean isLegalFile(File file) {
        return file != null && file.exists() && file.canRead() && file.isFile() && file.length() > 0;
    }

    public static long getFileOrDirectorySize(File directory) {
        long length;
        if (directory == null || !directory.exists()) {
            return 0;
        }
        if (directory.isFile()) {
            return directory.length();
        }
        long totalSize = 0;
        File[] fileList = directory.listFiles();
        if (fileList == null) {
            return 0;
        }
        for (File file : fileList) {
            if (file.isDirectory()) {
                length = getFileOrDirectorySize(file);
            } else {
                length = file.length();
            }
            totalSize += length;
        }
        return totalSize;
    }

    public static final boolean safeDeleteFile(File file) {
        boolean deleted = true;
        if (file != null && file.exists()) {
            Log.i(TAG, "safeDeleteFile, try to delete path: " + file.getPath());
            deleted = file.delete();
            if (!deleted) {
                Log.e(TAG, "Failed to delete file, try to delete when exit. path: " + file.getPath());
                file.deleteOnExit();
            }
        }
        return deleted;
    }

    public static final boolean deleteDir(String dir) {
        if (dir == null) {
            return false;
        }
        return deleteDir(new File(dir));
    }

    public static final boolean deleteDir(File file) {
        if (file == null || !file.exists()) {
            return false;
        }
        if (file.isFile()) {
            safeDeleteFile(file);
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File subFile : files) {
                    deleteDir(subFile);
                }
                safeDeleteFile(file);
            }
        }
        return true;
    }

    public static boolean verifyFileMd5(File file, String md5) {
        if (md5 == null) {
            return false;
        }
        String fileMd5 = getMD5(file);
        if (fileMd5 != null) {
            return md5.equals(fileMd5);
        }
        return false;
    }

    public static boolean isRawDexFile(String fileName) {
        if (fileName == null) {
            return false;
        }
        return fileName.endsWith(ShareConstants.DEX_SUFFIX);
    }

    public static boolean verifyDexFileMd5(File file, String md5) {
        return verifyDexFileMd5(file, "classes.dex", md5);
    }

    public static boolean verifyDexFileMd5(File file, String entryName, String md5) {
        if (file == null || md5 == null || entryName == null) {
            return false;
        }
        String fileMd5 = "";
        if (isRawDexFile(file.getName())) {
            fileMd5 = getMD5(file);
        } else {
            ZipFile dexJar = null;
            try {
                ZipFile dexJar2 = new ZipFile(file);
                try {
                    ZipEntry classesDex = dexJar2.getEntry(entryName);
                    if (classesDex == null) {
                        Log.e(TAG, "There's no entry named: classes.dex in " + file.getAbsolutePath());
                        closeZip(dexJar2);
                        return false;
                    }
                    InputStream is = null;
                    try {
                        is = dexJar2.getInputStream(classesDex);
                        fileMd5 = getMD5(is);
                    } catch (Throwable e) {
                        Log.e(TAG, "exception occurred when get md5: " + file.getAbsolutePath(), e);
                    } finally {
                        closeQuietly(is);
                    }
                    closeZip(dexJar2);
                } catch (Throwable th) {
                    th = th;
                    dexJar = dexJar2;
                    closeZip(dexJar);
                    throw th;
                }
            } catch (Throwable th2) {
                e = th2;
                try {
                    Log.e(TAG, "Bad dex jar file: " + file.getAbsolutePath(), e);
                    closeZip(dexJar);
                    return false;
                } catch (Throwable th3) {
                    th = th3;
                    closeZip(dexJar);
                    throw th;
                }
            }
        }
        return md5.equals(fileMd5);
    }

    public static void copyFileUsingStream(File source, File dest) throws IOException {
        FileOutputStream os;
        if (isLegalFile(source) && dest != null && !source.getAbsolutePath().equals(dest.getAbsolutePath())) {
            FileInputStream is = null;
            FileOutputStream os2 = null;
            File parent = dest.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            try {
                FileInputStream is2 = new FileInputStream(source);
                try {
                    os = new FileOutputStream(dest, false);
                } catch (Throwable th) {
                    th = th;
                    is = is2;
                    closeQuietly(is);
                    closeQuietly(os2);
                    throw th;
                }
                try {
                    byte[] buffer = new byte[16384];
                    while (true) {
                        int length = is2.read(buffer);
                        if (length > 0) {
                            os.write(buffer, 0, length);
                        } else {
                            closeQuietly(is2);
                            closeQuietly(os);
                            return;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    os2 = os;
                    is = is2;
                    closeQuietly(is);
                    closeQuietly(os2);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                closeQuietly(is);
                closeQuietly(os2);
                throw th;
            }
        }
    }

    public static String loadDigestes(JarFile jarFile, JarEntry je) throws Exception {
        InputStream bis = null;
        StringBuilder sb = new StringBuilder();
        try {
            byte[] bytes = new byte[16384];
            InputStream bis2 = new BufferedInputStream(jarFile.getInputStream(je));
            while (true) {
                try {
                    int readBytes = bis2.read(bytes);
                    if (readBytes > 0) {
                        sb.append(new String(bytes, 0, readBytes));
                    } else {
                        closeQuietly(bis2);
                        return sb.toString();
                    }
                } catch (Throwable th) {
                    th = th;
                    bis = bis2;
                    closeQuietly(bis);
                    throw th;
                }
            }
        } catch (Throwable th2) {
            th = th2;
            closeQuietly(bis);
            throw th;
        }
    }

    public static final String getMD5(InputStream is) {
        if (is == null) {
            return null;
        }
        try {
            BufferedInputStream bis = new BufferedInputStream(is);
            MessageDigest md = MessageDigest.getInstance(MessageDigestAlgorithms.MD5);
            StringBuilder md5Str = new StringBuilder(32);
            byte[] buf = new byte[ShareConstants.MD5_FILE_BUF_LENGTH];
            while (true) {
                int readCount = bis.read(buf);
                if (readCount == -1) {
                    break;
                }
                md.update(buf, 0, readCount);
            }
            byte[] hashValue = md.digest();
            for (byte b : hashValue) {
                md5Str.append(Integer.toString((b & UnsignedBytes.MAX_VALUE) + 256, 16).substring(1));
            }
            return md5Str.toString();
        } catch (Exception e) {
            return null;
        }
    }

    public static String getMD5(byte[] buffer) {
        byte[] md;
        try {
            MessageDigest mdTemp = MessageDigest.getInstance(MessageDigestAlgorithms.MD5);
            mdTemp.update(buffer);
            char[] str = new char[(j * 2)];
            int k = 0;
            for (byte byte0 : mdTemp.digest()) {
                int k2 = k + 1;
                str[k] = hexDigits[(byte0 >>> 4) & 15];
                k = k2 + 1;
                str[k2] = hexDigits[byte0 & Ascii.SI];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getMD5(File file) {
        String str = null;
        if (file != null && file.exists()) {
            FileInputStream fin = null;
            try {
                FileInputStream fin2 = new FileInputStream(file);
                try {
                    str = getMD5((InputStream) fin2);
                    closeQuietly(fin2);
                } catch (Exception e) {
                    fin = fin2;
                    closeQuietly(fin);
                    return str;
                } catch (Throwable th) {
                    th = th;
                    fin = fin2;
                    closeQuietly(fin);
                    throw th;
                }
            } catch (Exception e2) {
                closeQuietly(fin);
                return str;
            } catch (Throwable th2) {
                th = th2;
                closeQuietly(fin);
                throw th;
            }
        }
        return str;
    }

    public static String optimizedPathFor(File path, File optimizedDirectory) {
        if (ShareTinkerInternals.isAfterAndroidO()) {
            try {
                String currentInstructionSet = ShareTinkerInternals.getCurrentInstructionSet();
                File parentFile = path.getParentFile();
                String fileName = path.getName();
                int index = fileName.lastIndexOf(46);
                if (index > 0) {
                    fileName = fileName.substring(0, index);
                }
                return parentFile.getAbsolutePath() + "/oat/" + currentInstructionSet + "/" + fileName + ShareConstants.ODEX_SUFFIX;
            } catch (Exception e) {
                throw new TinkerRuntimeException("getCurrentInstructionSet fail:", e);
            }
        } else {
            String fileName2 = path.getName();
            if (!fileName2.endsWith(ShareConstants.DEX_SUFFIX)) {
                int lastDot = fileName2.lastIndexOf(Consts.DOT);
                if (lastDot < 0) {
                    fileName2 = fileName2 + ShareConstants.DEX_SUFFIX;
                } else {
                    StringBuilder sb = new StringBuilder(lastDot + 4);
                    sb.append(fileName2, 0, lastDot);
                    sb.append(ShareConstants.DEX_SUFFIX);
                    fileName2 = sb.toString();
                }
            }
            return new File(optimizedDirectory, fileName2).getPath();
        }
    }

    public static void closeZip(ZipFile zipFile) {
        if (zipFile != null) {
            try {
                zipFile.close();
            } catch (IOException e) {
                Log.w(TAG, "Failed to close resource", e);
            }
        }
    }

    public static boolean checkResourceArscMd5(File resOutput, String destMd5) {
        ZipFile resourceZip = null;
        try {
            ZipFile resourceZip2 = new ZipFile(resOutput);
            try {
                ZipEntry arscEntry = resourceZip2.getEntry(ShareConstants.RES_ARSC);
                if (arscEntry == null) {
                    Log.i(TAG, "checkResourceArscMd5 resources.arsc not found");
                    closeZip(resourceZip2);
                    ZipFile zipFile = resourceZip2;
                    return false;
                }
                InputStream inputStream = resourceZip2.getInputStream(arscEntry);
                String md5 = getMD5(inputStream);
                if (md5 == null || !md5.equals(destMd5)) {
                    closeQuietly(inputStream);
                    closeZip(resourceZip2);
                    ZipFile zipFile2 = resourceZip2;
                    return false;
                }
                closeQuietly(inputStream);
                closeZip(resourceZip2);
                ZipFile zipFile3 = resourceZip2;
                return true;
            } catch (Throwable th) {
                th = th;
                resourceZip = resourceZip2;
                closeZip(resourceZip);
                throw th;
            }
        } catch (Throwable th2) {
            e = th2;
            Log.i(TAG, "checkResourceArscMd5 throwable:" + e.getMessage());
            closeZip(resourceZip);
            return false;
        }
    }

    public static void ensureFileDirectory(File file) {
        if (file != null) {
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
        }
    }
}
