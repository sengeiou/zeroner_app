package com.youzan.sdk.web.event;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.youzan.sdk.event.EventAPI;
import com.youzan.sdk.model.goods.GoodsShareModel;
import com.youzan.sdk.web.bridge.IBridgeEnv;
import org.json.JSONException;
import org.json.JSONObject;

@Deprecated
public abstract class ShareDataEvent extends a {
    public abstract void call(IBridgeEnv iBridgeEnv, GoodsShareModel goodsShareModel);

    public String subscribe() {
        return EventAPI.EVENT_SHARE;
    }

    public final void call(IBridgeEnv env, String data) {
        try {
            call(env, new GoodsShareModel(new JSONObject(data)));
        } catch (JSONException e) {
            ThrowableExtension.printStackTrace(e);
        }
    }
}
