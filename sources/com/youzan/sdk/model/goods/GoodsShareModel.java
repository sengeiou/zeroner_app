package com.youzan.sdk.model.goods;

import com.tencent.open.SocialConstants;
import org.json.JSONException;
import org.json.JSONObject;

public class GoodsShareModel {

    /* renamed from: ʻ reason: contains not printable characters */
    private int f110;

    /* renamed from: ʼ reason: contains not printable characters */
    private String f111;

    /* renamed from: ˊ reason: contains not printable characters */
    private String f112;

    /* renamed from: ˋ reason: contains not printable characters */
    private String f113;

    /* renamed from: ˎ reason: contains not printable characters */
    private String f114;

    /* renamed from: ˏ reason: contains not printable characters */
    private String f115;

    /* renamed from: ᐝ reason: contains not printable characters */
    private int f116;

    public GoodsShareModel() {
    }

    public GoodsShareModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f112 = o.optString("title");
            this.f113 = o.optString("link");
            this.f114 = o.optString("img_url");
            this.f115 = o.optString(SocialConstants.PARAM_APP_DESC);
            this.f116 = o.optInt("img_width");
            this.f110 = o.optInt("img_height");
            this.f111 = o.optString("timeLineTitle");
        }
    }

    public String getDesc() {
        return this.f115 == null ? "" : this.f115;
    }

    public void setDesc(String desc) {
        this.f115 = desc;
    }

    public int getImgHeight() {
        return this.f110;
    }

    public void setImgHeight(int imgHeight) {
        this.f110 = imgHeight;
    }

    public String getImgUrl() {
        return this.f114 == null ? "" : this.f114;
    }

    public void setImgUrl(String imgUrl) {
        this.f114 = imgUrl;
    }

    public int getImgWidth() {
        return this.f116;
    }

    public void setImgWidth(int imgWidth) {
        this.f116 = imgWidth;
    }

    public String getLink() {
        return this.f113 == null ? "" : this.f113;
    }

    public void setLink(String link) {
        this.f113 = link;
    }

    public String getTimeLineTitle() {
        return this.f111 == null ? "" : this.f111;
    }

    public void setTimeLineTitle(String timeLineTitle) {
        this.f111 = timeLineTitle;
    }

    public String getTitle() {
        return this.f112 == null ? "" : this.f112;
    }

    public void setTitle(String title) {
        this.f112 = title;
    }

    public String toJson() {
        return "{\"title\":\"" + getTitle() + "\", \"link\":\"" + getLink() + "\", \"img_url\":\"" + getImgUrl() + "\", \"desc\":\"" + getDesc() + "\", \"img_width\":\"" + getImgWidth() + "\", \"img_height\":\"" + getImgHeight() + "\", \"timeLineTitle\":\"" + getTimeLineTitle() + "\"}";
    }
}
