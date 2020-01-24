package com.youzan.sdk.model.trade;

import org.json.JSONException;
import org.json.JSONObject;

public class TradeCartAddResultModel {

    /* renamed from: ˊ reason: contains not printable characters */
    private boolean f205;

    public TradeCartAddResultModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f205 = o.optBoolean("is_success");
        }
    }

    public boolean isSuccess() {
        return this.f205;
    }
}
