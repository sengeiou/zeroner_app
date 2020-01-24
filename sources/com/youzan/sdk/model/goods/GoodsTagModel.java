package com.youzan.sdk.model.goods;

import com.tencent.open.SocialConstants;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.util.Const.TableSchema;

public class GoodsTagModel {

    /* renamed from: ʻ reason: contains not printable characters */
    private String f134;

    /* renamed from: ʼ reason: contains not printable characters */
    private String f135;

    /* renamed from: ʽ reason: contains not printable characters */
    private String f136;

    /* renamed from: ˊ reason: contains not printable characters */
    private int f137;

    /* renamed from: ˋ reason: contains not printable characters */
    private String f138;

    /* renamed from: ˎ reason: contains not printable characters */
    private String f139;

    /* renamed from: ˏ reason: contains not printable characters */
    private String f140;

    /* renamed from: ᐝ reason: contains not printable characters */
    private int f141;

    public GoodsTagModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f137 = o.optInt("id");
            this.f138 = o.optString(TableSchema.COLUMN_NAME);
            this.f139 = o.optString("type");
            this.f140 = o.optString("created");
            this.f141 = o.optInt("item_num");
            this.f134 = o.optString("tag_url");
            this.f135 = o.optString("share_url");
            this.f136 = o.optString(SocialConstants.PARAM_APP_DESC);
        }
    }

    public int getId() {
        return this.f137;
    }

    public void setId(int id) {
        this.f137 = id;
    }

    public String getName() {
        return this.f138;
    }

    public void setName(String name) {
        this.f138 = name;
    }

    public String getType() {
        return this.f139;
    }

    public void setType(String type) {
        this.f139 = type;
    }

    public String getCreated() {
        return this.f140;
    }

    public void setCreated(String created) {
        this.f140 = created;
    }

    public int getItemNum() {
        return this.f141;
    }

    public void setItemNum(int itemNum) {
        this.f141 = itemNum;
    }

    public String getTagUrl() {
        return this.f134;
    }

    public void setTagUrl(String tagUrl) {
        this.f134 = tagUrl;
    }

    public String getShareUrl() {
        return this.f135;
    }

    public void setShareUrl(String shareUrl) {
        this.f135 = shareUrl;
    }

    public String getDesc() {
        return this.f136;
    }

    public void setDesc(String desc) {
        this.f136 = desc;
    }
}
