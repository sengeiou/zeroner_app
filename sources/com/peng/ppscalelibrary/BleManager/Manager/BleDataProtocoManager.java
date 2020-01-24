package com.peng.ppscalelibrary.BleManager.Manager;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.github.mikephil.charting.utils.Utils;
import com.inuker.bluetooth.library.beacon.Beacon;
import com.inuker.bluetooth.library.beacon.BeaconItem;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.utils.BluetoothLog;
import com.inuker.bluetooth.library.utils.ByteUtils;
import com.iwown.device_module.device_setting.configure.WristbandModel;
import com.peng.ppscalelibrary.BleManager.Interface.BleBMDJConnectInterface;
import com.peng.ppscalelibrary.BleManager.Interface.BleBMDJExitInterface;
import com.peng.ppscalelibrary.BleManager.Interface.BleBMDJInterface;
import com.peng.ppscalelibrary.BleManager.Interface.BleBMDJParttenInterface;
import com.peng.ppscalelibrary.BleManager.Interface.BleDataProtocoInterface;
import com.peng.ppscalelibrary.BleManager.Interface.BleDataStateInterface;
import com.peng.ppscalelibrary.BleManager.Model.BleConnectFliterModel;
import com.peng.ppscalelibrary.BleManager.Model.BleDeviceModel;
import com.peng.ppscalelibrary.BleManager.Model.BleEnum;
import com.peng.ppscalelibrary.BleManager.Model.BleEnum.BleSex;
import com.peng.ppscalelibrary.BleManager.Model.BleEnum.BleUnit;
import com.peng.ppscalelibrary.BleManager.Model.BleUserModel;
import com.peng.ppscalelibrary.BleManager.Model.LFPeopleGeneral;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.cli.HelpFormatter;

public class BleDataProtocoManager {
    public void analysisReciveData(String reciveData, String deviceMac, BleConnectFliterModel fliterModel, BleUserModel userModel, BleDataProtocoInterface protocoInterface, BleDataStateInterface stateInterface) {
        if (fliterModel.getScaleName().equals(BleConnectFliterModel.ELECTRONIC_SCALE)) {
            electronicScaleProtocol(reciveData, fliterModel, deviceMac, userModel, protocoInterface, stateInterface);
        } else if (fliterModel.getScaleName().equals(BleConnectFliterModel.ADORE_SCALE)) {
            adoreScaleProtocol(reciveData, fliterModel, deviceMac, userModel, protocoInterface, stateInterface);
        }
    }

    public String analysisSearchData(SearchResult device, BleConnectFliterModel fliterModel) {
        for (BeaconItem beaconItem : new Beacon(device.scanRecord).mItems) {
            if (beaconItem.type == fliterModel.getAdvDataType()) {
                String data = ByteUtils.byteToString(beaconItem.bytes);
                BluetoothLog.v("unanalysisSearchfinalData ------- " + data);
                if (data.length() > 22) {
                    int startIndex = data.length() - 22;
                    String finalData = data.substring(startIndex, startIndex + 22);
                    if (finalData.startsWith(BleEnum.BLE_SCALE_TYPE_CF) || finalData.startsWith(BleEnum.BLE_SCALE_TYPE_CE)) {
                        BluetoothLog.v("analysisSearchfinalData ------- " + finalData);
                        return finalData;
                    }
                } else {
                    continue;
                }
            }
        }
        return "";
    }

    public boolean isLockedData(String reciveData) {
        if (reciveData.substring(18, 20).equals("01")) {
            return false;
        }
        return true;
    }

    public List<byte[]> sendData(BleConnectFliterModel fliterModel, BleUserModel userModel) {
        if (fliterModel.getScaleName().equals(BleConnectFliterModel.ENERGY_SCALE) || fliterModel.getScaleName().equals(BleConnectFliterModel.HEALTH_SCALE) || fliterModel.getScaleName().equals(BleConnectFliterModel.BM_SCALE) || fliterModel.getScaleName().equals(BleConnectFliterModel.BODYFAT_SCALE) || fliterModel.getScaleName().equals(BleConnectFliterModel.HUMAN_SCALE)) {
            List<byte[]> resultArr = new ArrayList<>();
            resultArr.add(sendUnitData2Scale(userModel));
            return resultArr;
        } else if (fliterModel.getScaleName().equals(BleConnectFliterModel.ELECTRONIC_SCALE)) {
            return sendData2ElectronicScale(userModel);
        } else {
            if (!fliterModel.getScaleName().equals(BleConnectFliterModel.ADORE_SCALE)) {
                return new ArrayList<>();
            }
            List<byte[]> resultArr2 = new ArrayList<>();
            resultArr2.add(sendUnitData2Scale(userModel));
            resultArr2.add(sendSyncTimeData2AdoreScale());
            resultArr2.add(sendSyncHistoryData2AdoreScale());
            return resultArr2;
        }
    }

