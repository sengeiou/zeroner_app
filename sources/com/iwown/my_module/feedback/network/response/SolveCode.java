package com.iwown.my_module.feedback.network.response;

import com.iwown.data_link.base.RetCode;

public class SolveCode extends RetCode {
    private String data;

    public String getData() {
        return this.data;
    }

    public void setData(String data2) {
        this.data = data2;
    }
}
