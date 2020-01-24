package com.iwown.device_module.common.Bluetooth.sync.iv;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.util.SparseIntArray;
import com.iwown.ble_module.iwown.bluetooth.ZeronerBle;
import com.iwown.ble_module.iwown.cmd.ZeronerSendBluetoothCmdImpl;
import com.iwown.ble_module.iwown.task.DataBean;
import com.iwown.ble_module.iwown.task.ZeronerBackgroundThreadManager;
import com.iwown.data_link.eventbus.HealthDataEventBus;
import com.iwown.data_link.eventbus.SyncDataEvent;
import com.iwown.device_module.DeviceInitUtils;
import com.iwown.device_module.common.BaseActionUtils.FirmwareAction;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.lib_common.log.L;
import com.socks.library.KLog;
import org.greenrobot.eventbus.EventBus;

public class SyncData {
    private static final String END_ADD_28 = "end_add_28";
    private static final String END_ADD_29 = "end_add_29";
    private static final String END_ADD_51 = "end_add_51";
    private static final String END_ADD_53 = "end_add_53";
    private static final String START_ADD_28 = "start_add_28";
    private static final String START_ADD_29 = "start_add_29";
    private static final String START_ADD_51 = "start_add_51";
    private static final String START_ADD_53 = "start_add_53";
    private static final String SYNC_DATA = "sync_data";
    public static final int TYPE_28 = 1;
    public static final int TYPE_29 = 2;
    public static final int TYPE_51 = 4;
    public static final int TYPE_53 = 8;
    public static final Application context = DeviceInitUtils.getInstance().getMyApplication();
    private static SyncData instance;
    private static Handler mHandler = new Handler(Looper.getMainLooper());
    private int endAdd28;
    private int endAdd29;
    private int endAdd51;
    private int endAdd53;
    private boolean hasData28 = true;
    private boolean hasData29 = true;
    private boolean hasData51 = true;
    private boolean hasData53 = true;
    private int mAllAdd = 0;
    private int mDAll;
    private boolean mIsSyncDataInfo;
    /* access modifiers changed from: private */
    public int mNowType;
    /* access modifiers changed from: private */
    public int mSync29DataCount;
    private Runnable mSync29DataRunnable = new Runnable() {
        public void run() {
            SyncData.this.mSync29DataCount = SyncData.this.mSync29DataCount + 1;
            if (SyncData.this.mSync29DataCount > 2) {
                SyncData.this.mSync29DataCount = 0;
                SyncData.this.stopSyncData(2);
                return;
            }
            SyncData.this.sync29Data();
        }
    };
    private Runnable mSyncDataRunnable = new Runnable() {
        public void run() {
            SyncData.this.syncData(2);
            SyncData.this.syncData();
        }
    };
    private SparseIntArray map28 = new SparseIntArray();
    private SparseIntArray map51 = new SparseIntArray();
    private SparseIntArray map53 = new SparseIntArray();
    private int nowAdd28;
    private int nowAdd29;
    private int nowAdd51;
    private int nowAdd53;
    private boolean posted28Start = false;
    private boolean posted28Stop = false;
    private boolean posted29Start = false;
    private boolean posted29Stop = false;
    private boolean posted51Start = false;
    private boolean posted51Stop = false;
    private boolean posted53Start = false;
    private boolean posted53Stop = false;
    private int range28;
    private int range51;
    private int range53;
    private int starAdd28;
    private int starAdd29;
    private int starAdd51;
    private int starAdd53;
    private Runnable stopSyncRunnable = new Runnable() {
        public void run() {
            SyncData.this.stopSyncData(SyncData.this.mNowType);
            SyncData.this.syncData();
        }
    };

    public int getRange53() {
        return this.range53;
    }

    public void setRange53(int range532) {
        this.range53 = range532;
    }

    public int getRange28() {
        return this.range28;
    }

    public void setRange28(int range282) {
        this.range28 = range282;
    }

    public int getRange51() {
        return this.range51;
    }

    public void setRange51(int range512) {
        this.range51 = range512;
    }

