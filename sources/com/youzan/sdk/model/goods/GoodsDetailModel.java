package com.youzan.sdk.model.goods;

import com.tencent.open.SocialConstants;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GoodsDetailModel {

    /* renamed from: ʳ reason: contains not printable characters */
    private boolean f42;

    /* renamed from: ʴ reason: contains not printable characters */
    private List<GoodsSkuModel> f43;

    /* renamed from: ʹ reason: contains not printable characters */
    private String f44;

    /* renamed from: ʻ reason: contains not printable characters */
    private int f45;

    /* renamed from: ʼ reason: contains not printable characters */
    private String f46;

    /* renamed from: ʽ reason: contains not printable characters */
    private String f47;

    /* renamed from: ʾ reason: contains not printable characters */
    private String f48;

    /* renamed from: ʿ reason: contains not printable characters */
    private int f49;

    /* renamed from: ˆ reason: contains not printable characters */
    private List<GoodsImageModel> f50;

    /* renamed from: ˇ reason: contains not printable characters */
    private List<GoodsQrcodeModel> f51;

    /* renamed from: ˈ reason: contains not printable characters */
    private String f52;

    /* renamed from: ˉ reason: contains not printable characters */
    private boolean f53;

    /* renamed from: ˊ reason: contains not printable characters */
    public String f54;

    /* renamed from: ˋ reason: contains not printable characters */
    private String f55;

    /* renamed from: ˌ reason: contains not printable characters */
    private boolean f56;

    /* renamed from: ˍ reason: contains not printable characters */
    private boolean f57;

    /* renamed from: ˎ reason: contains not printable characters */
    private String f58;

    /* renamed from: ˏ reason: contains not printable characters */
    private String f59;

    /* renamed from: ˑ reason: contains not printable characters */
    private boolean f60;

    /* renamed from: ˡ reason: contains not printable characters */
    private List<GoodsTagModel> f61;

    /* renamed from: ˮ reason: contains not printable characters */
    private List<GoodsMessageModel> f62;

    /* renamed from: ͺ reason: contains not printable characters */
    private String f63;

    /* renamed from: ι reason: contains not printable characters */
    private String f64;

    /* renamed from: ՙ reason: contains not printable characters */
    private int f65;

    /* renamed from: י reason: contains not printable characters */
    private int f66;

    /* renamed from: ـ reason: contains not printable characters */
    private String f67;

    /* renamed from: ٴ reason: contains not printable characters */
    private double f68;

    /* renamed from: ۥ reason: contains not printable characters */
    private String f69;

    /* renamed from: ᐝ reason: contains not printable characters */
    private int f70;

    /* renamed from: ᐠ reason: contains not printable characters */
    private long f71;

    /* renamed from: ᐣ reason: contains not printable characters */
    private long f72;

    /* renamed from: ᐧ reason: contains not printable characters */
    private long f73;

    /* renamed from: ᐨ reason: contains not printable characters */
    private String f74;

    /* renamed from: ᴵ reason: contains not printable characters */
    private int f75;

    /* renamed from: ᵎ reason: contains not printable characters */
    private String f76;

    /* renamed from: ᵔ reason: contains not printable characters */
    private String f77;

    /* renamed from: ᵢ reason: contains not printable characters */
    private int f78;

    /* renamed from: ⁱ reason: contains not printable characters */
    private boolean f79;

    /* renamed from: ﹳ reason: contains not printable characters */
    private String f80;

    /* renamed from: ﹶ reason: contains not printable characters */
    private int f81;

    /* renamed from: ﹺ reason: contains not printable characters */
    private int f82;

    /* renamed from: ｰ reason: contains not printable characters */
    private String f83;

    /* renamed from: ﾞ reason: contains not printable characters */
    private String f84;

    public GoodsDetailModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f54 = o.optString("kdt_id");
            this.f55 = o.optString("num_iid");
            this.f58 = o.optString("alias");
            this.f59 = o.optString("title");
            this.f70 = o.optInt("cid");
            this.f45 = o.optInt("promotion_cid");
            this.f46 = o.optString("tag_ids");
            this.f47 = o.optString(SocialConstants.PARAM_APP_DESC);
            this.f63 = o.optString("origin_price");
            this.f64 = o.optString("outer_id");
            this.f48 = o.optString("outer_buy_url");
            this.f49 = o.optInt("buy_quota");
            this.f52 = o.optString("created");
            this.f53 = o.optBoolean("is_virtual");
            this.f56 = o.optBoolean("is_listing");
            this.f57 = o.optBoolean("is_lock");
            this.f60 = o.optBoolean("is_used");
            this.f67 = o.optString("product_type");
            this.f73 = o.optLong("auto_listing_time");
            this.f74 = o.optString("detail_url");
            this.f80 = o.optString("share_url");
            this.f84 = o.optString("pic_url");
            this.f44 = o.optString("pic_thumb_url");
            this.f65 = o.optInt("num");
            this.f66 = o.optInt("sold_num");
            this.f68 = o.optDouble("price");
            this.f75 = o.optInt("post_type");
            this.f76 = o.optString("post_fee");
            this.f77 = o.optString("delivery_template_fee");
            this.f78 = o.optInt("item_type");
            this.f79 = o.optBoolean("is_supplier_item");
            this.f81 = o.optInt("like_count");
            this.f82 = o.optInt("template_id");
            this.f83 = o.optString("template_title");
            this.f42 = o.optBoolean("join_level_discount");
            this.f69 = o.optString("sku_tree");
            this.f71 = o.optLong("item_validity_start");
            this.f72 = o.optLong("item_validity_end");
            JSONArray skusArray = o.optJSONArray("skus");
            if (skusArray != null && skusArray.length() > 0) {
                this.f43 = new ArrayList(skusArray.length());
                for (int i = 0; i < skusArray.length(); i++) {
                    this.f43.add(new GoodsSkuModel(skusArray.optJSONObject(i)));
                }
            }
            JSONArray itemImgsArray = o.optJSONArray("item_imgs");
            if (itemImgsArray != null && itemImgsArray.length() > 0) {
                this.f50 = new ArrayList(itemImgsArray.length());
                for (int i2 = 0; i2 < itemImgsArray.length(); i2++) {
                    this.f50.add(new GoodsImageModel(itemImgsArray.optJSONObject(i2)));
                }
            }
            JSONArray itemQrcodesArray = o.optJSONArray("item_qrcodes");
            if (itemQrcodesArray != null && itemQrcodesArray.length() > 0) {
                this.f51 = new ArrayList(itemQrcodesArray.length());
                for (int i3 = 0; i3 < itemQrcodesArray.length(); i3++) {
                    this.f51.add(new GoodsQrcodeModel(itemQrcodesArray.optJSONObject(i3)));
                }
            }
            JSONArray itemTagsArray = o.optJSONArray("item_tags");
            if (itemTagsArray != null && itemTagsArray.length() > 0) {
                this.f61 = new ArrayList(itemTagsArray.length());
                for (int i4 = 0; i4 < itemTagsArray.length(); i4++) {
                    this.f61.add(new GoodsTagModel(itemTagsArray.optJSONObject(i4)));
                }
            }
            JSONArray itemMessagesArray = o.optJSONArray("messages");
            if (itemMessagesArray != null && itemMessagesArray.length() > 0) {
                this.f62 = new ArrayList(itemMessagesArray.length());
                for (int i5 = 0; i5 < itemMessagesArray.length(); i5++) {
                    this.f62.add(new GoodsMessageModel(itemMessagesArray.optJSONObject(i5)));
                }
            }
        }
    }

    public String getAlias() {
        return this.f58;
    }

    public long getAutoListingTime() {
        return this.f73;
    }

    public int getBuyQuota() {
        return this.f49;
    }

    public int getCid() {
        return this.f70;
    }

    public String getCreated() {
        return this.f52;
    }

    public String getDeliveryTemplateFee() {
        return this.f77;
    }

    public String getDesc() {
        return this.f47;
    }

    public String getDetailUrl() {
        return this.f74;
    }

    public boolean isListing() {
        return this.f56;
    }

    public boolean isLock() {
        return this.f57;
    }

    public boolean isSupplierItem() {
        return this.f79;
    }

    public boolean isUsed() {
        return this.f60;
    }

    public boolean isVirtual() {
        return this.f53;
    }

    public List<GoodsImageModel> getItemImgs() {
        return this.f50;
    }

    public List<GoodsQrcodeModel> getItemQrcodes() {
        return this.f51;
    }

    public List<GoodsTagModel> getItemTags() {
        return this.f61;
    }

    public int getItemType() {
        return this.f78;
    }

    public boolean isJoinLevelDiscount() {
        return this.f42;
    }

    public int getLikeCount() {
        return this.f81;
    }

    public List<GoodsMessageModel> getMessages() {
        return this.f62;
    }

    public int getNum() {
        return this.f65;
    }

    public String getNumIid() {
        return this.f55;
    }

    public String getOriginPrice() {
        return this.f63;
    }

    public String getOuterBuyUrl() {
        return this.f48;
    }

    public String getOuterId() {
        return this.f64;
    }

    public String getPicThumbUrl() {
        return this.f44;
    }

    public String getPicUrl() {
        return this.f84;
    }

    public String getPostFee() {
        return this.f76;
    }

    public int getPostType() {
        return this.f75;
    }

    public double getPrice() {
        return this.f68;
    }

    public String getProductType() {
        return this.f67;
    }

    public int getPromotionCid() {
        return this.f45;
    }

    public String getShareUrl() {
        return this.f80;
    }

    public List<GoodsSkuModel> getSkus() {
        return this.f43;
    }

    public int getSoldNum() {
        return this.f66;
    }

    public String getTagIds() {
        return this.f46;
    }

    public int getTemplateId() {
        return this.f82;
    }

    public String getTemplateTitle() {
        return this.f83;
    }

    public String getTitle() {
        return this.f59;
    }

    public String getSkuTree() {
        return this.f69;
    }

    public String getKdtId() {
        return this.f54;
    }

    public long getItemValidityEnd() {
        return this.f72;
    }

    public long getItemValidityStart() {
        return this.f71;
    }
}
