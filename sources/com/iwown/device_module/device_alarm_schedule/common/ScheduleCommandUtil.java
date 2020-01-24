package com.iwown.device_module.device_alarm_schedule.common;

import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.device_module.common.BaseActionUtils;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.common.Bluetooth.receiver.zg.handler.ZGBaseUtils;
import com.iwown.device_module.common.sql.PhoneSchedule;
import com.iwown.device_module.common.sql.TB_schedulestatue;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.device_alarm_schedule.iv.manager.ScheduleWriteQueueManager;
import com.iwown.device_module.device_alarm_schedule.mtk.manager.MtkScheduleWriteQueueManager;
import com.iwown.device_module.device_alarm_schedule.pb.PbScheduleUtil;
import com.iwown.lib_common.date.DateUtil;
import com.socks.library.KLog;
import org.litepal.crud.DataSupport;

public class ScheduleCommandUtil {
    public static ScheduleCommandUtil instance;

    private ScheduleCommandUtil() {
    }

    public static ScheduleCommandUtil getInstance() {
        if (instance == null) {
            instance = new ScheduleCommandUtil();
        }
        return instance;
    }

    public static void add(TB_schedulestatue data) {
        if (BluetoothOperation.isIv()) {
            ScheduleWriteQueueManager.getInstance(ContextUtil.app).add(data);
        } else if (BluetoothOperation.isZg()) {
            if (!(ZGBaseUtils.alarm_mode1 == -1 || ZGBaseUtils.alarm_number1 == -1)) {
                data.setZg_mode(ZGBaseUtils.alarm_mode1);
                data.setZg_number(ZGBaseUtils.alarm_number1);
                data.save();
            }
            ZGBaseUtils.setAlarmScheduleModeNumber(-1, -1);
            ZGBaseUtils.updateAlarmAndSchedule(ContextUtil.app);
        } else if (BluetoothOperation.isMtk()) {
            MtkScheduleWriteQueueManager.getInstance(ContextUtil.app).add(data);
        } else if (BluetoothOperation.isProtoBuf()) {
            PbScheduleUtil.add(data);
        }
    }

