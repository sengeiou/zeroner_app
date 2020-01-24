package com.iwown.sport_module.ui.af.presenter.net;

import android.util.SparseArray;
import com.iwown.data_link.af.AfBean;
import com.iwown.data_link.af.AfResultBean;
import com.iwown.data_link.af.ModuleRouterRRIService;
import com.iwown.data_link.eventbus.HealthDataEventBus;
import com.iwown.device_module.common.network.NetFactory;
import com.iwown.device_module.common.network.callback.MyCallback;
import com.iwown.device_module.common.sql.TB_af_result;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.json.JsonTool;
import com.iwown.sport_module.R;
import com.iwown.sport_module.ui.af.presenter.net.INet.INetListener;
import com.socks.library.KLog;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.litepal.crud.DataSupport;

public class NetImpl implements INet {
    private int[] AfTime = new int[24];
    /* access modifiers changed from: private */
    public int[] afResultStr = {R.string.af_result_by_calc_1, R.string.af_result_by_calc_2, R.string.af_result_by_calc_3};
    private String dataFrom;
    /* access modifiers changed from: private */
    public INetListener listener;
    private int[] testTimes = new int[24];
    private long uid;

    public NetImpl(INetListener listener2, long uid2, String dataFrom2) {
        this.listener = listener2;
        this.uid = uid2;
        this.dataFrom = dataFrom2;
    }

    public void getAfResult(String date, int hour, List<Integer> realData) {
        TB_af_result tb_af_result = (TB_af_result) DataSupport.where("uid=? and data_From=? and record_date = ?", this.uid + "", ModuleRouterRRIService.getInstance().getRRIHasDataFrom(this.uid, date, this.dataFrom), date).findFirst(TB_af_result.class);
        if (tb_af_result != null) {
            int af_ai_result = tb_af_result.getAf_ai_result();
            if (af_ai_result == 3) {
                this.listener.onAfResult(this.afResultStr[2 % this.afResultStr.length]);
            } else if (af_ai_result == 2) {
                this.listener.onAfResult(this.afResultStr[1 % this.afResultStr.length]);
            } else {
                this.listener.onAfResult(this.afResultStr[0 % this.afResultStr.length]);
            }
        } else if (realData == null || realData.size() == 0) {
            KLog.d("数据为空");
        } else {
            getAfByNet(realData, hour, date);
        }
    }

    public void getAndUpdateResult(String date, int hour, List<Integer> realData) {
        String rriHasDataFrom = ModuleRouterRRIService.getInstance().getRRIHasDataFrom(this.uid, date, this.dataFrom);
        KLog.e("AF---计算房颤结果");
        if (((TB_af_result) DataSupport.where("uid=? and data_From=? and record_date = ?", this.uid + "", rriHasDataFrom, date).findFirst(TB_af_result.class)) != null) {
            getAfByNet(realData, hour, date);
        } else if (realData == null || realData.size() == 0) {
            KLog.d("数据为空");
        } else {
            getAfByNet(realData, hour, date);
        }
    }

    public void downloadAfData() {
    }

    public void uploadAfData(String date) {
    }

    public void saveAfResult(String date, SparseArray<List<Integer>> dataByHour) {
        getAndSaveResult(0, date, dataByHour);
    }

    /* access modifiers changed from: private */
    public void getAndSaveResult(final int hour, final String date, final SparseArray<List<Integer>> dataByHour) {
        String rriHasDataFrom = ModuleRouterRRIService.getInstance().getRRIHasDataFrom(this.uid, date, this.dataFrom);
        List<Integer> realData = (List) dataByHour.get(hour);
        if (realData == null) {
            KLog.d("数据为空");
            TB_af_result af_result = new TB_af_result();
            this.testTimes[hour] = hour;
            this.AfTime[hour] = -1;
            af_result.setTest_times(JsonTool.toJson(this.testTimes));
            af_result.setAf_times(JsonTool.toJson(this.AfTime));
            af_result.setUid(this.uid);
            af_result.setData_From(rriHasDataFrom);
            af_result.setRecord_date(date);
            af_result.saveOrUpdate("uid=? and data_From=? and record_date=?", this.uid + "", rriHasDataFrom, date);
            if (hour < 23) {
                getAndSaveResult(hour + 1, date, dataByHour);
            }
            HealthDataEventBus.updateAfEvent();
            return;
        }
        AfBean afResult = new AfBean();
        afResult.setRriList(realData);
        NetFactory.getInstance().getClient(new MyCallback() {
            public void onSuccess(Object o) {
                if (o instanceof AfResultBean) {
                    NetImpl.this.saveAfResult((AfResultBean) o, hour, date);
                    if (hour < 23) {
                        NetImpl.this.getAndSaveResult(hour + 1, date, dataByHour);
                    }
                    HealthDataEventBus.updateAfEvent();
                }
            }

            public void onFail(Throwable e) {
                KLog.e("房颤数据失败");
                HealthDataEventBus.updateAfEvent();
            }
        }).getAfResult(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JsonTool.toJson(afResult)));
    }

    /* access modifiers changed from: private */
    public void saveAfResult(AfResultBean afResultBean, int hour, String date) {
        String rriHasDataFrom = ModuleRouterRRIService.getInstance().getRRIHasDataFrom(this.uid, date, this.dataFrom);
        TB_af_result af_result = new TB_af_result();
        af_result.setUid(this.uid);
        af_result.setData_From(rriHasDataFrom);
        af_result.setRecord_date(date);
        af_result.setAf_ai_result(afResultBean.getAf());
        af_result.setTime(new DateUtil().getUnixTimestamp());
        af_result.setConfidence(afResultBean.getConfidence());
        af_result.saveOrUpdate("uid=? and data_From=? and record_date=?", this.uid + "", this.dataFrom, date);
        KLog.e("af---", "保存成功");
    }

    private void getAfByNet(List<Integer> realData, final int hour, final String date) {
        AfBean afResult = new AfBean();
        afResult.setRriList(realData);
        NetFactory.getInstance().getClient(new MyCallback() {
            public void onSuccess(Object o) {
                if (o instanceof AfResultBean) {
                    AfResultBean afResultBean = (AfResultBean) o;
                    int af = afResultBean.getAf();
                    int confidence = afResultBean.getConfidence();
                    KLog.e("af---", afResultBean);
                    if (confidence == 0 && af == 2) {
                        NetImpl.this.listener.onAfResult(NetImpl.this.afResultStr[1 % NetImpl.this.afResultStr.length]);
                    } else if (af == 1) {
                        NetImpl.this.listener.onAfResult(NetImpl.this.afResultStr[0 % NetImpl.this.afResultStr.length]);
                    } else {
                        NetImpl.this.listener.onAfResult(NetImpl.this.afResultStr[2 % NetImpl.this.afResultStr.length]);
                    }
                    KLog.e("房颤数据" + o);
                    NetImpl.this.saveAfResult(afResultBean, hour, date);
                }
            }

            public void onFail(Throwable e) {
                KLog.e("房颤数据失败");
                NetImpl.this.listener.onAfResult(NetImpl.this.afResultStr[2 % NetImpl.this.afResultStr.length]);
            }
        }).getAfResult(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JsonTool.toJson(afResult)));
    }
}
