package com.iwown.ble_module.iwown.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import org.apache.commons.cli.HelpFormatter;

public class WristBand implements Parcelable, Comparable<WristBand> {
    public static final Creator<WristBand> CREATOR = new Creator<WristBand>() {
        public WristBand createFromParcel(Parcel in) {
            return new WristBand(in);
        }

        public WristBand[] newArray(int size) {
            return new WristBand[size];
        }
    };
    private String address;
    private String alias;
    private String name;
    private String pair_code;
    private int rssi;
    private int sdkType = -1;

    public int getSdkType() {
        return this.sdkType;
    }

    public void setSdkType(int sdkType2) {
        this.sdkType = sdkType2;
    }

    public WristBand() {
    }

    public WristBand(String name2, String address2, int rssi2) {
        this.name = name2;
        this.address = address2;
        this.rssi = rssi2;
    }

    public WristBand(String name2, String address2) {
        this.name = name2;
        this.address = address2;
    }

    public int getRssi() {
        return this.rssi;
    }

    public void setRssi(int rssi2) {
        this.rssi = rssi2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address2) {
        this.address = address2;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias2) {
        this.alias = alias2;
    }

    public String getPairCode() {
        this.pair_code = this.name.substring(this.name.indexOf(HelpFormatter.DEFAULT_OPT_PREFIX) + 1);
        return this.pair_code;
    }

    public byte[] getPairCodeBytes() {
        byte[] pairs = new byte[4];
        for (int i = 6; i < 10; i++) {
            int c = this.name.charAt(i);
            Log.i("code" + (i + 1), "" + c);
            pairs[i - 6] = (byte) c;
        }
        return pairs;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((this.address == null ? 0 : this.address.hashCode()) + 31) * 31;
        if (this.name != null) {
            i = this.name.hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        WristBand other = (WristBand) obj;
        if (!TextUtils.equals(this.address, other.address) || !TextUtils.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.address);
        dest.writeString(this.pair_code);
    }

    public WristBand(Parcel in) {
        this.name = in.readString();
        this.address = in.readString();
        this.pair_code = in.readString();
    }

    public String toString() {
        return "WristBand{name='" + this.name + '\'' + ", alias='" + this.alias + '\'' + ", address='" + this.address + '\'' + ", pair_code='" + this.pair_code + '\'' + ", rssi=" + this.rssi + ", sdkType=" + this.sdkType + '}';
    }

    public int compareTo(@NonNull WristBand wristBand) {
        if (this.rssi == 0 || wristBand.rssi == 0) {
            this.rssi = Integer.MIN_VALUE;
            wristBand.rssi = Integer.MIN_VALUE;
        }
        if (this.rssi > wristBand.rssi) {
            return -1;
        }
        return 1;
    }
}
