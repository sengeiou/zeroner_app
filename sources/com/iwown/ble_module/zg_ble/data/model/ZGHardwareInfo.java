package com.iwown.ble_module.zg_ble.data.model;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.zg_ble.utils.ByteUtil;
import java.util.Arrays;

public class ZGHardwareInfo extends Result {
    private int dev_cfca;
    private int dev_fontic;
    private int dev_gsensor;
    private int dev_heart;
    private int dev_key_type;
    private int dev_moto;
    private int dev_nfc;
    private int dev_reserve = 0;
    private int dev_screen;
    private int dev_version_high;
    private int dev_version_low;
    private String model = "";

    public String getModel() {
        return this.model;
    }

    public void setModel(String model2) {
        this.model = model2;
    }

    public int getDev_version_high() {
        return this.dev_version_high;
    }

    public void setDev_version_high(int dev_version_high2) {
        this.dev_version_high = dev_version_high2;
    }

    public int getDev_version_low() {
        return this.dev_version_low;
    }

    public void setDev_version_low(int dev_version_low2) {
        this.dev_version_low = dev_version_low2;
    }

    public int getDev_screen() {
        return this.dev_screen;
    }

    public void setDev_screen(int dev_screen2) {
        this.dev_screen = dev_screen2;
    }

    public int getDev_key_type() {
        return this.dev_key_type;
    }

    public void setDev_key_type(int dev_key_type2) {
        this.dev_key_type = dev_key_type2;
    }

    public int getDev_fontic() {
        return this.dev_fontic;
    }

    public void setDev_fontic(int dev_fontic2) {
        this.dev_fontic = dev_fontic2;
    }

    public int getDev_gsensor() {
        return this.dev_gsensor;
    }

    public void setDev_gsensor(int dev_gsensor2) {
        this.dev_gsensor = dev_gsensor2;
    }

    public int getDev_moto() {
        return this.dev_moto;
    }

    public void setDev_moto(int dev_moto2) {
        this.dev_moto = dev_moto2;
    }

    public int getDev_heart() {
        return this.dev_heart;
    }

    public void setDev_heart(int dev_heart2) {
        this.dev_heart = dev_heart2;
    }

    public int getDev_cfca() {
        return this.dev_cfca;
    }

    public void setDev_cfca(int dev_cfca2) {
        this.dev_cfca = dev_cfca2;
    }

    public int getDev_nfc() {
        return this.dev_nfc;
    }

    public void setDev_nfc(int dev_nfc2) {
        this.dev_nfc = dev_nfc2;
    }

    public int getDev_reserve() {
        return this.dev_reserve;
    }

    public void setDev_reserve(int dev_reserve2) {
        this.dev_reserve = dev_reserve2;
    }

    public void parseData(byte[] datas) {
        try {
            this.dev_version_low = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 4, 5));
            this.dev_version_high = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 5, 6));
            this.dev_screen = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 6, 7));
            this.dev_key_type = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 7, 8));
            this.dev_fontic = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 8, 9));
            this.dev_gsensor = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 9, 10));
            this.dev_moto = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 10, 11));
            this.dev_heart = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 11, 12));
            this.dev_cfca = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 12, 13));
            this.dev_nfc = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 13, 14));
            this.model = ByteUtil.byteAsciiToChar(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 16, 17)), ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 17, 18)), ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 18, 19)), ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 19, 20)));
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }
}
