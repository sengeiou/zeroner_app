package com.iwown.device_module.interactive_service;

import android.content.Context;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.fatigue.FatigueData;
import com.iwown.data_link.fatigue.FatigueNet;
import com.iwown.data_link.fatigue.FatigueRetCode.FatigueDaily;
import com.iwown.data_link.fatigue.FatigueSend;
import com.iwown.data_link.fatigue.FatigueShowData;
import com.iwown.data_link.fatigue.IFatigueService;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.common.sql.TB_fatigue_history;
import com.iwown.device_module.common.utils.JsonUtils;
import com.iwown.lib_common.date.DateUtil;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.List;
import org.litepal.crud.DataSupport;

@Route(path = "/device/fatigue_service")
public class FatigueService implements IFatigueService {
    public void saveFatigueDatas(FatigueDaily data) {
        KLog.e(data);
        for (FatigueNet fatigueNet : data.getDailyData()) {
            int time = fatigueNet.getRecordDate();
            TB_fatigue_history history = new TB_fatigue_history();
            history.setUid(data.getUid());
            history.setData_from(fatigueNet.getData_from());
            history.setDate(String.valueOf(time));
            history.setTime(DateUtil.dateStr2Stamp(String.valueOf(time)));
            history.setDetail(JsonUtils.toJson(fatigueNet.getFatigues()));
            history.setUpload(1);
            history.saveOrUpdate("uid=? and date=? and data_from=?", data.getUid() + "", String.valueOf(time), fatigueNet.getData_from());
        }
    }

    public void updateFatigueDatas(long uid, String data_from, String time) {
        TB_fatigue_history oneFatigue = (TB_fatigue_history) DataSupport.where("uid=? and data_from=? and date=?", uid + "", data_from, time).findFirst(TB_fatigue_history.class);
        if (oneFatigue != null) {
            oneFatigue.setUpload(1);
            oneFatigue.updateAll("uid=? and data_from=? and date=?", uid + "", data_from, time);
        }
    }

    public List<FatigueShowData> getFatigues(long uid, String data_from, long endTime, int pageSize) {
        List<TB_fatigue_history> historys = DataSupport.where("uid=?  and time<=?", uid + "", endTime + "").order("time desc").limit(pageSize).find(TB_fatigue_history.class);
        List<FatigueShowData> fatigueShowDataArrayList = new ArrayList<>();
        for (TB_fatigue_history tb_fatigue_history : historys) {
            FatigueShowData fatigueShowData = new FatigueShowData();
            fatigueShowData.setData_from(data_from);
            fatigueShowData.setTime(tb_fatigue_history.getTime());
            fatigueShowData.setFatigues(tb_fatigue_history.getDetail());
            fatigueShowDataArrayList.add(0, fatigueShowData);
        }
        return fatigueShowDataArrayList;
    }

    public FatigueSend getUnUploadFatigueDatas(long newUID, String device) {
        try {
            List<TB_fatigue_history> tb_fatigue_histories = DataSupport.where("uid=?  and data_from=?", newUID + "", device + "").find(TB_fatigue_history.class);
            FatigueSend fatigueSend = new FatigueSend();
            try {
                List<FatigueNet> fatigueNets = new ArrayList<>();
                for (TB_fatigue_history tb_fatigue_history : tb_fatigue_histories) {
                    FatigueNet fatigueNet = new FatigueNet();
                    fatigueNet.setRecordDate(Integer.parseInt(tb_fatigue_history.getDate()));
                    fatigueNet.setData_from(tb_fatigue_history.getData_from());
                    fatigueNet.setFatigues(JsonUtils.getListJson(tb_fatigue_history.getDetail(), FatigueData.class));
                    fatigueNets.add(fatigueNet);
                }
                fatigueSend.setDailyData(fatigueNets);
                fatigueSend.setUid(newUID);
                return fatigueSend;
            } catch (Exception e) {
                e = e;
                FatigueSend fatigueSend2 = fatigueSend;
                FatigueSend fatigueSend3 = new FatigueSend();
                ThrowableExtension.printStackTrace(e);
                return fatigueSend3;
            }
        } catch (Exception e2) {
            e = e2;
            FatigueSend fatigueSend32 = new FatigueSend();
            ThrowableExtension.printStackTrace(e);
            return fatigueSend32;
        }
    }

    public boolean isMTK() {
        return BluetoothOperation.isMtk();
    }

    public void init(Context context) {
    }
}
