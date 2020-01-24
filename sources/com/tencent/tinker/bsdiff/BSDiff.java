package com.tencent.tinker.bsdiff;

import com.google.common.primitives.UnsignedBytes;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

public class BSDiff {
    private static final byte[] MAGIC_BYTES = {77, 105, 99, 114, 111, 77, 115, 103};

    private static class IntByRef {
        /* access modifiers changed from: private */
        public int value;

        private IntByRef() {
        }
    }

    private static void split(int[] arrayI, int[] arrayV, int start, int len, int h) {
        int j;
        if (len < 16) {
            for (int k = start; k < start + len; k += j) {
                j = 1;
                int x = arrayV[arrayI[k] + h];
                for (int i = 1; k + i < start + len; i++) {
                    if (arrayV[arrayI[k + i] + h] < x) {
                        x = arrayV[arrayI[k + i] + h];
                        j = 0;
                    }
                    if (arrayV[arrayI[k + i] + h] == x) {
                        int tmp = arrayI[k + j];
                        arrayI[k + j] = arrayI[k + i];
                        arrayI[k + i] = tmp;
                        j++;
                    }
                }
                for (int i2 = 0; i2 < j; i2++) {
                    arrayV[arrayI[k + i2]] = (k + j) - 1;
                }
                if (j == 1) {
                    arrayI[k] = -1;
                }
            }
            return;
        }
        int x2 = arrayV[arrayI[(len / 2) + start] + h];
        int jj = 0;
        int kk = 0;
        for (int i3 = start; i3 < start + len; i3++) {
            if (arrayV[arrayI[i3] + h] < x2) {
                jj++;
            }
            if (arrayV[arrayI[i3] + h] == x2) {
                kk++;
            }
        }
        int jj2 = jj + start;
        int kk2 = kk + jj2;
        int i4 = start;
        int j2 = 0;
        int k2 = 0;
        while (i4 < jj2) {
            if (arrayV[arrayI[i4] + h] < x2) {
                i4++;
            } else if (arrayV[arrayI[i4] + h] == x2) {
                int tmp2 = arrayI[i4];
                arrayI[i4] = arrayI[jj2 + j2];
                arrayI[jj2 + j2] = tmp2;
                j2++;
            } else {
                int tmp3 = arrayI[i4];
                arrayI[i4] = arrayI[kk2 + k2];
                arrayI[kk2 + k2] = tmp3;
                k2++;
            }
        }
        while (jj2 + j2 < kk2) {
            if (arrayV[arrayI[jj2 + j2] + h] == x2) {
                j2++;
            } else {
                int tmp4 = arrayI[jj2 + j2];
                arrayI[jj2 + j2] = arrayI[kk2 + k2];
                arrayI[kk2 + k2] = tmp4;
                k2++;
            }
        }
        if (jj2 > start) {
            split(arrayI, arrayV, start, jj2 - start, h);
        }
        for (int i5 = 0; i5 < kk2 - jj2; i5++) {
            arrayV[arrayI[jj2 + i5]] = kk2 - 1;
        }
        if (jj2 == kk2 - 1) {
            arrayI[jj2] = -1;
        }
        if (start + len > kk2) {
            split(arrayI, arrayV, kk2, (start + len) - kk2, h);
        }
    }

    private static void qsufsort(int[] arrayI, int[] arrayV, byte[] oldBuf, int oldsize) {
        int[] buckets = new int[256];
        for (int i = 0; i < oldsize; i++) {
            byte b = oldBuf[i] & UnsignedBytes.MAX_VALUE;
            buckets[b] = buckets[b] + 1;
        }
        for (int i2 = 1; i2 < 256; i2++) {
            buckets[i2] = buckets[i2] + buckets[i2 - 1];
        }
        for (int i3 = 255; i3 > 0; i3--) {
            buckets[i3] = buckets[i3 - 1];
        }
        buckets[0] = 0;
        for (int i4 = 0; i4 < oldsize; i4++) {
            byte b2 = oldBuf[i4] & UnsignedBytes.MAX_VALUE;
            int i5 = buckets[b2] + 1;
            buckets[b2] = i5;
            arrayI[i5] = i4;
        }
        arrayI[0] = oldsize;
        for (int i6 = 0; i6 < oldsize; i6++) {
            arrayV[i6] = buckets[oldBuf[i6] & UnsignedBytes.MAX_VALUE];
        }
        arrayV[oldsize] = 0;
        for (int i7 = 1; i7 < 256; i7++) {
            if (buckets[i7] == buckets[i7 - 1] + 1) {
                arrayI[buckets[i7]] = -1;
            }
        }
        arrayI[0] = -1;
        int h = 1;
        while (arrayI[0] != (-(oldsize + 1))) {
            int len = 0;
            int i8 = 0;
            while (i8 < oldsize + 1) {
                if (arrayI[i8] < 0) {
                    len -= arrayI[i8];
                    i8 -= arrayI[i8];
                } else {
                    if (len != 0) {
                        arrayI[i8 - len] = -len;
                    }
                    int len2 = (arrayV[arrayI[i8]] + 1) - i8;
                    split(arrayI, arrayV, i8, len2, h);
                    i8 += len2;
                    len = 0;
                }
            }
            if (len != 0) {
                arrayI[i8 - len] = -len;
            }
            h += h;
        }
        for (int i9 = 0; i9 < oldsize + 1; i9++) {
            arrayI[arrayV[i9]] = i9;
        }
    }

