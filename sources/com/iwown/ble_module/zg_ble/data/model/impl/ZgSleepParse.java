package com.iwown.ble_module.zg_ble.data.model.impl;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.utils.JsonTool;
import com.iwown.ble_module.zg_ble.data.model.ZgSleepData;
import com.iwown.ble_module.zg_ble.data.model.ZgSleepData.Sleep;
import com.iwown.ble_module.zg_ble.utils.ByteUtil;
import com.iwown.ble_module.zg_ble.utils.Util;
import com.iwown.lib_common.date.DateUtil;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ZgSleepParse {
    private static ZgSleepParse instance = null;
    private List<String> dataStr = new ArrayList();
    private List<Byte> mData = new ArrayList();

    public static synchronized ZgSleepParse getInstance() {
        ZgSleepParse zgSleepParse;
        synchronized (ZgSleepParse.class) {
            if (instance == null) {
                instance = new ZgSleepParse();
            }
            zgSleepParse = instance;
        }
        return zgSleepParse;
    }

    private ZgSleepParse() {
    }

    public List<Byte> getmData() {
        return this.mData;
    }

    public void setmData(List<Byte> mData2) {
        this.mData = mData2;
    }

    public void clear() {
        instance = null;
        this.mData.clear();
        this.dataStr.clear();
    }

    public void addList(byte[] data) {
        byte[] mDa;
        String msg = Util.bytesToString(data);
        if (!this.dataStr.contains(msg)) {
            this.dataStr.add(msg);
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

    public String parse() {
        long sUMin;
        long eUMin;
        if (this.mData.size() < 19) {
            KLog.e("no2855", "解析睡眠数据过短,解析异常" + this.mData.size());
            return null;
        }
        byte[] datas = new byte[this.mData.size()];
        for (int i = 0; i < this.mData.size(); i++) {
            datas[i] = ((Byte) this.mData.get(i)).byteValue();
        }
        try {
            ZgSleepData sleepData = new ZgSleepData();
            int year = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 4, 6));
            int month = ByteUtil.byteToInt(datas[6]);
            int day = ByteUtil.byteToInt(datas[7]);
            sleepData.setYear(year);
            sleepData.setMonth(month);
            sleepData.setDay(day);
            if (year == 0 || month == 0) {
                return null;
            }
            int sMin = -1;
            int eMin = -1;
            int startIndex = 0;
            ArrayList arrayList = new ArrayList();
            int i2 = 0;
            while (i2 < 240 && i2 + 20 < datas.length) {
                if (sMin == -1 && datas[i2 + 20] != 0) {
                    int[] mins = new int[4];
                    mins[3] = (datas[i2 + 20] >> 6) & 3;
                    mins[2] = (datas[i2 + 20] >> 4) & 3;
                    mins[1] = (datas[i2 + 20] >> 2) & 3;
                    mins[0] = datas[i2 + 20] & 3;
                    startIndex = i2;
                    for (int j = 0; j < 4; j++) {
                        if (mins[j] != 0 && sMin == -1) {
                            sMin = (i2 * 4) + j;
                        }
                    }
                }
                if (sMin >= 0) {
                    if (datas[i2 + 20] == 0) {
                        break;
                    }
                    int[] mins2 = new int[4];
                    mins2[3] = (datas[i2 + 20] >> 6) & 3;
                    mins2[2] = (datas[i2 + 20] >> 4) & 3;
                    mins2[1] = (datas[i2 + 20] >> 2) & 3;
                    mins2[0] = datas[i2 + 20] & 3;
                    for (int j2 = 0; j2 < 4; j2++) {
                        if (mins2[j2] != 0) {
                            arrayList.add(Integer.valueOf(getType(mins2[j2])));
                            eMin = (i2 * 4) + j2 + 1;
                        } else if (startIndex != i2) {
                            if (i2 + 1 >= 240) {
                                boolean isAdd = false;
                                int d = j2 + 1;
                                while (true) {
                                    if (d >= 4) {
                                        break;
                                    } else if (mins2[d] != 0) {
                                        isAdd = true;
                                        break;
                                    } else {
                                        d++;
                                    }
                                }
                                if (isAdd) {
                                    arrayList.add(Integer.valueOf(4));
                                    eMin = (i2 * 4) + j2 + 1;
                                }
                            } else if (datas[i2 + 20 + 1] != 0) {
                                arrayList.add(Integer.valueOf(4));
                                eMin = (i2 * 4) + j2 + 1;
                            } else {
                                boolean isAdd2 = false;
                                int d2 = j2 + 1;
                                while (true) {
                                    if (d2 >= 4) {
                                        break;
                                    } else if (mins2[d2] != 0) {
                                        isAdd2 = true;
                                        break;
                                    } else {
                                        d2++;
                                    }
                                }
                                if (isAdd2) {
                                    arrayList.add(Integer.valueOf(4));
                                    eMin = (i2 * 4) + j2 + 1;
                                }
                            }
                        }
                    }
                }
                i2++;
            }
            if (sMin + 1200 < 1440) {
                int sMin2 = sMin + 1200;
                DateUtil dateUtil = new DateUtil(year, month, day);
                dateUtil.addDay(-1);
                sUMin = Util.date2TimeStamp(dateUtil.getYear(), dateUtil.getMonth(), dateUtil.getDay(), sMin2 / 60, sMin2 % 60);
            } else {
                int sMin3 = (sMin + 1200) - 1440;
                sUMin = Util.date2TimeStamp(year, month, day, sMin3 / 60, sMin3 % 60);
            }
            if (eMin + 1200 < 1440) {
                int eMin2 = eMin + 1200;
                DateUtil dateUtil2 = new DateUtil(year, month, day);
                dateUtil2.addDay(-1);
                eUMin = Util.date2TimeStamp(dateUtil2.getYear(), dateUtil2.getMonth(), dateUtil2.getDay(), eMin2 / 60, eMin2 % 60);
            } else {
                int eMin3 = (eMin + 1200) - 1440;
                eUMin = Util.date2TimeStamp(year, month, day, eMin3 / 60, eMin3 % 60);
            }
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            int mTime = 0;
            int totalSleep = arrayList.size();
            int deepSleep = 0;
            int lightSleep = 0;
            int weakSleep = 0;
            for (int i3 = 0; i3 < arrayList.size(); i3++) {
                if (((Integer) arrayList.get(i3)).intValue() == 3) {
                    deepSleep++;
                } else if (((Integer) arrayList.get(i3)).intValue() == 6) {
                    weakSleep++;
                } else {
                    lightSleep++;
                }
                if (i3 < arrayList.size() - 1) {
                    if (arrayList.get(i3) == arrayList.get(i3 + 1)) {
                        mTime++;
                    } else {
                        arrayList2.add(Integer.valueOf(mTime + 1));
                        arrayList3.add(arrayList.get(i3));
                        mTime = 0;
                    }
                }
                if (i3 == arrayList.size() - 1) {
                    arrayList2.add(Integer.valueOf(mTime + 1));
                    arrayList3.add(arrayList.get(i3));
                    mTime = 0;
                }
            }
            int tol = 0;
            ArrayList arrayList4 = new ArrayList();
            for (int i4 = 0; i4 < arrayList3.size(); i4++) {
                Sleep sleep = new Sleep();
                int st1 = tol;
                tol += ((Integer) arrayList2.get(i4)).intValue();
                sleep.setSt(st1);
                sleep.setEt(tol);
                sleep.setType(((Integer) arrayList3.get(i4)).intValue());
                arrayList4.add(sleep);
            }
            int tUx = (int) ((eUMin - sUMin) / 60);
            KLog.e("no2855", "no2855睡眠计算: " + sUMin + " --> " + eUMin + " 差: " + tUx + " 总: " + totalSleep);
            if (tUx > totalSleep) {
                eUMin -= (long) ((tUx - totalSleep) * 60);
            } else if (tUx < totalSleep) {
                eUMin += (long) ((totalSleep - tUx) * 60);
            }
            sleepData.setTotalSleep(totalSleep);
            sleepData.setDeepSleep(deepSleep);
            sleepData.setLightSleep(lightSleep);
            sleepData.setWakeSleep(weakSleep);
            sleepData.setStartTime(sUMin);
            sleepData.setEndTime(eUMin);
            sleepData.setData(arrayList4);
            clear();
            return JsonTool.toJson(sleepData);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            KLog.e("no2855", "睡眠解析异常");
            return null;
        }
    }

    private int getT(int s) {
        if (s < 1200) {
            return s + 240;
        }
        return s - 1200;
    }

    private int getC(int c) {
        if (c % 4 == 0) {
            return c / 4;
        }
        return (c / 4) + 1;
    }

    private int getType(int type) {
        if (type == 1) {
            return 3;
        }
        if (type == 2) {
            return 4;
        }
        if (type == 3) {
            return 6;
        }
        return 0;
    }
}
