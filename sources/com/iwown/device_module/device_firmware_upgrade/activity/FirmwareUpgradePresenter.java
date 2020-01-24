package com.iwown.device_module.device_firmware_upgrade.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.iwown.bean.FMdeviceInfo;
import com.iwown.data_link.Constants.LogPath;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.device_module.common.BaseActionUtils.BleAction;
import com.iwown.device_module.common.BaseActionUtils.FirmwareAction;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.common.Bluetooth.receiver.iv.dao.DeviceBaseInfoSqlUtil;
import com.iwown.device_module.common.Bluetooth.receiver.mtk.dao.Mtk_DeviceBaseInfoSqlUtil;
import com.iwown.device_module.common.Bluetooth.receiver.proto.dao.PbDeviceInfoSqlUtil;
import com.iwown.device_module.common.Bluetooth.receiver.zg.ZGDataParsePresenter;
import com.iwown.device_module.common.network.NetFactory;
import com.iwown.device_module.common.network.callback.MyCallback;
import com.iwown.device_module.common.network.data.req.FwUpdate;
import com.iwown.device_module.common.network.data.req.Upgrade;
import com.iwown.device_module.common.network.data.resp.FirmwareDownCode;
import com.iwown.device_module.common.network.data.resp.FwUpdateReturnDetail;
import com.iwown.device_module.common.network.data.resp.FwUpdateReturnMessage;
import com.iwown.device_module.common.network.data.resp.RetCode;
import com.iwown.device_module.common.sql.DeviceBaseInfo;
import com.iwown.device_module.common.sql.Mtk_DeviceBaseInfo;
import com.iwown.device_module.common.sql.PbBaseInfo;
import com.iwown.device_module.common.sql.TB_FM_download;
import com.iwown.device_module.common.sql.ZG_BaseInfo;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.JsonUtils;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.device_module.common.utils.Utils;
import com.iwown.device_module.device_firmware_upgrade.SdCardUtil;
import com.iwown.device_module.device_firmware_upgrade.Util;
import com.iwown.device_module.device_firmware_upgrade.bean.FwUpdateInfo;
import com.iwown.device_module.device_firmware_upgrade.data.FirmwareUpgradeParamsBiz;
import com.iwown.device_module.device_firmware_upgrade.dwonloadBiz.CallbackHandler;
import com.iwown.device_module.device_firmware_upgrade.dwonloadBiz.DownloadService;
import com.iwown.device_module.device_firmware_upgrade.dwonloadBiz.DownloadServiceBiz;
import com.iwown.device_module.device_setting.configure.DeviceSettingsBiz;
import com.iwown.device_module.device_setting.configure.DeviceUtils;
import com.iwown.lib_common.file.FileUtils;
import com.iwown.lib_common.log.L;
import com.socks.library.KLog;
import java.io.File;
import java.io.IOException;

public class FirmwareUpgradePresenter implements Presenter {
    public static final int STEP_CHECK_UPDATE = 0;
    public static final int STEP_CONNECT_DFU_DEVICE = 8;
    public static final int STEP_CREATE_FILE_DIR = 4;
    public static final int STEP_DOWNLOAD_HARDWARE = 5;
    public static final int STEP_DOWNLOAD_USER_INFO = 3;
    public static final int STEP_END_OR_REPEAT = 11;
    public static final int STEP_FIRMWARE_AID = 1;
    public static final int STEP_RECEIVE_USER_FEEDBACK = 10;
    public static final int STEP_SEARCH_DFU_DEVICE = 7;
    public static final int STEP_UPLOAD_USER_INFO = 2;
    public static final int STEP_WRITE_DFU_COMMOND = 6;
    public static final int STEP_WRITE_HARDWARE_TO_DEVICE = 9;
    public static final String UPDATE_INFO = "update_info";
    /* access modifiers changed from: private */
    public View view;

    public FirmwareUpgradePresenter(View view2) {
        this.view = view2;
    }

    public void subscribe() {
    }

    public void unsubscribe() {
    }

