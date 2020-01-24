package com.inuker.bluetooth.library.connect.options;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.tencent.bugly.BuglyStrategy.a;

public class BleConnectOptions implements Parcelable {
    public static final Creator<BleConnectOptions> CREATOR = new Creator<BleConnectOptions>() {
        public BleConnectOptions createFromParcel(Parcel in) {
            return new BleConnectOptions(in);
        }

        public BleConnectOptions[] newArray(int size) {
            return new BleConnectOptions[size];
        }
    };
    private int connectRetry;
    private int connectTimeout;
    private int serviceDiscoverRetry;
    private int serviceDiscoverTimeout;

    public static class Builder {
        private static final int DEFAULT_CONNECT_RETRY = 0;
        private static final int DEFAULT_CONNECT_TIMEOUT = 30000;
        private static final int DEFAULT_SERVICE_DISCOVER_RETRY = 0;
        private static final int DEFAULT_SERVICE_DISCOVER_TIMEOUT = 30000;
        /* access modifiers changed from: private */
        public int connectRetry = 0;
        /* access modifiers changed from: private */
        public int connectTimeout = a.MAX_USERDATA_VALUE_LENGTH;
        /* access modifiers changed from: private */
        public int serviceDiscoverRetry = 0;
        /* access modifiers changed from: private */
        public int serviceDiscoverTimeout = a.MAX_USERDATA_VALUE_LENGTH;

        public Builder setConnectRetry(int retry) {
            this.connectRetry = retry;
            return this;
        }

        public Builder setServiceDiscoverRetry(int retry) {
            this.serviceDiscoverRetry = retry;
            return this;
        }

        public Builder setConnectTimeout(int timeout) {
            this.connectTimeout = timeout;
            return this;
        }

        public Builder setServiceDiscoverTimeout(int timeout) {
            this.serviceDiscoverTimeout = timeout;
            return this;
        }

        public BleConnectOptions build() {
            return new BleConnectOptions(this);
        }
    }

    public BleConnectOptions(Builder builder) {
        this.connectRetry = builder.connectRetry;
        this.serviceDiscoverRetry = builder.serviceDiscoverRetry;
        this.connectTimeout = builder.connectTimeout;
        this.serviceDiscoverTimeout = builder.serviceDiscoverTimeout;
    }

    protected BleConnectOptions(Parcel in) {
        this.connectRetry = in.readInt();
        this.serviceDiscoverRetry = in.readInt();
        this.connectTimeout = in.readInt();
        this.serviceDiscoverTimeout = in.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.connectRetry);
        dest.writeInt(this.serviceDiscoverRetry);
        dest.writeInt(this.connectTimeout);
        dest.writeInt(this.serviceDiscoverTimeout);
    }

    public int getConnectRetry() {
        return this.connectRetry;
    }

    public void setConnectRetry(int connectRetry2) {
        this.connectRetry = connectRetry2;
    }

    public int getServiceDiscoverRetry() {
        return this.serviceDiscoverRetry;
    }

    public void setServiceDiscoverRetry(int serviceDiscoverRetry2) {
        this.serviceDiscoverRetry = serviceDiscoverRetry2;
    }

    public int getConnectTimeout() {
        return this.connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout2) {
        this.connectTimeout = connectTimeout2;
    }

    public int getServiceDiscoverTimeout() {
        return this.serviceDiscoverTimeout;
    }

    public void setServiceDiscoverTimeout(int serviceDiscoverTimeout2) {
        this.serviceDiscoverTimeout = serviceDiscoverTimeout2;
    }

    public String toString() {
        return "BleConnectOptions{connectRetry=" + this.connectRetry + ", serviceDiscoverRetry=" + this.serviceDiscoverRetry + ", connectTimeout=" + this.connectTimeout + ", serviceDiscoverTimeout=" + this.serviceDiscoverTimeout + '}';
    }
}
