package com.iwown.ble_module.zg_ble.data;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;
import com.iwown.ble_module.iwown.bean.WristBand;
import com.iwown.ble_module.zg_ble.bluetooth.ZGService;
import com.socks.library.KLog;

public class ZGBaseBleReceiver extends BroadcastReceiver {
    private static ZGBaseBleReceiver instance;
    protected Context context;
    private IDataReceiveHandler mIDataReceiveHandler;

    private ZGBaseBleReceiver(IDataReceiveHandler iDataReceiveHandler) {
        LocalBroadcastManager.getInstance(this.context).registerReceiver(this, ZGService.getIntentFilter());
        this.mIDataReceiveHandler = iDataReceiveHandler;
    }

    public static ZGBaseBleReceiver getInstance(IDataReceiveHandler iDataReceiveHandler) {
        if (instance == null) {
            instance = new ZGBaseBleReceiver(iDataReceiveHandler);
        }
        return instance;
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onReceive(android.content.Context r34, android.content.Intent r35) {
        /*
            r33 = this;
            r0 = r34
            r1 = r33
            r1.context = r0
            java.lang.String r4 = r35.getAction()
            java.lang.String r31 = "com.zeroner.sdk.zg.ble.gatt_connected"
            r0 = r31
            boolean r31 = r0.equals(r4)
            if (r31 == 0) goto L_0x005d
            r31 = 1
            r0 = r33
            r1 = r31
            r0.connectStatue(r1)
        L_0x001e:
            java.lang.String r31 = "com.zeroner.sdk.zg.ble.characteristic_changed"
            r0 = r31
            boolean r31 = r0.equals(r4)
            if (r31 == 0) goto L_0x071c
            java.lang.String r31 = "VALUE"
            r0 = r35
            r1 = r31
            byte[] r12 = r0.getByteArrayExtra(r1)
            if (r12 == 0) goto L_0x0194
            int r0 = r12.length
            r31 = r0
            r32 = 3
            r0 = r31
            r1 = r32
            if (r0 > r1) goto L_0x0194
            java.lang.StringBuilder r31 = new java.lang.StringBuilder
            r31.<init>()
            java.lang.String r32 = " data error  length is lower "
            java.lang.StringBuilder r31 = r31.append(r32)
            java.lang.String r32 = com.iwown.ble_module.zg_ble.utils.ByteUtil.bytesToString1(r12)
            java.lang.StringBuilder r31 = r31.append(r32)
            java.lang.String r31 = r31.toString()
            com.socks.library.KLog.e(r31)
        L_0x005c:
            return
        L_0x005d:
            java.lang.String r31 = "com.zeroner.sdk.zg.ble.gatt_disconnected"
            r0 = r31
            boolean r31 = r0.equals(r4)
            if (r31 == 0) goto L_0x0072
            r31 = 0
            r0 = r33
            r1 = r31
            r0.connectStatue(r1)
            goto L_0x001e
        L_0x0072:
            java.lang.String r31 = "com.zeroner.sdk.zg.ble.characteristic_notification"
            r0 = r31
            boolean r31 = r0.equals(r4)
            if (r31 == 0) goto L_0x008f
            java.lang.String r31 = "CONNECTED"
            r32 = 0
            r0 = r35
            r1 = r31
            r2 = r32
            boolean r11 = r0.getBooleanExtra(r1, r2)
            if (r11 == 0) goto L_0x001e
            goto L_0x001e
        L_0x008f:
            java.lang.String r31 = "com.zeroner.sdk.zg.ble.device_found"
            r0 = r31
            boolean r31 = r0.equals(r4)
            if (r31 == 0) goto L_0x00f6
            android.os.Bundle r18 = r35.getExtras()
            java.lang.String r31 = "DEVICE"
            r0 = r18
            r1 = r31
            android.os.Parcelable r15 = r0.getParcelable(r1)
            android.bluetooth.BluetoothDevice r15 = (android.bluetooth.BluetoothDevice) r15
            java.lang.String r31 = "SCAN_RECORD"
            r0 = r18
            r1 = r31
            byte[] r27 = r0.getByteArray(r1)
            java.lang.String r31 = "RSSI"
            r0 = r18
            r1 = r31
            int r26 = r0.getInt(r1)
            if (r15 == 0) goto L_0x001e
            java.lang.String r22 = r15.getName()
            boolean r31 = android.text.TextUtils.isEmpty(r22)
            if (r31 == 0) goto L_0x00d3
            if (r27 == 0) goto L_0x00ed
        L_0x00cf:
            java.lang.String r22 = com.iwown.ble_module.utils.Util.isDevNameNULl(r27)
        L_0x00d3:
            com.iwown.ble_module.iwown.bean.WristBand r31 = new com.iwown.ble_module.iwown.bean.WristBand
            java.lang.String r32 = r15.getAddress()
            r0 = r31
            r1 = r22
            r2 = r32
            r3 = r26
            r0.<init>(r1, r2, r3)
            r0 = r33
            r1 = r31
            r0.onScanResult(r1)
            goto L_0x001e
        L_0x00ed:
            r31 = 0
            r0 = r31
            byte[] r0 = new byte[r0]
            r27 = r0
            goto L_0x00cf
        L_0x00f6:
            java.lang.String r31 = "com.zeroner.sdk.zg.ble.BLE_NO_CALLBACK"
            r0 = r31
            boolean r31 = r0.equals(r4)
            if (r31 == 0) goto L_0x010c
            r31 = 0
            r0 = r33
            r1 = r31
            r0.connectStatue(r1)
            goto L_0x001e
        L_0x010c:
            java.lang.String r31 = "com.zeroner.sdk.zg.ble.service_discovered"
            r0 = r31
            boolean r31 = r0.equals(r4)
            if (r31 == 0) goto L_0x012b
            java.lang.String r31 = "SERVICE_UUID"
            r0 = r35
            r1 = r31
            java.lang.String r30 = r0.getStringExtra(r1)
            r0 = r33
            r1 = r30
            r0.onDiscoverService(r1)
            goto L_0x001e
        L_0x012b:
            java.lang.String r31 = "com.zeroner.sdk.zg.ble.characteristic_notification"
            r0 = r31
            boolean r31 = r0.equals(r4)
            if (r31 == 0) goto L_0x014a
            java.lang.String r31 = "CHARACTER_UUID"
            r0 = r35
            r1 = r31
            java.lang.String r30 = r0.getStringExtra(r1)
            r0 = r33
            r1 = r30
            r0.onDiscoverCharacter(r1)
            goto L_0x001e
        L_0x014a:
            java.lang.String r31 = "com.zeroner.sdk.zg.ble.characteristic_write"
            r0 = r31
            boolean r31 = r0.equals(r4)
            if (r31 == 0) goto L_0x0167
            java.lang.String r31 = "DATA"
            r0 = r35
            r1 = r31
            byte[] r12 = r0.getByteArrayExtra(r1)
            r0 = r33
            r0.onCommonSend(r12)
            goto L_0x001e
        L_0x0167:
            java.lang.String r31 = "com.zeroner.sdk.zg.ble.BLE_CONNECT_ERROR_257"
            r0 = r31
            boolean r31 = r0.equals(r4)
            if (r31 == 0) goto L_0x0177
            r33.onBluetoothError()
            goto L_0x001e
        L_0x0177:
            java.lang.String r31 = "com.zeroner.sdk.zg.ble.characteristic_changed"
            r0 = r31
            boolean r31 = r0.equals(r4)
            if (r31 == 0) goto L_0x001e
            java.lang.String r31 = "ADDRESS"
            r0 = r35
            r1 = r31
            java.lang.String r5 = r0.getStringExtra(r1)
            r0 = r33
            r0.onCharacteristicChange(r5)
            goto L_0x001e
        L_0x0194:
            java.lang.String r24 = ""
            r31 = 0
            byte r14 = r12[r31]
            r23 = r14
            r31 = -120(0xffffffffffffff88, float:NaN)
            r0 = r31
            if (r14 != r0) goto L_0x0207
            java.lang.String r24 = com.iwown.ble_module.zg_ble.data.BleReceiverParseHandler.parse88(r12)
        L_0x01a7:
            r31 = 14
            r0 = r31
            if (r14 == r0) goto L_0x01cd
            r31 = 15
            r0 = r31
            if (r14 == r0) goto L_0x01cd
            r0 = r14 & 128(0x80, float:1.794E-43)
            r31 = r0
            if (r31 != 0) goto L_0x01cd
            java.lang.String r31 = "no2855--> dataType & 0x80"
            com.socks.library.KLog.d(r31)
            com.iwown.ble_module.zg_ble.data.model.Result r25 = new com.iwown.ble_module.zg_ble.data.model.Result
            r25.<init>()
            r0 = r25
            r0.initDataInfo(r12)
            java.lang.String r24 = r25.toString()
        L_0x01cd:
            switch(r14) {
                case -123: goto L_0x06be;
                default: goto L_0x01d0;
            }
        L_0x01d0:
            boolean r31 = android.text.TextUtils.isEmpty(r24)
            if (r31 != 0) goto L_0x005c
            r31 = 3
            r0 = r33
            r1 = r31
            r2 = r23
            r3 = r24
            r0.onDataArrived(r1, r2, r3)
            java.lang.StringBuilder r31 = new java.lang.StringBuilder
            r31.<init>()
            java.lang.String r32 = "json data------------"
            java.lang.StringBuilder r31 = r31.append(r32)
            r0 = r31
            r1 = r24
            java.lang.StringBuilder r31 = r0.append(r1)
            java.lang.String r32 = "------------"
            java.lang.StringBuilder r31 = r31.append(r32)
            java.lang.String r31 = r31.toString()
            com.socks.library.KLog.d(r31)
            goto L_0x005c
        L_0x0207:
            r31 = -119(0xffffffffffffff89, float:NaN)
            r0 = r31
            if (r14 != r0) goto L_0x02ce
            r31 = 1
            byte r31 = r12[r31]
            r29 = r31 & 15
            r31 = 2
            byte r31 = r12[r31]
            r32 = -127(0xffffffffffffff81, float:NaN)
            r0 = r31
            r1 = r32
            if (r0 != r1) goto L_0x0233
            r31 = 3
            byte r31 = r12[r31]
            r32 = 16
            r0 = r31
            r1 = r32
            if (r0 != r1) goto L_0x0233
            java.lang.String r24 = com.iwown.ble_module.zg_ble.data.BleReceiverParseHandler.parse89_81(r12)
            r23 = 89
            goto L_0x01a7
        L_0x0233:
            r31 = 1
            r0 = r29
            r1 = r31
            if (r0 != r1) goto L_0x0256
            r31 = 2
            byte r31 = r12[r31]
            r32 = -37
            r0 = r31
            r1 = r32
            if (r0 != r1) goto L_0x0252
            com.iwown.ble_module.zg_ble.data.model.impl.ZgDetailWalkParse.addList(r12)
            java.lang.String r24 = com.iwown.ble_module.zg_ble.data.model.impl.ZgDetailWalkParse.parse()
        L_0x024e:
            r23 = 90
            goto L_0x01a7
        L_0x0252:
            com.iwown.ble_module.zg_ble.data.model.impl.ZgDetailWalkParse.addList(r12)
            goto L_0x024e
        L_0x0256:
            r31 = 3
            r0 = r29
            r1 = r31
            if (r0 != r1) goto L_0x0289
            r31 = 2
            byte r31 = r12[r31]
            r32 = -112(0xffffffffffffff90, float:NaN)
            r0 = r31
            r1 = r32
            if (r0 != r1) goto L_0x027f
            com.iwown.ble_module.zg_ble.data.model.impl.ZgSleepParse r31 = com.iwown.ble_module.zg_ble.data.model.impl.ZgSleepParse.getInstance()
            r0 = r31
            r0.addList(r12)
            com.iwown.ble_module.zg_ble.data.model.impl.ZgSleepParse r31 = com.iwown.ble_module.zg_ble.data.model.impl.ZgSleepParse.getInstance()
            java.lang.String r24 = r31.parse()
        L_0x027b:
            r23 = 92
            goto L_0x01a7
        L_0x027f:
            com.iwown.ble_module.zg_ble.data.model.impl.ZgSleepParse r31 = com.iwown.ble_module.zg_ble.data.model.impl.ZgSleepParse.getInstance()
            r0 = r31
            r0.addList(r12)
            goto L_0x027b
        L_0x0289:
            r31 = 4
            r0 = r29
            r1 = r31
            if (r0 != r1) goto L_0x02be
            com.iwown.ble_module.zg_ble.data.model.impl.ZgDetailSportParse r31 = com.iwown.ble_module.zg_ble.data.model.impl.ZgDetailSportParse.getInstance()
            r32 = 2
            byte r32 = r12[r32]
            boolean r31 = r31.isOver(r32)
            if (r31 == 0) goto L_0x02b4
            com.iwown.ble_module.zg_ble.data.model.impl.ZgDetailSportParse r31 = com.iwown.ble_module.zg_ble.data.model.impl.ZgDetailSportParse.getInstance()
            r0 = r31
            r0.addList(r12)
            com.iwown.ble_module.zg_ble.data.model.impl.ZgDetailSportParse r31 = com.iwown.ble_module.zg_ble.data.model.impl.ZgDetailSportParse.getInstance()
            java.lang.String r24 = r31.parse()
        L_0x02b0:
            r23 = 93
            goto L_0x01a7
        L_0x02b4:
            com.iwown.ble_module.zg_ble.data.model.impl.ZgDetailSportParse r31 = com.iwown.ble_module.zg_ble.data.model.impl.ZgDetailSportParse.getInstance()
            r0 = r31
            r0.addList(r12)
            goto L_0x02b0
        L_0x02be:
            r31 = 2
            r0 = r29
            r1 = r31
            if (r0 != r1) goto L_0x01a7
            java.lang.String r24 = com.iwown.ble_module.zg_ble.data.model.impl.ZGHeartHandler.parseBaseHeart(r12)
            r23 = 91
            goto L_0x01a7
        L_0x02ce:
            r31 = -124(0xffffffffffffff84, float:NaN)
            r0 = r31
            if (r14 != r0) goto L_0x0300
            r31 = 2
            byte r31 = r12[r31]
            r32 = -127(0xffffffffffffff81, float:NaN)
            r0 = r31
            r1 = r32
            if (r0 != r1) goto L_0x02f4
            r31 = 3
            byte r31 = r12[r31]
            r32 = 2
            r0 = r31
            r1 = r32
            if (r0 != r1) goto L_0x02f4
            java.lang.String r31 = "alarm clock  return 0x02  incorrect order "
            com.socks.library.KLog.e(r31)
            goto L_0x01a7
        L_0x02f4:
            r31 = 1
            byte r31 = r12[r31]
            if (r31 != 0) goto L_0x01a7
            java.lang.String r24 = com.iwown.ble_module.zg_ble.data.alarm_clock.ZGAlarmClockScheduleHandler.parseAlarmClock(r12)
            goto L_0x01a7
        L_0x0300:
            r31 = -126(0xffffffffffffff82, float:NaN)
            r0 = r31
            if (r14 != r0) goto L_0x0321
            com.iwown.ble_module.zg_ble.data.model.ZG_TimeAndWeather r28 = new com.iwown.ble_module.zg_ble.data.model.ZG_TimeAndWeather
            r28.<init>()
            r0 = r28
            r0.initDataInfo(r12)
            int r31 = r28.getResult_code()
            if (r31 != 0) goto L_0x031b
            r0 = r28
            r0.parseData(r12)
        L_0x031b:
            java.lang.String r24 = r28.toString()
            goto L_0x01a7
        L_0x0321:
            r31 = -122(0xffffffffffffff86, float:NaN)
            r0 = r31
            if (r14 != r0) goto L_0x0342
            com.iwown.ble_module.zg_ble.data.model.ZGHardwareInfo r20 = new com.iwown.ble_module.zg_ble.data.model.ZGHardwareInfo
            r20.<init>()
            r0 = r20
            r0.initDataInfo(r12)
            int r31 = r20.getResult_code()
            if (r31 != 0) goto L_0x033c
            r0 = r20
            r0.parseData(r12)
        L_0x033c:
            java.lang.String r24 = com.iwown.ble_module.utils.JsonTool.toJson(r20)
            goto L_0x01a7
        L_0x0342:
            r31 = -121(0xffffffffffffff87, float:NaN)
            r0 = r31
            if (r14 != r0) goto L_0x0373
            com.iwown.ble_module.zg_ble.data.model.Result r25 = new com.iwown.ble_module.zg_ble.data.model.Result
            r25.<init>()
            r0 = r25
            r0.initDataInfo(r12)
            java.lang.String r24 = com.iwown.ble_module.utils.JsonTool.toJson(r25)
            java.lang.StringBuilder r31 = new java.lang.StringBuilder
            r31.<init>()
            java.lang.String r32 = "87 "
            java.lang.StringBuilder r31 = r31.append(r32)
            r0 = r31
            r1 = r24
            java.lang.StringBuilder r31 = r0.append(r1)
            java.lang.String r31 = r31.toString()
            com.socks.library.KLog.e(r31)
            goto L_0x01a7
        L_0x0373:
            r31 = -115(0xffffffffffffff8d, float:NaN)
            r0 = r31
            if (r14 != r0) goto L_0x03b6
            r31 = 2
            byte r31 = r12[r31]
            r32 = 1
            r0 = r31
            r1 = r32
            if (r0 != r1) goto L_0x03ab
            com.iwown.ble_module.zg_ble.data.model.impl.WelcomeBloodParse r31 = com.iwown.ble_module.zg_ble.data.model.impl.WelcomeBloodParse.getInstance()
            r0 = r31
            r0.parseWelcome(r12)
        L_0x038e:
            java.lang.StringBuilder r31 = new java.lang.StringBuilder
            r31.<init>()
            java.lang.String r32 = "8D "
            java.lang.StringBuilder r31 = r31.append(r32)
            r0 = r31
            r1 = r24
            java.lang.StringBuilder r31 = r0.append(r1)
            java.lang.String r31 = r31.toString()
            com.socks.library.KLog.e(r31)
            goto L_0x01a7
        L_0x03ab:
            com.iwown.ble_module.zg_ble.data.model.impl.WelcomeBloodParse r31 = com.iwown.ble_module.zg_ble.data.model.impl.WelcomeBloodParse.getInstance()
            r0 = r31
            java.lang.String r24 = r0.parseBlood(r12)
            goto L_0x038e
        L_0x03b6:
            r31 = -125(0xffffffffffffff83, float:NaN)
            r0 = r31
            if (r14 != r0) goto L_0x03d3
            com.iwown.ble_module.zg_ble.data.model.BleSpeed r8 = new com.iwown.ble_module.zg_ble.data.model.BleSpeed
            r8.<init>()
            r8.initDataInfo(r12)
            int r31 = r8.getResult_code()
            if (r31 != 0) goto L_0x03cd
            r8.parseData(r12)
        L_0x03cd:
            java.lang.String r24 = r8.toString()
            goto L_0x01a7
        L_0x03d3:
            r31 = 1
            r0 = r31
            if (r14 != r0) goto L_0x03f7
            r31 = 4
            byte r31 = r12[r31]
            if (r31 != 0) goto L_0x01a7
            com.iwown.ble_module.zg_ble.data.BleDataOrderHandler r31 = com.iwown.ble_module.zg_ble.data.BleDataOrderHandler.getInstance()
            byte[] r9 = r31.queryConnectMode()
            com.iwown.ble_module.zg_ble.BleHandler r31 = com.iwown.ble_module.zg_ble.BleHandler.getInstance()
            com.iwown.ble_module.zg_ble.task.BleMessage r32 = new com.iwown.ble_module.zg_ble.task.BleMessage
            r0 = r32
            r0.<init>(r9)
            r31.addTaskMessage(r32)
            goto L_0x01a7
        L_0x03f7:
            r31 = -127(0xffffffffffffff81, float:NaN)
            r0 = r31
            if (r14 != r0) goto L_0x0453
            com.iwown.ble_module.zg_ble.data.model.ZGConnectionMode r10 = com.iwown.ble_module.zg_ble.data.model.ZGConnectionMode.NotSupport
            r21 = 0
            r31 = 5
            byte r31 = r12[r31]     // Catch:{ Exception -> 0x044e }
            switch(r31) {
                case 1: goto L_0x0428;
                case 2: goto L_0x042b;
                case 3: goto L_0x042e;
                default: goto L_0x0408;
            }     // Catch:{ Exception -> 0x044e }
        L_0x0408:
            com.iwown.ble_module.zg_ble.data.model.ZGConnectionMode r10 = com.iwown.ble_module.zg_ble.data.model.ZGConnectionMode.NotSupport     // Catch:{ Exception -> 0x044e }
        L_0x040a:
            if (r21 != 0) goto L_0x0422
            com.iwown.ble_module.zg_ble.data.BleDataOrderHandler r31 = com.iwown.ble_module.zg_ble.data.BleDataOrderHandler.getInstance()
            byte[] r13 = r31.setConnectMode()
            com.iwown.ble_module.zg_ble.BleHandler r31 = com.iwown.ble_module.zg_ble.BleHandler.getInstance()
            com.iwown.ble_module.zg_ble.task.BleMessage r32 = new com.iwown.ble_module.zg_ble.task.BleMessage
            r0 = r32
            r0.<init>(r13)
            r31.addTaskMessage(r32)
        L_0x0422:
            java.lang.String r24 = com.iwown.ble_module.utils.JsonTool.toJson(r10)
            goto L_0x01a7
        L_0x0428:
            com.iwown.ble_module.zg_ble.data.model.ZGConnectionMode r10 = com.iwown.ble_module.zg_ble.data.model.ZGConnectionMode.Direct     // Catch:{ Exception -> 0x044e }
            goto L_0x040a
        L_0x042b:
            com.iwown.ble_module.zg_ble.data.model.ZGConnectionMode r10 = com.iwown.ble_module.zg_ble.data.model.ZGConnectionMode.iOS     // Catch:{ Exception -> 0x044e }
            goto L_0x040a
        L_0x042e:
            com.iwown.ble_module.zg_ble.data.model.ZGConnectionMode r10 = com.iwown.ble_module.zg_ble.data.model.ZGConnectionMode.Android     // Catch:{ Exception -> 0x044e }
            com.iwown.ble_module.zg_ble.data.BleDataOrderHandler r31 = com.iwown.ble_module.zg_ble.data.BleDataOrderHandler.getInstance()     // Catch:{ Exception -> 0x044e }
            byte[] r19 = r31.getFirmwareInformation()     // Catch:{ Exception -> 0x044e }
            com.iwown.ble_module.zg_ble.BleHandler r31 = com.iwown.ble_module.zg_ble.BleHandler.getInstance()     // Catch:{ Exception -> 0x044e }
            com.iwown.ble_module.zg_ble.task.BleMessage r32 = new com.iwown.ble_module.zg_ble.task.BleMessage     // Catch:{ Exception -> 0x044e }
            r0 = r32
            r1 = r19
            r0.<init>(r1)     // Catch:{ Exception -> 0x044e }
            r31.addTaskMessage(r32)     // Catch:{ Exception -> 0x044e }
            r33.onCharacteristicWriteData()     // Catch:{ Exception -> 0x044e }
            r21 = 1
            goto L_0x040a
        L_0x044e:
            r17 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r17)
            goto L_0x040a
        L_0x0453:
            r31 = -118(0xffffffffffffff8a, float:NaN)
            r0 = r31
            if (r14 != r0) goto L_0x0469
            com.iwown.ble_module.zg_ble.data.model.Result r25 = new com.iwown.ble_module.zg_ble.data.model.Result
            r25.<init>()
            r0 = r25
            r0.initDataInfo(r12)
            java.lang.String r24 = r25.toString()
            goto L_0x01a7
        L_0x0469:
            r31 = 6
            r0 = r31
            if (r14 != r0) goto L_0x047f
            com.iwown.ble_module.zg_ble.data.model.Result r25 = new com.iwown.ble_module.zg_ble.data.model.Result
            r25.<init>()
            r0 = r25
            r0.initDataInfo(r12)
            java.lang.String r24 = r25.toString()
            goto L_0x01a7
        L_0x047f:
            r31 = -117(0xffffffffffffff8b, float:NaN)
            r0 = r31
            if (r14 != r0) goto L_0x04b1
            com.iwown.ble_module.zg_ble.data.model.impl.ZgBPDetailParse r31 = com.iwown.ble_module.zg_ble.data.model.impl.ZgBPDetailParse.getInstance()
            r32 = 2
            byte r32 = r12[r32]
            boolean r31 = r31.isDataOver(r32)
            if (r31 == 0) goto L_0x04a6
            com.iwown.ble_module.zg_ble.data.model.impl.ZgBPDetailParse r31 = com.iwown.ble_module.zg_ble.data.model.impl.ZgBPDetailParse.getInstance()
            r0 = r31
            r0.addToArrays(r12)
            com.iwown.ble_module.zg_ble.data.model.impl.ZgBPDetailParse r31 = com.iwown.ble_module.zg_ble.data.model.impl.ZgBPDetailParse.getInstance()
            java.lang.String r24 = r31.parseBPData()
            goto L_0x01a7
        L_0x04a6:
            com.iwown.ble_module.zg_ble.data.model.impl.ZgBPDetailParse r31 = com.iwown.ble_module.zg_ble.data.model.impl.ZgBPDetailParse.getInstance()
            r0 = r31
            r0.addToArrays(r12)
            goto L_0x01a7
        L_0x04b1:
            r31 = -116(0xffffffffffffff8c, float:NaN)
            r0 = r31
            if (r14 != r0) goto L_0x0550
            r31 = 1
            byte r31 = r12[r31]
            r32 = 90
            r0 = r31
            r1 = r32
            if (r0 != r1) goto L_0x04ea
            java.lang.StringBuilder r31 = new java.lang.StringBuilder
            r31.<init>()
            java.lang.String r32 = "no2855--> 收到的数据: "
            java.lang.StringBuilder r31 = r31.append(r32)
            java.lang.String r32 = com.iwown.ble_module.zg_ble.utils.ByteUtil.bytesToHex(r12)
            java.lang.StringBuilder r31 = r31.append(r32)
            java.lang.String r31 = r31.toString()
            com.socks.library.KLog.e(r31)
            com.iwown.ble_module.zg_ble.data.model.impl.ZgGpsTotalParse r31 = com.iwown.ble_module.zg_ble.data.model.impl.ZgGpsTotalParse.getInstance()
            r0 = r31
            java.lang.String r24 = r0.parseGpsStatue(r12)
            goto L_0x01a7
        L_0x04ea:
            r31 = 1
            byte r31 = r12[r31]
            if (r31 != 0) goto L_0x0515
            com.iwown.ble_module.zg_ble.data.model.impl.ZgGpsTotalParse r31 = com.iwown.ble_module.zg_ble.data.model.impl.ZgGpsTotalParse.getInstance()
            r32 = 2
            byte r32 = r12[r32]
            boolean r31 = r31.isOver(r32)
            if (r31 == 0) goto L_0x050a
            com.iwown.ble_module.zg_ble.data.model.impl.ZgGpsTotalParse r31 = com.iwown.ble_module.zg_ble.data.model.impl.ZgGpsTotalParse.getInstance()
            r0 = r31
            java.lang.String r24 = r0.parse(r12)
            goto L_0x01a7
        L_0x050a:
            com.iwown.ble_module.zg_ble.data.model.impl.ZgGpsTotalParse r31 = com.iwown.ble_module.zg_ble.data.model.impl.ZgGpsTotalParse.getInstance()
            r0 = r31
            r0.addList(r12)
            goto L_0x01a7
        L_0x0515:
            r31 = 2
            byte r31 = r12[r31]
            r32 = -127(0xffffffffffffff81, float:NaN)
            r0 = r31
            r1 = r32
            if (r0 == r1) goto L_0x0546
            com.iwown.ble_module.zg_ble.data.model.impl.ZgGpsParse r31 = com.iwown.ble_module.zg_ble.data.model.impl.ZgGpsParse.getInstance()
            r32 = 2
            byte r32 = r12[r32]
            boolean r31 = r31.isOver(r32)
            if (r31 == 0) goto L_0x053b
            com.iwown.ble_module.zg_ble.data.model.impl.ZgGpsParse r31 = com.iwown.ble_module.zg_ble.data.model.impl.ZgGpsParse.getInstance()
            r0 = r31
            java.lang.String r24 = r0.parse(r12)
            goto L_0x01a7
        L_0x053b:
            com.iwown.ble_module.zg_ble.data.model.impl.ZgGpsParse r31 = com.iwown.ble_module.zg_ble.data.model.impl.ZgGpsParse.getInstance()
            r0 = r31
            r0.addList(r12)
            goto L_0x01a7
        L_0x0546:
            com.iwown.ble_module.zg_ble.data.model.impl.ZgGpsParse r31 = com.iwown.ble_module.zg_ble.data.model.impl.ZgGpsParse.getInstance()
            java.lang.String r24 = r31.parseOver()
            goto L_0x01a7
        L_0x0550:
            r31 = 14
            r0 = r31
            if (r14 != r0) goto L_0x05cb
            java.lang.StringBuilder r31 = new java.lang.StringBuilder
            r31.<init>()
            java.lang.String r32 = "no2855--> 收到的数据: "
            java.lang.StringBuilder r31 = r31.append(r32)
            java.lang.String r32 = com.iwown.ble_module.zg_ble.utils.ByteUtil.bytesToHex(r12)
            java.lang.StringBuilder r31 = r31.append(r32)
            java.lang.String r31 = r31.toString()
            com.socks.library.KLog.e(r31)
            int r0 = r12.length
            r31 = r0
            r32 = 2
            r0 = r31
            r1 = r32
            if (r0 <= r1) goto L_0x0592
            r31 = 1
            byte r31 = r12[r31]
            r32 = 90
            r0 = r31
            r1 = r32
            if (r0 != r1) goto L_0x05af
            com.iwown.ble_module.zg_ble.data.model.impl.ZgAgpsParse r31 = com.iwown.ble_module.zg_ble.data.model.impl.ZgAgpsParse.getInstance()
            r0 = r31
            java.lang.String r24 = r0.parseAgpsPage(r12)
        L_0x0592:
            java.lang.StringBuilder r31 = new java.lang.StringBuilder
            r31.<init>()
            java.lang.String r32 = "no2855--> 转出的数据 : "
            java.lang.StringBuilder r31 = r31.append(r32)
            r0 = r31
            r1 = r24
            java.lang.StringBuilder r31 = r0.append(r1)
            java.lang.String r31 = r31.toString()
            com.socks.library.KLog.e(r31)
            goto L_0x01a7
        L_0x05af:
            r31 = 1
            byte r31 = r12[r31]
            if (r31 != 0) goto L_0x05c0
            com.iwown.ble_module.zg_ble.data.model.impl.ZgAgpsParse r31 = com.iwown.ble_module.zg_ble.data.model.impl.ZgAgpsParse.getInstance()
            r0 = r31
            java.lang.String r24 = r0.parseStart(r12)
            goto L_0x0592
        L_0x05c0:
            com.iwown.ble_module.zg_ble.data.model.impl.ZgAgpsParse r31 = com.iwown.ble_module.zg_ble.data.model.impl.ZgAgpsParse.getInstance()
            r0 = r31
            java.lang.String r24 = r0.parseWrite(r12)
            goto L_0x0592
        L_0x05cb:
            r31 = -114(0xffffffffffffff8e, float:NaN)
            r0 = r31
            if (r14 != r0) goto L_0x05dd
            com.iwown.ble_module.zg_ble.data.model.impl.ZgAgpsParse r31 = com.iwown.ble_module.zg_ble.data.model.impl.ZgAgpsParse.getInstance()
            r0 = r31
            java.lang.String r24 = r0.parseCheck(r12)
            goto L_0x01a7
        L_0x05dd:
            r31 = -113(0xffffffffffffff8f, float:NaN)
            r0 = r31
            if (r14 != r0) goto L_0x064c
            int r0 = r12.length
            r31 = r0
            r32 = 6
            r0 = r31
            r1 = r32
            if (r0 < r1) goto L_0x01a7
            r31 = 1
            byte r31 = r12[r31]
            r32 = 90
            r0 = r31
            r1 = r32
            if (r0 != r1) goto L_0x0623
            com.iwown.ble_module.zg_ble.data.model.AgpsType r7 = new com.iwown.ble_module.zg_ble.data.model.AgpsType
            r7.<init>()
            r31 = 5
            byte r31 = r12[r31]
            if (r31 != 0) goto L_0x061b
            r31 = 0
            r0 = r31
            r7.setCode(r0)
        L_0x060c:
            r31 = 1
            byte r31 = r12[r31]
            r0 = r31
            r7.setData(r0)
            java.lang.String r24 = com.iwown.ble_module.utils.JsonTool.toJson(r7)
            goto L_0x01a7
        L_0x061b:
            r31 = 1
            r0 = r31
            r7.setCode(r0)
            goto L_0x060c
        L_0x0623:
            r31 = 1
            byte r31 = r12[r31]
            r32 = 3
            r0 = r31
            r1 = r32
            if (r0 != r1) goto L_0x01a7
            com.iwown.ble_module.zg_ble.data.model.AgpsType r7 = new com.iwown.ble_module.zg_ble.data.model.AgpsType
            r7.<init>()
            r31 = 5
            byte r31 = r12[r31]
            r0 = r31
            r7.setStatusCode(r0)
            r31 = 1
            byte r31 = r12[r31]
            r0 = r31
            r7.setData(r0)
            java.lang.String r24 = com.iwown.ble_module.utils.JsonTool.toJson(r7)
            goto L_0x01a7
        L_0x064c:
            r31 = 15
            r0 = r31
            if (r14 != r0) goto L_0x01a7
            r31 = 1
            r0 = r31
            java.lang.String r31 = com.iwown.ble_module.utils.Util.bytesToString(r12, r0)
            com.socks.library.KLog.e(r31)
            int r0 = r12.length
            r31 = r0
            r32 = 4
            r0 = r31
            r1 = r32
            if (r0 <= r1) goto L_0x01a7
            r31 = 1
            byte r31 = r12[r31]
            r32 = 90
            r0 = r31
            r1 = r32
            if (r0 == r1) goto L_0x0680
            r31 = 1
            byte r31 = r12[r31]
            r32 = 85
            r0 = r31
            r1 = r32
            if (r0 != r1) goto L_0x0698
        L_0x0680:
            r31 = 4
            byte r31 = r12[r31]
            if (r31 != 0) goto L_0x01a7
            com.iwown.ble_module.zg_ble.data.model.C100Bean r6 = new com.iwown.ble_module.zg_ble.data.model.C100Bean
            r6.<init>()
            r31 = 0
            r0 = r31
            r6.setCode(r0)
            java.lang.String r24 = com.iwown.ble_module.utils.JsonTool.toJson(r6)
            goto L_0x01a7
        L_0x0698:
            r31 = 1
            byte r31 = r12[r31]
            if (r31 == 0) goto L_0x01a7
            r31 = 4
            byte r31 = r12[r31]
            if (r31 != 0) goto L_0x06b6
            com.iwown.ble_module.zg_ble.data.model.C100Bean r6 = new com.iwown.ble_module.zg_ble.data.model.C100Bean
            r6.<init>()
            r31 = 1
            r0 = r31
            r6.setCode(r0)
            java.lang.String r24 = com.iwown.ble_module.utils.JsonTool.toJson(r6)
            goto L_0x01a7
        L_0x06b6:
            java.lang.String r31 = "yyyyy等待.."
            com.socks.library.KLog.e(r31)
            goto L_0x01a7
        L_0x06be:
            com.iwown.ble_module.zg_ble.data.model.DeviceSetting r16 = com.iwown.ble_module.zg_ble.data.model.DeviceSetting.getInstance()     // Catch:{ Exception -> 0x06d5 }
            r31 = 2
            byte r31 = r12[r31]     // Catch:{ Exception -> 0x06d5 }
            r32 = 1
            r0 = r31
            r1 = r32
            if (r0 != r1) goto L_0x06db
            r0 = r16
            r0.init_01_data(r12)     // Catch:{ Exception -> 0x06d5 }
            goto L_0x01d0
        L_0x06d5:
            r17 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r17)
            goto L_0x01d0
        L_0x06db:
            r31 = 2
            byte r31 = r12[r31]     // Catch:{ Exception -> 0x06d5 }
            r32 = 2
            r0 = r31
            r1 = r32
            if (r0 != r1) goto L_0x06ee
            r0 = r16
            r0.init_82_data(r12)     // Catch:{ Exception -> 0x06d5 }
            goto L_0x01d0
        L_0x06ee:
            r31 = 2
            byte r31 = r12[r31]     // Catch:{ Exception -> 0x06d5 }
            r32 = -125(0xffffffffffffff83, float:NaN)
            r0 = r31
            r1 = r32
            if (r0 == r1) goto L_0x0706
            r31 = 2
            byte r31 = r12[r31]     // Catch:{ Exception -> 0x06d5 }
            r32 = 3
            r0 = r31
            r1 = r32
            if (r0 != r1) goto L_0x01d0
        L_0x0706:
            r0 = r16
            r0.init_83_data(r12)     // Catch:{ Exception -> 0x06d5 }
            java.lang.String r24 = com.iwown.ble_module.utils.JsonTool.toJson(r16)     // Catch:{ Exception -> 0x06d5 }
            java.lang.String r31 = com.iwown.ble_module.utils.JsonTool.toJson(r16)     // Catch:{ Exception -> 0x06d5 }
            r0 = r34
            r1 = r31
            com.iwown.ble_module.zg_ble.data.SPUtils.saveFirmwareInformation(r0, r1)     // Catch:{ Exception -> 0x06d5 }
            goto L_0x01d0
        L_0x071c:
            java.lang.String r31 = "com.zeroner.sdk.zg.ble.BLE_CONNECT_ERROR_257"
            r0 = r31
            boolean r31 = r0.equals(r4)
            if (r31 == 0) goto L_0x005c
            r33.onBluetoothError()
            goto L_0x005c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.iwown.ble_module.zg_ble.data.ZGBaseBleReceiver.onReceive(android.content.Context, android.content.Intent):void");
    }

    /* access modifiers changed from: protected */
    public void onScanResult(WristBand dev) {
        if (this.mIDataReceiveHandler != null) {
            this.mIDataReceiveHandler.onScanResult(dev);
        }
    }

    /* access modifiers changed from: protected */
    public void onCharacteristicWriteData() {
        this.mIDataReceiveHandler.onBluetoothInit();
    }

    /* access modifiers changed from: protected */
    public void connectStatue(boolean isConnect) {
        this.mIDataReceiveHandler.connectStatue(isConnect);
    }

    /* access modifiers changed from: protected */
    public void onCommonSend(byte[] data) {
        this.mIDataReceiveHandler.onCommonSend(data);
    }

    /* access modifiers changed from: protected */
    public void onDiscoverService(String serviceUUID) {
        this.mIDataReceiveHandler.onDiscoverService(serviceUUID);
    }

    /* access modifiers changed from: protected */
    public void onDiscoverCharacter(String characterUUID) {
        this.mIDataReceiveHandler.onDiscoverCharacter(characterUUID);
    }

    /* access modifiers changed from: protected */
    public void onCharacteristicChange(String address) {
        this.mIDataReceiveHandler.onCharacteristicChange(address);
    }

    /* access modifiers changed from: protected */
    public void onBluetoothError() {
        this.mIDataReceiveHandler.onBluetoothError();
    }

    /* access modifiers changed from: protected */
    public void onDataArrived(int ble_sdk_type, int dataType, String data) {
        if (this.mIDataReceiveHandler == null) {
            KLog.e("mIDataReceiveHandler is null");
        } else {
            this.mIDataReceiveHandler.onDataArrived(ble_sdk_type, dataType, data);
        }
    }
}
