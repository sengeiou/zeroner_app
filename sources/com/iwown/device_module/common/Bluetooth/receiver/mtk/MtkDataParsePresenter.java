package com.iwown.device_module.common.Bluetooth.receiver.mtk;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.google.gson.Gson;
import com.iwown.ble_module.iwown.bean.KeyModel;
import com.iwown.ble_module.iwown.utils.Constants;
import com.iwown.ble_module.model.ECG_Data;
import com.iwown.ble_module.model.FMdeviceInfo;
import com.iwown.ble_module.model.GnssMinData;
import com.iwown.ble_module.model.GnssMinData.Gnss;
import com.iwown.ble_module.model.HealthDailyData;
import com.iwown.ble_module.model.HealthMinData;
import com.iwown.ble_module.model.IWBleParams;
import com.iwown.ble_module.model.ProductInfo;
import com.iwown.ble_module.model.R1HealthMinuteData;
import com.iwown.ble_module.mtk_ble.cmd.MtkCmdAssembler;
import com.iwown.data_link.eventbus.HaveGetModelEvent;
import com.iwown.data_link.eventbus.HealthDataEventBus;
import com.iwown.data_link.eventbus.RealTimeHREvent;
import com.iwown.data_link.eventbus.ViewRefresh;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.device_module.common.BaseActionUtils.BleAction;
import com.iwown.device_module.common.BaseActionUtils.FirmwareAction;
import com.iwown.device_module.common.BaseActionUtils.KeyCodeAction;
import com.iwown.device_module.common.BaseActionUtils.SETTING_INDEXS;
import com.iwown.device_module.common.BaseActionUtils.UserAction;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.common.Bluetooth.receiver.BluetoothDataParseReceiver;
import com.iwown.device_module.common.Bluetooth.receiver.iv.bean.CurrData_0x29;
import com.iwown.device_module.common.Bluetooth.receiver.mtk.bean.Ble61DataParse;
import com.iwown.device_module.common.Bluetooth.receiver.mtk.bean.Ble62DataParse;
import com.iwown.device_module.common.Bluetooth.receiver.mtk.bean.Ble64DataParse;
import com.iwown.device_module.common.Bluetooth.receiver.mtk.bean.Ble68DataParse;
import com.iwown.device_module.common.Bluetooth.receiver.mtk.bean.LongitudeAndLatitude;
import com.iwown.device_module.common.Bluetooth.receiver.mtk.bean.MtkDevSetting;
import com.iwown.device_module.common.Bluetooth.receiver.mtk.dao.Mtk_DeviceBaseInfoSqlUtil;
import com.iwown.device_module.common.Bluetooth.receiver.mtk.utils.DataUtil;
import com.iwown.device_module.common.Bluetooth.receiver.mtk.utils.MtkDataToServer;
import com.iwown.device_module.common.Bluetooth.receiver.mtk.utils.MtkToIvHandler;
import com.iwown.device_module.common.Bluetooth.sync.mtk.MTKHeadSetSync;
import com.iwown.device_module.common.Bluetooth.sync.mtk.MtkSync;
import com.iwown.device_module.common.network.NetFactory;
import com.iwown.device_module.common.network.data.req.DeviceSettingsSend;
import com.iwown.device_module.common.network.data.req.FactoryVersion;
import com.iwown.device_module.common.network.data.req.UserDeviceReq;
import com.iwown.device_module.common.sql.Mtk_DeviceBaseInfo;
import com.iwown.device_module.common.sql.TB_60_data;
import com.iwown.device_module.common.sql.TB_61_data;
import com.iwown.device_module.common.sql.TB_62_data;
import com.iwown.device_module.common.sql.TB_64_data;
import com.iwown.device_module.common.sql.TB_64_index_table;
import com.iwown.device_module.common.sql.TB_68_data;
import com.iwown.device_module.common.sql.TB_f1_index;
import com.iwown.device_module.common.sql.TB_mtk_statue;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.JsonUtils;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.device_module.device_operation.eventbus.BleConnectStatue;
import com.iwown.device_module.device_setting.configure.DeviceSettingLocal;
import com.iwown.device_module.device_setting.configure.DeviceSettingsBiz;
import com.iwown.device_module.device_setting.configure.DeviceUtils;
import com.iwown.device_module.device_setting.configure.eventbus.UpdateConfigUI;
import com.iwown.device_module.device_setting.heart.bean.AutoHeartStatue;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.json.JsonTool;
import com.iwown.lib_common.log.L;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import no.nordicsemi.android.dfu.internal.scanner.BootloaderScanner;
import org.apache.commons.cli.HelpFormatter;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

