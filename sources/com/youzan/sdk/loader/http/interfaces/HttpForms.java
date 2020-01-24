package com.youzan.sdk.loader.http.interfaces;

import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import com.youzan.sdk.loader.http.Query;
import java.io.File;
import java.util.Map;

@Keep
public interface HttpForms {
    HttpForms put(String str, double d);

    HttpForms put(String str, double d, boolean z);

    HttpForms put(String str, float f);

    HttpForms put(String str, float f, boolean z);

    HttpForms put(String str, int i);

    HttpForms put(String str, int i, boolean z);

    HttpForms put(String str, long j);

    HttpForms put(String str, long j, boolean z);

    HttpForms put(String str, File file);

    HttpForms put(String str, File file, boolean z);

    HttpForms put(String str, String str2);

    HttpForms put(String str, String str2, boolean z);

    HttpForms put(String str, short s);

    HttpForms put(String str, short s, boolean z);

    HttpForms put(String str, boolean z);

    HttpForms put(String str, boolean z, boolean z2);

    HttpForms puts(Map<String, String> map);

    <MODEL> HttpCall with(@NonNull Query<MODEL> query) throws NullPointerException;
}
