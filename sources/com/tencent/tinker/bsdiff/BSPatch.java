package com.tencent.tinker.bsdiff;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.zip.GZIPInputStream;

public class BSPatch {
    public static final int RETURN_DIFF_FILE_ERR = 2;
    public static final int RETURN_NEW_FILE_ERR = 4;
    public static final int RETURN_OLD_FILE_ERR = 3;
    public static final int RETURN_SUCCESS = 1;

    /* JADX INFO: finally extract failed */
    public static int patchLessMemory(RandomAccessFile oldFile, File newFile, File diffFile, int extLen) throws IOException {
        if (oldFile == null || oldFile.length() <= 0) {
            return 3;
        }
        if (newFile == null) {
            return 4;
        }
        if (diffFile == null || diffFile.length() <= 0) {
            return 2;
        }
        byte[] diffBytes = new byte[((int) diffFile.length())];
        InputStream diffInputStream = new FileInputStream(diffFile);
        try {
            BSUtil.readFromStream(diffInputStream, diffBytes, 0, diffBytes.length);
            diffInputStream.close();
            return patchLessMemory(oldFile, (int) oldFile.length(), diffBytes, diffBytes.length, newFile, extLen);
        } catch (Throwable th) {
            diffInputStream.close();
            throw th;
        }
    }

