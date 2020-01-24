package com.dmcbig.mediapicker.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.ArrayList;

public class Folder implements Parcelable {
    public static final Creator<Folder> CREATOR = new Creator<Folder>() {
        public Folder createFromParcel(Parcel source) {
            return new Folder(source);
        }

        public Folder[] newArray(int size) {
            return new Folder[size];
        }
    };
    public int count;
    ArrayList<Media> medias = new ArrayList<>();
    public String name;

    public void addMedias(Media media) {
        this.medias.add(media);
    }

    public Folder(String name2) {
        this.name = name2;
    }

    public ArrayList<Media> getMedias() {
        return this.medias;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.count);
        dest.writeTypedList(this.medias);
    }

    protected Folder(Parcel in) {
        this.name = in.readString();
        this.count = in.readInt();
        this.medias = in.createTypedArrayList(Media.CREATOR);
    }
}
