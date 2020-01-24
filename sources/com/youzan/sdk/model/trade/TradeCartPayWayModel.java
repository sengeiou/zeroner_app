package com.youzan.sdk.model.trade;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.util.Const.TableSchema;

public class TradeCartPayWayModel {

    /* renamed from: ˊ reason: contains not printable characters */
    private String f254;

    /* renamed from: ˋ reason: contains not printable characters */
    private String f255;

    /* renamed from: ˎ reason: contains not printable characters */
    private int f256;

    public TradeCartPayWayModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f254 = o.optString("code");
            this.f255 = o.optString(TableSchema.COLUMN_NAME);
            this.f256 = o.optInt("key");
        }
    }

    public String getCode() {
        return this.f254;
    }

    public String getName() {
        return this.f255;
    }

    public int getKey() {
        return this.f256;
    }
}
