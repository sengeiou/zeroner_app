package com.youzan.sdk.model.reviews;

import org.json.JSONException;
import org.json.JSONObject;

public class PaginatorModel {

    /* renamed from: ˊ reason: contains not printable characters */
    private int f142;

    /* renamed from: ˋ reason: contains not printable characters */
    private int f143;

    /* renamed from: ˎ reason: contains not printable characters */
    private int f144;

    public PaginatorModel(JSONObject o) throws JSONException {
        if (o != null) {
            this.f142 = o.optInt("pageSize");
            this.f143 = o.optInt("page");
            this.f144 = o.optInt("totalCount");
        }
    }

    public int getPage() {
        return this.f143;
    }

    public int getPageSize() {
        return this.f142;
    }

    public int getTotalCount() {
        return this.f144;
    }
}
