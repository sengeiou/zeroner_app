package com.iwown.ble_module.zg_ble.data.model;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.zg_ble.utils.ByteUtil;
import java.util.Arrays;

public class WelcomeTextInfo extends Result {
    private String text = "";
    private int utc_offset = 0;

    public String getText() {
        return this.text;
    }

    public void setText(String text2) {
        this.text = text2;
    }

    public int getUtc_offset() {
        return this.utc_offset;
    }

    public void setUtc_offset(int utc_offset2) {
        this.utc_offset = utc_offset2;
    }

    public void parseData(byte[] datas) {
        try {
            int len = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 4, 5));
            this.text = new String(Arrays.copyOfRange(datas, 5, len + 5), "utf-8");
            this.utc_offset = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, len + 5, len + 5 + 1));
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }
}
