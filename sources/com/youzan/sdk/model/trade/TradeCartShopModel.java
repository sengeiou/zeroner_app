package com.youzan.sdk.model.trade;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TradeCartShopModel {

    /* renamed from: ʻ reason: contains not printable characters */
    private List<TradeCartGoodsModel> f257;

    /* renamed from: ʼ reason: contains not printable characters */
    private long f258;

    /* renamed from: ʽ reason: contains not printable characters */
    private List<TradeCartPayWayModel> f259;

    /* renamed from: ˊ reason: contains not printable characters */
    private String f260;

    /* renamed from: ˋ reason: contains not printable characters */
    private long f261;

    /* renamed from: ˎ reason: contains not printable characters */
    private String f262;

    /* renamed from: ˏ reason: contains not printable characters */
    private String f263;

    /* renamed from: ᐝ reason: contains not printable characters */
    private List<TradeCartGoodsModel> f264;

    public TradeCartShopModel(JSONObject o) throws JSONException {
        this.f260 = o.optString("title");
        this.f261 = o.optLong("kdt_id");
        this.f262 = o.optString("store_name");
        this.f263 = o.optString("shop_url");
        this.f258 = o.optLong("latest_addcart_timestamp");
        JSONArray goodsArray = o.optJSONArray("goods_list");
        if (goodsArray != null && goodsArray.length() > 0) {
            this.f264 = new ArrayList(goodsArray.length());
            for (int i = 0; i < goodsArray.length(); i++) {
                this.f264.add(new TradeCartGoodsModel(goodsArray.optJSONObject(i)));
            }
        }
        JSONArray unavailableArray = o.optJSONArray("unavailable_goods_list");
        if (unavailableArray != null && unavailableArray.length() > 0) {
            this.f257 = new ArrayList(unavailableArray.length());
            for (int i2 = 0; i2 < unavailableArray.length(); i2++) {
                this.f257.add(new TradeCartGoodsModel(unavailableArray.optJSONObject(i2)));
            }
        }
        JSONArray paysArray = o.optJSONArray("pay_ways");
        if (paysArray != null && paysArray.length() > 0) {
            this.f259 = new ArrayList(paysArray.length());
            for (int i3 = 0; i3 < paysArray.length(); i3++) {
                this.f259.add(new TradeCartPayWayModel(paysArray.optJSONObject(i3)));
            }
        }
    }

    public String getTitle() {
        return this.f260;
    }

    public long getKdtId() {
        return this.f261;
    }

    public String getStoreName() {
        return this.f262;
    }

    public String getShopUrl() {
        return this.f263;
    }

    public List<TradeCartGoodsModel> getGoodsList() {
        return this.f264;
    }

    public List<TradeCartGoodsModel> getUnavailableGoodsList() {
        return this.f257;
    }

    public long getLatestAddCartTimestamp() {
        return this.f258;
    }

    public List<TradeCartPayWayModel> getPayWays() {
        return this.f259;
    }
}
