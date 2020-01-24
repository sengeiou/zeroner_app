package com.iwown.device_module.common.network.data.resp;

import java.util.List;

public class SportDownCode extends RetCode {
    private List<SportData> content;

    public List<SportData> getContent() {
        return this.content;
    }

    public void setContent(List<SportData> content2) {
        this.content = content2;
    }
}
