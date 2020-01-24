package com.iwown.lib_common.file;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.util.Log;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.socks.library.KLog;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.List;

public class FileIOUtils {
    private static final String LINE_SEP = System.getProperty("line.separator");
    private static int sBufferSize = 8192;

    private FileIOUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static boolean saveBitmap(String pathDir, String fileName, Bitmap bm) {
        Log.e("iwown", "保存图片");
        File f = new File(pathDir);
        if (!f.exists()) {
            f.mkdirs();
        }
        File file = new File(pathDir, fileName);
        if (file.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            bm.compress(CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            Log.i("iwown", "已经保存");
            return true;
        } catch (FileNotFoundException e) {
            ThrowableExtension.printStackTrace(e);
            return false;
        } catch (IOException e2) {
            ThrowableExtension.printStackTrace(e2);
            return false;
        }
    }

    public static boolean writeFileFromIS(String filePath, InputStream is) {
        return writeFileFromIS(getFileByPath(filePath), is, false);
    }

    public static boolean writeFileFromIS(String filePath, InputStream is, boolean append) {
        return writeFileFromIS(getFileByPath(filePath), is, append);
    }

    public static boolean writeFileFromIS(File file, InputStream is) {
        return writeFileFromIS(file, is, false);
    }

    public static boolean writeFileFromIS(File file, InputStream is, boolean append) {
        if (!createOrExistsFile(file) || is == null) {
            return false;
        }
        OutputStream os = null;
        try {
            OutputStream os2 = new BufferedOutputStream(new FileOutputStream(file, append));
            try {
                byte[] data = new byte[sBufferSize];
                while (true) {
                    int len = is.read(data, 0, sBufferSize);
                    if (len != -1) {
                        os2.write(data, 0, len);
                    } else {
                        CloseUtils.closeIO(is, os2);
                        return true;
                    }
                }
            } catch (IOException e) {
                e = e;
                os = os2;
                try {
                    ThrowableExtension.printStackTrace(e);
                    CloseUtils.closeIO(is, os);
                    return false;
                } catch (Throwable th) {
                    th = th;
                    CloseUtils.closeIO(is, os);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                os = os2;
                CloseUtils.closeIO(is, os);
                throw th;
            }
        } catch (IOException e2) {
            e = e2;
            ThrowableExtension.printStackTrace(e);
            CloseUtils.closeIO(is, os);
            return false;
        }
    }

    public static boolean writeFileFromBytesByStream(String filePath, byte[] bytes) {
        return writeFileFromBytesByStream(getFileByPath(filePath), bytes, false);
    }

    public static boolean writeFileFromBytesByStream(String filePath, byte[] bytes, boolean append) {
        return writeFileFromBytesByStream(getFileByPath(filePath), bytes, append);
    }

    public static boolean writeFileFromBytesByStream(File file, byte[] bytes) {
        return writeFileFromBytesByStream(file, bytes, false);
    }

    public static boolean writeFileFromBytesByStream(File file, byte[] bytes, boolean append) {
        if (bytes == null || !createOrExistsFile(file)) {
            return false;
        }
        BufferedOutputStream bos = null;
        try {
            BufferedOutputStream bos2 = new BufferedOutputStream(new FileOutputStream(file, append));
            try {
                bos2.write(bytes);
                CloseUtils.closeIO(bos2);
                return true;
            } catch (IOException e) {
                e = e;
                bos = bos2;
                try {
                    ThrowableExtension.printStackTrace(e);
                    CloseUtils.closeIO(bos);
                    return false;
                } catch (Throwable th) {
                    th = th;
                    CloseUtils.closeIO(bos);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                bos = bos2;
                CloseUtils.closeIO(bos);
                throw th;
            }
        } catch (IOException e2) {
            e = e2;
            ThrowableExtension.printStackTrace(e);
            CloseUtils.closeIO(bos);
            return false;
        }
    }

    public static boolean writeFileFromBytesByChannel(String filePath, byte[] bytes, boolean isForce) {
        return writeFileFromBytesByChannel(getFileByPath(filePath), bytes, false, isForce);
    }

    public static boolean writeFileFromBytesByChannel(String filePath, byte[] bytes, boolean append, boolean isForce) {
        return writeFileFromBytesByChannel(getFileByPath(filePath), bytes, append, isForce);
    }

    public static boolean writeFileFromBytesByChannel(File file, byte[] bytes, boolean isForce) {
        return writeFileFromBytesByChannel(file, bytes, false, isForce);
    }

    public static boolean writeFileFromBytesByChannel(File file, byte[] bytes, boolean append, boolean isForce) {
        if (bytes == null) {
            return false;
        }
        try {
            FileChannel fc = new FileOutputStream(file, append).getChannel();
            fc.position(fc.size());
            fc.write(ByteBuffer.wrap(bytes));
            if (isForce) {
                fc.force(true);
            }
            CloseUtils.closeIO(fc);
            return true;
        } catch (IOException e) {
            ThrowableExtension.printStackTrace(e);
            CloseUtils.closeIO(null);
            return false;
        } catch (Throwable th) {
            CloseUtils.closeIO(null);
            throw th;
        }
    }

    public static boolean writeFileFromBytesByMap(String filePath, byte[] bytes, boolean isForce) {
        return writeFileFromBytesByMap(filePath, bytes, false, isForce);
    }

    public static boolean writeFileFromBytesByMap(String filePath, byte[] bytes, boolean append, boolean isForce) {
        return writeFileFromBytesByMap(getFileByPath(filePath), bytes, append, isForce);
    }

    public static boolean writeFileFromBytesByMap(File file, byte[] bytes, boolean isForce) {
        return writeFileFromBytesByMap(file, bytes, false, isForce);
    }

    public static boolean writeFileFromBytesByMap(File file, byte[] bytes, boolean append, boolean isForce) {
        if (bytes == null || !createOrExistsFile(file)) {
            return false;
        }
        try {
            FileChannel fc = new FileOutputStream(file, append).getChannel();
            MappedByteBuffer mbb = fc.map(MapMode.READ_WRITE, fc.size(), (long) bytes.length);
            mbb.put(bytes);
            if (isForce) {
                mbb.force();
            }
            CloseUtils.closeIO(fc);
            return true;
        } catch (IOException e) {
            ThrowableExtension.printStackTrace(e);
            CloseUtils.closeIO(null);
            return false;
        } catch (Throwable th) {
            CloseUtils.closeIO(null);
            throw th;
        }
    }

    public static boolean writeFileFromString(String filePath, String content) {
        return writeFileFromString(getFileByPath(filePath), content, true);
    }

    public static boolean writeFileFromString(String filePath, String content, boolean append) {
        return writeFileFromString(getFileByPath(filePath), content, append);
    }

    public static boolean writeFileFromString(File file, String content) {
        return writeFileFromString(file, content, false);
    }

    public static boolean writeFileFromString(File file, String content, boolean append) {
        if (file == null || content == null || !createOrExistsFile(file)) {
            return false;
        }
        BufferedWriter bw = null;
        try {
            BufferedWriter bw2 = new BufferedWriter(new FileWriter(file, append));
            try {
                bw2.write(content);
                bw2.write("\r\n");
                bw2.flush();
                CloseUtils.closeIO(bw2);
                return true;
            } catch (IOException e) {
                e = e;
                bw = bw2;
                try {
                    ThrowableExtension.printStackTrace(e);
                    CloseUtils.closeIO(bw);
                    return false;
                } catch (Throwable th) {
                    th = th;
                    CloseUtils.closeIO(bw);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                bw = bw2;
                CloseUtils.closeIO(bw);
                throw th;
            }
        } catch (IOException e2) {
            e = e2;
            ThrowableExtension.printStackTrace(e);
            CloseUtils.closeIO(bw);
            return false;
        }
    }

    public static List<String> readFile2List(String filePath) {
        return readFile2List(getFileByPath(filePath), (String) null);
    }

    public static List<String> readFile2List(String filePath, String charsetName) {
        return readFile2List(getFileByPath(filePath), charsetName);
    }

    public static List<String> readFile2List(File file) {
        return readFile2List(file, 0, Integer.MAX_VALUE, (String) null);
    }

    public static List<String> readFile2List(File file, String charsetName) {
        return readFile2List(file, 0, Integer.MAX_VALUE, charsetName);
    }

    public static List<String> readFile2List(String filePath, int st, int end) {
        return readFile2List(getFileByPath(filePath), st, end, (String) null);
    }

    public static List<String> readFile2List(String filePath, int st, int end, String charsetName) {
        return readFile2List(getFileByPath(filePath), st, end, charsetName);
    }

    public static List<String> readFile2List(File file, int st, int end) {
        return readFile2List(file, st, end, (String) null);
    }

    /* JADX INFO: finally extract failed */
    public static List<String> readFile2List(File file, int st, int end, String charsetName) {
        BufferedReader reader;
        if (!isFileExists(file)) {
            return null;
        }
        if (st > end) {
            return null;
        }
        int curLine = 1;
        try {
            List<String> list = new ArrayList<>();
            if (isSpace(charsetName)) {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            } else {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charsetName));
            }
            while (true) {
                String line = reader.readLine();
                if (line == null || curLine > end) {
                    CloseUtils.closeIO(reader);
                } else {
                    if (st <= curLine && curLine <= end) {
                        list.add(line);
                    }
                    curLine++;
                }
            }
            CloseUtils.closeIO(reader);
            return list;
        } catch (IOException e) {
            ThrowableExtension.printStackTrace(e);
            CloseUtils.closeIO(null);
            return null;
        } catch (Throwable th) {
            CloseUtils.closeIO(null);
            throw th;
        }
    }

    public static String readFile2String(String filePath) {
        return readFile2String(getFileByPath(filePath), (String) null);
    }

    public static String readFile2String(String filePath, String charsetName) {
        return readFile2String(getFileByPath(filePath), charsetName);
    }

    public static String readFile2String(File file) {
        return readFile2String(file, (String) null);
    }

    public static String readFile2String(File file, String charsetName) {
        BufferedReader reader;
        String str = null;
        if (isFileExists(file)) {
            BufferedReader reader2 = null;
            try {
                StringBuilder sb = new StringBuilder();
                if (isSpace(charsetName)) {
                    reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                } else {
                    reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charsetName));
                }
                String line = reader2.readLine();
                if (line != null) {
                    sb.append(line);
                    while (true) {
                        String line2 = reader2.readLine();
                        if (line2 == null) {
                            break;
                        }
                        sb.append(LINE_SEP).append(line2);
                    }
                }
                str = sb.toString();
            } catch (IOException e) {
                ThrowableExtension.printStackTrace(e);
            } finally {
                CloseUtils.closeIO(reader2);
            }
        }
        return str;
    }

