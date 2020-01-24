package com.iwown.my_module.feedback.network.response;

import com.iwown.data_link.base.RetCode;
import java.util.List;

public class QuestionCode extends RetCode {
    private List<QuestionData> data;

    public List<QuestionData> getData() {
        return this.data;
    }

    public void setData(List<QuestionData> data2) {
        this.data = data2;
    }
}
