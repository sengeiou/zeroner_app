package com.iwown.device_module.device_alarm_schedule.mtk.manager;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import com.iwown.device_module.common.BaseActionUtils.UserAction;
import com.iwown.device_module.common.sql.TB_schedulestatue;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.device_module.device_alarm_schedule.common.IQueueManagerResultListener;
import com.iwown.device_module.device_alarm_schedule.common.ScheduleManager;
import com.iwown.device_module.device_alarm_schedule.mtk.Mtkschedule;
import com.iwown.device_module.device_alarm_schedule.mtk.Mtkschedule.OnScheduleWriteResult;
import com.iwown.device_module.device_alarm_schedule.mtk.Mtkschedule.ScheduleBluetoothDataParseBiz;
import com.iwown.device_module.device_alarm_schedule.utils.AddScheduleUtil;
import com.iwown.device_module.device_setting.configure.DeviceSettingsBiz;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.litepal.crud.DataSupport;

public class MtkScheduleWriteQueueManager {
    /* access modifiers changed from: private */
    public static boolean HAS_EXCEPTION = false;
    /* access modifiers changed from: private */
    public static boolean NO_EXCEPTION = true;
    public static final int OPERATION_TYPE_ADD = 0;
    public static final int OPERATION_TYPE_DELETE = 1;
    public static final int QUEUE_RESULT_BLUETOOTH_ERR = 2;
    public static final int QUEUE_RESULT_FAIL = 1;
    public static final int QUEUE_RESULT_SUCCESS = 0;
    public static final int QUEUE_RESULT_TIMEOUT = 3;
    public static final int QUEUE_STATE_BLUETOOTH_BREAK = 1;
    public static final int QUEUE_STATE_BLUETOOTH_CONNECT = 0;
    public static final int QUEUE_STATE_IMMEDIATELY = 1;
    public static final int QUEUE_STATE_READDEVICE_INFO = 4;
    public static final int QUEUE_STATE_SYNC_AFTERERR = 3;
    public static final int QUEUE_STATE_WRITEQUEUE = 2;
    /* access modifiers changed from: private */
    public static String TAG = "Schedule";
    private static String exception = "errbluetooth";
    private static String hasException = "no";
    private static MtkScheduleWriteQueueManager mScheduleManager;
    private static String noException = "yes";
    private static String sharePrefPath = "scheduleerrflag";
    private final int IS_CLEAR_FIRST_NO = 2;
    private final int IS_CLEAR_FIRST_QUEUE_NULL = 0;
    private final int IS_CLEAR_FIRST_YES = 1;
    private int QueueNum = 0;
    /* access modifiers changed from: private */
    public boolean isBusyWriting;
    /* access modifiers changed from: private */
    public boolean isReadDeviceInfo = false;
    /* access modifiers changed from: private */
    public boolean isStartImmediateWrite = false;
    /* access modifiers changed from: private */
    public boolean isSyncDataAfterErr = false;
    /* access modifiers changed from: private */
    public boolean isWriteQueueState = false;
    private Context mContext;
    List<ScheduleOperation> mCurScheduleOperationList;
    private OnScheduleWriteResult mOnScheduleWriteResult = new OnScheduleWriteResult() {
        public void onResult(int result, int state) {
            KLog.d(MtkScheduleWriteQueueManager.TAG, "日程队列回调收到驱动发来的消息回调 result：" + result + ", state：" + state);
            if (result == 5) {
                if (MtkScheduleWriteQueueManager.this.isStartImmediateWrite) {
                    MtkScheduleWriteQueueManager.this.isStartImmediateWrite = false;
                    MtkScheduleWriteQueueManager.this.isBusyWriting = false;
                    MtkScheduleWriteQueueManager.this.setException(MtkScheduleWriteQueueManager.HAS_EXCEPTION);
                    KLog.d(MtkScheduleWriteQueueManager.TAG, "立即写入, 蓝牙断开");
                    MtkScheduleWriteQueueManager.this.sendResult(2, 1);
                } else if (MtkScheduleWriteQueueManager.this.isWriteQueueState) {
                    MtkScheduleWriteQueueManager.this.isWriteQueueState = false;
                    MtkScheduleWriteQueueManager.this.isBusyWriting = false;
                    MtkScheduleWriteQueueManager.this.setException(MtkScheduleWriteQueueManager.HAS_EXCEPTION);
                    KLog.d(MtkScheduleWriteQueueManager.TAG, "写队列, 蓝牙断开");
                    MtkScheduleWriteQueueManager.this.sendResult(2, 2);
                } else if (MtkScheduleWriteQueueManager.this.isSyncDataAfterErr) {
                    MtkScheduleWriteQueueManager.this.isSyncDataAfterErr = false;
                    MtkScheduleWriteQueueManager.this.isBusyWriting = false;
                    MtkScheduleWriteQueueManager.this.setException(MtkScheduleWriteQueueManager.HAS_EXCEPTION);
                    KLog.d(MtkScheduleWriteQueueManager.TAG, "同步上一次数据, 蓝牙断开");
                    MtkScheduleWriteQueueManager.this.sendResult(2, 3);
                } else if (MtkScheduleWriteQueueManager.this.isReadDeviceInfo && state == 7) {
                    KLog.d(MtkScheduleWriteQueueManager.TAG, "isReadDeviceInfo 蓝牙断开");
                    MtkScheduleWriteQueueManager.this.stopReadDeviceInfo();
                    MtkScheduleWriteQueueManager.this.sendResult(2, 4);
                } else if (state == 1) {
                    KLog.d(MtkScheduleWriteQueueManager.TAG, "蓝牙断开");
                    MtkScheduleWriteQueueManager.this.sendResult(2, 1);
                } else if (state == 0) {
                    KLog.d(MtkScheduleWriteQueueManager.TAG, "蓝牙连接");
                    MtkScheduleWriteQueueManager.this.sendResult(2, 0);
                }
            } else if (result == 6) {
                if (MtkScheduleWriteQueueManager.this.isStartImmediateWrite) {
                    MtkScheduleWriteQueueManager.this.isStartImmediateWrite = false;
                    MtkScheduleWriteQueueManager.this.isBusyWriting = false;
                    MtkScheduleWriteQueueManager.this.setException(MtkScheduleWriteQueueManager.HAS_EXCEPTION);
                    KLog.d(MtkScheduleWriteQueueManager.TAG, "立即写入, 接收超时,state = " + state);
                    MtkScheduleWriteQueueManager.this.sendResult(3, 1);
                } else if (MtkScheduleWriteQueueManager.this.isWriteQueueState) {
                    MtkScheduleWriteQueueManager.this.isWriteQueueState = false;
                    MtkScheduleWriteQueueManager.this.isBusyWriting = false;
                    MtkScheduleWriteQueueManager.this.setException(MtkScheduleWriteQueueManager.HAS_EXCEPTION);
                    KLog.d(MtkScheduleWriteQueueManager.TAG, "立即写入, 接收超时,state = " + state);
                    MtkScheduleWriteQueueManager.this.sendResult(3, 2);
                } else if (MtkScheduleWriteQueueManager.this.isSyncDataAfterErr) {
                    MtkScheduleWriteQueueManager.this.isSyncDataAfterErr = false;
                    MtkScheduleWriteQueueManager.this.isBusyWriting = false;
                    MtkScheduleWriteQueueManager.this.setException(MtkScheduleWriteQueueManager.HAS_EXCEPTION);
                    KLog.d(MtkScheduleWriteQueueManager.TAG, "同步上一次数据, 接收超时");
                    MtkScheduleWriteQueueManager.this.sendResult(3, 3);
                } else if (MtkScheduleWriteQueueManager.this.isReadDeviceInfo && state == 7) {
                    KLog.d(MtkScheduleWriteQueueManager.TAG, "设备信息, 接收超时");
                    MtkScheduleWriteQueueManager.this.stopReadDeviceInfo();
                    MtkScheduleWriteQueueManager.this.sendResult(3, 4);
                }
            } else if (result == 1) {
                if (state == 0) {
                    if (MtkScheduleWriteQueueManager.this.isStartImmediateWrite) {
                        KLog.d(MtkScheduleWriteQueueManager.TAG, "isStartImmediateWrite， 读信息成功");
                        int clearState = MtkScheduleWriteQueueManager.this.isClearDeviceFirst();
                        if (clearState == 0) {
                            KLog.d(MtkScheduleWriteQueueManager.TAG, "isStartImmediateWrite， 队列为空，不需要再操作");
                            MtkScheduleWriteQueueManager.this.isStartImmediateWrite = false;
                            MtkScheduleWriteQueueManager.this.isBusyWriting = false;
                            MtkScheduleWriteQueueManager.this.setException(MtkScheduleWriteQueueManager.NO_EXCEPTION);
                            MtkScheduleWriteQueueManager.this.sendResult(0, 1);
                        } else if (clearState == 2) {
                            KLog.d(MtkScheduleWriteQueueManager.TAG, "isStartImmediateWrite， 不需要清除设备");
                            MtkScheduleWriteQueueManager.this.isStartImmediateWrite = false;
                            MtkScheduleWriteQueueManager.this.startWriteQueue();
                        } else if (clearState == 1) {
                            KLog.d(MtkScheduleWriteQueueManager.TAG, "isStartImmediateWrite， 需要清除设备");
                            MtkScheduleWriteQueueManager.this.ClearDeviceAllData();
                        }
                    }
                } else if (state == 2) {
                    KLog.d(MtkScheduleWriteQueueManager.TAG, "设置日程 成功");
                    if (MtkScheduleWriteQueueManager.this.isWriteQueueState) {
                        KLog.d(MtkScheduleWriteQueueManager.TAG, "isWriteQueueState 设置日程 成功");
                        MtkScheduleWriteQueueManager.this.writeNextQueue();
                    }
                } else if (state == 4) {
                    KLog.d(MtkScheduleWriteQueueManager.TAG, "关闭日程 成功");
                    if (MtkScheduleWriteQueueManager.this.isWriteQueueState) {
                        KLog.d(MtkScheduleWriteQueueManager.TAG, "isWriteQueueState 关闭日程 成功");
                        MtkScheduleWriteQueueManager.this.writeNextQueue();
                    }
                } else if (state == 3) {
                    if (MtkScheduleWriteQueueManager.this.isStartImmediateWrite) {
                        KLog.d(MtkScheduleWriteQueueManager.TAG, "isStartImmediateWrite 清除日程 成功 立即同步");
                        MtkScheduleWriteQueueManager.this.isStartImmediateWrite = false;
                        MtkScheduleWriteQueueManager.this.updateQueueFromBaseData();
                        MtkScheduleWriteQueueManager.this.startWriteQueue();
                    } else if (MtkScheduleWriteQueueManager.this.isSyncDataAfterErr) {
                        KLog.d(MtkScheduleWriteQueueManager.TAG, "isSyncDataAfterErr 清除日程 成功 同步上一次数据");
                        MtkScheduleWriteQueueManager.this.isSyncDataAfterErr = false;
                        ScheduleManager.getInstance().updateIsChangeDeviceSchedule();
                        MtkScheduleWriteQueueManager.this.startWriteQueue();
                    }
                } else if (state == 7) {
                    KLog.d(MtkScheduleWriteQueueManager.TAG, "读取设备信息 成功");
                    if (MtkScheduleWriteQueueManager.this.isReadDeviceInfo) {
                        KLog.d(MtkScheduleWriteQueueManager.TAG, "isReadDeviceInfo 读取设备信息 成功");
                        MtkScheduleWriteQueueManager.this.stopReadDeviceInfo();
                        MtkScheduleWriteQueueManager.this.sendResult(0, 4);
                    }
                }
            } else if (result != 0) {
            } else {
                if (state == 4 || state == 2) {
                    if (MtkScheduleWriteQueueManager.this.isWriteQueueState) {
                        KLog.d(MtkScheduleWriteQueueManager.TAG, "isWriteQueueState 写、删除失败");
                        MtkScheduleWriteQueueManager.this.isWriteQueueState = false;
                        MtkScheduleWriteQueueManager.this.isBusyWriting = false;
                        MtkScheduleWriteQueueManager.this.sendResult(1, 2);
                    }
                } else if (state != 3) {
                } else {
                    if (MtkScheduleWriteQueueManager.this.isStartImmediateWrite) {
                        KLog.d(MtkScheduleWriteQueueManager.TAG, "isStartImmediateWrite 清除日程 失败");
                        MtkScheduleWriteQueueManager.this.isStartImmediateWrite = false;
                        MtkScheduleWriteQueueManager.this.isBusyWriting = false;
                        MtkScheduleWriteQueueManager.this.sendResult(1, 1);
                    } else if (MtkScheduleWriteQueueManager.this.isSyncDataAfterErr) {
                        KLog.d(MtkScheduleWriteQueueManager.TAG, "isSyncDataAfterErr 同步上一次数据 失败");
                        MtkScheduleWriteQueueManager.this.isSyncDataAfterErr = false;
                        MtkScheduleWriteQueueManager.this.isBusyWriting = false;
                        MtkScheduleWriteQueueManager.this.sendResult(1, 3);
                    }
                }
            }
        }
    };
    private IQueueManagerResultListener mQueueManagerResult;
    List<ScheduleOperation> mScheduleOperationList1;
    List<ScheduleOperation> mScheduleOperationList2;
    private Mtkschedule mV3_scheduleData_biz;
    private int writeQueueIndex = 0;