    public void checkVersion() {
        FMdeviceInfo fMdeviceInfo = DeviceUtils.getDeviceInfo();
        FwUpdate fm = new FwUpdate();
        fm.setApp(AppConfigUtil.APP_TYPE);
        fm.setPlatform("Nordic");
        fm.setApp_version(Utils.getClientVersionCode(ContextUtil.app));
        fm.setDevice_model(DeviceSettingsBiz.getInstance().getModelDfu(fMdeviceInfo.getModel()));
        fm.setDevice_type(Util.device_type(PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name_Current_Device)));
        fm.setApp_platform(1);
        fm.setModule(1);
        fm.setSkip(1);
        fm.setFw_version(fMdeviceInfo.getSwversion());
        NetFactory.getInstance().getClient(new MyCallback<FwUpdateReturnMessage>() {
            public void onSuccess(FwUpdateReturnMessage fwUpdateReturnMessage) {
                if (fwUpdateReturnMessage != null) {
                    try {
                        if (fwUpdateReturnMessage.getFirmware() != null) {
                            FirmwareUpgradePresenter.this.saveFwUpdateDetail(fwUpdateReturnMessage.getFirmware());
                            if (!TextUtils.isEmpty(PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Address_Current_Device))) {
                                PrefUtil.save((Context) ContextUtil.app, FirmwareAction.Firmware_Update_Device_Mac, PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Address_Current_Device));
                            }
                        }
                    } catch (Exception e) {
                        ThrowableExtension.printStackTrace(e);
                    }
                }
            }

