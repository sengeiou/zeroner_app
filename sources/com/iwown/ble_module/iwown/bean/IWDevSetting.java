package com.iwown.ble_module.iwown.bean;

import com.iwown.ble_module.iwown.utils.ByteUtil;
import java.util.Arrays;

public class IWDevSetting {
    private int autoHr = -1;
    private int autoSleep;
    private int autoSpt = -1;
    private int backLightEt;
    private int backLightSt;
    private int canFindPhone = -1;
    private int gesture;
    private int is24Hour;
    private int isConnectTipOpen;
    private int is_dd_mm_format = -1;
    private int language;
    private int light;
    private int reverse_light_Et = -1;
    private int reverse_light_St = -1;
    private int screen;
    private int unit;

    public IWDevSetting() {
    }

    public IWDevSetting(int light2, int gesture2, int unit2, int is24Hour2, int autoSleep2, int backLightSt2, int backLightEt2, int screen2, int language2, int isConnectTipOpen2, int is_dd_mm_format2, int reverse_light_St2, int reverse_light_Et2, int autoHr2, int canFindPhone2) {
        this.light = light2;
        this.gesture = gesture2;
        this.unit = unit2;
        this.is24Hour = is24Hour2;
        this.autoSleep = autoSleep2;
        this.backLightSt = backLightSt2;
        this.backLightEt = backLightEt2;
        this.screen = screen2;
        this.language = language2;
        this.is_dd_mm_format = is_dd_mm_format2;
        this.reverse_light_St = reverse_light_St2;
        this.reverse_light_Et = reverse_light_Et2;
        this.autoHr = autoHr2;
        this.canFindPhone = canFindPhone2;
        this.autoSpt = canFindPhone2;
        this.isConnectTipOpen = isConnectTipOpen2;
    }

    public IWDevSetting(int light2, int gesture2, int unit2, int is24Hour2, int autoSleep2, int backLightSt2, int backLightEt2, int screen2, int language2, int isConnectTipOpen2, int is_dd_mm_format2, int reverse_light_St2, int reverse_light_Et2, int autoHr2) {
        this.light = light2;
        this.gesture = gesture2;
        this.unit = unit2;
        this.is24Hour = is24Hour2;
        this.autoSleep = autoSleep2;
        this.backLightSt = backLightSt2;
        this.backLightEt = backLightEt2;
        this.screen = screen2;
        this.language = language2;
        this.is_dd_mm_format = is_dd_mm_format2;
        this.reverse_light_St = reverse_light_St2;
        this.reverse_light_Et = reverse_light_Et2;
        this.autoHr = autoHr2;
        this.isConnectTipOpen = isConnectTipOpen2;
    }

    public IWDevSetting(int light2, int gesture2, int unit2, int is24Hour2, int autoSleep2, int backLightSt2, int backLightEt2, int screen2, int language2, int isConnectTipOpen2, int is_dd_mm_format2) {
        this.light = light2;
        this.gesture = gesture2;
        this.unit = unit2;
        this.is24Hour = is24Hour2;
        this.autoSleep = autoSleep2;
        this.backLightSt = backLightSt2;
        this.backLightEt = backLightEt2;
        this.screen = screen2;
        this.language = language2;
        this.is_dd_mm_format = is_dd_mm_format2;
        this.isConnectTipOpen = isConnectTipOpen2;
    }

    public IWDevSetting(int light2, int gesture2, int unit2, int is24Hour2, int autoSleep2, int backLightSt2, int backLightEt2, int screen2, int language2, int isConnectTipOpen2, int is_dd_mm_format2, int reverse_light_St2, int reverse_light_Et2) {
        this.light = light2;
        this.gesture = gesture2;
        this.unit = unit2;
        this.is24Hour = is24Hour2;
        this.autoSleep = autoSleep2;
        this.backLightSt = backLightSt2;
        this.backLightEt = backLightEt2;
        this.screen = screen2;
        this.language = language2;
        this.is_dd_mm_format = is_dd_mm_format2;
        this.reverse_light_St = reverse_light_St2;
        this.reverse_light_Et = reverse_light_Et2;
        this.isConnectTipOpen = isConnectTipOpen2;
    }

    public IWDevSetting(int light2, int gesture2, int unit2, int is24Hour2, int autoSleep2, int backLightSt2, int backLightEt2) {
        this.light = light2;
        this.gesture = gesture2;
        this.unit = unit2;
        this.is24Hour = is24Hour2;
        this.autoSleep = autoSleep2;
        this.backLightSt = backLightSt2;
        this.backLightEt = backLightEt2;
    }

    public IWDevSetting(int light2, int gesture2, int unit2, int is24Hour2, int autoSleep2, int backLightSt2, int backLightEt2, int screen2, int language2, int isConnectTipOpen2) {
        this.light = light2;
        this.gesture = gesture2;
        this.unit = unit2;
        this.is24Hour = is24Hour2;
        this.autoSleep = autoSleep2;
        this.backLightSt = backLightSt2;
        this.backLightEt = backLightEt2;
        this.screen = screen2;
        this.language = language2;
        this.isConnectTipOpen = isConnectTipOpen2;
    }

    public int getIsConnectTipOpen() {
        return this.isConnectTipOpen;
    }

    public void setIsConnectTipOpen(int isConnectTipOpen2) {
        this.isConnectTipOpen = isConnectTipOpen2;
    }

    public int getIs_dd_mm_format() {
        return this.is_dd_mm_format;
    }

    public void setIs_dd_mm_format(int is_dd_mm_format2) {
        this.is_dd_mm_format = is_dd_mm_format2;
    }

    public int getReverse_light_St() {
        return this.reverse_light_St;
    }

