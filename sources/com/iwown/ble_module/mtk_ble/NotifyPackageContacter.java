package com.iwown.ble_module.mtk_ble;

import android.util.Log;
import com.google.common.primitives.UnsignedBytes;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.utils.Util;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class NotifyPackageContacter {
    private static int dataLength = -1;
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();
    public static NotifyPackageContacter instance = null;
    private static boolean isDataOver = true;
    private String TAG = getClass().getSimpleName();
    private byte[] datas = null;

    public byte[] getDatas() {
        return this.datas;
    }

    public void setDatas(byte[] datas2) {
        this.datas = datas2;
    }

    private NotifyPackageContacter() {
    }

    public static NotifyPackageContacter getInstance() {
        if (instance == null) {
            synchronized (NotifyPackageContacter.class) {
                if (instance == null) {
                    instance = new NotifyPackageContacter();
                }
            }
        }
        return instance;
    }

    public boolean contactData(byte[] data) {
        if (!(data == null || data.length == 0)) {
            Log.i(this.TAG, "接收原始数据 未处理 datas--->" + Util.bytesToString(data));
            if (!isDataOver || dataLength != -1) {
                if ((data[0] == 35 || data[0] == 34) && data.length > 3 && data[1] == -1) {
                    this.datas = new byte[0];
                    dataLength = data[3] & UnsignedBytes.MAX_VALUE;
                    Log.i(this.TAG, "发现掉包情况 --->" + Util.bytesToString(data));
                    MtkBroadcastSender.getInstance().onError(MTKBleError.BLE_NOTIFY_DATA_LOSE_PACKAGE);
                }
            } else if (data[0] == 35 || data[0] == 34) {
                dataLength = data[3] & UnsignedBytes.MAX_VALUE;
                this.datas = new byte[0];
            }
            if (dataLength != -1) {
                this.datas = Util.concat(this.datas, data);
            }
            try {
                if (dataLength != -1 && this.datas.length - 4 >= dataLength) {
                    Log.i(this.TAG, "接收数据长度 datas--->" + (this.datas.length - 4) + "    ff = " + this.datas[1]);
                    Log.i(this.TAG, "接收原始数据 datas--->" + Util.bytesToString(this.datas));
                    clearDataState();
                    return true;
                } else if (dataLength != -1) {
                    isDataOver = false;
                    return isDataOver;
                }
            } catch (Exception e) {
                clearDataState();
                ThrowableExtension.printStackTrace(e);
                return false;
            }
        }
        return false;
    }

    private void clearDataState() {
        isDataOver = true;
        dataLength = -1;
    }
}
