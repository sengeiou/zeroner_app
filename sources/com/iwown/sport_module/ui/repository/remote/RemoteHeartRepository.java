package com.iwown.sport_module.ui.repository.remote;

import android.content.Context;
import com.iwown.data_link.heart.HeartShowData;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.sport_module.net.NetFactory;
import com.iwown.sport_module.net.callback.MyCallback;
import com.iwown.sport_module.ui.repository.DataSource;
import com.iwown.sport_module.ui.repository.DataSource.DataCallBack;
import com.socks.library.KLog;
import java.util.Date;

public class RemoteHeartRepository implements DataSource {
    private static RemoteHeartRepository instance;
    private Context context;

    private RemoteHeartRepository(Context context1) {
        this.context = context1;
    }

    public static RemoteHeartRepository getInsatnce(Context context2) {
        if (instance == null) {
            instance = new RemoteHeartRepository(context2);
        }
        return instance;
    }

    public Context getContext() {
        return this.context;
    }

    public void getHeartByTime(HeartShowData heartShowData, final DataCallBack<Integer> dataCallBack) {
        DateUtil date_month = new DateUtil(DateUtil.getFirstDayMonth(new Date(heartShowData.dateUtil.getTimestamp())), false);
        NetFactory.getInstance().getClient(new MyCallback<Integer>() {
            public void onSuccess(Integer integer) {
                dataCallBack.onResult(integer);
            }

            public void onFail(Throwable e) {
                dataCallBack.onResult(Integer.valueOf(-1));
            }
        }).downHeartHoursData(heartShowData.uid, DateUtil.getDaysOfMonth(new java.sql.Date(heartShowData.dateUtil.getTimestamp())), date_month.getSyyyyMMddDate(), heartShowData.dateUtil);
    }

    public void getHeartSatatus(DateUtil dateUtil, final DataCallBack<Integer> dataCallBack) {
        int daysOfMonth = DateUtil.getDaysOfMonth(new java.sql.Date(dateUtil.getTimestamp()));
        KLog.e("getHeartSatatus Remote");
        NetFactory.getInstance().getClient(new MyCallback<Integer>() {
            public void onSuccess(Integer integer) {
                dataCallBack.onResult(integer);
            }

            public void onFail(Throwable e) {
                dataCallBack.onResult(Integer.valueOf(-1));
            }
        }).downloadHeartStatusByDate(UserConfig.getInstance().getNewUID(), daysOfMonth, dateUtil.getSyyyyMMddDate());
    }

    public void getHeartSports(HeartShowData heartShowData, final DataCallBack<Integer> dataCallBack) {
        DateUtil date_month = new DateUtil(DateUtil.getFirstDayMonth(new Date(heartShowData.dateUtil.getTimestamp())), false);
        NetFactory.getInstance().getClient(new MyCallback<Integer>() {
            public void onSuccess(Integer integer) {
                dataCallBack.onResult(integer);
            }

            public void onFail(Throwable e) {
                dataCallBack.onResult(Integer.valueOf(-1));
            }
        }).heartDownRepo(heartShowData.uid, DateUtil.getDaysOfMonth(new java.sql.Date(heartShowData.dateUtil.getTimestamp())), date_month.getSyyyyMMddDate(), heartShowData.dateUtil);
    }
}