    public void setReverse_light_St(int reverse_light_St2) {
        this.reverse_light_St = reverse_light_St2;
    }

    public int getReverse_light_Et() {
        return this.reverse_light_Et;
    }

    public void setReverse_light_Et(int reverse_light_Et2) {
        this.reverse_light_Et = reverse_light_Et2;
    }

    public int getAutoSpt() {
        return this.autoSpt;
    }

    public void setAutoSpt(int autoSpt2) {
        this.autoSpt = autoSpt2;
    }

    public int getAutoHr() {
        return this.autoHr;
    }

    public void setAutoHr(int autoHr2) {
        this.autoHr = autoHr2;
    }

    public int getCanFindPhone() {
        return this.canFindPhone;
    }

    public void setCanFindPhone(int canFindPhone2) {
        this.canFindPhone = canFindPhone2;
    }

    public int getUnit() {
        return this.unit;
    }

    public void setUnit(int unit2) {
        this.unit = unit2;
    }

    public IWDevSetting(int light2, int gesture2, int is24Hour2, int autoSleep2, int backLightSt2, int backLightEt2) {
        this.light = light2;
        this.gesture = gesture2;
        this.is24Hour = is24Hour2;
        this.autoSleep = autoSleep2;
        this.backLightSt = backLightSt2;
        this.backLightEt = backLightEt2;
    }

    public int getLight() {
        return this.light;
    }

    public void setLight(int light2) {
        this.light = light2;
    }

    public int getGesture() {
        return this.gesture;
    }

    public void setGesture(int gesture2) {
        this.gesture = gesture2;
    }

    public int getIs24Hour() {
        return this.is24Hour;
    }

    public void setIs24Hour(int is24Hour2) {
        this.is24Hour = is24Hour2;
    }

    public int getAutoSleep() {
        return this.autoSleep;
    }

    public void setAutoSleep(int autoSleep2) {
        this.autoSleep = autoSleep2;
    }

    public int getBackLightSt() {
        return this.backLightSt;
    }

    public void setBackLightSt(int backLightSt2) {
        this.backLightSt = backLightSt2;
    }

    public int getBackLightEt() {
        return this.backLightEt;
    }

    public void setBackLightEt(int backLightEt2) {
        this.backLightEt = backLightEt2;
    }

    public int getScreen() {
        return this.screen;
    }

    public void setScreen(int screen2) {
        this.screen = screen2;
    }

    public int getLanguage() {
        return this.language;
    }

    public void setLanguage(int language2) {
        this.language = language2;
    }

    public void parseData(byte[] datas) {
        if (datas.length < 15) {
            this.light = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 4, 5));
            this.gesture = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 5, 6));
            this.unit = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 6, 7));
            this.is24Hour = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 7, 8));
            this.autoSleep = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 8, 9));
            this.backLightSt = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 10, 11));
            this.backLightEt = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 11, 12));
        } else if (datas.length == 15) {
            this.light = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 4, 5));
            this.gesture = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 5, 6));
            this.unit = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 6, 7));
            this.is24Hour = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 7, 8));
            this.autoSleep = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 8, 9));
            this.backLightSt = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 10, 11));
            this.backLightEt = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 11, 12));
            this.screen = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 12, 13));
            this.language = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 13, 14));
            this.isConnectTipOpen = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 14, 15));
        } else if (datas.length == 16) {
            this.light = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 4, 5));
            this.gesture = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 5, 6));
            this.unit = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 6, 7));
            this.is24Hour = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 7, 8));
            this.autoSleep = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 8, 9));
            this.backLightSt = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 10, 11));
            this.backLightEt = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 11, 12));
            this.screen = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 12, 13));
            this.language = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 13, 14));
            this.isConnectTipOpen = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 14, 15));
            this.is_dd_mm_format = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 15, 16));
        } else if (datas.length == 18) {
            this.light = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 4, 5));
            this.gesture = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 5, 6));
            this.unit = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 6, 7));
            this.is24Hour = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 7, 8));
            this.autoSleep = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 8, 9));
            this.backLightSt = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 10, 11));
            this.backLightEt = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 11, 12));
            this.screen = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 12, 13));
            this.language = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 13, 14));
            this.isConnectTipOpen = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 14, 15));
            this.is_dd_mm_format = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 15, 16));
            this.reverse_light_St = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 16, 17));
            this.reverse_light_Et = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 17, 18));
        } else if (datas.length == 19) {
            this.light = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 4, 5));
            this.gesture = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 5, 6));
            this.unit = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 6, 7));
            this.is24Hour = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 7, 8));
            this.autoSleep = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 8, 9));
            this.backLightSt = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 10, 11));
            this.backLightEt = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 11, 12));
            this.screen = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 12, 13));
            this.language = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 13, 14));
            this.isConnectTipOpen = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 14, 15));
            this.is_dd_mm_format = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 15, 16));
            this.reverse_light_St = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 16, 17));
            this.reverse_light_Et = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 17, 18));
            this.autoHr = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 18, 19));
        } else if (datas.length == 20) {
            this.light = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 4, 5));
            this.gesture = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 5, 6));
            this.unit = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 6, 7));
            this.is24Hour = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 7, 8));
            this.autoSleep = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 8, 9));
            this.backLightSt = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 10, 11));
            this.backLightEt = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 11, 12));
            this.screen = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 12, 13));
            this.language = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 13, 14));
            this.isConnectTipOpen = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 14, 15));
            this.is_dd_mm_format = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 15, 16));
            this.reverse_light_St = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 16, 17));
            this.reverse_light_Et = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 17, 18));
            this.autoHr = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 18, 19));
            this.canFindPhone = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 19, 20));
        }
    }
}
