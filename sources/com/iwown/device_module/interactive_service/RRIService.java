package com.iwown.device_module.interactive_service;

import android.content.Context;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.iwown.data_link.af.AfContenBean;
import com.iwown.data_link.af.IRRIService;
import com.iwown.device_module.common.sql.TB_rri_data;
import com.iwown.device_module.common.sql.TB_rri_index_table;
import com.iwown.lib_common.date.DateUtil;
import java.util.LinkedHashMap;
import java.util.Map;
import org.litepal.crud.DataSupport;

@Route(path = "/device/rri_service")
public class RRIService implements IRRIService {
    public boolean hasRRIData(long uid) {
        if (DataSupport.where("uid=?", uid + "").count(TB_rri_data.class) > 0) {
            return true;
        }
        return false;
    }

    public Map<String, AfContenBean> getCalendarMap(long uid, String dataFrom) {
        Map<String, AfContenBean> map = new LinkedHashMap<>();
        for (TB_rri_data tb_rri_data1 : DataSupport.where("uid=?", uid + "").find(TB_rri_data.class)) {
            int timeStamp = tb_rri_data1.getTimeStamp();
            String s = new DateUtil((long) timeStamp, true).getyyyyMMddDate();
            AfContenBean bean = new AfContenBean();
            bean.setUnix_time(timeStamp);
            map.put(s, bean);
        }
        return map;
    }

    public String getRRIDataFrom(long uid, String dataFrom) {
        TB_rri_index_table index_table = (TB_rri_index_table) DataSupport.where("uid=?", uid + "").findFirst(TB_rri_index_table.class);
        if (index_table == null) {
            return dataFrom;
        }
        TB_rri_index_table index_table1 = (TB_rri_index_table) DataSupport.where("uid=? and dataFrom=?", uid + "", dataFrom).findFirst(TB_rri_index_table.class);
        if (index_table1 == null) {
            return index_table.getDataFrom();
        }
        return index_table1.getDataFrom();
    }

    public String getRRIHasDataFrom(long uid, String date, String dataFrom) {
        TB_rri_index_table index_table = (TB_rri_index_table) DataSupport.where("uid=? and data_ymd=?", uid + "", date).findFirst(TB_rri_index_table.class);
        if (index_table == null) {
            return dataFrom;
        }
        TB_rri_index_table index_table1 = (TB_rri_index_table) DataSupport.where("uid=? and dataFrom=? and data_ymd=?", uid + "", dataFrom, date).findFirst(TB_rri_index_table.class);
        if (index_table1 == null) {
            return index_table.getDataFrom();
        }
        return index_table1.getDataFrom();
    }

    public void init(Context context) {
    }
}
