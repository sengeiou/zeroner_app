package com.tencent.tinker.loader;

import android.os.Build.VERSION;
import android.util.Log;
import com.tencent.tinker.loader.shareutil.ShareFileLockHelper;
import com.tencent.tinker.loader.shareutil.SharePatchFileUtil;
import dalvik.system.DexFile;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public final class TinkerDexOptimizer {
    private static final String INTERPRET_LOCK_FILE_NAME = "interpret.lock";
    private static final String TAG = "Tinker.ParallelDex";

    private static class OptimizeWorker {
        private static String targetISA = null;
        private final ResultCallback callback;
        private final File dexFile;
        private final File optimizedDir;
        private final boolean useInterpretMode;

        OptimizeWorker(File dexFile2, File optimizedDir2, boolean useInterpretMode2, String targetISA2, ResultCallback cb) {
            this.dexFile = dexFile2;
            this.optimizedDir = optimizedDir2;
            this.useInterpretMode = useInterpretMode2;
            this.callback = cb;
            targetISA = targetISA2;
        }

        public boolean run() {
            try {
                if (SharePatchFileUtil.isLegalFile(this.dexFile) || this.callback == null) {
                    if (this.callback != null) {
                        this.callback.onStart(this.dexFile, this.optimizedDir);
                    }
                    String optimizedPath = SharePatchFileUtil.optimizedPathFor(this.dexFile, this.optimizedDir);
                    if (this.useInterpretMode) {
                        interpretDex2Oat(this.dexFile.getAbsolutePath(), optimizedPath);
                    } else {
                        DexFile.loadDex(this.dexFile.getAbsolutePath(), optimizedPath, 0);
                    }
                    if (this.callback != null) {
                        this.callback.onSuccess(this.dexFile, this.optimizedDir, new File(optimizedPath));
                    }
                    return true;
                }
                this.callback.onFailed(this.dexFile, this.optimizedDir, new IOException("dex file " + this.dexFile.getAbsolutePath() + " is not exist!"));
                return false;
            } catch (Throwable e) {
                Log.e(TinkerDexOptimizer.TAG, "Failed to optimize dex: " + this.dexFile.getAbsolutePath(), e);
                if (this.callback != null) {
                    this.callback.onFailed(this.dexFile, this.optimizedDir, e);
                    return false;
                }
            }
        }

        private void interpretDex2Oat(String dexFilePath, String oatFilePath) throws IOException {
            File oatFile = new File(oatFilePath);
            if (!oatFile.exists()) {
                oatFile.getParentFile().mkdirs();
            }
            ShareFileLockHelper fileLock = null;
            try {
                fileLock = ShareFileLockHelper.getFileLock(new File(oatFile.getParentFile(), TinkerDexOptimizer.INTERPRET_LOCK_FILE_NAME));
                List<String> commandAndParams = new ArrayList<>();
                commandAndParams.add("dex2oat");
                if (VERSION.SDK_INT >= 24) {
                    commandAndParams.add("--runtime-arg");
                    commandAndParams.add("-classpath");
                    commandAndParams.add("--runtime-arg");
                    commandAndParams.add("&");
                }
                commandAndParams.add("--dex-file=" + dexFilePath);
                commandAndParams.add("--oat-file=" + oatFilePath);
                commandAndParams.add("--instruction-set=" + targetISA);
                if (VERSION.SDK_INT > 25) {
                    commandAndParams.add("--compiler-filter=quicken");
                } else {
                    commandAndParams.add("--compiler-filter=interpret-only");
                }
                ProcessBuilder pb = new ProcessBuilder(commandAndParams);
                pb.redirectErrorStream(true);
                Process dex2oatProcess = pb.start();
                StreamConsumer.consumeInputStream(dex2oatProcess.getInputStream());
                StreamConsumer.consumeInputStream(dex2oatProcess.getErrorStream());
                int ret = dex2oatProcess.waitFor();
                if (ret != 0) {
                    throw new IOException("dex2oat works unsuccessfully, exit code: " + ret);
                } else if (fileLock != null) {
                    try {
                        fileLock.close();
                    } catch (IOException e) {
                        Log.w(TinkerDexOptimizer.TAG, "release interpret Lock error", e);
                    }
                }
            } catch (InterruptedException e2) {
                throw new IOException("dex2oat is interrupted, msg: " + e2.getMessage(), e2);
            } catch (Throwable th) {
                if (fileLock != null) {
                    try {
                        fileLock.close();
                    } catch (IOException e3) {
                        Log.w(TinkerDexOptimizer.TAG, "release interpret Lock error", e3);
                    }
                }
                throw th;
            }
        }
    }

    public interface ResultCallback {
        void onFailed(File file, File file2, Throwable th);

        void onStart(File file, File file2);

        void onSuccess(File file, File file2, File file3);
    }

    private static class StreamConsumer {
        static final Executor STREAM_CONSUMER = Executors.newSingleThreadExecutor();

        private StreamConsumer() {
        }

        static void consumeInputStream(final InputStream is) {
            STREAM_CONSUMER.execute(new Runnable() {
                public void run() {
                    if (is != null) {
                        do {
                            try {
                            } catch (IOException e) {
                                try {
                                    return;
                                } catch (Exception e2) {
                                    return;
                                }
                            } finally {
                                try {
                                    is.close();
                                } catch (Exception e3) {
                                }
                            }
                        } while (is.read(new byte[256]) > 0);
                    }
                }
            });
        }
    }

    public static boolean optimizeAll(Collection<File> dexFiles, File optimizedDir, ResultCallback cb) {
        return optimizeAll(dexFiles, optimizedDir, false, null, cb);
    }

    public static boolean optimizeAll(Collection<File> dexFiles, File optimizedDir, boolean useInterpretMode, String targetISA, ResultCallback cb) {
        ArrayList<File> sortList = new ArrayList<>(dexFiles);
        Collections.sort(sortList, new Comparator<File>() {
            public int compare(File lhs, File rhs) {
                long diffSize = lhs.length() - rhs.length();
                if (diffSize > 0) {
                    return 1;
                }
                if (diffSize == 0) {
                    return 0;
                }
                return -1;
            }
        });
        Collections.reverse(sortList);
        Iterator it = sortList.iterator();
        while (it.hasNext()) {
            if (!new OptimizeWorker((File) it.next(), optimizedDir, useInterpretMode, targetISA, cb).run()) {
                return false;
            }
        }
        return true;
    }
}