            public void onFail(Throwable e) {
            }
        }).checkFirmwareUpdate(fm);
    }

    public FwUpdateReturnDetail fwUpdateDetail() {
        try {
            if (BluetoothOperation.isIv()) {
                DeviceBaseInfo info = DeviceBaseInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Update_Server_Resp);
                if (info != null && !TextUtils.isEmpty(info.getContent())) {
                    return (FwUpdateReturnDetail) JsonUtils.fromJson(info.getContent(), FwUpdateReturnDetail.class);
                }
            } else if (BluetoothOperation.isMtk() || BluetoothOperation.isMTKEarphone()) {
                Mtk_DeviceBaseInfo info2 = Mtk_DeviceBaseInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Update_Server_Resp);
                if (info2 != null && !TextUtils.isEmpty(info2.getContent())) {
                    return (FwUpdateReturnDetail) JsonUtils.fromJson(info2.getContent(), FwUpdateReturnDetail.class);
                }
            } else if (BluetoothOperation.isZg()) {
                ZG_BaseInfo info3 = ZGDataParsePresenter.getZGBaseInfoByKey(FirmwareAction.Firmware_Update_Server_Resp);
                if (info3 != null && !TextUtils.isEmpty(info3.getContent())) {
                    return (FwUpdateReturnDetail) JsonUtils.fromJson(info3.getContent(), FwUpdateReturnDetail.class);
                }
            } else if (BluetoothOperation.isProtoBuf()) {
                PbBaseInfo info4 = PbDeviceInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Update_Server_Resp);
                if (info4 != null && !TextUtils.isEmpty(info4.getContent())) {
                    return (FwUpdateReturnDetail) JsonUtils.fromJson(info4.getContent(), FwUpdateReturnDetail.class);
                }
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
        return new FwUpdateReturnDetail();
    }

    public void saveFwUpdateDetail(FwUpdateReturnDetail fw) {
        if (fw != null) {
            if (BluetoothOperation.isIv()) {
                DeviceBaseInfoSqlUtil.updateDeviceBaseInfo(ContextUtil.app, FirmwareAction.Firmware_Update_Server_Resp, JsonUtils.toJson(fw));
            } else if (BluetoothOperation.isMtk() || BluetoothOperation.isMTKEarphone()) {
                Mtk_DeviceBaseInfoSqlUtil.updateDeviceBaseInfo(ContextUtil.app, FirmwareAction.Firmware_Update_Server_Resp, JsonUtils.toJson(fw));
            } else if (BluetoothOperation.isZg()) {
                ZGDataParsePresenter.updateZGBaseInfo(FirmwareAction.Firmware_Update_Server_Resp, JsonUtils.toJson(fw));
            } else if (BluetoothOperation.isProtoBuf()) {
                PbDeviceInfoSqlUtil.updateDeviceBaseInfo(ContextUtil.app, FirmwareAction.Firmware_Update_Server_Resp, JsonUtils.toJson(fw));
            }
        }
    }

    public void uploadUserInfo() {
        KLog.e("---uploadUserInfo");
        try {
            FwUpdateReturnDetail detail = fwUpdateDetail();
            String model = DeviceUtils.getDeviceInfo().getModel();
            KLog.e("保存的固件下载地址", detail.getDownload_link());
            if (!TextUtils.isEmpty(PrefUtil.getString(ContextUtil.app, FirmwareAction.Firmware_Update_Device_Mac))) {
                String newmac = Util.getNewMac(PrefUtil.getString(ContextUtil.app, FirmwareAction.Firmware_Update_Device_Mac), 1);
                TB_FM_download fm = new TB_FM_download();
                fm.setMac(newmac);
                fm.setUrl(detail.getDownload_link());
                KLog.e("保存的固件下载地址", detail.getDownload_link());
                if (newmac != null && FirmwareUpgradeParamsBiz.getInstance().queryMacExists(newmac)) {
                    FirmwareUpgradeParamsBiz.getInstance().uploadMacUrl(fm);
                } else if (!(newmac == null || detail.getDownload_link() == null)) {
                    fm.save();
                }
                if (detail.getDownload_link() == null) {
                    checkVersion();
                    this.view.pUpdateUI(2, true);
                    return;
                }
                Upgrade upgrade = new Upgrade();
                upgrade.setUid(ContextUtil.getLUID());
                upgrade.setMac(newmac);
                upgrade.setUrl(detail.getDownload_link());
                upgrade.setModel(model);
                NetFactory.getInstance().getClient(new MyCallback<RetCode>() {
                    public void onSuccess(RetCode retCode) {
                        if (retCode.getRetCode() == 0) {
                            FirmwareUpgradePresenter.this.view.pGoToStep(4);
                        } else {
                            FirmwareUpgradePresenter.this.view.pUpdateUI(2, true);
                        }
                    }

                    public void onFail(Throwable e) {
                        FirmwareUpgradePresenter.this.view.pUpdateUI(2, true);
                    }
                }).uploadUpgradeRepo(upgrade);
                return;
            }
            PrefUtil.save((Context) ContextUtil.app, FirmwareAction.Firmware_Update_Device_Mac, PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Address));
            this.view.pUpdateUI(2, true);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            this.view.pUpdateUI(2, true);
        }
    }

    public void downloadUserInfo() {
        NetFactory.getInstance().getClient(new MyCallback<FirmwareDownCode>() {
            public void onSuccess(FirmwareDownCode firmwareDownCode) {
                if (firmwareDownCode == null) {
                    FirmwareUpgradePresenter.this.view.pUpdateUI(3, true);
                } else if (firmwareDownCode.getRetCode() == 0) {
                    try {
                        if (firmwareDownCode.getModel() == null) {
                            FirmwareUpgradePresenter.this.view.pUpdateUI(3, true);
                            return;
                        }
                        if (!TextUtils.isEmpty(Util.getNewMac(firmwareDownCode.getMac(), 2))) {
                            PrefUtil.save((Context) ContextUtil.app, FirmwareAction.Firmware_Update_Device_Mac, Util.getNewMac(firmwareDownCode.getMac(), 2));
                        }
                        FwUpdateReturnDetail fd = FirmwareUpgradePresenter.this.fwUpdateDetail();
                        fd.setDownload_link(firmwareDownCode.getUrl());
                        FirmwareUpgradePresenter.this.saveFwUpdateDetail(fd);
                        FirmwareUpgradePresenter.this.view.pGoToStep(4);
                    } catch (Exception e) {
                        ThrowableExtension.printStackTrace(e);
                        FirmwareUpgradePresenter.this.view.pUpdateUI(3, true);
                    }
                } else {
                    FirmwareUpgradePresenter.this.view.pUpdateUI(3, true);
                }
            }

            public void onFail(Throwable e) {
                FirmwareUpgradePresenter.this.view.pUpdateUI(3, true);
            }
        }).DownLoadUpgradeRepo(ContextUtil.getLUID());
    }

    public void createFileDir() {
        if (!FileUtils.checkSaveLocationExists() || FileUtils.checkFileExists("Zeroner")) {
            String path = SdCardUtil.getSDCardPath();
            boolean exists = FileUtils.checkFilePathExists(path + File.separator + "Zeroner");
            L.file("是否存在sd卡文件" + exists, 6);
            if (!exists) {
                L.file("创建存在sd卡文件", 6);
                try {
                    FileUtils.mkdirFile(path + "/Zeroner/android.txt");
                } catch (IOException e) {
                    ThrowableExtension.printStackTrace(e);
                }
            }
        } else if (VERSION.SDK_INT >= 19) {
            createFileDirFor19();
        }
        String path2 = SdCardUtil.getSDCardPath();
        boolean exists2 = FileUtils.checkFilePathExists(path2 + File.separator + "Zeroner");
        L.file("是否存在sd卡文件" + exists2, 6);
        if (!exists2) {
            L.file("创建存在sd卡文件" + exists2, 6);
            try {
                FileUtils.mkdirFile(path2 + "/Zeroner/android.txt");
            } catch (IOException e2) {
                ThrowableExtension.printStackTrace(e2);
            }
        }
        if (exists2) {
            this.view.pGoToStep(5);
        } else {
            this.view.pUpdateUI(4, true);
        }
    }

    @NonNull
    private String createFileName() {
        String model = DeviceUtils.getDeviceInfo().getModel();
        return model + "_firmware_" + DeviceSettingsBiz.getInstance().getSuffix(model);
    }

    public void deleteFile() {
        String model = DeviceUtils.getDeviceInfo().getModel();
        String surffix = DeviceSettingsBiz.getInstance().getSuffix(model);
        FileUtils.deleteFile(LogPath.ZERONER + model + "_firmware_" + surffix);
        KLog.e("要删除的文件--/Zeroner/" + model + "_firmware_" + surffix);
    }

    public void downloadUpgradle(String url, CallbackHandler callbackHandler) {
        String fileName = createFileName();
        KLog.e("fileName : " + fileName);
        DownloadServiceBiz.getInstance().setmHandler(callbackHandler);
        Intent service = new Intent(ContextUtil.app, DownloadService.class);
        service.putExtra("type", 1);
        service.putExtra("path", url);
        service.putExtra("fileName", fileName);
        try {
            if (VERSION.SDK_INT >= 26) {
                ContextUtil.app.startForegroundService(service);
            } else {
                ContextUtil.app.startService(service);
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    @TargetApi(19)
    private void createFileDirFor19() {
        if (!FileUtils.createDirectory("Zeroner")) {
            String[] sdPath = FileUtils.getVolumePaths(ContextUtil.app);
            if (sdPath.length > 1) {
                for (int i = 0; i < sdPath.length; i++) {
                    if (sdPath[i] != null) {
                        try {
                            if (Environment.getStorageState(new File(sdPath[i] + "/Zeroner")).equals("mounted")) {
                                FileUtils.mkdirFile(sdPath[i] + "/Zeroner/android.txt");
                            }
                        } catch (IOException e) {
                            ThrowableExtension.printStackTrace(e);
                        }
                    }
                }
            }
        }
    }

    public boolean isNordic() {
        return 2 == DeviceSettingsBiz.getInstance().getDevPlatform(DeviceUtils.getDeviceInfo().getModel());
    }

    public boolean isDialog() {
        return 3 == DeviceSettingsBiz.getInstance().getDevPlatform(DeviceUtils.getDeviceInfo().getModel());
    }

    public void saveConnectTime() {
        FwUpdateInfo.getInstance().setState(2);
        FwUpdateInfo.getInstance().setLastProgressTime(System.currentTimeMillis());
        FwUpdateInfo.getInstance().save();
    }

    public boolean checkFile() {
        return createFile().exists();
    }

    public File createFile() {
        String model = DeviceUtils.getDeviceInfo().getModel();
        return new File(Environment.getExternalStorageDirectory().toString() + LogPath.ZERONER + model + "_firmware_" + DeviceSettingsBiz.getInstance().getSuffix(model));
    }
}
