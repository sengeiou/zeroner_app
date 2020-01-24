package com.youzan.sdk.model.goods;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.util.Const.TableSchema;

public class GoodsMessageModel {

    /* renamed from: ʻ reason: contains not printable characters */
    private boolean f91;

    /* renamed from: ʼ reason: contains not printable characters */
    private boolean f92;

    /* renamed from: ʽ reason: contains not printable characters */
    private boolean f93;

    /* renamed from: ˊ reason: contains not printable characters */
    private String f94;

    /* renamed from: ˋ reason: contains not printable characters */
    private String f95;

    /* renamed from: ˎ reason: contains not printable characters */
    private int f96;

    /* renamed from: ˏ reason: contains not printable characters */
    private int f97;

    /* renamed from: ͺ reason: contains not printable characters */
    private boolean f98;

    /* renamed from: ι reason: contains not printable characters */
    private boolean f99;

    /* renamed from: ᐝ reason: contains not printable characters */
    private boolean f100;

    public GoodsMessageModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f94 = o.optString(TableSchema.COLUMN_NAME);
            this.f95 = o.optString("type");
            this.f96 = o.optInt("multiple");
            this.f97 = o.optInt("required");
            this.f100 = o.optBoolean("disable");
            this.f91 = o.optBoolean("disableDelete");
            this.f92 = o.optBoolean("disableEditName");
            this.f93 = o.optBoolean("disableType");
            this.f98 = o.optBoolean("disableRequired");
            this.f99 = o.optBoolean("disableMultiple");
        }
    }

    public boolean isDisable() {
        return this.f100;
    }

    public boolean isDisableDelete() {
        return this.f91;
    }

    public boolean isDisableEditName() {
        return this.f92;
    }

    public boolean isDisableMultiple() {
        return this.f99;
    }

    public boolean isDisableRequired() {
        return this.f98;
    }

    public boolean isDisableType() {
        return this.f93;
    }

    public int getMultiple() {
        return this.f96;
    }

    public String getName() {
        return this.f94;
    }

    public int getRequired() {
        return this.f97;
    }

    public String getType() {
        return this.f95;
    }
}
