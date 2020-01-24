package com.youzan.sdk.model.goods;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GoodsTagListModel {

    /* renamed from: ˊ reason: contains not printable characters */
    private List<GoodsTagModel> f132;

    /* renamed from: ˋ reason: contains not printable characters */
    private int f133;

    public GoodsTagListModel(JSONObject o) throws JSONException {
        if (o != null) {
            JSONArray itemsArray = o.optJSONArray("tags");
            if (itemsArray != null && itemsArray.length() > 0) {
                this.f132 = new ArrayList(itemsArray.length());
                for (int i = 0; i < itemsArray.length(); i++) {
                    this.f132.add(new GoodsTagModel(itemsArray.optJSONObject(i)));
                }
            }
            this.f133 = o.optInt("total_results");
        }
    }

    public List<GoodsTagModel> getItems() {
        return this.f132;
    }

    public void setItems(List<GoodsTagModel> items) {
        this.f132 = items;
    }

    public int getTotalResults() {
        return this.f133;
    }

    public void setTotalResults(int totalResults) {
        this.f133 = totalResults;
    }
}
