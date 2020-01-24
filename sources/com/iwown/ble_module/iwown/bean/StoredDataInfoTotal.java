package com.iwown.ble_module.iwown.bean;

import com.iwown.ble_module.iwown.utils.ByteUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StoredDataInfoTotal {
    private int dataTypeNum;
    private List<StoredDataInfoDetail> infoList = new ArrayList();

    public List<StoredDataInfoDetail> getInfoList() {
        return this.infoList;
    }

    public void setInfoList(List<StoredDataInfoDetail> infoList2) {
        this.infoList = infoList2;
    }

    public int getDataTypeNum() {
        return this.dataTypeNum;
    }

    public void setDataTypeNum(int dataTypeNum2) {
        this.dataTypeNum = dataTypeNum2;
    }

    public void parseData(byte[] datas) {
        if (datas.length >= 4) {
            this.dataTypeNum = (datas.length - 4) / 7;
            this.infoList.clear();
            for (int index = 0; index < this.dataTypeNum; index++) {
                byte[] bytes = Arrays.copyOfRange(datas, (index * 7) + 4, ((index + 1) * 7) + 4);
                StoredDataInfoDetail infoDetail = new StoredDataInfoDetail();
                infoDetail.setType(bytes[0]);
                infoDetail.setMax_range(ByteUtil.bytesToInt(Arrays.copyOfRange(bytes, 1, 3)));
                infoDetail.setStart_index(ByteUtil.bytesToInt(Arrays.copyOfRange(bytes, 3, 5)));
                infoDetail.setEnd_index(ByteUtil.bytesToInt(Arrays.copyOfRange(bytes, 5, 7)));
                this.infoList.add(infoDetail);
            }
        }
    }
}