    private static int search(int[] arrayI, byte[] oldBuf, int oldSize, byte[] newBuf, int newSize, int newBufOffset, int start, int end, IntByRef pos) {
        if (end - start < 2) {
            int x = matchlen(oldBuf, oldSize, arrayI[start], newBuf, newSize, newBufOffset);
            int y = matchlen(oldBuf, oldSize, arrayI[end], newBuf, newSize, newBufOffset);
            if (x > y) {
                pos.value = arrayI[start];
                return x;
            }
            pos.value = arrayI[end];
            return y;
        }
        int x2 = start + ((end - start) / 2);
        if (memcmp(oldBuf, oldSize, arrayI[x2], newBuf, newSize, newBufOffset) < 0) {
            return search(arrayI, oldBuf, oldSize, newBuf, newSize, newBufOffset, x2, end, pos);
        }
        return search(arrayI, oldBuf, oldSize, newBuf, newSize, newBufOffset, start, x2, pos);
    }

    private static int matchlen(byte[] oldBuf, int oldSize, int oldOffset, byte[] newBuf, int newSize, int newOffset) {
        int end = Math.min(oldSize - oldOffset, newSize - newOffset);
        for (int i = 0; i < end; i++) {
            if (oldBuf[oldOffset + i] != newBuf[newOffset + i]) {
                return i;
            }
        }
        return end;
    }

    private static int memcmp(byte[] s1, int s1Size, int s1offset, byte[] s2, int s2Size, int s2offset) {
        int n = s1Size - s1offset;
        if (n > s2Size - s2offset) {
            n = s2Size - s2offset;
        }
        for (int i = 0; i < n; i++) {
            if (s1[i + s1offset] != s2[i + s2offset]) {
                return s1[i + s1offset] < s2[i + s2offset] ? -1 : 1;
            }
        }
        return 0;
    }

    public static void bsdiff(File oldFile, File newFile, File diffFile) throws IOException {
        InputStream oldInputStream = new BufferedInputStream(new FileInputStream(oldFile));
        InputStream newInputStream = new BufferedInputStream(new FileInputStream(newFile));
        OutputStream diffOutputStream = new FileOutputStream(diffFile);
        try {
            diffOutputStream.write(bsdiff(oldInputStream, (int) oldFile.length(), newInputStream, (int) newFile.length()));
        } finally {
            diffOutputStream.close();
        }
    }

    public static byte[] bsdiff(InputStream oldInputStream, int oldsize, InputStream newInputStream, int newsize) throws IOException {
        byte[] oldBuf = new byte[oldsize];
        BSUtil.readFromStream(oldInputStream, oldBuf, 0, oldsize);
        oldInputStream.close();
        byte[] newBuf = new byte[newsize];
        BSUtil.readFromStream(newInputStream, newBuf, 0, newsize);
        newInputStream.close();
        return bsdiff(oldBuf, oldsize, newBuf, newsize);
    }

