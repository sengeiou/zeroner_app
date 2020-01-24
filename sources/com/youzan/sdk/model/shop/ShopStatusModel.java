package com.youzan.sdk.model.shop;

import org.json.JSONException;
import org.json.JSONObject;

public class ShopStatusModel {

    /* renamed from: ʻ reason: contains not printable characters */
    private boolean f180;

    /* renamed from: ʼ reason: contains not printable characters */
    private boolean f181;

    /* renamed from: ʽ reason: contains not printable characters */
    private boolean f182;

    /* renamed from: ˊ reason: contains not printable characters */
    private boolean f183;

    /* renamed from: ˋ reason: contains not printable characters */
    private boolean f184;

    /* renamed from: ˎ reason: contains not printable characters */
    private boolean f185;

    /* renamed from: ˏ reason: contains not printable characters */
    private boolean f186;

    /* renamed from: ͺ reason: contains not printable characters */
    private boolean f187;

    /* renamed from: ι reason: contains not printable characters */
    private boolean f188;

    /* renamed from: ᐝ reason: contains not printable characters */
    private boolean f189;

    public ShopStatusModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f183 = o.optBoolean("is_bind_weixin");
            this.f184 = o.optBoolean("is_weixin_service");
            this.f185 = o.optBoolean("is_weixin_unauthorized_service");
            this.f186 = o.optBoolean("is_weixin_publisher");
            this.f189 = o.optBoolean("is_weixin_unauthorized_publisher");
            this.f180 = o.optBoolean("is_secured_transactions");
            this.f181 = o.optBoolean("is_set_shopping_cart");
            this.f182 = o.optBoolean("is_set_buy_record");
            this.f187 = o.optBoolean("is_set_customer_reviews");
            this.f188 = o.optBoolean("is_set_fans_only");
        }
    }

    public boolean isBindWeixin() {
        return this.f183;
    }

    public boolean isSecuredTransactions() {
        return this.f180;
    }

    public boolean isSetBuyRecord() {
        return this.f182;
    }

    public boolean isSetCustomerReviews() {
        return this.f187;
    }

    public boolean isSetFansOnly() {
        return this.f188;
    }

    public boolean isSetShoppingCart() {
        return this.f181;
    }

    public boolean isWeixinPublisher() {
        return this.f186;
    }

    public boolean isWeixinService() {
        return this.f184;
    }

    public boolean isWeixinUnauthorizedPublisher() {
        return this.f189;
    }

    public boolean isWeixinUnauthorizedService() {
        return this.f185;
    }
}
