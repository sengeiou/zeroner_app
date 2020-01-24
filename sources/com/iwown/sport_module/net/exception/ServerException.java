package com.iwown.sport_module.net.exception;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.Constants;

public class ServerException extends RuntimeException {
    private int code = -1;
    private String msg = "";

    public int code() {
        return this.code;
    }

    public String message() {
        return this.msg;
    }

    public ServerException(int retCode) {
        this.code = retCode;
        this.msg = getMsg(retCode);
    }

    private String getMsg(int retCode) {
        try {
            return Constants.getServerMsgByCode(retCode);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return "unknown mistake " + retCode;
        }
    }
}
