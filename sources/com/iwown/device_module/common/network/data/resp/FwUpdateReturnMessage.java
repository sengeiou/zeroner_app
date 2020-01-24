package com.iwown.device_module.common.network.data.resp;

public class FwUpdateReturnMessage extends RetCode {
    private FwUpdateReturnDetail firmware;

    public FwUpdateReturnDetail getFirmware() {
        return this.firmware;
    }

    public void setFirmware(FwUpdateReturnDetail firmware2) {
        this.firmware = firmware2;
    }
}
