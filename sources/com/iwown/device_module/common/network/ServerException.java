package com.iwown.device_module.common.network;

import com.socks.library.KLog;

public class ServerException extends RuntimeException {
    public String msg;
    public int retCode;

    public ServerException(int retCode2) {
        this.retCode = retCode2;
        this.msg = getMsg(retCode2);
        KLog.e(retCode2 + "  " + this.msg);
    }

    public ServerException(int retCode2, String msg2) {
        this.retCode = retCode2;
        this.msg = msg2;
    }

    private String getMsg(int retCode2) {
        switch (retCode2) {
            case -1:
                return "返回数据异常 retCode";
            case 0:
                return "ok";
            case 10404:
                return "no data";
            case 20055:
                return "异常20055";
            default:
                return "未知异常 " + retCode2;
        }
    }
}