    public byte[] sendMBDJData2Scale() {
        return ByteUtils.stringToBytes("060F0000");
    }

    public byte[] sendExitMBDJData2Scale() {
        return ByteUtils.stringToBytes("06110000");
    }

    public static byte[] deleteAdoreHistoryData() {
        return ByteUtils.stringToBytes("F201");
    }

    public static byte[] sendSyncHistoryData2AdoreScale() {
        return ByteUtils.stringToBytes("F200");
    }

    private void electronicScaleProtocol(String reciveData, BleConnectFliterModel fliterModel, String deviceMac, BleUserModel userModel, BleDataProtocoInterface protocoInterface, BleDataStateInterface stateInterface) {
        if (reciveData.length() == 32) {
            if (reciveData.substring(0, 2).equals(BleEnum.BLE_SCALE_TYPE_CF)) {
                String weightDataLow = reciveData.substring(8, 10);
                double weightKg = ((double) hexToTen(weightDataLow + reciveData.substring(10, 12))) / 10.0d;
                double bmi = weightKg / Math.pow(((double) userModel.userHeight) / 100.0d, 2.0d);
                String fatDataLow = reciveData.substring(12, 14);
                double bodyfatPercentage = ((double) hexToTen(fatDataLow + reciveData.substring(14, 16))) / 10.0d;
                double boneKg = ((double) hexToTen(reciveData.substring(16, 18))) / 10.0d;
                String muscleDataLow = reciveData.substring(18, 20);
                double muscleKg = ((double) hexToTen(muscleDataLow + reciveData.substring(20, 22))) / 10.0d;
                int VFL = hexToTen(reciveData.substring(22, 24));
                String waterPercentageDataLow = reciveData.substring(24, 26);
                double waterPercentage = ((double) hexToTen(waterPercentageDataLow + reciveData.substring(26, 28))) / 10.0d;
                String BMRDataLow = reciveData.substring(28, 30);
                int BMR = hexToTen(BMRDataLow + reciveData.substring(30, 32));
                final LFPeopleGeneral bodyDataModel = new LFPeopleGeneral(weightKg, (double) userModel.userHeight, sexEnum2Int(userModel.sex), userModel.age, 0, Utils.DOUBLE_EPSILON, Utils.DOUBLE_EPSILON, BleEnum.BLE_SCALE_TYPE_CF, fliterModel.getScaleName());
                bodyDataModel.lfWeightKg = weightKg;
                bodyDataModel.lfBMI = bmi;
                bodyDataModel.lfBodyfatPercentage = bodyfatPercentage;
                bodyDataModel.lfBoneKg = boneKg;
                bodyDataModel.lfMuscleKg = muscleKg;
                bodyDataModel.lfVFAL = VFL;
                bodyDataModel.lfWaterPercentage = waterPercentage;
                bodyDataModel.lfBMR = BMR;
                BleDeviceModel bleDeviceModel = new BleDeviceModel(deviceMac, fliterModel.getScaleName());
                Handler handler = new Handler(Looper.getMainLooper());
                final BleDataProtocoInterface bleDataProtocoInterface = protocoInterface;
                final BleDeviceModel bleDeviceModel2 = bleDeviceModel;
                handler.post(new Runnable() {
                    public void run() {
                        bleDataProtocoInterface.lockedData(bodyDataModel, bleDeviceModel2);
                        bleDataProtocoInterface.deviceInfo(bleDeviceModel2);
                    }
                });
            }
        }
    }

