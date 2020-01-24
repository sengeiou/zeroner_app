package com.youzan.sdk.model.reviews;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ReviewsModel {

    /* renamed from: ˊ reason: contains not printable characters */
    private PaginatorModel f170;

    /* renamed from: ˋ reason: contains not printable characters */
    private List<ReviewItemModel> f171;

    public ReviewsModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f170 = new PaginatorModel(o.optJSONObject("paginator"));
            JSONArray arrayObj = o.optJSONArray("items");
            if (arrayObj != null && arrayObj.length() > 0) {
                int length = arrayObj.length();
                this.f171 = new ArrayList(length);
                for (int i = 0; i < length; i++) {
                    this.f171.add(new ReviewItemModel(arrayObj.optJSONObject(i)));
                }
            }
        }
    }

    public List<ReviewItemModel> getItems() {
        return this.f171;
    }

    public PaginatorModel getPaginator() {
        return this.f170;
    }
}