    public static byte[] readFile2BytesByStream(String filePath) {
        return readFile2BytesByStream(getFileByPath(filePath));
    }

    public static byte[] readFile2BytesByStream(File file) {
        byte[] bArr = null;
        if (isFileExists(file)) {
            FileInputStream fis = null;
            ByteArrayOutputStream os = null;
            try {
                FileInputStream fis2 = new FileInputStream(file);
                try {
                    ByteArrayOutputStream os2 = new ByteArrayOutputStream();
                    try {
                        byte[] b = new byte[sBufferSize];
                        while (true) {
                            int len = fis2.read(b, 0, sBufferSize);
                            if (len == -1) {
                                break;
                            }
                            os2.write(b, 0, len);
                        }
                        bArr = os2.toByteArray();
                        CloseUtils.closeIO(fis2, os2);
                    } catch (IOException e) {
                        e = e;
                        os = os2;
                        fis = fis2;
                        try {
                            ThrowableExtension.printStackTrace(e);
                            CloseUtils.closeIO(fis, os);
                            return bArr;
                        } catch (Throwable th) {
                            th = th;
                            CloseUtils.closeIO(fis, os);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        os = os2;
                        fis = fis2;
                        CloseUtils.closeIO(fis, os);
                        throw th;
                    }
                } catch (IOException e2) {
                    e = e2;
                    fis = fis2;
                    ThrowableExtension.printStackTrace(e);
                    CloseUtils.closeIO(fis, os);
                    return bArr;
                } catch (Throwable th3) {
                    th = th3;
                    fis = fis2;
                    CloseUtils.closeIO(fis, os);
                    throw th;
                }
            } catch (IOException e3) {
                e = e3;
                ThrowableExtension.printStackTrace(e);
                CloseUtils.closeIO(fis, os);
                return bArr;
            }
        }
        return bArr;
    }

    public static byte[] readFile2BytesByChannel(String filePath) {
        return readFile2BytesByChannel(getFileByPath(filePath));
    }

    public static byte[] readFile2BytesByChannel(File file) {
        byte[] bArr = null;
        if (isFileExists(file)) {
            FileChannel fc = null;
            try {
                fc = new RandomAccessFile(file, "r").getChannel();
                ByteBuffer byteBuffer = ByteBuffer.allocate((int) fc.size());
                do {
                } while (fc.read(byteBuffer) > 0);
                bArr = byteBuffer.array();
            } catch (IOException e) {
                ThrowableExtension.printStackTrace(e);
            } finally {
                CloseUtils.closeIO(fc);
            }
        }
        return bArr;
    }

    public static byte[] readFile2BytesByMap(String filePath) {
        return readFile2BytesByMap(getFileByPath(filePath));
    }

    /* JADX INFO: finally extract failed */
    public static byte[] readFile2BytesByMap(File file) {
        if (!isFileExists(file)) {
            return null;
        }
        FileChannel fc = null;
        try {
            fc = new RandomAccessFile(file, "r").getChannel();
            int size = (int) fc.size();
            byte[] result = new byte[size];
            fc.map(MapMode.READ_ONLY, 0, (long) size).load().get(result, 0, size);
            CloseUtils.closeIO(fc);
            return result;
        } catch (IOException e) {
            ThrowableExtension.printStackTrace(e);
            CloseUtils.closeIO(fc);
            return null;
        } catch (Throwable th) {
            CloseUtils.closeIO(fc);
            throw th;
        }
    }

    public static void setBufferSize(int bufferSize) {
        sBufferSize = bufferSize;
    }

    private static File getFileByPath(String filePath) {
        if (isSpace(filePath)) {
            return null;
        }
        return new File(filePath);
    }

    private static boolean createOrExistsFile(String filePath) {
        return createOrExistsFile(getFileByPath(filePath));
    }

    private static boolean createOrExistsFile(File file) {
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
            KLog.e("==" + file.getPath());
            return file.createNewFile();
        } catch (IOException e) {
            ThrowableExtension.printStackTrace(e);
            return z;
        }
    }

