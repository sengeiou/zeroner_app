package com.dmcbig.mediapicker.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.alibaba.android.arouter.utils.Consts;

public class Media implements Parcelable {
    public static final Creator<Media> CREATOR = new Creator<Media>() {
        public Media createFromParcel(Parcel source) {
            return new Media(source);
        }

        public Media[] newArray(int size) {
            return new Media[size];
        }
    };
    public String extension;
    public int id;
    public int mediaType;
    public String name;
    public String parentDir;
    public String path;
    public long size;
    public long time;

    public Media(String path2, String name2, long time2, int mediaType2, long size2, int id2, String parentDir2) {
        this.path = path2;
        this.name = name2;
        if (TextUtils.isEmpty(name2) || name2.indexOf(Consts.DOT) == -1) {
            this.extension = "null";
        } else {
            this.extension = name2.substring(name2.lastIndexOf(Consts.DOT), name2.length());
        }
        this.time = time2;
        this.mediaType = mediaType2;
        this.size = size2;
        this.id = id2;
        this.parentDir = parentDir2;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.path);
        dest.writeString(this.name);
        dest.writeString(this.extension);
        dest.writeLong(this.time);
        dest.writeInt(this.mediaType);
        dest.writeLong(this.size);
        dest.writeInt(this.id);
        dest.writeString(this.parentDir);
    }

    protected Media(Parcel in) {
        this.path = in.readString();
        this.name = in.readString();
        this.extension = in.readString();
        this.time = in.readLong();
        this.mediaType = in.readInt();
        this.size = in.readLong();
        this.id = in.readInt();
        this.parentDir = in.readString();
    }
}
