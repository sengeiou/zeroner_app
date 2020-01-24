package com.youzan.sdk.query;

import android.support.annotation.NonNull;
import com.youzan.sdk.loader.http.b;
import com.youzan.sdk.loader.http.interfaces.NotImplementedException;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class TradeCartCountQuery extends b<Integer> {
    /* access modifiers changed from: protected */
    /* renamed from: ˊ reason: contains not printable characters */
    public Integer onParse(@NonNull JSONObject data) throws NotImplementedException, JSONException {
        return Integer.valueOf(data.optInt("data", 0));
    }

    /* access modifiers changed from: protected */
    @NonNull
    public String attachTo() {
        return "appsdk.trade.cart.count";
    }

    /* access modifiers changed from: protected */
    @NonNull
    public Class<Integer> getModel() {
        return Integer.class;
    }
}
