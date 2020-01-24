package com.iwown.sport_module.contract;

import com.iwown.data_link.data.CopySportGps;
import com.iwown.data_link.heart.HeartData;
import com.iwown.sport_module.map.LongitudeAndLatitude;
import com.iwown.sport_module.pojo.DataFragmentBean;
import com.iwown.sport_module.pojo.DiagramsData;
import com.iwown.sport_module.pojo.MapHealthyData;
import com.iwown.sport_module.pojo.R1DataBean;
import java.util.List;

public class SportAnalyseContract {

    public interface GpsPresenter {
        void getDeviceLocation(long j, String str, long j2, long j3, String str2);

        void getPhoneLocation(long j, int i, String str, long j2, long j3, String str2);

        void getWatch62Location(long j, String str, long j2, long j3, String str2);
    }

    public interface Presenter {
        void initCopySportGps(CopySportGps copySportGps, boolean z, boolean z2);
    }

    public interface View {
        void onDiagramArrive(DiagramsData diagramsData);

        void onHeartDataArrive(HeartData heartData);

        void onMapHealthDataArrive(MapHealthyData mapHealthyData);

        void onPaceChartBeansArrive(List<DataFragmentBean> list);

        void onR1Data(R1DataBean r1DataBean);

        void refreshMapView(List<LongitudeAndLatitude> list);
    }
}
