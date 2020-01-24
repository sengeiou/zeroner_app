package com.iwown.device_module.device_setting.wifi_scale.bean;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

@SuppressLint({"ParcelCreator"})
public class ScaleUserBean implements Parcelable {
    private String birthday;
    private int gender;
    private float height;
    private long uid;
    private String url;
    private String userName;

    public ScaleUserBean() {
    }

    public ScaleUserBean(String userName2, long uid2) {
        this.userName = userName2;
        this.uid = uid2;
    }

    public ScaleUserBean(String userName2) {
        this.userName = userName2;
    }

    public ScaleUserBean(String userName2, String url2) {
        this.userName = userName2;
        this.url = url2;
    }

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public float getHeight() {
        return this.height;
    }

    public void setHeight(float height2) {
        this.height = height2;
    }

    public int getGender() {
        return this.gender;
    }

    public void setGender(int gender2) {
        this.gender = gender2;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public void setBirthday(String birthday2) {
        this.birthday = birthday2;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName2) {
        this.userName = userName2;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url2) {
        this.url = url2;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
    }
}
