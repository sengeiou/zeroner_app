package com.iwown.device_module.common.Bluetooth.sync.proto;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.google.protobuf.ByteString;
import com.iwown.ble_module.proto.cmd.ProtoBufSendBluetoothCmdImpl;
import com.iwown.ble_module.proto.model.ProtoBufFileUpdateInfo;
import com.iwown.ble_module.proto.task.BackgroundThreadManager;
import com.iwown.ble_module.utils.Util;
import com.iwown.data_link.eventbus.ApgsFinishEvent;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.device_module.common.BaseActionUtils;
import com.iwown.device_module.common.BaseActionUtils.FilePath;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.common.network.NetFactory;
import com.iwown.device_module.common.network.callback.MyCallback;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.file.FileIOUtils;
import com.socks.library.KLog;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

public class ProtoBufUpdate {
    private static final int DATA = 2;
    private static final int DESC = 4;
    private static final int EXIT = 3;
    private static final int INIT = 1;
    private static volatile ProtoBufUpdate instance = null;
    private static Handler mHandler = new Handler(Looper.getMainLooper());
    private static final String url1 = "https://offline-live1.services.u-blox.com/GetOfflineData.ashx?token=ALKccrhbDE6DMfGLzob8dQ;gnss=gps;period=1;resolution=1";
    private static final String url2 = "http://online-live1.services.u-blox.com/GetOnlineData.ashx?token=ALKccrhbDE6DMfGLzob8dQ;gnss=gps;datatype=alm";
    /* access modifiers changed from: private */
    public byte[] allBytes;
    private long allCyc32;
    private byte[] completeBytes = new byte[0];
    private int completeOffset = 0;
    /* access modifiers changed from: private */
    public String fileName = "protobuf.ubx";
    /* access modifiers changed from: private */
    public int fuType;
    /* access modifiers changed from: private */
    public boolean isUpdate = false;
    private int maxMtu = 244;
    private List<byte[]> packageList;
    private int position = 0;
    private String rootPath = FilePath.ProtoBuf_Ble_90_DOWNLOAD_Dir;

    public static class Type {
        public static final int TYPE_FONT = 1;
        public static final int TYPE_GPS = 0;
        public static final int TYPE_MGAONLINE = 2;
    }

    public static ProtoBufUpdate getInstance() {
        if (instance == null) {
            synchronized (ProtoBufUpdate.class) {
                if (instance == null) {
                    instance = new ProtoBufUpdate();
                }
            }
        }
        return instance;
    }

    public boolean isUpdate() {
        return this.isUpdate;
    }

    public void setUpdate(boolean update) {
        this.isUpdate = update;
    }

    public void startUpdate(int type) {
        if (!BluetoothOperation.isConnected()) {
            KLog.e("yanxi....没有连接");
        } else if (ProtoBufSync.getInstance().isSync()) {
        } else {
            if (this.isUpdate) {
                KLog.e("yanxi....正在更新");
                return;
            }
            String[] split = PrefUtil.getString(ContextUtil.app, BaseActionUtils.PROTOBUF_CYC_CHECK).split("&&");
            if (this.fuType != 0 || split.length != 3 || !split[0].equals(new DateUtil().getY_M_D()) || !split[2].equals(UserConfig.getInstance().getDevice())) {
                updateInfo(type);
                return;
            }
            KLog.d("yanxi----- agps不需要升级");
            this.isUpdate = false;
            sendFinishUpdate();
        }
    }

    /* access modifiers changed from: private */
    public void updateInfo(int type) {
        if (type == 1) {
            this.fuType = 1;
        } else if (type == 0) {
            this.fuType = 0;
            downloadFile("https://offline-live1.services.u-blox.com/GetOfflineData.ashx?token=ALKccrhbDE6DMfGLzob8dQ;gnss=gps;period=1;resolution=1");
        } else {
            this.fuType = 2;
            downloadFile("http://online-live1.services.u-blox.com/GetOnlineData.ashx?token=ALKccrhbDE6DMfGLzob8dQ;gnss=gps;datatype=alm");
        }
    }

    private void initData(ProtoBufFileUpdateInfo fileUpdateInfo) {
        KLog.e(fileUpdateInfo.toString());
        if (this.allBytes == null || this.allBytes.length == 0) {
            this.allBytes = FileIOUtils.readFile2BytesByMap((Environment.getExternalStorageDirectory().getAbsolutePath() + this.rootPath) + this.fileName);
            if (this.allBytes == null || this.allBytes.length == 0) {
                return;
            }
        }
        this.allCyc32 = Util.CRC_32(this.allBytes);
        this.packageList = multipePackage(this.allBytes, fileUpdateInfo.getMtu());
        this.maxMtu = PrefUtil.getInt(ContextUtil.app, BaseActionUtils.PROTOBUF_MTU_INFO);
        int fileOffset = fileUpdateInfo.getFileOffset();
        int crc32AtOffset = fileUpdateInfo.getCrc32AtOffset();
        if (fileOffset == 0) {
            clearInfo();
            return;
        }
        int tempOffset = 0;
        byte[] tempBytes = new byte[0];
        int tempPostion = -1;
        int i = 0;
        while (true) {
            if (i >= this.packageList.size()) {
                break;
            }
            tempOffset += ((byte[]) this.packageList.get(i)).length;
            tempBytes = Util.concat(tempBytes, (byte[]) this.packageList.get(i));
            if (fileOffset == tempOffset && ((long) crc32AtOffset) == Util.CRC_32(tempBytes)) {
                tempPostion = i;
                this.position = i;
                this.completeBytes = tempBytes;
                this.completeOffset = tempOffset;
                break;
            }
            i++;
        }
        if (tempPostion == -1) {
            clearInfo();
        }
    }