    private class ScheduleOperation {
        private TB_schedulestatue data;
        /* access modifiers changed from: private */
        public int operationType;

        public ScheduleOperation(TB_schedulestatue data2, int operationType2) {
            this.data = data2;
            this.operationType = operationType2;
        }

        public TB_schedulestatue getData() {
            return this.data;
        }

        public void setData(TB_schedulestatue data2) {
            this.data = data2;
        }

        public int getOperationType() {
            return this.operationType;
        }

        public void setOperationType(int operationType2) {
            this.operationType = operationType2;
        }
    }

    public static synchronized MtkScheduleWriteQueueManager getInstance(Context context) {
        MtkScheduleWriteQueueManager mtkScheduleWriteQueueManager;
        synchronized (MtkScheduleWriteQueueManager.class) {
            if (mScheduleManager == null) {
                mScheduleManager = new MtkScheduleWriteQueueManager(context);
            }
            mtkScheduleWriteQueueManager = mScheduleManager;
        }
        return mtkScheduleWriteQueueManager;
    }

    private MtkScheduleWriteQueueManager(Context context) {
        this.mContext = context;
        this.mScheduleOperationList1 = new ArrayList();
        this.mScheduleOperationList2 = new ArrayList();
        this.mCurScheduleOperationList = this.mScheduleOperationList1;
        this.mV3_scheduleData_biz = Mtkschedule.getInstance(this.mContext);
        this.mV3_scheduleData_biz.setOnScheduleWriteResult(this.mOnScheduleWriteResult);
        this.isBusyWriting = false;
    }

