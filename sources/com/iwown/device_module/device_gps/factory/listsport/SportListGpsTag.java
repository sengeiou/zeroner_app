package com.iwown.device_module.device_gps.factory.listsport;

import com.iwown.data_link.data.CopySportGps;
import com.iwown.device_module.common.sql.TB_sport_gps_segment;
import com.socks.library.KLog;
import java.util.LinkedList;
import java.util.List;
import org.litepal.crud.DataSupport;

public class SportListGpsTag extends SportListAllExecutor {
    public List<CopySportGps> getCopySportGpsList(long uid, long startTime, int count, int sportType) {
        List<CopySportGps> copySportGpsList = new LinkedList<>();
        List<TB_sport_gps_segment> gps_segmentList = DataSupport.where("uid=? and start_time<? and sport_type=?", uid + "", startTime + "", sportType + "").order("start_time desc").find(TB_sport_gps_segment.class);
        if (gps_segmentList != null && gps_segmentList.size() > 0) {
            List<TB_sport_gps_segment> gps_segments = new LinkedList<>();
            if (gps_segments.size() <= count) {
                gps_segments.addAll(gps_segmentList);
            } else {
                gps_segments.addAll(gps_segmentList.subList(0, count));
            }
            KLog.e("no2855--> TB_sport_gps_segment size: " + gps_segments.size());
            for (TB_sport_gps_segment gps_segment : gps_segments) {
                CopySportGps copySportGps = new CopySportGps();
                copySportGps.setUid(uid);
                copySportGps.setData_from(gps_segment.getData_from());
                copySportGps.setDuration(gps_segment.getDuration());
                copySportGps.setCalorie(gps_segment.getCalorie());
                copySportGps.setDistance(gps_segment.getDistance());
                copySportGps.setStep(gps_segment.getStep());
                copySportGps.setSport_type(gps_segment.getSport_type());
                copySportGps.setSource_type(gps_segment.getSource_type());
                copySportGps.setEnd_time(gps_segment.getEnd_time());
                copySportGps.setStart_time(gps_segment.getStart_time());
                copySportGps.setGps_url(gps_segment.getGps_url());
                copySportGps.setHeart_url(gps_segment.getHeart_url());
                copySportGps.setR1_url(gps_segment.getR1_url());
                copySportGps.setUpload(gps_segment.getUpload());
                copySportGpsList.add(copySportGps);
            }
        }
        return copySportGpsList;
    }
}
