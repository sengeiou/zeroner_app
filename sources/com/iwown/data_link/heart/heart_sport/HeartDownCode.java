package com.iwown.data_link.heart.heart_sport;

import com.iwown.data_link.base.RetCode;
import java.util.List;

public class HeartDownCode extends RetCode {
    private List<HeartDownData1> content;

    public List<HeartDownData1> getContent() {
        return this.content;
    }

    public void setContent(List<HeartDownData1> content2) {
        this.content = content2;
    }
}