    public int getPerdaySetableNum() {
        return this.mV3_scheduleData_biz.getPerdaySetableNum();
    }

    public int getCurSetableNum() {
        return this.mV3_scheduleData_biz.getCurSetableNum();
    }

    public int getMaxSetableNum() {
        return this.mV3_scheduleData_biz.getMaxSetableNum();
    }

    public void clearBusyWrite() {
        this.isBusyWriting = false;
        this.isSyncDataAfterErr = false;
        this.isStartImmediateWrite = false;
        this.isWriteQueueState = false;
    }

    public boolean getIsBusyWriting() {
        return this.isBusyWriting;
    }

    public int getBluedToothTime() {
        return this.mV3_scheduleData_biz.getBluedToothTime();
    }

    public void setException(boolean e) {
        Editor editor = this.mContext.getSharedPreferences(sharePrefPath, 0).edit();
        if (e) {
            editor.putString(exception, noException);
        } else {
            editor.putString(exception, hasException);
        }
        editor.commit();
    }

    public boolean getNoException() {
        String err = this.mContext.getSharedPreferences(sharePrefPath, 0).getString(exception, "");
        KLog.d("同步数据", "getBluetoothErrFlag = " + err);
        if (!err.equals(hasException)) {
            return true;
        }
        KLog.d("同步数据", "getBluetoothErrFlag = bluetoothErr? :" + err);
        return false;
    }

