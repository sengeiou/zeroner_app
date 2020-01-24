package com.youzan.sdk.model.shop;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.util.Const.TableSchema;

public class ShopBasicModel {

    /* renamed from: ˊ reason: contains not printable characters */
    private int f175;

    /* renamed from: ˋ reason: contains not printable characters */
    private String f176;

    /* renamed from: ˎ reason: contains not printable characters */
    private String f177;

    /* renamed from: ˏ reason: contains not printable characters */
    private String f178;

    /* renamed from: ᐝ reason: contains not printable characters */
    private String f179;

    public ShopBasicModel(int certType, String name, String logo, String url, String sid) {
        this.f175 = certType;
        this.f176 = name;
        this.f177 = logo;
        this.f178 = url;
        this.f179 = sid;
    }

    public ShopBasicModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f175 = o.optInt("cert_type");
            this.f176 = o.optString(TableSchema.COLUMN_NAME);
            this.f177 = o.optString("logo");
            this.f178 = o.optString("url");
            this.f179 = o.optString("sid");
        }
    }

    public int getCertType() {
        return this.f175;
    }

    public String getName() {
        return this.f176;
    }

    public String getLogo() {
        return this.f177;
    }

    public String getUrl() {
        return this.f178;
    }

    public String getSid() {
        return this.f179;
    }
}
