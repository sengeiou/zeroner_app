package com.youzan.sdk.model.ump;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PromotionModel {

    /* renamed from: ˊ reason: contains not printable characters */
    private PromotionItemModel f276;

    /* renamed from: ˋ reason: contains not printable characters */
    private List<PromotionOrderModel> f277;

    /* renamed from: ˎ reason: contains not printable characters */
    private PromotionPointsExchangeModel f278;

    /* renamed from: ˏ reason: contains not printable characters */
    private PromotionPackageBuyDetailModel f279;

    public PromotionModel(JSONObject o) throws JSONException {
        if (o != null) {
            JSONObject itemPromotionObj = o.optJSONObject("item_promotion");
            if (itemPromotionObj != null) {
                this.f276 = new PromotionItemModel(itemPromotionObj);
            }
            JSONArray orderPromotionsArray = o.optJSONArray("order_promotions");
            if (orderPromotionsArray != null && orderPromotionsArray.length() > 0) {
                this.f277 = new ArrayList(orderPromotionsArray.length());
                for (int i = 0; i < orderPromotionsArray.length(); i++) {
                    this.f277.add(new PromotionOrderModel(orderPromotionsArray.optJSONObject(i)));
                }
            }
            JSONObject goodsPointsObj = o.optJSONObject("goods_points");
            if (goodsPointsObj != null) {
                this.f278 = new PromotionPointsExchangeModel(goodsPointsObj);
            }
            JSONObject packageBuyObj = o.optJSONObject("package_buy");
            if (packageBuyObj != null) {
                this.f279 = new PromotionPackageBuyDetailModel(packageBuyObj);
            }
        }
    }

    public PromotionItemModel getItemPromotion() {
        return this.f276;
    }

    public List<PromotionOrderModel> getOrderPromotions() {
        return this.f277;
    }

    public PromotionPointsExchangeModel getGoodsPoints() {
        return this.f278;
    }

    public PromotionPackageBuyDetailModel getPackageBuy() {
        return this.f279;
    }
}
