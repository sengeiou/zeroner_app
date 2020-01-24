package com.iwown.my_module.healthy.network.response;

import com.iwown.data_link.base.RetCode;

public class WxBindReturnCode extends RetCode {
    private String qrcode;

    public String getQrcode() {
        return this.qrcode;
    }

    public void setQrcode(String qrcode2) {
        this.qrcode = qrcode2;
    }
}
