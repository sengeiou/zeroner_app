package com.iwown.ble_module.zg_ble.bluetooth;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class BleDevice implements Parcelable {
    public static final Creator<BleDevice> CREATOR = new Creator<BleDevice>() {
        public BleDevice createFromParcel(Parcel source) {
            return new BleDevice(source);
        }

        public BleDevice[] newArray(int size) {
            return new BleDevice[size];
        }
    };
    private String address;
    private String name;

    public BleDevice(String name2, String address2) {
        this.address = address2;
        this.name = name2;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address2) {
        this.address = address2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.address);
        dest.writeString(this.name);
    }

    public BleDevice() {
    }

    protected BleDevice(Parcel in) {
        this.address = in.readString();
        this.name = in.readString();
    }
}
