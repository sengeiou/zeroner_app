package com.youzan.sdk.model.goods;

import org.json.JSONException;
import org.json.JSONObject;

public class GoodsSkuItemModel {

    /* renamed from: ˊ reason: contains not printable characters */
    public int f117;

    /* renamed from: ˋ reason: contains not printable characters */
    public int f118;

    /* renamed from: ˎ reason: contains not printable characters */
    public String f119;

    /* renamed from: ˏ reason: contains not printable characters */
    public String f120;

    public GoodsSkuItemModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f117 = o.optInt("kid", 0);
            this.f118 = o.optInt("vid", 0);
            this.f119 = o.optString("k");
            this.f120 = o.optString("v");
        }
    }

    public String getkDesc() {
        return this.f119;
    }

    public int getKid() {
        return this.f117;
    }

    public String getvDesc() {
        return this.f120;
    }

    public int getVid() {
        return this.f118;
    }
}
