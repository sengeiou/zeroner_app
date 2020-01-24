package com.youzan.sdk.query;

import android.support.annotation.NonNull;
import com.youzan.sdk.loader.http.b;
import com.youzan.sdk.loader.http.interfaces.NotImplementedException;
import com.youzan.sdk.model.goods.GoodsDetailModel;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class GoodsItemQuery extends b<GoodsDetailModel> {
    /* access modifiers changed from: protected */
    @NonNull
    public Class<GoodsDetailModel> getModel() {
        return GoodsDetailModel.class;
    }

    /* access modifiers changed from: protected */
    @NonNull
    public String attachTo() {
        return "appsdk.item.get";
    }

    /* access modifiers changed from: protected */
    /* renamed from: ˊ reason: contains not printable characters */
    public GoodsDetailModel onParse(@NonNull JSONObject data) throws NotImplementedException, JSONException {
        return new GoodsDetailModel(data.optJSONObject("item"));
    }
}
