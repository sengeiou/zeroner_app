package com.youzan.sdk.model.ump;

import com.tencent.open.SocialConstants;
import org.json.JSONException;
import org.json.JSONObject;

public class PromotionOrderModel {

    /* renamed from: ʻ reason: contains not printable characters */
    private String f280;

    /* renamed from: ˊ reason: contains not printable characters */
    private String f281;

    /* renamed from: ˋ reason: contains not printable characters */
    private int f282;

    /* renamed from: ˎ reason: contains not printable characters */
    private int f283;

    /* renamed from: ˏ reason: contains not printable characters */
    private String f284;

    /* renamed from: ᐝ reason: contains not printable characters */
    private String f285;

    public PromotionOrderModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f281 = o.optString("promotion_name");
            this.f282 = o.optInt("promotion_id");
            this.f283 = o.optInt("promotion_type_id");
            this.f284 = o.optString(SocialConstants.PARAM_APP_DESC);
            this.f285 = o.optString("start_date");
            this.f280 = o.optString("end_date");
        }
    }

    public String getPromotionName() {
        return this.f281;
    }

    public int getPromotionId() {
        return this.f282;
    }

    public int getPromotionTypeId() {
        return this.f283;
    }

    public String getDesc() {
        return this.f284;
    }

    public String getEndDate() {
        return this.f280;
    }

    public String getStartDate() {
        return this.f285;
    }
}
