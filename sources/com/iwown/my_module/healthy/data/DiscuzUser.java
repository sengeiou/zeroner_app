package com.iwown.my_module.healthy.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class DiscuzUser implements Parcelable {
    public static final Creator<DiscuzUser> CREATOR = new Creator<DiscuzUser>() {
        public DiscuzUser createFromParcel(Parcel source) {
            return new DiscuzUser(source);
        }

        public DiscuzUser[] newArray(int size) {
            return new DiscuzUser[size];
        }
    };
    private String email;
    private String password;
    private String userName;

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName2) {
        this.userName = userName2;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password2) {
        this.password = password2;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email2) {
        this.email = email2;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userName);
        dest.writeString(this.password);
        dest.writeString(this.email);
    }

    public DiscuzUser() {
    }

    protected DiscuzUser(Parcel in) {
        this.userName = in.readString();
        this.password = in.readString();
        this.email = in.readString();
    }
}
