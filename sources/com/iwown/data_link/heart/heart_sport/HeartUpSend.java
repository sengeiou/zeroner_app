package com.iwown.data_link.heart.heart_sport;

import java.util.List;

public class HeartUpSend {
    private List<HeartDownData1> content;
    private long uid;

    public List<HeartDownData1> getContent() {
        return this.content;
    }

    public void setContent(List<HeartDownData1> content2) {
        this.content = content2;
    }

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }
}
