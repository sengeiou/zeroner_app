package com.youzan.sdk.event;

import android.view.View;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.youzan.sdk.model.goods.GoodsShareModel;
import com.youzan.sdk.web.bridge.Event;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class AbsShareEvent implements Event {
    public abstract void call(View view, GoodsShareModel goodsShareModel);

    public String subscribe() {
        return EventAPI.EVENT_SHARE;
    }

    public final void call(View view, String data) {
        GoodsShareModel model;
        try {
            model = new GoodsShareModel(new JSONObject(data));
        } catch (JSONException e) {
            model = new GoodsShareModel();
            ThrowableExtension.printStackTrace(e);
        }
        call(view, model);
    }
}
