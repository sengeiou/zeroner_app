package com.iwown.device_module.common.network.data.resp;

import java.util.List;

public class FamilyNoAccountList extends RetCode {
    private List<FamilyNoAccount> data;

    public List<FamilyNoAccount> getData() {
        return this.data;
    }

    public void setData(List<FamilyNoAccount> data2) {
        this.data = data2;
    }
}
