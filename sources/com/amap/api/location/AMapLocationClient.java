package com.amap.api.location;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.webkit.WebView;
import com.loc.ba;
import com.loc.d;
import com.loc.di;
import com.loc.dp;
import com.loc.p;
import com.loc.v;
import com.loc.w;

public class AMapLocationClient {
    Context a;
    LocationManagerBase b;

    public AMapLocationClient(Context context) {
        if (context == null) {
            try {
                throw new IllegalArgumentException("Context参数不能为null");
            } catch (Throwable th) {
                di.a(th, "AMapLocationClient", "AMapLocationClient 1");
            }
        } else {
            this.a = context.getApplicationContext();
            this.b = a(this.a, null);
        }
    }

    public AMapLocationClient(Context context, Intent intent) {
        if (context == null) {
            try {
                throw new IllegalArgumentException("Context参数不能为null");
            } catch (Throwable th) {
                di.a(th, "AMapLocationClient", "AMapLocationClient 2");
            }
        } else {
            this.a = context.getApplicationContext();
            this.b = a(this.a, intent);
        }
    }

    private static LocationManagerBase a(Context context, Intent intent) {
        LocationManagerBase dVar;
        try {
            v b2 = di.b();
            dp.a(context, b2);
            boolean c = dp.c(context);
            dp.a(context);
            if (c) {
                dVar = (LocationManagerBase) ba.a(context, b2, w.c("IY29tLmFtYXAuYXBpLmxvY2F0aW9uLkxvY2F0aW9uTWFuYWdlcldyYXBwZXI="), d.class, new Class[]{Context.class, Intent.class}, new Object[]{context, intent});
            } else {
                dVar = new d(context, intent);
            }
        } catch (Throwable th) {
            dVar = new d(context, intent);
        }
        return dVar == null ? new d(context, intent) : dVar;
    }

    public static String getDeviceId(Context context) {
        return p.w(context);
    }

    public static void setApiKey(String str) {
        try {
            AMapLocationClientOption.a = str;
        } catch (Throwable th) {
            di.a(th, "AMapLocationClient", "setApiKey");
        }
    }

    public void disableBackgroundLocation(boolean z) {
        try {
            if (this.b != null) {
                this.b.disableBackgroundLocation(z);
            }
        } catch (Throwable th) {
            di.a(th, "AMapLocationClient", "disableBackgroundLocation");
        }
    }

    public void enableBackgroundLocation(int i, Notification notification) {
        try {
            if (this.b != null) {
                this.b.enableBackgroundLocation(i, notification);
            }
        } catch (Throwable th) {
            di.a(th, "AMapLocationClient", "enableBackgroundLocation");
        }
    }

    public AMapLocation getLastKnownLocation() {
        try {
            if (this.b != null) {
                return this.b.getLastKnownLocation();
            }
        } catch (Throwable th) {
            di.a(th, "AMapLocationClient", "getLastKnownLocation");
        }
        return null;
    }

    public String getVersion() {
        return "4.7.0";
    }

    public boolean isStarted() {
        try {
            if (this.b != null) {
                return this.b.isStarted();
            }
        } catch (Throwable th) {
            di.a(th, "AMapLocationClient", "isStarted");
        }
        return false;
    }

    public void onDestroy() {
        try {
            if (this.b != null) {
                this.b.onDestroy();
            }
        } catch (Throwable th) {
            di.a(th, "AMapLocationClient", "onDestroy");
        }
    }

    public void setLocationListener(AMapLocationListener aMapLocationListener) {
        if (aMapLocationListener == null) {
            try {
                throw new IllegalArgumentException("listener参数不能为null");
            } catch (Throwable th) {
                di.a(th, "AMapLocationClient", "setLocationListener");
            }
        } else if (this.b != null) {
            this.b.setLocationListener(aMapLocationListener);
        }
    }

    public void setLocationOption(AMapLocationClientOption aMapLocationClientOption) {
        if (aMapLocationClientOption == null) {
            try {
                throw new IllegalArgumentException("LocationManagerOption参数不能为null");
            } catch (Throwable th) {
                di.a(th, "AMapLocationClient", "setLocationOption");
            }
        } else if (this.b != null) {
            this.b.setLocationOption(aMapLocationClientOption);
        }
    }

    public void startAssistantLocation() {
        try {
            if (this.b != null) {
                this.b.startAssistantLocation();
            }
        } catch (Throwable th) {
            di.a(th, "AMapLocationClient", "startAssistantLocation");
        }
    }

    public void startAssistantLocation(WebView webView) {
        try {
            if (this.b != null) {
                this.b.startAssistantLocation(webView);
            }
        } catch (Throwable th) {
            di.a(th, "AMapLocationClient", "startAssistantLocation1");
        }
    }

    public void startLocation() {
        try {
            if (this.b != null) {
                this.b.startLocation();
            }
        } catch (Throwable th) {
            di.a(th, "AMapLocationClient", "startLocation");
        }
    }

    public void stopAssistantLocation() {
        try {
            if (this.b != null) {
                this.b.stopAssistantLocation();
            }
        } catch (Throwable th) {
            di.a(th, "AMapLocationClient", "stopAssistantLocation");
        }
    }

    public void stopLocation() {
        try {
            if (this.b != null) {
                this.b.stopLocation();
            }
        } catch (Throwable th) {
            di.a(th, "AMapLocationClient", "stopLocation");
        }
    }

    public void unRegisterLocationListener(AMapLocationListener aMapLocationListener) {
        try {
            if (this.b != null) {
                this.b.unRegisterLocationListener(aMapLocationListener);
            }
        } catch (Throwable th) {
            di.a(th, "AMapLocationClient", "unRegisterLocationListener");
        }
    }
}
