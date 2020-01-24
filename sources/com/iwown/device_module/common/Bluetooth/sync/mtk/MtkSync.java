package com.iwown.device_module.common.Bluetooth.sync.mtk;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.google.gson.Gson;
import com.iwown.ble_module.iwown.task.ZeronerBackgroundThreadManager;
import com.iwown.ble_module.model.Power;
import com.iwown.ble_module.mtk_ble.cmd.MtkCmdAssembler;
import com.iwown.ble_module.mtk_ble.task.MtkBackgroundThreadManager;
import com.iwown.ble_module.utils.ByteUtil;
import com.iwown.data_link.eventbus.EpoEvent;
import com.iwown.data_link.eventbus.SyncDataEvent;
import com.iwown.data_link.eventbus.ViewRefresh;
import com.iwown.device_module.common.BaseActionUtils.FirmwareAction;
import com.iwown.device_module.common.BaseActionUtils.SharedPreferencesAction;
import com.iwown.device_module.common.Bluetooth.receiver.mtk.MtkDataParsePresenter;
import com.iwown.device_module.common.Bluetooth.receiver.mtk.bean.P1SendBleData;
import com.iwown.device_module.common.Bluetooth.receiver.mtk.bean.SeqModel;
import com.iwown.device_module.common.Bluetooth.receiver.mtk.dao.Mtk_DeviceBaseInfoSqlUtil;
import com.iwown.device_module.common.sql.Mtk_DeviceBaseInfo;
import com.iwown.device_module.common.sql.TB_sum_61_62_64;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.JsonUtils;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.log.L;
import com.socks.library.KLog;
import coms.mediatek.ctrl.epo.EpoDownloadChangeListener;
import coms.mediatek.ctrl.epo.EpoDownloadController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import org.greenrobot.eventbus.EventBus;
import org.litepal.crud.DataSupport;

public class MtkSync implements EpoDownloadChangeListener {
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
    private static MtkSync instance;
    private static Handler mHandler = new Handler(Looper.getMainLooper());
    private final String TAG = getClass().getSimpleName();
    List<P1SendBleData> bleP1 = new ArrayList();
    DateUtil date = new DateUtil();
    private long epoTime = 0;
    private boolean f1IsOver = true;
    private long gpsSportTime = 0;
    private boolean isOk = false;
    private boolean isUpEpo = false;
    private int lastIndex = 0;
    private long lastTime = 0;
    List<Integer> listNum = new ArrayList();
    private int mAllAdd = 0;
    private int mDAll;
    private boolean mIsSyncDataInfo;
    /* access modifiers changed from: private */
    public int mNowType;
    Map<String, SeqModel> map = new HashMap();
    private int nowSync;
    Queue<P1SendBleData> queueP1;
    private int sendNum = 0;
    private int sendType = 0;
    private Runnable stopSyncRunnable = new Runnable() {
        public void run() {
            MtkSync.this.stopSyncData(MtkSync.this.mNowType);
            KLog.e("15s未收到" + MtkSync.this.mNowType + "类型数据, 停止同步该类型数据");
        }
    };
    private int syncP1Num = 0;
    private int upIndex;

    private MtkSync() {
        EpoDownloadController.addListener(this);
    }

    public static MtkSync getInstance() {
        if (instance == null) {
            instance = new MtkSync();
        }
        return instance;
    }

    public void setmIsSyncDataInfo(boolean mIsSyncDataInfo2) {
        this.mIsSyncDataInfo = mIsSyncDataInfo2;
    }

    public boolean isSyncDataInfo() {
        if (this.mIsSyncDataInfo && !isOver() && isUpEpo()) {
            return true;
        }
        return false;
    }

    public void clear() {
        Editor edit = ContextUtil.app.getSharedPreferences(SYNC_DATA, 0).edit();
        edit.clear();
        edit.apply();
    }

    public void judgeStopSyncData() {
        mHandler.removeCallbacks(this.stopSyncRunnable);
        mHandler.postDelayed(this.stopSyncRunnable, 15000);
    }

    public int getProgress() {
        if (this.mAllAdd <= 0) {
            return 0;
        }
        Log.d("testsync", "进度条：  " + this.mDAll + " -- " + this.mAllAdd);
        if (this.mDAll >= this.mAllAdd) {
            this.mDAll = this.mAllAdd;
        }
        return (this.mDAll * 100) / this.mAllAdd;
    }

    public void stopSyncDataAll() {
        clear();
        stopSyncAllF1Data();
        mHandler.removeCallbacks(this.stopSyncRunnable);
        this.mIsSyncDataInfo = false;
    }