    public boolean isSyncDataInfo() {
        return this.mIsSyncDataInfo;
    }

    public void setIsSyncDataInfo(boolean isSyncDataInfo) {
        this.mIsSyncDataInfo = isSyncDataInfo;
    }

    public int getNowAdd53() {
        return this.nowAdd53;
    }

    public void setNowAdd53(int nowAdd532, boolean isOver) {
        if (this.mIsSyncDataInfo) {
            this.nowAdd53 = nowAdd532;
            if (this.map53.indexOfKey(nowAdd532) > -1) {
                remove(this.map53, nowAdd532);
                if (this.map53.size() == 0 || isOver) {
                    this.hasData53 = false;
                }
            } else if (isOver) {
                this.hasData53 = false;
            }
            EventBus.getDefault().post(new SyncDataEvent((int) getProgress(), false));
            checkProgress(this.hasData53, 8);
        }
    }

    private void remove(SparseIntArray map, int now) {
        map.delete(now);
    }

    public int getNowAdd28() {
        return this.nowAdd28;
    }

    public void setNowAdd28(int nowAdd282, boolean isOver) {
        if (this.mIsSyncDataInfo) {
            this.nowAdd28 = nowAdd282;
            if (this.map28.indexOfKey(nowAdd282) > -1) {
                remove(this.map28, nowAdd282);
                if (this.map28.size() == 0 || isOver) {
                    this.hasData28 = false;
                }
            } else if (isOver) {
                this.hasData28 = false;
            }
            EventBus.getDefault().post(new SyncDataEvent((int) getProgress(), false));
            checkProgress(this.hasData28, 1);
        }
    }

    public int getNowAdd29() {
        return this.nowAdd29;
    }

    public void setNowAdd29(int nowAdd292, boolean isOver) {
        this.nowAdd29 = nowAdd292;
        checkProgress(this.hasData29, 2);
    }

    public int getNowAdd51() {
        return this.nowAdd51;
    }

    public void setNowAdd51(int nowAdd512, boolean isOver) {
        if (this.mIsSyncDataInfo) {
            this.nowAdd51 = nowAdd512;
            if (this.map51.indexOfKey(nowAdd512) > -1) {
                remove(this.map51, nowAdd512);
                if (this.map51.size() == 0 || isOver) {
                    this.hasData51 = false;
                }
            } else if (isOver) {
                this.hasData51 = false;
            }
            EventBus.getDefault().post(new SyncDataEvent((int) getProgress(), false));
            checkProgress(this.hasData51, 4);
        }
    }

    public static SyncData getInstance() {
        if (instance == null) {
            synchronized (SyncData.class) {
                if (instance == null) {
                    instance = new SyncData();
                }
            }
        }
        return instance;
    }

    private SyncData() {
        initData();
    }

    private void initData() {
        SharedPreferences sp = context.getSharedPreferences(SYNC_DATA, 0);
        setStarAdd28(sp.getInt(START_ADD_28, 0));
        setEndAdd28(sp.getInt(END_ADD_28, 0));
        setStarAdd29(sp.getInt(START_ADD_29, 0));
        setEndAdd29(sp.getInt(END_ADD_29, 0));
        setStarAdd51(sp.getInt(START_ADD_51, 0));
        setEndAdd51(sp.getInt(END_ADD_51, 0));
        setStarAdd53(sp.getInt(START_ADD_53, 0));
        setEndAdd53(sp.getInt(END_ADD_53, 0));
    }

    public int getStarAdd28() {
        return this.starAdd28;
    }

    public void setStarAdd28(int starAdd282) {
        this.starAdd28 = starAdd282;
    }

    public int getEndAdd28() {
        return this.endAdd28;
    }

    public void setEndAdd28(int endAdd282) {
        this.endAdd28 = endAdd282;
    }

    public int getStarAdd29() {
        return this.starAdd29;
    }

    public void setStarAdd29(int starAdd292) {
        this.starAdd29 = starAdd292;
    }

    public int getEndAdd29() {
        return this.endAdd29;
    }

