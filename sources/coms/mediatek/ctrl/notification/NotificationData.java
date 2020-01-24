package coms.mediatek.ctrl.notification;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class NotificationData implements Serializable {
    private String[] a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private long g;
    private int h;
    private ArrayList<NotificationActions> i;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        NotificationData notificationData = (NotificationData) obj;
        if (this.g != notificationData.g) {
            return false;
        }
        if (this.h != notificationData.h) {
            return false;
        }
        return this.b == null ? notificationData.b == null : this.b.equals(notificationData.b);
    }

    public ArrayList<NotificationActions> getActionsList() {
        return this.i;
    }

    public String getAppID() {
        return this.e;
    }

    public String getGroupKey() {
        return this.d;
    }

    public int getMsgId() {
        return this.h;
    }

    public String getPackageName() {
        return this.b;
    }

    public String getTag() {
        return this.f;
    }

    public String[] getTextList() {
        return this.a;
    }

    public String getTickerText() {
        return this.c;
    }

    public long getWhen() {
        return this.g;
    }

    public int hashCode() {
        int i2 = 0;
        int hashCode = ((((this.b == null ? 0 : this.b.hashCode()) + (((this.d == null ? 0 : this.d.hashCode()) + (((this.i == null ? 0 : this.i.hashCode()) + 31) * 31)) * 31)) * 31) + Arrays.hashCode(this.a)) * 31;
        if (this.c != null) {
            i2 = this.c.hashCode();
        }
        return ((hashCode + i2) * 31) + ((int) (this.g ^ (this.g >>> 32)));
    }

    public void setActionsList(ArrayList<NotificationActions> arrayList) {
        this.i = arrayList;
    }

    public void setAppID(String str) {
        this.e = str;
    }

    public void setGroupKey(String str) {
        this.d = str;
    }

    public void setMsgId(int i2) {
        this.h = i2;
    }

    public void setPackageName(String str) {
        this.b = str;
    }

    public void setTag(String str) {
        this.f = str;
    }

    public void setTextList(String[] strArr) {
        this.a = strArr;
    }

    public void setTickerText(String str) {
        this.c = str;
    }

    public void setWhen(long j) {
        this.g = j;
    }

    public String toString() {
        return "NotificationData [mTextList=" + Arrays.toString(this.a) + ", mPackageName=" + this.b + ", mTickerText=" + this.c + ", mGroupKey=" + this.d + ", mAppID=" + this.e + ", mTag=" + this.f + ", mWhen=" + this.g + ", mMsgId=" + this.h + ", mActionsList=" + this.i + "]";
    }
}
