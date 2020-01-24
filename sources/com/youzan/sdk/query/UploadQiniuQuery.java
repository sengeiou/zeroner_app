package com.youzan.sdk.query;

import android.support.annotation.NonNull;
import com.youzan.sdk.loader.http.Query;
import org.json.JSONObject;

public abstract class UploadQiniuQuery extends Query<String> {
    public int getAuthType() {
        return 0;
    }

    /* access modifiers changed from: protected */
    @NonNull
    public Class<String> getModel() {
        return String.class;
    }

    /* access modifiers changed from: protected */
    /* renamed from: ˊ reason: contains not printable characters */
    public String onParse(@NonNull JSONObject data) throws Exception {
        return data.optString("attachment_url");
    }

    @NonNull
    public String attachTo() {
        return "https://up.qbox.me/";
    }
}
