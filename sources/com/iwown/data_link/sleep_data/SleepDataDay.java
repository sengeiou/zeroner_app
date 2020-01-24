package com.iwown.data_link.sleep_data;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.iwown.data_link.base.BaseDataShow;

public class SleepDataDay extends BaseDataShow implements Parcelable {
    public static final Creator<SleepDataDay> CREATOR = new Creator<SleepDataDay>() {
        public SleepDataDay createFromParcel(Parcel in) {
            return new SleepDataDay(in);
        }

        public SleepDataDay[] newArray(int size) {
            return new SleepDataDay[size];
        }
    };
    public String data_from;
    public String date;
    public int db_id = 0;
    public int score;
    public int size;
    public SleepDownData1 sleepDownData1;
    public long time_unix;
    public long uid;

    public SleepDataDay() {
    }

    protected SleepDataDay(Parcel in) {
        this.date = in.readString();
        this.time_unix = in.readLong();
        this.data_from = in.readString();
        this.uid = in.readLong();
        this.size = in.readInt();
        this.score = in.readInt();
        this.db_id = in.readInt();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.date);
        dest.writeLong(this.time_unix);
        dest.writeString(this.data_from);
        dest.writeLong(this.uid);
        dest.writeInt(this.size);
        dest.writeInt(this.score);
        dest.writeInt(this.db_id);
    }

    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "SleepDataDay{date='" + this.date + '\'' + ", time_unix=" + this.time_unix + ", data_from='" + this.data_from + '\'' + ", sleepDownData1=" + this.sleepDownData1 + ", uid=" + this.uid + '}';
    }
}
