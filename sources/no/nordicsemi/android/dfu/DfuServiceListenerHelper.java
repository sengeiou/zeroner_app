package no.nordicsemi.android.dfu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import no.nordicsemi.android.error.GattError;

public class DfuServiceListenerHelper {
    private static LogBroadcastReceiver mLogBroadcastReceiver;
    private static ProgressBroadcastsReceiver mProgressBroadcastReceiver;

    private static class LogBroadcastReceiver extends BroadcastReceiver {
        private DfuLogListener mGlobalLogListener;
        private Map<String, DfuLogListener> mListeners;

        private LogBroadcastReceiver() {
            this.mListeners = new HashMap();
        }

        /* access modifiers changed from: private */
        public void setLogListener(DfuLogListener globalLogListener) {
            this.mGlobalLogListener = globalLogListener;
        }

        /* access modifiers changed from: private */
        public void setLogListener(String deviceAddress, DfuLogListener listener) {
            this.mListeners.put(deviceAddress, listener);
            this.mListeners.put(DfuServiceListenerHelper.getIncrementedAddress(deviceAddress), listener);
        }

        /* access modifiers changed from: private */
        public boolean removeLogListener(DfuLogListener listener) {
            if (this.mGlobalLogListener == listener) {
                this.mGlobalLogListener = null;
            }
            Iterator it = this.mListeners.entrySet().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Entry<String, DfuLogListener> entry = (Entry) it.next();
                if (entry.getValue() == listener) {
                    this.mListeners.remove(entry.getKey());
                    break;
                }
            }
            Iterator it2 = this.mListeners.entrySet().iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                Entry<String, DfuLogListener> entry2 = (Entry) it2.next();
                if (entry2.getValue() == listener) {
                    this.mListeners.remove(entry2.getKey());
                    break;
                }
            }
            return this.mGlobalLogListener == null && this.mListeners.isEmpty();
        }

        public void onReceive(Context context, Intent intent) {
            String address = intent.getStringExtra("no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_ADDRESS");
            DfuLogListener globalListener = this.mGlobalLogListener;
            DfuLogListener deviceListener = (DfuLogListener) this.mListeners.get(address);
            if (globalListener != null || deviceListener != null) {
                int level = intent.getIntExtra(DfuBaseService.EXTRA_LOG_LEVEL, 0);
                String message = intent.getStringExtra(DfuBaseService.EXTRA_LOG_MESSAGE);
                if (globalListener != null) {
                    globalListener.onLogEvent(address, level, message);
                }
                if (deviceListener != null) {
                    deviceListener.onLogEvent(address, level, message);
                }
            }
        }
    }

    private static class ProgressBroadcastsReceiver extends BroadcastReceiver {
        private DfuProgressListener mGlobalProgressListener;
        private Map<String, DfuProgressListener> mListeners;

        private ProgressBroadcastsReceiver() {
            this.mListeners = new HashMap();
        }

        /* access modifiers changed from: private */
        public void setProgressListener(DfuProgressListener globalProgressListener) {
            this.mGlobalProgressListener = globalProgressListener;
        }

        /* access modifiers changed from: private */
        public void setProgressListener(String deviceAddress, DfuProgressListener listener) {
            this.mListeners.put(deviceAddress, listener);
            this.mListeners.put(DfuServiceListenerHelper.getIncrementedAddress(deviceAddress), listener);
        }

        /* access modifiers changed from: private */
        public boolean removeProgressListener(DfuProgressListener listener) {
            if (this.mGlobalProgressListener == listener) {
                this.mGlobalProgressListener = null;
            }
            Iterator it = this.mListeners.entrySet().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Entry<String, DfuProgressListener> entry = (Entry) it.next();
                if (entry.getValue() == listener) {
                    this.mListeners.remove(entry.getKey());
                    break;
                }
            }
            Iterator it2 = this.mListeners.entrySet().iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                Entry<String, DfuProgressListener> entry2 = (Entry) it2.next();
                if (entry2.getValue() == listener) {
                    this.mListeners.remove(entry2.getKey());
                    break;
                }
            }
            return this.mGlobalProgressListener == null && this.mListeners.isEmpty();
        }

        public void onReceive(Context context, Intent intent) {
            String address = intent.getStringExtra("no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_ADDRESS");
            DfuProgressListener globalListener = this.mGlobalProgressListener;
            DfuProgressListener deviceListener = (DfuProgressListener) this.mListeners.get(address);
            if (globalListener != null || deviceListener != null) {
                String action = intent.getAction();
                char c = 65535;
                switch (action.hashCode()) {
                    case -2021868104:
                        if (action.equals("no.nordicsemi.android.dfu.broadcast.BROADCAST_PROGRESS")) {
                            c = 0;
                            break;
                        }
                        break;
                    case -1282379203:
                        if (action.equals("no.nordicsemi.android.dfu.broadcast.BROADCAST_ERROR")) {
                            c = 1;
                            break;
                        }
                        break;
                }
                switch (c) {
                    case 0:
                        int progress = intent.getIntExtra("no.nordicsemi.android.dfu.extra.EXTRA_DATA", 0);
                        float speed = intent.getFloatExtra("no.nordicsemi.android.dfu.extra.EXTRA_SPEED_B_PER_MS", 0.0f);
                        float avgSpeed = intent.getFloatExtra("no.nordicsemi.android.dfu.extra.EXTRA_AVG_SPEED_B_PER_MS", 0.0f);
                        int currentPart = intent.getIntExtra("no.nordicsemi.android.dfu.extra.EXTRA_PART_CURRENT", 0);
                        int partsTotal = intent.getIntExtra("no.nordicsemi.android.dfu.extra.EXTRA_PARTS_TOTAL", 0);
                        switch (progress) {
                            case -7:
                                if (globalListener != null) {
                                    globalListener.onDeviceDisconnected(address);
                                    globalListener.onDfuAborted(address);
                                }
                                if (deviceListener != null) {
                                    deviceListener.onDeviceDisconnected(address);
                                    deviceListener.onDfuAborted(address);
                                    return;
                                }
                                return;
                            case -6:
                                if (globalListener != null) {
                                    globalListener.onDeviceDisconnected(address);
                                    globalListener.onDfuCompleted(address);
                                }
                                if (deviceListener != null) {
                                    deviceListener.onDeviceDisconnected(address);
                                    deviceListener.onDfuCompleted(address);
                                    return;
                                }
                                return;
                            case -5:
                                if (globalListener != null) {
                                    globalListener.onDeviceDisconnecting(address);
                                }
                                if (deviceListener != null) {
                                    deviceListener.onDeviceDisconnecting(address);
                                    return;
                                }
                                return;
                            case -4:
                                if (globalListener != null) {
                                    globalListener.onFirmwareValidating(address);
                                }
                                if (deviceListener != null) {
                                    deviceListener.onFirmwareValidating(address);
                                    return;
                                }
                                return;
                            case -3:
                                if (globalListener != null) {
                                    globalListener.onEnablingDfuMode(address);
                                }
                                if (deviceListener != null) {
                                    deviceListener.onEnablingDfuMode(address);
                                    return;
                                }
                                return;
                            case -2:
                                if (globalListener != null) {
                                    globalListener.onDeviceConnected(address);
                                    globalListener.onDfuProcessStarting(address);
                                }
                                if (deviceListener != null) {
                                    deviceListener.onDeviceConnected(address);
                                    deviceListener.onDfuProcessStarting(address);
                                    return;
                                }
                                return;
                            case -1:
                                if (globalListener != null) {
                                    globalListener.onDeviceConnecting(address);
                                }
                                if (deviceListener != null) {
                                    deviceListener.onDeviceConnecting(address);
                                    return;
                                }
                                return;
                            default:
                                if (progress == 0) {
                                    if (globalListener != null) {
                                        globalListener.onDfuProcessStarted(address);
                                    }
                                    if (deviceListener != null) {
                                        deviceListener.onDfuProcessStarted(address);
                                    }
                                }
                                if (globalListener != null) {
                                    globalListener.onProgressChanged(address, progress, speed, avgSpeed, currentPart, partsTotal);
                                }
                                if (deviceListener != null) {
                                    deviceListener.onProgressChanged(address, progress, speed, avgSpeed, currentPart, partsTotal);
                                    return;
                                }
                                return;
                        }
                    case 1:
                        int error = intent.getIntExtra("no.nordicsemi.android.dfu.extra.EXTRA_DATA", 0);
                        int errorType = intent.getIntExtra("no.nordicsemi.android.dfu.extra.EXTRA_ERROR_TYPE", 0);
                        if (globalListener != null) {
                            globalListener.onDeviceDisconnected(address);
                        }
                        if (deviceListener != null) {
                            deviceListener.onDeviceDisconnected(address);
                        }
                        switch (errorType) {
                            case 1:
                                if (globalListener != null) {
                                    globalListener.onError(address, error, errorType, GattError.parseConnectionError(error));
                                }
                                if (deviceListener != null) {
                                    deviceListener.onError(address, error, errorType, GattError.parseConnectionError(error));
                                    return;
                                }
                                return;
                            case 3:
                                if (globalListener != null) {
                                    globalListener.onError(address, error, errorType, GattError.parseDfuRemoteError(error));
                                }
                                if (deviceListener != null) {
                                    deviceListener.onError(address, error, errorType, GattError.parseDfuRemoteError(error));
                                    return;
                                }
                                return;
                            default:
                                if (globalListener != null) {
                                    globalListener.onError(address, error, errorType, GattError.parse(error));
                                }
                                if (deviceListener != null) {
                                    deviceListener.onError(address, error, errorType, GattError.parse(error));
                                    return;
                                }
                                return;
                        }
                    default:
                        return;
                }
            }
        }
    }

    public static void registerProgressListener(Context context, DfuProgressListener listener) {
        if (mProgressBroadcastReceiver == null) {
            mProgressBroadcastReceiver = new ProgressBroadcastsReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction("no.nordicsemi.android.dfu.broadcast.BROADCAST_PROGRESS");
            filter.addAction("no.nordicsemi.android.dfu.broadcast.BROADCAST_ERROR");
            LocalBroadcastManager.getInstance(context).registerReceiver(mProgressBroadcastReceiver, filter);
        }
        mProgressBroadcastReceiver.setProgressListener(listener);
    }

    public static void registerProgressListener(Context context, DfuProgressListener listener, String deviceAddress) {
        if (mProgressBroadcastReceiver == null) {
            mProgressBroadcastReceiver = new ProgressBroadcastsReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction("no.nordicsemi.android.dfu.broadcast.BROADCAST_PROGRESS");
            filter.addAction("no.nordicsemi.android.dfu.broadcast.BROADCAST_ERROR");
            LocalBroadcastManager.getInstance(context).registerReceiver(mProgressBroadcastReceiver, filter);
        }
        mProgressBroadcastReceiver.setProgressListener(deviceAddress, listener);
    }

    public static void unregisterProgressListener(Context context, DfuProgressListener listener) {
        if (mProgressBroadcastReceiver != null && mProgressBroadcastReceiver.removeProgressListener(listener)) {
            LocalBroadcastManager.getInstance(context).unregisterReceiver(mProgressBroadcastReceiver);
            mProgressBroadcastReceiver = null;
        }
    }

    public static void registerLogListener(Context context, DfuLogListener listener) {
        if (mLogBroadcastReceiver == null) {
            mLogBroadcastReceiver = new LogBroadcastReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(DfuBaseService.BROADCAST_LOG);
            LocalBroadcastManager.getInstance(context).registerReceiver(mLogBroadcastReceiver, filter);
        }
        mLogBroadcastReceiver.setLogListener(listener);
    }

    public static void registerLogListener(Context context, DfuLogListener listener, String deviceAddress) {
        if (mLogBroadcastReceiver == null) {
            mLogBroadcastReceiver = new LogBroadcastReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(DfuBaseService.BROADCAST_LOG);
            LocalBroadcastManager.getInstance(context).registerReceiver(mLogBroadcastReceiver, filter);
        }
        mLogBroadcastReceiver.setLogListener(deviceAddress, listener);
    }

    public static void unregisterLogListener(Context context, DfuLogListener listener) {
        if (mLogBroadcastReceiver != null && mLogBroadcastReceiver.removeLogListener(listener)) {
            LocalBroadcastManager.getInstance(context).unregisterReceiver(mLogBroadcastReceiver);
            mLogBroadcastReceiver = null;
        }
    }

    /* access modifiers changed from: private */
    public static String getIncrementedAddress(String deviceAddress) {
        String firstBytes = deviceAddress.substring(0, 15);
        return firstBytes + String.format("%02X", new Object[]{Integer.valueOf((Integer.valueOf(deviceAddress.substring(15), 16).intValue() + 1) & 255)});
    }
}
