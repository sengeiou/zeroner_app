package com.youzan.sdk.model.goods;

import com.tencent.open.SocialConstants;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.util.Const.TableSchema;

public class GoodsQrcodeModel {

    /* renamed from: ʻ reason: contains not printable characters */
    private String f101;

    /* renamed from: ʼ reason: contains not printable characters */
    private String f102;

    /* renamed from: ʽ reason: contains not printable characters */
    private String f103;

    /* renamed from: ˊ reason: contains not printable characters */
    private int f104;

    /* renamed from: ˋ reason: contains not printable characters */
    private String f105;

    /* renamed from: ˎ reason: contains not printable characters */
    private String f106;

    /* renamed from: ˏ reason: contains not printable characters */
    private String f107;

    /* renamed from: ͺ reason: contains not printable characters */
    private String f108;

    /* renamed from: ᐝ reason: contains not printable characters */
    private String f109;

    public GoodsQrcodeModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f104 = o.optInt("id");
            this.f105 = o.optString(TableSchema.COLUMN_NAME);
            this.f106 = o.optString(SocialConstants.PARAM_APP_DESC);
            this.f107 = o.optString("created");
            this.f109 = o.optString("type");
            this.f101 = o.optString("discount");
            this.f102 = o.optString("decrease");
            this.f103 = o.optString("link_url");
            this.f108 = o.optString("weixin_qrcode_url");
        }
    }

    public int getId() {
        return this.f104;
    }

    public void setId(int id) {
        this.f104 = id;
    }

    public String getName() {
        return this.f105;
    }

    public void setName(String name) {
        this.f105 = name;
    }

    public String getDesc() {
        return this.f106;
    }

    public void setDesc(String desc) {
        this.f106 = desc;
    }

    public String getCreated() {
        return this.f107;
    }

    public void setCreated(String created) {
        this.f107 = created;
    }

    public String getType() {
        return this.f109;
    }

    public void setType(String type) {
        this.f109 = type;
    }

    public String getDiscount() {
        return this.f101;
    }

    public void setDiscount(String discount) {
        this.f101 = discount;
    }

    public String getDecrease() {
        return this.f102;
    }

    public void setDecrease(String decrease) {
        this.f102 = decrease;
    }

    public String getLinkUrl() {
        return this.f103;
    }

    public void setLinkUrl(String linkUrl) {
        this.f103 = linkUrl;
    }

    public String getWeixinQrcodeUrl() {
        return this.f108;
    }

    public void setWeixinQrcodeUrl(String weixinQrcodeUrl) {
        this.f108 = weixinQrcodeUrl;
    }
}
