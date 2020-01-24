package com.inuker.bluetooth.library.search;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class SearchTask implements Parcelable {
    public static final Creator<SearchTask> CREATOR = new Creator<SearchTask>() {
        public SearchTask createFromParcel(Parcel source) {
            return new SearchTask(source);
        }

        public SearchTask[] newArray(int size) {
            return new SearchTask[size];
        }
    };
    private int searchDuration;
    private int searchType;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.searchType);
        dest.writeInt(this.searchDuration);
    }

    public SearchTask() {
    }

    public int getSearchType() {
        return this.searchType;
    }

    public void setSearchType(int searchType2) {
        this.searchType = searchType2;
    }

    public int getSearchDuration() {
        return this.searchDuration;
    }

    public void setSearchDuration(int searchDuration2) {
        this.searchDuration = searchDuration2;
    }

    protected SearchTask(Parcel in) {
        this.searchType = in.readInt();
        this.searchDuration = in.readInt();
    }
}
