package com.youzan.sdk.model.goods;

import com.github.mikephil.charting.utils.Utils;
import org.json.JSONException;
import org.json.JSONObject;

public class GoodsSkuModel {

    /* renamed from: ʻ reason: contains not printable characters */
    private String f121;

    /* renamed from: ʼ reason: contains not printable characters */
    private String f122;

    /* renamed from: ʽ reason: contains not printable characters */
    private int f123;

    /* renamed from: ʾ reason: contains not printable characters */
    private String f124;

    /* renamed from: ˊ reason: contains not printable characters */
    private String f125;

    /* renamed from: ˋ reason: contains not printable characters */
    private String f126;

    /* renamed from: ˎ reason: contains not printable characters */
    private String f127;

    /* renamed from: ˏ reason: contains not printable characters */
    private String f128;

    /* renamed from: ͺ reason: contains not printable characters */
    private double f129;

    /* renamed from: ι reason: contains not printable characters */
    private String f130;

    /* renamed from: ᐝ reason: contains not printable characters */
    private int f131;

    public GoodsSkuModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f125 = o.optString("outer_id");
            this.f126 = o.optString("sku_id");
            this.f127 = o.optString("sku_unique_code");
            this.f128 = o.optString("num_iid");
            this.f121 = o.optString("properties_name");
            this.f122 = o.optString("properties_name_json");
            this.f131 = o.optInt("quantity");
            this.f123 = o.optInt("with_hold_quantity");
            this.f129 = o.optDouble("price", Utils.DOUBLE_EPSILON);
            this.f130 = o.optString("created");
            this.f124 = o.optString("modified");
        }
    }

    public String getOuterId() {
        return this.f125;
    }

    public void setOuterId(String outerId) {
        this.f125 = outerId;
    }

    public String getSkuId() {
        return this.f126;
    }

    public void setSkuId(String skuId) {
        this.f126 = skuId;
    }

    public String getSkuUniqueCode() {
        return this.f127;
    }

    public void setSkuUniqueCode(String skuUniqueCode) {
        this.f127 = skuUniqueCode;
    }

    public String getNumIid() {
        return this.f128;
    }

    public void setNumIid(String numIid) {
        this.f128 = numIid;
    }

    public int getQuantity() {
        return this.f131;
    }

    public void setQuantity(int quantity) {
        this.f131 = quantity;
    }

    public String getPropertiesName() {
        return this.f121;
    }

    public void setPropertiesName(String propertiesName) {
        this.f121 = propertiesName;
    }

    public String getPropertiesNameJson() {
        return this.f122;
    }

    public void setPropertiesNameJson(String propertiesNameJson) {
        this.f122 = propertiesNameJson;
    }

    public int getWithHoldQuantity() {
        return this.f123;
    }

    public void setWithHoldQuantity(int withHoldQuantity) {
        this.f123 = withHoldQuantity;
    }

    public double getPrice() {
        return this.f129;
    }

    public void setPrice(double price) {
        this.f129 = price;
    }

    public String getCreated() {
        return this.f130;
    }

    public void setCreated(String created) {
        this.f130 = created;
    }

    public String getModified() {
        return this.f124;
    }

    public void setModified(String modified) {
        this.f124 = modified;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return this.f126.equals(((GoodsSkuModel) o).f126);
    }

    public int hashCode() {
        return this.f126.hashCode();
    }
}
