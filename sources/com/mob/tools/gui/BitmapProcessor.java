package com.mob.tools.gui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import com.mob.tools.MobLog;
import com.mob.tools.network.NetworkHelper;
import com.mob.tools.network.NetworkHelper.NetworkTimeOut;
import com.mob.tools.network.RawNetworkCallback;
import com.mob.tools.utils.BitmapHelper;
import com.mob.tools.utils.Data;
import com.mob.tools.utils.R;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class BitmapProcessor {
    private static final int CAPACITY = 3;
    private static final int MAX_CACHE_SIZE = 50;
    private static final int MAX_CACHE_TIME = 60000;
    private static final int MAX_REQ_TIME = 200;
    private static final int MAX_SIZE = 100;
    private static final int OVERFLOW_SIZE = 120;
    private static BitmapProcessor instance;
    /* access modifiers changed from: private */
    public File cacheDir;
    /* access modifiers changed from: private */
    public CachePool<String, Bitmap> cachePool;
    private ManagerThread manager;
    /* access modifiers changed from: private */
    public int maxReqCount;
    /* access modifiers changed from: private */
    public ArrayList<ImageReq> netReqTPS;
    private int overflowReqCount;
    /* access modifiers changed from: private */
    public ArrayList<ImageReq> reqList;
    /* access modifiers changed from: private */
    public int reqTimeout;
    /* access modifiers changed from: private */
    public boolean work;
    /* access modifiers changed from: private */
    public WorkerThread[] workerList;

    public interface BitmapCallback {
        void onImageGot(String str, Bitmap bitmap);
    }

    public static class ImageReq {
        /* access modifiers changed from: private */
        public BitmapCallback callback;
        private Bitmap image;
        private long reqTime = System.currentTimeMillis();
        /* access modifiers changed from: private */
        public String url;
        /* access modifiers changed from: private */
        public WorkerThread worker;

        /* access modifiers changed from: private */
        public void throwComplete(Bitmap bitmap) {
            this.image = bitmap;
            if (this.callback != null) {
                this.callback.onImageGot(this.url, this.image);
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("url=").append(this.url);
            sb.append("time=").append(this.reqTime);
            sb.append("worker=").append(this.worker.getName()).append(" (").append(this.worker.getId()).append("");
            return sb.toString();
        }
    }

    private static class ManagerThread extends Timer {
        /* access modifiers changed from: private */
        public BitmapProcessor processor;

        public ManagerThread(BitmapProcessor bp) {
            this.processor = bp;
            schedule(new TimerTask() {
                private int counter;

                public void run() {
                    if (ManagerThread.this.processor.work) {
                        this.counter--;
                        if (this.counter <= 0) {
                            this.counter = 100;
                            ManagerThread.this.scan();
                        }
                    }
                }
            }, 0, (long) this.processor.reqTimeout);
        }

        /* access modifiers changed from: private */
        public void scan() {
            boolean z;
            if (this.processor.cachePool != null) {
                this.processor.cachePool.trimBeforeTime(System.currentTimeMillis() - 60000);
            }
            MobLog.getInstance().d(">>>> BitmapProcessor.cachePool: " + (this.processor.cachePool == null ? 0 : this.processor.cachePool.size()), new Object[0]);
            MobLog.getInstance().d(">>>> BitmapProcessor.reqList: " + (this.processor.reqList == null ? 0 : this.processor.reqList.size()), new Object[0]);
            if (this.processor.work) {
                long curTime = System.currentTimeMillis();
                for (int i = 0; i < this.processor.workerList.length; i++) {
                    if (this.processor.workerList[i] == null) {
                        this.processor.workerList[i] = new WorkerThread(this.processor);
                        this.processor.workerList[i].setName("worker " + i);
                        WorkerThread workerThread = this.processor.workerList[i];
                        if (i == 0) {
                            z = true;
                        } else {
                            z = false;
                        }
                        workerThread.localType = z;
                        this.processor.workerList[i].start();
                    } else if (curTime - this.processor.workerList[i].lastReport > ((long) (this.processor.reqTimeout * 100))) {
                        this.processor.workerList[i].interrupt();
                        boolean localType = this.processor.workerList[i].localType;
                        this.processor.workerList[i] = new WorkerThread(this.processor);
                        this.processor.workerList[i].setName("worker " + i);
                        this.processor.workerList[i].localType = localType;
                        this.processor.workerList[i].start();
                    }
                }
            }
        }
    }

    private static class PatchInputStream extends FilterInputStream {
        InputStream in;

        protected PatchInputStream(InputStream in2) {
            super(in2);
            this.in = in2;
        }

        public long skip(long n) throws IOException {
            long m = 0;
            while (m < n) {
                long _m = this.in.skip(n - m);
                if (_m == 0) {
                    break;
                }
                m += _m;
            }
            return m;
        }
    }

    private static class WorkerThread extends Thread {
        /* access modifiers changed from: private */
        public ImageReq curReq;
        /* access modifiers changed from: private */
        public long lastReport = System.currentTimeMillis();
        /* access modifiers changed from: private */
        public boolean localType;
        /* access modifiers changed from: private */
        public BitmapProcessor processor;

        public WorkerThread(BitmapProcessor bp) {
            this.processor = bp;
        }

        public void run() {
            while (this.processor.work) {
                try {
                    if (this.localType) {
                        doLocalTask();
                    } else {
                        doNetworkTask();
                    }
                } catch (Throwable t) {
                    MobLog.getInstance().w(t);
                }
            }
        }

        private void doLocalTask() throws Throwable {
            ImageReq req = null;
            synchronized (this.processor.reqList) {
                if (this.processor.reqList.size() > 0) {
                    req = (ImageReq) this.processor.reqList.remove(0);
                }
            }
            if (req != null) {
                Bitmap bm = (Bitmap) this.processor.cachePool.get(req.url);
                if (bm != null) {
                    this.curReq = req;
                    this.curReq.worker = this;
                    req.throwComplete(bm);
                } else if (new File(this.processor.cacheDir, Data.MD5(req.url)).exists()) {
                    doTask(req);
                    this.lastReport = System.currentTimeMillis();
                    return;
                } else {
                    synchronized (this.processor.reqList) {
                        if (this.processor.netReqTPS.size() > this.processor.maxReqCount) {
                            synchronized (this.processor.reqList) {
                                while (this.processor.reqList.size() > 0) {
                                    this.processor.reqList.remove(0);
                                }
                            }
                            this.processor.netReqTPS.remove(0);
                        }
                    }
                    this.processor.netReqTPS.add(req);
                }
                this.lastReport = System.currentTimeMillis();
                return;
            }
            this.lastReport = System.currentTimeMillis();
            try {
                Thread.sleep(30);
            } catch (Throwable th) {
            }
        }

        private void doNetworkTask() throws Throwable {
            ImageReq req = null;
            synchronized (this.processor.netReqTPS) {
                if (this.processor.netReqTPS.size() > 0) {
                    req = (ImageReq) this.processor.netReqTPS.remove(0);
                }
            }
            if (req == null) {
                synchronized (this.processor.reqList) {
                    if (this.processor.reqList.size() > 0) {
                        req = (ImageReq) this.processor.reqList.remove(0);
                    }
                }
            }
            if (req != null) {
                Bitmap bm = (Bitmap) this.processor.cachePool.get(req.url);
                if (bm != null) {
                    this.curReq = req;
                    this.curReq.worker = this;
                    req.throwComplete(bm);
                } else {
                    doTask(req);
                }
                this.lastReport = System.currentTimeMillis();
                return;
            }
            this.lastReport = System.currentTimeMillis();
            try {
                Thread.sleep(30);
            } catch (Throwable th) {
            }
        }

        private void doTask(final ImageReq req) throws Throwable {
            this.curReq = req;
            this.curReq.worker = this;
            Bitmap bm = null;
            final boolean saveAsPng = req.url.toLowerCase().endsWith("png") || req.url.toLowerCase().endsWith("gif");
            final File file = new File(this.processor.cacheDir, Data.MD5(req.url));
            if (file.exists()) {
                bm = BitmapHelper.getBitmap(file.getAbsolutePath());
                if (bm != null) {
                    this.processor.cachePool.put(req.url, bm);
                    req.throwComplete(bm);
                }
                this.curReq = null;
            } else {
                new NetworkHelper().rawGet(req.url, (RawNetworkCallback) new RawNetworkCallback() {
                    public void onResponse(InputStream is) throws Throwable {
                        Bitmap bitmap = BitmapHelper.getBitmap((InputStream) new PatchInputStream(is), 1);
                        if (bitmap == null || bitmap.isRecycled()) {
                            WorkerThread.this.curReq = null;
                            return;
                        }
                        WorkerThread.this.saveFile(bitmap, file, saveAsPng);
                        if (bitmap != null) {
                            WorkerThread.this.processor.cachePool.put(req.url, bitmap);
                            req.throwComplete(bitmap);
                        }
                        WorkerThread.this.curReq = null;
                    }
                }, (NetworkTimeOut) null);
            }
            if (bm != null) {
                this.processor.cachePool.put(req.url, bm);
                req.throwComplete(bm);
            }
            this.curReq = null;
        }

        /* access modifiers changed from: private */
        public void saveFile(Bitmap bitmap, File file, boolean saveAdPng) {
            try {
                if (file.exists()) {
                    file.delete();
                }
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
                CompressFormat type = saveAdPng ? CompressFormat.PNG : CompressFormat.JPEG;
                FileOutputStream fos = new FileOutputStream(file);
                bitmap.compress(type, 100, fos);
                fos.flush();
                fos.close();
            } catch (Throwable th) {
                if (file.exists()) {
                    file.delete();
                }
            }
        }

        public void interrupt() {
            try {
                super.interrupt();
            } catch (Throwable th) {
            }
        }
    }

    public static synchronized void prepare(Context context) {
        synchronized (BitmapProcessor.class) {
            prepare(context, 0, 0, 0, 0.0f, 0);
        }
    }

    public static synchronized void prepare(Context context, int workerCount, int reqTimeout2, int maxReqCount2, float reqOverflowRate, int maxCacheSize) {
        synchronized (BitmapProcessor.class) {
            if (instance == null) {
                instance = new BitmapProcessor(context.getApplicationContext(), workerCount, reqTimeout2, maxReqCount2, reqOverflowRate, maxCacheSize);
            }
        }
    }

    public static synchronized void start() {
        synchronized (BitmapProcessor.class) {
            if (instance == null) {
                throw new RuntimeException("Call BitmapProcessor.prepare(String) before start");
            }
            instance.work = true;
        }
    }

    public static synchronized void stop() {
        synchronized (BitmapProcessor.class) {
            if (instance != null) {
                instance.work = false;
                synchronized (instance.reqList) {
                    instance.reqList.clear();
                }
                instance.manager.cancel();
                for (int i = 0; i < instance.workerList.length; i++) {
                    if (instance.workerList[i] != null) {
                        instance.workerList[i].interrupt();
                    }
                }
                instance = null;
            }
        }
    }

    public static synchronized void process(String url, BitmapCallback callback) {
        synchronized (BitmapProcessor.class) {
            if (!(instance == null || url == null)) {
                boolean found = false;
                if (instance.reqList != null) {
                    synchronized (instance.reqList) {
                        int i = 0;
                        int size = instance.reqList.size();
                        while (true) {
                            if (i >= size) {
                                break;
                            }
                            ImageReq req = (ImageReq) instance.reqList.get(i);
                            if (req.url.equals(url) && req.callback.equals(callback)) {
                                found = true;
                                break;
                            }
                            i++;
                        }
                    }
                }
                if (!found) {
                    ImageReq req2 = new ImageReq();
                    req2.url = url;
                    req2.callback = callback;
                    instance.reqList.add(req2);
                    synchronized (instance.reqList) {
                        if (instance.reqList.size() > instance.overflowReqCount) {
                            while (instance.reqList.size() > instance.maxReqCount) {
                                instance.reqList.remove(0);
                            }
                        }
                    }
                    start();
                }
            }
        }
    }

    public static Bitmap getBitmapFromCache(String url) {
        if (instance == null) {
            return null;
        }
        return (Bitmap) instance.cachePool.get(url);
    }

    private BitmapProcessor(Context context, int workerCount, int reqTimeout2, int maxReqCount2, float reqOverflowRate, int maxCacheSize) {
        if (reqTimeout2 <= 0) {
            reqTimeout2 = 200;
        }
        this.reqTimeout = reqTimeout2;
        this.maxReqCount = maxReqCount2 > 0 ? maxReqCount2 : 100;
        this.overflowReqCount = reqOverflowRate > 1.0f ? (int) (((float) maxReqCount2) * reqOverflowRate) : 120;
        this.reqList = new ArrayList<>();
        this.netReqTPS = new ArrayList<>();
        if (workerCount <= 0) {
            workerCount = 3;
        }
        this.workerList = new WorkerThread[workerCount];
        if (maxCacheSize <= 0) {
            maxCacheSize = 50;
        }
        this.cachePool = new CachePool<>(maxCacheSize);
        this.cacheDir = new File(R.getImageCachePath(context));
        this.manager = new ManagerThread(this);
    }
}
