package com.youzan.sdk.query;

import android.support.annotation.NonNull;
import com.youzan.sdk.loader.http.b;
import com.youzan.sdk.loader.http.interfaces.NotImplementedException;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class TradeCartAddQuery extends b<Boolean> {
    /* access modifiers changed from: protected */
    /* renamed from: ˊ reason: contains not printable characters */
    public Boolean onParse(@NonNull JSONObject data) throws NotImplementedException, JSONException {
        return Boolean.valueOf(data.optBoolean("is_success", false));
    }

    /* access modifiers changed from: protected */
    @NonNull
    public String attachTo() {
        return "appsdk.trade.cart.add";
    }

    /* access modifiers changed from: protected */
    @NonNull
    public Class<Boolean> getModel() {
        return Boolean.class;
    }
}
