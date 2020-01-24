package com.iwown.device_module.common.Bluetooth.sync.mtk;

import android.os.Handler;
import android.os.Looper;
import com.iwown.ble_module.iwown.task.ZeronerBackgroundThreadManager;
import com.iwown.ble_module.mtk_ble.cmd.MtkCmdAssembler;
import com.iwown.ble_module.mtk_ble.task.MtkBackgroundThreadManager;
import com.iwown.data_link.eventbus.SyncDataEvent;
import com.iwown.data_link.sport_data.R1DataBean;
import com.iwown.device_module.common.Bluetooth.receiver.mtk.utils.MtkDataToServer;
import com.iwown.device_module.common.Bluetooth.receiver.mtk.utils.R1ConvertHeartHandler;
import com.iwown.device_module.common.sql.headset.DataIndex_68;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.lib_common.log.L;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.litepal.crud.DataSupport;

public class MTKHeadSetSync {
    private static MTKHeadSetSync instance;
    private static Handler mHandler = new Handler(Looper.getMainLooper());
    private int avgPerDay = 0;
    private int counter;
    private List<DataIndex_68> dbIndexList;
    private long gpsSportTime = 0;
    private final int gpsTimeOut = 360000;
    private long lastTime = 0;
    private boolean mIsSyncDataInfo;
    private int seqTotal = 0;
    private int total = 0;

    private class MyThread extends Thread {
        private MyThread() {
        }

        public void run() {
            super.run();
            MtkDataToServer.upload68ToServer();
        }
    }

    private MTKHeadSetSync() {
    }

    public static MTKHeadSetSync getInstance() {
        if (instance == null) {
            instance = new MTKHeadSetSync();
        }
        return instance;
    }

    public void syncDataInfo() {
        MtkBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, MtkCmdAssembler.getInstance().setHeartBeat(0));
        EventBus.getDefault().post(new SyncDataEvent());
        if (this.mIsSyncDataInfo) {
            KLog.e("---*正在同步...:" + String.valueOf(this.mIsSyncDataInfo));
            L.file("正在同步...", 4);
        } else if (isGpsSporting()) {
            KLog.e("---*正在gps运动同步");
            L.file("正在gps运动同步...", 4);
        } else {
            this.mIsSyncDataInfo = true;
            EventBus.getDefault().post(new SyncDataEvent(0, false));
            KLog.e("---*start sync r1 data");
            startSyncData68();
        }
    }

    private void startSyncData68() {
        this.lastTime = System.currentTimeMillis() / 1000;
        Calendar cal = Calendar.getInstance();
        cal.add(6, -60);
        DataSupport.deleteAll(DataIndex_68.class, "year = ? and month = ? and day=?", String.valueOf(cal.get(1)), String.valueOf(cal.get(2) + 1), String.valueOf(cal.get(5)));
        ZeronerBackgroundThreadManager.getInstance().wakeUp();
        mHandler.postDelayed(new Runnable() {
            public void run() {
                MtkCmdAssembler.getInstance().getIndexTableAccordingType(104);
                KLog.e("---*send cmd to sync 68 index");
            }
        }, 1000);
    }

    public void syncDetailData(List<DataIndex_68> indexList) {
        this.lastTime = System.currentTimeMillis() / 1000;
        if (indexList == null || indexList.size() <= 0) {
            EventBus.getDefault().post(new SyncDataEvent(100, true, 1, 1));
            this.mIsSyncDataInfo = false;
            this.counter = 0;
            return;
        }
        this.total = indexList.size();
        Collections.sort(indexList);
        this.dbIndexList = indexList;
        for (DataIndex_68 dbIndex : indexList) {
            this.seqTotal += dbIndex.getEnd_idx() - dbIndex.getStart_idx();
        }
        this.avgPerDay = this.seqTotal / this.total;
        sendCmdByIndex(this.dbIndexList, 0);
    }

    /* access modifiers changed from: private */
    public void sendCmdByIndex(final List<DataIndex_68> indexList, int position) {
        if (position < indexList.size()) {
            DataIndex_68 index = (DataIndex_68) indexList.get(position);
            MtkCmdAssembler.getInstance().getDetailDataAsIndex(index.getYear(), index.getMonth(), index.getDay(), index.getStart_idx(), index.getEnd_idx(), 104);
            KLog.e("---*send cmd to sync 68 detail");
            final int next_position = position + 1;
            if (next_position < indexList.size()) {
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        MTKHeadSetSync.this.sendCmdByIndex(indexList, next_position);
                    }
                }, 3000);
            }
        }
    }

    public void reportProgress() {
        this.lastTime = System.currentTimeMillis() / 1000;
        EventBus.getDefault().post(new SyncDataEvent(100, true, this.dbIndexList.size(), this.dbIndexList.size()));
        this.mIsSyncDataInfo = false;
        this.counter = 0;
        jobAfterSyncFinish();
    }

    public void reportProgress(int count) {
        this.lastTime = System.currentTimeMillis() / 1000;
        if (count >= this.seqTotal) {
            EventBus.getDefault().post(new SyncDataEvent(100, true, this.dbIndexList.size(), this.dbIndexList.size()));
            this.mIsSyncDataInfo = false;
            this.counter = 0;
            jobAfterSyncFinish();
        }
        int dayseq = count / this.avgPerDay;
        int mod = count % this.avgPerDay;
        if (mod == 0) {
            EventBus.getDefault().post(new SyncDataEvent(100, false, this.total, dayseq));
            return;
        }
        int dayseq2 = dayseq + 1;
        if (mod == this.avgPerDay / 2) {
            EventBus.getDefault().post(new SyncDataEvent(50, false, this.total, dayseq2));
        }
    }

    public boolean isSyncDataInfo() {
        if (this.mIsSyncDataInfo && !isTimeOut()) {
            return true;
        }
        return false;
    }

    public int getCounter() {
        return this.counter;
    }

    public void setCounter(int counter2) {
        this.counter = counter2;
    }

    private void jobAfterSyncFinish() {
        KLog.e("---sync job finish");
        R1DataBean r1DataBean = new R1DataBean();
        r1DataBean.setTag("R1TableConvert");
        R1ConvertHeartHandler.to51(r1DataBean);
        List<Integer> years = new ArrayList<>();
        List<Integer> months = new ArrayList<>();
        List<Integer> days = new ArrayList<>();
        for (int i = 0; i < this.dbIndexList.size(); i++) {
            years.add(Integer.valueOf(((DataIndex_68) this.dbIndexList.get(i)).getYear()));
            months.add(Integer.valueOf(((DataIndex_68) this.dbIndexList.get(i)).getMonth()));
            days.add(Integer.valueOf(((DataIndex_68) this.dbIndexList.get(i)).getDay()));
        }
        r1DataBean.setYear(years);
        r1DataBean.setMonth(months);
        r1DataBean.setDay(days);
        EventBus.getDefault().post(r1DataBean);
    }

    public boolean isGpsSporting() {
        return false;
    }

    public void setGpsSportTime(long time) {
        this.gpsSportTime = time;
    }

    public boolean isTimeOut() {
        return (System.currentTimeMillis() / 1000) - this.lastTime >= 30;
    }
}
