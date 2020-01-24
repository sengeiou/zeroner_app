package com.inuker.bluetooth.library.model;

import android.bluetooth.BluetoothGattCharacteristic;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.inuker.bluetooth.library.utils.ListUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

public class BleGattProfile implements Parcelable {
    public static final Creator<BleGattProfile> CREATOR = new Creator<BleGattProfile>() {
        public BleGattProfile createFromParcel(Parcel in) {
            return new BleGattProfile(in);
        }

        public BleGattProfile[] newArray(int size) {
            return new BleGattProfile[size];
        }
    };
    private List<BleGattService> services;

    public BleGattProfile(Map<UUID, Map<UUID, BluetoothGattCharacteristic>> map) {
        List<BleGattService> serviceList = new ArrayList<>();
        for (Entry entry : map.entrySet()) {
            BleGattService service = new BleGattService((UUID) entry.getKey(), (Map) entry.getValue());
            if (!serviceList.contains(service)) {
                serviceList.add(service);
            }
        }
        addServices(serviceList);
    }

    public BleGattProfile(Parcel in) {
        in.readTypedList(getServices(), BleGattService.CREATOR);
    }

    public void addServices(List<BleGattService> services2) {
        Collections.sort(services2);
        getServices().addAll(services2);
    }

    public List<BleGattService> getServices() {
        if (this.services == null) {
            this.services = new ArrayList();
        }
        return this.services;
    }

    public BleGattService getService(UUID serviceId) {
        if (serviceId == null) {
            return null;
        }
        for (BleGattService service : getServices()) {
            if (service.getUUID().equals(serviceId)) {
                return service;
            }
        }
        return null;
    }

    public boolean containsCharacter(UUID serviceId, UUID characterId) {
        if (serviceId == null || characterId == null) {
            return false;
        }
        BleGattService service = getService(serviceId);
        if (service == null) {
            return false;
        }
        List<BleGattCharacter> characters = service.getCharacters();
        if (ListUtils.isEmpty(characters)) {
            return false;
        }
        for (BleGattCharacter character : characters) {
            if (characterId.equals(character.getUuid())) {
                return true;
            }
        }
        return false;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(getServices());
    }

    public int describeContents() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (BleGattService service : this.services) {
            sb.append(service).append("\n");
        }
        return sb.toString();
    }
}