    public int startSyncDataAfterErr() {
        updateQueueFromBaseData();
        this.isSyncDataAfterErr = true;
        this.isBusyWriting = true;
        return ClearDeviceAllData();
    }

    public boolean getIsSyncDataAfterErr() {
        return this.isSyncDataAfterErr;
    }

    public void add(TB_schedulestatue data) {
        KLog.d(TAG, "add");
        if (!this.isBusyWriting) {
            this.mScheduleOperationList1.add(new ScheduleOperation(data, 0));
            this.mCurScheduleOperationList = this.mScheduleOperationList1;
        } else if (this.mCurScheduleOperationList == this.mScheduleOperationList1) {
            this.mScheduleOperationList2.add(new ScheduleOperation(data, 0));
        } else {
            this.mScheduleOperationList1.add(new ScheduleOperation(data, 0));
        }
        runOperationList();
    }

    public void delete(TB_schedulestatue data) {
        KLog.d(TAG, "delete");
        if (!this.isBusyWriting) {
            this.mScheduleOperationList1.add(new ScheduleOperation(data, 1));
            this.mCurScheduleOperationList = this.mScheduleOperationList1;
        } else if (this.mCurScheduleOperationList == this.mScheduleOperationList1) {
            this.mScheduleOperationList2.add(new ScheduleOperation(data, 1));
        } else {
            this.mScheduleOperationList1.add(new ScheduleOperation(data, 1));
        }
        runOperationList();
    }