    public void setEndAdd29(int endAdd292) {
        this.endAdd29 = endAdd292;
    }

    public int getStarAdd51() {
        return this.starAdd51;
    }

    public void setStarAdd51(int starAdd512) {
        this.starAdd51 = starAdd512;
    }

    public int getEndAdd51() {
        return this.endAdd51;
    }

    public void setEndAdd51(int endAdd512) {
        this.endAdd51 = endAdd512;
    }

    public int getStarAdd53() {
        return this.starAdd53;
    }

    public void setStarAdd53(int starAdd532) {
        this.starAdd53 = starAdd532;
    }

    public int getEndAdd53() {
        return this.endAdd53;
    }

    public void setEndAdd53(int endAdd532) {
        this.endAdd53 = endAdd532;
    }

    public void initMap() {
        boolean z;
        boolean z2 = true;
        createMap(this.map28, this.starAdd28, this.endAdd28, this.range28);
        createMap(this.map51, this.starAdd51, this.endAdd51, this.range51);
        createMap(this.map53, this.starAdd53, this.endAdd53, this.range53);
        this.hasData28 = this.starAdd28 != this.endAdd28;
        this.hasData28 = true;
        if (this.starAdd51 != this.endAdd51) {
            z = true;
        } else {
            z = false;
        }
        this.hasData51 = z;
        if (this.starAdd53 == this.endAdd53) {
            z2 = false;
        }
        this.hasData53 = z2;
        this.mAllAdd = this.map28.size() + this.map51.size() + this.map53.size();
    }

    private void createMap(SparseIntArray map, int start, int end, int range) {
        KLog.d("start : " + start + "  end :  " + end);
        map.clear();
        if (start > end) {
            end += range;
        }
        if (start != end && range != 0) {
            for (int index = start; index <= end; index++) {
                map.put(index % range, index % range);
            }
        }
    }

    public void save() {
        Editor edit = context.getSharedPreferences(SYNC_DATA, 0).edit();
        edit.putInt(START_ADD_28, this.starAdd28);
        edit.putInt(END_ADD_28, this.starAdd28);
        edit.putInt(START_ADD_29, this.starAdd29);
        edit.putInt(END_ADD_29, this.starAdd29);
        edit.putInt(START_ADD_51, this.starAdd51);
        edit.putInt(END_ADD_51, this.starAdd51);
        edit.putInt(START_ADD_53, this.starAdd53);
        edit.putInt(END_ADD_53, this.starAdd53);
        edit.apply();
    }

    public void clear() {
        Editor edit = context.getSharedPreferences(SYNC_DATA, 0).edit();
        edit.clear();
        edit.apply();
        initData();
    }

    private void checkProgress(boolean haveData, int type) {
        this.mDAll = this.mAllAdd - ((this.map28.size() + this.map51.size()) + this.map53.size());
        KLog.d("checkProgress : " + this.mDAll + "  total : " + this.mAllAdd);
        this.mNowType = type;
        if (!haveData) {
            stopSyncData(type);
            syncData();
            return;
        }
        judgeStopSyncData();
    }

    public void judgeStopSyncData() {
        mHandler.removeCallbacks(this.stopSyncRunnable);
        mHandler.postDelayed(this.stopSyncRunnable, 15000);
    }

    public float getProgress() {
        if (this.mAllAdd <= 0) {
            return 0.0f;
        }
        if (this.mDAll >= this.mAllAdd) {
            L.file("getProgress : " + this.mDAll + "  total : " + this.mAllAdd, 4);
            this.mDAll = this.mAllAdd;
        }
        KLog.i(NotificationCompat.CATEGORY_PROGRESS + ((this.mDAll * 100) / this.mAllAdd));
        return (float) ((this.mDAll * 100) / this.mAllAdd);
    }

    public void syncData() {
        if (!check28() && !check51()) {
            check53();
        }
    }

    private boolean check53() {
        if (!this.hasData53) {
            return false;
        }
        syncData(8);
        return true;
    }