    private static boolean createOrExistsDir(File file) {
        return file != null && (!file.exists() ? file.mkdirs() : file.isDirectory());
    }

    private static boolean isFileExists(File file) {
        return file != null && file.exists();
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

    /* JADX WARNING: Removed duplicated region for block: B:16:0x003f A[SYNTHETIC, Splitter:B:16:0x003f] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x004b A[SYNTHETIC, Splitter:B:22:0x004b] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.io.File write2SDFromString(java.lang.String r5, java.lang.String r6, java.lang.String r7) {
        /*
            r1 = 0
            r2 = 0
            com.iwown.lib_common.file.FileUtils.creatSDDir(r5)     // Catch:{ Exception -> 0x0039 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0039 }
            r4.<init>()     // Catch:{ Exception -> 0x0039 }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Exception -> 0x0039 }
            java.lang.StringBuilder r4 = r4.append(r6)     // Catch:{ Exception -> 0x0039 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0039 }
            java.io.File r1 = com.iwown.lib_common.file.FileUtils.creatSDFile(r4)     // Catch:{ Exception -> 0x0039 }
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
            return r1
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
        throw new UnsupportedOperationException("Method not decompiled: com.iwown.lib_common.file.FileIOUtils.write2SDFromString(java.lang.String, java.lang.String, java.lang.String):java.io.File");
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x003e A[SYNTHETIC, Splitter:B:16:0x003e] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x004a A[SYNTHETIC, Splitter:B:22:0x004a] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.io.File write2SDFromString(java.lang.String r5, java.lang.String r6, java.lang.String r7, boolean r8) {
        /*
            r1 = 0
            r2 = 0
            com.iwown.lib_common.file.FileUtils.creatSDDir(r5)     // Catch:{ Exception -> 0x0038 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0038 }
            r4.<init>()     // Catch:{ Exception -> 0x0038 }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Exception -> 0x0038 }
            java.lang.StringBuilder r4 = r4.append(r6)     // Catch:{ Exception -> 0x0038 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0038 }
            java.io.File r1 = com.iwown.lib_common.file.FileUtils.creatSDFile(r4)     // Catch:{ Exception -> 0x0038 }
            java.io.FileWriter r3 = new java.io.FileWriter     // Catch:{ Exception -> 0x0038 }
            r3.<init>(r1, r8)     // Catch:{ Exception -> 0x0038 }
            r3.write(r7)     // Catch:{ Exception -> 0x0056, all -> 0x0053 }
            java.lang.String r4 = "\r\n"
            r3.write(r4)     // Catch:{ Exception -> 0x0056, all -> 0x0053 }
            r3.flush()     // Catch:{ Exception -> 0x0056, all -> 0x0053 }
            if (r3 == 0) goto L_0x0030
            r3.close()     // Catch:{ Exception -> 0x0032 }
        L_0x0030:
            r2 = r3
        L_0x0031:
            return r1
        L_0x0032:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            r2 = r3
            goto L_0x0031
        L_0x0038:
            r0 = move-exception
        L_0x0039:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)     // Catch:{ all -> 0x0047 }
            if (r2 == 0) goto L_0x0031
            r2.close()     // Catch:{ Exception -> 0x0042 }
            goto L_0x0031
        L_0x0042:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x0031
        L_0x0047:
            r4 = move-exception
        L_0x0048:
            if (r2 == 0) goto L_0x004d
            r2.close()     // Catch:{ Exception -> 0x004e }
        L_0x004d:
            throw r4
        L_0x004e:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x004d
        L_0x0053:
            r4 = move-exception
            r2 = r3
            goto L_0x0048
        L_0x0056:
            r0 = move-exception
            r2 = r3
            goto L_0x0039
        */
        throw new UnsupportedOperationException("Method not decompiled: com.iwown.lib_common.file.FileIOUtils.write2SDFromString(java.lang.String, java.lang.String, java.lang.String, boolean):java.io.File");
    }
}