    public void updateDetail(int type, ProtoBufFileUpdateInfo fileUpdateInfo) {
        if (type == 4) {
            KLog.e(fileUpdateInfo);
            if (fileUpdateInfo.isValid()) {
                this.isUpdate = false;
                return;
            }
            initData(fileUpdateInfo);
            if (this.position != 0) {
                exeData();
                return;
            }
            exeInit();
        }
        if (type == 1) {
            if (fileUpdateInfo.getStatus() == 0) {
                exeData();
            } else {
                KLog.e("同步失败");
                this.isUpdate = false;
            }
        }
        if (type == 2) {
            if (fileUpdateInfo.getStatus() == 0) {
                exeData();
            } else if (fileUpdateInfo.getStatus() == 1) {
                KLog.e("参数失败");
                initData(fileUpdateInfo);
            } else {
                this.isUpdate = false;
            }
        }
        if (type == 3 && fileUpdateInfo.getStatus() == 0 && fileUpdateInfo.isValid()) {
            KLog.e("同步完成");
            this.isUpdate = false;
        }
    }

    private List<byte[]> multipePackage(byte[] bytes, int mtu) {
        List<byte[]> packageList2 = new LinkedList<>();
        if (mtu > 0) {
            if (bytes.length > mtu) {
                int i = 0;
                while (i < bytes.length) {
                    int to = i + mtu;
                    if (to > bytes.length) {
                        to = bytes.length;
                    }
                    packageList2.add(Arrays.copyOfRange(bytes, i, to));
                    i += mtu;
                }
            } else {
                packageList2.add(Arrays.copyOfRange(bytes, 0, bytes.length));
            }
        }
        return packageList2;
    }

    private void exeInit() {
        BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().setFileInitUpdate(this.fuType, this.allBytes.length, (int) this.allCyc32, "cs", this.completeOffset, (int) Util.CRC_32(this.completeBytes)));
    }

    private void exeData() {
        if (this.position < this.packageList.size()) {
            byte[] bytes = (byte[]) this.packageList.get(this.position);
            ByteString bytes1 = ByteString.copyFrom(bytes);
            this.completeBytes = Util.concat(this.completeBytes, bytes);
            long cyc32Office = Util.CRC_32(this.completeBytes);
            List<byte[]> bytesList = multipePackage(ProtoBufSendBluetoothCmdImpl.getInstance().setFileDataUpdate(this.fuType, this.completeOffset, (int) cyc32Office, bytes1), this.maxMtu);
            for (int i = 0; i < bytesList.size(); i++) {
                BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, (byte[]) bytesList.get(i));
            }
            this.completeOffset = ((byte[]) this.packageList.get(this.position)).length + this.completeOffset;
            this.position++;
        } else {
            BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().setFileDataExit(this.fuType));
            this.isUpdate = false;
            long checkCode = Util.CRC_32(this.allBytes);
            String data = new DateUtil().getY_M_D();
            if (this.fuType == 0) {
                PrefUtil.save((Context) ContextUtil.app, BaseActionUtils.PROTOBUF_CYC_CHECK, data + "&&" + checkCode + "&&" + UserConfig.getInstance().getDevice());
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        KLog.e("写入MGAONLINE");
                        ProtoBufUpdate.this.updateInfo(2);
                    }
                }, 1000);
            } else {
                this.isUpdate = false;
                sendFinishUpdate();
                return;
            }
        }
        int progress = (this.position * 100) / this.packageList.size();
        KLog.e("progress:" + progress);
        String typeDesc = "AGPS";
        if (this.fuType == 1) {
            typeDesc = "FONT";
        } else if (this.fuType == 2) {
            typeDesc = "MGAONLINE";
        }
        KLog.d("yanxi...protobuf升级" + typeDesc + "进度:" + progress);
    }

    public int getFuType() {
        return this.fuType;
    }

    private void clearInfo() {
        this.position = 0;
        this.completeBytes = new byte[0];
        this.completeOffset = 0;
    }

    private void downloadFile(String url) {
        final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + this.rootPath;
        NetFactory.getInstance().getClient(new MyCallback() {
            public void onSuccess(Object o) {
                KLog.d("yanxi...PROTOBUF数据下载完成..." + ProtoBufUpdate.this.fuType);
                Log.e("update", "start");
                ProtoBufUpdate.this.isUpdate = true;
                ProtoBufUpdate.this.allBytes = FileIOUtils.readFile2BytesByMap(path + ProtoBufUpdate.this.fileName);
                BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().setFileDescUpdate(true));
            }

            public void onFail(Throwable e) {
                KLog.d(" PROTOBUF数据下载失败");
                ProtoBufUpdate.this.isUpdate = false;
                ProtoBufUpdate.this.sendFinishUpdate();
            }
        }).downAndSaveFile(url, this.rootPath, this.fileName);
    }

    /* access modifiers changed from: private */
    public void sendFinishUpdate() {
        KLog.d("升级完成或者无需要升级");
        ApgsFinishEvent event = new ApgsFinishEvent();
        event.setStatus(true);
        event.setType(4);
        EventBus.getDefault().post(event);
    }
}