    private boolean check51() {
        if (!this.hasData51) {
            return false;
        }
        syncData(4);
        return true;
    }

    private boolean check28() {
        if (!this.hasData28) {
            return false;
        }
        syncData(1);
        return true;
    }

    public void syncData(int type) {
        L.file("发送开始同步指令 : " + type, 4);
        mHandler.removeCallbacks(this.mSyncDataRunnable);
        if ((type & 1) > 0 && !this.posted28Start) {
            this.posted28Start = true;
            this.posted28Stop = false;
            this.mNowType = type;
            byte[] data4 = ZeronerSendBluetoothCmdImpl.getInstance().setDialydata28(1, true, 0);
            DataBean dataBean = new DataBean();
            dataBean.addData(data4);
            ZeronerBackgroundThreadManager.getInstance().addWriteData(context, dataBean);
        }
        if ((type & 2) > 0) {
            sync29Data();
        }
        if ((type & 4) > 0 && !this.posted51Start) {
            this.posted51Start = true;
            this.posted51Stop = false;
            this.mNowType = type;
            byte[] data12 = ZeronerSendBluetoothCmdImpl.getInstance().syncHeartRateSegmentData(1);
            DataBean dataBean2 = new DataBean();
            dataBean2.addData(data12);
            ZeronerBackgroundThreadManager.getInstance().addWriteData(context, dataBean2);
        }
        if ((type & 8) > 0 && !this.posted53Start) {
            this.posted53Start = true;
            this.posted53Stop = false;
            this.mNowType = type;
            byte[] data11 = ZeronerSendBluetoothCmdImpl.getInstance().syncHeartRateHourData(1);
            DataBean dataBean3 = new DataBean();
            dataBean3.addData(data11);
            ZeronerBackgroundThreadManager.getInstance().addWriteData(context, dataBean3);
        }
        judgeStopSyncData();
    }

    public void stopSyncDataAll() {
        stopSyncData(15);
        mHandler.removeCallbacks(this.stopSyncRunnable);
        mHandler.removeCallbacks(this.mSyncDataRunnable);
    }

    public void stopSyncData(int type) {
        if (type == this.mNowType) {
            mHandler.removeCallbacks(this.stopSyncRunnable);
        }
        L.file("发送停止同步指令 : " + type, 4);
        if ((type & 1) > 0) {
            this.hasData28 = false;
            if (ZeronerBle.getInstance().isConnected() && type != 1 && !this.posted28Stop) {
                this.posted28Stop = true;
                this.posted28Start = false;
                byte[] data4 = ZeronerSendBluetoothCmdImpl.getInstance().setDialydata28(0, true, 0);
                DataBean dataBean = new DataBean();
                dataBean.addData(data4);
                ZeronerBackgroundThreadManager.getInstance().addWriteData(context, dataBean);
            }
        }
        if ((type & 2) > 0) {
            this.hasData29 = false;
            removeSync29DataTimeTask();
            if (ZeronerBle.getInstance().isConnected() && type != 2) {
                byte[] data5 = ZeronerSendBluetoothCmdImpl.getInstance().setDialydata29(0);
                DataBean dataBean2 = new DataBean();
                dataBean2.addData(data5);
                ZeronerBackgroundThreadManager.getInstance().addWriteData(context, dataBean2);
            }
        }
        if ((type & 4) > 0) {
            this.hasData51 = false;
            if (ZeronerBle.getInstance().isConnected() && !this.posted51Stop && !isNotHaveHeart()) {
                this.posted51Stop = true;
                this.posted51Start = false;
                byte[] data12 = ZeronerSendBluetoothCmdImpl.getInstance().syncHeartRateSegmentData(0);
                DataBean dataBean3 = new DataBean();
                dataBean3.addData(data12);
                ZeronerBackgroundThreadManager.getInstance().addWriteData(context, dataBean3);
            }
        }
        if ((type & 8) > 0) {
            this.hasData53 = false;
            if (ZeronerBle.getInstance().isConnected() && !this.posted53Stop && !isNotHaveHeart()) {
                this.posted53Stop = true;
                this.posted53Start = false;
                byte[] data11 = ZeronerSendBluetoothCmdImpl.getInstance().syncHeartRateHourData(0);
                DataBean dataBean4 = new DataBean();
                dataBean4.addData(data11);
                ZeronerBackgroundThreadManager.getInstance().addWriteData(context, dataBean4);
            }
        }
        if (!this.hasData28 && !this.hasData51 && !this.hasData53 && !this.hasData29) {
            mHandler.removeCallbacks(this.stopSyncRunnable);
            if (ZeronerBle.getInstance().isConnected()) {
                if (this.mIsSyncDataInfo) {
                    HealthDataEventBus.updateAllDataEvent();
                    EventBus.getDefault().post(new SyncDataEvent(0, true));
                }
                if (type < 15) {
                    byte[] data42 = ZeronerSendBluetoothCmdImpl.getInstance().setDialydata28(0, true, 0);
                    DataBean dataBean5 = new DataBean();
                    dataBean5.addData(data42);
                    ZeronerBackgroundThreadManager.getInstance().addWriteData(context, dataBean5);
                    byte[] data52 = ZeronerSendBluetoothCmdImpl.getInstance().setDialydata29(0);
                    DataBean dataBean1 = new DataBean();
                    dataBean1.addData(data52);
                    ZeronerBackgroundThreadManager.getInstance().addWriteData(context, dataBean1);
                }
            }
            this.mIsSyncDataInfo = false;
        }
    }