    public void energyScaleProtocol(String reciveData, BleConnectFliterModel fliterModel, String deviceMac, BleUserModel userModel, BleDataProtocoInterface protocoInterface) {
        if (reciveData.length() >= 22) {
            String reciveData2 = reciveData.substring(0, 22);
            String preStr = reciveData2.substring(0, 2);
            if (preStr.equals(BleEnum.BLE_SCALE_TYPE_CF)) {
                double weightKg = ((double) hexToTen(reciveData2.substring(8, 10) + reciveData2.substring(6, 8))) / 100.0d;
                int sex = sexEnum2Int(userModel.sex);
                int impedance = hexToTen(reciveData2.substring(14, 16) + reciveData2.substring(12, 14) + reciveData2.substring(10, 12));
                LFPeopleGeneral bodyDataModel = new LFPeopleGeneral(weightKg, (double) userModel.userHeight, sex, userModel.age, impedance, Utils.DOUBLE_EPSILON, Utils.DOUBLE_EPSILON, BleEnum.BLE_SCALE_TYPE_CF, fliterModel.getScaleName());
                final String signLocked = reciveData2.substring(18, 20);
                Handler handler = new Handler(Looper.getMainLooper());
                final int finalImpedance = impedance;
                final double d = weightKg;
                final BleDataProtocoInterface bleDataProtocoInterface = protocoInterface;
                final LFPeopleGeneral lFPeopleGeneral = bodyDataModel;
                final String str = deviceMac;
                final BleConnectFliterModel bleConnectFliterModel = fliterModel;
                handler.post(new Runnable() {
                    public void run() {
                        if (signLocked.equals("01")) {
                            BluetoothLog.v("progress weight ------- " + d);
                            bleDataProtocoInterface.progressData(lFPeopleGeneral);
                            return;
                        }
                        BluetoothLog.v("locked weight ------- " + d + "impedanceInt ------- " + finalImpedance);
                        BleDeviceModel deviceModel = new BleDeviceModel(str, bleConnectFliterModel.getScaleName());
                        bleDataProtocoInterface.lockedData(lFPeopleGeneral, deviceModel);
                        bleDataProtocoInterface.deviceInfo(deviceModel);
                    }
                });
                return;
            }
            if (preStr.equals(BleEnum.BLE_SCALE_TYPE_CE)) {
                double weightKg2 = ((double) hexToTen(reciveData2.substring(8, 10) + reciveData2.substring(6, 8))) / 100.0d;
                String signLocked2 = reciveData2.substring(18, 20);
                LFPeopleGeneral lFPeopleGeneral2 = new LFPeopleGeneral(weightKg2, (double) userModel.userHeight, sexEnum2Int(userModel.sex), userModel.age, 0, Utils.DOUBLE_EPSILON, Utils.DOUBLE_EPSILON, BleEnum.BLE_SCALE_TYPE_CF, fliterModel.getScaleName());
                Handler handler2 = new Handler(Looper.getMainLooper());
                final String str2 = signLocked2;
                final double d2 = weightKg2;
                final BleDataProtocoInterface bleDataProtocoInterface2 = protocoInterface;
                final LFPeopleGeneral lFPeopleGeneral3 = lFPeopleGeneral2;
                final String str3 = deviceMac;
                final BleConnectFliterModel bleConnectFliterModel2 = fliterModel;
                handler2.post(new Runnable() {
                    public void run() {
                        if (str2.equals("01")) {
                            BluetoothLog.v("progress weight ------- " + d2);
                            bleDataProtocoInterface2.progressData(lFPeopleGeneral3);
                            return;
                        }
                        BluetoothLog.v("locked weight ------- " + d2);
                        BleDeviceModel deviceModel = new BleDeviceModel(str3, bleConnectFliterModel2.getScaleName());
                        bleDataProtocoInterface2.lockedData(lFPeopleGeneral3, deviceModel);
                        bleDataProtocoInterface2.deviceInfo(deviceModel);
                    }
                });
            }
        }
    }

    public void bmScaleProtocol(String reciveData, BleConnectFliterModel fliterModel, BleBMDJInterface bmdjInterface) {
        BluetoothLog.v(reciveData);
        boolean isEnd = false;
        if (reciveData.startsWith("10060F01")) {
            isEnd = false;
        }
        if (reciveData.startsWith("10060F02")) {
            isEnd = true;
        }
        int time = hexToTen(reciveData.substring(10, 12) + reciveData.substring(8, 10));
        if (bmdjInterface != null) {
            bmdjInterface.timeInterval(time, isEnd);
        }
    }

    public void bmScaleStatusProtocol(String reciveData, BleConnectFliterModel fliterModel, BleBMDJConnectInterface connectInterface) {
        if (reciveData.equals("10060F0001") && connectInterface != null) {
            connectInterface.connectSuccess();
        }
        if (reciveData.equals("10060F0003") && connectInterface != null) {
            connectInterface.connectFailed();
        }
    }

    public void bmScaleExitStatusProtocol(String reciveData, BleConnectFliterModel fliterModel, BleBMDJExitInterface exitInterface) {
        if (reciveData.equals("1006110001") && exitInterface != null) {
            exitInterface.exitSuccess();
        }
        if (reciveData.equals("1006110003") && exitInterface != null) {
            exitInterface.exitFailed();
        }
    }

