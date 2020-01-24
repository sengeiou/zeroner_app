package com.iwown.ble_module.zg_ble.data.model.impl;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.utils.JsonTool;
import com.iwown.ble_module.zg_ble.data.model.detail_sport.model.ZgDetailSportData;
import com.iwown.ble_module.zg_ble.data.model.detail_sport.model.ZgDetailSportData.Sport;
import com.iwown.ble_module.zg_ble.utils.ByteUtil;
import com.iwown.ble_module.zg_ble.utils.Util;
import com.socks.library.KLog;
import com.tencent.tinker.android.dx.instruction.Opcodes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ZgDetailSportParse {
    private static ZgDetailSportParse instance = null;
    private List<String> dataStr = new ArrayList();
    private int lastC = 0;
    private List<Byte> mData = new ArrayList();

    public static synchronized ZgDetailSportParse getInstance() {
        ZgDetailSportParse zgDetailSportParse;
        synchronized (ZgDetailSportParse.class) {
            if (instance == null) {
                instance = new ZgDetailSportParse();
            }
            zgDetailSportParse = instance;
        }
        return zgDetailSportParse;
    }

    private ZgDetailSportParse() {
    }

    public void clear() {
        instance = null;
        this.mData.clear();
        this.dataStr.clear();
        this.lastC = 0;
    }

    public void addList(byte[] data) {
        byte[] mDa;
        String msg = Util.bytesToString(data);
        if (!this.dataStr.contains(msg)) {
            this.dataStr.add(msg);
            this.lastC = data[2];
            if (data[2] == 1) {
                this.mData.clear();
                this.dataStr.clear();
                this.dataStr.add(msg);
                mDa = data;
            } else {
                mDa = Arrays.copyOfRange(data, 4, data.length);
            }
            for (byte valueOf : mDa) {
                this.mData.add(Byte.valueOf(valueOf));
            }
        }
    }

    public boolean isOver(int count) {
        return count - this.lastC != 1;
    }

    public String parse() {
        if (this.mData.size() < 212) {
            KLog.e("no2855", "解析运动数据过短,解析异常" + this.mData.size());
            return null;
        }
        byte[] datas = new byte[this.mData.size()];
        for (int i = 0; i < this.mData.size(); i++) {
            datas[i] = ((Byte) this.mData.get(i)).byteValue();
        }
        try {
            ZgDetailSportData sportData = new ZgDetailSportData();
            int year = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 4, 6));
            int month = ByteUtil.byteToInt(datas[6]);
            int day = ByteUtil.byteToInt(datas[7]);
            int count = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 8, 12));
            ArrayList arrayList = new ArrayList();
            if (count > 0) {
                int mPos = 12;
                int lastCount = 0;
                for (int i2 = 0; i2 < count; i2++) {
                    Sport sport = new Sport();
                    sport.setSportType(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, mPos, mPos + 2)));
                    int total = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, mPos + 2, mPos + 4));
                    sport.setTotalMin(total);
                    int sHour = ByteUtil.byteToInt(datas[mPos + 4]);
                    int sMin = ByteUtil.byteToInt(datas[mPos + 5]);
                    int eHour = ByteUtil.byteToInt(datas[mPos + 6]);
                    int eMin = ByteUtil.byteToInt(datas[mPos + 7]);
                    sport.setStartMin((sHour * 60) + sMin);
                    sport.setEndMin((eHour * 60) + eMin);
                    sport.setSteps(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, mPos + 8, mPos + 12)));
                    sport.setDistance((float) (ByteUtil.bytesToInt(Arrays.copyOfRange(datas, mPos + 12, mPos + 14)) * 10));
                    sport.setCalories((float) ByteUtil.bytesToInt(Arrays.copyOfRange(datas, mPos + 14, mPos + 16)));
                    sport.setSpmMax(ByteUtil.byteToInt(datas[mPos + 16]));
                    sport.setSpmAvg(ByteUtil.byteToInt(datas[mPos + 17]));
                    sport.setHeartMax(ByteUtil.byteToInt(datas[mPos + 18]));
                    sport.setHeartAvg(ByteUtil.byteToInt(datas[mPos + 19]));
                    for (int j = lastCount + Opcodes.REM_INT_LIT16; j < lastCount + Opcodes.REM_INT_LIT16 + total; j++) {
                        sport.getHeart().add(Integer.valueOf(ByteUtil.byteToInt(datas[j])));
                    }
                    lastCount += total;
                    mPos += 20;
                    arrayList.add(sport);
                }
            }
            sportData.setSports(arrayList);
            sportData.setYear(year);
            sportData.setMonth(month);
            sportData.setDay(day);
            sportData.setCount(count);
            clear();
            return JsonTool.toJson(sportData);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            KLog.e("no2855", "解析运动数据异常");
            return null;
        }
    }
}
