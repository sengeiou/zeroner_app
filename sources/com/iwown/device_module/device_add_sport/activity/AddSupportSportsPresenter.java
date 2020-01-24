package com.iwown.device_module.device_add_sport.activity;

import android.text.TextUtils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.iwown.bean.SportType;
import com.iwown.ble_module.iwown.cmd.ZeronerSendBluetoothCmdImpl;
import com.iwown.ble_module.iwown.task.DataBean;
import com.iwown.ble_module.iwown.task.ZeronerBackgroundThreadManager;
import com.iwown.ble_module.mtk_ble.task.MtkBackgroundThreadManager;
import com.iwown.device_module.R;
import com.iwown.device_module.common.BaseActionUtils.FirmwareAction;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.common.Bluetooth.receiver.iv.dao.DeviceBaseInfoSqlUtil;
import com.iwown.device_module.common.Bluetooth.receiver.mtk.dao.Mtk_DeviceBaseInfoSqlUtil;
import com.iwown.device_module.common.Bluetooth.receiver.proto.dao.PbDeviceInfoSqlUtil;
import com.iwown.device_module.common.sql.DeviceBaseInfo;
import com.iwown.device_module.common.sql.Mtk_DeviceBaseInfo;
import com.iwown.device_module.common.sql.PbBaseInfo;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.JsonUtils;
import com.iwown.device_module.device_add_sport.activity.AddSupportSportsContract.AddSportPresenter;
import com.iwown.device_module.device_add_sport.activity.AddSupportSportsContract.addSportView;
import com.iwown.device_module.device_add_sport.bean.AddSport;
import com.iwown.device_module.device_add_sport.util.AddSportUtil;
import com.iwown.device_module.device_setting.configure.DeviceUtils;
import com.iwown.device_module.device_setting.configure.WristbandModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddSupportSportsPresenter implements AddSportPresenter {
    public static final int Default_Icon = 999;
    addSportView view;

    public AddSupportSportsPresenter(addSportView view2) {
        this.view = view2;
    }

    public void subscribe() {
    }

    public void unsubscribe() {
    }

    public SportType supportSports() {
        try {
            if (BluetoothOperation.isIv()) {
                DeviceBaseInfo deviceBaseInfo = DeviceBaseInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Support_Sport);
                if (deviceBaseInfo != null && !TextUtils.isEmpty(deviceBaseInfo.getContent())) {
                    return (SportType) JsonUtils.fromJson(deviceBaseInfo.getContent(), SportType.class);
                }
            } else if (BluetoothOperation.isMtk()) {
                Mtk_DeviceBaseInfo deviceBaseInfo2 = Mtk_DeviceBaseInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Support_Sport);
                if (deviceBaseInfo2 != null && !TextUtils.isEmpty(deviceBaseInfo2.getContent())) {
                    return (SportType) JsonUtils.fromJson(deviceBaseInfo2.getContent(), SportType.class);
                }
            } else if (BluetoothOperation.isProtoBuf()) {
                PbBaseInfo deviceBaseInfo3 = PbDeviceInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Support_Sport);
                if (deviceBaseInfo3 != null && !TextUtils.isEmpty(deviceBaseInfo3.getContent())) {
                    return (SportType) JsonUtils.fromJson(deviceBaseInfo3.getContent(), SportType.class);
                }
            }
            if (0 == 0) {
                if (BluetoothOperation.isIv()) {
                    byte[] data = ZeronerSendBluetoothCmdImpl.getInstance().getSportType();
                    DataBean dataBean2 = new DataBean();
                    dataBean2.addData(data);
                    ZeronerBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, dataBean2);
                } else if (BluetoothOperation.isMtk()) {
                    MtkBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ZeronerSendBluetoothCmdImpl.getInstance().getSportType());
                } else if (BluetoothOperation.isProtoBuf()) {
                }
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
        return new SportType();
    }

    public List<AddSport> unCheckSupportSports(SportType sportTypes) {
        ArrayList<AddSport> unAddedRecleList = new ArrayList<>();
        if (sportTypes == null) {
            return unAddedRecleList;
        }
        int[] sports = sportTypes.getTypes();
        if (sports == null || sports.length <= 0) {
            return unAddedRecleList;
        }
        for (int i = 0; i < sports.length; i++) {
            AddSport info = new AddSport();
            int type = sports[i];
            if (type != 1) {
                String model = DeviceUtils.getDeviceInfo().getModel();
                if (TextUtils.isEmpty(model) || ((!WristbandModel.MODEL_I6NH.equalsIgnoreCase(model) && !WristbandModel.MODEL_I6H9.equalsIgnoreCase(model) && !WristbandModel.MODEL_I6HR.equalsIgnoreCase(model)) || type != 131)) {
                    info.setType(sports[i]);
                    if (AddSportUtil.getSporyImgOrName(0, type) != -1) {
                        info.setSportName(ContextUtil.app.getString(AddSportUtil.getSporyImgOrName(0, type)));
                        info.setDrawableId(AddSportUtil.getSporyImgOrName(1, type));
                        unAddedRecleList.add(info);
                    }
                }
            }
        }
        try {
            DeviceBaseInfo deviceBaseInfo = DeviceBaseInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Support_Sports_Status_UnChecked);
            String content = null;
            if (!(deviceBaseInfo == null || deviceBaseInfo.getContent() == null)) {
                content = deviceBaseInfo.getContent();
            }
            if (content == null) {
                return unAddedRecleList;
            }
            ArrayList<AddSport> unChecked = JsonUtils.getListJson(content, AddSport.class);
            if (unChecked == null || unChecked.size() <= 0) {
                return unAddedRecleList;
            }
            return unChecked;
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return unAddedRecleList;
        }
    }

    public List<AddSport> defaultIcon(int default_Icon) {
        ArrayList<AddSport> defaultList = new ArrayList<>();
        for (int i = 0; i < default_Icon; i++) {
            AddSport addSport = new AddSport();
            addSport.setType(999);
            addSport.setSportName("");
            addSport.setDrawableId(R.mipmap.circle_3x);
            defaultList.add(addSport);
        }
        try {
            DeviceBaseInfo deviceBaseInfo = DeviceBaseInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Support_Sports_Status);
            String content = null;
            if (!(deviceBaseInfo == null || deviceBaseInfo.getContent() == null)) {
                content = deviceBaseInfo.getContent();
            }
            ArrayList<AddSport> checked = JsonUtils.getListJson(content, AddSport.class);
            if (checked == null || checked.size() != default_Icon) {
                return defaultList;
            }
            return checked;
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return defaultList;
        }
    }

    public void sendSupportSportCommand(List<AddSport> addSports) {
        for (int i = 0; i < 7; i++) {
            ArrayList<Byte> list = new ArrayList<>();
            list.add(0, Byte.valueOf((byte) i));
            for (AddSport sport : addSports) {
                if (sport.getType() != 999) {
                    list.add(Byte.valueOf((byte) 32));
                    list.add(Byte.valueOf((byte) 3));
                    list.add(Byte.valueOf((byte) sport.getType()));
                }
            }
            list.add(Byte.valueOf((byte) 32));
            list.add(Byte.valueOf((byte) 3));
            list.add(Byte.valueOf(1));
            if (BluetoothOperation.isIv()) {
                byte[] totalData = ZeronerSendBluetoothCmdImpl.getInstance().setSportGole(list);
                int cmd_nums = totalData.length % 20 == 0 ? totalData.length / 20 : (totalData.length / 20) + 1;
                for (int j = 0; j < cmd_nums; j++) {
                    byte[] data1 = Arrays.copyOfRange(totalData, j * 20, (j + 1) * 20 > totalData.length ? totalData.length : (j + 1) * 20);
                    DataBean dataBean3 = new DataBean();
                    dataBean3.addData(data1);
                    ZeronerBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, dataBean3);
                }
            } else if (BluetoothOperation.isMtk()) {
            }
        }
    }
}
