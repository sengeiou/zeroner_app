package com.iwown.device_module.device_gps.factory.onesport;

import com.iwown.data_link.data.CopySportGps;
import com.iwown.device_module.common.sql.TB_sport_swim;
import org.litepal.crud.DataSupport;

public class SportSwimTag extends SportAllExecutor {
    public CopySportGps getCopySportGps(long uid, long startTime, String dataFrom, int sportType) {
        TB_sport_swim gps_segment = (TB_sport_swim) DataSupport.where("uid=? and start_time=? and data_from=?", uid + "", startTime + "", dataFrom).findFirst(TB_sport_swim.class);
        if (gps_segment == null) {
            return new CopySportGps();
        }
        CopySportGps copySportGps = new CopySportGps();
        copySportGps.setUid(uid);
        copySportGps.setData_from(gps_segment.getData_from());
        copySportGps.setDuration(gps_segment.getDuration());
        copySportGps.setCalorie(gps_segment.getCalorie());
        copySportGps.setSport_type(gps_segment.getSport_type());
        copySportGps.setEnd_time(gps_segment.getEnd_time());
        copySportGps.setStart_time(gps_segment.getStart_time());
        copySportGps.setStep(gps_segment.getDone_times());
        copySportGps.setDistance(gps_segment.getDistance());
        copySportGps.setDone_times(gps_segment.getLaps());
        copySportGps.setHeart_url(gps_segment.getHeart_url());
        return copySportGps;
    }
}
