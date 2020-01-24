package com.youzan.sdk.query;

import android.support.annotation.NonNull;
import com.youzan.sdk.loader.http.b;
import org.json.JSONObject;

public abstract class TradeCartDeleteQuery extends b<Boolean> {
    /* access modifiers changed from: protected */
    @NonNull
    public Class<Boolean> getModel() {
        return Boolean.class;
    }

    /* access modifiers changed from: protected */
    @NonNull
    public String attachTo() {
        return "appsdk.trade.cart.delete";
    }

    /* access modifiers changed from: protected */
    /* renamed from: ˊ reason: contains not printable characters */
    public Boolean onParse(@NonNull JSONObject data) throws Exception {
        return Boolean.valueOf(data.optBoolean("is_success", false));
    }
}