    public void edit(TB_schedulestatue srcData, TB_schedulestatue dstData) {
        KLog.d(TAG, "delete");
        if (!this.isBusyWriting) {
            this.mScheduleOperationList1.add(new ScheduleOperation(srcData, 1));
            this.mScheduleOperationList1.add(new ScheduleOperation(dstData, 0));
            this.mCurScheduleOperationList = this.mScheduleOperationList1;
        } else if (this.mCurScheduleOperationList == this.mScheduleOperationList1) {
            this.mScheduleOperationList2.add(new ScheduleOperation(srcData, 1));
            this.mScheduleOperationList2.add(new ScheduleOperation(dstData, 0));
        } else {
            this.mScheduleOperationList1.add(new ScheduleOperation(srcData, 1));
            this.mScheduleOperationList1.add(new ScheduleOperation(dstData, 0));
        }
        runOperationList();
    }

    private int getQueueAddNum() {
        int size = this.mCurScheduleOperationList.size();
        int num = 0;
        for (int i = 0; i < size; i++) {
            if (((ScheduleOperation) this.mCurScheduleOperationList.get(i)).operationType == 0) {
                num++;
            }
        }
        return num;
    }

    public void readScheduleInfo() {
        this.mV3_scheduleData_biz.readScheduleInfo();
    }

