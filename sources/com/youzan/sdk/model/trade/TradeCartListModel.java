package com.youzan.sdk.model.trade;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TradeCartListModel {

    /* renamed from: ˊ reason: contains not printable characters */
    private List<TradeCartShopModel> f243;

    /* renamed from: ˋ reason: contains not printable characters */
    private boolean f244;

    public TradeCartListModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f244 = o.optBoolean("is_success");
            JSONArray array = o.optJSONArray("data");
            if (array != null && array.length() > 0) {
                this.f243 = new ArrayList(array.length());
                for (int i = 0; i < array.length(); i++) {
                    this.f243.add(new TradeCartShopModel(array.optJSONObject(i)));
                }
            }
        }
    }

    public List<TradeCartShopModel> getData() {
        return this.f243;
    }

    public boolean isSuccess() {
        return this.f244;
    }
}
