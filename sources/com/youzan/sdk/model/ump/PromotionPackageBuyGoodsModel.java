package com.youzan.sdk.model.ump;

import org.json.JSONException;
import org.json.JSONObject;

public class PromotionPackageBuyGoodsModel {

    /* renamed from: ˊ reason: contains not printable characters */
    private String f293;

    /* renamed from: ˋ reason: contains not printable characters */
    private String f294;

    /* renamed from: ˎ reason: contains not printable characters */
    private String f295;

    /* renamed from: ˏ reason: contains not printable characters */
    private String f296;

    public PromotionPackageBuyGoodsModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f293 = o.optString("price");
            this.f294 = o.optString("pic_thumb_url");
            this.f295 = o.optString("title");
            this.f296 = o.optString("pic_url");
        }
    }

    public String getPrice() {
        return this.f293;
    }

    public String getPicThumbUrl() {
        return this.f294;
    }

    public String getTitle() {
        return this.f295;
    }

    public String getPicUrl() {
        return this.f296;
    }
}