    private void runOperationList() {
        if (!this.isBusyWriting) {
            startImmediateWrite();
        }
    }

    private void startImmediateWrite() {
        KLog.d(TAG, "startImmediateWrite");
        this.isStartImmediateWrite = true;
        this.isBusyWriting = true;
        setException(HAS_EXCEPTION);
        this.mV3_scheduleData_biz.readScheduleInfo();
    }

    /* access modifiers changed from: private */
    public int isClearDeviceFirst() {
        int writeNum = this.mCurScheduleOperationList.size();
        if (writeNum == 0) {
            return 0;
        }
        KLog.d(TAG, "writeNum = " + writeNum);
        if (getQueueAddNum() > this.mV3_scheduleData_biz.getCurSetableNum()) {
            return 1;
        }
        return 2;
    }

    /* access modifiers changed from: private */
    public int ClearDeviceAllData() {
        return this.mV3_scheduleData_biz.clearAllSchedule();
    }

    /* access modifiers changed from: private */
    public void updateQueueFromBaseData() {
        this.mCurScheduleOperationList.clear();
        this.mScheduleOperationList1.clear();
        this.mScheduleOperationList2.clear();
        long uid = PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid);
        Calendar curCal = Calendar.getInstance();
        String dates = AddScheduleUtil.getTBDatesString(curCal.get(1), curCal.get(2) + 1, curCal.get(5));
        String times = AddScheduleUtil.getTBTimesString(curCal.get(11), curCal.get(12));
        new ArrayList();
        List<TB_schedulestatue> validDataList = DataSupport.where("UID=? AND dates>?", String.valueOf(uid), dates).find(TB_schedulestatue.class);
        new ArrayList();
        List<TB_schedulestatue> validDataList2 = DataSupport.where("UID=? AND dates=? AND times>?", String.valueOf(uid), dates, times).find(TB_schedulestatue.class);
        int size = validDataList2.size();
        for (int i = 0; i < size; i++) {
            validDataList.add(validDataList2.get(i));
        }
        int size2 = validDataList.size();
        for (int i2 = 0; i2 < size2; i2++) {
            this.mScheduleOperationList1.add(new ScheduleOperation((TB_schedulestatue) validDataList.get(i2), 0));
        }
        this.mCurScheduleOperationList = this.mScheduleOperationList1;
        KLog.d(TAG, "updateQueueFromBaseData:size = " + size2);
    }

    /* access modifiers changed from: private */
    public void startWriteQueue() {
        KLog.d(TAG, "startWriteQueue");
        this.QueueNum = this.mCurScheduleOperationList.size();
        if (this.QueueNum == 0) {
            KLog.d(TAG, "startWriteQueue 队列为空");
            this.isBusyWriting = false;
            this.mCurScheduleOperationList.clear();
            setException(NO_EXCEPTION);
            sendResult(0, 2);
            return;
        }
        this.writeQueueIndex = 0;
        this.isWriteQueueState = true;
        KLog.d(TAG, "startWriteQueue 队列不为空");
        pullQueue2Write(this.writeQueueIndex);
    }

    /* access modifiers changed from: private */
    public void writeNextQueue() {
        KLog.d(TAG, "writeNextQueue");
        this.writeQueueIndex++;
        if (this.writeQueueIndex == this.QueueNum) {
            KLog.d(TAG, "writeNextQueue 写操作完成，清空队列");
            if (this.mCurScheduleOperationList == this.mScheduleOperationList1) {
                this.mCurScheduleOperationList.clear();
                this.mScheduleOperationList1.clear();
                if (this.mScheduleOperationList2.size() > 0) {
                    this.writeQueueIndex = 0;
                    this.QueueNum = this.mScheduleOperationList2.size();
                    KLog.d(TAG, "writeNextQueue 继续写下一个");
                    this.mCurScheduleOperationList = this.mScheduleOperationList2;
                    pullQueue2Write(this.writeQueueIndex);
                    return;
                }
                stopWriteQueue();
                setException(NO_EXCEPTION);
                sendResult(0, 2);
                return;
            }
            this.mCurScheduleOperationList.clear();
            this.mScheduleOperationList2.clear();
            if (this.mScheduleOperationList1.size() > 0) {
                this.writeQueueIndex = 0;
                this.QueueNum = this.mScheduleOperationList1.size();
                KLog.d(TAG, "writeNextQueue 继续写下一个");
                this.mCurScheduleOperationList = this.mScheduleOperationList1;
                pullQueue2Write(this.writeQueueIndex);
                return;
            }
            stopWriteQueue();
            setException(NO_EXCEPTION);
            sendResult(0, 2);
            return;
        }
        KLog.d(TAG, "writeNextQueue 继续写下一个");
        pullQueue2Write(this.writeQueueIndex);
    }

    private void stopWriteQueue() {
        this.isWriteQueueState = false;
        this.isBusyWriting = false;
    }

    private void pullQueue2Write(int index) {
        KLog.d(TAG, "pullQueue2Write");
        TB_schedulestatue data = ((ScheduleOperation) this.mCurScheduleOperationList.get(index)).getData();
        int type = ((ScheduleOperation) this.mCurScheduleOperationList.get(index)).getOperationType();
        if (type == 0) {
            KLog.d(TAG, "pullQueue2Write OPERATION_TYPE_ADD");
            this.mV3_scheduleData_biz.writeSchedule(data.getYear(), data.getMonth(), data.getDay(), data.getHour(), data.getMinute(), data.getText() + data.getRemind());
        } else if (1 == type) {
            KLog.d(TAG, "pullQueue2Write OPERATION_TYPE_DELETE");
            this.mV3_scheduleData_biz.closeSchedule(data.getYear(), data.getMonth(), data.getDay(), data.getHour(), data.getMinute());
        }
    }

    public boolean getIsSupportSchedule() {
        return DeviceSettingsBiz.getInstance().supportSomeSetting(16);
    }

    public int startReadDeviceInfo() {
        KLog.d(TAG, "startReadDeviceInfo");
        this.isReadDeviceInfo = true;
        this.isBusyWriting = true;
        return this.mV3_scheduleData_biz.readDeviceInfo();
    }

    /* access modifiers changed from: private */
    public void stopReadDeviceInfo() {
        this.isReadDeviceInfo = false;
        this.isBusyWriting = false;
    }

    public void setInitDataFromTB() {
        long uid = PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid);
        Calendar curCal = Calendar.getInstance();
        String dates = AddScheduleUtil.getTBDatesString(curCal.get(1), curCal.get(2) + 1, curCal.get(5));
        String times = AddScheduleUtil.getTBTimesString(curCal.get(11), curCal.get(12));
        this.mV3_scheduleData_biz.setCurSetableNum(DataSupport.where("UID=? AND dates=? AND times>?", String.valueOf(uid), dates, times).count(TB_schedulestatue.class) + DataSupport.where("UID=? AND dates>?", String.valueOf(uid), dates).count(TB_schedulestatue.class));
    }

    public void setQueueManagerResult(IQueueManagerResultListener queueManagerResult) {
        this.mQueueManagerResult = queueManagerResult;
    }

    /* access modifiers changed from: private */
    public void sendResult(int result, int state) {
        if (this.mQueueManagerResult != null) {
            this.mQueueManagerResult.onResult(result, state);
        }
    }

    public void newScheduleBluetoothDataParseBiz() {
        this.mV3_scheduleData_biz.newScheduleBluetoothDataParseBiz();
    }

    public ScheduleBluetoothDataParseBiz getScheduleBluetoothDataParse() {
        return this.mV3_scheduleData_biz.getScheduleBluetoothDataParse();
    }
}