    public void bmScaleStartTimingProtocol(String reciveData, BleConnectFliterModel fliterModel, BleBMDJParttenInterface parttenInterface) {
        BluetoothLog.v(reciveData);
        if (reciveData.equals("10060F010100") && parttenInterface != null) {
            parttenInterface.startTiming();
        }
    }

    private void adoreScaleProtocol(String reciveData, BleConnectFliterModel fliterModel, String deviceMac, BleUserModel userModel, BleDataProtocoInterface protocoInterface, BleDataStateInterface stateInterface) {
        if (reciveData.length() == fliterModel.getHistoryDataLength()) {
            if (reciveData.substring(0, 2).equals(BleEnum.BLE_SCALE_TYPE_CF)) {
                LFPeopleGeneral bodyDataModel = new LFPeopleGeneral(((double) hexToTen(reciveData.substring(8, 10) + reciveData.substring(6, 8))) / 100.0d, (double) userModel.userHeight, sexEnum2Int(userModel.sex), userModel.age, hexToTen(reciveData.substring(14, 16) + reciveData.substring(12, 14) + reciveData.substring(10, 12)), Utils.DOUBLE_EPSILON, Utils.DOUBLE_EPSILON, BleEnum.BLE_SCALE_TYPE_CF, fliterModel.getScaleName());
                int year = hexToTen(reciveData.substring(22, 24) + reciveData.substring(24, 26));
                int mounth = hexToTen(reciveData.substring(26, 28));
                int day = hexToTen(reciveData.substring(28, 30));
                int hour = hexToTen(reciveData.substring(30, 32));
                int minite = hexToTen(reciveData.substring(32, 34));
                int secound = hexToTen(reciveData.substring(34, 36));
                final BleDeviceModel deviceModel = new BleDeviceModel(deviceMac, fliterModel.getScaleName());
                String clock = year + HelpFormatter.DEFAULT_OPT_PREFIX;
                if (mounth < 10) {
                    clock = clock + "0";
                }
                String clock2 = clock + mounth + HelpFormatter.DEFAULT_OPT_PREFIX;
                if (day < 10) {
                    clock2 = clock2 + "0";
                }
                String clock3 = clock2 + day + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR;
                if (hour < 10) {
                    clock3 = clock3 + "0";
                }
                String clock4 = clock3 + hour + ":";
                if (minite < 10) {
                    clock4 = clock4 + '0';
                }
                String clock5 = clock4 + minite + ":";
                if (secound < 10) {
                    clock5 = clock5 + '0';
                }
                String clock6 = clock5 + secound;
                Handler handler = new Handler(Looper.getMainLooper());
                final String finalClock = clock6;
                final BleDataProtocoInterface bleDataProtocoInterface = protocoInterface;
                final LFPeopleGeneral lFPeopleGeneral = bodyDataModel;
                handler.post(new Runnable() {
                    public void run() {
                        bleDataProtocoInterface.deviceInfo(deviceModel);
                        bleDataProtocoInterface.historyData(false, lFPeopleGeneral, finalClock);
                    }
                });
            }
        } else if (reciveData.length() == fliterModel.getHistoryEndDataLength()) {
            if (reciveData.equals("F200")) {
                Handler handler2 = new Handler(Looper.getMainLooper());
                final BleDataStateInterface bleDataStateInterface = stateInterface;
                final BleDataProtocoInterface bleDataProtocoInterface2 = protocoInterface;
                handler2.post(new Runnable() {
                    public void run() {
                        bleDataStateInterface.sendHistoryData();
                        bleDataProtocoInterface2.historyData(true, null, "");
                    }
                });
            }
        }
    }

    private static List<byte[]> sendData2ElectronicScale(BleUserModel userModel) {
        byte[] xorByte = getXorForElectronicScale(ByteUtils.stringToBytes("FE" + decimal2Hex(userModel.groupNum) + decimal2Hex(sexEnum2Int(userModel.sex)) + decimal2Hex(0) + decimal2Hex(userModel.userHeight) + decimal2Hex(userModel.age) + decimal2Hex(electronicUnitEnum2Int(userModel.unit))));
        List<byte[]> resultArr = new ArrayList<>();
        resultArr.add(xorByte);
        return resultArr;
    }

