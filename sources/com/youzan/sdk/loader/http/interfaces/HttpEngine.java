package com.youzan.sdk.loader.http.interfaces;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.youzan.sdk.loader.http.Query;
import java.io.File;
import java.util.List;
import java.util.Map;

public interface HttpEngine {
    void cancel();

    <MODEL> void request(@Nullable Context context, int i, @NonNull String str, @NonNull Map<String, String> map, @Nullable Map<String, File> map2, @NonNull Map<String, String> map3, @Nullable Class<MODEL> cls, @NonNull Query<MODEL> query, boolean z);

    <MODEL> void response(@Nullable String str, @Nullable Map<String, List<String>> map, @Nullable Exception exc, @NonNull Query<MODEL> query, @Nullable Context context, @Nullable Class<MODEL> cls);
}
