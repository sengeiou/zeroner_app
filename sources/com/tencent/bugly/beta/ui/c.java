package com.tencent.bugly.beta.ui;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources.NotFoundException;
import android.support.v4.app.NotificationCompat.Builder;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.download.BetaReceiver;
import com.tencent.bugly.beta.download.DownloadTask;
import com.tencent.bugly.beta.global.a;
import com.tencent.bugly.beta.global.e;
import com.tencent.bugly.proguard.an;
import com.tencent.bugly.proguard.y;
import com.tencent.open.SocialConstants;
import java.util.Locale;

/* compiled from: BUGLY */
public class c {
    public static c a = new c();
    public DownloadTask b;
    public String c = (this.h.getPackageName() + ".beta.DOWNLOAD_NOTIFY");
    public y d;
    public b e;
    private NotificationManager f = ((NotificationManager) this.h.getSystemService("notification"));
    private Notification g;
    private Context h = e.E.s;
    private boolean i = true;
    private long j;
    private Builder k;

    private c() {
        this.h.registerReceiver(new BetaReceiver(), new IntentFilter(this.c));
    }

    public void a(DownloadTask downloadTask) {
        long j2 = 0;
        this.b = downloadTask;
        this.j = this.b.getSavedLength();
        this.i = downloadTask.isNeededNotify();
        if (this.i && e.E.R) {
            this.f.cancel(1000);
            Intent intent = new Intent(this.c);
            intent.putExtra(SocialConstants.TYPE_REQUEST, 1);
            if (this.k == null) {
                this.k = new Builder(this.h);
            }
            Builder contentTitle = this.k.setTicker(Beta.strNotificationDownloading + e.E.y).setContentTitle(e.E.y);
            Locale locale = Locale.getDefault();
            String str = "%s %d%%";
            Object[] objArr = new Object[2];
            objArr[0] = Beta.strNotificationDownloading;
            if (this.b.getTotalLength() != 0) {
                j2 = (this.b.getSavedLength() * 100) / this.b.getTotalLength();
            }
            objArr[1] = Integer.valueOf((int) j2);
            contentTitle.setContentText(String.format(locale, str, objArr)).setContentIntent(PendingIntent.getBroadcast(this.h, 1, intent, 268435456)).setAutoCancel(false);
            if (e.E.f > 0) {
                this.k.setSmallIcon(e.E.f);
            } else if (!(e.E.z == null || e.E.z.applicationInfo == null)) {
                this.k.setSmallIcon(e.E.z.applicationInfo.icon);
            }
            try {
                if (e.E.g > 0 && this.h.getResources().getDrawable(e.E.g) != null) {
                    this.k.setLargeIcon(a.a(this.h.getResources().getDrawable(e.E.g)));
                }
            } catch (NotFoundException e2) {
                an.c(c.class, "[initNotify] " + e2.getMessage(), new Object[0]);
            }
            this.g = this.k.build();
            this.f.notify(1000, this.g);
        }
    }

    public void a() {
        long j2 = 0;
        if (this.i && this.b != null && e.E.R) {
            if (this.b.getSavedLength() - this.j > 307200 || this.b.getStatus() == 1 || this.b.getStatus() == 5 || this.b.getStatus() == 3) {
                this.j = this.b.getSavedLength();
                if (this.b.getStatus() == 1) {
                    this.k.setAutoCancel(true).setContentText(Beta.strNotificationClickToInstall).setContentTitle(String.format("%s %s", new Object[]{e.E.y, Beta.strNotificationDownloadSucc}));
                } else if (this.b.getStatus() == 5) {
                    this.k.setAutoCancel(false).setContentText(Beta.strNotificationClickToRetry).setContentTitle(String.format("%s %s", new Object[]{e.E.y, Beta.strNotificationDownloadError}));
                } else if (this.b.getStatus() == 2) {
                    Builder contentTitle = this.k.setContentTitle(e.E.y);
                    Locale locale = Locale.getDefault();
                    String str = "%s %d%%";
                    Object[] objArr = new Object[2];
                    objArr[0] = Beta.strNotificationDownloading;
                    if (this.b.getTotalLength() != 0) {
                        j2 = (this.b.getSavedLength() * 100) / this.b.getTotalLength();
                    }
                    objArr[1] = Integer.valueOf((int) j2);
                    contentTitle.setContentText(String.format(locale, str, objArr)).setAutoCancel(false);
                } else if (this.b.getStatus() == 3) {
                    Builder contentTitle2 = this.k.setContentTitle(e.E.y);
                    Locale locale2 = Locale.getDefault();
                    String str2 = "%s %d%%";
                    Object[] objArr2 = new Object[2];
                    objArr2[0] = Beta.strNotificationClickToContinue;
                    if (this.b.getTotalLength() != 0) {
                        j2 = (this.b.getSavedLength() * 100) / this.b.getTotalLength();
                    }
                    objArr2[1] = Integer.valueOf((int) j2);
                    contentTitle2.setContentText(String.format(locale2, str2, objArr2)).setAutoCancel(false);
                }
                this.g = this.k.build();
                this.f.notify(1000, this.g);
            }
        }
    }

    public synchronized void a(y yVar, b bVar) {
        this.d = yVar;
        this.e = bVar;
        this.f.cancel(1001);
        Intent intent = new Intent(this.c);
        intent.putExtra(SocialConstants.TYPE_REQUEST, 2);
        if (this.k == null) {
            this.k = new Builder(this.h);
        }
        this.k.setTicker(e.E.y + Beta.strNotificationHaveNewVersion).setContentTitle(String.format("%s %s", new Object[]{e.E.y, Beta.strNotificationHaveNewVersion})).setContentIntent(PendingIntent.getBroadcast(this.h, 2, intent, 268435456)).setAutoCancel(true).setContentText(String.format("%s.%s", new Object[]{yVar.e.d, Integer.valueOf(yVar.e.c)}));
        if (e.E.f > 0) {
            this.k.setSmallIcon(e.E.f);
        } else if (!(e.E.z == null || e.E.z.applicationInfo == null)) {
            this.k.setSmallIcon(e.E.z.applicationInfo.icon);
        }
        if (e.E.g > 0 && this.h.getResources().getDrawable(e.E.g) != null) {
            this.k.setLargeIcon(a.a(this.h.getResources().getDrawable(e.E.g)));
        }
        this.g = this.k.build();
        this.f.notify(1001, this.g);
    }
}
