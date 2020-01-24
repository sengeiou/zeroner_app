package com.iwown.device_module.common.network.data.resp;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class FwUpdateReturnDetail implements Parcelable {
    public static final Creator<FwUpdateReturnDetail> CREATOR = new Creator<FwUpdateReturnDetail>() {
        public FwUpdateReturnDetail createFromParcel(Parcel in) {
            return new FwUpdateReturnDetail(in);
        }

        public FwUpdateReturnDetail[] newArray(int size) {
            return new FwUpdateReturnDetail[size];
        }
    };
    private int app;
    private int app_platform;
    private int app_version;
    private int device_model;
    private int device_platform;
    private int device_type;
    private String download_link;
    private String fw_version;
    private int module;
    private int open_for_upgrade;
    private String update_information;

    public FwUpdateReturnDetail(int open_for_upgrade2) {
        this.open_for_upgrade = open_for_upgrade2;
    }

    public FwUpdateReturnDetail() {
    }

    protected FwUpdateReturnDetail(Parcel in) {
        this.app = in.readInt();
        this.app_platform = in.readInt();
        this.app_version = in.readInt();
        this.device_model = in.readInt();
        this.device_platform = in.readInt();
        this.device_type = in.readInt();
        this.download_link = in.readString();
        this.fw_version = in.readString();
        this.module = in.readInt();
        this.update_information = in.readString();
    }

    public String getUpdate_information() {
        return this.update_information;
    }

    public void setUpdate_information(String update_information2) {
        this.update_information = update_information2;
    }

    public int getOpen_for_upgrade() {
        return this.open_for_upgrade;
    }

    public void setOpen_for_upgrade(int open_for_upgrade2) {
        this.open_for_upgrade = open_for_upgrade2;
    }

    public int getApp() {
        return this.app;
    }

    public void setApp(int app2) {
        this.app = app2;
    }

    public int getApp_platform() {
        return this.app_platform;
    }

    public void setApp_platform(int app_platform2) {
        this.app_platform = app_platform2;
    }

    public int getApp_version() {
        return this.app_version;
    }

    public void setApp_version(int app_version2) {
        this.app_version = app_version2;
    }

    public int getDevice_model() {
        return this.device_model;
    }

    public void setDevice_model(int device_model2) {
        this.device_model = device_model2;
    }

    public int getDevice_platform() {
        return this.device_platform;
    }

    public void setDevice_platform(int device_platform2) {
        this.device_platform = device_platform2;
    }

    public int getDevice_type() {
        return this.device_type;
    }

    public void setDevice_type(int device_type2) {
        this.device_type = device_type2;
    }

    public String getDownload_link() {
        return this.download_link;
    }

    public void setDownload_link(String download_link2) {
        this.download_link = download_link2;
    }

    public String getFw_version() {
        return this.fw_version;
    }

    public void setFw_version(String fw_version2) {
        this.fw_version = fw_version2;
    }

    public int getModule() {
        return this.module;
    }

    public void setModule(int module2) {
        this.module = module2;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.app);
        dest.writeInt(this.app_platform);
        dest.writeInt(this.app_version);
        dest.writeInt(this.device_model);
        dest.writeInt(this.device_platform);
        dest.writeInt(this.device_type);
        dest.writeString(this.download_link);
        dest.writeString(this.fw_version);
        dest.writeInt(this.module);
        dest.writeString(this.update_information);
    }
}
