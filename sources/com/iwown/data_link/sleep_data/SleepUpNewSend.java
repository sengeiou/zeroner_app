package com.iwown.data_link.sleep_data;

import java.util.List;

public class SleepUpNewSend {
    private List<SleepDownData1> content;
    private long uid;

    public List<SleepDownData1> getContent() {
        return this.content;
    }

    public void setContent(List<SleepDownData1> content2) {
        this.content = content2;
    }

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }
}
