package com.iwown.device_module.device_gps.factory.listsport;

import com.iwown.data_link.data.CopySportGps;
import com.iwown.device_module.common.sql.TB_sport_other;
import java.util.LinkedList;
import java.util.List;
import org.litepal.crud.DataSupport;

public class SportListOtherTag extends SportListAllExecutor {
    public List<CopySportGps> getCopySportGpsList(long uid, long startTime, int count, int sportType) {
        List<CopySportGps> copySportGpsList = new LinkedList<>();
        List<TB_sport_other> gps_segmentList = DataSupport.where("uid=? and start_time<?", uid + "", startTime + "").order("start_time desc").find(TB_sport_other.class);
        if (gps_segmentList != null && gps_segmentList.size() > 0) {
            List<TB_sport_other> gps_segments = new LinkedList<>();
            if (gps_segments.size() <= count) {
                gps_segments.addAll(gps_segmentList);
            } else {
                gps_segments.addAll(gps_segmentList.subList(0, count));
            }
            for (TB_sport_other gps_segment : gps_segments) {
                CopySportGps copySportGps = new CopySportGps();
                copySportGps.setUid(uid);
                copySportGps.setData_from(gps_segment.getData_from());
                copySportGps.setDuration(gps_segment.getDuration());
                copySportGps.setCalorie(gps_segment.getCalorie());
                copySportGps.setSport_type(gps_segment.getSport_type());
                copySportGps.setEnd_time(gps_segment.getEnd_time());
                copySportGps.setStart_time(gps_segment.getStart_time());
                copySportGps.setHeart_url(gps_segment.getHeart_url());
                copySportGps.setDone_times(gps_segment.getDone_times());
                copySportGps.setUpload(gps_segment.getUpload_type());
                copySportGpsList.add(copySportGps);
            }
        }
        return copySportGpsList;
    }
}
