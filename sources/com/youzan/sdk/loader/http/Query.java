package com.youzan.sdk.loader.http;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.youzan.sdk.loader.http.interfaces.HttpCall;
import com.youzan.sdk.loader.http.interfaces.NotImplementedException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.json.JSONObject;

public abstract class Query<MODEL> implements HttpCall {

    /* renamed from: ˏ reason: contains not printable characters */
    private static final int f19 = 200;

    /* renamed from: ˊ reason: contains not printable characters */
    Map<String, List<String>> f20;

    /* renamed from: ˋ reason: contains not printable characters */
    String f21;

    /* renamed from: ˎ reason: contains not printable characters */
    c f22;

    /* access modifiers changed from: protected */
    @NonNull
    public abstract String attachTo();

    /* access modifiers changed from: protected */
    public abstract int getAuthType();

    /* access modifiers changed from: protected */
    @NonNull
    public abstract Class<MODEL> getModel();

    /* access modifiers changed from: protected */
    public abstract void onFailure(@NonNull Exception exc);

    /* access modifiers changed from: protected */
    public abstract void onSuccess(@NonNull MODEL model);

    /* renamed from: ˋ reason: contains not printable characters */
    private JSONObject m18(@Nullable String raw) throws Exception {
        if (TextUtils.isEmpty(raw)) {
            throw new NullPointerException("Http response body is empty");
        }
        JSONObject obj = new JSONObject(raw);
        JSONObject error = obj.optJSONObject("error_response");
        JSONObject response = obj.optJSONObject("response");
        if (error != null) {
            int code = error.optInt("code", 0);
            if (!(code == 0 || code == 200)) {
                throw new IllegalStateException(String.format(Locale.CHINA, "%s(%d)", new Object[]{error.optString("msg"), Integer.valueOf(code)}));
            }
        } else if (response != null) {
            int code2 = response.optInt("code");
            boolean isSuccess = response.optBoolean("is_success", true);
            boolean success = response.optBoolean("success", true);
            String message = response.optString("message");
            if (!success || !isSuccess) {
                throw new IllegalStateException(String.format(Locale.CHINA, "%s(%d)", new Object[]{message, Integer.valueOf(code2)}));
            }
        } else {
            int code3 = obj.optInt("code");
            if (!(code3 == 0 || code3 == 200)) {
                throw new IllegalStateException(String.format(Locale.CHINA, "%s(%d)", new Object[]{obj.optString("msg"), Integer.valueOf(code3)}));
            }
        }
        return obj;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: ˊ reason: contains not printable characters */
    public MODEL m20(String body) throws Exception {
        return m17(m19(m18(body)));
    }

    /* access modifiers changed from: protected */
    public int getHTTPMethod() {
        return 1;
    }

    /* renamed from: ˊ reason: contains not printable characters */
    private MODEL m17(@NonNull JSONObject body) throws Exception {
        try {
            return onParse(body);
        } catch (NotImplementedException e) {
            return getModel().getConstructor(new Class[]{JSONObject.class}).newInstance(new Object[]{body});
        }
    }

    /* access modifiers changed from: protected */
    public MODEL onParse(@NonNull JSONObject data) throws Exception {
        throw new NotImplementedException();
    }

    /* renamed from: ˋ reason: contains not printable characters */
    private JSONObject m19(JSONObject body) {
        JSONObject response = body.optJSONObject("response");
        if (response != null) {
            JSONObject data = response.optJSONObject("data");
            if (data != null) {
                return data;
            }
            return response;
        }
        JSONObject data2 = body.optJSONObject("data");
        if (data2 != null) {
            body = data2;
        }
        return body;
    }

    /* access modifiers changed from: protected */
    public Map<String, List<String>> getResponseHeader() {
        return this.f20;
    }

    public void cancel() {
        if (this.f22 != null) {
            this.f22.cancel();
        }
    }
}
