package com.iwown.my_module.model.response;

public class MarketInfoResponse extends ReturnCode {
    private MarketInfo data;

    public MarketInfo getData() {
        return this.data;
    }

    public void setData(MarketInfo data2) {
        this.data = data2;
    }
}
