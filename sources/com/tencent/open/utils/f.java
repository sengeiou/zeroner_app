package com.tencent.open.utils;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.SystemClock;
import com.alibaba.android.arouter.utils.Consts;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.tencent.connect.common.Constants;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class f {
    private static Map<String, f> a = Collections.synchronizedMap(new HashMap());
    private static String b = null;
    /* access modifiers changed from: private */
    public Context c = null;
    private String d = null;
    private JSONObject e = null;
    private long f = 0;
    /* access modifiers changed from: private */
    public int g = 0;
    private boolean h = true;

    public static f a(Context context, String str) {
        f fVar;
        synchronized (a) {
            com.tencent.open.a.f.a("openSDK_LOG.OpenConfig", "getInstance begin");
            if (str != null) {
                b = str;
            }
            if (str == null) {
                if (b != null) {
                    str = b;
                } else {
                    str = "0";
                }
            }
            fVar = (f) a.get(str);
            if (fVar == null) {
                fVar = new f(context, str);
                a.put(str, fVar);
            }
            com.tencent.open.a.f.a("openSDK_LOG.OpenConfig", "getInstance end");
        }
        return fVar;
    }

    private f(Context context, String str) {
        this.c = context.getApplicationContext();
        this.d = str;
        a();
        b();
    }

    private void a() {
        try {
            this.e = new JSONObject(c("com.tencent.open.config.json"));
        } catch (JSONException e2) {
            this.e = new JSONObject();
        }
    }

    private String c(String str) {
        InputStream open;
        String str2;
        String str3 = "";
        try {
            if (this.d != null) {
                str2 = str + Consts.DOT + this.d;
            } else {
                str2 = str;
            }
            open = this.c.openFileInput(str2);
        } catch (FileNotFoundException e2) {
            try {
                open = this.c.getAssets().open(str);
            } catch (IOException e3) {
                ThrowableExtension.printStackTrace(e3);
                return str3;
            }
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(open, Charset.forName("UTF-8")));
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
            try {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    stringBuffer.append(readLine);
                } else {
                    String stringBuffer2 = stringBuffer.toString();
                    try {
                        return stringBuffer2;
                    } catch (IOException e4) {
                        ThrowableExtension.printStackTrace(e4);
                        return stringBuffer2;
                    }
                }
            } catch (IOException e5) {
                ThrowableExtension.printStackTrace(e5);
                try {
                    return str3;
                } catch (IOException e6) {
                    ThrowableExtension.printStackTrace(e6);
                    return str3;
                }
            } finally {
                try {
                    open.close();
                    bufferedReader.close();
                } catch (IOException e7) {
                    ThrowableExtension.printStackTrace(e7);
                }
            }
        }
    }

    private void a(String str, String str2) {
        try {
            if (this.d != null) {
                str = str + Consts.DOT + this.d;
            }
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.c.openFileOutput(str, 0), Charset.forName("UTF-8"));
            outputStreamWriter.write(str2);
            outputStreamWriter.flush();
            outputStreamWriter.close();
        } catch (IOException e2) {
            ThrowableExtension.printStackTrace(e2);
        }
    }

    private void b() {
        if (this.g != 0) {
            d("update thread is running, return");
            return;
        }
        this.g = 1;
        final Bundle bundle = new Bundle();
        bundle.putString("appid", this.d);
        bundle.putString("appid_for_getting_config", this.d);
        bundle.putString("status_os", VERSION.RELEASE);
        bundle.putString("status_machine", Build.MODEL);
        bundle.putString("status_version", VERSION.SDK);
        bundle.putString("sdkv", Constants.SDK_VERSION);
        bundle.putString("sdkp", "a");
        new Thread() {
            public void run() {
                try {
                    f.this.a(j.d(HttpUtils.openUrl2(f.this.c, "http://cgi.connect.qq.com/qqconnectopen/openapi/policy_conf", "GET", bundle).a));
                } catch (Exception e) {
                    ThrowableExtension.printStackTrace(e);
                }
                f.this.g = 0;
            }
        }.start();
    }

    /* access modifiers changed from: private */
    public void a(JSONObject jSONObject) {
        d("cgi back, do update");
        this.e = jSONObject;
        a("com.tencent.open.config.json", jSONObject.toString());
        this.f = SystemClock.elapsedRealtime();
    }

    private void c() {
        int optInt = this.e.optInt("Common_frequency");
        if (optInt == 0) {
            optInt = 1;
        }
        if (SystemClock.elapsedRealtime() - this.f >= ((long) (optInt * 3600000))) {
            b();
        }
    }

    public int a(String str) {
        d("get " + str);
        c();
        return this.e.optInt(str);
    }

    public boolean b(String str) {
        d("get " + str);
        c();
        Object opt = this.e.opt(str);
        if (opt == null) {
            return false;
        }
        if (opt instanceof Integer) {
            return !opt.equals(Integer.valueOf(0));
        } else if (opt instanceof Boolean) {
            return ((Boolean) opt).booleanValue();
        } else {
            return false;
        }
    }

    private void d(String str) {
        if (this.h) {
            com.tencent.open.a.f.a("openSDK_LOG.OpenConfig", str + "; appid: " + this.d);
        }
    }
}