public class MtkDataParsePresenter {
    private static String TAG = "MtkDataParsePresenter";
    public static final int Type = 2;
    static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
    /* access modifiers changed from: private */
    public static String mDeviceName = null;
    /* access modifiers changed from: private */
    public static Handler mHandler = new Handler(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public static long mUid = 0;
    public static Map<String, Integer> map62 = new HashMap();
    /* access modifiers changed from: private */
    public static int[] months = new int[3];
    static int number = 0;
    static Runnable sync61Runnable = new Runnable() {
        public void run() {
            MtkDataParsePresenter.fixedThreadPool.submit(new Runnable() {
                public void run() {
                    MtkDataParsePresenter.mHandler.removeCallbacks(MtkDataParsePresenter.sync61Runnable);
                    KLog.e("testf1shuju111数据接收超时或者接收完毕发送其他命令: ");
                    if (!MtkSync.getInstance().isOver()) {
                        if (MtkSync.getInstance().isOneDayOver()) {
                            MtkDataParsePresenter.number++;
                            KLog.e("61DataToIv", "数据开始存为其他表:" + MtkDataParsePresenter.months[0] + HelpFormatter.DEFAULT_OPT_PREFIX + MtkDataParsePresenter.months[1] + HelpFormatter.DEFAULT_OPT_PREFIX + MtkDataParsePresenter.months[2]);
                            MtkDataParsePresenter.Data61ToOther(PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid), MtkDataParsePresenter.months[0], MtkDataParsePresenter.months[1], MtkDataParsePresenter.months[2], ContextUtil.getDeviceNameNoClear());
                            if (MtkDataParsePresenter.number == 1) {
                                KLog.i("=====sync one day=====" + (MtkDataParsePresenter.number == 1));
                                EventBus.getDefault().post(new ViewRefresh(false, 40));
                                HealthDataEventBus.updateAllUI();
                            }
                            if (MtkDataParsePresenter.number == 2) {
                                MtkDataToServer.syncSaveTodayCmd();
                                KLog.e("=========number==2======");
                            }
                        }
                        if (!MtkSync.getInstance().sync_P1_data()) {
                            L.file("no2855 61数据准备写入文件:" + MtkDataParsePresenter.months[0] + HelpFormatter.DEFAULT_OPT_PREFIX + MtkDataParsePresenter.months[1] + HelpFormatter.DEFAULT_OPT_PREFIX + MtkDataParsePresenter.months[2], 3);
                            KLog.e("no2855--> 61数据准备写入文件:" + MtkDataParsePresenter.months[0] + HelpFormatter.DEFAULT_OPT_PREFIX + MtkDataParsePresenter.months[1] + HelpFormatter.DEFAULT_OPT_PREFIX + MtkDataParsePresenter.months[2]);
                            MtkDataToServer.upCmd62ToServer();
                            MtkDataToServer.syncUpCmdToServer();
                            MtkDataParsePresenter.number = 0;
                        } else {
                            String mDay = new DateUtil(MtkDataParsePresenter.months[0], MtkDataParsePresenter.months[1], MtkDataParsePresenter.months[2]).getSyyyyMMddDate();
                            KLog.e("no2855 61数据不能写入文件: " + mDay);
                            L.file("no2855 61数据不能写入文件: " + mDay, 3);
                            if (MtkSync.getInstance().getEndDay().equals(mDay)) {
                                L.file("no2855 61数据已是最后一天，写入文件 : " + mDay, 3);
                                KLog.e("no2855--> 61数据已是最后一天，写入文件 : " + mDay);
                                MtkDataToServer.upCmd62ToServer();
                                MtkDataToServer.syncUpCmdToServer();
                            }
                        }
                        MtkDataParsePresenter.mHandler.postDelayed(MtkDataParsePresenter.sync61Runnable, 15000);
                    }
                }
            });
        }
    };
    static Runnable sync68Runnable = new Runnable() {
        public void run() {
            MtkDataParsePresenter.fixedThreadPool.submit(new Runnable() {
                public void run() {
                    MtkDataParsePresenter.mHandler.removeCallbacks(MtkDataParsePresenter.sync68Runnable);
                    KLog.e("licldltestf1shuju111", "数据接收超时或者接收完毕发送其他命令: ");
                    if (!MtkSync.getInstance().isOver()) {
                        if (MtkSync.getInstance().isOneDayOver()) {
                            MtkDataParsePresenter.number++;
                            KLog.e("68DataToIv", "数据开始存为其他表:" + MtkDataParsePresenter.months[0] + HelpFormatter.DEFAULT_OPT_PREFIX + MtkDataParsePresenter.months[1] + HelpFormatter.DEFAULT_OPT_PREFIX + MtkDataParsePresenter.months[2]);
                            if (MtkDataParsePresenter.number == 1) {
                                KLog.i("=====sync one day=====" + (MtkDataParsePresenter.number == 1));
                                EventBus.getDefault().post(new ViewRefresh(false, 40));
                                HealthDataEventBus.updateAllUI();
                            }
                            if (MtkDataParsePresenter.number == 2) {
                                MtkDataToServer.saveTodayCmd();
                                KLog.e("=========number==2======");
                            }
                        }
                        if (!MtkSync.getInstance().sync_P1_data()) {
                            KLog.e("upDataToServer", "upCmdToServer--2");
                            MtkDataToServer.upCmdToServer();
                            MtkDataToServer.upCmd62ToServer();
                            MtkDataParsePresenter.number = 0;
                        }
                        MtkDataParsePresenter.mHandler.postDelayed(MtkDataParsePresenter.sync61Runnable, 10000);
                    }
                }
            });
        }
    };
    static Runnable sync68Timeout = new Runnable() {
        public void run() {
            MTKHeadSetSync.getInstance().reportProgress();
            KLog.e("---*report sync finish event");
        }
    };

