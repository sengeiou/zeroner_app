package com.inuker.bluetooth.library.model;

import android.bluetooth.BluetoothGattCharacteristic;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BleGattService implements Parcelable, Comparable {
    public static final Creator<BleGattService> CREATOR = new Creator<BleGattService>() {
        public BleGattService createFromParcel(Parcel in) {
            return new BleGattService(in);
        }

        public BleGattService[] newArray(int size) {
            return new BleGattService[size];
        }
    };
    private List<BleGattCharacter> characters;
    private UUID uuid;

    public BleGattService(UUID uuid2, Map<UUID, BluetoothGattCharacteristic> characters2) {
        this.uuid = uuid2;
        for (BluetoothGattCharacteristic characteristic : characters2.values()) {
            getCharacters().add(new BleGattCharacter(characteristic));
        }
    }

    protected BleGattService(Parcel in) {
        this.uuid = (UUID) in.readSerializable();
        in.readTypedList(getCharacters(), BleGattCharacter.CREATOR);
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public List<BleGattCharacter> getCharacters() {
        if (this.characters == null) {
            this.characters = new ArrayList();
        }
        return this.characters;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.uuid);
        dest.writeTypedList(getCharacters());
    }

    public int describeContents() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Service: %s\n", new Object[]{this.uuid}));
        List<BleGattCharacter> characters2 = getCharacters();
        int size = characters2.size();
        for (int i = 0; i < size; i++) {
            sb.append(String.format(">>> Character: %s", new Object[]{characters2.get(i)}));
            if (i != size - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public int compareTo(Object another) {
        if (another == null) {
            return 1;
        }
        return this.uuid.compareTo(((BleGattService) another).uuid);
    }
}
