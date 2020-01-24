package com.youzan.sdk.model.goods;

import org.json.JSONException;
import org.json.JSONObject;

public class GoodsImageModel {

    /* renamed from: ʻ reason: contains not printable characters */
    private String f85;

    /* renamed from: ˊ reason: contains not printable characters */
    private int f86;

    /* renamed from: ˋ reason: contains not printable characters */
    private String f87;

    /* renamed from: ˎ reason: contains not printable characters */
    private String f88;

    /* renamed from: ˏ reason: contains not printable characters */
    private String f89;

    /* renamed from: ᐝ reason: contains not printable characters */
    private String f90;

    public GoodsImageModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f86 = o.optInt("id");
            this.f87 = o.optString("created");
            this.f88 = o.optString("url");
            this.f89 = o.optString("thumbnail");
            this.f90 = o.optString("medium");
            this.f85 = o.optString("combine");
        }
    }

    public int getId() {
        return this.f86;
    }

    public void setId(int id) {
        this.f86 = id;
    }

    public String getCreated() {
        return this.f87;
    }

    public void setCreated(String created) {
        this.f87 = created;
    }

    public String getUrl() {
        return this.f88;
    }

    public void setUrl(String url) {
        this.f88 = url;
    }

    public String getThumbnail() {
        return this.f89;
    }

    public void setThumbnail(String thumbnail) {
        this.f89 = thumbnail;
    }

    public String getMedium() {
        return this.f90;
    }

    public void setMedium(String medium) {
        this.f90 = medium;
    }

    public String getCombine() {
        return this.f85;
    }

    public void setCombine(String combine) {
        this.f85 = combine;
    }
}