    /* access modifiers changed from: private */
    public void sync29Data() {
        mHandler.removeCallbacks(this.mSync29DataRunnable);
        if (ZeronerBle.getInstance().isConnected()) {
            byte[] data5 = ZeronerSendBluetoothCmdImpl.getInstance().setDialydata29(1);
            DataBean dataBean1 = new DataBean();
            dataBean1.addData(data5);
            ZeronerBackgroundThreadManager.getInstance().addWriteData(context, dataBean1);
            mHandler.postDelayed(this.mSync29DataRunnable, 10000);
        }
    }

    public void removeSync29DataTimeTask() {
        mHandler.removeCallbacks(this.mSync29DataRunnable);
        this.mSync29DataCount = 0;
    }

    public void syncDataInfo() {
        KLog.e("syncDataInfo");
        Application application = context;
        EventBus.getDefault().post(new SyncDataEvent());
        if (this.mIsSyncDataInfo) {
            L.file("正在同步...", 4);
            return;
        }
        clearMap();
        this.mIsSyncDataInfo = true;
        this.mNowType = 0;
        clearData();
        PrefUtil.save((Context) ContextUtil.app, FirmwareAction.Firmware_Can_Support_08, false);
        byte[] bytes = ZeronerSendBluetoothCmdImpl.getInstance().readDataInfoStored();
        DataBean dataBean1 = new DataBean();
        dataBean1.addData(bytes);
        ZeronerBackgroundThreadManager.getInstance().addWriteData(context, dataBean1);
        judgeSyncData();
        judgeStopSyncData();
    }

    private void clearData() {
        this.hasData28 = true;
        this.hasData29 = true;
        this.posted28Start = false;
        this.posted29Start = false;
        this.posted51Start = false;
        this.posted53Start = false;
        this.posted28Stop = false;
        this.posted29Stop = false;
        this.posted51Stop = false;
        this.posted53Stop = false;
        this.hasData51 = true;
        this.hasData53 = true;
        if (isNotHaveHeart()) {
            this.hasData51 = false;
            this.hasData53 = false;
        }
        this.mAllAdd = 0;
        this.mDAll = 0;
    }

    private boolean isNotHaveHeart() {
        return true;
    }

    private void clearMap() {
        clearMapImpl();
    }

    private void clearMapImpl() {
        this.map28.clear();
        this.map53.clear();
        this.map51.clear();
    }

    private void judgeSyncData() {
        mHandler.removeCallbacks(this.mSyncDataRunnable);
        mHandler.postDelayed(this.mSyncDataRunnable, 10000);
    }
}
