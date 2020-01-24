package com.youzan.sdk;

import com.tencent.connect.common.Constants;
import org.json.JSONException;
import org.json.JSONObject;

public class YouzanToken {

    /* renamed from: ˊ reason: contains not printable characters */
    private String f0;

    /* renamed from: ˋ reason: contains not printable characters */
    private String f1;

    /* renamed from: ˎ reason: contains not printable characters */
    private String f2;

    public YouzanToken(JSONObject o) throws JSONException {
        JSONObject data;
        if (o != null) {
            if (o.has("data")) {
                data = o.optJSONObject("data");
            } else {
                data = o;
            }
            this.f0 = data.optString(Constants.PARAM_ACCESS_TOKEN);
            this.f1 = data.optString("cookie_key");
            this.f2 = data.optString("cookie_value");
        }
    }

    public YouzanToken() {
    }

    public String getAccessToken() {
        return this.f0;
    }

    public void setAccessToken(String token) {
        this.f0 = token;
    }

    public String getCookieKey() {
        return this.f1;
    }

    public void setCookieKey(String cookieKey) {
        this.f1 = cookieKey;
    }

    public String getCookieValue() {
        return this.f2;
    }

    public void setCookieValue(String cookieValue) {
        this.f2 = cookieValue;
    }
}
