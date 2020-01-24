package com.iwown.healthy.network;

import com.iwown.data_link.consts.ApiConst;
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
            case 10002:
                return "缺少参数提供";
            case 10007:
                return "参数格式异常";
            case 20002:
                return "回答问题错误";
            case 20012:
                return "您已经绑定过手表了";
            case 20013:
                return "手表未激活";
            case 20055:
                return "异常20055";
            case 20056:
                return "验证码服务器异常";
            case ApiConst.PasswordNotMatch /*50003*/:
                return "密码不匹配";
            case ApiConst.UserAlreadyExist /*50004*/:
                return "用户已存在";
            case 50007:
                return "服务器DB异常";
            case ApiConst.UserNotExist /*50012*/:
                return "用户不存在";
            default:
                return "未知异常 " + retCode2;
        }
    }
}
