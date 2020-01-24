package com.youzan.sdk.query;

import android.support.annotation.NonNull;
import com.youzan.sdk.loader.http.b;
import com.youzan.sdk.model.ump.PromotionModel;

public abstract class GoodsPromotionQuery extends b<PromotionModel> {
    /* access modifiers changed from: protected */
    @NonNull
    public Class<PromotionModel> getModel() {
        return PromotionModel.class;
    }

    /* access modifiers changed from: protected */
    @NonNull
    public String attachTo() {
        return "appsdk.ump.promotion.get";
    }
}
