package com.inuker.bluetooth.library.connect;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import com.inuker.bluetooth.library.Constants;
import com.inuker.bluetooth.library.RuntimeChecker;
import com.inuker.bluetooth.library.connect.listener.GattResponseListener;
import com.inuker.bluetooth.library.connect.listener.IBluetoothGattResponse;
import com.inuker.bluetooth.library.connect.listener.ReadCharacterListener;
import com.inuker.bluetooth.library.connect.listener.ReadDescriptorListener;
import com.inuker.bluetooth.library.connect.listener.ReadRssiListener;
import com.inuker.bluetooth.library.connect.listener.ServiceDiscoverListener;
import com.inuker.bluetooth.library.connect.listener.WriteCharacterListener;
import com.inuker.bluetooth.library.connect.listener.WriteDescriptorListener;
import com.inuker.bluetooth.library.connect.response.BluetoothGattResponse;
import com.inuker.bluetooth.library.model.BleGattProfile;
import com.inuker.bluetooth.library.utils.BluetoothLog;
import com.inuker.bluetooth.library.utils.BluetoothUtils;
import com.inuker.bluetooth.library.utils.ByteUtils;
import com.inuker.bluetooth.library.utils.Version;
import com.inuker.bluetooth.library.utils.proxy.ProxyBulk;
import com.inuker.bluetooth.library.utils.proxy.ProxyInterceptor;
import com.inuker.bluetooth.library.utils.proxy.ProxyUtils;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BleConnectWorker implements Callback, IBleConnectWorker, IBluetoothGattResponse, ProxyInterceptor, RuntimeChecker {
    private static final int MSG_GATT_RESPONSE = 288;
    private BleGattProfile mBleGattProfile;
    private BluetoothDevice mBluetoothDevice;
    private BluetoothGatt mBluetoothGatt;
    private IBluetoothGattResponse mBluetoothGattResponse;
    private volatile int mConnectStatus;
    private Map<UUID, Map<UUID, BluetoothGattCharacteristic>> mDeviceProfile;
    private GattResponseListener mGattResponseListener;
    private RuntimeChecker mRuntimeChecker;
    private Handler mWorkerHandler;

    public BleConnectWorker(String mac, RuntimeChecker runtimeChecker) {
        BluetoothAdapter adapter = BluetoothUtils.getBluetoothAdapter();
        if (adapter != null) {
            this.mBluetoothDevice = adapter.getRemoteDevice(mac);
            this.mRuntimeChecker = runtimeChecker;
            this.mWorkerHandler = new Handler(Looper.myLooper(), this);
            this.mDeviceProfile = new HashMap();
            this.mBluetoothGattResponse = (IBluetoothGattResponse) ProxyUtils.getProxy(this, IBluetoothGattResponse.class, this);
            return;
        }
        throw new IllegalStateException("ble adapter null");
    }

    private void refreshServiceProfile() {
        BluetoothLog.v(String.format("refreshServiceProfile for %s", new Object[]{this.mBluetoothDevice.getAddress()}));
        List<BluetoothGattService> services = this.mBluetoothGatt.getServices();
        Map<UUID, Map<UUID, BluetoothGattCharacteristic>> newProfiles = new HashMap<>();
        for (BluetoothGattService service : services) {
            UUID serviceUUID = service.getUuid();
            Map<UUID, BluetoothGattCharacteristic> map = (Map) newProfiles.get(serviceUUID);
            if (map == null) {
                BluetoothLog.v("Service: " + serviceUUID);
                map = new HashMap<>();
                newProfiles.put(service.getUuid(), map);
            }
            for (BluetoothGattCharacteristic character : service.getCharacteristics()) {
                BluetoothLog.v("character: uuid = " + character.getUuid());
                map.put(character.getUuid(), character);
            }
        }
        this.mDeviceProfile.clear();
        this.mDeviceProfile.putAll(newProfiles);
        this.mBleGattProfile = new BleGattProfile(this.mDeviceProfile);
    }

    private BluetoothGattCharacteristic getCharacter(UUID service, UUID character) {
        BluetoothGattCharacteristic characteristic = null;
        if (!(service == null || character == null)) {
            Map<UUID, BluetoothGattCharacteristic> characters = (Map) this.mDeviceProfile.get(service);
            if (characters != null) {
                characteristic = (BluetoothGattCharacteristic) characters.get(character);
            }
        }
        if (characteristic != null || this.mBluetoothGatt == null) {
            return characteristic;
        }
        BluetoothGattService gattService = this.mBluetoothGatt.getService(service);
        if (gattService != null) {
            return gattService.getCharacteristic(character);
        }
        return characteristic;
    }

    private void setConnectStatus(int status) {
        BluetoothLog.v(String.format("setConnectStatus status = %s", new Object[]{Constants.getStatusText(status)}));
        this.mConnectStatus = status;
    }

    public void onConnectionStateChange(int status, int newState) {
        checkRuntime();
        BluetoothLog.v(String.format("onConnectionStateChange for %s: status = %d, newState = %d", new Object[]{this.mBluetoothDevice.getAddress(), Integer.valueOf(status), Integer.valueOf(newState)}));
        if (status == 0 && newState == 2) {
            setConnectStatus(2);
            if (this.mGattResponseListener != null) {
                this.mGattResponseListener.onConnectStatusChanged(true);
                return;
            }
            return;
        }
        closeGatt();
    }

    public void onServicesDiscovered(int status) {
        checkRuntime();
        BluetoothLog.v(String.format("onServicesDiscovered for %s: status = %d", new Object[]{this.mBluetoothDevice.getAddress(), Integer.valueOf(status)}));
        if (status == 0) {
            setConnectStatus(19);
            broadcastConnectStatus(16);
            refreshServiceProfile();
        }
        if (this.mGattResponseListener != null && (this.mGattResponseListener instanceof ServiceDiscoverListener)) {
            ((ServiceDiscoverListener) this.mGattResponseListener).onServicesDiscovered(status, this.mBleGattProfile);
        }
    }

    public void onCharacteristicRead(BluetoothGattCharacteristic characteristic, int status, byte[] value) {
        checkRuntime();
        BluetoothLog.v(String.format("onCharacteristicRead for %s: status = %d, service = 0x%s, character = 0x%s, value = %s", new Object[]{this.mBluetoothDevice.getAddress(), Integer.valueOf(status), characteristic.getService().getUuid(), characteristic.getUuid(), ByteUtils.byteToString(value)}));
        if (this.mGattResponseListener != null && (this.mGattResponseListener instanceof ReadCharacterListener)) {
            ((ReadCharacterListener) this.mGattResponseListener).onCharacteristicRead(characteristic, status, value);
        }
    }

    public void onCharacteristicWrite(BluetoothGattCharacteristic characteristic, int status, byte[] value) {
        checkRuntime();
        BluetoothLog.v(String.format("onCharacteristicWrite for %s: status = %d, service = 0x%s, character = 0x%s, value = %s", new Object[]{this.mBluetoothDevice.getAddress(), Integer.valueOf(status), characteristic.getService().getUuid(), characteristic.getUuid(), ByteUtils.byteToString(value)}));
        if (this.mGattResponseListener != null && (this.mGattResponseListener instanceof WriteCharacterListener)) {
            ((WriteCharacterListener) this.mGattResponseListener).onCharacteristicWrite(characteristic, status, value);
        }
    }

    public void onCharacteristicChanged(BluetoothGattCharacteristic characteristic, byte[] value) {
        checkRuntime();
        BluetoothLog.v(String.format("onCharacteristicChanged for %s: value = %s, service = 0x%s, character = 0x%s", new Object[]{this.mBluetoothDevice.getAddress(), ByteUtils.byteToString(value), characteristic.getService().getUuid(), characteristic.getUuid()}));
        broadcastCharacterChanged(characteristic.getService().getUuid(), characteristic.getUuid(), value);
    }

    public void onDescriptorRead(BluetoothGattDescriptor descriptor, int status, byte[] value) {
        checkRuntime();
        BluetoothLog.v(String.format("onDescriptorRead for %s: status = %d, service = 0x%s, character = 0x%s, descriptor = 0x%s", new Object[]{this.mBluetoothDevice.getAddress(), Integer.valueOf(status), descriptor.getCharacteristic().getService().getUuid(), descriptor.getCharacteristic().getUuid(), descriptor.getUuid()}));
        if (this.mGattResponseListener != null && (this.mGattResponseListener instanceof ReadDescriptorListener)) {
            ((ReadDescriptorListener) this.mGattResponseListener).onDescriptorRead(descriptor, status, value);
        }
    }

    public void onDescriptorWrite(BluetoothGattDescriptor descriptor, int status) {
        checkRuntime();
        BluetoothLog.v(String.format("onDescriptorWrite for %s: status = %d, service = 0x%s, character = 0x%s, descriptor = 0x%s", new Object[]{this.mBluetoothDevice.getAddress(), Integer.valueOf(status), descriptor.getCharacteristic().getService().getUuid(), descriptor.getCharacteristic().getUuid(), descriptor.getUuid()}));
        if (this.mGattResponseListener != null && (this.mGattResponseListener instanceof WriteDescriptorListener)) {
            ((WriteDescriptorListener) this.mGattResponseListener).onDescriptorWrite(descriptor, status);
        }
    }

    public void onReadRemoteRssi(int rssi, int status) {
        checkRuntime();
        BluetoothLog.v(String.format("onReadRemoteRssi for %s, rssi = %d, status = %d", new Object[]{this.mBluetoothDevice.getAddress(), Integer.valueOf(rssi), Integer.valueOf(status)}));
        if (this.mGattResponseListener != null && (this.mGattResponseListener instanceof ReadRssiListener)) {
            ((ReadRssiListener) this.mGattResponseListener).onReadRemoteRssi(rssi, status);
        }
    }

    private void broadcastConnectStatus(int status) {
        Intent intent = new Intent(Constants.ACTION_CONNECT_STATUS_CHANGED);
        intent.putExtra(Constants.EXTRA_MAC, this.mBluetoothDevice.getAddress());
        intent.putExtra(Constants.EXTRA_STATUS, status);
        BluetoothUtils.sendBroadcast(intent);
    }

    private void broadcastCharacterChanged(UUID service, UUID character, byte[] value) {
        Intent intent = new Intent(Constants.ACTION_CHARACTER_CHANGED);
        intent.putExtra(Constants.EXTRA_MAC, this.mBluetoothDevice.getAddress());
        intent.putExtra(Constants.EXTRA_SERVICE_UUID, service);
        intent.putExtra(Constants.EXTRA_CHARACTER_UUID, character);
        intent.putExtra(Constants.EXTRA_BYTE_VALUE, value);
        BluetoothUtils.sendBroadcast(intent);
    }

    public boolean openGatt() {
        checkRuntime();
        BluetoothLog.v(String.format("openGatt for %s", new Object[]{getAddress()}));
        if (this.mBluetoothGatt != null) {
            BluetoothLog.e(String.format("Previous gatt not closed", new Object[0]));
            return true;
        }
        Context context = BluetoothUtils.getContext();
        BluetoothGattCallback callback = new BluetoothGattResponse(this.mBluetoothGattResponse);
        if (Version.isMarshmallow()) {
            this.mBluetoothGatt = this.mBluetoothDevice.connectGatt(context, false, callback, 2);
        } else {
            this.mBluetoothGatt = this.mBluetoothDevice.connectGatt(context, false, callback);
        }
        if (this.mBluetoothGatt != null) {
            return true;
        }
        BluetoothLog.e(String.format("openGatt failed: connectGatt return null!", new Object[0]));
        return false;
    }

    private String getAddress() {
        return this.mBluetoothDevice.getAddress();
    }

    public void closeGatt() {
        checkRuntime();
        BluetoothLog.v(String.format("closeGatt for %s", new Object[]{getAddress()}));
        if (this.mBluetoothGatt != null) {
            this.mBluetoothGatt.close();
            this.mBluetoothGatt = null;
        }
        if (this.mGattResponseListener != null) {
            this.mGattResponseListener.onConnectStatusChanged(false);
        }
        setConnectStatus(0);
        broadcastConnectStatus(32);
    }

    public boolean discoverService() {
        checkRuntime();
        BluetoothLog.v(String.format("discoverService for %s", new Object[]{getAddress()}));
        if (this.mBluetoothGatt == null) {
            BluetoothLog.e(String.format("discoverService but gatt is null!", new Object[0]));
            return false;
        } else if (this.mBluetoothGatt.discoverServices()) {
            return true;
        } else {
            BluetoothLog.e(String.format("discoverServices failed", new Object[0]));
            return false;
        }
    }

    public int getCurrentStatus() {
        checkRuntime();
        return this.mConnectStatus;
    }

    public void registerGattResponseListener(GattResponseListener listener) {
        checkRuntime();
        this.mGattResponseListener = listener;
    }

    public void clearGattResponseListener(GattResponseListener listener) {
        checkRuntime();
        if (this.mGattResponseListener == listener) {
            this.mGattResponseListener = null;
        }
    }

    public boolean refreshDeviceCache() {
        BluetoothLog.v(String.format("refreshDeviceCache for %s", new Object[]{getAddress()}));
        checkRuntime();
        if (this.mBluetoothGatt == null) {
            BluetoothLog.e(String.format("ble gatt null", new Object[0]));
            return false;
        } else if (BluetoothUtils.refreshGattCache(this.mBluetoothGatt)) {
            return true;
        } else {
            BluetoothLog.e(String.format("refreshDeviceCache failed", new Object[0]));
            return false;
        }
    }

    public boolean readCharacteristic(UUID service, UUID character) {
        BluetoothLog.v(String.format("readCharacteristic for %s: service = 0x%s, character = 0x%s", new Object[]{this.mBluetoothDevice.getAddress(), service, character}));
        checkRuntime();
        BluetoothGattCharacteristic characteristic = getCharacter(service, character);
        if (characteristic == null) {
            BluetoothLog.e(String.format("characteristic not exist!", new Object[0]));
            return false;
        } else if (!isCharacteristicReadable(characteristic)) {
            BluetoothLog.e(String.format("characteristic not readable!", new Object[0]));
            return false;
        } else if (this.mBluetoothGatt == null) {
            BluetoothLog.e(String.format("ble gatt null", new Object[0]));
            return false;
        } else if (this.mBluetoothGatt.readCharacteristic(characteristic)) {
            return true;
        } else {
            BluetoothLog.e(String.format("readCharacteristic failed", new Object[0]));
            return false;
        }
    }

    public boolean writeCharacteristic(UUID service, UUID character, byte[] value) {
        BluetoothLog.v(String.format("writeCharacteristic for %s: service = 0x%s, character = 0x%s, value = 0x%s", new Object[]{this.mBluetoothDevice.getAddress(), service, character, ByteUtils.byteToString(value)}));
        checkRuntime();
        BluetoothGattCharacteristic characteristic = getCharacter(service, character);
        if (characteristic == null) {
            BluetoothLog.e(String.format("characteristic not exist!", new Object[0]));
            return false;
        } else if (!isCharacteristicWritable(characteristic)) {
            BluetoothLog.e(String.format("characteristic not writable!", new Object[0]));
            return false;
        } else if (this.mBluetoothGatt == null) {
            BluetoothLog.e(String.format("ble gatt null", new Object[0]));
            return false;
        } else {
            if (value == null) {
                value = ByteUtils.EMPTY_BYTES;
            }
            characteristic.setValue(value);
            if (this.mBluetoothGatt.writeCharacteristic(characteristic)) {
                return true;
            }
            BluetoothLog.e(String.format("writeCharacteristic failed", new Object[0]));
            return false;
        }
    }

    public boolean readDescriptor(UUID service, UUID character, UUID descriptor) {
        BluetoothLog.v(String.format("readDescriptor for %s: service = 0x%s, character = 0x%s, descriptor = 0x%s", new Object[]{this.mBluetoothDevice.getAddress(), service, character, descriptor}));
        checkRuntime();
        BluetoothGattCharacteristic characteristic = getCharacter(service, character);
        if (characteristic == null) {
            BluetoothLog.e(String.format("characteristic not exist!", new Object[0]));
            return false;
        }
        BluetoothGattDescriptor gattDescriptor = characteristic.getDescriptor(descriptor);
        if (gattDescriptor == null) {
            BluetoothLog.e(String.format("descriptor not exist", new Object[0]));
            return false;
        } else if (this.mBluetoothGatt == null) {
            BluetoothLog.e(String.format("ble gatt null", new Object[0]));
            return false;
        } else if (this.mBluetoothGatt.readDescriptor(gattDescriptor)) {
            return true;
        } else {
            BluetoothLog.e(String.format("readDescriptor failed", new Object[0]));
            return false;
        }
    }

    public boolean writeDescriptor(UUID service, UUID character, UUID descriptor, byte[] value) {
        BluetoothLog.v(String.format("writeDescriptor for %s: service = 0x%s, character = 0x%s, descriptor = 0x%s, value = 0x%s", new Object[]{this.mBluetoothDevice.getAddress(), service, character, descriptor, ByteUtils.byteToString(value)}));
        checkRuntime();
        BluetoothGattCharacteristic characteristic = getCharacter(service, character);
        if (characteristic == null) {
            BluetoothLog.e(String.format("characteristic not exist!", new Object[0]));
            return false;
        }
        BluetoothGattDescriptor gattDescriptor = characteristic.getDescriptor(descriptor);
        if (gattDescriptor == null) {
            BluetoothLog.e(String.format("descriptor not exist", new Object[0]));
            return false;
        } else if (this.mBluetoothGatt == null) {
            BluetoothLog.e(String.format("ble gatt null", new Object[0]));
            return false;
        } else {
            if (value == null) {
                value = ByteUtils.EMPTY_BYTES;
            }
            gattDescriptor.setValue(value);
            if (this.mBluetoothGatt.writeDescriptor(gattDescriptor)) {
                return true;
            }
            BluetoothLog.e(String.format("writeDescriptor failed", new Object[0]));
            return false;
        }
    }

    public boolean writeCharacteristicWithNoRsp(UUID service, UUID character, byte[] value) {
        BluetoothLog.v(String.format("writeCharacteristicWithNoRsp for %s: service = 0x%s, character = 0x%s, value = 0x%s", new Object[]{this.mBluetoothDevice.getAddress(), service, character, ByteUtils.byteToString(value)}));
        checkRuntime();
        BluetoothGattCharacteristic characteristic = getCharacter(service, character);
        if (characteristic == null) {
            BluetoothLog.e(String.format("characteristic not exist!", new Object[0]));
            return false;
        } else if (!isCharacteristicNoRspWritable(characteristic)) {
            BluetoothLog.e(String.format("characteristic not norsp writable!", new Object[0]));
            return false;
        } else if (this.mBluetoothGatt == null) {
            BluetoothLog.e(String.format("ble gatt null", new Object[0]));
            return false;
        } else {
            if (value == null) {
                value = ByteUtils.EMPTY_BYTES;
            }
            characteristic.setValue(value);
            characteristic.setWriteType(1);
            if (this.mBluetoothGatt.writeCharacteristic(characteristic)) {
                return true;
            }
            BluetoothLog.e(String.format("writeCharacteristic failed", new Object[0]));
            return false;
        }
    }

    public boolean setCharacteristicNotification(UUID service, UUID character, boolean enable) {
        checkRuntime();
        BluetoothLog.v(String.format("setCharacteristicNotification for %s, service = %s, character = %s, enable = %b", new Object[]{getAddress(), service, character, Boolean.valueOf(enable)}));
        BluetoothGattCharacteristic characteristic = getCharacter(service, character);
        if (characteristic == null) {
            BluetoothLog.e(String.format("characteristic not exist!", new Object[0]));
            return false;
        } else if (!isCharacteristicNotifyable(characteristic)) {
            BluetoothLog.e(String.format("characteristic not notifyable!", new Object[0]));
            return false;
        } else if (this.mBluetoothGatt == null) {
            BluetoothLog.e(String.format("ble gatt null", new Object[0]));
            return false;
        } else if (!this.mBluetoothGatt.setCharacteristicNotification(characteristic, enable)) {
            BluetoothLog.e(String.format("setCharacteristicNotification failed", new Object[0]));
            return false;
        } else {
            BluetoothGattDescriptor descriptor = characteristic.getDescriptor(Constants.CLIENT_CHARACTERISTIC_CONFIG);
            if (descriptor == null) {
                BluetoothLog.e(String.format("getDescriptor for notify null!", new Object[0]));
                return false;
            }
            if (!descriptor.setValue(enable ? BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE : BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE)) {
                BluetoothLog.e(String.format("setValue for notify descriptor failed!", new Object[0]));
                return false;
            } else if (this.mBluetoothGatt.writeDescriptor(descriptor)) {
                return true;
            } else {
                BluetoothLog.e(String.format("writeDescriptor for notify failed", new Object[0]));
                return false;
            }
        }
    }

    public boolean setCharacteristicIndication(UUID service, UUID character, boolean enable) {
        checkRuntime();
        BluetoothLog.v(String.format("setCharacteristicIndication for %s, service = %s, character = %s, enable = %b", new Object[]{getAddress(), service, character, Boolean.valueOf(enable)}));
        BluetoothGattCharacteristic characteristic = getCharacter(service, character);
        if (characteristic == null) {
            BluetoothLog.e(String.format("characteristic not exist!", new Object[0]));
            return false;
        } else if (!isCharacteristicIndicatable(characteristic)) {
            BluetoothLog.e(String.format("characteristic not indicatable!", new Object[0]));
            return false;
        } else if (this.mBluetoothGatt == null) {
            BluetoothLog.e(String.format("ble gatt null", new Object[0]));
            return false;
        } else if (!this.mBluetoothGatt.setCharacteristicNotification(characteristic, enable)) {
            BluetoothLog.e(String.format("setCharacteristicIndication failed", new Object[0]));
            return false;
        } else {
            BluetoothGattDescriptor descriptor = characteristic.getDescriptor(Constants.CLIENT_CHARACTERISTIC_CONFIG);
            if (descriptor == null) {
                BluetoothLog.e(String.format("getDescriptor for indicate null!", new Object[0]));
                return false;
            }
            if (!descriptor.setValue(enable ? BluetoothGattDescriptor.ENABLE_INDICATION_VALUE : BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE)) {
                BluetoothLog.e(String.format("setValue for indicate descriptor failed!", new Object[0]));
                return false;
            } else if (this.mBluetoothGatt.writeDescriptor(descriptor)) {
                return true;
            } else {
                BluetoothLog.e(String.format("writeDescriptor for indicate failed", new Object[0]));
                return false;
            }
        }
    }

    public boolean readRemoteRssi() {
        checkRuntime();
        BluetoothLog.v(String.format("readRemoteRssi for %s", new Object[]{getAddress()}));
        if (this.mBluetoothGatt == null) {
            BluetoothLog.e(String.format("ble gatt null", new Object[0]));
            return false;
        } else if (this.mBluetoothGatt.readRemoteRssi()) {
            return true;
        } else {
            BluetoothLog.e(String.format("readRemoteRssi failed", new Object[0]));
            return false;
        }
    }

    public BleGattProfile getGattProfile() {
        return this.mBleGattProfile;
    }

    private boolean isCharacteristicReadable(BluetoothGattCharacteristic characteristic) {
        return (characteristic == null || (characteristic.getProperties() & 2) == 0) ? false : true;
    }

    private boolean isCharacteristicWritable(BluetoothGattCharacteristic characteristic) {
        return (characteristic == null || (characteristic.getProperties() & 8) == 0) ? false : true;
    }

    private boolean isCharacteristicNoRspWritable(BluetoothGattCharacteristic characteristic) {
        return (characteristic == null || (characteristic.getProperties() & 4) == 0) ? false : true;
    }

    private boolean isCharacteristicNotifyable(BluetoothGattCharacteristic characteristic) {
        return (characteristic == null || (characteristic.getProperties() & 16) == 0) ? false : true;
    }

    private boolean isCharacteristicIndicatable(BluetoothGattCharacteristic characteristic) {
        return (characteristic == null || (characteristic.getProperties() & 32) == 0) ? false : true;
    }

    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_GATT_RESPONSE /*288*/:
                ProxyBulk.safeInvoke(msg.obj);
                break;
        }
        return true;
    }

    public boolean onIntercept(Object object, Method method, Object[] args) {
        this.mWorkerHandler.obtainMessage(MSG_GATT_RESPONSE, new ProxyBulk(object, method, args)).sendToTarget();
        return true;
    }

    public void checkRuntime() {
        this.mRuntimeChecker.checkRuntime();
    }
}
