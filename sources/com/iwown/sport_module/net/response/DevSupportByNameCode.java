package com.iwown.sport_module.net.response;

import com.iwown.data_link.base.RetCode;
import java.util.List;

public class DevSupportByNameCode extends RetCode {
    private List<SupportsByNameItem> data = null;

    public List<SupportsByNameItem> getList() {
        return this.data;
    }

    public void setList(List<SupportsByNameItem> list) {
        this.data = list;
    }
}
