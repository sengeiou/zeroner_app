package coms.mediatek.ctrl.notification;

import android.app.PendingIntent;
import java.io.Serializable;

public class NotificationActions implements Serializable {
    private String a;
    private PendingIntent b;
    private String c;

    public String getActionId() {
        return this.a;
    }

    public PendingIntent getActionIntent() {
        return this.b;
    }

    public String getActionTitle() {
        return this.c;
    }

    public void setActionId(String str) {
        this.a = str;
    }

    public void setActionIntent(PendingIntent pendingIntent) {
        this.b = pendingIntent;
    }

    public void setActionTitle(String str) {
        this.c = str;
    }

    public String toString() {
        return "NotificationActions [mActionId=" + this.a + ", mActionIntent=" + this.b + ", mActionTitle=" + this.c + ", toString()=" + super.toString() + "]";
    }
}