    private void stopSyncAllF1Data() {
        MtkCmdAssembler.getInstance().stopSyncDetailData(97, 98, 100);
        MtkCmdAssembler.getInstance().dailyHealthDataSwitch(false);
    }

    public void stopSyncData(int type) {
        KLog.e("停止同步数据类型:" + type);
        if (type == this.mNowType) {
            mHandler.removeCallbacks(this.stopSyncRunnable);
        }
    }

    public void syncDataInfo() {
        MtkBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, MtkCmdAssembler.getInstance().setHeartBeat(0));
        EventBus.getDefault().post(new SyncDataEvent());
        if (isSyncDataInfo()) {
            L.file("正在同步...", 4);
            return;
        }
        this.mIsSyncDataInfo = true;
        if (isOver() && !isUpEpo()) {
            EventBus.getDefault().post(new SyncDataEvent(0, false));
            if (PrefUtil.getInt(ContextUtil.app, SharedPreferencesAction.EARPHONE) == 1) {
                KLog.e("sync data r1");
                beginSyncR1();
                return;
            }
            beginSyncF1();
        }
    }

    public Map<String, SeqModel> getMap() {
        return this.map;
    }

    public void setMap(Map<String, SeqModel> map2) {
        this.map = map2;
    }

    public synchronized void validate_61_62_1_data(int type) {
        this.upIndex++;
        KLog.i("手环上报的数据总数量:" + this.upIndex);
    }

    public boolean isOver() {
        return (System.currentTimeMillis() / 1000) - this.lastTime >= 30;
    }

    public void setF1IsOver() {
        f1Over();
        this.f1IsOver = true;
    }

    private void beginSyncF1() {
        this.f1IsOver = false;
        clearIndex();
        this.lastTime = System.currentTimeMillis() / 1000;
        DataSupport.deleteAll(TB_sum_61_62_64.class, new String[0]);
        ZeronerBackgroundThreadManager.getInstance().wakeUp();
        mHandler.postDelayed(new Runnable() {
            public void run() {
                Log.e("licl", "准备同步数据--->");
                MtkCmdAssembler.getInstance().dailyHealthDataSwitch(true);
                MtkCmdAssembler.getInstance().getIndexTableAccordingType(97);
            }
        }, 1000);
    }

    private void beginSyncR1() {
        this.f1IsOver = false;
        clearIndex();
        this.lastTime = System.currentTimeMillis() / 1000;
        ZeronerBackgroundThreadManager.getInstance().wakeUp();
        mHandler.postDelayed(new Runnable() {
            public void run() {
                Log.e("licl", "准备同步数据68--->");
                MtkCmdAssembler.getInstance().getIndexTableAccordingType(104);
                Log.e("licl", "send index table cmd for 68--->");
            }
        }, 1000);
    }

    private void clearF1() {
        this.sendNum = 0;
        this.isOk = false;
        this.listNum.clear();
        this.upIndex = 0;
        this.syncP1Num = 0;
        this.bleP1.clear();
    }

    public void syncP1AllData() {
        clearF1();
        List<TB_sum_61_62_64> tbSum = DataSupport.order("date_time desc").find(TB_sum_61_62_64.class);
        KLog.e("licl", "testf1shuju111开始获取全部数据 -:" + tbSum.size());
        if (tbSum.size() > 0) {
            if (tbSum.size() > 1) {
                String date2 = ((TB_sum_61_62_64) tbSum.get(0)).getDate();
                int sums = 0;
                for (int i = 0; i < tbSum.size() - 1; i++) {
                    this.bleP1.add(new P1SendBleData(((TB_sum_61_62_64) tbSum.get(i)).getYear(), ((TB_sum_61_62_64) tbSum.get(i)).getMonth(), ((TB_sum_61_62_64) tbSum.get(i)).getDay(), ByteUtil.bytesToInt(ByteUtil.hexToBytes(((TB_sum_61_62_64) tbSum.get(i)).getSend_cmd().substring(0, 4))), ByteUtil.bytesToInt(ByteUtil.hexToBytes(((TB_sum_61_62_64) tbSum.get(i)).getSend_cmd().substring(4, 8))), ((TB_sum_61_62_64) tbSum.get(i)).getType()));
                    if (((TB_sum_61_62_64) tbSum.get(i)).getDate().equals(((TB_sum_61_62_64) tbSum.get(i + 1)).getDate())) {
                        sums += ((TB_sum_61_62_64) tbSum.get(i)).getSum();
                    } else {
                        this.listNum.add(Integer.valueOf(sums + ((TB_sum_61_62_64) tbSum.get(i)).getSum()));
                        sums = 0;
                    }
                }
                this.listNum.add(Integer.valueOf(sums + ((TB_sum_61_62_64) tbSum.get(tbSum.size() - 1)).getSum()));
                int j = tbSum.size() - 1;
                this.bleP1.add(new P1SendBleData(((TB_sum_61_62_64) tbSum.get(j)).getYear(), ((TB_sum_61_62_64) tbSum.get(j)).getMonth(), ((TB_sum_61_62_64) tbSum.get(j)).getDay(), ByteUtil.bytesToInt(ByteUtil.hexToBytes(((TB_sum_61_62_64) tbSum.get(j)).getSend_cmd().substring(0, 4))), ByteUtil.bytesToInt(ByteUtil.hexToBytes(((TB_sum_61_62_64) tbSum.get(j)).getSend_cmd().substring(4, 8))), ((TB_sum_61_62_64) tbSum.get(j)).getType()));
            } else {
                this.bleP1.add(new P1SendBleData(((TB_sum_61_62_64) tbSum.get(0)).getYear(), ((TB_sum_61_62_64) tbSum.get(0)).getMonth(), ((TB_sum_61_62_64) tbSum.get(0)).getDay(), ByteUtil.bytesToInt(ByteUtil.hexToBytes(((TB_sum_61_62_64) tbSum.get(0)).getSend_cmd().substring(0, 4))), ByteUtil.bytesToInt(ByteUtil.hexToBytes(((TB_sum_61_62_64) tbSum.get(0)).getSend_cmd().substring(4, 8))), ((TB_sum_61_62_64) tbSum.get(0)).getType()));
            }
            for (int i2 = 0; i2 < this.bleP1.size(); i2++) {
                KLog.e("licl", "开始获取11全部数据 -:" + new Gson().toJson(this.bleP1.get(i2)));
            }
            KLog.e("licl", "开始获取总天数为:" + this.listNum.size());
            if (this.bleP1 != null && this.bleP1.size() > 0) {
                L.file("no2855 61数据同步数据最后时间: " + ((P1SendBleData) this.bleP1.get(this.bleP1.size() - 1)).date, 3);
            }
            sync_P1_data();
            return;
        }
        EventBus.getDefault().post(new ViewRefresh(false, 40));
        EventBus.getDefault().post(new SyncDataEvent(100, true, 0, 0));
        setEpo();
        KLog.e("testf1shuju全部同步结束------------");
        f1Over();
        this.bleP1.clear();
        this.mIsSyncDataInfo = false;
        MtkDataParsePresenter.upCmdToServer();
    }

    public void syncR1AllData() {
        clearF1();
        List<TB_sum_61_62_64> tbSum = DataSupport.where("type_str=?", "0x68").order("date_time desc").find(TB_sum_61_62_64.class);
        KLog.e("licl", "开始获取68数据 -:" + tbSum.size());
        if (tbSum.size() > 0) {
            if (tbSum.size() > 1) {
                String date2 = ((TB_sum_61_62_64) tbSum.get(0)).getDate();
                int sums = 0;
                for (int i = 0; i < tbSum.size() - 1; i++) {
                    this.bleP1.add(new P1SendBleData(((TB_sum_61_62_64) tbSum.get(i)).getYear(), ((TB_sum_61_62_64) tbSum.get(i)).getMonth(), ((TB_sum_61_62_64) tbSum.get(i)).getDay(), ByteUtil.bytesToInt(ByteUtil.hexToBytes(((TB_sum_61_62_64) tbSum.get(i)).getSend_cmd().substring(0, 4))), ByteUtil.bytesToInt(ByteUtil.hexToBytes(((TB_sum_61_62_64) tbSum.get(i)).getSend_cmd().substring(4, 8))), ((TB_sum_61_62_64) tbSum.get(i)).getType()));
                    if (((TB_sum_61_62_64) tbSum.get(i)).getDate().equals(((TB_sum_61_62_64) tbSum.get(i + 1)).getDate())) {
                        sums += ((TB_sum_61_62_64) tbSum.get(i)).getSum();
                    } else {
                        this.listNum.add(Integer.valueOf(sums + ((TB_sum_61_62_64) tbSum.get(i)).getSum()));
                        sums = 0;
                    }
                }
                this.listNum.add(Integer.valueOf(sums + ((TB_sum_61_62_64) tbSum.get(tbSum.size() - 1)).getSum()));
                int j = tbSum.size() - 1;
                this.bleP1.add(new P1SendBleData(((TB_sum_61_62_64) tbSum.get(j)).getYear(), ((TB_sum_61_62_64) tbSum.get(j)).getMonth(), ((TB_sum_61_62_64) tbSum.get(j)).getDay(), ByteUtil.bytesToInt(ByteUtil.hexToBytes(((TB_sum_61_62_64) tbSum.get(j)).getSend_cmd().substring(0, 4))), ByteUtil.bytesToInt(ByteUtil.hexToBytes(((TB_sum_61_62_64) tbSum.get(j)).getSend_cmd().substring(4, 8))), ((TB_sum_61_62_64) tbSum.get(j)).getType()));
            } else {
                this.bleP1.add(new P1SendBleData(((TB_sum_61_62_64) tbSum.get(0)).getYear(), ((TB_sum_61_62_64) tbSum.get(0)).getMonth(), ((TB_sum_61_62_64) tbSum.get(0)).getDay(), ByteUtil.bytesToInt(ByteUtil.hexToBytes(((TB_sum_61_62_64) tbSum.get(0)).getSend_cmd().substring(0, 4))), ByteUtil.bytesToInt(ByteUtil.hexToBytes(((TB_sum_61_62_64) tbSum.get(0)).getSend_cmd().substring(4, 8))), ((TB_sum_61_62_64) tbSum.get(0)).getType()));
            }
            for (int i2 = 0; i2 < this.bleP1.size(); i2++) {
                KLog.e("licl", "开始获取11全部数据 -:" + new Gson().toJson(this.bleP1.get(i2)));
            }
            KLog.e("licl", "开始获取总天数为:" + this.listNum.size());
            sync_P1_data();
            return;
        }
        EventBus.getDefault().post(new ViewRefresh(false, 40));
        EventBus.getDefault().post(new SyncDataEvent(100, true, 0, 0));
        KLog.e("test r1 shuju", "全部同步结束------------");
        f1Over();
        this.bleP1.clear();
        this.mIsSyncDataInfo = false;
        MtkDataParsePresenter.upCmdToServer();
    }

    public int getSendType() {
        return this.sendType;
    }

    public boolean sync_P1_data() {
        this.lastTime = System.currentTimeMillis() / 1000;
        ZeronerBackgroundThreadManager.getInstance().wakeUp();
        if (this.syncP1Num < this.bleP1.size()) {
            if (isOneDayOver()) {
                this.sendNum++;
                this.upIndex = 0;
            }
            this.nowSync = ((P1SendBleData) this.bleP1.get(this.syncP1Num)).getEndIndex();
            if (((P1SendBleData) this.bleP1.get(this.syncP1Num)).getDataType() == 97) {
                if (this.nowSync > 4096) {
                    this.nowSync -= 4096;
                }
            } else if (((P1SendBleData) this.bleP1.get(this.syncP1Num)).getDataType() == 98) {
                if (this.nowSync > 1024) {
                    this.nowSync -= 1024;
                }
            } else if (this.nowSync > 1280) {
                this.nowSync -= 1280;
            }
            MtkCmdAssembler.getInstance().getDetailDataAsIndex(((P1SendBleData) this.bleP1.get(this.syncP1Num)).getYear(), ((P1SendBleData) this.bleP1.get(this.syncP1Num)).getMonth(), ((P1SendBleData) this.bleP1.get(this.syncP1Num)).getDay(), ((P1SendBleData) this.bleP1.get(this.syncP1Num)).getStartIndex(), ((P1SendBleData) this.bleP1.get(this.syncP1Num)).getEndIndex(), ((P1SendBleData) this.bleP1.get(this.syncP1Num)).getDataType());
            KLog.e("licltestf1shuju", "发送P1的数据:p1Num " + this.syncP1Num + " - " + ((P1SendBleData) this.bleP1.get(this.syncP1Num)).getStartIndex() + " -  " + ((P1SendBleData) this.bleP1.get(this.syncP1Num)).getEndIndex() + " -- " + this.nowSync + " - " + ((P1SendBleData) this.bleP1.get(this.syncP1Num)).getDataType());
            this.syncP1Num++;
            return true;
        }
        KLog.e("licltestf1shuju", "全部同步结束------------");
        EventBus.getDefault().post(new SyncDataEvent(100, true, 0, 0));
        this.bleP1.clear();
        f1Over();
        setEpo();
        this.mIsSyncDataInfo = false;
        return false;
    }

    public boolean isOneDayOver() {
        Log.e("licl", "testf1shuju111syncP1num" + this.syncP1Num + "/bleP1.size" + this.bleP1.size());
        if (this.bleP1.size() <= 0) {
            return true;
        }
        if (this.syncP1Num - 1 == this.bleP1.size() - 1) {
            return true;
        }
        if (this.syncP1Num == 0) {
            return false;
        }
        if (((P1SendBleData) this.bleP1.get(this.syncP1Num - 1)).getDate().equals(((P1SendBleData) this.bleP1.get(this.syncP1Num)).getDate())) {
            return false;
        }
        return true;
    }

    public boolean isUpFile() {
        if (this.isOk) {
            this.isOk = false;
            return true;
        } else if ((this.sendNum != 1 || this.sendType < 2) && !isOver()) {
            return false;
        } else {
            return true;
        }
    }

    private void f1Over() {
        this.upIndex = 0;
        this.f1IsOver = true;
        this.lastTime = 0;
        this.listNum.clear();
    }

    public void setEpo() {
        if (!new DateUtil().getSyyyyMMddDate().equalsIgnoreCase(PrefUtil.getString(ContextUtil.app, FirmwareAction.Firmware_Last_Epo_Time))) {
            Mtk_DeviceBaseInfo info = Mtk_DeviceBaseInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Battery);
            if (info != null) {
                Power power = (Power) JsonUtils.fromJson(info.getContent(), Power.class);
                if (power == null || power.getPower() < 20) {
                    EventBus.getDefault().post(new EpoEvent(EpoEvent.STATE_LOW_BATTERY, -1));
                    return;
                }
                getInstance().setUpEpo(true);
                EventBus.getDefault().post(new EpoEvent(EpoEvent.STATE_INIT, 0));
                L.file("准备写入EPO------>", 5);
                Log.d("licl", "准备写入EPO------>");
                MtkCmdAssembler.getInstance().writeEpo();
            }
        }
    }

    public void progressUP(boolean isStop, String data) {
        this.lastTime = System.currentTimeMillis() / 1000;
        if (this.listNum.size() == 0) {
            this.mAllAdd = 0;
        } else {
            this.mAllAdd = ((Integer) this.listNum.get(this.sendNum >= this.listNum.size() ? this.listNum.size() - 1 : this.sendNum)).intValue();
        }
        if (this.mAllAdd <= 0) {
            EventBus.getDefault().post(new SyncDataEvent(0, isStop, this.listNum.size(), this.sendNum + 1));
            return;
        }
        double indexd = (((double) this.upIndex) * 100.0d) / (((double) this.mAllAdd) * 1.0d);
        int index = (int) indexd;
        if (index >= 100 || indexd > 99.9d) {
            index = 100;
        }
        if (this.lastIndex != index) {
            this.lastIndex = index;
            EventBus.getDefault().post(new SyncDataEvent(index, false, this.listNum.size(), this.sendNum + 1));
        }
    }

    public void clearIndex() {
        this.upIndex = 0;
        this.mAllAdd = 0;
        this.lastIndex = 0;
        this.mDAll = 0;
        this.isOk = false;
    }

    public int getNowSync() {
        return this.nowSync;
    }

    public void setUpEpo(boolean isUpEpo2) {
        this.isUpEpo = isUpEpo2;
    }

    public boolean isUpEpo() {
        if (this.isUpEpo && System.currentTimeMillis() - this.epoTime < 30) {
            return true;
        }
        return false;
    }

    public void notifyProgressChanged(float v) {
        if (((double) v) < 0.01d) {
            KLog.e("epo开始写入");
            setUpEpo(true);
            EventBus.getDefault().post(new EpoEvent(EpoEvent.STATE_INIT, 0));
        } else if (v >= 1.0f) {
            setUpEpo(false);
            EventBus.getDefault().post(new EpoEvent(EpoEvent.STATE_END, 100));
            PrefUtil.save((Context) ContextUtil.app, FirmwareAction.Firmware_Last_Epo_Time, new DateUtil().getSyyyyMMddDate());
            KLog.e("epo写入完成");
        } else {
            setUpEpo(true);
            EventBus.getDefault().post(new EpoEvent(EpoEvent.STATE_SENDING, (int) (100.0f * v)));
        }
    }

    public void notifyDownloadResult(int i) {
    }

    public void notifyConnectionChanged(int i) {
    }

    public boolean isGpsSporting() {
        if (System.currentTimeMillis() - this.gpsSportTime < 600000) {
            return false;
        }
        return true;
    }

    public void setGpsSportTime(long time) {
        this.gpsSportTime = time;
    }

    public String getEndDay() {
        if (this.bleP1 == null || this.bleP1.size() <= 0) {
            return "";
        }
        return ((P1SendBleData) this.bleP1.get(this.bleP1.size() - 1)).date;
    }
}
