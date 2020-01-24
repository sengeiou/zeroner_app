package com.hiflying.smartlink;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.hiflying.commons.log.HFLog;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import kotlin.jvm.internal.LongCompanionObject;

public abstract class AbstractSmartLinker implements ISmartLinker {
    @Deprecated
    public static int MAX_DURATION_RECEIVE_SMART_CONFIG = 60000;
    @Deprecated
    public static int MAX_DURATION_WAIT_MORE_DEVICE = 10000;
    private static final int MSG_SMART_LINK_COMPLETED = 2;
    private static final int MSG_SMART_LINK_NEW_DEVICE = 1;
    public static int PORT_RECEIVE_SMART_CONFIG = 49999;
    protected Context mContext;
    /* access modifiers changed from: private */
    public HashSet<String> mDeviceMacs = new HashSet<>();
    /* access modifiers changed from: private */
    public Handler mHander = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (AbstractSmartLinker.this.mOnSmartLinkListener != null) {
                        AbstractSmartLinker.this.mOnSmartLinkListener.onLinked((SmartLinkedModule) msg.obj);
                        return;
                    }
                    return;
                case 2:
                    if (AbstractSmartLinker.this.mOnSmartLinkListener == null) {
                        return;
                    }
                    if (AbstractSmartLinker.this.mDeviceMacs.isEmpty()) {
                        AbstractSmartLinker.this.mOnSmartLinkListener.onTimeOut();
                        return;
                    } else {
                        AbstractSmartLinker.this.mOnSmartLinkListener.onCompleted();
                        return;
                    }
                default:
                    return;
            }
        }
    };
    protected boolean mIsSmartLinking;
    protected boolean mIsTimeout;
    protected OnSmartLinkListener mOnSmartLinkListener;
    protected DatagramSocket mSmartConfigSocket;
    protected int mTimeoutPeriod = 60000;
    /* access modifiers changed from: private */
    public int mWaitMoreDevicePeriod = MAX_DURATION_WAIT_MORE_DEVICE;

    /* access modifiers changed from: protected */
    public abstract Runnable[] setupSendAction(String str, String... strArr) throws Exception;

    protected AbstractSmartLinker() {
    }

    /* access modifiers changed from: protected */
    public void initSmartConfigSocket() throws SocketException {
        this.mSmartConfigSocket = new DatagramSocket(PORT_RECEIVE_SMART_CONFIG);
        this.mSmartConfigSocket.setSoTimeout(1200);
    }

    /* access modifiers changed from: protected */
    public void closeDestroySmartConfigSocket() {
        if (this.mSmartConfigSocket != null) {
            this.mSmartConfigSocket.close();
            this.mSmartConfigSocket.disconnect();
            this.mSmartConfigSocket = null;
        }
    }

    /* access modifiers changed from: protected */
    public DatagramSocket getSmartConfigSocket() {
        return this.mSmartConfigSocket;
    }

    /* access modifiers changed from: private */
    public Runnable[] createSenderRunnables(final CountDownLatch latch, final Runnable[] runnables) {
        Runnable[] _runnnables = null;
        if (runnables != null) {
            _runnnables = new Runnable[runnables.length];
            for (int i = 0; i < runnables.length; i++) {
                final int j = i;
                _runnnables[i] = new Runnable() {
                    public void run() {
                        runnables[j].run();
                        latch.countDown();
                    }
                };
            }
        }
        return _runnnables;
    }

    /* access modifiers changed from: private */
    public Runnable createReceiverRunnable(final CountDownLatch latch) {
        return new Runnable() {
            public void run() {
                AbstractSmartLinker.this.setupReceiveAction().run();
                latch.countDown();
            }
        };
    }

    /* access modifiers changed from: protected */
    public Runnable setupReceiveAction() {
        return new Runnable() {
            public void run() {
                AbstractSmartLinker.this.mDeviceMacs.clear();
                byte[] buffer = new byte[1024];
                DatagramPacket pack = new DatagramPacket(buffer, buffer.length);
                long startTime = System.currentTimeMillis();
                long findDeivceTime = LongCompanionObject.MAX_VALUE;
                while (AbstractSmartLinker.this.mIsSmartLinking) {
                    long currentTime = System.currentTimeMillis();
                    if (!AbstractSmartLinker.this.mIsSmartLinking || currentTime - startTime > ((long) AbstractSmartLinker.this.mTimeoutPeriod) || currentTime - findDeivceTime > ((long) AbstractSmartLinker.this.mWaitMoreDevicePeriod)) {
                        break;
                    }
                    try {
                        AbstractSmartLinker.this.mSmartConfigSocket.receive(pack);
                        byte[] datas = new byte[pack.getLength()];
                        System.arraycopy(buffer, 0, datas, 0, datas.length);
                        if (datas.length > 12) {
                            boolean ignore = true;
                            for (int i = 0; i < datas.length; i++) {
                                ignore = datas[i] == 5;
                                if (!ignore) {
                                    break;
                                }
                            }
                            if (!ignore) {
                                StringBuffer sb = new StringBuffer();
                                for (byte b : datas) {
                                    sb.append((char) b);
                                }
                                String result = sb.toString().trim();
                                if (result.startsWith("smart_config")) {
                                    HFLog.d((Object) AbstractSmartLinker.this, "Received: " + result);
                                    String result2 = result.replace("smart_config", "").trim();
                                    if (result2.length() != 0 && !AbstractSmartLinker.this.mDeviceMacs.contains(result2)) {
                                        AbstractSmartLinker.this.mDeviceMacs.add(result2);
                                        SmartLinkedModule module = new SmartLinkedModule();
                                        module.setId(result2);
                                        module.setMac(result2);
                                        module.setIp(pack.getAddress().getHostAddress());
                                        AbstractSmartLinker.this.mHander.sendMessage(AbstractSmartLinker.this.mHander.obtainMessage(1, module));
                                        if (findDeivceTime == LongCompanionObject.MAX_VALUE) {
                                            findDeivceTime = System.currentTimeMillis();
                                        }
                                    }
                                }
                            }
                        }
                    } catch (IOException e) {
                        HFLog.v((Object) AbstractSmartLinker.this, "smartLinkSocket.receive(pack) timeout");
                    }
                }
                AbstractSmartLinker.this.mIsSmartLinking = false;
            }
        };
    }

    public void setOnSmartLinkListener(OnSmartLinkListener listener) {
        this.mOnSmartLinkListener = listener;
    }

    public void start(Context context, final String password, final String... ssid) throws Exception {
        if (this.mIsSmartLinking) {
            HFLog.w((Object) this, "SmartLink is already linking, do not start it again!");
            return;
        }
        HFLog.d((Object) this, "Smart Link started!");
        this.mIsSmartLinking = true;
        initSmartConfigSocket();
        this.mContext = context;
        new Thread(new Runnable() {
            public void run() {
                Runnable[] senderActions = null;
                try {
                    senderActions = AbstractSmartLinker.this.setupSendAction(password, ssid);
                } catch (Exception e1) {
                    ThrowableExtension.printStackTrace(e1);
                }
                int length = 1;
                if (senderActions != null) {
                    length = 1 + senderActions.length;
                }
                CountDownLatch latch = new CountDownLatch(length);
                ExecutorService threadPool = Executors.newFixedThreadPool(length);
                Runnable receiverRunnable = AbstractSmartLinker.this.createReceiverRunnable(latch);
                AbstractSmartLinker.this.mIsTimeout = false;
                if (senderActions != null) {
                    Runnable[] senderRunnables = AbstractSmartLinker.this.createSenderRunnables(latch, senderActions);
                    if (senderRunnables != null) {
                        for (Runnable runnable : senderRunnables) {
                            threadPool.execute(runnable);
                        }
                    }
                }
                threadPool.execute(receiverRunnable);
                try {
                    AbstractSmartLinker.this.mIsTimeout = !latch.await((long) AbstractSmartLinker.this.mTimeoutPeriod, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    ThrowableExtension.printStackTrace(e);
                }
                AbstractSmartLinker.this.mIsSmartLinking = false;
                threadPool.shutdownNow();
                AbstractSmartLinker.this.closeDestroySmartConfigSocket();
                AbstractSmartLinker.this.mHander.sendEmptyMessage(2);
                HFLog.d((Object) AbstractSmartLinker.this, "Smart Link finished!");
            }
        }).start();
    }

    public void stop() {
        this.mIsSmartLinking = false;
        closeDestroySmartConfigSocket();
    }

    public boolean isSmartLinking() {
        return this.mIsSmartLinking;
    }

    public void setTimeoutPeriod(int timeoutPeriod) {
        if (timeoutPeriod > 0) {
            this.mTimeoutPeriod = timeoutPeriod;
        }
    }

    public void setWaitMoreDevicePeriod(int period) {
        this.mWaitMoreDevicePeriod = period;
    }
}
