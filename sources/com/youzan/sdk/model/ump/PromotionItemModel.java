package com.youzan.sdk.model.ump;

import com.tencent.open.SocialConstants;
import org.json.JSONException;
import org.json.JSONObject;

public class PromotionItemModel {

    /* renamed from: ʻ reason: contains not printable characters */
    private String f265;

    /* renamed from: ʼ reason: contains not printable characters */
    private int f266;

    /* renamed from: ʽ reason: contains not printable characters */
    private int f267;

    /* renamed from: ʾ reason: contains not printable characters */
    private String f268;

    /* renamed from: ˊ reason: contains not printable characters */
    private String f269;

    /* renamed from: ˋ reason: contains not printable characters */
    private String f270;

    /* renamed from: ˎ reason: contains not printable characters */
    private boolean f271;

    /* renamed from: ˏ reason: contains not printable characters */
    private String f272;

    /* renamed from: ͺ reason: contains not printable characters */
    private String f273;

    /* renamed from: ι reason: contains not printable characters */
    private String f274;

    /* renamed from: ᐝ reason: contains not printable characters */
    private String f275;

    public PromotionItemModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f269 = o.optString("end_date");
            this.f270 = o.optString("promotion_name");
            this.f271 = o.optBoolean("can_join_cart");
            this.f272 = o.optString("sku_id_list");
            this.f275 = o.optString("promotion_id");
            this.f265 = o.optString("sku_price_list");
            this.f266 = o.optInt("stock");
            this.f267 = o.optInt("promotion_type_id");
            this.f273 = o.optString(SocialConstants.PARAM_APP_DESC);
            this.f274 = o.optString("start_date");
            this.f268 = o.optString("promotion_alias");
        }
    }

    public String getEndDate() {
        return this.f269;
    }

    public String getPromotionName() {
        return this.f270;
    }

    public boolean isCanJoinCart() {
        return this.f271;
    }

    public String getSkuIdList() {
        return this.f272;
    }

    public String getPromotionId() {
        return this.f275;
    }

    public String getSkuPriceList() {
        return this.f265;
    }

    public int getStock() {
        return this.f266;
    }

    public int getPromotionTypeId() {
        return this.f267;
    }

    public String getDesc() {
        return this.f273;
    }

    public String getStartDate() {
        return this.f274;
    }

    public String getPromotionAlias() {
        return this.f268;
    }
}
