package com.iwown.device_module.device_gps.factory.onesport;

import com.iwown.data_link.data.CopySportGps;
import com.iwown.device_module.common.sql.TB_sport_gps_segment;
import org.litepal.crud.DataSupport;

public class SportGpsTag extends SportAllExecutor {
    public CopySportGps getCopySportGps(long uid, long startTime, String dataFrom, int sportType) {
        TB_sport_gps_segment gps_segment = (TB_sport_gps_segment) DataSupport.where("uid=? and start_time=? and data_from=? and sport_type=?", uid + "", startTime + "", dataFrom, sportType + "").findFirst(TB_sport_gps_segment.class);
        if (gps_segment == null) {
            return new CopySportGps();
        }
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
        return copySportGps;
    }
}
