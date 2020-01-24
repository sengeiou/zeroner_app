package me.yokeyword.fragmentation.helper.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public final class ResultRecord implements Parcelable {
    public static final Creator<ResultRecord> CREATOR = new Creator<ResultRecord>() {
        public ResultRecord createFromParcel(Parcel in) {
            return new ResultRecord(in);
        }

        public ResultRecord[] newArray(int size) {
            return new ResultRecord[size];
        }
    };
    public int requestCode;
    public Bundle resultBundle;
    public int resultCode = 0;

    public ResultRecord() {
    }

    protected ResultRecord(Parcel in) {
        this.requestCode = in.readInt();
        this.resultCode = in.readInt();
        this.resultBundle = in.readBundle(getClass().getClassLoader());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.requestCode);
        dest.writeInt(this.resultCode);
        dest.writeBundle(this.resultBundle);
    }
}
