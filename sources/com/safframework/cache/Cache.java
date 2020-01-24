package com.safframework.cache;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.Process;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.cli.HelpFormatter;
import org.json.JSONArray;
import org.json.JSONObject;

public class Cache {
    private static final int MAX_COUNT = Integer.MAX_VALUE;
    private static final int MAX_SIZE = 50000000;
    private static Map<String, Cache> mInstanceMap = new HashMap();
    private CacheManager cacheManager;

    public class CacheManager {
        /* access modifiers changed from: private */
        public final AtomicInteger cacheCount;
        protected File cacheDir;
        /* access modifiers changed from: private */
        public final AtomicLong cacheSize;
        private final int countLimit;
        /* access modifiers changed from: private */
        public final Map<File, Long> lastUsageDates;
        private final long sizeLimit;

        private CacheManager(File cacheDir2, long sizeLimit2, int countLimit2) {
            this.lastUsageDates = Collections.synchronizedMap(new HashMap());
            this.cacheDir = cacheDir2;
            this.sizeLimit = sizeLimit2;
            this.countLimit = countLimit2;
            this.cacheSize = new AtomicLong();
            this.cacheCount = new AtomicInteger();
            calculateCacheSizeAndCacheCount();
        }

        private void calculateCacheSizeAndCacheCount() {
            new Thread(new Runnable() {
                public void run() {
                    int size = 0;
                    int count = 0;
                    File[] cachedFiles = CacheManager.this.cacheDir.listFiles();
                    if (cachedFiles != null) {
                        for (File cachedFile : cachedFiles) {
                            size = (int) (((long) size) + CacheManager.this.calculateSize(cachedFile));
                            count++;
                            CacheManager.this.lastUsageDates.put(cachedFile, Long.valueOf(cachedFile.lastModified()));
                        }
                        CacheManager.this.cacheSize.set((long) size);
                        CacheManager.this.cacheCount.set(count);
                    }
                }
            }).start();
        }

        /* access modifiers changed from: private */
        public void put(File file) {
            int curCacheCount = this.cacheCount.get();
            while (curCacheCount + 1 > this.countLimit) {
                this.cacheSize.addAndGet(-removeNext());
                curCacheCount = this.cacheCount.addAndGet(-1);
            }
            this.cacheCount.addAndGet(1);
            long valueSize = calculateSize(file);
            long curCacheSize = this.cacheSize.get();
            while (curCacheSize + valueSize > this.sizeLimit) {
                curCacheSize = this.cacheSize.addAndGet(-removeNext());
            }
            this.cacheSize.addAndGet(valueSize);
            Long currentTime = Long.valueOf(System.currentTimeMillis());
            file.setLastModified(currentTime.longValue());
            this.lastUsageDates.put(file, currentTime);
        }

        /* access modifiers changed from: private */
        public File get(String key) {
            File file = newFile(key);
            Long currentTime = Long.valueOf(System.currentTimeMillis());
            file.setLastModified(currentTime.longValue());
            this.lastUsageDates.put(file, currentTime);
            return file;
        }

        /* access modifiers changed from: private */
        public File newFile(String key) {
            return new File(this.cacheDir, key.hashCode() + "");
        }

        /* access modifiers changed from: private */
        public boolean remove(String key) {
            return get(key).delete();
        }

        /* access modifiers changed from: private */
        public void clear() {
            this.lastUsageDates.clear();
            this.cacheSize.set(0);
            File[] files = this.cacheDir.listFiles();
            if (files != null) {
                for (File f : files) {
                    f.delete();
                }
            }
        }

