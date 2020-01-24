package com.iwown.sport_module.contract;

import com.iwown.data_link.data.CopySportGps;
import com.iwown.data_link.heart.HeartData;
import com.iwown.sport_module.map.LongitudeAndLatitude;
import com.iwown.sport_module.pojo.DataFragmentBean;
import com.iwown.sport_module.pojo.DiagramsData;
import com.iwown.sport_module.pojo.MapHealthyData;
import com.iwown.sport_module.pojo.R1DataBean;
import java.util.List;

public interface RunActivityContract {

    public interface MapHealthyDataModel {
        void getDiagramsDataPhone();

        void getDiagramsDataWatch();

        void getDiagramsDataWristGps(long j, long j2, String str, boolean z);

        void getHealthyDataAboutPhone(long j, long j2, long j3, float f, boolean z, int i, String str);

        void getHealthyDataAboutWatch(long j, long j2, long j3, String str, String str2, String str3, int i, boolean z, int i2, String str4, boolean z2);

        void getHealthyDataAboutWrist(long j, long j2, long j3, String str, int i, String str2);

        void getHeartDataWatch();

        void getHeartDataWrist();

        void getPaceChartBeanListPhone();

        void getPaceChartBeanListWatch();

        void getPaceChartBeanListWristGps(long j, long j2, String str);

        void getR1Data(long j, String str, long j2, String str2);

        void getSportHeartData(long j, String str, long j2, int i, String str2);
    }

    public interface Model {
        List<LongitudeAndLatitude> getTrackDataAboutPhone(long j, long j2, int i, String str, String str2, String str3);

        List<LongitudeAndLatitude> getTrackDataAboutWatch(long j, long j2, long j3, String str, String str2, String str3);
    }

    public interface Presenter {
        void getDeviceLocation(long j, String str, long j2, long j3, String str2);

        void getHealthyDataAboutPhone(long j, long j2, long j3, float f, boolean z, int i, String str);

        void getHealthyDataAboutWatch(long j, long j2, long j3, String str, String str2, String str3, int i, boolean z, int i2, String str4);

        void getHealthyDataAboutWrist(long j, long j2, long j3, String str, int i, String str2);

        void getPhoneLocation(long j, int i, String str, long j2, long j3, String str2);

        void getPhoneTrackData(long j, long j2, int i, String str, String str2, String str3);

        void getR1Data(long j, String str, long j2, String str2);

        void getSportHeart(long j, String str, long j2, int i, String str2);

        void getWatchTrackData(long j, long j2, long j3, String str, String str2, String str3);

        void getWristGpsPace(long j, String str, long j2, long j3, boolean z);

        void initCopySportGps(CopySportGps copySportGps, boolean z, boolean z2);

        void initModel(CopySportGps copySportGps, long j, long j2, long j3, String str, String str2, String str3, int i, boolean z);
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
