package com.iwown.device_module.common.Bluetooth.receiver.zg.handler;

import android.os.Environment;
import com.iwown.ble_module.zg_ble.BleHandler;
import com.iwown.ble_module.zg_ble.data.BleDataOrderHandler;
import com.iwown.ble_module.zg_ble.task.AgpsBleMessage;
import com.iwown.data_link.BaseNetUrl;
import com.iwown.data_link.Constants.LogPath;
import com.iwown.device_module.common.network.NetFactory;
import com.iwown.device_module.common.network.callback.MyCallback;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.file.FileIOUtils;
import com.socks.library.KLog;
import com.tencent.tinker.android.dx.instruction.Opcodes;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

public class CR100AGPSPresenter {
    private static final int OFFLINE = 0;
    private static final int ONLINE = 1;
    private static volatile CR100AGPSPresenter instance;
    private byte[] agpsFile;
    private int fileSize;
    private boolean isInit = false;
    private int packageNumAll;
    private String path;
    private int position;
    private int selectLine;

    public static CR100AGPSPresenter getInstance() {
        if (instance == null) {
            synchronized (CR100AGPSPresenter.class) {
                if (instance == null) {
                    instance = new CR100AGPSPresenter();
                }
            }
        }
        return instance;
    }

    private CR100AGPSPresenter() {
    }

