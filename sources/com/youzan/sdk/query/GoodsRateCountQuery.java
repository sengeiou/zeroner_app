package com.youzan.sdk.query;

import android.support.annotation.NonNull;
import com.youzan.sdk.loader.http.b;
import com.youzan.sdk.model.reviews.ReviewsRateModel;

public abstract class GoodsRateCountQuery extends b<ReviewsRateModel> {
    /* access modifiers changed from: protected */
    @NonNull
    public String attachTo() {
        return "appsdk.item.reviews.countgoodsrate";
    }

    /* access modifiers changed from: protected */
    @NonNull
    public Class<ReviewsRateModel> getModel() {
        return ReviewsRateModel.class;
    }
}
