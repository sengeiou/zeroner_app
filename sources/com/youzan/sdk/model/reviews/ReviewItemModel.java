package com.youzan.sdk.model.reviews;

import com.tencent.open.SocialConstants;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ReviewItemModel {

    /* renamed from: ʹ reason: contains not printable characters */
    private int f145;

    /* renamed from: ʻ reason: contains not printable characters */
    private String f146;

    /* renamed from: ʼ reason: contains not printable characters */
    private int f147;

    /* renamed from: ʽ reason: contains not printable characters */
    private String f148;

    /* renamed from: ʾ reason: contains not printable characters */
    private int f149;

    /* renamed from: ʿ reason: contains not printable characters */
    private int f150;

    /* renamed from: ˈ reason: contains not printable characters */
    private int f151;

    /* renamed from: ˉ reason: contains not printable characters */
    private String f152;

    /* renamed from: ˊ reason: contains not printable characters */
    private String f153;

    /* renamed from: ˋ reason: contains not printable characters */
    private int f154;

    /* renamed from: ˌ reason: contains not printable characters */
    private int f155;

    /* renamed from: ˍ reason: contains not printable characters */
    private boolean f156;

    /* renamed from: ˎ reason: contains not printable characters */
    private int f157;

    /* renamed from: ˏ reason: contains not printable characters */
    private int f158;

    /* renamed from: ˑ reason: contains not printable characters */
    private int f159;

    /* renamed from: ͺ reason: contains not printable characters */
    private String f160;

    /* renamed from: ι reason: contains not printable characters */
    private String f161;

    /* renamed from: ՙ reason: contains not printable characters */
    private int f162;

    /* renamed from: י reason: contains not printable characters */
    private List<String> f163;

    /* renamed from: ـ reason: contains not printable characters */
    private String f164;

    /* renamed from: ᐝ reason: contains not printable characters */
    private int f165;

    /* renamed from: ᐧ reason: contains not printable characters */
    private String f166;

    /* renamed from: ᐨ reason: contains not printable characters */
    private int f167;

    /* renamed from: ﹳ reason: contains not printable characters */
    private int f168;

    /* renamed from: ﾞ reason: contains not printable characters */
    private boolean f169;

    public ReviewItemModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f153 = o.optString("fansNickname");
            this.f154 = o.getInt("goodsId");
            this.f157 = o.optInt("supplierGoodsId");
            this.f158 = o.optInt("buyerId");
            this.f165 = o.optInt("likeNum");
            this.f146 = o.optString("fansPicture");
            this.f147 = o.optInt("rate");
            this.f148 = o.optString("review");
            this.f160 = o.optString("createdTime");
            this.f161 = o.optString("alias");
            this.f149 = o.optInt("id");
            this.f150 = o.optInt("skuId");
            this.f151 = o.optInt("logiRate");
            this.f152 = o.optString("orderNo");
            this.f155 = o.optInt("fansId");
            this.f156 = o.optBoolean("otherShop");
            this.f159 = o.optInt("kdtId");
            this.f164 = o.optString("updateTime");
            this.f166 = o.optString("sellerComment");
            this.f167 = o.optInt("supplierKdtId");
            this.f168 = o.optInt("descRate");
            this.f169 = o.optBoolean("ilike");
            this.f145 = o.optInt("servRate");
            this.f162 = o.optInt("fansType");
            JSONArray arrayObj = o.optJSONArray(SocialConstants.PARAM_AVATAR_URI);
            if (arrayObj != null && arrayObj.length() > 0) {
                int length = arrayObj.length();
                this.f163 = new ArrayList(length);
                for (int i = 0; i < length; i++) {
                    this.f163.add(arrayObj.optString(i));
                }
            }
        }
    }

    public String getAlias() {
        return this.f161;
    }

    public int getBuyerId() {
        return this.f158;
    }

    public String getCreatedTime() {
        return this.f160;
    }

    public int getDescRate() {
        return this.f168;
    }

    public int getFansId() {
        return this.f155;
    }

    public String getFansNickname() {
        return this.f153;
    }

    public String getFansPicture() {
        return this.f146;
    }

    public int getFansType() {
        return this.f162;
    }

    public int getGoodsId() {
        return this.f154;
    }

    public int getId() {
        return this.f149;
    }

    public boolean isIlike() {
        return this.f169;
    }

    public int getKdtId() {
        return this.f159;
    }

    public int getLikeNum() {
        return this.f165;
    }

    public int getLogiRate() {
        return this.f151;
    }

    public String getOrderNo() {
        return this.f152;
    }

    public boolean isOtherShop() {
        return this.f156;
    }

    public List<String> getPicture() {
        return this.f163;
    }

    public int getRate() {
        return this.f147;
    }

    public String getReview() {
        return this.f148;
    }

    public String getSellerComment() {
        return this.f166;
    }

    public int getServRate() {
        return this.f145;
    }

    public int getSkuId() {
        return this.f150;
    }

    public int getSupplierGoodsId() {
        return this.f157;
    }

    public int getSupplierKdtId() {
        return this.f167;
    }

    public String getUpdateTime() {
        return this.f164;
    }
}
