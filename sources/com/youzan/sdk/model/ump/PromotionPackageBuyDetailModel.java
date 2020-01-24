package com.youzan.sdk.model.ump;

import com.tencent.open.SocialConstants;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PromotionPackageBuyDetailModel {

    /* renamed from: ʻ reason: contains not printable characters */
    private String f286;

    /* renamed from: ʼ reason: contains not printable characters */
    private String f287;

    /* renamed from: ˊ reason: contains not printable characters */
    private String f288;

    /* renamed from: ˋ reason: contains not printable characters */
    private String f289;

    /* renamed from: ˎ reason: contains not printable characters */
    private List<PromotionPackageBuyGoodsModel> f290;

    /* renamed from: ˏ reason: contains not printable characters */
    private int f291;

    /* renamed from: ᐝ reason: contains not printable characters */
    private int f292;

    public PromotionPackageBuyDetailModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f288 = o.optString("end_date");
            this.f289 = o.optString("promotion_name");
            this.f291 = o.optInt("promotion_id");
            this.f292 = o.optInt("promotion_type_id");
            this.f286 = o.optString(SocialConstants.PARAM_APP_DESC);
            this.f287 = o.optString("start_date");
            JSONArray itemsArray = o.optJSONArray("goods_list");
            if (itemsArray != null && itemsArray.length() > 0) {
                this.f290 = new ArrayList(itemsArray.length());
                for (int i = 0; i < itemsArray.length(); i++) {
                    this.f290.add(new PromotionPackageBuyGoodsModel(itemsArray.optJSONObject(i)));
                }
            }
        }
    }

    public String getEndDate() {
        return this.f288;
    }

    public String getPromotionName() {
        return this.f289;
    }

    public List<PromotionPackageBuyGoodsModel> getGoodsList() {
        return this.f290;
    }

    public int getPromotionId() {
        return this.f291;
    }

    public int getPromotionTypeId() {
        return this.f292;
    }

    public String getDesc() {
        return this.f286;
    }

    public String getStartDate() {
        return this.f287;
    }
}
