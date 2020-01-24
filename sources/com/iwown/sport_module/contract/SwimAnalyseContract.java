package com.iwown.sport_module.contract;

import com.iwown.sport_module.pojo.SwimRateData;
import com.iwown.sport_module.pojo.data.SwimHealthyData;

public class SwimAnalyseContract {

    public interface DataCallBack {
        void getRateFromFile();

        void getRateFromNet();
    }

    public interface Presenter {
        void getSwimBaseData(long j, String str, long j2);

        void getSwimRateData(long j, String str, long j2, long j3, String str2);
    }

    public interface View {
        void onBaseDataArrive(SwimHealthyData swimHealthyData);

        void onRateDataArrive(SwimRateData swimRateData);
    }
}