    public static void delete(TB_schedulestatue data) {
        try {
            DateUtil dateUtil = new DateUtil(data.getYear(), data.getMonth(), data.getDay(), data.getHour(), data.getMinute());
            KLog.i("====" + String.valueOf(dateUtil.getTimestamp()));
            DataSupport.deleteAll(PhoneSchedule.class, "uid= ? and time = ?", ContextUtil.getUID(), String.valueOf(dateUtil.getUnixTimestamp() * 1000));
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
        if (BluetoothOperation.isIv()) {
            ScheduleWriteQueueManager.getInstance(ContextUtil.app).delete(data);
        } else if (BluetoothOperation.isZg()) {
            ZGBaseUtils.updateAlarmAndSchedule(ContextUtil.app);
        } else if (BluetoothOperation.isMtk()) {
            MtkScheduleWriteQueueManager.getInstance(ContextUtil.app).delete(data);
        } else if (BluetoothOperation.isProtoBuf()) {
            PbScheduleUtil.delete(data);
        }
    }

    public static void edit(TB_schedulestatue srcData, TB_schedulestatue dstData) {
        if (BluetoothOperation.isIv()) {
            ScheduleWriteQueueManager.getInstance(ContextUtil.app).edit(srcData, dstData);
        } else if (BluetoothOperation.isZg()) {
            if (!(ZGBaseUtils.alarm_mode1 == -1 || ZGBaseUtils.alarm_number1 == -1)) {
                dstData.setZg_mode(ZGBaseUtils.alarm_mode1);
                dstData.setZg_number(ZGBaseUtils.alarm_number1);
            }
            dstData.save();
            ZGBaseUtils.setAlarmScheduleModeNumber(-1, -1);
            ZGBaseUtils.updateAlarmAndSchedule(ContextUtil.app);
        } else if (BluetoothOperation.isMtk()) {
            MtkScheduleWriteQueueManager.getInstance(ContextUtil.app).edit(srcData, dstData);
        } else if (BluetoothOperation.isProtoBuf()) {
            PbScheduleUtil.edit(srcData, dstData);
        }
    }

    public boolean getIsBusyWriting() {
        if (BluetoothOperation.isIv()) {
            return ScheduleWriteQueueManager.getInstance(ContextUtil.app).getIsBusyWriting();
        }
        if (BluetoothOperation.isMtk()) {
            return MtkScheduleWriteQueueManager.getInstance(ContextUtil.app).getIsBusyWriting();
        }
        return false;
    }

    public void readScheduleInfo() {
        if (BluetoothOperation.isIv()) {
            ScheduleWriteQueueManager.getInstance(ContextUtil.app).readScheduleInfo();
        } else if (BluetoothOperation.isMtk()) {
            MtkScheduleWriteQueueManager.getInstance(ContextUtil.app).readScheduleInfo();
        }
    }

    public int getPerdaySetableNum() {
        if (BluetoothOperation.isIv()) {
            return ScheduleWriteQueueManager.getInstance(ContextUtil.app).getPerdaySetableNum();
        }
        if (BluetoothOperation.isMtk()) {
            return MtkScheduleWriteQueueManager.getInstance(ContextUtil.app).getPerdaySetableNum();
        }
        if (BluetoothOperation.isZg()) {
        }
        return 4;
    }

    public int getMaxSetableNum() {
        if (BluetoothOperation.isIv()) {
            return ScheduleWriteQueueManager.getInstance(ContextUtil.app).getMaxSetableNum();
        }
        if (BluetoothOperation.isMtk()) {
            return MtkScheduleWriteQueueManager.getInstance(ContextUtil.app).getMaxSetableNum();
        }
        return 4;
    }

    public int startSyncDataAfterErr() {
        if (BluetoothOperation.isIv()) {
            return ScheduleWriteQueueManager.getInstance(ContextUtil.app).startSyncDataAfterErr();
        }
        if (BluetoothOperation.isMtk()) {
            return MtkScheduleWriteQueueManager.getInstance(ContextUtil.app).startSyncDataAfterErr();
        }
        return 0;
    }

    public boolean getIsSyncDataAfterErr() {
        if (BluetoothOperation.isIv()) {
            return ScheduleWriteQueueManager.getInstance(ContextUtil.app).getIsSyncDataAfterErr();
        }
        if (BluetoothOperation.isMtk()) {
            return MtkScheduleWriteQueueManager.getInstance(ContextUtil.app).getIsSyncDataAfterErr();
        }
        return false;
    }

    public int getBluedToothTime() {
        if (BluetoothOperation.isIv()) {
            return ScheduleWriteQueueManager.getInstance(ContextUtil.app).getBluedToothTime();
        }
        if (BluetoothOperation.isMtk()) {
            return MtkScheduleWriteQueueManager.getInstance(ContextUtil.app).getBluedToothTime();
        }
        return 6;
    }

    public void setInitDataFromTB() {
        if (BluetoothOperation.isIv()) {
            ScheduleWriteQueueManager.getInstance(ContextUtil.app).setInitDataFromTB();
        } else if (BluetoothOperation.isMtk()) {
            MtkScheduleWriteQueueManager.getInstance(ContextUtil.app).setInitDataFromTB();
        }
    }

    public void clearBusyWrite() {
        if (BluetoothOperation.isIv()) {
            ScheduleWriteQueueManager.getInstance(ContextUtil.app).clearBusyWrite();
        } else if (BluetoothOperation.isMtk()) {
            MtkScheduleWriteQueueManager.getInstance(ContextUtil.app).clearBusyWrite();
        }
    }

    public int startReadDeviceInfo() {
        if (BluetoothOperation.isIv()) {
            return ScheduleWriteQueueManager.getInstance(ContextUtil.app).startReadDeviceInfo();
        }
        if (BluetoothOperation.isMtk()) {
            return MtkScheduleWriteQueueManager.getInstance(ContextUtil.app).startReadDeviceInfo();
        }
        return 0;
    }

    public boolean getIsSupportSchedule() {
        if (BluetoothOperation.isIv()) {
            return ScheduleWriteQueueManager.getInstance(ContextUtil.app).getIsSupportSchedule();
        }
        if (BluetoothOperation.isMtk()) {
            return MtkScheduleWriteQueueManager.getInstance(ContextUtil.app).getIsSupportSchedule();
        }
        if (BluetoothOperation.isZg() || BluetoothOperation.isProtoBuf()) {
            return true;
        }
        return false;
    }

    public boolean getNoException() {
        if (BluetoothOperation.isIv()) {
            return ScheduleWriteQueueManager.getInstance(ContextUtil.app).getNoException();
        }
        if (BluetoothOperation.isMtk()) {
            return MtkScheduleWriteQueueManager.getInstance(ContextUtil.app).getNoException();
        }
        if (BluetoothOperation.isZg() || BluetoothOperation.isProtoBuf()) {
            return true;
        }
        return false;
    }

    public void newScheduleBluetoothDataParseBiz() {
        if (BluetoothOperation.isIv()) {
            ScheduleWriteQueueManager.getInstance(ContextUtil.app).newScheduleBluetoothDataParseBiz();
        } else if (BluetoothOperation.isMtk()) {
            MtkScheduleWriteQueueManager.getInstance(ContextUtil.app).newScheduleBluetoothDataParseBiz();
        }
    }

    public void registerReceiver() {
        if (BluetoothOperation.isIv()) {
            IntentFilter intentFilter = BaseActionUtils.getIntentFilter();
            LocalBroadcastManager.getInstance(ContextUtil.app).registerReceiver(ScheduleWriteQueueManager.getInstance(ContextUtil.app).getScheduleBluetoothDataParse(), intentFilter);
        } else if (BluetoothOperation.isMtk()) {
            IntentFilter intentFilter2 = BaseActionUtils.getIntentFilter();
            LocalBroadcastManager.getInstance(ContextUtil.app).registerReceiver(MtkScheduleWriteQueueManager.getInstance(ContextUtil.app).getScheduleBluetoothDataParse(), intentFilter2);
        }
    }

    public void unRegisterReceiver() {
        if (BluetoothOperation.isIv()) {
            LocalBroadcastManager.getInstance(ContextUtil.app).unregisterReceiver(ScheduleWriteQueueManager.getInstance(ContextUtil.app).getScheduleBluetoothDataParse());
        } else if (BluetoothOperation.isMtk()) {
            LocalBroadcastManager.getInstance(ContextUtil.app).unregisterReceiver(MtkScheduleWriteQueueManager.getInstance(ContextUtil.app).getScheduleBluetoothDataParse());
        }
    }

    public void setQueueManagerResult(IQueueManagerResultListener queueManagerResult) {
        if (BluetoothOperation.isIv()) {
            ScheduleWriteQueueManager.getInstance(ContextUtil.app).setQueueManagerResult(queueManagerResult);
        } else if (BluetoothOperation.isMtk()) {
            MtkScheduleWriteQueueManager.getInstance(ContextUtil.app).setQueueManagerResult(queueManagerResult);
        } else {
            if (BluetoothOperation.isZg()) {
            }
        }
    }
}
