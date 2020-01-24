package com.tencent.tinker.loader.shareutil;

import android.util.Log;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileLock;

public class ShareFileLockHelper implements Closeable {
    public static final int LOCK_WAIT_EACH_TIME = 10;
    public static final int MAX_LOCK_ATTEMPTS = 3;
    private static final String TAG = "Tinker.FileLockHelper";
    private final FileLock fileLock;
    private final FileOutputStream outputStream;

    private ShareFileLockHelper(File lockFile) throws IOException {
        this.outputStream = new FileOutputStream(lockFile);
        int numAttempts = 0;
        FileLock localFileLock = null;
        Exception saveException = null;
        while (numAttempts < 3) {
            numAttempts++;
            try {
                localFileLock = this.outputStream.getChannel().lock();
                if (localFileLock != null) {
                    break;
                }
                Thread.sleep(10);
            } catch (Exception e) {
                saveException = e;
                Log.e(TAG, "getInfoLock Thread failed time:10");
            }
        }
        if (localFileLock == null) {
            throw new IOException("Tinker Exception:FileLockHelper lock file failed: " + lockFile.getAbsolutePath(), saveException);
        }
        this.fileLock = localFileLock;
    }

    public static ShareFileLockHelper getFileLock(File lockFile) throws IOException {
        return new ShareFileLockHelper(lockFile);
    }

    public void close() throws IOException {
        try {
            if (this.fileLock != null) {
                this.fileLock.release();
            }
        } finally {
            if (this.outputStream != null) {
                this.outputStream.close();
            }
        }
    }
}
