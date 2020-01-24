package com.iwown.device_module.device_gps.factory.onesport;

import com.iwown.data_link.data.CopySportGps;
import com.iwown.device_module.common.sql.TB_sport_other;
import org.litepal.crud.DataSupport;

public class SportOtherTag extends SportAllExecutor {
    public CopySportGps getCopySportGps(long uid, long startTime, String dataFrom, int sportType) {
        TB_sport_other gps_segment = (TB_sport_other) DataSupport.where("uid=? and start_time=? and data_from=?", uid + "", startTime + "", dataFrom).findFirst(TB_sport_other.class);
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
        copySportGps.setHeart_url(gps_segment.getHeart_url());
        copySportGps.setDone_times(gps_segment.getDone_times());
        return copySportGps;
    }
}
