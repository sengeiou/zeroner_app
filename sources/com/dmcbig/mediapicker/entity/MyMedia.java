package com.dmcbig.mediapicker.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class MyMedia implements Parcelable {
    public String extension;
    public int height;
    public int id;
    public int mediaType;
    public String name;
    public String parentDir;
    public String path;
    public long size;
    public long time;
    public int width;

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width2) {
        this.width = width2;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height2) {
        this.height = height2;
    }

    public void setMedia(Media media) {
        this.path = media.path;
        this.name = media.name;
        this.extension = media.extension;
        this.time = media.time;
        this.mediaType = media.mediaType;
        this.size = media.size;
        this.id = media.id;
        this.parentDir = media.parentDir;
    }

    public void setMedia(Media media, int width2, int height2) {
        this.path = media.path;
        this.name = media.name;
        this.extension = media.extension;
        this.time = media.time;
        this.mediaType = media.mediaType;
        this.size = media.size;
        this.id = media.id;
        this.parentDir = media.parentDir;
        this.width = width2;
        this.height = height2;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(this.path);
        dest.writeString(this.name);
        dest.writeString(this.extension);
        dest.writeLong(this.time);
        dest.writeInt(this.mediaType);
        dest.writeLong(this.size);
        dest.writeInt(this.id);
        dest.writeString(this.parentDir);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
    }
}
