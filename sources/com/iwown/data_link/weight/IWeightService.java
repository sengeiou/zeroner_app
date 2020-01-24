package com.iwown.data_link.weight;

import com.alibaba.android.arouter.facade.template.IProvider;
import java.util.List;

public interface IWeightService extends IProvider {
    List<ScaleBodyFat> getLocalDataSizeDay(long j, int i);

    void getNetWeightUsers(long j);

    int getRawWeightCounts(String str);

    String getScaleMac(long j);

    List<WeightUser> getWeightUsers(long j);

    void saveNetRWWeight(WifiScaleRWResp wifiScaleRWResp);

    void saveNetWeight(ScaleDataResp scaleDataResp);

    void saveS2WifiConfig(long j, String str, String str2);

    void updateMac(long j, String str);
}