    private static byte[] sendUnitData2Scale(BleUserModel userModel) {
        int sexEnum2Int = sexEnum2Int(userModel.sex);
        return getXor(ByteUtils.stringToBytes("FD00" + decimal2Hex(energyUnitEnum2Int(userModel.unit)) + "00000000000000"));
    }

    private static byte[] sendSyncTimeData2AdoreScale() {
        String[] strArr = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss").format(new Date()).split("/");
        String byteStr = WristbandModel.MODEL_F1;
        for (String s : strArr) {
            byteStr = byteStr + decimal2Hex(Integer.parseInt(s));
        }
        return ByteUtils.stringToBytes(byteStr);
    }

    private static int hexToTen(String hex) {
        if (TextUtils.isEmpty(hex)) {
            return 0;
        }
        return Integer.valueOf(hex, 16).intValue();
    }

    private static byte[] getXorForElectronicScale(byte[] datas) {
        byte[] bytes = new byte[(datas.length + 1)];
        byte temp = datas[1];
        bytes[0] = datas[0];
        bytes[1] = datas[1];
        for (int i = 2; i < datas.length; i++) {
            bytes[i] = datas[i];
            temp = (byte) (datas[i] ^ temp);
        }
        bytes[datas.length] = temp;
        return bytes;
    }

    private static byte[] getXor(byte[] datas) {
        byte[] bytes = new byte[(datas.length + 1)];
        byte temp = datas[0];
        bytes[0] = datas[0];
        for (int i = 1; i < datas.length; i++) {
            bytes[i] = datas[i];
            temp = (byte) (datas[i] ^ temp);
        }
        bytes[datas.length] = temp;
        return bytes;
    }

    private static int sexEnum2Int(BleSex enumSex) {
        switch (enumSex) {
            case Male:
                return 1;
            case Female:
                return 0;
            default:
                return 0;
        }
    }

    private static int electronicUnitEnum2Int(BleUnit enumUnit) {
        switch (enumUnit) {
            case BLE_UNIT_KG:
                return 1;
            case BLE_UNIT_LB:
                return 2;
            case BLE_UNIT_JIN:
                return 1;
            case BLE_UNIT_ST_LB:
                return 4;
            default:
                return 0;
        }
    }

    private static int energyUnitEnum2Int(BleUnit enumUnit) {
        switch (enumUnit) {
            case BLE_UNIT_KG:
                return 0;
            case BLE_UNIT_LB:
                return 1;
            case BLE_UNIT_JIN:
                return 3;
            case BLE_UNIT_ST_LB:
                return 0;
            default:
                return 0;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002e  */
    /* JADX WARNING: Removed duplicated region for block: B:21:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String decimal2Hex(int r6) {
        /*
            java.lang.String r0 = ""
            r1 = 0
        L_0x0004:
            r4 = 9
            if (r1 >= r4) goto L_0x0026
            int r3 = r6 % 16
            int r6 = r6 / 16
            switch(r3) {
                case 10: goto L_0x0043;
                case 11: goto L_0x0047;
                case 12: goto L_0x004b;
                case 13: goto L_0x004f;
                case 14: goto L_0x0053;
                case 15: goto L_0x0057;
                default: goto L_0x000f;
            }
        L_0x000f:
            java.lang.String r2 = java.lang.String.valueOf(r3)
        L_0x0013:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.StringBuilder r4 = r4.append(r2)
            java.lang.StringBuilder r4 = r4.append(r0)
            java.lang.String r0 = r4.toString()
            if (r6 != 0) goto L_0x005b
        L_0x0026:
            int r4 = r0.length()
            int r4 = r4 % 2
            if (r4 == 0) goto L_0x0042
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "0"
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.StringBuilder r4 = r4.append(r0)
            java.lang.String r0 = r4.toString()
        L_0x0042:
            return r0
        L_0x0043:
            java.lang.String r2 = "A"
            goto L_0x0013
        L_0x0047:
            java.lang.String r2 = "B"
            goto L_0x0013
        L_0x004b:
            java.lang.String r2 = "C"
            goto L_0x0013
        L_0x004f:
            java.lang.String r2 = "D"
            goto L_0x0013
        L_0x0053:
            java.lang.String r2 = "E"
            goto L_0x0013
        L_0x0057:
            java.lang.String r2 = "F"
            goto L_0x0013
        L_0x005b:
            int r1 = r1 + 1
            goto L_0x0004
        */
        throw new UnsupportedOperationException("Method not decompiled: com.peng.ppscalelibrary.BleManager.Manager.BleDataProtocoManager.decimal2Hex(int):java.lang.String");
    }
}
