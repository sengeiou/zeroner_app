package com.iwown.sport_module.net.response;

import com.iwown.data_link.base.RetCode;
import com.iwown.sport_module.gps.data.GpsUpTotal;
import java.util.List;

public class GpsDownCode extends RetCode {
    private List<GpsUpTotal> content;

    public List<GpsUpTotal> getContent() {
        return this.content;
    }

    public void setContent(List<GpsUpTotal> content2) {
        this.content = content2;
    }
}