        private long removeNext() {
            if (this.lastUsageDates.isEmpty()) {
                return 0;
            }
            Long oldestUsage = null;
            File mostLongUsedFile = null;
            Set<Entry<File, Long>> entries = this.lastUsageDates.entrySet();
            synchronized (this.lastUsageDates) {
                for (Entry<File, Long> entry : entries) {
                    if (mostLongUsedFile == null) {
                        mostLongUsedFile = (File) entry.getKey();
                        oldestUsage = (Long) entry.getValue();
                    } else {
                        Long lastValueUsage = (Long) entry.getValue();
                        if (lastValueUsage.longValue() < oldestUsage.longValue()) {
                            oldestUsage = lastValueUsage;
                            mostLongUsedFile = (File) entry.getKey();
                        }
                    }
                }
            }
            long calculateSize = calculateSize(mostLongUsedFile);
            if (!mostLongUsedFile.delete()) {
                return calculateSize;
            }
            this.lastUsageDates.remove(mostLongUsedFile);
            return calculateSize;
        }

        /* access modifiers changed from: private */
        public long calculateSize(File file) {
            return file.length();
        }
    }

    private static class CacheUtils {
        private static final char mSeparator = ' ';

        private CacheUtils() {
        }

        /* access modifiers changed from: private */
        public static boolean isDue(String str) {
            return isDue(str.getBytes());
        }

        /* access modifiers changed from: private */
        public static boolean isDue(byte[] data) {
            String[] strs = getDateInfoFromDate(data);
            if (strs != null && strs.length == 2) {
                String saveTimeStr = strs[0];
                while (saveTimeStr.startsWith("0")) {
                    saveTimeStr = saveTimeStr.substring(1, saveTimeStr.length());
                }
                if (System.currentTimeMillis() > (1000 * Long.parseLong(strs[1])) + Long.parseLong(saveTimeStr)) {
                    return true;
                }
            }
            return false;
        }

        /* access modifiers changed from: private */
        public static String newStringWithDateInfo(int second, String strInfo) {
            return createDateInfo(second) + strInfo;
        }

        /* access modifiers changed from: private */
        public static byte[] newByteArrayWithDateInfo(int second, byte[] data2) {
            byte[] data1 = createDateInfo(second).getBytes();
            byte[] retdata = new byte[(data1.length + data2.length)];
            System.arraycopy(data1, 0, retdata, 0, data1.length);
            System.arraycopy(data2, 0, retdata, data1.length, data2.length);
            return retdata;
        }

        /* access modifiers changed from: private */
        public static String clearDateInfo(String strInfo) {
            if (strInfo == null || !hasDateInfo(strInfo.getBytes())) {
                return strInfo;
            }
            return strInfo.substring(strInfo.indexOf(32) + 1, strInfo.length());
        }

        /* access modifiers changed from: private */
        public static byte[] clearDateInfo(byte[] data) {
            if (hasDateInfo(data)) {
                return copyOfRange(data, indexOf(data, mSeparator) + 1, data.length);
            }
            return data;
        }

        private static boolean hasDateInfo(byte[] data) {
            return data != null && data.length > 15 && data[13] == 45 && indexOf(data, mSeparator) > 14;
        }

        private static String[] getDateInfoFromDate(byte[] data) {
            if (!hasDateInfo(data)) {
                return null;
            }
            return new String[]{new String(copyOfRange(data, 0, 13)), new String(copyOfRange(data, 14, indexOf(data, mSeparator)))};
        }

        /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=byte, for r3v0, types: [byte, char] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private static int indexOf(byte[] r2, byte r3) {
            /*
                r0 = 0
            L_0x0001:
                int r1 = r2.length
                if (r0 >= r1) goto L_0x000c
                byte r1 = r2[r0]
                if (r1 != r3) goto L_0x0009
            L_0x0008:
                return r0
            L_0x0009:
                int r0 = r0 + 1
                goto L_0x0001
            L_0x000c:
                r0 = -1
                goto L_0x0008
            */
            throw new UnsupportedOperationException("Method not decompiled: com.safframework.cache.Cache.CacheUtils.indexOf(byte[], char):int");
        }

        private static byte[] copyOfRange(byte[] original, int from, int to) {
            int newLength = to - from;
            if (newLength < 0) {
                throw new IllegalArgumentException(from + " > " + to);
            }
            byte[] copy = new byte[newLength];
            System.arraycopy(original, from, copy, 0, Math.min(original.length - from, newLength));
            return copy;
        }

