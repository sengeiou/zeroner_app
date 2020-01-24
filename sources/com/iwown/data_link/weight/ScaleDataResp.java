package com.iwown.data_link.weight;

import com.iwown.data_link.base.RetCode;
import java.util.List;

public class ScaleDataResp extends RetCode {
    private List<ScaleBodyFat> content;

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public List<ScaleBodyFat> getContent() {
        return this.content;
    }

    public void setContent(List<ScaleBodyFat> content2) {
        this.content = content2;
    }
}
