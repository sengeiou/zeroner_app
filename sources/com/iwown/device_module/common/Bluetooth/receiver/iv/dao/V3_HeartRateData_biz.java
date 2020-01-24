package com.iwown.device_module.common.Bluetooth.receiver.iv.dao;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.google.gson.Gson;
import com.iwown.device_module.common.Bluetooth.receiver.iv.bean.HeartRateDetial;
import com.iwown.device_module.common.sql.heart.TB_heartrate_data;
import java.util.ArrayList;
import java.util.List;
import org.litepal.crud.DataSupport;

public class V3_HeartRateData_biz {
    public int query_data_exist(String uid, int year, int month, int day) {
        try {
            return DataSupport.where(" uid=? AND year=? and month=? and day=? ", uid, year + "", month + "", day + "").count(TB_heartrate_data.class);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return 0;
        }
    }

    public List<HeartRateDetial> query_heartrate_detial(String uid, int year, int month, int day) {
        List<HeartRateDetial> rateDetials = new ArrayList<>();
        Gson gson = new Gson();
        try {
            List<TB_heartrate_data> list = DataSupport.where("uid=? and year=? and month=? and day=? ", uid, year + "", month + "", day + "").find(TB_heartrate_data.class);
            if (list.size() > 0) {
                for (TB_heartrate_data data : list) {
                    rateDetials.add((HeartRateDetial) gson.fromJson(data.getDetail_data(), HeartRateDetial.class));
                }
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
        return rateDetials;
    }

    public List<TB_heartrate_data> queryUpload(long uid) {
        return DataSupport.where("uid=? and _uploaded=? and start_time>?", uid + "", "0", "0").find(TB_heartrate_data.class);
    }
}