    private void writeAGPS168() {
        byte[] bytes;
        byte[] datas = Arrays.copyOfRange(this.agpsFile, this.position * Opcodes.MUL_FLOAT, (this.position * Opcodes.MUL_FLOAT) + Opcodes.MUL_FLOAT);
        int num = datas.length % 16 == 0 ? datas.length / 16 : (datas.length / 16) + 1;
        List<byte[]> cmdList = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            if (i == num - 1) {
                bytes = Arrays.copyOfRange(datas, i * 16, datas.length);
            } else {
                bytes = Arrays.copyOfRange(datas, i * 16, (i * 16) + 16);
            }
            cmdList.add(BleDataOrderHandler.getInstance().writeC100Agps(ContextUtil.app, i + 1, 1, this.packageNumAll, bytes));
        }
        BleHandler.getInstance().addTaskMessage(new AgpsBleMessage(cmdList));
        KLog.e("yyyyy---发送完第" + this.position + "个包总共有" + this.packageNumAll + "个包 总size" + this.fileSize);
    }

    private void writeAGPS168Online() {
        byte[] datas;
        byte[] bytes;
        if (this.position == 0) {
            datas = Arrays.copyOfRange(this.agpsFile, this.position, 32);
        } else if (this.position == 11) {
            datas = Arrays.copyOfRange(this.agpsFile, ((this.position - 1) * Opcodes.LONG_TO_INT) + 32, ((this.position - 1) * Opcodes.LONG_TO_INT) + 32 + 44);
            int end = ((this.position - 1) * Opcodes.LONG_TO_INT) + 32 + 44;
            KLog.e("yyyyy---解析第" + (((this.position - 1) * Opcodes.LONG_TO_INT) + 32) + "到" + end + "个包 总size" + this.fileSize);
        } else {
            datas = Arrays.copyOfRange(this.agpsFile, ((this.position - 1) * Opcodes.LONG_TO_INT) + 32, ((this.position - 1) * Opcodes.LONG_TO_INT) + 32 + Opcodes.LONG_TO_INT);
            int end2 = ((this.position - 1) * Opcodes.LONG_TO_INT) + 32 + Opcodes.LONG_TO_INT;
            KLog.e("yyyyy---解析第" + (((this.position - 1) * Opcodes.LONG_TO_INT) + 32) + "到" + end2 + "个包 总size" + this.fileSize);
        }
        List<byte[]> cmdList = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            if (i == 10) {
                if (datas.length > i * 16) {
                    bytes = Arrays.copyOfRange(datas, i * 16, datas.length);
                } else {
                    bytes = new byte[8];
                }
            } else if (datas.length > i * 16) {
                bytes = Arrays.copyOfRange(datas, i * 16, (i * 16) + 16);
            } else {
                bytes = new byte[16];
            }
            byte[] cmd = new byte[0];
            if (this.position == 0) {
                cmd = BleDataOrderHandler.getInstance().writeC100Agps(ContextUtil.app, i + 1, 2, this.packageNumAll, bytes);
            } else if (this.selectLine == 1) {
                cmd = BleDataOrderHandler.getInstance().writeC100Agps(ContextUtil.app, i + 1, 3, this.packageNumAll, bytes);
            }
            cmdList.add(cmd);
        }
        BleHandler.getInstance().addTaskMessage(new AgpsBleMessage(cmdList));
        KLog.e("yyyyy---发送完第" + this.position + "个包总共有" + this.packageNumAll + "个包 总size" + this.fileSize);
    }

    private void getPackageNum() {
        this.packageNumAll = this.fileSize % Opcodes.MUL_FLOAT == 0 ? this.fileSize / Opcodes.MUL_FLOAT : (this.fileSize / Opcodes.MUL_FLOAT) + 1;
        if (this.selectLine == 0) {
            this.packageNumAll = (this.packageNumAll / 8) * 3;
        } else {
            this.packageNumAll = 12;
        }
    }

    private byte[] getFileInfo(String path2) {
        this.agpsFile = FileIOUtils.readFile2BytesByStream(path2);
        return this.agpsFile;
    }

    private void initData() {
        byte[] fileInfo = getFileInfo(this.path);
        if (fileInfo != null) {
            this.fileSize = fileInfo.length;
            getPackageNum();
        }
    }

    public void init(String path2) {
        this.isInit = true;
        this.selectLine = 0;
        this.path = path2;
        initData();
    }

    public void init(String path2, int selectLine2) {
        this.isInit = true;
        this.selectLine = selectLine2;
        this.path = path2;
        initData();
    }

    public void startAgps() {
        KLog.d("yyyyy---发送完第" + this.position + "个包总共有" + this.packageNumAll + "个包" + this.selectLine);
        if (this.position == this.packageNumAll) {
            this.position = 0;
            if (this.selectLine == 0) {
                updateAgpsFile(1);
                return;
            }
            KLog.e("关闭AGPS");
            closeApgs();
            return;
        }
        if (this.selectLine == 0) {
            writeAGPS168();
        } else {
            writeAGPS168Online();
        }
        EventBus.getDefault().post("c100-apgs-progress:" + calcProgress());
        this.position++;
    }

    private int calcProgress() {
        return ((this.position + 1) * 100) / this.packageNumAll;
    }

    /* access modifiers changed from: private */
    public void openApgs() {
        BleDataOrderHandler.getInstance().startAgps();
        BleDataOrderHandler.getInstance().writeOfflineAgpsLength(ContextUtil.app, this.packageNumAll);
    }

    /* access modifiers changed from: private */
    public void openOnlineApgs() {
        BleDataOrderHandler.getInstance().startAgps();
        BleDataOrderHandler.getInstance().writeOnlineAgpsLength(ContextUtil.app, this.packageNumAll);
    }

    private void closeApgs() {
        BleDataOrderHandler.getInstance().endAgps();
    }

    private void checkUpdate() {
        BleDataOrderHandler.getInstance().checkC100AgpsIsUp();
    }

    private void updateAgpsFile(int selectLine2) {
        this.selectLine = selectLine2;
        String str = LogPath.ZERONER;
        String offlineUrl = BaseNetUrl.agpsOfflineUrl;
        String onlineUrl = BaseNetUrl.agpsOnlineUrl;
        String fileOfflineName = "offline.ubx";
        String fileOnlineName = "online.ubx";
        File offlineFile = new File(Environment.getExternalStorageDirectory() + LogPath.ZERONER + fileOfflineName);
        File onlineFile = new File(Environment.getExternalStorageDirectory() + LogPath.ZERONER + fileOnlineName);
        boolean isNeedDownload = true;
        if (selectLine2 == 0) {
            if (offlineFile.exists() && offlineFile.exists()) {
                if (new DateUtil().getSyyyyMMddDate().equals(new DateUtil(offlineFile.lastModified(), false).getSyyyyMMddDate())) {
                    isNeedDownload = false;
                    KLog.e("http已经是最新的cep文件了");
                }
            }
            if (!isNeedDownload) {
                init(offlineFile.getAbsolutePath(), 0);
                openApgs();
                return;
            }
            download(offlineUrl, offlineFile, fileOfflineName, LogPath.ZERONER, selectLine2);
            return;
        }
        download(onlineUrl, onlineFile, fileOnlineName, LogPath.ZERONER, selectLine2);
    }

    public void checkIsNeedAgpsUpdate() {
        checkUpdate();
    }

    public void downloadAgpsFile() {
        updateAgpsFile(0);
    }

    private void download(String url, final File file, String fileName, String savePath, final int selectLine2) {
        NetFactory.getInstance().getClient(new MyCallback() {
            public void onSuccess(Object o) {
                if (selectLine2 == 0) {
                    CR100AGPSPresenter.this.init(file.getAbsolutePath(), 0);
                    CR100AGPSPresenter.this.openApgs();
                    return;
                }
                CR100AGPSPresenter.this.init(file.getAbsolutePath(), 1);
                CR100AGPSPresenter.this.openOnlineApgs();
            }

            public void onFail(Throwable e) {
                KLog.d("AGPS失败");
            }
        }).downAndSaveFile(url, savePath, fileName);
    }
}