    public static long getmUid() {
        if (mUid == 0) {
            mUid = PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid);
        }
        return mUid;
    }

    public static String getmDeviceName() {
        if (TextUtils.isEmpty(mDeviceName)) {
            mDeviceName = PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name);
        }
        return mDeviceName;
    }

    public static void parseProtoclData(Context context, int dataType, String data) {
        EventBus.getDefault().post(new BleConnectStatue(true));
        KLog.i(TAG, "---*数据接收：0x" + Integer.toHexString(dataType));
        KLog.i(TAG, "---*数据接收：" + data);
        switch (dataType) {
            case 0:
                parse00Data(context, data);
                return;
            case 1:
                parse01Data(context, data);
                break;
            case 11:
                break;
            case 12:
                parse0cData(context, data);
                return;
            case 19:
                parse13Data(context, data);
                return;
            case 25:
                parse19Data(context, data);
                return;
            case 26:
                parse1AData(context, data);
                return;
            case 64:
                parse40Data(context, data);
                return;
            case 96:
                parse60Data(context, data);
                return;
            case 97:
                parse61Data(context, data);
                return;
            case 98:
                parse62Data(context, data);
                return;
            case 100:
                parse64Data(context, data);
                return;
            case 104:
                parse68Data(context, data);
                return;
            case Constants.HEART_RATE_TYPE /*1055*/:
                EventBus.getDefault().post(new RealTimeHREvent(Integer.parseInt(data)));
                KLog.e("receive standard hr data : " + data);
                return;
            default:
                return;
        }
        parse0bData(context, data);
    }

    private static void parse0bData(Context context, String data) {
        Mtk_DeviceBaseInfoSqlUtil.updateDeviceBaseInfo(context, FirmwareAction.Firmware_Factory_Version_Time, data);
    }

    private static void parse0cData(Context context, String data) {
        try {
            Mtk_DeviceBaseInfoSqlUtil.updateDeviceBaseInfo(context, FirmwareAction.Firmware_Factory_Version_Info, data);
            FactoryVersion factoryVersion = new FactoryVersion();
            factoryVersion.setUid(ContextUtil.getLUID());
            factoryVersion.setMac_address(ContextUtil.getDeviceAddressNoClear());
            factoryVersion.setName(ContextUtil.getDeviceNameNoClear());
            Mtk_DeviceBaseInfo content = Mtk_DeviceBaseInfoSqlUtil.getBaseInfoByKey(FirmwareAction.Firmware_Factory_Version_Time, ContextUtil.getLUID(), ContextUtil.getDeviceNameCurr());
            if (content != null) {
                factoryVersion.setCmd_b(((ProductInfo) JsonTool.fromJson(content.getContent(), ProductInfo.class)).cmd);
                factoryVersion.setCmd_c(((ProductInfo) JsonTool.fromJson(Mtk_DeviceBaseInfoSqlUtil.getBaseInfoByKey(FirmwareAction.Firmware_Factory_Version_Info, ContextUtil.getLUID(), ContextUtil.getDeviceNameCurr()).getContent(), ProductInfo.class)).cmd);
                NetFactory.getInstance().getClient(null).deviceUploadFactoryVersion(factoryVersion);
                return;
            }
            factoryVersion.setCmd_b(new ProductInfo().cmd);
            factoryVersion.setCmd_c(((ProductInfo) JsonTool.fromJson(Mtk_DeviceBaseInfoSqlUtil.getBaseInfoByKey(FirmwareAction.Firmware_Factory_Version_Info, ContextUtil.getLUID(), ContextUtil.getDeviceNameCurr()).getContent(), ProductInfo.class)).cmd);
            NetFactory.getInstance().getClient(null).deviceUploadFactoryVersion(factoryVersion);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    private static void parse64Data(final Context context, final String data) {
        int ctrl64;
        try {
            ctrl64 = JsonUtils.getInt(data, "ctrl");
        } catch (Exception e) {
            ctrl64 = -100;
            ThrowableExtension.printStackTrace(e);
        }
        KLog.e("licl", "获取到64 -:" + ctrl64);
        if (ctrl64 == 0) {
            fixedThreadPool.submit(new Runnable() {
                public void run() {
                    MtkDataParsePresenter.mUid = PrefUtil.getLong(context, UserAction.User_Uid);
                    MtkDataParsePresenter.mDeviceName = PrefUtil.getString(context, BleAction.Bluetooth_Device_Name);
                    Ble64DataParse.parseCtrl0(data);
                    MtkSync.getInstance().syncP1AllData();
                }
            });
        } else {
            fixedThreadPool.submit(new Runnable() {
                public void run() {
                    MtkDataParsePresenter.mHandler.removeCallbacks(MtkDataParsePresenter.sync61Runnable);
                    MtkSync.getInstance().validate_61_62_1_data(4);
                    ECG_Data cmd64 = (ECG_Data) new Gson().fromJson(data, ECG_Data.class);
                    TB_64_data tb_data = new TB_64_data();
                    tb_data.setUid(MtkDataParsePresenter.getmUid());
                    tb_data.setData_from(MtkDataParsePresenter.getmDeviceName());
                    tb_data.setSeq(cmd64.getSeq());
                    tb_data.setYear(cmd64.getYear());
                    tb_data.setMonth(cmd64.getMonth());
                    tb_data.setDay(cmd64.getDay());
                    tb_data.setHour(cmd64.getHour());
                    tb_data.setSecond(cmd64.getSecond());
                    tb_data.setMin(cmd64.getMin());
                    tb_data.setEcg(new Gson().toJson((Object) cmd64.getEcg_raw_data()));
                    MtkSync.getInstance().progressUP(false, tb_data.getMonth() + HelpFormatter.DEFAULT_OPT_PREFIX + tb_data.getDay());
                    DateUtil dateUtil = new DateUtil(cmd64.getYear(), cmd64.getMonth(), cmd64.getDay(), cmd64.getHour(), cmd64.getMin(), cmd64.getSecond());
                    tb_data.setTime(dateUtil.getUnixTimestamp());
                    tb_data.saveOrUpdate("uid=? and data_from=?  and year=? and month=? and day=? and hour=? and min=? and second=? and seq=?", String.valueOf(MtkDataParsePresenter.getmUid()), String.valueOf(MtkDataParsePresenter.getmDeviceName()), String.valueOf(tb_data.getYear()), String.valueOf(tb_data.getMonth()), String.valueOf(tb_data.getDay()), String.valueOf(tb_data.getHour()), String.valueOf(tb_data.getMin()), String.valueOf(tb_data.getSecond()), String.valueOf(tb_data.getSeq()));
                    if (cmd64.getSeq() + 1 == MtkSync.getInstance().getNowSync()) {
                        KLog.e("no2855-->> 64 数据同步结束: " + (cmd64.getSeq() + 1) + " == " + dateUtil.getSyyyyMMddDate());
                        TB_64_index_table index_table = (TB_64_index_table) DataSupport.where("uid=? and data_ymd=? and data_from=? and seq_end=?", String.valueOf(MtkDataParsePresenter.getmUid()), dateUtil.getSyyyyMMddDate(), MtkDataParsePresenter.getmDeviceName(), (cmd64.getSeq() + 1) + "").findFirst(TB_64_index_table.class);
                        if (index_table != null) {
                            index_table.setSync_seq(cmd64.getSeq() + 1);
                            index_table.update(index_table.getId());
                        }
                        MtkDataParsePresenter.mHandler.post(MtkDataParsePresenter.sync61Runnable);
                    }
                    MtkDataParsePresenter.mHandler.postDelayed(MtkDataParsePresenter.sync61Runnable, 10000);
                }
            });
        }
    }

    private static void parse62Data(Context context, final String data) {
        int ctrl62 = JsonUtils.getInt(data, "ctrl");
        Log.e("licl", "获取到62 -:" + ctrl62);
        if (ctrl62 == 0) {
            fixedThreadPool.submit(new Runnable() {
                public void run() {
                    MtkDataParsePresenter.mUid = PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid);
                    MtkDataParsePresenter.mDeviceName = PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name);
                    DataUtil.deleteBleGps();
                    Ble62DataParse.parseCtrl0(data);
                    MtkCmdAssembler.getInstance().getIndexTableAccordingType(100);
                }
            });
        } else {
            fixedThreadPool.submit(new Runnable() {
                public void run() {
                    MtkDataParsePresenter.mHandler.removeCallbacks(MtkDataParsePresenter.sync61Runnable);
                    GnssMinData ble62DataParse = (GnssMinData) new Gson().fromJson(data, GnssMinData.class);
                    int year = ble62DataParse.getYear();
                    String str = "2_" + new DateUtil(year, ble62DataParse.getMonth(), ble62DataParse.getDay()).getYyyyMMddDate();
                    MtkSync.getInstance().validate_61_62_1_data(2);
                    TB_62_data cmd62 = new TB_62_data();
                    cmd62.setUid(MtkDataParsePresenter.getmUid());
                    cmd62.setData_from(MtkDataParsePresenter.getmDeviceName() + "");
                    cmd62.setCtrl(ble62DataParse.getCtrl());
                    cmd62.setSeq(ble62DataParse.getIndex());
                    cmd62.setYear(ble62DataParse.getYear());
                    cmd62.setMonth(ble62DataParse.getMonth());
                    cmd62.setDay(ble62DataParse.getDay());
                    cmd62.setHour(ble62DataParse.getHour());
                    cmd62.setMin(ble62DataParse.getMin());
                    cmd62.setFreq(ble62DataParse.getFreq());
                    cmd62.setNum(ble62DataParse.getNum());
                    cmd62.setCmd(ble62DataParse.getCmd());
                    DateUtil date62 = new DateUtil(cmd62.getYear(), cmd62.getMonth(), cmd62.getDay(), cmd62.getHour(), cmd62.getMin(), 0);
                    cmd62.setTime(date62.getTimestamp());
                    List<Gnss> gnssDatas = ble62DataParse.getmGnssMinDataList();
                    ArrayList arrayList = new ArrayList();
                    for (Gnss gnssData : gnssDatas) {
                        LongitudeAndLatitude la = new LongitudeAndLatitude();
                        la.setLatitude(gnssData.getLatitude());
                        la.setLongitude(gnssData.getLongitude());
                        la.setAltitude(gnssData.getAltitude());
                        la.setGps_speed(gnssData.getGps_speed());
                        arrayList.add(la);
                    }
                    cmd62.setGnssData(new Gson().toJson((Object) arrayList));
                    MtkSync.getInstance().progressUP(false, cmd62.getMonth() + HelpFormatter.DEFAULT_OPT_PREFIX + cmd62.getDay() + " -62" + (ble62DataParse.getIndex() + 1) + " - " + MtkSync.getInstance().getNowSync());
                    if (cmd62.getYear() - 2000 != 255 || cmd62.getMonth() - 1 != 255 || cmd62.getDay() - 1 != 255 || cmd62.getHour() != 255 || cmd62.getMin() != 255) {
                        if (!MtkDataParsePresenter.map62.containsKey(date62.getSyyyyMMddDate())) {
                            MtkDataParsePresenter.addTB62Mtk(date62);
                            MtkDataParsePresenter.map62.put(date62.getSyyyyMMddDate(), Integer.valueOf(1));
                        }
                        cmd62.saveOrUpdate("uid=? and seq=? and year =? and month=? and day=? and hour=? and min=?", String.valueOf(MtkDataParsePresenter.getmUid()), String.valueOf(cmd62.getSeq()), String.valueOf(cmd62.getYear()), String.valueOf(cmd62.getMonth()), String.valueOf(cmd62.getDay()), String.valueOf(cmd62.getHour()), String.valueOf(cmd62.getMin()));
                        if (ble62DataParse.getIndex() + 1 == MtkSync.getInstance().getNowSync()) {
                            KLog.e("testf1shuju11162一天的数据结束:" + cmd62.getYear() + HelpFormatter.DEFAULT_OPT_PREFIX + cmd62.getMonth() + HelpFormatter.DEFAULT_OPT_PREFIX + cmd62.getDay());
                            MtkDataParsePresenter.mHandler.post(MtkDataParsePresenter.sync61Runnable);
                        }
                        MtkDataParsePresenter.mHandler.postDelayed(MtkDataParsePresenter.sync61Runnable, 10000);
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public static void addTB62Mtk(DateUtil dateUtil1) {
        DateUtil dateUtil = new DateUtil(dateUtil1.getYear(), dateUtil1.getMonth(), dateUtil1.getDay());
        L.file("62总数据与分数据异常，" + dateUtil.getSyyyyMMddDate(), 3);
        TB_mtk_statue mtk_statue = new TB_mtk_statue();
        mtk_statue.setUid(mUid);
        mtk_statue.setData_from(mDeviceName);
        mtk_statue.setType(62);
        mtk_statue.setYear(dateUtil.getYear());
        mtk_statue.setMonth(dateUtil.getMonth());
        mtk_statue.setDay(dateUtil.getDay());
        mtk_statue.setHas_file(2);
        mtk_statue.setHas_up(2);
        mtk_statue.setHas_tb(2);
        mtk_statue.setDate(dateUtil.getUnixTimestamp());
        mtk_statue.saveOrUpdate("uid=? and data_from=? and type=? and date=?", mUid + "", mDeviceName, "62", dateUtil.getUnixTimestamp() + "");
    }

    private static synchronized void parse61Data(Context context, final String data) {
        synchronized (MtkDataParsePresenter.class) {
            try {
                if (new JSONObject(data).getInt("ctrl") == 0) {
                    fixedThreadPool.submit(new Runnable() {
                        public void run() {
                            MtkDataParsePresenter.mUid = PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid);
                            MtkDataParsePresenter.mDeviceName = PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name);
                            Ble61DataParse.parseCtrl0(data);
                            MtkCmdAssembler.getInstance().getIndexTableAccordingType(98);
                        }
                    });
                } else {
                    mHandler.removeCallbacks(sync61Runnable);
                    fixedThreadPool.submit(new Runnable() {
                        public void run() {
                            HealthMinData ble61DataParse = (HealthMinData) new Gson().fromJson(data, HealthMinData.class);
                            int year = ble61DataParse.getYear();
                            int month = ble61DataParse.getMonth();
                            int day = ble61DataParse.getDay();
                            String str = "1_" + new DateUtil(year, month, day).getYyyyMMddDate();
                            MtkSync.getInstance().validate_61_62_1_data(1);
                            TB_61_data cmd_61 = new TB_61_data();
                            cmd_61.setUid(MtkDataParsePresenter.getmUid());
                            cmd_61.setData_from(MtkDataParsePresenter.getmDeviceName());
                            cmd_61.setCtrl(ble61DataParse.getCtrl());
                            cmd_61.setSeq(ble61DataParse.getSeq());
                            cmd_61.setYear(ble61DataParse.getYear());
                            cmd_61.setMonth(ble61DataParse.getMonth());
                            cmd_61.setDay(ble61DataParse.getDay());
                            cmd_61.setHour(ble61DataParse.getHour());
                            cmd_61.setMin(ble61DataParse.getMin());
                            MtkSync.getInstance().progressUP(false, cmd_61.getMonth() + HelpFormatter.DEFAULT_OPT_PREFIX + cmd_61.getDay() + " -61-" + (ble61DataParse.getSeq() + 1) + " - " + MtkSync.getInstance().getNowSync());
                            if (cmd_61.getYear() - 2000 != 255 || cmd_61.getMonth() - 1 != 255 || cmd_61.getDay() - 1 != 255 || cmd_61.getHour() != 255 || cmd_61.getMin() != 255) {
                                MtkDataParsePresenter.months[0] = ble61DataParse.getYear();
                                MtkDataParsePresenter.months[1] = ble61DataParse.getMonth();
                                MtkDataParsePresenter.months[2] = ble61DataParse.getDay();
                                cmd_61.setTime(new DateUtil(cmd_61.getYear(), cmd_61.getMonth(), cmd_61.getDay(), cmd_61.getHour(), cmd_61.getMin(), ble61DataParse.getSecond()).getTimestamp());
                                cmd_61.setData_type(ble61DataParse.getData_type());
                                cmd_61.setSport_type(ble61DataParse.getSport_type());
                                cmd_61.setCalorie(ble61DataParse.getCalorie());
                                cmd_61.setStep(ble61DataParse.getStep());
                                cmd_61.setDistance(ble61DataParse.getDistance());
                                cmd_61.setState_type(ble61DataParse.getState_type());
                                cmd_61.setAutomatic(ble61DataParse.getAutomaticMin());
                                cmd_61.setReserve(ble61DataParse.getSecond());
                                cmd_61.setMin_bpm(ble61DataParse.getMin_bpm());
                                cmd_61.setMax_bpm(ble61DataParse.getMax_bpm());
                                cmd_61.setAvg_bpm(ble61DataParse.getAvg_bpm());
                                cmd_61.setLevel(ble61DataParse.getLevel());
                                cmd_61.setSdnn(ble61DataParse.getSdnn());
                                cmd_61.setLf_hf(ble61DataParse.getLf());
                                cmd_61.setHf(ble61DataParse.getHf());
                                cmd_61.setLf_hf(ble61DataParse.getLf_hf());
                                cmd_61.setBpm_hr(ble61DataParse.getBpm_hr());
                                cmd_61.setSbp(ble61DataParse.getSbp());
                                cmd_61.setDbp(ble61DataParse.getDbp());
                                cmd_61.setBpm(ble61DataParse.getBpm());
                                cmd_61.setCmd(ble61DataParse.getCmd());
                                cmd_61.saveOrUpdate("uid=? and cmd=?", String.valueOf(MtkDataParsePresenter.getmUid()), cmd_61.getCmd() + "");
                                if (ble61DataParse.getSeq() + 1 == MtkSync.getInstance().getNowSync()) {
                                    KLog.e("testf1shuju111-61有一天的同步结束: " + year + HelpFormatter.DEFAULT_OPT_PREFIX + month + HelpFormatter.DEFAULT_OPT_PREFIX + day + "  已同步到的: " + MtkSync.getInstance().getNowSync());
                                    ContentValues values = new ContentValues();
                                    values.put("ok", Integer.valueOf(1));
                                    DataSupport.updateAll(TB_f1_index.class, values, "uid=? and data_from=? and end_seq=?", MtkDataParsePresenter.getmUid() + "", MtkDataParsePresenter.getmDeviceName() + "", MtkSync.getInstance().getNowSync() + "");
                                    MtkDataParsePresenter.mHandler.post(MtkDataParsePresenter.sync61Runnable);
                                }
                                MtkDataParsePresenter.mHandler.postDelayed(MtkDataParsePresenter.sync61Runnable, 10000);
                            }
                        }
                    });
                }
            } catch (JSONException e) {
                ThrowableExtension.printStackTrace(e);
            }
        }
        return;
    }

    private static void parse60Data(Context context, String data) {
        long uid = PrefUtil.getLong(context, UserAction.User_Uid);
        String datafrom = PrefUtil.getString(context, BleAction.Bluetooth_Device_Name);
        mDeviceName = datafrom;
        mUid = uid;
        HealthDailyData ble60DataParse = (HealthDailyData) new Gson().fromJson(data, HealthDailyData.class);
        KLog.i("ble60DataParse" + ble60DataParse.toString());
        TB_60_data cmd_60 = new TB_60_data();
        cmd_60.setUid(uid);
        cmd_60.setData_from(datafrom);
        cmd_60.setYear(ble60DataParse.getYear());
        cmd_60.setMonth(ble60DataParse.getMonth());
        cmd_60.setDay(ble60DataParse.getDay());
        cmd_60.setData_type(ble60DataParse.getData_type());
        cmd_60.setSteps(ble60DataParse.getSteps());
        cmd_60.setCalorie(ble60DataParse.getCalorie());
        cmd_60.setDistance(ble60DataParse.getDistance());
        cmd_60.setAvg_bpm(ble60DataParse.getAvg_bpm());
        cmd_60.setMax_bpm(ble60DataParse.getMax_bpm());
        cmd_60.setMin_bpm(ble60DataParse.getMin_bpm());
        cmd_60.setAvg_bpm(ble60DataParse.getAvg_bpm());
        cmd_60.setLevel(ble60DataParse.getLevel());
        cmd_60.setSdnn(ble60DataParse.getSdnn());
        cmd_60.setLf(ble60DataParse.getLf());
        cmd_60.setHf(ble60DataParse.getHf());
        cmd_60.setLf_hf(ble60DataParse.getLf_hf());
        cmd_60.setBpm_hr(ble60DataParse.getBpm_hr());
        cmd_60.setSbp(ble60DataParse.getSbp());
        cmd_60.setDbp(ble60DataParse.getDbp());
        cmd_60.setBpm(ble60DataParse.getBpm());
        CurrData_0x29 currData = new CurrData_0x29();
        currData.setTotalSteps(ble60DataParse.getSteps() + "");
        currData.setTotalCalories(((int) ble60DataParse.getCalorie()) + "");
        Mtk_DeviceBaseInfoSqlUtil.updateDeviceBaseInfo(context, FirmwareAction.Firmware_Curr_0x29_Data, JsonUtils.toJson(currData));
        cmd_60.saveOrUpdate("uid=? and year=? and month=? and day=? ", String.valueOf(uid), String.valueOf(cmd_60.getYear()), String.valueOf(cmd_60.getMonth()), String.valueOf(cmd_60.getDay()));
        DataUtil.saveTBWalk(ble60DataParse.getYear(), ble60DataParse.getMonth(), ble60DataParse.getDay(), ble60DataParse.getCalorie() + "", ble60DataParse.getDistance() + "", ble60DataParse.getSteps() + "");
        EventBus.getDefault().post(new CurrData_0x29(ble60DataParse.getSteps() + "", ble60DataParse.getCalorie() + ""));
    }

    private static void parse68Data(Context context, final String data) {
        try {
            final long uid = PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid);
            final String from = PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name);
            if (new JSONObject(data).getInt("ctrl") == 0) {
                KLog.e("---*receive 68 index data");
                fixedThreadPool.submit(new Runnable() {
                    public void run() {
                        MTKHeadSetSync.getInstance().syncDetailData(Ble68DataParse.parseCtrl0(data));
                    }
                });
                return;
            }
            mHandler.removeCallbacks(sync68Timeout);
            fixedThreadPool.submit(new Runnable() {
                public void run() {
                    KLog.e("---*receive 68 detail data");
                    R1HealthMinuteData ble68DataParse = (R1HealthMinuteData) new Gson().fromJson(data, R1HealthMinuteData.class);
                    TB_68_data data68 = new TB_68_data();
                    data68.setUid(uid);
                    data68.setData_from(from);
                    data68.setCtrl(ble68DataParse.getCtrl());
                    data68.setSeq(ble68DataParse.getSeq());
                    data68.setYear(ble68DataParse.getYear());
                    data68.setMonth(ble68DataParse.getMonth());
                    data68.setDay(ble68DataParse.getDay());
                    data68.setHour(ble68DataParse.getHour());
                    data68.setMin(ble68DataParse.getMinute());
                    data68.setSeconds(ble68DataParse.getSecond());
                    MTKHeadSetSync.getInstance().setCounter(MTKHeadSetSync.getInstance().getCounter() + 1);
                    if (data68.getYear() - 2000 == 255 && data68.getMonth() - 1 == 255 && data68.getDay() - 1 == 255 && data68.getHour() == 255 && data68.getMin() == 255) {
                        KLog.e("---*invalid 68 data");
                        return;
                    }
                    MtkDataParsePresenter.months[0] = ble68DataParse.getYear();
                    MtkDataParsePresenter.months[1] = ble68DataParse.getMonth();
                    MtkDataParsePresenter.months[2] = ble68DataParse.getDay();
                    data68.setTime(new DateUtil(data68.getYear(), data68.getMonth(), data68.getDay(), data68.getHour(), data68.getMin(), data68.getSeconds()).getTimestamp());
                    data68.setData_type(ble68DataParse.getData_type());
                    if ((data68.getData_type() & 240) == 32) {
                        KLog.e("contain sport data");
                        data68.setSport_type(ble68DataParse.getWalk().getSport_type());
                        data68.setCalorie(ble68DataParse.getWalk().getCalorie());
                        data68.setStep(ble68DataParse.getWalk().getStep());
                        data68.setDistance(ble68DataParse.getWalk().getDistance());
                        data68.setState_type(ble68DataParse.getWalk().getState_type());
                        data68.setRateOfStride_avg(ble68DataParse.getWalk().getRateOfStride_avg());
                        data68.setRateOfStride_max(ble68DataParse.getWalk().getRateOfStride_max());
                        data68.setRateOfStride_min(ble68DataParse.getWalk().getRateOfStride_min());
                        data68.setFlight_avg(ble68DataParse.getWalk().getFlight_avg());
                        data68.setFlight_max(ble68DataParse.getWalk().getFlight_max());
                        data68.setFlight_min(ble68DataParse.getWalk().getFlight_min());
                        data68.setTouchDown_avg(ble68DataParse.getWalk().getTouchDown_avg());
                        data68.setTouchDown_max(ble68DataParse.getWalk().getTouchDown_max());
                        data68.setTouchDown_min(ble68DataParse.getWalk().getTouchDown_min());
                        data68.setTouchDownPower_avg(ble68DataParse.getWalk().getTouchDownPower_avg());
                        data68.setTouchDownPower_balance(ble68DataParse.getWalk().getTouchDownPower_balance());
                        data68.setTouchDownPower_max(ble68DataParse.getWalk().getTouchDownPower_max());
                        data68.setTouchDownPower_min(ble68DataParse.getWalk().getTouchDownPower_min());
                        data68.setTouchDownPower_stop(ble68DataParse.getWalk().getTouchDownPower_stop());
                    }
                    if ((data68.getData_type() & 15) == 1) {
                        KLog.e("contain hr data");
                        data68.setAvg_hr(ble68DataParse.getHr().getAvg_hr());
                        data68.setMax_hr(ble68DataParse.getHr().getMax_hr());
                        data68.setMin_hr(ble68DataParse.getHr().getMin_hr());
                    }
                    data68.setCmd(ble68DataParse.getCmd());
                    data68.saveOrUpdate("uid=? and data_from=? and data_type=? and year=? and month=? and day=? and hour=? and min=? and seconds=? and state_type=? and sport_type=?", String.valueOf(uid), from, String.valueOf(data68.getData_type()), String.valueOf(data68.getYear()), String.valueOf(data68.getMonth()), String.valueOf(data68.getDay()), String.valueOf(data68.getHour()), String.valueOf(data68.getMin()), String.valueOf(data68.getSeconds()), String.valueOf(data68.getState_type()), String.valueOf(data68.getSport_type()));
                    KLog.e("---*save 68 detail data");
                    MTKHeadSetSync.getInstance().reportProgress(MTKHeadSetSync.getInstance().getCounter());
                    KLog.e(String.format("---*report sync event, count:%d", new Object[]{Integer.valueOf(MTKHeadSetSync.getInstance().getCounter())}));
                }
            });
            mHandler.postDelayed(sync68Timeout, BootloaderScanner.TIMEOUT);
        } catch (Exception ex) {
            ThrowableExtension.printStackTrace(ex);
        }
    }

    public static void upCmdToServer() {
        fixedThreadPool.submit(new Runnable() {
            public void run() {
                KLog.e("no2855--> 开始结算mt");
                MtkDataToServer.syncUpCmdToServer();
                MtkDataToServer.upCmd62ToServer();
            }
        });
    }

    /* access modifiers changed from: private */
    public static void Data61ToOther(long uid, int year, int month, int day, String dataFrom) {
        List<TB_61_data> list = MtkToIvHandler.sort61DataBySeq(uid, year, month, day, dataFrom);
        MtkToIvHandler.mtk61DataToHeartBle(uid, year, month, day, dataFrom, list);
        MtkToIvHandler.fatigueDataToIv(uid, year, month, day, dataFrom, list);
        MtkToIvHandler.sportAnd51HeartDataToIv(uid, year, month, day, dataFrom, list);
        MtkToIvHandler.saveGpsToBlue(uid, dataFrom, year, month, day);
        MtkToIvHandler.mtk61DataToBloodData(uid, year, month, day, dataFrom, list);
    }

    private static void parse00Data(Context context, String data) {
        try {
            PrefUtil.save((Context) ContextUtil.app, BleAction.Bluetooth_Device_Name, BluetoothOperation.getWristBand().getName());
            PrefUtil.save((Context) ContextUtil.app, BleAction.Bluetooth_Device_Address, BluetoothOperation.getWristBand().getAddress());
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
        FMdeviceInfo info = (FMdeviceInfo) JsonUtils.fromJson(data, FMdeviceInfo.class);
        Mtk_DeviceBaseInfoSqlUtil.updateDeviceBaseInfo(context, FirmwareAction.Firmware_Information, data);
        KLog.i("firmware version:" + info.getSwversion());
        DeviceSettingsSend dss = new DeviceSettingsSend();
        dss.setApp(AppConfigUtil.APP_TYPE);
        dss.setModel(info.getModel());
        dss.setVersion(info.getSwversion());
        dss.setApp_platform(1);
        DeviceSettingsBiz.getInstance().remoteDeviceSettings(dss);
        UserDeviceReq req = new UserDeviceReq();
        req.setUid(ContextUtil.getLUID());
        req.setDevice_model(DeviceSettingsBiz.getInstance().getModelDfu(info.getModel()));
        EventBus.getDefault().post(new HaveGetModelEvent(info.getModel()));
        req.setFw_version(info.getSwversion());
        DeviceSettingsBiz.getInstance().upUserDevice(req);
    }

    private static void parse01Data(Context context, String data) {
        Mtk_DeviceBaseInfoSqlUtil.updateDeviceBaseInfo(context, FirmwareAction.Firmware_Battery, data);
        EventBus.getDefault().post(new UpdateConfigUI(UpdateConfigUI.Config_Battery_Update));
    }

    private static void parse1AData(Context context, String data) {
        Mtk_DeviceBaseInfoSqlUtil.updateDeviceBaseInfo(context, FirmwareAction.Firmware_Support_Sport, data);
    }

    private static void parse19Data(Context context, String data) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5 = true;
        KLog.i("---*parse19Data:" + data);
        Mtk_DeviceBaseInfoSqlUtil.updateDeviceBaseInfo(context, FirmwareAction.Firmware_Settings_Default, data);
        MtkDevSetting iwDevSetting = (MtkDevSetting) JsonUtils.fromJson(data, MtkDevSetting.class);
        DeviceSettingLocal settingLocal = DeviceUtils.localDeviceSetting();
        if (iwDevSetting.getLight() == 1) {
            z = true;
        } else {
            z = false;
        }
        settingLocal.setLed(z);
        if (iwDevSetting.getIs24Hour() == 1) {
            z2 = true;
        } else {
            z2 = false;
        }
        settingLocal.setTimeFormat(z2);
        settingLocal.setBackLightStartTime(iwDevSetting.getBackLightSt());
        settingLocal.setBackLightEndTime(iwDevSetting.getBackLightEt());
        if (iwDevSetting.getScreen() == 1) {
            z3 = true;
        } else {
            z3 = false;
        }
        settingLocal.setScreenColor(z3);
        if (!BluetoothOperation.isMTKEarphone()) {
            settingLocal.setLanguage(iwDevSetting.getLanguage());
        }
        AutoHeartStatue autoHeart = DeviceUtils.autoHeartStatue();
        if (iwDevSetting.getAutoHr() == 1) {
            z4 = true;
        } else {
            z4 = false;
        }
        autoHeart.setHeart_switch(z4);
        if (iwDevSetting.getIsSmartTrackOpen() != 1) {
            z5 = false;
        }
        settingLocal.setAutoRecognitionMotion(z5);
        DeviceUtils.saveLocalDeviceSetting(settingLocal);
        DeviceUtils.saveAutoHeartStatue(autoHeart);
        DeviceUtils.writeCommandToDevice(SETTING_INDEXS.All_Of_Them);
    }

    private static void parse40Data(Context context, String data) {
        int code = ((KeyModel) JsonUtils.fromJson(data, KeyModel.class)).getKeyCode();
        if (code == 1) {
            context.sendBroadcast(new Intent(KeyCodeAction.Action_Seleie_Data));
        } else if (code == 2) {
            ContextUtil.startSong();
        } else if (code == 9) {
            Intent intent1 = new Intent(KeyCodeAction.Action_Phone_Statue_Out);
            intent1.setComponent(new ComponentName(AppConfigUtil.package_name, "com.iwown.device_module.device_message_push.CallReceiver"));
            ContextUtil.app.sendBroadcast(intent1);
        } else if (code == 8) {
            Intent intent3 = new Intent(KeyCodeAction.Action_Phone_Mute);
            intent3.setComponent(new ComponentName(AppConfigUtil.package_name, "com.iwown.device_module.device_message_push.CallReceiver"));
            context.sendBroadcast(intent3);
        } else if (code == 12) {
            Intent intent4 = new Intent(KeyCodeAction.Action_Phone_Ring);
            intent4.setComponent(new ComponentName(AppConfigUtil.package_name, "com.iwown.device_module.device_message_push.CallReceiver"));
            context.sendBroadcast(intent4);
        } else if (code == 3) {
            Intent intent42 = new Intent(KeyCodeAction.Action_Voice_Start);
            intent42.setComponent(new ComponentName(AppConfigUtil.package_name, "com.iwown.device_module.device_message_push.CallReceiver"));
            context.sendBroadcast(intent42);
        } else if (code == 4) {
            Intent intent43 = new Intent(KeyCodeAction.Action_Voice_Start);
            intent43.setComponent(new ComponentName(AppConfigUtil.package_name, "com.iwown.device_module.device_message_push.CallReceiver"));
            context.sendBroadcast(intent43);
        }
    }

    private static void parse13Data(Context context, String data) {
        boolean isNewProtocol = ((IWBleParams) JsonTool.fromJson(data, IWBleParams.class)).isNewProtocol();
        KLog.i("data,0x13" + data + "isNewProtocol" + isNewProtocol);
        PrefUtil.save(context, FirmwareAction.Firmware_New_Protocol, isNewProtocol);
        if (isNewProtocol) {
            BluetoothDataParseReceiver.setNewConnectProtocol();
        }
    }
}
