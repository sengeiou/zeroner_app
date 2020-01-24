package com.iwown.device_module.common.Bluetooth.sync.proto;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.SparseArray;
import com.iwown.ble_module.proto.cmd.ProtoBufSendBluetoothCmdImpl;
import com.iwown.ble_module.proto.task.BackgroundThreadManager;
import com.iwown.data_link.eventbus.StartSyncDataEvent;
import com.iwown.data_link.eventbus.SyncDataEvent;
import com.iwown.device_module.common.BaseActionUtils.BleAction;
import com.iwown.device_module.common.Bluetooth.receiver.proto.dao.ProtoBufSleepSqlUtils;
import com.iwown.device_module.common.sql.PbSupportInfo;
import com.iwown.device_module.common.sql.ProtoBuf_index_80;
import com.iwown.device_module.common.sql.TB_64_index_table;
import com.iwown.device_module.common.sql.TB_mtk_statue;
import com.iwown.device_module.common.sql.TB_rri_index_table;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.lib_common.date.DateUtil;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.litepal.crud.DataSupport;

public class ProtoBufSync {
    public static final int ECG_DATA = 2;
    public static final int GNSS_DATA = 1;
    public static final int HEALTH_DATA = 0;
    public static final int PPG_DATA = 3;
    public static final int RRI_DATA = 4;
    private static volatile ProtoBufSync instance;
    public static boolean isFirstSync = false;
    private static Handler mHandler = new Handler(Looper.getMainLooper());
    private SparseArray<List<ProtoBuf_index_80>> array = new SparseArray<>();
    private int currentType;
    private boolean hasData = false;
    private boolean isSync = false;
    private int lastPosition = -1;
    private int timeDelay = 40000;
    private SparseArray<List<ProtobufSyncSeq>> totalSeqList = new SparseArray<>();
    private List<Integer> typeArray = new ArrayList();

    public static ProtoBufSync getInstance() {
        if (instance == null) {
            synchronized (ProtoBufSync.class) {
                if (instance == null) {
                    instance = new ProtoBufSync();
                }
            }
        }
        return instance;
    }