    public static byte[] bsdiff(byte[] oldBuf, int oldsize, byte[] newBuf, int newsize) throws IOException {
        int[] arrayI = new int[(oldsize + 1)];
        qsufsort(arrayI, new int[(oldsize + 1)], oldBuf, oldsize);
        int diffBLockLen = 0;
        byte[] diffBlock = new byte[newsize];
        int extraBlockLen = 0;
        byte[] extraBlock = new byte[newsize];
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteOut);
        dataOutputStream.write(MAGIC_BYTES);
        dataOutputStream.writeLong(-1);
        dataOutputStream.writeLong(-1);
        dataOutputStream.writeLong((long) newsize);
        dataOutputStream.flush();
        GZIPOutputStream bzip2Out = new GZIPOutputStream(dataOutputStream);
        DataOutputStream dataOut = new DataOutputStream(bzip2Out);
        int scan = 0;
        int matchLen = 0;
        int lastscan = 0;
        int lastpos = 0;
        int lastoffset = 0;
        IntByRef pos = new IntByRef();
        while (scan < newsize) {
            int oldscore = 0;
            scan += matchLen;
            int scsc = scan;
            while (scan < newsize) {
                matchLen = search(arrayI, oldBuf, oldsize, newBuf, newsize, scan, 0, oldsize, pos);
                while (scsc < scan + matchLen) {
                    if (scsc + lastoffset < oldsize && oldBuf[scsc + lastoffset] == newBuf[scsc]) {
                        oldscore++;
                    }
                    scsc++;
                }
                if ((matchLen == oldscore && matchLen != 0) || matchLen > oldscore + 8) {
                    break;
                }
                if (scan + lastoffset < oldsize && oldBuf[scan + lastoffset] == newBuf[scan]) {
                    oldscore--;
                }
                scan++;
            }
            if (matchLen != oldscore || scan == newsize) {
                int equalNum = 0;
                int sf = 0;
                int lenFromOld = 0;
                int i = 0;
                while (lastscan + i < scan && lastpos + i < oldsize) {
                    if (oldBuf[lastpos + i] == newBuf[lastscan + i]) {
                        equalNum++;
                    }
                    i++;
                    if ((equalNum * 2) - i > (sf * 2) - lenFromOld) {
                        sf = equalNum;
                        lenFromOld = i;
                    }
                }
                int lenb = 0;
                if (scan < newsize) {
                    int equalNum2 = 0;
                    int sb = 0;
                    int i2 = 1;
                    while (scan >= lastscan + i2 && pos.value >= i2) {
                        if (oldBuf[pos.value - i2] == newBuf[scan - i2]) {
                            equalNum2++;
                        }
                        if ((equalNum2 * 2) - i2 > (sb * 2) - lenb) {
                            sb = equalNum2;
                            lenb = i2;
                        }
                        i2++;
                    }
                }
                if (lastscan + lenFromOld > scan - lenb) {
                    int overlap = (lastscan + lenFromOld) - (scan - lenb);
                    int equalNum3 = 0;
                    int ss = 0;
                    int lens = 0;
                    for (int i3 = 0; i3 < overlap; i3++) {
                        if (newBuf[((lastscan + lenFromOld) - overlap) + i3] == oldBuf[((lastpos + lenFromOld) - overlap) + i3]) {
                            equalNum3++;
                        }
                        if (newBuf[(scan - lenb) + i3] == oldBuf[(pos.value - lenb) + i3]) {
                            equalNum3--;
                        }
                        if (equalNum3 > ss) {
                            ss = equalNum3;
                            lens = i3 + 1;
                        }
                    }
                    lenFromOld += lens - overlap;
                    lenb -= lens;
                }
                for (int i4 = 0; i4 < lenFromOld; i4++) {
                    diffBlock[diffBLockLen + i4] = (byte) (newBuf[lastscan + i4] - oldBuf[lastpos + i4]);
                }
                for (int i5 = 0; i5 < (scan - lenb) - (lastscan + lenFromOld); i5++) {
                    extraBlock[extraBlockLen + i5] = newBuf[lastscan + lenFromOld + i5];
                }
                diffBLockLen += lenFromOld;
                extraBlockLen += (scan - lenb) - (lastscan + lenFromOld);
                dataOut.writeInt(lenFromOld);
                dataOut.writeInt((scan - lenb) - (lastscan + lenFromOld));
                dataOut.writeInt((pos.value - lenb) - (lastpos + lenFromOld));
                lastscan = scan - lenb;
                lastpos = pos.value - lenb;
                lastoffset = pos.value - scan;
            }
        }
        dataOut.flush();
        bzip2Out.finish();
        int ctrlBlockLen = dataOutputStream.size() - 32;
        GZIPOutputStream bzip2Out2 = new GZIPOutputStream(dataOutputStream);
        bzip2Out2.write(diffBlock, 0, diffBLockLen);
        bzip2Out2.finish();
        bzip2Out2.flush();
        int diffBlockLen = (dataOutputStream.size() - ctrlBlockLen) - 32;
        GZIPOutputStream bzip2Out3 = new GZIPOutputStream(dataOutputStream);
        bzip2Out3.write(extraBlock, 0, extraBlockLen);
        bzip2Out3.finish();
        bzip2Out3.flush();
        dataOutputStream.close();
        ByteArrayOutputStream byteHeaderOut = new ByteArrayOutputStream(32);
        DataOutputStream dataOutputStream2 = new DataOutputStream(byteHeaderOut);
        dataOutputStream2.write(MAGIC_BYTES);
        dataOutputStream2.writeLong((long) ctrlBlockLen);
        dataOutputStream2.writeLong((long) diffBlockLen);
        dataOutputStream2.writeLong((long) newsize);
        dataOutputStream2.close();
        byte[] diffBytes = byteOut.toByteArray();
        byte[] headerBytes = byteHeaderOut.toByteArray();
        System.arraycopy(headerBytes, 0, diffBytes, 0, headerBytes.length);
        return diffBytes;
    }
}
