package com.iwown.data_link.sleep_data;

import com.iwown.data_link.base.RetCode;
import java.util.List;

public class SleepDownCode extends RetCode {
    private List<SleepDownData1> content;

    public List<SleepDownData1> getContent() {
        return this.content;
    }

    public void setContent(List<SleepDownData1> content2) {
        this.content = content2;
    }

    public String toString() {
        return "SleepDownCode{content=" + this.content + '}';
    }
}
