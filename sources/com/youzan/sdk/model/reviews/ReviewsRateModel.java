package com.youzan.sdk.model.reviews;

import org.json.JSONException;
import org.json.JSONObject;

public class ReviewsRateModel {

    /* renamed from: ˊ reason: contains not printable characters */
    private int f172;

    /* renamed from: ˋ reason: contains not printable characters */
    private int f173;

    /* renamed from: ˎ reason: contains not printable characters */
    private int f174;

    public ReviewsRateModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f172 = o.optInt("badNum");
            this.f173 = o.optInt("bestNum");
            this.f174 = o.optInt("commonNum");
        }
    }

    public int getBadNum() {
        return this.f172;
    }

    public int getBestNum() {
        return this.f173;
    }

    public int getCommonNum() {
        return this.f174;
    }
}
