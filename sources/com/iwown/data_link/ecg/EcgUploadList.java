package com.iwown.data_link.ecg;

import java.util.List;

public class EcgUploadList {
    List<EcgUploadNet> Data;

    public List<EcgUploadNet> getData() {
        return this.Data;
    }

    public void setData(List<EcgUploadNet> data) {
        this.Data = data;
    }
}