    public void syncData() {
        String data_from = PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name);
        BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().getRealHealthData());
        if (this.isSync) {
            KLog.d("正在同步..");
            return;
        }
        PbSupportInfo protoBufSupportInfo = (PbSupportInfo) DataSupport.where("data_from=?", data_from).findFirst(PbSupportInfo.class);
        if (protoBufSupportInfo != null) {
            this.typeArray = getTypeArray(protoBufSupportInfo);
            EventBus.getDefault().post(new StartSyncDataEvent());
            this.isSync = true;
            this.currentType = 0;
            initData();
        }
    }

    private void initData() {
        if (this.currentType < this.typeArray.size()) {
            BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().itHisData(((Integer) this.typeArray.get(this.currentType)).intValue()));
        }
    }

    public void syncDetailData(Context context, List<ProtoBuf_index_80> index_80s) {
        if (index_80s == null || index_80s.size() <= 0) {
            KLog.e("yanxi----同步完成一项--1--" + this.hasData);
            this.currentType++;
            if (this.currentType < this.typeArray.size()) {
                initData();
            } else if (!this.hasData) {
                this.hasData = false;
                getInstance().progressFinish();
            }
        } else {
            KLog.e("有数据" + this.hasData);
            this.hasData = true;
            int indexType = ((ProtoBuf_index_80) index_80s.get(0)).getIndexType();
            this.array.put(indexType, index_80s);
            List<ProtobufSyncSeq> protobufSyncSeqs = new ArrayList<>();
            for (int i = 0; i < index_80s.size(); i++) {
                ProtoBuf_index_80 dbIndex = (ProtoBuf_index_80) index_80s.get(i);
                protobufSyncSeqs.add(new ProtobufSyncSeq(dbIndex.getEnd_idx() - dbIndex.getStart_idx(), dbIndex.getStart_idx(), i + 1, dbIndex.getEnd_idx(), indexType));
                saveIndexTable(indexType, dbIndex);
            }
            this.totalSeqList.put(indexType, protobufSyncSeqs);
            syncDetailByIndex(context, indexType, index_80s, 0);
        }
    }

    /* access modifiers changed from: private */
    public void syncDetailByIndex(Context context, int hisDataType, List<ProtoBuf_index_80> indexList, int position) {
        if (position < indexList.size()) {
            ProtoBuf_index_80 index = (ProtoBuf_index_80) indexList.get(position);
            int startSeq = index.getStart_idx();
            int endSeq = index.getEnd_idx();
            KLog.d("80 data ----- sync ---" + startSeq + "---" + endSeq);
            detailData(context, hisDataType, startSeq, endSeq);
            final int nextPosition = position + 1;
            if (nextPosition < indexList.size()) {
                KLog.d("80 data ----- sync ---" + startSeq + "---" + endSeq);
                final Context context2 = context;
                final int i = hisDataType;
                final List<ProtoBuf_index_80> list = indexList;
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        ProtoBufSync.this.syncDetailByIndex(context2, i, list, nextPosition);
                    }
                }, 1000);
                return;
            }
            KLog.d("yanxi----同步完成一项--2--" + this.hasData);
            this.currentType++;
            if (this.currentType < this.typeArray.size()) {
                initData();
            }
        }
    }

    private void detailData(Context context, int type, int startSeq, int endSeq) {
        BackgroundThreadManager.getInstance().addWriteData(context, ProtoBufSendBluetoothCmdImpl.getInstance().startHisData(type, startSeq, endSeq));
    }

    private void syncFinish() {
        ProtoBufSleepSqlUtils.dispSleepData((List) this.array.get(0));
        this.totalSeqList.clear();
        this.array.clear();
        KLog.d("80 data ----- sync ---finish");
        if (this.typeArray.contains(Integer.valueOf(1))) {
            ProtoBufUpdate.getInstance().startUpdate(0);
        }
    }

    public boolean isSync() {
        return this.isSync;
    }

    public void setSync(boolean sync) {
        this.isSync = sync;
    }

    public int currentProgress(int type, int seq) {
        int currentIndex = -1;
        String typeDesc = "";
        if (type == 0) {
            if (getLanguage().equals("zh")) {
                typeDesc = "健康";
            } else {
                typeDesc = " health ";
            }
        } else if (type == 1) {
            typeDesc = "GPS";
        } else if (type == 2) {
            typeDesc = "ECG";
        } else if (type == 4) {
            typeDesc = "RRI";
        }
        List<ProtobufSyncSeq> protobufSyncSeqs = (List) this.totalSeqList.get(type);
        if (protobufSyncSeqs == null) {
            return 0;
        }
        int i = 0;
        while (true) {
            if (i >= protobufSyncSeqs.size()) {
                break;
            }
            int startSeq = ((ProtobufSyncSeq) protobufSyncSeqs.get(i)).getStartSeq();
            int endSeq = ((ProtobufSyncSeq) protobufSyncSeqs.get(i)).getEndSeq();
            if (seq >= startSeq && seq <= endSeq) {
                currentIndex = i;
                break;
            }
            i++;
        }
        if (currentIndex == -1) {
            return 0;
        }
        int progress = (((seq - ((ProtobufSyncSeq) protobufSyncSeqs.get(currentIndex)).getStartSeq()) + 1) * 100) / ((ProtobufSyncSeq) protobufSyncSeqs.get(currentIndex)).getTotalSeq();
        if (this.lastPosition != progress) {
            KLog.d("YANXI----" + progress + "---" + seq + "---" + ((ProtobufSyncSeq) protobufSyncSeqs.get(currentIndex)).getStartSeq() + "---" + ((ProtobufSyncSeq) protobufSyncSeqs.get(currentIndex)).getEndSeq() + "----" + currentIndex);
            EventBus.getDefault().post(new SyncDataEvent(progress, false, protobufSyncSeqs.size(), ((ProtobufSyncSeq) protobufSyncSeqs.get(currentIndex)).getCurrentDay(), typeDesc));
            this.lastPosition = progress;
        }
        return progress;
    }

    public void progressFinish() {
        if (this.isSync) {
            this.hasData = false;
            EventBus.getDefault().post(new SyncDataEvent(100, true));
            this.isSync = false;
            KLog.e("80 data ----- progressFinish");
            syncFinish();
        }
    }

    public void stopSync() {
        this.isSync = false;
        for (int i = 0; i < this.typeArray.size(); i++) {
            BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().stopHisData(((Integer) this.typeArray.get(i)).intValue()));
        }
    }

    public String getLanguage() {
        return ContextUtil.app.getResources().getConfiguration().locale.getLanguage();
    }

    private void saveIndexTable(int indexType, ProtoBuf_index_80 dbIndex) {
        if (indexType == 1) {
            DateUtil dateUtil = new DateUtil(dbIndex.getYear(), dbIndex.getMonth(), dbIndex.getDay());
            TB_mtk_statue mtk_statue = new TB_mtk_statue();
            mtk_statue.setUid(dbIndex.getUid());
            mtk_statue.setData_from(dbIndex.getData_from());
            mtk_statue.setType(80);
            mtk_statue.setYear(dbIndex.getYear());
            mtk_statue.setMonth(dbIndex.getMonth());
            mtk_statue.setDay(dbIndex.getDay());
            mtk_statue.setHas_file(2);
            mtk_statue.setHas_up(2);
            mtk_statue.setHas_tb(2);
            mtk_statue.setDate(dateUtil.getUnixTimestamp());
            mtk_statue.saveOrUpdate("uid=? and data_from=? and type=? and date=?", dbIndex.getUid() + "", dbIndex.getData_from(), "80", dateUtil.getUnixTimestamp() + "");
        } else if (indexType == 2) {
            TB_64_index_table indexTable = new TB_64_index_table();
            DateUtil d = new DateUtil(dbIndex.getYear(), dbIndex.getMonth(), dbIndex.getDay(), dbIndex.getHour(), dbIndex.getMin(), dbIndex.getSecond());
            indexTable.setUid(dbIndex.getUid());
            indexTable.setData_from(dbIndex.getData_from());
            indexTable.setData_ymd(d.getSyyyyMMddDate());
            indexTable.setSeq_start(dbIndex.getStart_idx());
            indexTable.setSeq_end(dbIndex.getEnd_idx());
            indexTable.setSync_seq(dbIndex.getEnd_idx());
            indexTable.setDate(d.getY_M_D_H_M_S());
            indexTable.setUnixTime(d.getUnixTimestamp());
            indexTable.saveOrUpdate("uid=? and data_from =? and date=?", String.valueOf(dbIndex.getUid()), dbIndex.getData_from(), d.getY_M_D_H_M_S());
        } else if (indexType == 4) {
            TB_rri_index_table index_table = new TB_rri_index_table();
            DateUtil d2 = new DateUtil(dbIndex.getYear(), dbIndex.getMonth(), dbIndex.getDay(), dbIndex.getHour(), dbIndex.getMin(), dbIndex.getSecond());
            index_table.setDataFrom(dbIndex.getData_from());
            index_table.setData_ymd(d2.getSyyyyMMddDate());
            index_table.setDate(d2.getY_M_D_H_M_S());
            index_table.setStart_seq(dbIndex.getStart_idx());
            index_table.setEnd_seq(dbIndex.getEnd_idx());
            index_table.setUid(dbIndex.getUid());
            index_table.setDate(d2.getY_M_D_H_M_S());
            index_table.saveOrUpdate("uid=? and dataFrom =? and date=?", String.valueOf(dbIndex.getUid()), dbIndex.getData_from(), d2.getY_M_D_H_M_S());
        }
    }

    private List<Integer> getTypeArray(PbSupportInfo protoBufSupportInfo) {
        List<Integer> integers = new ArrayList<>();
        if (protoBufSupportInfo.isSupport_health()) {
            integers.add(Integer.valueOf(0));
        }
        if (protoBufSupportInfo.isSupport_gnss()) {
            integers.add(Integer.valueOf(1));
        }
        if (protoBufSupportInfo.isSupport_ecg()) {
            integers.add(Integer.valueOf(2));
        }
        if (protoBufSupportInfo.isSupport_ppg()) {
            integers.add(Integer.valueOf(3));
        }
        if (protoBufSupportInfo.isSupport_rri()) {
            integers.add(Integer.valueOf(4));
        }
        return integers;
    }
}
