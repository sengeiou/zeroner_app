package com.iwown.my_module.utility;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.android.arouter.utils.Consts;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.device_module.device_camera.exif.ExifInterface.GpsSpeedRef;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FileUtility {
    private static String SDPATH = (Environment.getExternalStorageDirectory() + "/");
    private String TAG = getClass().getSimpleName();

    public enum PathStatus {
        SUCCESS,
        EXITS,
        ERROR
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
        throw new UnsupportedOperationException("Method not decompiled: com.iwown.my_module.utility.FileUtility.writeInputStreamToDisk(java.lang.String, java.lang.String, java.io.InputStream):boolean");
    }

    public static void clearInfoForFile(String fileName) {
        File file = new File(fileName);
        try {
            if (!file.exists()) {
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

    public static void write(Context context, String fileName, String content) {
        if (content == null) {
            content = "";
        }
        try {
            FileOutputStream fos = context.openFileOutput(fileName, 0);
            fos.write(content.getBytes());
            fos.close();
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    public static boolean makeDirs(String filePath) {
        String folderName = getFolderName(filePath);
        if (TextUtils.isEmpty(folderName)) {
            return false;
        }
        File folder = new File(folderName);
        if (!folder.exists() || !folder.isDirectory()) {
            return folder.mkdirs();
        }
        return true;
    }

    public static String getFolderName(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }
        int filePosi = filePath.lastIndexOf(File.separator);
        return filePosi == -1 ? "" : filePath.substring(0, filePosi);
    }

    public static synchronized void mkdirFile(String path) throws IOException {
        synchronized (FileUtility.class) {
            if (!TextUtils.isEmpty(path)) {
                File fileTemp = new File(new File(path).getParent());
                if (!fileTemp.exists()) {
                    fileTemp.mkdirs();
                }
            }
        }
    }

    public static String read(Context context, String fileName) {
        try {
            return readInStream(context.openFileInput(fileName));
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return "";
        }
    }

    public static String readInStream(InputStream inStream) {
        try {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[512];
            while (true) {
                int length = inStream.read(buffer);
                if (length != -1) {
                    outStream.write(buffer, 0, length);
                } else {
                    outStream.close();
                    inStream.close();
                    return outStream.toString();
                }
            }
        } catch (IOException e) {
            Log.i("FileTest", e.getMessage());
            return null;
        }
    }

    public static File createFile(String folderPath, String fileName) {
        File destDir = new File(folderPath);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        return new File(folderPath, fileName);
    }

    public static void saveFile(String string, String filePath) {
        File saveFile = new File(filePath);
        if (!saveFile.exists()) {
            new File(saveFile.getParent()).mkdirs();
            try {
                saveFile.createNewFile();
            } catch (IOException e) {
                ThrowableExtension.printStackTrace(e);
            }
        }
        try {
            FileOutputStream outStream = new FileOutputStream(saveFile);
            try {
                outStream.write(string.getBytes());
            } catch (IOException e2) {
                ThrowableExtension.printStackTrace(e2);
            }
            try {
                outStream.close();
            } catch (IOException e3) {
                ThrowableExtension.printStackTrace(e3);
            }
        } catch (FileNotFoundException e4) {
            ThrowableExtension.printStackTrace(e4);
        }
    }

    public static boolean writeFile(byte[] buffer, String folder, String fileName) {
        boolean writeSucc = false;
        String folderPath = "";
        if (Environment.getExternalStorageState().equals("mounted")) {
            folderPath = Environment.getExternalStorageDirectory() + File.separator + folder + File.separator;
        } else {
            writeSucc = false;
        }
        File fileDir = new File(folderPath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        FileOutputStream out = null;
        try {
            FileOutputStream out2 = new FileOutputStream(new File(folderPath + fileName));
            try {
                out2.write(buffer);
                writeSucc = true;
                try {
                    out2.close();
                    FileOutputStream fileOutputStream = out2;
                } catch (IOException e) {
                    ThrowableExtension.printStackTrace(e);
                    FileOutputStream fileOutputStream2 = out2;
                }
            } catch (Exception e2) {
                e = e2;
                out = out2;
                try {
                    ThrowableExtension.printStackTrace(e);
                    try {
                        out.close();
                    } catch (IOException e3) {
                        ThrowableExtension.printStackTrace(e3);
                    }
                    return writeSucc;
                } catch (Throwable th) {
                    th = th;
                    try {
                        out.close();
                    } catch (IOException e4) {
                        ThrowableExtension.printStackTrace(e4);
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                out = out2;
                out.close();
                throw th;
            }
        } catch (Exception e5) {
            e = e5;
            ThrowableExtension.printStackTrace(e);
            out.close();
            return writeSucc;
        }
        return writeSucc;
    }

    public static String getFileName(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return "";
        }
        return filePath.substring(filePath.lastIndexOf(File.separator) + 1);
    }

    public static String getFileNameNoFormat(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return "";
        }
        return filePath.substring(filePath.lastIndexOf(File.separator) + 1, filePath.lastIndexOf(46));
    }

    public static void method1(String file, String conent) {
        BufferedWriter out = null;
        try {
            BufferedWriter out2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false)));
            try {
                out2.write(conent);
                try {
                    out2.close();
                    BufferedWriter bufferedWriter = out2;
                } catch (IOException e) {
                    ThrowableExtension.printStackTrace(e);
                    BufferedWriter bufferedWriter2 = out2;
                }
            } catch (Exception e2) {
                e = e2;
                out = out2;
                try {
                    ThrowableExtension.printStackTrace(e);
                    try {
                        out.close();
                    } catch (IOException e3) {
                        ThrowableExtension.printStackTrace(e3);
                    }
                } catch (Throwable th) {
                    th = th;
                    try {
                        out.close();
                    } catch (IOException e4) {
                        ThrowableExtension.printStackTrace(e4);
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                out = out2;
                out.close();
                throw th;
            }
        } catch (Exception e5) {
            e = e5;
            ThrowableExtension.printStackTrace(e);
            out.close();
        }
    }

    public static String getFileFormat(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(46) + 1);
    }

    public static long getFileSize(String filePath) {
        File file = new File(filePath);
        if (file == null || !file.exists()) {
            return 0;
        }
        return file.length();
    }

    public static String getFileSize(long size) {
        if (size <= 0) {
            return "0";
        }
        DecimalFormat df = new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.ENGLISH));
        float temp = ((float) size) / 1024.0f;
        if (temp >= 1024.0f) {
            return df.format((double) (temp / 1024.0f)) + "M";
        }
        return df.format((double) temp) + GpsSpeedRef.KILOMETERS;
    }

    public static String formatFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00", new DecimalFormatSymbols(Locale.ENGLISH));
        String str = "";
        if (fileS < 1024) {
            return df.format((double) fileS) + "B";
        }
        if (fileS < PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED) {
            return df.format(((double) fileS) / 1024.0d) + "KB";
        }
        if (fileS < 1073741824) {
            return df.format(((double) fileS) / 1048576.0d) + "MB";
        }
        return df.format(((double) fileS) / 1.073741824E9d) + "G";
    }

    public static long getDirSize(File dir) {
        File[] files;
        long dirSize = 0;
        if (dir != null && dir.isDirectory()) {
            dirSize = 0;
            for (File file : dir.listFiles()) {
                if (file.isFile()) {
                    dirSize += file.length();
                } else if (file.isDirectory()) {
                    dirSize = dirSize + file.length() + getDirSize(file);
                }
            }
        }
        return dirSize;
    }

    public long getFileList(File dir) {
        File[] files = dir.listFiles();
        long count = (long) files.length;
        for (File file : files) {
            if (file.isDirectory()) {
                count = (count + getFileList(file)) - 1;
            }
        }
        return count;
    }

    public static byte[] toBytes(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        while (true) {
            int ch = in.read();
            if (ch != -1) {
                out.write(ch);
            } else {
                byte[] buffer = out.toByteArray();
                out.close();
                return buffer;
            }
        }
    }

    public static boolean checkFileExists(String name) {
        if (name.equals("")) {
            return false;
        }
        return new File(Environment.getExternalStorageDirectory().toString() + name).exists();
    }

    public static boolean checkFilePathExists(String path) {
        return new File(path).exists();
    }

    public static long getFreeDiskSpace() {
        long freeSpace = 0;
        if (!Environment.getExternalStorageState().equals("mounted")) {
            return -1;
        }
        try {
            StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
            freeSpace = (((long) stat.getAvailableBlocks()) * ((long) stat.getBlockSize())) / 1024;
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
        return freeSpace;
    }

    public static boolean createDirectory(String directoryName) {
        if (directoryName.equals("")) {
            return false;
        }
        boolean mkdir = new File(Environment.getExternalStorageDirectory().toString() + directoryName).mkdir();
        return true;
    }

    public static boolean checkSaveLocationExists() {
        if (Environment.getExternalStorageState().equals("mounted")) {
            return true;
        }
        return false;
    }

    public static boolean checkExternalSDExists() {
        return System.getenv().containsKey("SECONDARY_STORAGE");
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

    public static boolean deleteDirectory(String fileName) {
        SecurityManager checker = new SecurityManager();
        if (fileName.equals("")) {
            return false;
        }
        File newPath = new File(Environment.getExternalStorageDirectory().toString() + fileName);
        checker.checkDelete(newPath.toString());
        if (!newPath.isDirectory()) {
            return false;
        }
        String[] listfile = newPath.list();
        int i = 0;
        while (i < listfile.length) {
            try {
                new File(newPath.toString() + "/" + listfile[i].toString()).delete();
                i++;
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
                return false;
            }
        }
        newPath.delete();
        Log.i("deleteDirectory", fileName);
        return true;
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
            Log.i("deleteFile", fileName);
            newPath.delete();
            return true;
        } catch (SecurityException se) {
            ThrowableExtension.printStackTrace(se);
            return false;
        }
    }

    public static int deleteBlankPath(String path) {
        File f = new File(path);
        if (!f.canWrite()) {
            return 1;
        }
        if (f.list() != null && f.list().length > 0) {
            return 2;
        }
        if (f.delete()) {
            return 0;
        }
        return 3;
    }

    public static boolean reNamePath(String oldName, String newName) {
        return new File(oldName).renameTo(new File(newName));
    }

    public static boolean deleteFileWithPath(String filePath) {
        SecurityManager checker = new SecurityManager();
        File f = new File(filePath);
        checker.checkDelete(filePath);
        if (!f.isFile()) {
            return false;
        }
        Log.i("deleteFile", filePath);
        f.delete();
        return true;
    }

    public static void clearFileWithPath(String filePath) {
        List<File> files = listPathFiles(filePath);
        if (!files.isEmpty()) {
            for (File f : files) {
                if (f.isDirectory()) {
                    clearFileWithPath(f.getAbsolutePath());
                } else {
                    f.delete();
                }
            }
        }
    }

    public String getSDPATH() {
        return SDPATH;
    }

    public static File creatSDFile(String fileName) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    public static File creatSDDir(String dirName) {
        File dir = new File(dirName);
        dir.mkdirs();
        return dir;
    }

    public boolean isFileExist(String fileName) {
        return new File(SDPATH + fileName).exists();
    }

    public static File write2SDFromInput(String path, String fileName, InputStream input) {
        File file = null;
        OutputStream output = null;
        try {
            creatSDDir(path);
            file = creatSDFile(path + fileName);
            OutputStream output2 = new FileOutputStream(file, true);
            try {
                byte[] buffer = new byte[30];
                while (input.read(buffer) != -1) {
                    output2.write(buffer);
                }
                output2.flush();
                try {
                    output2.close();
                    OutputStream outputStream = output2;
                } catch (Exception e) {
                    ThrowableExtension.printStackTrace(e);
                    OutputStream outputStream2 = output2;
                }
            } catch (Exception e2) {
                e = e2;
                output = output2;
                try {
                    ThrowableExtension.printStackTrace(e);
                    try {
                        output.close();
                    } catch (Exception e3) {
                        ThrowableExtension.printStackTrace(e3);
                    }
                    return file;
                } catch (Throwable th) {
                    th = th;
                    try {
                        output.close();
                    } catch (Exception e4) {
                        ThrowableExtension.printStackTrace(e4);
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                output = output2;
                output.close();
                throw th;
            }
        } catch (Exception e5) {
            e = e5;
            ThrowableExtension.printStackTrace(e);
            output.close();
            return file;
        }
        return file;
    }

    public static InputStream StringTOInputStream(String in) throws Exception {
        return new ByteArrayInputStream(in.getBytes("utf-8"));
    }

    public static String getSDRoot() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    public static String getExternalSDRoot() {
        return (String) System.getenv().get("SECONDARY_STORAGE");
    }

    public static List<String> listPath(String root) {
        File[] listFiles;
        List<String> allDir = new ArrayList<>();
        SecurityManager checker = new SecurityManager();
        File path = new File(root);
        checker.checkRead(root);
        if (path.isDirectory()) {
            for (File f : path.listFiles()) {
                if (f.isDirectory() && !f.getName().startsWith(Consts.DOT)) {
                    allDir.add(f.getAbsolutePath());
                }
            }
        }
        return allDir;
    }

    public static List<File> listPathFiles(String root) {
        File[] files;
        List<File> allDir = new ArrayList<>();
        SecurityManager checker = new SecurityManager();
        File path = new File(root);
        checker.checkRead(root);
        for (File f : path.listFiles()) {
            if (f.isFile()) {
                allDir.add(f);
            } else {
                listPath(f.getAbsolutePath());
            }
        }
        return allDir;
    }

    public static PathStatus createPath(String newPath) {
        File path = new File(newPath);
        if (path.exists()) {
            return PathStatus.EXITS;
        }
        if (path.mkdir()) {
            return PathStatus.SUCCESS;
        }
        return PathStatus.ERROR;
    }

    public static String getPathName(String absolutePath) {
        return absolutePath.substring(absolutePath.lastIndexOf(File.separator) + 1, absolutePath.length());
    }

    public static String getAppCache(Context context, String dir) {
        String savePath = context.getCacheDir().getAbsolutePath() + "/" + dir + "/";
        File savedir = new File(savePath);
        if (!savedir.exists()) {
            savedir.mkdirs();
        }
        return savePath;
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x003f A[SYNTHETIC, Splitter:B:15:0x003f] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x004b A[SYNTHETIC, Splitter:B:21:0x004b] */
    /* JADX WARNING: Removed duplicated region for block: B:32:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void write2SDFromString(java.lang.String r5, java.lang.String r6, java.lang.String r7) {
        /*
            r1 = 0
            r2 = 0
            creatSDDir(r5)     // Catch:{ Exception -> 0x0039 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0039 }
            r4.<init>()     // Catch:{ Exception -> 0x0039 }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Exception -> 0x0039 }
            java.lang.StringBuilder r4 = r4.append(r6)     // Catch:{ Exception -> 0x0039 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0039 }
            java.io.File r1 = creatSDFile(r4)     // Catch:{ Exception -> 0x0039 }
            java.io.FileWriter r3 = new java.io.FileWriter     // Catch:{ Exception -> 0x0039 }
            r4 = 1
            r3.<init>(r1, r4)     // Catch:{ Exception -> 0x0039 }
            r3.write(r7)     // Catch:{ Exception -> 0x0057, all -> 0x0054 }
            java.lang.String r4 = "\r\n"
            r3.write(r4)     // Catch:{ Exception -> 0x0057, all -> 0x0054 }
            r3.flush()     // Catch:{ Exception -> 0x0057, all -> 0x0054 }
            if (r3 == 0) goto L_0x0031
            r3.close()     // Catch:{ Exception -> 0x0033 }
        L_0x0031:
            r2 = r3
        L_0x0032:
            return
        L_0x0033:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            r2 = r3
            goto L_0x0032
        L_0x0039:
            r0 = move-exception
        L_0x003a:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)     // Catch:{ all -> 0x0048 }
            if (r2 == 0) goto L_0x0032
            r2.close()     // Catch:{ Exception -> 0x0043 }
            goto L_0x0032
        L_0x0043:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x0032
        L_0x0048:
            r4 = move-exception
        L_0x0049:
            if (r2 == 0) goto L_0x004e
            r2.close()     // Catch:{ Exception -> 0x004f }
        L_0x004e:
            throw r4
        L_0x004f:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x004e
        L_0x0054:
            r4 = move-exception
            r2 = r3
            goto L_0x0049
        L_0x0057:
            r0 = move-exception
            r2 = r3
            goto L_0x003a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.iwown.my_module.utility.FileUtility.write2SDFromString(java.lang.String, java.lang.String, java.lang.String):void");
    }
}
