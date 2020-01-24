package com.iwown.device_module.device_alarm_schedule.iv.biz;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import com.iwown.ble_module.iwown.cmd.ZeronerAlarmClockScheduleHandler.ScheduleBean;
import com.iwown.ble_module.iwown.cmd.ZeronerSendBluetoothCmdImpl;
import com.iwown.ble_module.iwown.task.DataBean;
import com.iwown.ble_module.iwown.task.ZeronerBackgroundThreadManager;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.common.Bluetooth.receiver.BluetoothCallbackReceiver;
import com.iwown.device_module.common.sql.TB_schedulestatue;
import com.iwown.device_module.common.utils.JsonUtils;
import com.iwown.my_module.utility.Constants.ServiceErrorCode;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.litepal.crud.DataSupport;

public class V3_scheduleData_biz {
    private static final int BLUETOOTH_READ_TYPE_DEVICEINFO = 6;
    private static final int BLUETOOTH_READ_TYPE_INFO = 4;
    private static final int BLUETOOTH_READ_TYPE_SCHEDULE = 5;
    private static final int BLUETOOTH_TYPE_NULL = 0;
    private static final int BLUETOOTH_WRITE_TYPE_CLEARSCHEDULE = 2;
    private static final int BLUETOOTH_WRITE_TYPE_CLOSESCHEDULE = 3;
    private static final int BLUETOOTH_WRITE_TYPE_WRITESCHEDULE = 1;
    public static final int SCHEDULE_RESULT_BLUETOOTH_ERR = 5;
    public static final int SCHEDULE_RESULT_FAIL = 0;
    public static final int SCHEDULE_RESULT_RECEIVE_TIMEOUT = 6;
    public static final int SCHEDULE_RESULT_SUCCESS = 1;
    public static final int SCHEDULE_STATE_CLEAR = 3;
    public static final int SCHEDULE_STATE_CLOSE = 4;
    public static final int SCHEDULE_STATE_INFO = 0;
    public static final int SCHEDULE_STATE_NULL = 1000;
    public static final int SCHEDULE_STATE_READ_DEVICE_INFO = 7;
    public static final int SCHEDULE_STATE_SCHEDULE = 1;
    public static final int SCHEDULE_STATE_SET = 2;
    public static final int STATE_BLUETOOTH_BREAK = 1;
    public static final int STATE_BLUETOOTH_CONNECT = 0;
    public static final int STATE_SCHEDULE_BLUETOOTH_BUSYING = 2;
    public static final int STATE_SCHEDULE_BLUETOOTH_NOCONNECT = 1;
    public static final int STATE_SCHEDULE_BLUETOOTH_SUCCESS = 0;
    /* access modifiers changed from: private */
    public static String TAG = "iv_Schedule";
    private static V3_scheduleData_biz mV3_scheduleData_biz;
    private final int HANDLER_RECEIVER_MSG_TIMEOUT = 1;
    /* access modifiers changed from: private */
    public int bluetoothStateType = 0;
    /* access modifiers changed from: private */
    public int curSenderState = 1000;
    /* access modifiers changed from: private */
    public int curSetableNum = 32;
    /* access modifiers changed from: private */
    public Handler handlerReceiveTimer = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    KLog.d(V3_scheduleData_biz.TAG, "蓝牙超时计数器:curSenderState = " + V3_scheduleData_biz.this.curSenderState + ",secReceiveTimer = " + V3_scheduleData_biz.this.secReceiveTimer);
                    if (V3_scheduleData_biz.this.mOnScheduleWriteResult != null) {
                        V3_scheduleData_biz.this.mOnScheduleWriteResult.onResult(6, V3_scheduleData_biz.this.curSenderState);
                    }
                    V3_scheduleData_biz.this.stopReceiveTimer();
                    break;
            }
            super.handleMessage(msg);
        }
    };
    /* access modifiers changed from: private */
    public boolean isRunReceiveTimer = false;
    private Context mContext;
    /* access modifiers changed from: private */
    public OnScheduleWriteResult mOnScheduleWriteResult;
    private ScheduleBluetoothDataParseBiz mScheduleBluetoothDataParseBiz = null;
    /* access modifiers changed from: private */
    public int maxSetableNum = 32;
    /* access modifiers changed from: private */
    public int perdaySetableNum = 4;
    private byte readScheduleHeader;
    private final int secMaxReceiveTime = ServiceErrorCode.YOU_AND_ME_IS_FRIEND;
    /* access modifiers changed from: private */
    public int secReceiveTimer = 0;
    long timScheClear = 0;
    long timScheClose = 0;
    long timScheInfo = 0;
    long timScheRead = 0;
    long timScheSet = 0;
    private byte writeScheduleHeader;

    public interface OnScheduleWriteResult {
        void onResult(int i, int i2);
    }

    class ReceiveTimer implements Runnable {
        ReceiveTimer() {
        }

        public void run() {
            while (V3_scheduleData_biz.this.isRunReceiveTimer) {
                try {
                    Thread.sleep(100);
                    V3_scheduleData_biz.this.secReceiveTimer = V3_scheduleData_biz.this.secReceiveTimer + 1;
                    if (V3_scheduleData_biz.this.secReceiveTimer >= 600) {
                        Message msg = new Message();
                        msg.what = 1;
                        V3_scheduleData_biz.this.handlerReceiveTimer.sendMessage(msg);
                    }
                } catch (Exception e) {
                    KLog.d(V3_scheduleData_biz.TAG, "ReceiveTimer Exception:" + e.toString());
                }
            }
        }
    }

    public class ScheduleBluetoothDataParseBiz extends BluetoothCallbackReceiver {
        public ScheduleBluetoothDataParseBiz() {
        }

        public void connectStatue(boolean isConnect) {
            if (!isConnect) {
                KLog.d(V3_scheduleData_biz.TAG, "蓝牙状态改变：" + isConnect);
                if (V3_scheduleData_biz.this.bluetoothStateType != 0) {
                    KLog.d(V3_scheduleData_biz.TAG, "发送过程中蓝牙断开");
                    V3_scheduleData_biz.this.sendResult(5, V3_scheduleData_biz.this.curSenderState);
                    V3_scheduleData_biz.this.stopReceiveTimer();
                    return;
                }
                V3_scheduleData_biz.this.sendResult(5, 1);
                return;
            }
            V3_scheduleData_biz.this.sendResult(5, 0);
        }

        public void onDataArrived(Context context, int ble_sdk_type, int dataType, String data) {
            super.onDataArrived(context, ble_sdk_type, dataType, data);
            switch (dataType) {
                case 29:
                    int success = JsonUtils.getInt(data, "success");
                    KLog.d(V3_scheduleData_biz.TAG, "接收到数据：(1表示成功，0表示失败)设置日程 = " + success);
                    if (V3_scheduleData_biz.this.bluetoothStateType == 1) {
                        V3_scheduleData_biz.this.timScheSet = System.nanoTime() - V3_scheduleData_biz.this.timScheSet;
                        KLog.d(V3_scheduleData_biz.TAG, "时间测试:设置日程 = " + String.valueOf(TimeUnit.NANOSECONDS.toMillis(V3_scheduleData_biz.this.timScheSet)) + " ms");
                        KLog.d(V3_scheduleData_biz.TAG, "设置日程:" + success);
                        V3_scheduleData_biz.this.resetStateParam();
                        V3_scheduleData_biz.this.stopReceiveTimer();
                        V3_scheduleData_biz.this.sendResult(success, 2);
                        return;
                    } else if (V3_scheduleData_biz.this.bluetoothStateType == 2) {
                        V3_scheduleData_biz.this.timScheClear = System.nanoTime() - V3_scheduleData_biz.this.timScheClear;
                        KLog.d(V3_scheduleData_biz.TAG, "时间测试:清空日程 = " + String.valueOf(TimeUnit.NANOSECONDS.toMillis(V3_scheduleData_biz.this.timScheClear)) + " ms");
                        KLog.d(V3_scheduleData_biz.TAG, "清空日程:" + success);
                        V3_scheduleData_biz.this.resetStateParam();
                        V3_scheduleData_biz.this.stopReceiveTimer();
                        KLog.d(V3_scheduleData_biz.TAG, "sendResult:SCHEDULE_STATE_CLEAR");
                        V3_scheduleData_biz.this.sendResult(success, 3);
                        return;
                    } else if (V3_scheduleData_biz.this.bluetoothStateType == 3) {
                        V3_scheduleData_biz.this.timScheClose = System.nanoTime() - V3_scheduleData_biz.this.timScheClose;
                        KLog.d(V3_scheduleData_biz.TAG, "时间测试:关闭日程 = " + String.valueOf(TimeUnit.NANOSECONDS.toMillis(V3_scheduleData_biz.this.timScheClose)) + " ms");
                        KLog.d(V3_scheduleData_biz.TAG, "关闭日程:" + success);
                        V3_scheduleData_biz.this.resetStateParam();
                        V3_scheduleData_biz.this.stopReceiveTimer();
                        V3_scheduleData_biz.this.sendResult(1, 4);
                        return;
                    } else {
                        return;
                    }
                case 30:
                    KLog.d(V3_scheduleData_biz.TAG, "接收到数据：读取日程");
                    if (4 == V3_scheduleData_biz.this.bluetoothStateType) {
                        ScheduleBean scheduleBean = (ScheduleBean) JsonUtils.fromJson(data, ScheduleBean.class);
                        V3_scheduleData_biz.this.curSetableNum = scheduleBean.curSetableNum;
                        V3_scheduleData_biz.this.maxSetableNum = scheduleBean.maxSetableNum;
                        V3_scheduleData_biz.this.perdaySetableNum = scheduleBean.perdaySetableNum;
                        V3_scheduleData_biz.this.timScheInfo = System.nanoTime() - V3_scheduleData_biz.this.timScheInfo;
                        KLog.d(V3_scheduleData_biz.TAG, "时间测试:读取日程信息 = " + String.valueOf(TimeUnit.NANOSECONDS.toMillis(V3_scheduleData_biz.this.timScheInfo)) + " ms");
                        KLog.d(V3_scheduleData_biz.TAG, "curSetableNum = " + V3_scheduleData_biz.this.curSetableNum + ",maxSetableNum = " + V3_scheduleData_biz.this.maxSetableNum + ",perdaySetableNum = " + V3_scheduleData_biz.this.perdaySetableNum);
                        V3_scheduleData_biz.this.resetStateParam();
                        V3_scheduleData_biz.this.stopReceiveTimer();
                        V3_scheduleData_biz.this.sendResult(1, 0);
                        return;
                    } else if (5 == V3_scheduleData_biz.this.bluetoothStateType) {
                        KLog.e("ScheduleBluetoothDataParseBiz 读取指定日程  不应该进入这个方法");
                        return;
                    } else {
                        return;
                    }
                default:
                    return;
            }
        }
    }

    public static synchronized V3_scheduleData_biz getInstance(Context context) {
        V3_scheduleData_biz v3_scheduleData_biz;
        synchronized (V3_scheduleData_biz.class) {
            if (mV3_scheduleData_biz == null) {
                mV3_scheduleData_biz = new V3_scheduleData_biz(context);
            }
            v3_scheduleData_biz = mV3_scheduleData_biz;
        }
        return v3_scheduleData_biz;
    }

    private V3_scheduleData_biz(Context context) {
        this.mContext = context;
        KLog.i("ScheduleManager", "删除了过期日程数量：" + deleteScheduleBefore());
    }

    public void setCurSetableNum(int num) {
        this.curSetableNum = num;
    }

    public void setMaxSetableNum(int maxSetableNum2) {
        this.maxSetableNum = maxSetableNum2;
    }

    public int getMaxSetableNum() {
        return this.maxSetableNum;
    }

    public int getPerdaySetableNum() {
        return this.perdaySetableNum;
    }

    public int getCurSetableNum() {
        return this.curSetableNum;
    }

    public boolean getIsBluetoothState() {
        return BluetoothOperation.isConnected();
    }

    /* access modifiers changed from: private */
    public void resetStateParam() {
        this.bluetoothStateType = 0;
    }

    private byte int2byte(int integer) {
        return (byte) (integer & 255);
    }

    /* access modifiers changed from: private */
    public void sendResult(int result, int state) {
        if (this.mOnScheduleWriteResult != null) {
        }
        KLog.d(TAG, "sendResult result = " + result + ", state = " + state);
        this.mOnScheduleWriteResult.onResult(result, state);
    }

    public int getBluedToothTime() {
        return (600 - this.secReceiveTimer) / 100;
    }

    private void startReceiveTimer(int state) {
        KLog.d(TAG, "启动蓝牙超时计数器");
        this.curSenderState = state;
        this.secReceiveTimer = 0;
        this.isRunReceiveTimer = true;
        new Thread(new ReceiveTimer()).start();
    }

    /* access modifiers changed from: private */
    public void stopReceiveTimer() {
        KLog.d(TAG, "关闭蓝牙超时计数器");
        this.curSenderState = 1000;
        this.isRunReceiveTimer = false;
        resetStateParam();
    }

    public void readDeviceInfoNoResponse() {
        KLog.d(TAG, "readDeviceInfoNoResponse");
        byte[] data = ZeronerSendBluetoothCmdImpl.getInstance().getFirmwareInformation();
        DataBean dataBean = new DataBean();
        dataBean.addData(data);
        ZeronerBackgroundThreadManager.getInstance().addWriteData(this.mContext, dataBean);
        KLog.d(TAG, "readDeviceInfo 发送指令");
    }

    public int readDeviceInfo() {
        KLog.d(TAG, "readDeviceInfo");
        if (getIsBluetoothState()) {
            this.bluetoothStateType = 6;
            byte[] data = ZeronerSendBluetoothCmdImpl.getInstance().getFirmwareInformation();
            DataBean dataBean = new DataBean();
            dataBean.addData(data);
            ZeronerBackgroundThreadManager.getInstance().addWriteData(this.mContext, dataBean);
            startReceiveTimer(7);
            KLog.d(TAG, "readDeviceInfo 发送指令");
            return 0;
        }
        KLog.d(TAG, "readDeviceInfo 蓝牙断开");
        this.bluetoothStateType = 0;
        sendResult(5, 7);
        return 1;
    }

    public void writeScheduleNoResponse(int year, int month, int day, int hour, int minute, String text) {
        ZeronerSendBluetoothCmdImpl.getInstance().setSchedule(this.mContext, year, month, day, hour, minute, text);
    }

    public int writeSchedule(int year, int month, int day, int hour, int minute, String text) {
        if (getIsBluetoothState()) {
            ZeronerSendBluetoothCmdImpl.getInstance().setSchedule(this.mContext, year, month, day, hour, minute, text);
            this.timScheSet = System.nanoTime();
            this.bluetoothStateType = 1;
            startReceiveTimer(2);
            return 0;
        }
        resetStateParam();
        sendResult(5, 2);
        return 1;
    }

    public int clearAllSchedule() {
        KLog.d(TAG, "clearAllSchedule");
        if (getIsBluetoothState()) {
            KLog.d(TAG, "clearAllSchedule:isBluetoothState");
            ZeronerSendBluetoothCmdImpl.getInstance().clearAllSchedule(this.mContext);
            this.bluetoothStateType = 2;
            startReceiveTimer(3);
            return 0;
        }
        KLog.d(TAG, "clearAllSchedule:蓝牙断开");
        resetStateParam();
        sendResult(5, 3);
        return 1;
    }

    public int closeSchedule(int year, int month, int day, int hour, int minute) {
        if (getIsBluetoothState()) {
            KLog.d(TAG, "删除 开始时间测试");
            this.timScheClose = System.nanoTime();
            ZeronerSendBluetoothCmdImpl.getInstance().closeSchedule(this.mContext, year, month, day, hour, minute);
            this.bluetoothStateType = 3;
            startReceiveTimer(4);
            return 0;
        }
        resetStateParam();
        sendResult(5, 4);
        return 1;
    }

    public int readScheduleInfo() {
        KLog.d(TAG, "readScheduleInfo");
        if (getIsBluetoothState()) {
            KLog.d(TAG, "readScheduleInfo isBluetoothState");
            new ArrayList<>().add(new byte[]{0});
            ZeronerSendBluetoothCmdImpl.getInstance().readScheduleInfo(this.mContext);
            this.timScheInfo = System.nanoTime();
            this.bluetoothStateType = 4;
            startReceiveTimer(0);
            return 0;
        }
        resetStateParam();
        sendResult(5, 0);
        return 1;
    }

    public int readSchedule(int year, int month, int day, int hour, int minute) {
        KLog.d(TAG, "readSchedule");
        KLog.d(TAG, "bluetoothReadType");
        if (getIsBluetoothState()) {
            KLog.d(TAG, "isBluetoothState");
            new ArrayList<>().add(new byte[]{1, int2byte(year - 2000), int2byte(month - 1), int2byte(day - 1), int2byte(hour), int2byte(minute)});
            ZeronerSendBluetoothCmdImpl.getInstance().readScheduleInfo(this.mContext);
            this.timScheRead = System.nanoTime();
            this.bluetoothStateType = 5;
            startReceiveTimer(1);
            return 0;
        }
        resetStateParam();
        sendResult(5, 1);
        return 1;
    }

    public void setOnScheduleWriteResult(OnScheduleWriteResult onScheduleWriteResult) {
        this.mOnScheduleWriteResult = onScheduleWriteResult;
    }

    public void newScheduleBluetoothDataParseBiz() {
        if (this.mScheduleBluetoothDataParseBiz == null) {
            this.mScheduleBluetoothDataParseBiz = new ScheduleBluetoothDataParseBiz();
        }
    }

    public ScheduleBluetoothDataParseBiz getScheduleBluetoothDataParse() {
        if (getScheduleBluetoothDataParseBiz()) {
            return this.mScheduleBluetoothDataParseBiz;
        }
        return new ScheduleBluetoothDataParseBiz();
    }

    public boolean getScheduleBluetoothDataParseBiz() {
        return this.mScheduleBluetoothDataParseBiz != null;
    }

    public static int deleteScheduleBefore() {
        List<TB_schedulestatue> tb_schedulestatues = DataSupport.findAll(TB_schedulestatue.class, new long[0]);
        int delcnt = 0;
        if (tb_schedulestatues == null || tb_schedulestatues.size() == 0) {
            return 0;
        }
        for (int i = 0; i < tb_schedulestatues.size(); i++) {
            TB_schedulestatue tb_schedulestatue = (TB_schedulestatue) tb_schedulestatues.get(i);
            Calendar calendar = Calendar.getInstance();
            calendar.set(tb_schedulestatue.getYear(), tb_schedulestatue.getMonth() - 1, tb_schedulestatue.getDay(), tb_schedulestatue.getHour(), tb_schedulestatue.getMinute());
            if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
                delcnt++;
                tb_schedulestatue.delete();
            }
        }
        return delcnt;
    }
}
