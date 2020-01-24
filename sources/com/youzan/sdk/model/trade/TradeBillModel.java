package com.youzan.sdk.model.trade;

import org.json.JSONException;
import org.json.JSONObject;

public class TradeBillModel {

    /* renamed from: ˊ reason: contains not printable characters */
    private String f190;

    /* renamed from: ˋ reason: contains not printable characters */
    private String f191;

    public TradeBillModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f190 = o.optString("book_key");
            this.f191 = o.optString("url");
        }
    }

    public String getBookKey() {
        return this.f190;
    }

    public String getUrl() {
        return this.f191;
    }
}
