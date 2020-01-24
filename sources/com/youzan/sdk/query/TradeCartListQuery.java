package com.youzan.sdk.query;

import android.support.annotation.NonNull;
import com.youzan.sdk.loader.http.b;
import com.youzan.sdk.model.trade.TradeCartFormatModel;
import org.json.JSONArray;
import org.json.JSONObject;

public abstract class TradeCartListQuery extends b<TradeCartFormatModel> {
    /* access modifiers changed from: protected */
    @NonNull
    public Class<TradeCartFormatModel> getModel() {
        return TradeCartFormatModel.class;
    }

    /* access modifiers changed from: protected */
    @NonNull
    public String attachTo() {
        return "appsdk.trade.cart.list";
    }

    /* access modifiers changed from: protected */
    /* renamed from: ˊ reason: contains not printable characters */
    public TradeCartFormatModel onParse(@NonNull JSONObject data) throws Exception {
        JSONArray array = data.getJSONArray("data");
        if (array == null || array.length() <= 0) {
            return new TradeCartFormatModel(null);
        }
        return new TradeCartFormatModel(array.getJSONObject(0));
    }
}
