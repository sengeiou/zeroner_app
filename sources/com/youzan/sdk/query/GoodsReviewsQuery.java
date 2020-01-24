package com.youzan.sdk.query;

import android.support.annotation.NonNull;
import com.youzan.sdk.loader.http.b;
import com.youzan.sdk.loader.http.interfaces.NotImplementedException;
import com.youzan.sdk.model.reviews.ReviewsModel;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class GoodsReviewsQuery extends b<ReviewsModel> {
    /* access modifiers changed from: protected */
    @NonNull
    public String attachTo() {
        return "appsdk.item.reviews.queryreview";
    }

    /* access modifiers changed from: protected */
    @NonNull
    public Class<ReviewsModel> getModel() {
        return ReviewsModel.class;
    }

    /* access modifiers changed from: protected */
    /* renamed from: ˊ reason: contains not printable characters */
    public ReviewsModel onParse(@NonNull JSONObject data) throws NotImplementedException, JSONException {
        JSONObject body = data.optJSONObject("itemReviewsModels");
        if (body != null) {
            return new ReviewsModel(body.optJSONObject("data"));
        }
        throw new IllegalArgumentException("Unsupported json structures");
    }
}