        private static String createDateInfo(int second) {
            String currentTime = System.currentTimeMillis() + "";
            while (currentTime.length() < 13) {
                currentTime = "0" + currentTime;
            }
            return currentTime + HelpFormatter.DEFAULT_OPT_PREFIX + second + mSeparator;
        }
    }

    public static Cache get(Context context) {
        return get(context, "Cache");
    }

    public static Cache get(Context context, String cacheName) {
        return get(new File(context.getCacheDir(), cacheName), 50000000, Integer.MAX_VALUE);
    }

    public static Cache get(File cacheDir, long max_zise, int max_count) {
        Cache manager = (Cache) mInstanceMap.get(cacheDir.getAbsoluteFile() + "_" + Process.myPid());
        if (manager != null) {
            return manager;
        }
        Cache manager2 = new Cache(cacheDir, max_zise, max_count);
        mInstanceMap.put(cacheDir.getAbsolutePath() + "_" + Process.myPid(), manager2);
        return manager2;
    }

    private Cache(File cacheDir, long max_size, int max_count) {
        if (cacheDir.exists() || cacheDir.mkdirs()) {
            this.cacheManager = new CacheManager(cacheDir, max_size, max_count);
            return;
        }
        throw new RuntimeException("can't make dirs in " + cacheDir.getAbsolutePath());
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0030 A[SYNTHETIC, Splitter:B:15:0x0030] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0044 A[SYNTHETIC, Splitter:B:22:0x0044] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void put(java.lang.String r7, java.lang.String r8) {
        /*
            r6 = this;
            com.safframework.cache.Cache$CacheManager r4 = r6.cacheManager
            java.io.File r1 = r4.newFile(r7)
            r2 = 0
            java.io.BufferedWriter r3 = new java.io.BufferedWriter     // Catch:{ IOException -> 0x002a }
            java.io.FileWriter r4 = new java.io.FileWriter     // Catch:{ IOException -> 0x002a }
            r4.<init>(r1)     // Catch:{ IOException -> 0x002a }
            r5 = 1024(0x400, float:1.435E-42)
            r3.<init>(r4, r5)     // Catch:{ IOException -> 0x002a }
            r3.write(r8)     // Catch:{ IOException -> 0x0058, all -> 0x0055 }
            if (r3 == 0) goto L_0x001e
            r3.flush()     // Catch:{ IOException -> 0x0025 }
            r3.close()     // Catch:{ IOException -> 0x0025 }
        L_0x001e:
            com.safframework.cache.Cache$CacheManager r4 = r6.cacheManager
            r4.put(r1)
            r2 = r3
        L_0x0024:
            return
        L_0x0025:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x001e
        L_0x002a:
            r0 = move-exception
        L_0x002b:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)     // Catch:{ all -> 0x0041 }
            if (r2 == 0) goto L_0x0036
            r2.flush()     // Catch:{ IOException -> 0x003c }
            r2.close()     // Catch:{ IOException -> 0x003c }
        L_0x0036:
            com.safframework.cache.Cache$CacheManager r4 = r6.cacheManager
            r4.put(r1)
            goto L_0x0024
        L_0x003c:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x0036
        L_0x0041:
            r4 = move-exception
        L_0x0042:
            if (r2 == 0) goto L_0x004a
            r2.flush()     // Catch:{ IOException -> 0x0050 }
            r2.close()     // Catch:{ IOException -> 0x0050 }
        L_0x004a:
            com.safframework.cache.Cache$CacheManager r5 = r6.cacheManager
            r5.put(r1)
            throw r4
        L_0x0050:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x004a
        L_0x0055:
            r4 = move-exception
            r2 = r3
            goto L_0x0042
        L_0x0058:
            r0 = move-exception
            r2 = r3
            goto L_0x002b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.safframework.cache.Cache.put(java.lang.String, java.lang.String):void");
    }

    public void put(String key, String value, int saveTime) {
        put(key, CacheUtils.newStringWithDateInfo(saveTime, value));
    }

    /* JADX WARNING: Removed duplicated region for block: B:33:0x0066 A[SYNTHETIC, Splitter:B:33:0x0066] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x006b  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0077 A[SYNTHETIC, Splitter:B:41:0x0077] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x007c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getString(java.lang.String r10) {
        /*
            r9 = this;
            r7 = 0
            com.safframework.cache.Cache$CacheManager r8 = r9.cacheManager
            java.io.File r2 = r8.get(r10)
            boolean r8 = r2.exists()
            if (r8 != 0) goto L_0x000e
        L_0x000d:
            return r7
        L_0x000e:
            r6 = 0
            r3 = 0
            java.io.BufferedReader r4 = new java.io.BufferedReader     // Catch:{ IOException -> 0x0060 }
            java.io.FileReader r8 = new java.io.FileReader     // Catch:{ IOException -> 0x0060 }
            r8.<init>(r2)     // Catch:{ IOException -> 0x0060 }
            r4.<init>(r8)     // Catch:{ IOException -> 0x0060 }
            java.lang.String r5 = ""
        L_0x001d:
            java.lang.String r0 = r4.readLine()     // Catch:{ IOException -> 0x0088, all -> 0x0085 }
            if (r0 == 0) goto L_0x0035
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0088, all -> 0x0085 }
            r8.<init>()     // Catch:{ IOException -> 0x0088, all -> 0x0085 }
            java.lang.StringBuilder r8 = r8.append(r5)     // Catch:{ IOException -> 0x0088, all -> 0x0085 }
            java.lang.StringBuilder r8 = r8.append(r0)     // Catch:{ IOException -> 0x0088, all -> 0x0085 }
            java.lang.String r5 = r8.toString()     // Catch:{ IOException -> 0x0088, all -> 0x0085 }
            goto L_0x001d
        L_0x0035:
            boolean r8 = com.safframework.cache.Cache.CacheUtils.isDue(r5)     // Catch:{ IOException -> 0x0088, all -> 0x0085 }
            if (r8 != 0) goto L_0x004f
            java.lang.String r7 = com.safframework.cache.Cache.CacheUtils.clearDateInfo(r5)     // Catch:{ IOException -> 0x0088, all -> 0x0085 }
            if (r4 == 0) goto L_0x0044
            r4.close()     // Catch:{ IOException -> 0x004a }
        L_0x0044:
            if (r6 == 0) goto L_0x000d
            r9.remove(r10)
            goto L_0x000d
        L_0x004a:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x0044
        L_0x004f:
            r6 = 1
            if (r4 == 0) goto L_0x0055
            r4.close()     // Catch:{ IOException -> 0x005b }
        L_0x0055:
            if (r6 == 0) goto L_0x000d
            r9.remove(r10)
            goto L_0x000d
        L_0x005b:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x0055
        L_0x0060:
            r1 = move-exception
        L_0x0061:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)     // Catch:{ all -> 0x0074 }
            if (r3 == 0) goto L_0x0069
            r3.close()     // Catch:{ IOException -> 0x006f }
        L_0x0069:
            if (r6 == 0) goto L_0x000d
            r9.remove(r10)
            goto L_0x000d
        L_0x006f:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x0069
        L_0x0074:
            r7 = move-exception
        L_0x0075:
            if (r3 == 0) goto L_0x007a
            r3.close()     // Catch:{ IOException -> 0x0080 }
        L_0x007a:
            if (r6 == 0) goto L_0x007f
            r9.remove(r10)
        L_0x007f:
            throw r7
        L_0x0080:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x007a
        L_0x0085:
            r7 = move-exception
            r3 = r4
            goto L_0x0075
        L_0x0088:
            r1 = move-exception
            r3 = r4
            goto L_0x0061
        */
        throw new UnsupportedOperationException("Method not decompiled: com.safframework.cache.Cache.getString(java.lang.String):java.lang.String");
    }

    public void put(String key, JSONObject value) {
        put(key, value.toString());
    }

    public void put(String key, JSONObject value, int saveTime) {
        put(key, value.toString(), saveTime);
    }

    public JSONObject getJSONObject(String key) {
        try {
            return new JSONObject(getString(key));
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return null;
        }
    }

    public void put(String key, JSONArray value) {
        put(key, value.toString());
    }

    public void put(String key, JSONArray value, int saveTime) {
        put(key, value.toString(), saveTime);
    }

    public JSONArray getJSONArray(String key) {
        try {
            return new JSONArray(getString(key));
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0029 A[SYNTHETIC, Splitter:B:15:0x0029] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x003d A[SYNTHETIC, Splitter:B:22:0x003d] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void put(java.lang.String r7, byte[] r8) {
        /*
            r6 = this;
            com.safframework.cache.Cache$CacheManager r4 = r6.cacheManager
            java.io.File r1 = r4.newFile(r7)
            r2 = 0
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0023 }
            r3.<init>(r1)     // Catch:{ Exception -> 0x0023 }
            r3.write(r8)     // Catch:{ Exception -> 0x0051, all -> 0x004e }
            if (r3 == 0) goto L_0x0017
            r3.flush()     // Catch:{ IOException -> 0x001e }
            r3.close()     // Catch:{ IOException -> 0x001e }
        L_0x0017:
            com.safframework.cache.Cache$CacheManager r4 = r6.cacheManager
            r4.put(r1)
            r2 = r3
        L_0x001d:
            return
        L_0x001e:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x0017
        L_0x0023:
            r0 = move-exception
        L_0x0024:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)     // Catch:{ all -> 0x003a }
            if (r2 == 0) goto L_0x002f
            r2.flush()     // Catch:{ IOException -> 0x0035 }
            r2.close()     // Catch:{ IOException -> 0x0035 }
        L_0x002f:
            com.safframework.cache.Cache$CacheManager r4 = r6.cacheManager
            r4.put(r1)
            goto L_0x001d
        L_0x0035:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x002f
        L_0x003a:
            r4 = move-exception
        L_0x003b:
            if (r2 == 0) goto L_0x0043
            r2.flush()     // Catch:{ IOException -> 0x0049 }
            r2.close()     // Catch:{ IOException -> 0x0049 }
        L_0x0043:
            com.safframework.cache.Cache$CacheManager r5 = r6.cacheManager
            r5.put(r1)
            throw r4
        L_0x0049:
            r0 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x0043
        L_0x004e:
            r4 = move-exception
            r2 = r3
            goto L_0x003b
        L_0x0051:
            r0 = move-exception
            r2 = r3
            goto L_0x0024
        */
        throw new UnsupportedOperationException("Method not decompiled: com.safframework.cache.Cache.put(java.lang.String, byte[]):void");
    }

    public void put(String key, byte[] value, int saveTime) {
        put(key, CacheUtils.newByteArrayWithDateInfo(saveTime, value));
    }

    /* JADX WARNING: Removed duplicated region for block: B:47:0x0075 A[SYNTHETIC, Splitter:B:47:0x0075] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x007a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public byte[] getBytes(java.lang.String r11) {
        /*
            r10 = this;
            r6 = 0
            r0 = 0
            r5 = 0
            com.safframework.cache.Cache$CacheManager r7 = r10.cacheManager     // Catch:{ Exception -> 0x005e }
            java.io.File r4 = r7.get(r11)     // Catch:{ Exception -> 0x005e }
            boolean r7 = r4.exists()     // Catch:{ Exception -> 0x005e }
            if (r7 != 0) goto L_0x001f
            if (r0 == 0) goto L_0x0014
            r0.close()     // Catch:{ IOException -> 0x001a }
        L_0x0014:
            if (r5 == 0) goto L_0x0019
            r10.remove(r11)
        L_0x0019:
            return r6
        L_0x001a:
            r3 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r3)
            goto L_0x0014
        L_0x001f:
            java.io.RandomAccessFile r1 = new java.io.RandomAccessFile     // Catch:{ Exception -> 0x005e }
            java.lang.String r7 = "r"
            r1.<init>(r4, r7)     // Catch:{ Exception -> 0x005e }
            long r8 = r1.length()     // Catch:{ Exception -> 0x0086, all -> 0x0083 }
            int r7 = (int) r8     // Catch:{ Exception -> 0x0086, all -> 0x0083 }
            byte[] r2 = new byte[r7]     // Catch:{ Exception -> 0x0086, all -> 0x0083 }
            r1.read(r2)     // Catch:{ Exception -> 0x0086, all -> 0x0083 }
            boolean r7 = com.safframework.cache.Cache.CacheUtils.isDue(r2)     // Catch:{ Exception -> 0x0086, all -> 0x0083 }
            if (r7 != 0) goto L_0x004c
            byte[] r6 = com.safframework.cache.Cache.CacheUtils.clearDateInfo(r2)     // Catch:{ Exception -> 0x0086, all -> 0x0083 }
            if (r1 == 0) goto L_0x0040
            r1.close()     // Catch:{ IOException -> 0x0047 }
        L_0x0040:
            if (r5 == 0) goto L_0x0045
            r10.remove(r11)
        L_0x0045:
            r0 = r1
            goto L_0x0019
        L_0x0047:
            r3 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r3)
            goto L_0x0040
        L_0x004c:
            r5 = 1
            if (r1 == 0) goto L_0x0052
            r1.close()     // Catch:{ IOException -> 0x0059 }
        L_0x0052:
            if (r5 == 0) goto L_0x0057
            r10.remove(r11)
        L_0x0057:
            r0 = r1
            goto L_0x0019
        L_0x0059:
            r3 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r3)
            goto L_0x0052
        L_0x005e:
            r3 = move-exception
        L_0x005f:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r3)     // Catch:{ all -> 0x0072 }
            if (r0 == 0) goto L_0x0067
            r0.close()     // Catch:{ IOException -> 0x006d }
        L_0x0067:
            if (r5 == 0) goto L_0x0019
            r10.remove(r11)
            goto L_0x0019
        L_0x006d:
            r3 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r3)
            goto L_0x0067
        L_0x0072:
            r6 = move-exception
        L_0x0073:
            if (r0 == 0) goto L_0x0078
            r0.close()     // Catch:{ IOException -> 0x007e }
        L_0x0078:
            if (r5 == 0) goto L_0x007d
            r10.remove(r11)
        L_0x007d:
            throw r6
        L_0x007e:
            r3 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r3)
            goto L_0x0078
        L_0x0083:
            r6 = move-exception
            r0 = r1
            goto L_0x0073
        L_0x0086:
            r3 = move-exception
            r0 = r1
            goto L_0x005f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.safframework.cache.Cache.getBytes(java.lang.String):byte[]");
    }

    public void put(String key, Serializable value) {
        put(key, value, -1);
    }

    public void put(String key, Serializable value, int saveTime) {
        ObjectOutputStream oos = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                ObjectOutputStream oos2 = new ObjectOutputStream(baos);
                try {
                    oos2.writeObject(value);
                    byte[] data = baos.toByteArray();
                    if (saveTime != -1) {
                        put(key, data, saveTime);
                    } else {
                        put(key, data);
                    }
                    try {
                        oos2.close();
                        ObjectOutputStream objectOutputStream = oos2;
                        ByteArrayOutputStream byteArrayOutputStream = baos;
                    } catch (IOException e) {
                        ObjectOutputStream objectOutputStream2 = oos2;
                        ByteArrayOutputStream byteArrayOutputStream2 = baos;
                    }
                } catch (Exception e2) {
                    e = e2;
                    oos = oos2;
                    ByteArrayOutputStream byteArrayOutputStream3 = baos;
                } catch (Throwable th) {
                    th = th;
                    oos = oos2;
                    ByteArrayOutputStream byteArrayOutputStream4 = baos;
                    try {
                        oos.close();
                    } catch (IOException e3) {
                    }
                    throw th;
                }
            } catch (Exception e4) {
                e = e4;
                ByteArrayOutputStream byteArrayOutputStream5 = baos;
                try {
                    ThrowableExtension.printStackTrace(e);
                    try {
                        oos.close();
                    } catch (IOException e5) {
                    }
                } catch (Throwable th2) {
                    th = th2;
                    oos.close();
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                ByteArrayOutputStream byteArrayOutputStream6 = baos;
                oos.close();
                throw th;
            }
        } catch (Exception e6) {
            e = e6;
            ThrowableExtension.printStackTrace(e);
            oos.close();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0032 A[SYNTHETIC, Splitter:B:24:0x0032] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0037 A[SYNTHETIC, Splitter:B:27:0x0037] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0048 A[SYNTHETIC, Splitter:B:35:0x0048] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x004d A[SYNTHETIC, Splitter:B:38:0x004d] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object getObject(java.lang.String r9) {
        /*
            r8 = this;
            r6 = 0
            byte[] r2 = r8.getBytes(r9)
            if (r2 == 0) goto L_0x0021
            r0 = 0
            r4 = 0
            java.io.ByteArrayInputStream r1 = new java.io.ByteArrayInputStream     // Catch:{ Exception -> 0x002c }
            r1.<init>(r2)     // Catch:{ Exception -> 0x002c }
            java.io.ObjectInputStream r5 = new java.io.ObjectInputStream     // Catch:{ Exception -> 0x0062, all -> 0x005b }
            r5.<init>(r1)     // Catch:{ Exception -> 0x0062, all -> 0x005b }
            java.lang.Object r6 = r5.readObject()     // Catch:{ Exception -> 0x0065, all -> 0x005e }
            if (r1 == 0) goto L_0x001c
            r1.close()     // Catch:{ IOException -> 0x0022 }
        L_0x001c:
            if (r5 == 0) goto L_0x0021
            r5.close()     // Catch:{ IOException -> 0x0027 }
        L_0x0021:
            return r6
        L_0x0022:
            r3 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r3)
            goto L_0x001c
        L_0x0027:
            r3 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r3)
            goto L_0x0021
        L_0x002c:
            r3 = move-exception
        L_0x002d:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r3)     // Catch:{ all -> 0x0045 }
            if (r0 == 0) goto L_0x0035
            r0.close()     // Catch:{ IOException -> 0x0040 }
        L_0x0035:
            if (r4 == 0) goto L_0x0021
            r4.close()     // Catch:{ IOException -> 0x003b }
            goto L_0x0021
        L_0x003b:
            r3 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r3)
            goto L_0x0021
        L_0x0040:
            r3 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r3)
            goto L_0x0035
        L_0x0045:
            r7 = move-exception
        L_0x0046:
            if (r0 == 0) goto L_0x004b
            r0.close()     // Catch:{ IOException -> 0x0051 }
        L_0x004b:
            if (r4 == 0) goto L_0x0050
            r4.close()     // Catch:{ IOException -> 0x0056 }
        L_0x0050:
            throw r7
        L_0x0051:
            r3 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r3)
            goto L_0x004b
        L_0x0056:
            r3 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r3)
            goto L_0x0050
        L_0x005b:
            r7 = move-exception
            r0 = r1
            goto L_0x0046
        L_0x005e:
            r7 = move-exception
            r4 = r5
            r0 = r1
            goto L_0x0046
        L_0x0062:
            r3 = move-exception
            r0 = r1
            goto L_0x002d
        L_0x0065:
            r3 = move-exception
            r4 = r5
            r0 = r1
            goto L_0x002d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.safframework.cache.Cache.getObject(java.lang.String):java.lang.Object");
    }

    public void put(String key, Parcelable value) {
        put(key, value, -1);
    }

    public void put(String key, Parcelable value, int saveTime) {
        try {
            byte[] data = ParcelableUtils.marshall(value);
            if (saveTime != -1) {
                put(key, data, saveTime);
            } else {
                put(key, data);
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    public Parcel getParcelObject(String key) {
        byte[] data = getBytes(key);
        if (data != null) {
            return ParcelableUtils.unmarshall(data);
        }
        return null;
    }

    public <T> T getObject(String key, Creator<T> creator) {
        byte[] data = getBytes(key);
        if (data != null) {
            return ParcelableUtils.unmarshall(data, creator);
        }
        return null;
    }

    public boolean remove(String key) {
        return this.cacheManager.remove(key);
    }

    public void clear() {
        this.cacheManager.clear();
    }
}
