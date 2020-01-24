package com.youzan.sdk.model.trade;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TradeCartFormatModel {

    /* renamed from: ʻ reason: contains not printable characters */
    private List<TradeCartGoodsModel> f206;

    /* renamed from: ˊ reason: contains not printable characters */
    private String f207;

    /* renamed from: ˋ reason: contains not printable characters */
    private String f208;

    /* renamed from: ˎ reason: contains not printable characters */
    private String f209;

    /* renamed from: ˏ reason: contains not printable characters */
    private String f210;

    /* renamed from: ᐝ reason: contains not printable characters */
    private List<TradeCartGoodsModel> f211;

    public TradeCartFormatModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f207 = o.optString("title");
            this.f208 = o.optString("kdt_id");
            this.f209 = o.optString("store_name");
            this.f210 = o.optString("shop_url");
            JSONArray array = o.optJSONArray("goods_list");
            if (array != null && array.length() > 0) {
                this.f211 = new ArrayList(array.length());
                for (int i = 0; i < array.length(); i++) {
                    this.f211.add(new TradeCartGoodsModel(array.optJSONObject(i)));
                }
            }
            JSONArray array2 = o.optJSONArray("unavailable_goods_list");
            if (array2 != null && array2.length() > 0) {
                this.f206 = new ArrayList(array2.length());
                for (int i2 = 0; i2 < array2.length(); i2++) {
                    this.f206.add(new TradeCartGoodsModel(array2.optJSONObject(i2)));
                }
            }
        }
    }

    public List<TradeCartGoodsModel> getGoodsList() {
        return this.f211;
    }

    public String getKdtId() {
        return this.f208;
    }

    public String getShopUrl() {
        return this.f210;
    }

    public String getStoreName() {
        return this.f209;
    }

    public String getTitle() {
        return this.f207;
    }

    public List<TradeCartGoodsModel> getUnavailableGoodsList() {
        return this.f206;
    }
}
