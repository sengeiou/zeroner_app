package com.iwown.data_link.sport_data;

import com.iwown.data_link.base.RetCode;
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
