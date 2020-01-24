package com.iwown.data_link.weight;

import com.iwown.data_link.base.RetCode;
import java.util.List;

public class WifiScaleRWResp extends RetCode {
    List<WeightData> data;

    public List<WeightData> getData() {
        return this.data;
    }

    public void setData(List<WeightData> data2) {
        this.data = data2;
    }
}