    public static int patchLessMemory(RandomAccessFile oldFile, int oldsize, byte[] diffBuf, int diffSize, File newFile, int extLen) throws IOException {
        if (oldFile == null || oldsize <= 0) {
            return 3;
        }
        if (newFile == null) {
            return 4;
        }
        if (diffBuf == null || diffSize <= 0) {
            return 2;
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(diffBuf, 0, diffSize);
        DataInputStream diffIn = new DataInputStream(byteArrayInputStream);
        diffIn.skip(8);
        long ctrlBlockLen = diffIn.readLong();
        long diffBlockLen = diffIn.readLong();
        int newsize = (int) diffIn.readLong();
        diffIn.close();
        InputStream in = new ByteArrayInputStream(diffBuf, 0, diffSize);
        in.skip(32);
        GZIPInputStream gZIPInputStream = new GZIPInputStream(in);
        DataInputStream ctrlBlockIn = new DataInputStream(gZIPInputStream);
        InputStream in2 = new ByteArrayInputStream(diffBuf, 0, diffSize);
        in2.skip(32 + ctrlBlockLen);
        InputStream diffBlockIn = new GZIPInputStream(in2);
        InputStream in3 = new ByteArrayInputStream(diffBuf, 0, diffSize);
        in3.skip(diffBlockLen + ctrlBlockLen + 32);
        InputStream extraBlockIn = new GZIPInputStream(in3);
        FileOutputStream fileOutputStream = new FileOutputStream(newFile);
        int oldpos = 0;
        int newpos = 0;
        try {
            int[] ctrl = new int[3];
            while (newpos < newsize) {
                for (int i = 0; i <= 2; i++) {
                    ctrl[i] = ctrlBlockIn.readInt();
                }
                if (ctrl[0] + newpos > newsize) {
                    fileOutputStream.close();
                    oldFile.close();
                    fileOutputStream.close();
                    return 2;
                }
                byte[] buffer = new byte[ctrl[0]];
                if (!BSUtil.readFromStream(diffBlockIn, buffer, 0, ctrl[0])) {
                    fileOutputStream.close();
                    oldFile.close();
                    fileOutputStream.close();
                    return 2;
                }
                byte[] oldBuffer = new byte[ctrl[0]];
                if (oldFile.read(oldBuffer, 0, ctrl[0]) < ctrl[0]) {
                    fileOutputStream.close();
                    oldFile.close();
                    fileOutputStream.close();
                    return 2;
                }
                for (int i2 = 0; i2 < ctrl[0]; i2++) {
                    if (oldpos + i2 >= 0 && oldpos + i2 < oldsize) {
                        buffer[i2] = (byte) (buffer[i2] + oldBuffer[i2]);
                    }
                }
                fileOutputStream.write(buffer);
                int newpos2 = newpos + ctrl[0];
                int oldpos2 = oldpos + ctrl[0];
                if (ctrl[1] + newpos2 > newsize) {
                    fileOutputStream.close();
                    oldFile.close();
                    fileOutputStream.close();
                    return 2;
                }
                byte[] buffer2 = new byte[ctrl[1]];
                if (!BSUtil.readFromStream(extraBlockIn, buffer2, 0, ctrl[1])) {
                    fileOutputStream.close();
                    return 2;
                }
                fileOutputStream.write(buffer2);
                fileOutputStream.flush();
                newpos = newpos2 + ctrl[1];
                oldpos = oldpos2 + ctrl[2];
                oldFile.seek((long) oldpos);
            }
            ctrlBlockIn.close();
            diffBlockIn.close();
            extraBlockIn.close();
            oldFile.close();
            fileOutputStream.close();
            return 1;
        } finally {
            oldFile.close();
            fileOutputStream.close();
        }
    }

    /* JADX INFO: finally extract failed */
    public static int patchFast(File oldFile, File newFile, File diffFile, int extLen) throws IOException {
        if (oldFile == null || oldFile.length() <= 0) {
            return 3;
        }
        if (newFile == null) {
            return 4;
        }
        if (diffFile == null || diffFile.length() <= 0) {
            return 2;
        }
        InputStream oldInputStream = new BufferedInputStream(new FileInputStream(oldFile));
        byte[] diffBytes = new byte[((int) diffFile.length())];
        InputStream diffInputStream = new FileInputStream(diffFile);
        try {
            BSUtil.readFromStream(diffInputStream, diffBytes, 0, diffBytes.length);
            diffInputStream.close();
            byte[] newBytes = patchFast(oldInputStream, (int) oldFile.length(), diffBytes, extLen);
            OutputStream newOutputStream = new FileOutputStream(newFile);
            try {
                newOutputStream.write(newBytes);
                newOutputStream.close();
                return 1;
            } catch (Throwable th) {
                newOutputStream.close();
                throw th;
            }
        } catch (Throwable th2) {
            diffInputStream.close();
            throw th2;
        }
    }

    /* JADX INFO: finally extract failed */
    public static int patchFast(InputStream oldInputStream, InputStream diffInputStream, File newFile) throws IOException {
        if (oldInputStream == null) {
            return 3;
        }
        if (newFile == null) {
            return 4;
        }
        if (diffInputStream == null) {
            return 2;
        }
        byte[] oldBytes = BSUtil.inputStreamToByte(oldInputStream);
        byte[] diffBytes = BSUtil.inputStreamToByte(diffInputStream);
        byte[] newBytes = patchFast(oldBytes, oldBytes.length, diffBytes, diffBytes.length, 0);
        OutputStream newOutputStream = new FileOutputStream(newFile);
        try {
            newOutputStream.write(newBytes);
            newOutputStream.close();
            return 1;
        } catch (Throwable th) {
            newOutputStream.close();
            throw th;
        }
    }

    public static byte[] patchFast(InputStream oldInputStream, InputStream diffInputStream) throws IOException {
        if (oldInputStream == null || diffInputStream == null) {
            return null;
        }
        byte[] oldBytes = BSUtil.inputStreamToByte(oldInputStream);
        byte[] diffBytes = BSUtil.inputStreamToByte(diffInputStream);
        return patchFast(oldBytes, oldBytes.length, diffBytes, diffBytes.length, 0);
    }

    public static byte[] patchFast(InputStream oldInputStream, int oldsize, byte[] diffBytes, int extLen) throws IOException {
        byte[] oldBuf = new byte[oldsize];
        BSUtil.readFromStream(oldInputStream, oldBuf, 0, oldsize);
        oldInputStream.close();
        return patchFast(oldBuf, oldsize, diffBytes, diffBytes.length, extLen);
    }

    public static byte[] patchFast(byte[] oldBuf, int oldsize, byte[] diffBuf, int diffSize, int extLen) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(diffBuf, 0, diffSize);
        DataInputStream diffIn = new DataInputStream(byteArrayInputStream);
        diffIn.skip(8);
        long ctrlBlockLen = diffIn.readLong();
        long diffBlockLen = diffIn.readLong();
        int newsize = (int) diffIn.readLong();
        diffIn.close();
        InputStream in = new ByteArrayInputStream(diffBuf, 0, diffSize);
        in.skip(32);
        GZIPInputStream gZIPInputStream = new GZIPInputStream(in);
        DataInputStream ctrlBlockIn = new DataInputStream(gZIPInputStream);
        InputStream in2 = new ByteArrayInputStream(diffBuf, 0, diffSize);
        in2.skip(32 + ctrlBlockLen);
        InputStream diffBlockIn = new GZIPInputStream(in2);
        InputStream in3 = new ByteArrayInputStream(diffBuf, 0, diffSize);
        in3.skip(diffBlockLen + ctrlBlockLen + 32);
        InputStream extraBlockIn = new GZIPInputStream(in3);
        byte[] newBuf = new byte[newsize];
        int oldpos = 0;
        int newpos = 0;
        int[] ctrl = new int[3];
        while (newpos < newsize) {
            for (int i = 0; i <= 2; i++) {
                ctrl[i] = ctrlBlockIn.readInt();
            }
            if (ctrl[0] + newpos > newsize) {
                throw new IOException("Corrupt by wrong patch file.");
            } else if (!BSUtil.readFromStream(diffBlockIn, newBuf, newpos, ctrl[0])) {
                throw new IOException("Corrupt by wrong patch file.");
            } else {
                for (int i2 = 0; i2 < ctrl[0]; i2++) {
                    if (oldpos + i2 >= 0 && oldpos + i2 < oldsize) {
                        int i3 = newpos + i2;
                        newBuf[i3] = (byte) (newBuf[i3] + oldBuf[oldpos + i2]);
                    }
                }
                int newpos2 = newpos + ctrl[0];
                int oldpos2 = oldpos + ctrl[0];
                if (ctrl[1] + newpos2 > newsize) {
                    throw new IOException("Corrupt by wrong patch file.");
                } else if (!BSUtil.readFromStream(extraBlockIn, newBuf, newpos2, ctrl[1])) {
                    throw new IOException("Corrupt by wrong patch file.");
                } else {
                    newpos = newpos2 + ctrl[1];
                    oldpos = oldpos2 + ctrl[2];
                }
            }
        }
        ctrlBlockIn.close();
        diffBlockIn.close();
        extraBlockIn.close();
        return newBuf;
    }
}
