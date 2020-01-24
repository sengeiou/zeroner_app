package com.youzan.sdk.query;

import android.support.annotation.NonNull;
import com.youzan.sdk.loader.http.b;
import com.youzan.sdk.loader.http.interfaces.NotImplementedException;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class UploadTokenQuery extends b<String> {
    /* access modifiers changed from: protected */
    /* renamed from: ˊ reason: contains not printable characters */
    public String onParse(@NonNull JSONObject data) throws NotImplementedException, JSONException {
        return data.optString("upload_token");
    }

    /* access modifiers changed from: protected */
    @NonNull
    public Class<String> getModel() {
        return String.class;
    }

    /* access modifiers changed from: protected */
    @NonNull
    public String attachTo() {
        return "appsdk.kdt.picture.uploadtoken.get";
    }
}
