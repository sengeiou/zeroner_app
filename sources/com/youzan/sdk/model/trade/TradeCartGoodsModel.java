package com.youzan.sdk.model.trade;

import android.text.TextUtils;
import com.youzan.sdk.model.goods.GoodsSkuItemModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TradeCartGoodsModel {

    /* renamed from: ʹ reason: contains not printable characters */
    private long f212;

    /* renamed from: ʻ reason: contains not printable characters */
    private String f213;

    /* renamed from: ʼ reason: contains not printable characters */
    private String f214;

    /* renamed from: ʽ reason: contains not printable characters */
    private String f215;

    /* renamed from: ʾ reason: contains not printable characters */
    private int f216;

    /* renamed from: ʿ reason: contains not printable characters */
    private String f217;

    /* renamed from: ˈ reason: contains not printable characters */
    private String f218;

    /* renamed from: ˉ reason: contains not printable characters */
    private String f219;

    /* renamed from: ˊ reason: contains not printable characters */
    private boolean f220 = true;

    /* renamed from: ˋ reason: contains not printable characters */
    private String f221;

    /* renamed from: ˌ reason: contains not printable characters */
    private String f222;

    /* renamed from: ˍ reason: contains not printable characters */
    private int f223;

    /* renamed from: ˎ reason: contains not printable characters */
    private String f224;

    /* renamed from: ˏ reason: contains not printable characters */
    private int f225;

    /* renamed from: ˑ reason: contains not printable characters */
    private String f226;

    /* renamed from: ͺ reason: contains not printable characters */
    private String f227;

    /* renamed from: ι reason: contains not printable characters */
    private long f228;

    /* renamed from: ՙ reason: contains not printable characters */
    private String f229;

    /* renamed from: י reason: contains not printable characters */
    private int f230;

    /* renamed from: ـ reason: contains not printable characters */
    private String f231;

    /* renamed from: ٴ reason: contains not printable characters */
    private String f232;

    /* renamed from: ᐝ reason: contains not printable characters */
    private int f233;

    /* renamed from: ᐧ reason: contains not printable characters */
    private long f234;

    /* renamed from: ᐨ reason: contains not printable characters */
    private String f235;

    /* renamed from: ᴵ reason: contains not printable characters */
    private String f236;

    /* renamed from: ᵎ reason: contains not printable characters */
    private int f237;

    /* renamed from: ᵔ reason: contains not printable characters */
    private long f238;

    /* renamed from: ᵢ reason: contains not printable characters */
    private long f239;

    /* renamed from: ⁱ reason: contains not printable characters */
    private int f240;

    /* renamed from: ﹳ reason: contains not printable characters */
    private long f241;

    /* renamed from: ﾞ reason: contains not printable characters */
    private long f242;

    public TradeCartGoodsModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f221 = o.optString("thumb_url");
            this.f224 = o.optString("discount_price");
            this.f225 = o.optInt("num");
            this.f233 = o.optInt("stock_num");
            this.f213 = o.optString("discount");
            this.f214 = o.optString("title");
            this.f215 = o.optString("activity_alias");
            this.f227 = o.optString("platform");
            this.f228 = o.optLong("kdt_id");
            this.f216 = o.optInt("sub_type");
            this.f217 = o.optString("alias");
            this.f218 = o.optString("nobody");
            this.f219 = o.optString("sku");
            this.f223 = o.optInt("direct_seller");
            this.f226 = o.optString("ext");
            this.f231 = o.optString("store_id");
            this.f234 = o.optLong("pay_price");
            this.f235 = o.optString("error_msg");
            this.f241 = o.optLong("create_time");
            this.f242 = o.optLong("goods_id");
            this.f212 = o.optLong("sku_id");
            this.f229 = o.optString("attachment_url");
            this.f230 = o.optInt("service_type");
            this.f232 = o.optString("messages");
            this.f236 = o.optString("support_express_type");
            this.f237 = o.optInt("goods_type");
            this.f238 = o.optLong("updated_time");
            this.f239 = o.optLong("channel_id");
            this.f240 = o.optInt("limit_num");
            if (!TextUtils.isEmpty(this.f219)) {
                JSONArray array = new JSONArray(this.f219);
                int length = array.length();
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < length; i++) {
                    if (i > 0) {
                        builder.append(", ");
                    }
                    builder.append(new GoodsSkuItemModel(array.getJSONObject(i)).getvDesc());
                }
                this.f222 = builder.toString();
            }
        }
    }

    public String getActivityAlias() {
        return this.f215;
    }

    public void setActivityAlias(String activityAlias) {
        this.f215 = activityAlias;
    }

    public String getAlias() {
        return this.f217;
    }

    public void setAlias(String alias) {
        this.f217 = alias;
    }

    public String getAttachmentUrl() {
        return this.f229;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.f229 = attachmentUrl;
    }

    public long getChannelId() {
        return this.f239;
    }

    public void setChannelId(long channelId) {
        this.f239 = channelId;
    }

    public long getCreateTime() {
        return this.f241;
    }

    public void setCreateTime(long createTime) {
        this.f241 = createTime;
    }

    public int getDirectSeller() {
        return this.f223;
    }

    public void setDirectSeller(int directSeller) {
        this.f223 = directSeller;
    }

    public String getDiscount() {
        return this.f213;
    }

    public void setDiscount(String discount) {
        this.f213 = discount;
    }

    public String getDiscountPrice() {
        return this.f224;
    }

    public void setDiscountPrice(String discountPrice) {
        this.f224 = discountPrice;
    }

    public String getErrorMsg() {
        return this.f235;
    }

    public void setErrorMsg(String errorMsg) {
        this.f235 = errorMsg;
    }

    public String getExt() {
        return this.f226;
    }

    public void setExt(String ext) {
        this.f226 = ext;
    }

    public long getGoodsId() {
        return this.f242;
    }

    public void setGoodsId(long goodsId) {
        this.f242 = goodsId;
    }

    public int getGoodsType() {
        return this.f237;
    }

    public void setGoodsType(int goodsType) {
        this.f237 = goodsType;
    }

    public long getKdtId() {
        return this.f228;
    }

    public void setKdtId(long kdtId) {
        this.f228 = kdtId;
    }

    public int getLimitNum() {
        return this.f240;
    }

    public void setLimitNum(int limitNum) {
        this.f240 = limitNum;
    }

    public String getMessages() {
        return this.f232;
    }

    public void setMessages(String messages) {
        this.f232 = messages;
    }

    public String getNobody() {
        return this.f218;
    }

    public void setNobody(String nobody) {
        this.f218 = nobody;
    }

    public int getNum() {
        return this.f225;
    }

    public void setNum(int num) {
        this.f225 = num;
    }

    public long getPayPrice() {
        return this.f234;
    }

    public void setPayPrice(long payPrice) {
        this.f234 = payPrice;
    }

    public String getPlatform() {
        return this.f227;
    }

    public void setPlatform(String platform) {
        this.f227 = platform;
    }

    public int getServiceType() {
        return this.f230;
    }

    public void setServiceType(int serviceType) {
        this.f230 = serviceType;
    }

    public String getSku() {
        return this.f219;
    }

    public void setSku(String sku) {
        this.f219 = sku;
    }

    public String getSkuDesc() {
        return this.f222;
    }

    public void setSkuDesc(String skuDesc) {
        this.f222 = skuDesc;
    }

    public long getSkuId() {
        return this.f212;
    }

    public void setSkuId(long skuId) {
        this.f212 = skuId;
    }

    public int getStockNum() {
        return this.f233;
    }

    public void setStockNum(int stockNum) {
        this.f233 = stockNum;
    }

    public String getStoreId() {
        return this.f231;
    }

    public void setStoreId(String storeId) {
        this.f231 = storeId;
    }

    public int getSubType() {
        return this.f216;
    }

    public void setSubType(int subType) {
        this.f216 = subType;
    }

    public String getSupportExpressType() {
        return this.f236;
    }

    public void setSupportExpressType(String supportExpressType) {
        this.f236 = supportExpressType;
    }

    public String getThumbUrl() {
        return this.f221;
    }

    public void setThumbUrl(String thumbUrl) {
        this.f221 = thumbUrl;
    }

    public String getTitle() {
        return this.f214;
    }

    public void setTitle(String title) {
        this.f214 = title;
    }

    public long getUpdatedTime() {
        return this.f238;
    }

    public void setUpdatedTime(long updatedTime) {
        this.f238 = updatedTime;
    }

    public boolean isSelected() {
        return this.f220;
    }

    public void setSelected(boolean selected) {
        this.f220 = selected;
    }

    public boolean equals(Object o) {
        boolean z = true;
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TradeCartGoodsModel model = (TradeCartGoodsModel) o;
        if (this.f228 != model.f228 || this.f242 != model.f242) {
            return false;
        }
        if (this.f212 != model.f212) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return (((((int) (this.f228 ^ (this.f228 >>> 32))) * 31) + ((int) (this.f242 ^ (this.f242 >>> 32)))) * 31) + ((int) (this.f212 ^ (this.f212 >>> 32)));
    }
}
