package com.iwown.device_module.interactive_service;

import android.content.Context;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.iwown.data_link.walk_29_data.Content;
import com.iwown.data_link.walk_29_data.IWalkService;
import com.iwown.data_link.walk_29_data.V3_walk;
import com.iwown.device_module.common.sql.TB_v3_walk;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.json.JsonTool;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.List;
import org.litepal.crud.DataSupport;

@Route(path = "/device/walk_service")
public class WalkService implements IWalkService {
    public String get29Walk(long uid, String date, String dataFrom) {
        List<TB_v3_walk> list = DataSupport.where("uid=? and date=? and data_from=?", String.valueOf(uid), date, dataFrom + "").order("step desc").find(TB_v3_walk.class);
        if (list.size() > 0) {
            return JsonTool.toJson(list.get(0));
        }
        return JsonTool.toJson(new TB_v3_walk());
    }

    public List<V3_walk> query29UpData(long uid) {
        List<TB_v3_walk> walks = DataSupport.where("uid=? and _uploaded=?", String.valueOf(uid), "0").find(TB_v3_walk.class);
        KLog.e("licl", "licl:" + JsonTool.toJson(walks));
        if (walks == null || walks.size() == 0) {
            return null;
        }
        List<V3_walk> strings = new ArrayList<>();
        for (TB_v3_walk walk : walks) {
            strings.add(JsonTool.fromJson(JsonTool.toJson(walk), V3_walk.class));
        }
        return strings;
    }

    public void changeUpFlag(long uid) {
        TB_v3_walk tb_walk = new TB_v3_walk();
        tb_walk.set_uploaded(1);
        tb_walk.updateAll("uid=? and _uploaded=?", uid + "", "0");
    }

    public void save29DataFromHistory(List<Content> list) {
        if (list != null && list.size() != 0) {
            for (Content content : list) {
                TB_v3_walk walk = new TB_v3_walk();
                walk.setUid(content.getUid());
                walk.setCalorie(content.getCalorie());
                walk.setData_from(content.getData_from());
                walk.setDistance((float) content.getWalk_distance());
                walk.setStep(content.getSteps());
                DateUtil dateUtil = new DateUtil(content.getRecord_date(), true);
                if (!dateUtil.isToday()) {
                    walk.setDate(dateUtil.getSyyyyMMddDate());
                    walk.setRecord_date(content.getRecord_date());
                    walk.set_uploaded(1);
                    walk.saveOrUpdate("uid=? and record_date=?", content.getUid() + "", content.getRecord_date() + "");
                }
            }
        }
    }

    public V3_walk get29Walk(long uid, long long_unix_record_date) {
        return (V3_walk) JsonTool.fromJson(JsonTool.toJson((TB_v3_walk) DataSupport.where("uid=? and record_date=?", uid + "", long_unix_record_date + "").findFirst(TB_v3_walk.class)), V3_walk.class);
    }

    public V3_walk get29WalkByDataFrom(long uid, long long_unix_record_date, String data_from) {
        return (V3_walk) JsonTool.fromJson(JsonTool.toJson((TB_v3_walk) DataSupport.where("uid=? and record_date=? and data_from=?", uid + "", long_unix_record_date + "", data_from + "").findFirst(TB_v3_walk.class)), V3_walk.class);
    }

    public void init(Context context) {
    }
}
