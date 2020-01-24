package com.iwown.data_link.walk_29_data;

import com.iwown.data_link.base.RetCode;
import java.util.ArrayList;
import java.util.List;

public class WalkingDownCode extends RetCode {
    List<Content> content = new ArrayList();

    public List<Content> getContent() {
        return this.content;
    }

    public void setContent(List<Content> content2) {
        this.content = content2;
    }
}
