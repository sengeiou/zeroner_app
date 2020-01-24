package com.peng.ppscalelibrary.BleManager.Manager;

import com.inuker.bluetooth.library.beacon.Beacon;
import com.inuker.bluetooth.library.beacon.BeaconItem;
import com.inuker.bluetooth.library.model.BleGattCharacter;
import com.inuker.bluetooth.library.model.BleGattProfile;
import com.inuker.bluetooth.library.model.BleGattService;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.utils.BluetoothLog;
import com.inuker.bluetooth.library.utils.ByteUtils;
import com.peng.ppscalelibrary.BleManager.Interface.BleConnectFliterInterface;
import com.peng.ppscalelibrary.BleManager.Model.BleConnectFliterModel;
import com.peng.ppscalelibrary.BleManager.Model.BleDeviceModel;
import java.util.List;

public class BleConnectFliterManager {
    private List<BleDeviceModel> bindingList;
    private BleConnectFliterModel matchedFliterModel;

    public void setBindingList(List<BleDeviceModel> bindingList2) {
        this.bindingList = bindingList2;
    }

    public boolean exceptBindedList2ConnectDevice(SearchResult resultDevice) {
        return filterByDeviceList(resultDevice, true);
    }

    public boolean inBindedList2ConnectDevice(SearchResult resultDevice) {
        return filterByDeviceList(resultDevice, false);
    }

    public boolean needNotConnect(SearchResult resultDevice) {
        if (!resultDevice.getName().equals(BleConnectFliterModel.ELECTRONIC_SCALE)) {
            return true;
        }
        return false;
    }

    public BleConnectFliterModel getMatchedFliterModel() {
        return this.matchedFliterModel;
    }

    public void monitorTargetResponse(int code, BleGattProfile profile, BleConnectFliterInterface fliterInterface) {
        if (code == 0) {
            for (BleGattService service : profile.getServices()) {
                if (this.matchedFliterModel != null && service.getUUID().toString().startsWith(this.matchedFliterModel.getServiceUUID())) {
                    for (BleGattCharacter character : service.getCharacters()) {
                        if (character.getUuid().toString().startsWith(this.matchedFliterModel.getCharacteristicNofifyUUID()) && fliterInterface != null) {
                            fliterInterface.targetResponse(service.getUUID(), character.getUuid(), this.matchedFliterModel);
                        }
                        if (character.getUuid().toString().startsWith(this.matchedFliterModel.getCharacteristicWriteUUID()) && fliterInterface != null) {
                            fliterInterface.target2Write(service.getUUID(), character.getUuid(), this.matchedFliterModel);
                        }
                    }
                }
            }
        }
    }

    public void monitirBMDJResponse(int code, BleGattProfile profile, BleConnectFliterInterface fliterInterface) {
        BluetoothLog.d(String.format("monitirBMDJResponse：profile----------------------:\n%s", new Object[]{profile}));
        if (code == 0) {
            for (BleGattService service : profile.getServices()) {
                if (this.matchedFliterModel != null && service.getUUID().toString().startsWith(this.matchedFliterModel.getServiceUUID())) {
                    for (BleGattCharacter character : service.getCharacters()) {
                        if (character.getUuid().toString().startsWith(this.matchedFliterModel.getCharacteristicBMDJUUID()) && fliterInterface != null) {
                            BluetoothLog.d(String.format("service.getUUID()----------------------:%s\ncharacter.getUuid()----------------------:%s", new Object[]{service.getUUID(), character.getUuid()}));
                            fliterInterface.targetResponse(service.getUUID(), character.getUuid(), this.matchedFliterModel);
                        }
                    }
                }
            }
        }
    }

    private boolean filterByDeviceList(SearchResult device, boolean isBinding) {
        if (isTargetDevice(device, BleConnectFliterModel.getFliterArr())) {
            return inBindedList(device, this.bindingList, isBinding);
        }
        return false;
    }

    private boolean isTargetDevice(SearchResult device, List<BleConnectFliterModel> fliterList) {
        for (BleConnectFliterModel model : fliterList) {
            if (equalNameWithCurrentFliterModel(device, model)) {
                boolean equalBeaconBytesLenght = equalBeaconBytesLenghtWithCuurentFliterModel(device, model);
                BluetoothLog.v("equalBeaconBytesLenght----" + device.toString());
                if (equalBeaconBytesLenght && equalAdvDataLengthWithCuurentFliterModel(device, model)) {
                    this.matchedFliterModel = model;
                    BluetoothLog.v("matchedFliterModel----" + model.toString());
                    return true;
                }
            }
        }
        return false;
    }

    private boolean equalNameWithCurrentFliterModel(SearchResult deviceModel, BleConnectFliterModel fliterModel) {
        if (deviceModel.getName().equals(fliterModel.getScaleName())) {
            return true;
        }
        return false;
    }

    private boolean equalAdvDataLengthWithCuurentFliterModel(SearchResult device, BleConnectFliterModel fliterModel) {
        for (BeaconItem beaconItem : new Beacon(device.scanRecord).mItems) {
            if (beaconItem.type == fliterModel.getAdvDataType() && beaconItem.bytes.length == fliterModel.getAdvDataLength()) {
                return true;
            }
        }
        return false;
    }

    private boolean equalBeaconBytesLenghtWithCuurentFliterModel(SearchResult device, BleConnectFliterModel fliterModel) {
        Beacon beacon = new Beacon(device.scanRecord);
        BluetoothLog.d(String.format("beacon----------------------:\n%s\n", new Object[]{String.format("getName %s-----getAddress %s-----beacon %s----length %d", new Object[]{device.getName(), device.getAddress(), ByteUtils.byteToString(beacon.mBytes), Integer.valueOf(ByteUtils.byteToString(beacon.mBytes).length())})}));
        if (ByteUtils.byteToString(beacon.mBytes).length() == fliterModel.getBeaconBytesLenght()) {
            return true;
        }
        return false;
    }

    private boolean inBindedList(SearchResult device, List<BleDeviceModel> deviceList, boolean isBinding) {
        BleDeviceModel currentDeviceModel = new BleDeviceModel(device.getAddress(), device.getName());
        if (isBinding) {
            for (BleDeviceModel deviceModel : deviceList) {
                if (deviceModel.getDeviceMac().equals(currentDeviceModel.getDeviceMac())) {
                    BluetoothLog.v("deviceModel----" + deviceModel.getDeviceMac());
                    BluetoothLog.v("currentDeviceModel----" + currentDeviceModel.getDeviceMac());
                    return false;
                }
            }
            BluetoothLog.v("currentDeviceModeltrue----" + currentDeviceModel.getDeviceMac());
            return true;
        }
        for (BleDeviceModel deviceModel2 : deviceList) {
            if (deviceModel2.getDeviceMac().equals(currentDeviceModel.getDeviceMac())) {
                return true;
            }
        }
        return false;
    }
}
