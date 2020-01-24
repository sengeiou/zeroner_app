package com.youzan.sdk.query;

import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import com.youzan.sdk.loader.http.b;
import com.youzan.sdk.loader.http.interfaces.NotImplementedException;
import com.youzan.sdk.model.shop.ShopStatusModel;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class ShopStatusQuery extends b<ShopStatusModel> {
    /* access modifiers changed from: protected */
    @NonNull
    public String attachTo() {
        return "appsdk.shop.status.get";
    }

    /* access modifiers changed from: protected */
    @NonNull
    public Class<ShopStatusModel> getModel() {
        return ShopStatusModel.class;
    }

    /* access modifiers changed from: protected */
    /* renamed from: ˊ reason: contains not printable characters */
    public ShopStatusModel onParse(@NonNull JSONObject data) throws NotImplementedException, JSONException {
        return new ShopStatusModel(data.optJSONObject(NotificationCompat.CATEGORY_STATUS));
    }
}
