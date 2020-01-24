package com.youzan.sdk.model.ump;

import org.json.JSONException;
import org.json.JSONObject;

public class PromotionPointsExchangeModel {

    /* renamed from: ʻ reason: contains not printable characters */
    private int f297;

    /* renamed from: ʼ reason: contains not printable characters */
    private String f298;

    /* renamed from: ˊ reason: contains not printable characters */
    private String f299;

    /* renamed from: ˋ reason: contains not printable characters */
    private String f300;

    /* renamed from: ˎ reason: contains not printable characters */
    private String f301;

    /* renamed from: ˏ reason: contains not printable characters */
    private int f302;

    /* renamed from: ᐝ reason: contains not printable characters */
    private int f303;

    public PromotionPointsExchangeModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f299 = o.optString("exchange_price");
            this.f300 = o.optString("end_date");
            this.f301 = o.optString("promotion_name");
            this.f302 = o.optInt("promotion_id");
            this.f303 = o.optInt("promotion_type_id");
            this.f297 = o.optInt("exchange_points");
            this.f298 = o.optString("start_date");
        }
    }

    public String getExchangePrice() {
        return this.f299;
    }

    public String getEndDate() {
        return this.f300;
    }

    public String getPromotionName() {
        return this.f301;
    }

    public int getPromotionId() {
        return this.f302;
    }

    public int getPromotionTypeId() {
        return this.f303;
    }

    public int getExchangePoints() {
        return this.f297;
    }

    public String getStartDate() {
        return this.f298;
    }
}
