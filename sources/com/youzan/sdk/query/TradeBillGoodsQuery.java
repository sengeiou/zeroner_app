package com.youzan.sdk.query;

import android.support.annotation.NonNull;
import com.youzan.sdk.loader.http.b;
import com.youzan.sdk.model.trade.TradeBillModel;

public abstract class TradeBillGoodsQuery extends b<TradeBillModel> {
    /* access modifiers changed from: protected */
    @NonNull
    public Class<TradeBillModel> getModel() {
        return TradeBillModel.class;
    }

    /* access modifiers changed from: protected */
    @NonNull
    public String attachTo() {
        return "appsdk.trade.bill.goods.url.get";
    }
}
