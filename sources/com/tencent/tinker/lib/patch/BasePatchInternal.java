package com.tencent.tinker.lib.patch;

import com.tencent.tinker.commons.util.StreamUtil;
import com.tencent.tinker.lib.util.TinkerLog;
import com.tencent.tinker.loader.shareutil.SharePatchFileUtil;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class BasePatchInternal {
    protected static final String DEX_META_FILE = "assets/dex_meta.txt";
    protected static final String DEX_OPTIMIZE_PATH = "odex";
    protected static final String DEX_PATH = "dex";
    protected static final int MAX_EXTRACT_ATTEMPTS = 2;
    protected static final String RES_META_FILE = "assets/res_meta.txt";
    protected static final String SO_META_FILE = "assets/so_meta.txt";
    protected static final String SO_PATH = "lib";
    protected static final String TAG = "Tinker.BasePatchInternal";
    protected static final int TYPE_CLASS_N_DEX = 7;
    protected static final int TYPE_DEX = 3;
    protected static final int TYPE_Library = 5;
    protected static final int TYPE_RESOURCE = 6;

    public static boolean extract(ZipFile zipFile, ZipEntry entryFile, File extractTo, String targetMd5, boolean isDex) throws IOException {
        int numAttempts = 0;
        boolean isExtractionSuccessful = false;
        while (numAttempts < 2 && !isExtractionSuccessful) {
            numAttempts++;
            InputStream is = null;
            OutputStream os = null;
            TinkerLog.i(TAG, "try Extracting " + extractTo.getPath(), new Object[0]);
            try {
                InputStream is2 = new BufferedInputStream(zipFile.getInputStream(entryFile));
                try {
                    OutputStream os2 = new BufferedOutputStream(new FileOutputStream(extractTo));
                    try {
                        byte[] buffer = new byte[16384];
                        while (true) {
                            int length = is2.read(buffer);
                            if (length <= 0) {
                                break;
                            }
                            os2.write(buffer, 0, length);
                        }
                        StreamUtil.closeQuietly(os2);
                        StreamUtil.closeQuietly(is2);
                        if (targetMd5 == null) {
                            isExtractionSuccessful = true;
                        } else if (isDex) {
                            isExtractionSuccessful = SharePatchFileUtil.verifyDexFileMd5(extractTo, targetMd5);
                        } else {
                            isExtractionSuccessful = SharePatchFileUtil.verifyFileMd5(extractTo, targetMd5);
                        }
                        TinkerLog.i(TAG, "isExtractionSuccessful: %b", Boolean.valueOf(isExtractionSuccessful));
                        if (!isExtractionSuccessful && (!extractTo.delete() || extractTo.exists())) {
                            TinkerLog.e(TAG, "Failed to delete corrupted dex " + extractTo.getPath(), new Object[0]);
                        }
                    } catch (Throwable th) {
                        th = th;
                        os = os2;
                        is = is2;
                        StreamUtil.closeQuietly(os);
                        StreamUtil.closeQuietly(is);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    is = is2;
                    StreamUtil.closeQuietly(os);
                    StreamUtil.closeQuietly(is);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                StreamUtil.closeQuietly(os);
                StreamUtil.closeQuietly(is);
                throw th;
            }
        }
        return isExtractionSuccessful;
    }

    public static int getMetaCorruptedCode(int type) {
        if (type == 3) {
            return -3;
        }
        if (type == 5) {
            return -4;
        }
        if (type == 6) {
            return -8;
        }
        return 0;
    }
}
