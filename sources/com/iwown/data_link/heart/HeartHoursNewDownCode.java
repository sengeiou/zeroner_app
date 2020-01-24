package com.iwown.data_link.heart;

import com.iwown.data_link.base.RetCode;
import java.util.List;

public class HeartHoursNewDownCode extends RetCode {
    private List<HeartHoursDownCode> content;

    public List<HeartHoursDownCode> getContent() {
        return this.content;
    }

    public void setContent(List<HeartHoursDownCode> content2) {
        this.content = content2;
    }
}
