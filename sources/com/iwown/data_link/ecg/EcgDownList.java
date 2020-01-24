package com.iwown.data_link.ecg;

import com.iwown.data_link.base.NggReturnCode;
import java.util.ArrayList;

public class EcgDownList extends NggReturnCode {
    ArrayList<EcgDownLoadNet> Data;

    public ArrayList<EcgDownLoadNet> getData() {
        return this.Data;
    }

    public void setData(ArrayList<EcgDownLoadNet> data) {
        this.Data = data;
    }
}
