package com.kunekt.healthy.wxapi;

public class WxQqEvent {
    private String msg;
    private int type;

    public WxQqEvent(int type2, String msg2) {
        this.type = type2;
        this.msg = msg2;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg2) {
        this.msg = msg2;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type2) {
        this.type = type2;
    }
}
