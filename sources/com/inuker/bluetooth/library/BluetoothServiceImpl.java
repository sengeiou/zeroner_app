package com.inuker.bluetooth.library;

import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.inuker.bluetooth.library.IBluetoothService.Stub;
import com.inuker.bluetooth.library.connect.BleConnectManager;
import com.inuker.bluetooth.library.connect.options.BleConnectOptions;
import com.inuker.bluetooth.library.connect.response.BleGeneralResponse;
import com.inuker.bluetooth.library.search.BluetoothSearchManager;
import com.inuker.bluetooth.library.search.SearchRequest;
import java.util.UUID;

public class BluetoothServiceImpl extends Stub implements Callback {
    private static BluetoothServiceImpl sInstance;
    private Handler mHandler = new Handler(Looper.getMainLooper(), this);

    private BluetoothServiceImpl() {
    }

    public static BluetoothServiceImpl getInstance() {
        if (sInstance == null) {
            synchronized (BluetoothServiceImpl.class) {
                if (sInstance == null) {
                    sInstance = new BluetoothServiceImpl();
                }
            }
        }
        return sInstance;
    }

    public void callBluetoothApi(int code, Bundle args, final IResponse response) throws RemoteException {
        Message msg = this.mHandler.obtainMessage(code, new BleGeneralResponse() {
            public void onResponse(int code, Bundle data) {
                if (response != null) {
                    if (data == null) {
                        data = new Bundle();
                    }
                    try {
                        response.onResponse(code, data);
                    } catch (RemoteException e) {
                        ThrowableExtension.printStackTrace(e);
                    }
                }
            }
        });
        args.setClassLoader(getClass().getClassLoader());
        msg.setData(args);
        msg.sendToTarget();
    }

    public boolean handleMessage(Message msg) {
        Bundle args = msg.getData();
        String mac = args.getString(Constants.EXTRA_MAC);
        UUID service = (UUID) args.getSerializable(Constants.EXTRA_SERVICE_UUID);
        UUID character = (UUID) args.getSerializable(Constants.EXTRA_CHARACTER_UUID);
        UUID descriptor = (UUID) args.getSerializable(Constants.EXTRA_DESCRIPTOR_UUID);
        byte[] value = args.getByteArray(Constants.EXTRA_BYTE_VALUE);
        BleGeneralResponse response = (BleGeneralResponse) msg.obj;
        switch (msg.what) {
            case 1:
                BleConnectManager.connect(mac, (BleConnectOptions) args.getParcelable(Constants.EXTRA_OPTIONS), response);
                break;
            case 2:
                BleConnectManager.disconnect(mac);
                break;
            case 3:
                BleConnectManager.read(mac, service, character, response);
                break;
            case 4:
                BleConnectManager.write(mac, service, character, value, response);
                break;
            case 5:
                BleConnectManager.writeNoRsp(mac, service, character, value, response);
                break;
            case 6:
                BleConnectManager.notify(mac, service, character, response);
                break;
            case 7:
                BleConnectManager.unnotify(mac, service, character, response);
                break;
            case 8:
                BleConnectManager.readRssi(mac, response);
                break;
            case 10:
                BleConnectManager.indicate(mac, service, character, response);
                break;
            case 11:
                BluetoothSearchManager.search((SearchRequest) args.getParcelable(Constants.EXTRA_REQUEST), response);
                break;
            case 12:
                BluetoothSearchManager.stopSearch();
                break;
            case 13:
                BleConnectManager.readDescriptor(mac, service, character, descriptor, response);
                break;
            case 14:
                BleConnectManager.writeDescriptor(mac, service, character, descriptor, value, response);
                break;
            case 20:
                BleConnectManager.clearRequest(mac, args.getInt(Constants.EXTRA_TYPE, 0));
                break;
            case 21:
                BleConnectManager.refreshCache(mac);
                break;
        }
        return true;
    }
}
