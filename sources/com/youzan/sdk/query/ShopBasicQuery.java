package com.youzan.sdk.query;

import android.support.annotation.NonNull;
import com.youzan.sdk.loader.http.b;
import com.youzan.sdk.model.shop.ShopBasicModel;

public abstract class ShopBasicQuery extends b<ShopBasicModel> {
    /* access modifiers changed from: protected */
    @NonNull
    public Class<ShopBasicModel> getModel() {
        return ShopBasicModel.class;
    }

    /* access modifiers changed from: protected */
    @NonNull
    public String attachTo() {
        return "appsdk.shop.basic.get";
    }
}
