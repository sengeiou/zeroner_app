package com.iwown.my_module.healthy.network.response;

import com.iwown.data_link.base.RetCode;

public class AllUserInfoRetCode extends RetCode {
    private AllInfoData data;
    private int data_type;

    public int getData_type() {
        return this.data_type;
    }

    public void setData_type(int data_type2) {
        this.data_type = data_type2;
    }

    public AllInfoData getData() {
        return this.data;
    }

    public void setData(AllInfoData data2) {
        this.data = data2;
    }
}
