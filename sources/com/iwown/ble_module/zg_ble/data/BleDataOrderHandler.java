package com.iwown.ble_module.zg_ble.data;

import android.content.Context;
import android.support.annotation.IntRange;
import com.google.common.base.Ascii;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.utils.JsonTool;
import com.iwown.ble_module.zg_ble.BleHandler;
import com.iwown.ble_module.zg_ble.data.alarm_clock.ZGAlarmClockScheduleHandler;
import com.iwown.ble_module.zg_ble.data.alarm_clock.ZGAlarmClockScheduleHandler.ZGAlarmClockBean;
import com.iwown.ble_module.zg_ble.data.alarm_clock.ZGAlarmClockScheduleHandler.ZGSchedule;
import com.iwown.ble_module.zg_ble.data.model.DeviceSetting;
import com.iwown.ble_module.zg_ble.data.model.NotificationType;
import com.iwown.ble_module.zg_ble.data.model.TDay;
import com.iwown.ble_module.zg_ble.data.model.impl.ZGHeartHandler;
import com.iwown.ble_module.zg_ble.task.AgpsBleMessage;
import com.iwown.ble_module.zg_ble.task.BleMessage;
import com.iwown.ble_module.zg_ble.utils.ByteUtil;
import com.iwown.ble_module.zg_ble.utils.Util;
import com.iwown.lib_common.log.L;
import com.socks.library.KLog;
import com.tencent.tinker.android.dx.instruction.Opcodes;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class BleDataOrderHandler {

    private static class BleDataOrderHanlderHolder {
        public static BleDataOrderHandler bleDataOrderHandler = new BleDataOrderHandler();

        private BleDataOrderHanlderHolder() {
        }
    }

    public static class Settings {
        public static final int Setting_1 = 1;
        public static final int Setting_10 = 10;
        public static final int Setting_11 = 11;
        public static final int Setting_12 = 12;
        public static final int Setting_13 = 13;
        public static final int Setting_14 = 14;
        public static final int Setting_15 = 15;
        public static final int Setting_16 = 16;
        public static final int Setting_17 = 17;
        public static final int Setting_18 = 18;
        public static final int Setting_19 = 19;
        public static final int Setting_2 = 2;
        public static final int Setting_20 = 20;
        public static final int Setting_21 = 21;
        public static final int Setting_22 = 22;
        public static final int Setting_23 = 23;
        public static final int Setting_24 = 24;
        public static final int Setting_3 = 3;
        public static final int Setting_4 = 4;
        public static final int Setting_5 = 5;
        public static final int Setting_6 = 6;
        public static final int Setting_7 = 7;
        public static final int Setting_8 = 8;
        public static final int Setting_9 = 9;
    }

    public static BleDataOrderHandler getInstance() {
        return BleDataOrderHanlderHolder.bleDataOrderHandler;
    }

    public void setAlarmClockAndSchedule(Context context, List<ZGAlarmClockBean> zgAlarmClockBeanList, List<ZGSchedule> scheduleList) {
        int count;
        byte[] mergeHeadLenBytes;
        StringBuilder stringBuilder = new StringBuilder();
        byte[] datas = new byte[240];
        List<byte[]> all_datas = new ArrayList<>();
        byte[] alarm_bytes = ZGAlarmClockScheduleHandler.writeAlarmZG(zgAlarmClockBeanList);
        int count2 = 1;
        int index = 0;
        int i = 0;
        while (i < alarm_bytes.length) {
            byte[] alarm_data = new byte[16];
            System.arraycopy(alarm_bytes, i, alarm_data, 0, 16);
            int count3 = count + 1;
            byte[] mergeHeadLenBytes2 = getMergeHeadLenBytes(4, 0, count, alarm_data);
            System.arraycopy(mergeHeadLenBytes2, 0, datas, index, mergeHeadLenBytes2.length);
            index += mergeHeadLenBytes2.length;
            String s = ByteUtil.bytesToString1(mergeHeadLenBytes2);
            KLog.d("setAlarmClockAndSchedule alarm bytes " + s + " size " + mergeHeadLenBytes2.length);
            all_datas.add(mergeHeadLenBytes2);
            stringBuilder.append(s + "\n");
            i += 16;
            count2 = count3;
        }
        KLog.d("alarm  count " + count + "  index " + index);
        byte[] schedule_bytes = ZGAlarmClockScheduleHandler.writeScheduleZG(scheduleList);
        int index2 = 0;
        for (int i2 = 0; i2 < schedule_bytes.length; i2 += 16) {
            byte[] alarm_data2 = new byte[16];
            System.arraycopy(schedule_bytes, i2, alarm_data2, 0, 16);
            if (i2 + 16 >= schedule_bytes.length) {
                count += 128;
                mergeHeadLenBytes = getMergeHeadLenBytes(4, 0, count, alarm_data2);
            } else {
                int count4 = count + 1;
                mergeHeadLenBytes = getMergeHeadLenBytes(4, 0, count, alarm_data2);
                count = count4;
            }
            System.arraycopy(mergeHeadLenBytes, 0, datas, index2, mergeHeadLenBytes.length);
            index2 += mergeHeadLenBytes.length;
            String s2 = ByteUtil.bytesToString1(mergeHeadLenBytes);
            KLog.d("setAlarmClockAndSchedule schedule bytes " + s2 + " size " + mergeHeadLenBytes.length);
            all_datas.add(mergeHeadLenBytes);
            stringBuilder.append(s2 + "\n");
        }
        KLog.d("schedule  count " + count + "  index " + index2);
        BleHandler.getInstance().addTaskMessage(new BleMessage(all_datas));
    }

    private byte[] getMergeHeadLenBytes(int cmd, int ext_cmd, int packet_No, byte[] bytes) {
        ArrayList<byte[]> datas = new ArrayList<>();
        datas.add(bytes);
        return writeWristBandData(cmd, ext_cmd, packet_No, datas);
    }

    private byte[] writeWristBandData(int cmd, int Ext_cmd, int Packet_No, ArrayList<byte[]> datas) {
        byte[] commonData = new byte[4];
        commonData[0] = (byte) cmd;
        commonData[1] = (byte) Ext_cmd;
        commonData[2] = (byte) Packet_No;
        if (datas != null) {
            commonData[3] = (byte) ((byte[]) datas.get(0)).length;
            byte[] data = new byte[((byte[]) datas.get(0)).length];
            for (int i = 0; i < ((byte[]) datas.get(0)).length; i++) {
                data[i] = ((byte[]) datas.get(0))[i];
            }
            return Util.concat(commonData, data);
        }
        commonData[3] = 0;
        return commonData;
    }

    public byte[] getHardwareFeatures() {
        return getMergeHeadLenBytes(Opcodes.LONG_TO_DOUBLE, 0, 129, new byte[]{0});
    }

    public byte[] getBattery() {
        return new byte[0];
    }

    public byte[] setConnectMode() {
        return new byte[]{1, 90, -127, 8, 1, 2, 3, 4, 5, 6, 7, 8};
    }

    public byte[] queryConnectMode() {
        return new byte[]{-127, 0, -127, 1, 0};
    }

    public byte[] getDataDate() {
        return new byte[]{-120, 0, -127, 1, 0};
    }

    public byte[] getFirmwareInformation() {
        return getMergeHeadLenBytes(Opcodes.LONG_TO_FLOAT, 0, 129, new byte[]{0});
    }

    public void setAllOfThem(Context context, int callStatus, int callStart, int callEnd, int MsgStatus, int msgStart, int msgEnd, int gestures, int gesStartHour, int gesEndHour, int heartOn, int heartStartHour, int heartEndHour, int language, int warmingOn, int heartHighAlarm, int heartLowAlarm, int unitType, int temType, int timeDisplay) {
        KLog.i("-----------setAllOfThem-------------------");
        List<byte[]> data = new ArrayList<>();
        DeviceSetting ds = getDeviceSetting(context);
        if (ds != null) {
            if (gesEndHour == 0) {
                gesEndHour = 24;
            }
            if (gesEndHour > 0) {
                gesEndHour--;
            }
            try {
                KLog.e("heart --> " + heartEndHour);
                if (heartEndHour == 0) {
                    heartEndHour = 24;
                }
                if (heartEndHour > 0) {
                    heartEndHour--;
                }
                try {
                    KLog.e("call --> " + callEnd);
                    if (callEnd == 0) {
                        callEnd = 24;
                    }
                    if (callEnd > 0) {
                        callEnd--;
                    }
                    try {
                        KLog.e("msgEnd --> " + msgEnd);
                        if (msgEnd == 0) {
                            msgEnd = 24;
                        }
                        if (msgEnd > 0) {
                            msgEnd--;
                        }
                        byte zgType = 0;
                        if (language == 0) {
                            zgType = 0;
                        } else if (language == 1) {
                            zgType = 16;
                        } else if (language == 2) {
                            zgType = 64;
                        } else if (language == 3) {
                            zgType = 32;
                        } else if (language != 4) {
                            if (language == 5) {
                                zgType = 48;
                            } else if (language == 9) {
                                zgType = 80;
                            } else if (language == 13) {
                                zgType = -48;
                            }
                        }
                        byte unit = (byte) (((byte) (((byte) ds.getUnitSet()) & Integer.parseInt("00001111", 2))) | zgType);
                        if (unitType == 0) {
                            unit = (byte) (unit & 253);
                        } else if (unitType == 1) {
                            unit = (byte) (unit | 2);
                        }
                        if (temType == 0) {
                            unit = (byte) (unit & 251);
                        } else if (temType == 1) {
                            unit = (byte) (unit | 4);
                        }
                        if (timeDisplay == 0) {
                            unit = (byte) (unit & 254);
                        } else if (timeDisplay == 1) {
                            unit = (byte) (unit | 1);
                        }
                        byte[] d1 = ds.getD1();
                        d1[0] = 5;
                        d1[4] = unit;
                        d1[16] = (byte) callStatus;
                        d1[17] = (byte) callStart;
                        d1[18] = (byte) callEnd;
                        data.add(d1);
                        byte[] d2 = ds.getD2();
                        d2[0] = 5;
                        d2[4] = (byte) MsgStatus;
                        d2[5] = (byte) msgStart;
                        d2[6] = (byte) msgEnd;
                        d2[8] = (byte) heartOn;
                        d2[9] = (byte) heartStartHour;
                        d2[10] = (byte) heartEndHour;
                        d2[11] = (byte) warmingOn;
                        d2[12] = (byte) heartHighAlarm;
                        d2[13] = (byte) heartLowAlarm;
                        d2[19] = (byte) gestures;
                        data.add(d2);
                        byte[] d3 = ds.getD3();
                        d3[0] = 5;
                        d3[4] = (byte) gesStartHour;
                        d3[5] = (byte) gesEndHour;
                        d3[7] = 0;
                        data.add(d3);
                        addTasks(context, data);
                        ds.setComingCallEnable(callStatus);
                        ds.setMessageEnable(MsgStatus);
                        ds.setRollEnable(gestures);
                        ds.setRollStartHour(gesStartHour);
                        ds.setRollEndHour(gesEndHour);
                        ds.setQuietHeartEnable(heartOn);
                        ds.setQuietHeartStartHour(heartStartHour);
                        ds.setQuietHeartEndHour(heartEndHour);
                        ds.setUnitSet(unit);
                        ds.setHeartAlarmEnable(warmingOn);
                        ds.setHighHeartAlarm(heartHighAlarm);
                        ds.setLowHeartAlarm(heartLowAlarm);
                        ds.setD1(d1);
                        ds.setD2(d2);
                        ds.setD3(d3);
                        setDeviceSetting(context, JsonTool.toJson(ds));
                    } catch (Exception e) {
                    }
                } catch (Exception e2) {
                }
            } catch (Exception e3) {
            }
        }
    }

    public void setLanguage(Context context, int type) {
        byte zgType;
        switch (type) {
            case 0:
                zgType = 0;
                break;
            case 1:
                zgType = 16;
                break;
            case 2:
                zgType = 64;
                break;
            case 3:
                zgType = 32;
                break;
            case 4:
                zgType = 112;
                break;
            case 5:
                zgType = 48;
                break;
            case 6:
                zgType = -112;
                break;
            case 7:
                zgType = Byte.MIN_VALUE;
                break;
            case 8:
                zgType = -96;
                break;
            case 9:
                zgType = 80;
                break;
            case 10:
                zgType = -64;
                break;
            case 11:
                zgType = 96;
                break;
            case 12:
                zgType = -80;
                break;
            case 13:
                zgType = -48;
                break;
            case 14:
                zgType = -32;
                break;
            default:
                zgType = 0;
                break;
        }
        List<byte[]> data = new ArrayList<>();
        DeviceSetting ds = getDeviceSetting(context);
        if (ds != null) {
            byte unit = (byte) (((byte) (((byte) ds.getUnitSet()) & Integer.parseInt("00001111", 2))) | zgType);
            try {
                checkData(ds, 1, new int[]{unit});
                byte[] d1 = ds.getD1();
                d1[0] = 5;
                d1[4] = unit;
                data.add(d1);
                byte[] d2 = ds.getD2();
                d2[0] = 5;
                data.add(d2);
                byte[] d3 = ds.getD3();
                d3[0] = 5;
                d3[7] = 0;
                data.add(d3);
                BleHandler.getInstance().addTaskMessage(new BleMessage(data));
                ds.setUnitSet(unit);
                ds.setD1(d1);
                ds.setD2(d2);
                ds.setD3(d3);
                setDeviceSetting(context, JsonTool.toJson(ds));
            } catch (Exception e) {
            }
        }
    }

    /* JADX WARNING: type inference failed for: r6v0, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v1, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v2, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r8v2, types: [int[]] */
    /* JADX WARNING: type inference failed for: r0v0, types: [byte[], java.lang.Object] */
    /* JADX WARNING: type inference failed for: r6v3, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v4 */
    /* JADX WARNING: type inference failed for: r6v5 */
    /* JADX WARNING: Incorrect type for immutable var: ssa=byte[], code=null, for r0v0, types: [byte[], java.lang.Object] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r6v0, names: [unit], types: [byte]
      assigns: [byte]
      uses: [?[int, boolean, short, byte, char], ?[int, float], byte, int]
      mth insns count: 42
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1507)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1507)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1507)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 5 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setTimeDisplay(android.content.Context r13, int r14) {
        /*
            r12 = this;
            r11 = 5
            r8 = 1
            r10 = 0
            java.lang.String r7 = "-----------setTimeDisplay-------------------"
            com.socks.library.KLog.i(r7)
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            com.iwown.ble_module.zg_ble.data.model.DeviceSetting r4 = r12.getDeviceSetting(r13)
            if (r4 != 0) goto L_0x0015
        L_0x0014:
            return
        L_0x0015:
            int r7 = r4.getUnitSet()
            byte r6 = (byte) r7
            if (r14 != 0) goto L_0x006a
            r7 = r6 & 254(0xfe, float:3.56E-43)
            byte r6 = (byte) r7
        L_0x001f:
            r7 = 1
            r8 = 1
            int[] r8 = new int[r8]     // Catch:{ Exception -> 0x0070 }
            r9 = 0
            r8[r9] = r6     // Catch:{ Exception -> 0x0070 }
            r12.checkData(r4, r7, r8)     // Catch:{ Exception -> 0x0070 }
            byte[] r0 = r4.getD1()
            r0[r10] = r11
            r7 = 4
            r0[r7] = r6
            r3.add(r0)
            byte[] r1 = r4.getD2()
            r1[r10] = r11
            r3.add(r1)
            byte[] r2 = r4.getD3()
            r2[r10] = r11
            r7 = 7
            r2[r7] = r10
            r3.add(r2)
            com.iwown.ble_module.zg_ble.BleHandler r7 = com.iwown.ble_module.zg_ble.BleHandler.getInstance()
            com.iwown.ble_module.zg_ble.task.BleMessage r8 = new com.iwown.ble_module.zg_ble.task.BleMessage
            r8.<init>(r3)
            r7.addTaskMessage(r8)
            r4.setUnitSet(r6)
            r4.setD1(r0)
            r4.setD2(r1)
            r4.setD3(r2)
            java.lang.String r7 = com.iwown.ble_module.utils.JsonTool.toJson(r4)
            r12.setDeviceSetting(r13, r7)
            goto L_0x0014
        L_0x006a:
            if (r14 != r8) goto L_0x001f
            r7 = r6 | 1
            byte r6 = (byte) r7
            goto L_0x001f
        L_0x0070:
            r5 = move-exception
            goto L_0x0014
        */
        throw new UnsupportedOperationException("Method not decompiled: com.iwown.ble_module.zg_ble.data.BleDataOrderHandler.setTimeDisplay(android.content.Context, int):void");
    }

    public void setUserWeight(Context context, int weight) {
        KLog.i("-----------setUserWeight-------------------");
        List<byte[]> data = new ArrayList<>();
        DeviceSetting ds = getDeviceSetting(context);
        try {
            checkData(ds, 2, new int[]{weight});
            byte[] d1 = ds.getD1();
            d1[0] = 5;
            d1[5] = (byte) weight;
            data.add(d1);
            byte[] d2 = ds.getD2();
            d2[0] = 5;
            data.add(d2);
            byte[] d3 = ds.getD3();
            d3[0] = 5;
            d3[7] = 0;
            data.add(d3);
            BleHandler.getInstance().addTaskMessage(new BleMessage(data));
            ds.setWeight(weight);
            ds.setD1(d1);
            ds.setD2(d2);
            ds.setD3(d3);
            setDeviceSetting(context, JsonTool.toJson(ds));
        } catch (Exception e) {
        }
    }

    public void setWalkStride(Context context, int stride) {
        List<byte[]> data = new ArrayList<>();
        DeviceSetting ds = getDeviceSetting(context);
        try {
            checkData(ds, 3, new int[]{stride});
            byte[] d1 = ds.getD1();
            d1[0] = 5;
            d1[6] = (byte) stride;
            data.add(d1);
            byte[] d2 = ds.getD2();
            d2[0] = 5;
            data.add(d2);
            byte[] d3 = ds.getD3();
            d3[0] = 5;
            d3[7] = 0;
            data.add(d3);
            BleHandler.getInstance().addTaskMessage(new BleMessage(data));
            ds.setWalkStride(stride);
            ds.setD1(d1);
            ds.setD2(d2);
            ds.setD3(d3);
            setDeviceSetting(context, JsonTool.toJson(ds));
        } catch (Exception e) {
        }
    }

    public void setRunStride(Context context, int stride) {
        List<byte[]> data = new ArrayList<>();
        DeviceSetting ds = getDeviceSetting(context);
        try {
            checkData(ds, 4, new int[]{stride});
            byte[] d1 = ds.getD1();
            d1[0] = 5;
            d1[7] = (byte) stride;
            data.add(d1);
            byte[] d2 = ds.getD2();
            d2[0] = 5;
            data.add(d2);
            byte[] d3 = ds.getD3();
            d3[0] = 5;
            d3[7] = 0;
            data.add(d3);
            BleHandler.getInstance().addTaskMessage(new BleMessage(data));
            ds.setRunStride(stride);
            ds.setD1(d1);
            ds.setD2(d2);
            ds.setD3(d3);
            setDeviceSetting(context, JsonTool.toJson(ds));
        } catch (Exception e) {
        }
    }

    public void setStride(Context context, int wStride, int rStride) {
        KLog.i("-------------------setStride--------------");
        List<byte[]> data = new ArrayList<>();
        DeviceSetting ds = getDeviceSetting(context);
        try {
            checkData(ds, 14, new int[]{wStride, rStride});
            byte[] d1 = ds.getD1();
            d1[0] = 5;
            d1[6] = (byte) wStride;
            d1[7] = (byte) rStride;
            data.add(d1);
            byte[] d2 = ds.getD2();
            d2[0] = 5;
            data.add(d2);
            byte[] d3 = ds.getD3();
            d3[0] = 5;
            d3[7] = 0;
            data.add(d3);
            BleHandler.getInstance().addTaskMessage(new BleMessage(data));
            ds.setWalkStride(wStride);
            ds.setRunStride(rStride);
            ds.setD1(d1);
            ds.setD2(d2);
            ds.setD3(d3);
            setDeviceSetting(context, JsonTool.toJson(ds));
        } catch (Exception e) {
        }
    }

    private DeviceSetting getDeviceSetting(Context context) {
        DeviceSetting ds = (DeviceSetting) JsonTool.fromJson(SPUtils.readFirmwareInformation(context), DeviceSetting.class);
        if (ds != null) {
            return ds;
        }
        return null;
    }

    private void setDeviceSetting(Context context, String settingsJson) {
        SPUtils.saveFirmwareInformation(context, settingsJson);
    }

    private void checkData(DeviceSetting ds, int type, int[] array) throws Exception {
        if (ds == null) {
            throw new RuntimeException();
        }
    }

    public void syncDataOver(Context context) {
        BleHandler.getInstance().addTaskMessage(new BleMessage(new byte[]{-118, 0, -127, 1, 0}));
    }

    public byte[] getDetailWalk(int day) {
        return new byte[]{-119, ByteUtil.hexToBytes(String.valueOf(day) + "1")[0], -127, 1, 0};
    }

    public byte[] getDetailSport(int day) {
        return new byte[]{-119, ByteUtil.hexToBytes(String.valueOf(day) + "4")[0], -127, 1, 0};
    }

    public byte[] getDetailSleep(int day) {
        return new byte[]{-119, ByteUtil.hexToBytes(String.valueOf(day) + "3")[0], -127, 1, 0};
    }

    public byte[] readHeartData(int marginSize) {
        return new byte[]{-119, ZGHeartHandler.readHeart(marginSize), -127, 1, 0};
    }

    public byte[] getTotalData(TDay tday) {
        byte[] cmd = new byte[5];
        switch (tday) {
            case Today:
                cmd[0] = -119;
                cmd[1] = 0;
                cmd[2] = -127;
                cmd[3] = 1;
                cmd[4] = 0;
                break;
            case T_1:
                cmd[0] = -119;
                cmd[1] = 16;
                cmd[2] = -127;
                cmd[3] = 1;
                cmd[4] = 0;
                break;
            case T_2:
                cmd[0] = -119;
                cmd[1] = 32;
                cmd[2] = -127;
                cmd[3] = 1;
                cmd[4] = 0;
                break;
            case T_3:
                cmd[0] = -119;
                cmd[1] = 48;
                cmd[2] = -127;
                cmd[3] = 1;
                cmd[4] = 0;
                break;
            case T_4:
                cmd[0] = -119;
                cmd[1] = 64;
                cmd[2] = -127;
                cmd[3] = 1;
                cmd[4] = 0;
                break;
            case T_5:
                cmd[0] = -119;
                cmd[1] = 80;
                cmd[2] = -127;
                cmd[3] = 1;
                cmd[4] = 0;
                break;
            case T_6:
                cmd[0] = -119;
                cmd[1] = 96;
                cmd[2] = -127;
                cmd[3] = 1;
                cmd[4] = 0;
                break;
            case T_7:
                cmd[0] = -119;
                cmd[1] = 112;
                cmd[2] = -127;
                cmd[3] = 1;
                cmd[4] = 0;
                break;
        }
        return cmd;
    }

    public void setStepsTarget(Context context, int target) {
        KLog.i("-----------setStepsTarget-------------------");
        List<byte[]> data = new ArrayList<>();
        DeviceSetting ds = getDeviceSetting(context);
        try {
            checkData(ds, 5, new int[]{target});
            byte[] d1 = ds.getD1();
            d1[0] = 5;
            d1[8] = (byte) (target & 255);
            d1[9] = (byte) ((target >> 8) & 255);
            data.add(d1);
            byte[] d2 = ds.getD2();
            d2[0] = 5;
            data.add(d2);
            byte[] d3 = ds.getD3();
            d3[0] = 5;
            d3[7] = 0;
            data.add(d3);
            addTasks(context, data);
            ds.setStepsOnceday(target);
            ds.setD1(d1);
            ds.setD2(d2);
            ds.setD3(d3);
            setDeviceSetting(context, JsonTool.toJson(ds));
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    private void addTasks(Context context, List<byte[]> data) {
        BleHandler.getInstance().addTaskMessage(new BleMessage(data));
    }

    public void setKcalTarget(Context context, int target) {
        List<byte[]> data = new ArrayList<>();
        DeviceSetting ds = getDeviceSetting(context);
        try {
            checkData(ds, 6, new int[]{target});
            byte[] d1 = ds.getD1();
            d1[0] = 5;
            d1[10] = (byte) (target & 255);
            d1[11] = (byte) ((target >> 8) & 255);
            data.add(d1);
            byte[] d2 = ds.getD2();
            d2[0] = 5;
            data.add(d2);
            byte[] d3 = ds.getD3();
            d3[0] = 5;
            d3[7] = 0;
            data.add(d3);
            addTasks(context, data);
            ds.setCalorieOnceday(target);
            ds.setD1(d1);
            ds.setD2(d2);
            ds.setD3(d3);
            setDeviceSetting(context, JsonTool.toJson(ds));
        } catch (Exception e) {
        }
    }

    public void setDistanceTarget(Context context, int target) {
        List<byte[]> data = new ArrayList<>();
        DeviceSetting ds = getDeviceSetting(context);
        try {
            checkData(ds, 7, new int[]{target});
            byte[] d1 = ds.getD1();
            d1[0] = 5;
            d1[12] = (byte) target;
            data.add(d1);
            byte[] d2 = ds.getD2();
            d2[0] = 5;
            data.add(d2);
            byte[] d3 = ds.getD3();
            d3[0] = 5;
            d3[7] = 0;
            data.add(d3);
            addTasks(context, data);
            ds.setDistanceOnceday(target);
            ds.setD1(d1);
            ds.setD2(d2);
            ds.setD3(d3);
            setDeviceSetting(context, JsonTool.toJson(ds));
        } catch (Exception e) {
        }
    }

    public void setCallNotificationSwitch(Context context, int type) {
        KLog.i("-----------setCallNotificationSwitch-------------------");
        List<byte[]> data = new ArrayList<>();
        DeviceSetting ds = getDeviceSetting(context);
        if (ds != null) {
            byte[] d1 = ds.getD1();
            d1[0] = 5;
            d1[16] = (byte) type;
            data.add(d1);
            byte[] d2 = ds.getD2();
            d2[0] = 5;
            data.add(d2);
            byte[] d3 = ds.getD3();
            d3[0] = 5;
            d3[7] = 0;
            data.add(d3);
            addTasks(context, data);
            ds.setComingCallEnable(type);
            ds.setD1(d1);
            ds.setD2(d2);
            ds.setD3(d3);
            setDeviceSetting(context, JsonTool.toJson(ds));
        }
    }

    public void setComingCallHours(Context context, int startHour, int endHour) {
        KLog.i("-----------setComingCallHours-------------------");
        List<byte[]> data = new ArrayList<>();
        DeviceSetting ds = getDeviceSetting(context);
        if (endHour == 0) {
            endHour = 24;
        }
        if (endHour > 0) {
            endHour--;
        }
        try {
            checkData(ds, 8, new int[]{startHour, endHour});
            byte[] d1 = ds.getD1();
            d1[0] = 5;
            d1[17] = (byte) startHour;
            d1[18] = (byte) endHour;
            data.add(d1);
            byte[] d2 = ds.getD2();
            d2[0] = 5;
            data.add(d2);
            byte[] d3 = ds.getD3();
            d3[0] = 5;
            d3[7] = 0;
            data.add(d3);
            addTasks(context, data);
            ds.setComingCallStartHour(startHour);
            ds.setComingCallEndHour(endHour);
            ds.setD1(d1);
            ds.setD2(d2);
            ds.setD3(d3);
            setDeviceSetting(context, JsonTool.toJson(ds));
        } catch (Exception e) {
        }
    }

    public void comingCallShake(Context context, int type, int count) {
        List<byte[]> data = new ArrayList<>();
        byte[] typeArray = ByteUtil.byteToBitArray(type);
        byte[] countArray = ByteUtil.byteToBitArray(count);
        int shakeModel = Integer.parseInt(ByteUtil.bytesToStringFormat(new byte[]{typeArray[5], typeArray[6], typeArray[7], countArray[3], countArray[4], countArray[5], countArray[6], countArray[7]}), 2);
        DeviceSetting ds = getDeviceSetting(context);
        if (ds != null) {
            byte[] d1 = ds.getD1();
            d1[0] = 5;
            d1[19] = (byte) shakeModel;
            data.add(d1);
            byte[] d2 = ds.getD2();
            d2[0] = 5;
            data.add(d2);
            byte[] d3 = ds.getD3();
            d3[0] = 5;
            d3[7] = 0;
            data.add(d3);
            addTasks(context, data);
            ds.setComingCallRing(shakeModel);
            ds.setD1(d1);
            ds.setD2(d2);
            ds.setD3(d3);
            setDeviceSetting(context, JsonTool.toJson(ds));
        }
    }

    public void setMsgNotificationSwitch(Context context, int type) {
        KLog.i("-----------setMsgNotificationSwitch-------------------");
        List<byte[]> data = new ArrayList<>();
        DeviceSetting ds = getDeviceSetting(context);
        if (ds != null) {
            byte[] d1 = ds.getD1();
            d1[0] = 5;
            data.add(d1);
            byte[] d2 = ds.getD2();
            d2[0] = 5;
            d2[4] = (byte) type;
            data.add(d2);
            byte[] d3 = ds.getD3();
            d3[0] = 5;
            d3[7] = 0;
            data.add(d3);
            addTasks(context, data);
            ds.setMessageEnable(type);
            ds.setD1(d1);
            ds.setD2(d2);
            ds.setD3(d3);
            setDeviceSetting(context, JsonTool.toJson(ds));
        }
    }

    public void setComingMessageHours(Context context, int startHour, int endHour) {
        KLog.i("-----------setComingMessageHours-------------------");
        List<byte[]> data = new ArrayList<>();
        DeviceSetting ds = getDeviceSetting(context);
        if (endHour == 0) {
            endHour = 24;
        }
        if (endHour > 0) {
            endHour--;
        }
        try {
            checkData(ds, 9, new int[]{startHour, endHour});
            byte[] d1 = ds.getD1();
            d1[0] = 5;
            data.add(d1);
            byte[] d2 = ds.getD2();
            d2[0] = 5;
            d2[5] = (byte) startHour;
            d2[6] = (byte) endHour;
            data.add(d2);
            byte[] d3 = ds.getD3();
            d3[0] = 5;
            d3[7] = 0;
            data.add(d3);
            addTasks(context, data);
            ds.setMessageStartHour(startHour);
            ds.setMessageEndHour(endHour);
            ds.setD1(d1);
            ds.setD2(d2);
            ds.setD3(d3);
            setDeviceSetting(context, JsonTool.toJson(ds));
        } catch (Exception e) {
        }
    }

    public void heartDetection(Context context, int heartOn, int startHour, int endHour) {
        KLog.i("-----------heartDetection-------------------");
        List<byte[]> data = new ArrayList<>();
        DeviceSetting ds = getDeviceSetting(context);
        try {
            KLog.e("heart --> " + endHour);
            if (endHour == 0) {
                endHour = 24;
            }
            if (endHour > 0) {
                endHour--;
            }
            checkData(ds, 10, new int[]{heartOn, startHour, endHour});
            byte[] d1 = ds.getD1();
            d1[0] = 5;
            data.add(d1);
            byte[] d2 = ds.getD2();
            d2[0] = 5;
            d2[8] = (byte) heartOn;
            d2[9] = (byte) startHour;
            d2[10] = (byte) endHour;
            data.add(d2);
            byte[] d3 = ds.getD3();
            d3[0] = 5;
            d3[7] = 0;
            data.add(d3);
            addTasks(context, data);
            ds.setQuietHeartEnable(heartOn);
            ds.setQuietHeartStartHour(startHour);
            ds.setQuietHeartEndHour(endHour);
            ds.setD1(d1);
            ds.setD2(d2);
            ds.setD3(d3);
            setDeviceSetting(context, JsonTool.toJson(ds));
        } catch (Exception e) {
        }
    }

    public void setHeartAlarm(Context context, int warmingOn, int heartHighAlarm, int heartLowAlarm) {
        KLog.i("-----------setHeartAlarm-------------------");
        List<byte[]> data = new ArrayList<>();
        DeviceSetting ds = getDeviceSetting(context);
        try {
            checkData(ds, 11, new int[]{warmingOn, heartHighAlarm, heartLowAlarm});
            byte[] d1 = ds.getD1();
            d1[0] = 5;
            data.add(d1);
            byte[] d2 = ds.getD2();
            d2[0] = 5;
            d2[11] = (byte) warmingOn;
            d2[12] = (byte) heartHighAlarm;
            d2[13] = (byte) heartLowAlarm;
            data.add(d2);
            byte[] d3 = ds.getD3();
            d3[0] = 5;
            d3[7] = 0;
            data.add(d3);
            addTasks(context, data);
            ds.setHeartAlarmEnable(warmingOn);
            ds.setHighHeartAlarm(heartHighAlarm);
            ds.setLowHeartAlarm(heartLowAlarm);
            ds.setD1(d1);
            ds.setD2(d2);
            ds.setD3(d3);
            setDeviceSetting(context, JsonTool.toJson(ds));
        } catch (Exception e) {
        }
    }

    public void heartWarmingShake(Context context, int type, int count) {
        List<byte[]> data = new ArrayList<>();
        byte[] typeArray = ByteUtil.byteToBitArray(type);
        byte[] countArray = ByteUtil.byteToBitArray(count);
        int shakeModel = Integer.parseInt(ByteUtil.bytesToStringFormat(new byte[]{typeArray[5], typeArray[6], typeArray[7], countArray[3], countArray[4], countArray[5], countArray[6], countArray[7]}), 2);
        DeviceSetting ds = getDeviceSetting(context);
        if (ds != null) {
            byte[] d1 = ds.getD1();
            d1[0] = 5;
            data.add(d1);
            byte[] d2 = ds.getD2();
            d2[0] = 5;
            d2[14] = (byte) shakeModel;
            data.add(d2);
            byte[] d3 = ds.getD3();
            d3[0] = 5;
            d3[7] = 0;
            data.add(d3);
            addTasks(context, data);
            ds.setHeartRing(shakeModel);
            ds.setD1(d1);
            ds.setD2(d2);
            ds.setD3(d3);
            setDeviceSetting(context, JsonTool.toJson(ds));
        }
    }

    public void setShake(Context context, int phoneType, int phoneCount, int msgType, int msgCount, int setLongType, int setLongCount, int heartType, int heartCount) {
        KLog.i("-----------setShake-------------------");
        List<byte[]> data = new ArrayList<>();
        int phoneShakeModel = shakeModel(phoneType, phoneCount);
        int messageShakeModel = shakeModel(msgType, msgCount);
        int setLongShakeModel = shakeModel(setLongType, setLongCount);
        int heartShakeModel = shakeModel(heartType, heartCount);
        DeviceSetting ds = getDeviceSetting(context);
        if (ds != null) {
            byte[] d1 = ds.getD1();
            d1[0] = 5;
            d1[19] = (byte) phoneShakeModel;
            data.add(d1);
            byte[] d2 = ds.getD2();
            d2[0] = 5;
            d2[7] = (byte) messageShakeModel;
            d2[14] = (byte) heartShakeModel;
            d2[18] = (byte) setLongShakeModel;
            data.add(d2);
            byte[] d3 = ds.getD3();
            d3[0] = 5;
            d3[7] = 0;
            data.add(d3);
            addTasks(context, data);
            ds.setHeartRing(phoneShakeModel);
            ds.setMessageRing(messageShakeModel);
            ds.setSitlongRing(setLongShakeModel);
            ds.setHeartRing(heartShakeModel);
            ds.setD1(d1);
            ds.setD2(d2);
            ds.setD3(d3);
            setDeviceSetting(context, JsonTool.toJson(ds));
        }
    }

    public int shakeModel(int type, int count) {
        byte[] typeArray = ByteUtil.byteToBitArray(type);
        byte[] countArray = ByteUtil.byteToBitArray(count);
        return Integer.parseInt(ByteUtil.bytesToStringFormat(new byte[]{typeArray[5], typeArray[6], typeArray[7], countArray[3], countArray[4], countArray[5], countArray[6], countArray[7]}), 2);
    }

    public void setLongSitAlarm(Context context, int alarm, int startHour, int endHour) {
        KLog.i("-----------setLongSitAlarm-------------------");
        List<byte[]> data = new ArrayList<>();
        DeviceSetting ds = getDeviceSetting(context);
        if (endHour == 0) {
            endHour = 24;
        }
        if (endHour > 0) {
            endHour--;
        }
        try {
            checkData(ds, 12, new int[]{alarm, startHour, endHour});
            byte[] d1 = ds.getD1();
            d1[0] = 5;
            data.add(d1);
            byte[] d2 = ds.getD2();
            d2[0] = 5;
            d2[15] = (byte) alarm;
            d2[16] = (byte) startHour;
            d2[17] = (byte) endHour;
            data.add(d2);
            byte[] d3 = ds.getD3();
            d3[0] = 5;
            d3[7] = 0;
            data.add(d3);
            addTasks(context, data);
            ds.setSitLongAlarmEnable(alarm);
            ds.setSitlongStartHour(startHour);
            ds.setSitlongEndHour(endHour);
            ds.setD1(d1);
            ds.setD2(d2);
            ds.setD3(d3);
            setDeviceSetting(context, JsonTool.toJson(ds));
        } catch (Exception e) {
        }
    }

    public void comingLongSitShake(Context context, int type, int count) {
        List<byte[]> data = new ArrayList<>();
        byte[] typeArray = ByteUtil.byteToBitArray(type);
        byte[] countArray = ByteUtil.byteToBitArray(count);
        int shakeModel = Integer.parseInt(ByteUtil.bytesToStringFormat(new byte[]{typeArray[5], typeArray[6], typeArray[7], countArray[3], countArray[4], countArray[5], countArray[6], countArray[7]}), 2);
        DeviceSetting ds = getDeviceSetting(context);
        if (ds != null) {
            byte[] d1 = ds.getD1();
            d1[0] = 5;
            data.add(d1);
            byte[] d2 = ds.getD2();
            d2[0] = 5;
            d2[18] = (byte) shakeModel;
            data.add(d2);
            byte[] d3 = ds.getD3();
            d3[0] = 5;
            d3[7] = 0;
            data.add(d3);
            addTasks(context, data);
            ds.setSitlongRing(shakeModel);
            ds.setD1(d1);
            ds.setD2(d2);
            ds.setD3(d3);
            setDeviceSetting(context, JsonTool.toJson(ds));
        }
    }

    public void setGesture(Context context, int gestureOn, int startHour, int endHour) {
        KLog.i("-----------setGesture-------------------");
        List<byte[]> data = new ArrayList<>();
        DeviceSetting ds = getDeviceSetting(context);
        if (endHour == 0) {
            endHour = 24;
        }
        if (endHour > 0) {
            endHour--;
        }
        try {
            checkData(ds, 13, new int[]{gestureOn, startHour, endHour});
            byte[] d1 = ds.getD1();
            d1[0] = 5;
            data.add(d1);
            byte[] d2 = ds.getD2();
            d2[0] = 5;
            d2[19] = (byte) gestureOn;
            data.add(d2);
            byte[] d3 = ds.getD3();
            d3[0] = 5;
            d3[4] = (byte) startHour;
            d3[5] = (byte) endHour;
            d3[7] = 0;
            data.add(d3);
            addTasks(context, data);
            ds.setRollEnable(gestureOn);
            ds.setRollStartHour(startHour);
            ds.setRollEndHour(endHour);
            ds.setD1(d1);
            ds.setD2(d2);
            ds.setD3(d3);
            setDeviceSetting(context, JsonTool.toJson(ds));
        } catch (Exception e) {
        }
    }

    public void watchSelect(Context context, int type) {
        List<byte[]> data = new ArrayList<>();
        DeviceSetting ds = getDeviceSetting(context);
        if (ds != null) {
            byte[] d1 = ds.getD1();
            d1[0] = 5;
            data.add(d1);
            byte[] d2 = ds.getD2();
            d2[0] = 5;
            data.add(d2);
            byte[] d3 = ds.getD3();
            d3[0] = 5;
            d3[6] = (byte) type;
            d3[7] = 0;
            data.add(d3);
            addTasks(context, data);
            ds.setWatchSelect(type);
            ds.setD1(d1);
            ds.setD2(d2);
            ds.setD3(d3);
            setDeviceSetting(context, JsonTool.toJson(ds));
        }
    }

    public void weatherUnit(Context context, int weatherUnit) {
        List<byte[]> data = new ArrayList<>();
        DeviceSetting ds = getDeviceSetting(context);
        if (ds != null) {
            byte unit = (byte) ds.getUnitSet();
            if (weatherUnit == 0) {
                unit = (byte) (unit & 254);
            } else if (weatherUnit == 1) {
                unit = (byte) (unit | 1);
            }
            try {
                checkData(ds, 1, new int[]{weatherUnit});
                byte[] d1 = ds.getD1();
                d1[0] = 5;
                d1[4] = unit;
                data.add(d1);
                byte[] d2 = ds.getD2();
                d2[0] = 5;
                data.add(d2);
                byte[] d3 = ds.getD3();
                d3[0] = 5;
                d3[7] = 0;
                data.add(d3);
                addTasks(context, data);
                ds.setUnitSet(unit);
                ds.setD1(d1);
                ds.setD2(d2);
                ds.setD3(d3);
                setDeviceSetting(context, JsonTool.toJson(ds));
            } catch (Exception e) {
            }
        }
    }

    public byte[] setUnbind() {
        return new byte[]{6, 0, -127, 16, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    }

    public byte[][] messageNotification(String header, String message, boolean needWait) {
        byte[][] cmd = combineNotificationCmd(NotificationType.Message, header, message);
        List<byte[]> list1 = new ArrayList<>();
        for (byte[] add : cmd) {
            list1.add(add);
        }
        BleHandler.getInstance().addTaskMessage(new BleMessage(list1));
        return cmd;
    }

    public byte[][] callNotification(String header, String callInfo, boolean needWait) {
        byte[][] cmd = combineNotificationCmd(NotificationType.Call, header, callInfo);
        List<byte[]> list1 = new ArrayList<>();
        for (byte[] add : cmd) {
            list1.add(add);
        }
        BleHandler.getInstance().addTaskMessage(new BleMessage(list1));
        return cmd;
    }

    private byte[][] combineNotificationCmd(NotificationType type, String header, String content) {
        int size;
        if ((content == null || content.equals("")) && (header == null || header.equals(""))) {
            return null;
        }
        char[] contentChars = null;
        char[] headerChars = null;
        if (header != null && !header.equals("")) {
            headerChars = new char[(header.length() + 1)];
            char[] charArray = header.toCharArray();
            for (int i = 0; i < charArray.length; i++) {
                headerChars[i] = charArray[i];
            }
            headerChars[header.length()] = 10;
        }
        if (content != null && !content.equals("")) {
            contentChars = content.toCharArray();
        }
        char[] fullChars = new char[((headerChars == null ? 0 : headerChars.length) + (contentChars == null ? 0 : contentChars.length))];
        int idx = 0;
        if (headerChars != null) {
            for (char c : headerChars) {
                fullChars[idx] = c;
                idx++;
            }
        }
        if (contentChars != null) {
            for (char c2 : contentChars) {
                fullChars[idx] = c2;
                idx++;
            }
        }
        int totalBytes = 0;
        List<Byte> bytesList = new ArrayList<>();
        for (char valueOf : fullChars) {
            byte[] cbytes = String.valueOf(valueOf).getBytes();
            totalBytes += cbytes.length;
            if (totalBytes > 240) {
                break;
            }
            for (byte valueOf2 : cbytes) {
                bytesList.add(Byte.valueOf(valueOf2));
            }
        }
        Byte[] msgArray = (Byte[]) bytesList.toArray(new Byte[bytesList.size()]);
        if (msgArray == null || msgArray.length == 0) {
            return null;
        }
        int totalPack = msgArray.length / 16;
        int lastPackRemain = msgArray.length % 16;
        if (lastPackRemain != 0) {
            totalPack++;
        }
        byte[][] cmds = new byte[totalPack][];
        for (int i2 = 0; i2 < totalPack; i2++) {
            if (i2 != totalPack - 1) {
                size = 16;
            } else if (lastPackRemain == 0) {
                size = 16;
            } else {
                size = lastPackRemain;
            }
            cmds[i2] = new byte[(size + 4)];
            cmds[i2][0] = 7;
            if (type == NotificationType.Call) {
                cmds[i2][1] = 10;
            } else {
                cmds[i2][1] = -91;
            }
            cmds[i2][2] = (byte) (i2 + 1);
            if (i2 == totalPack - 1) {
                cmds[i2][2] = (byte) (i2 + 1 + 128);
            }
            cmds[i2][3] = (byte) size;
            for (int j = 0; j < size; j++) {
                cmds[i2][j + 4] = msgArray[(i2 * 16) + j].byteValue();
            }
        }
        return cmds;
    }

    public byte[] setTimeAndWeather() {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(1);
        int year = calendar.get(1);
        return getMergeHeadLenBytes(2, 0, 129, new byte[]{(byte) (year % 256), (byte) (year / 256), (byte) (calendar.get(2) + 1), (byte) calendar.get(5), (byte) calendar.get(11), (byte) calendar.get(12), (byte) calendar.get(13), (byte) (calendar.get(7) - 1), Ascii.FF, 100});
    }

    public byte[] setTimeAndWeather(int year, int month, int day, int hour, int minute, int second, int week, @IntRange(from = 0, to = 10) int weather, int temperature) {
        return getMergeHeadLenBytes(2, 0, 129, new byte[]{(byte) (year % 256), (byte) (year / 256), (byte) month, (byte) day, (byte) hour, (byte) minute, (byte) second, (byte) week, (byte) weather, (byte) temperature});
    }

    public byte[] setTimeAndWeather(@IntRange(from = 0, to = 10) int weather, int temperature) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(1);
        int year = calendar.get(1);
        return getMergeHeadLenBytes(2, 0, 129, new byte[]{(byte) (year % 256), (byte) (year / 256), (byte) (calendar.get(2) + 1), (byte) calendar.get(5), (byte) calendar.get(11), (byte) calendar.get(12), (byte) calendar.get(13), (byte) (calendar.get(7) - 1), (byte) weather, (byte) temperature});
    }

    public byte[] getTime() {
        return getMergeHeadLenBytes(Opcodes.INT_TO_FLOAT, 0, 129, new byte[]{0});
    }

    public byte[] testShake(@IntRange(from = 1, to = 7) int mode, @IntRange(from = 0, to = 31) int times) {
        return testHardware(6, (mode << 5) | times);
    }

    public byte[] testHardware(int testIndex, int testValue) {
        byte[] bytes = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        for (int i = 0; i < bytes.length; i++) {
            if (i == testIndex) {
                if (testIndex == 0) {
                    bytes[0] = (byte) (testValue % 256);
                    bytes[1] = (byte) (testValue / 256);
                } else {
                    bytes[i] = (byte) testValue;
                }
            }
        }
        return getMergeHeadLenBytes(6, 0, 129, bytes);
    }

    public byte[] setUpgrade() {
        return getMergeHeadLenBytes(6, 0, 129, new byte[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
    }

    /* JADX WARNING: type inference failed for: r6v0, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v1, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v2, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r8v2, types: [int[]] */
    /* JADX WARNING: type inference failed for: r0v0, types: [byte[], java.lang.Object] */
    /* JADX WARNING: type inference failed for: r6v3, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v4 */
    /* JADX WARNING: type inference failed for: r6v5 */
    /* JADX WARNING: Incorrect type for immutable var: ssa=byte[], code=null, for r0v0, types: [byte[], java.lang.Object] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r6v0, names: [unit], types: [byte]
      assigns: [byte]
      uses: [?[int, boolean, short, byte, char], ?[int, float], byte, int]
      mth insns count: 40
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1507)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1507)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1507)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 5 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setUnitSwitch(android.content.Context r13, int r14) {
        /*
            r12 = this;
            r11 = 5
            r8 = 1
            r10 = 0
            java.lang.String r7 = "-----------setUnitSwitch-------------------"
            com.socks.library.KLog.i(r7)
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            com.iwown.ble_module.zg_ble.data.model.DeviceSetting r4 = r12.getDeviceSetting(r13)
            if (r4 != 0) goto L_0x0015
        L_0x0014:
            return
        L_0x0015:
            int r7 = r4.getUnitSet()
            byte r6 = (byte) r7
            if (r14 != 0) goto L_0x0061
            r7 = r6 & 253(0xfd, float:3.55E-43)
            byte r6 = (byte) r7
        L_0x001f:
            r7 = 1
            r8 = 1
            int[] r8 = new int[r8]     // Catch:{ Exception -> 0x0067 }
            r9 = 0
            r8[r9] = r6     // Catch:{ Exception -> 0x0067 }
            r12.checkData(r4, r7, r8)     // Catch:{ Exception -> 0x0067 }
            byte[] r0 = r4.getD1()
            r0[r10] = r11
            r7 = 4
            r0[r7] = r6
            r3.add(r0)
            byte[] r1 = r4.getD2()
            r1[r10] = r11
            r3.add(r1)
            byte[] r2 = r4.getD3()
            r2[r10] = r11
            r7 = 7
            r2[r7] = r10
            r3.add(r2)
            r12.addTasks(r13, r3)
            r4.setUnitSet(r6)
            r4.setD1(r0)
            r4.setD2(r1)
            r4.setD3(r2)
            java.lang.String r7 = com.iwown.ble_module.utils.JsonTool.toJson(r4)
            r12.setDeviceSetting(r13, r7)
            goto L_0x0014
        L_0x0061:
            if (r14 != r8) goto L_0x001f
            r7 = r6 | 2
            byte r6 = (byte) r7
            goto L_0x001f
        L_0x0067:
            r5 = move-exception
            goto L_0x0014
        */
        throw new UnsupportedOperationException("Method not decompiled: com.iwown.ble_module.zg_ble.data.BleDataOrderHandler.setUnitSwitch(android.content.Context, int):void");
    }

    /* JADX WARNING: type inference failed for: r6v0, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v1, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v2, types: [int, byte] */
    /* JADX WARNING: type inference failed for: r8v2, types: [int[]] */
    /* JADX WARNING: type inference failed for: r0v0, types: [byte[], java.lang.Object] */
    /* JADX WARNING: type inference failed for: r6v3, types: [byte] */
    /* JADX WARNING: type inference failed for: r6v4 */
    /* JADX WARNING: type inference failed for: r6v5 */
    /* JADX WARNING: Incorrect type for immutable var: ssa=byte[], code=null, for r0v0, types: [byte[], java.lang.Object] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r6v0, names: [unit], types: [byte]
      assigns: [byte]
      uses: [?[int, boolean, short, byte, char], ?[int, float], byte, int]
      mth insns count: 40
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1507)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1507)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1507)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 5 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setTemperatureUnitSwitch(android.content.Context r13, int r14) {
        /*
            r12 = this;
            r11 = 5
            r8 = 1
            r10 = 0
            java.lang.String r7 = "-----------setTemperatureUnitSwitch-------------------"
            com.socks.library.KLog.i(r7)
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            com.iwown.ble_module.zg_ble.data.model.DeviceSetting r4 = r12.getDeviceSetting(r13)
            if (r4 != 0) goto L_0x0015
        L_0x0014:
            return
        L_0x0015:
            int r7 = r4.getUnitSet()
            byte r6 = (byte) r7
            if (r14 != 0) goto L_0x0061
            r7 = r6 & 251(0xfb, float:3.52E-43)
            byte r6 = (byte) r7
        L_0x001f:
            r7 = 1
            r8 = 1
            int[] r8 = new int[r8]     // Catch:{ Exception -> 0x0067 }
            r9 = 0
            r8[r9] = r6     // Catch:{ Exception -> 0x0067 }
            r12.checkData(r4, r7, r8)     // Catch:{ Exception -> 0x0067 }
            byte[] r0 = r4.getD1()
            r0[r10] = r11
            r7 = 4
            r0[r7] = r6
            r3.add(r0)
            byte[] r1 = r4.getD2()
            r1[r10] = r11
            r3.add(r1)
            byte[] r2 = r4.getD3()
            r2[r10] = r11
            r7 = 7
            r2[r7] = r10
            r3.add(r2)
            r12.addTasks(r13, r3)
            r4.setUnitSet(r6)
            r4.setD1(r0)
            r4.setD2(r1)
            r4.setD3(r2)
            java.lang.String r7 = com.iwown.ble_module.utils.JsonTool.toJson(r4)
            r12.setDeviceSetting(r13, r7)
            goto L_0x0014
        L_0x0061:
            if (r14 != r8) goto L_0x001f
            r7 = r6 | 4
            byte r6 = (byte) r7
            goto L_0x001f
        L_0x0067:
            r5 = move-exception
            goto L_0x0014
        */
        throw new UnsupportedOperationException("Method not decompiled: com.iwown.ble_module.zg_ble.data.BleDataOrderHandler.setTemperatureUnitSwitch(android.content.Context, int):void");
    }

    public void closeCallPhone(Context context) {
        BleHandler.getInstance().addTaskMessage(new BleMessage(new byte[]{7, Ascii.VT, -127, 1, 0}));
    }

    private int getTimeZone() {
        return Math.round(((float) Calendar.getInstance().getTimeZone().getRawOffset()) / 3600000.0f);
    }

    public void writeOldWelcomePageText(String text, int timeZone, int height, int sex) {
        byte[] byte2;
        byte[] utf8_text = new byte[0];
        try {
            utf8_text = text.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            ThrowableExtension.printStackTrace(e);
        }
        byte[] name_len = {(byte) utf8_text.length};
        byte[] utc_offset_and_reverse = {(byte) getTimeZone()};
        if (name_len.length >= 10) {
            byte[] bArr = {10};
            byte2 = Util.concat(Util.concat(bArr, Arrays.copyOfRange(utf8_text, 0, 10)), utc_offset_and_reverse);
        } else {
            byte2 = Util.concat(Util.concat(name_len, Util.concat(utf8_text, new byte[(10 - utf8_text.length)])), utc_offset_and_reverse);
        }
        byte[] byte22 = Util.concat(byte2, new byte[]{(byte) height, (byte) sex});
        byte[] byte3 = null;
        if (byte22.length < 16) {
            byte3 = new byte[(16 - byte22.length)];
        }
        byte[] send1 = getMergeHeadLenBytes(13, 0, 129, Util.concat(byte22, byte3));
        BleHandler instance = BleHandler.getInstance();
        BleMessage bleMessage = new BleMessage(send1);
        instance.addTaskMessage(bleMessage);
    }

    public void writeWelcomePageText(String text, int timeZone, int height, int sex, int[] blood) {
        byte[] byte2;
        byte[] oneSend;
        byte[] utf8_text = new byte[0];
        try {
            utf8_text = text.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            ThrowableExtension.printStackTrace(e);
        }
        byte[] name_len = {(byte) utf8_text.length};
        byte[] utc_offset_and_reverse = {(byte) getTimeZone()};
        if (name_len.length >= 10) {
            byte[] bArr = {10};
            byte2 = Util.concat(Util.concat(bArr, Arrays.copyOfRange(utf8_text, 0, 10)), utc_offset_and_reverse);
        } else {
            byte2 = Util.concat(Util.concat(name_len, Util.concat(utf8_text, new byte[(10 - utf8_text.length)])), utc_offset_and_reverse);
        }
        byte[] byte22 = Util.concat(byte2, new byte[]{(byte) height, (byte) sex});
        byte[] twoSend = new byte[8];
        if (blood == null || blood.length < 8) {
            L.file("发送的0D指令血压长度过短,默认发0", 1);
            KLog.d("no2855 发送的0D指令血压长度过短,默认发0");
            oneSend = Util.concat(byte22, new byte[]{0, 0});
        } else {
            oneSend = Util.concat(byte22, new byte[]{(byte) blood[0], (byte) blood[1]});
            for (int i = 2; i < 8; i++) {
                twoSend[i - 2] = (byte) blood[i];
            }
        }
        byte[] send1 = getMergeHeadLenBytes(13, 0, 1, oneSend);
        byte[] send2 = getMergeHeadLenBytes(13, 0, Opcodes.INT_TO_FLOAT, twoSend);
        LinkedList linkedList = new LinkedList();
        linkedList.add(send1);
        linkedList.add(send2);
        BleHandler instance = BleHandler.getInstance();
        BleMessage bleMessage = new BleMessage((List<byte[]>) linkedList);
        instance.addTaskMessage(bleMessage);
    }

    public byte[] getBP() {
        return getMergeHeadLenBytes(Opcodes.DOUBLE_TO_LONG, 5, 129, new byte[]{0});
    }

    public void readWelcomeBlood() {
        BleHandler.getInstance().addTaskMessage(new BleMessage(getMergeHeadLenBytes(Opcodes.INT_TO_BYTE, 0, 129, new byte[]{0})));
    }

    public void getTotalGps() {
        byte[] bytes = getMergeHeadLenBytes(Opcodes.DOUBLE_TO_FLOAT, 0, 129, new byte[]{0});
        KLog.e("no2855-->发送8c获取总数据 " + ByteUtil.bytesToHex(bytes));
        BleHandler.getInstance().addTaskMessage(new BleMessage(bytes));
    }

    public void getOneDayGps(int day) {
        if (day < 1 || day > 7) {
            day = 1;
        }
        BleHandler.getInstance().addTaskMessage(new BleMessage(getMergeHeadLenBytes(Opcodes.DOUBLE_TO_FLOAT, (byte) day, 129, new byte[]{0})));
    }

    public void startAgps() {
        BleHandler.getInstance().addTaskMessage(new AgpsBleMessage(new byte[]{Ascii.SO, 0, -127, 1, 1}));
    }

    public byte[] writeAgps(int sum2048, int count2048, int count256, int count16, byte[] data) {
        int sum256 = sum2048 % 16 == 0 ? sum2048 / 256 : (sum2048 / 256) + 1;
        KLog.d("no2855-->sum2048大小比较:  == " + sum256 + " == " + count2048 + " == " + count256);
        byte data1 = (byte) count256;
        byte data2 = (byte) count16;
        if (count16 >= 16) {
            data2 = -112;
        }
        if (data1 == sum256) {
            data1 = (byte) (data1 | 128);
        }
        return getMergeHeadLenBytes(14, data1, data2, data);
    }

    public void checkAgpsWrite() {
        BleHandler.getInstance().addTaskMessage(new AgpsBleMessage(new byte[]{-114, 0, -127, 1, 90}));
    }

    public void endAgps() {
        BleHandler.getInstance().addTaskMessage(new AgpsBleMessage(new byte[]{Ascii.SO, 0, -127, 1, 0}));
    }

    public void checkAgpsIsUp() {
        BleHandler.getInstance().addTaskMessage(new AgpsBleMessage(new byte[]{-114, 90, -127, 1, 0}));
    }

    public void checkGpsStatue() {
        KLog.e("no2855-->写入gps的开关状态 0x8C");
        BleHandler.getInstance().addTaskMessage(new AgpsBleMessage(new byte[]{-116, 90, -127, 1, 0}));
    }

    public void checkAgpsStatue() {
        BleHandler.getInstance().addTaskMessage(new AgpsBleMessage(new byte[]{-114, 2, -127, 1, 0}));
    }

    public void writeAgpsLength(int length) {
        BleHandler.getInstance().addTaskMessage(new AgpsBleMessage(new byte[]{Ascii.SO, 90, -127, 1, (byte) length}));
    }

    public byte[] writeC100Agps(Context context, int num168, int packageNum, int packageAllNum, byte[] data) {
        byte data1 = (byte) packageNum;
        byte data2 = (byte) num168;
        if (data1 == packageAllNum) {
        }
        if (num168 >= 11) {
            data2 = (byte) (data2 | 128);
        }
        byte[] bytes = getMergeHeadLenBytes(15, data1, data2, data);
        KLog.e("C100", "bytes:   " + Util.bytesToString(bytes));
        return bytes;
    }

    public void writeOnlineAgpsLength(Context context, int length) {
        BleHandler.getInstance().addTaskMessage(new AgpsBleMessage(new byte[]{Ascii.SI, 85, -127, 1, (byte) length}));
    }

    public void writeOfflineAgpsLength(Context context, int length) {
        BleHandler.getInstance().addTaskMessage(new AgpsBleMessage(new byte[]{Ascii.SI, 90, -127, 1, (byte) length}));
    }

    public void checkC100AgpsIsUp() {
        BleHandler.getInstance().addTaskMessage(new AgpsBleMessage(new byte[]{-113, 90, -127, 1, 0}));
    }
}
