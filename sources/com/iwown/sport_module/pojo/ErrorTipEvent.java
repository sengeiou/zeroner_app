package com.iwown.sport_module.pojo;

public class ErrorTipEvent {
    public static final int ERROR = 2;
    public static final int OK = 0;
    public static final int WARNING = 1;
    private boolean needANewTip = false;
    private String tip_content = "";
    private int tip_level = -1;

    public ErrorTipEvent(String tip_content2, int tip_level2) {
        this.tip_content = tip_content2;
        this.tip_level = tip_level2;
    }

    public ErrorTipEvent(String tip_content2, int tip_level2, boolean need_new) {
        this.tip_content = tip_content2;
        this.tip_level = tip_level2;
        this.needANewTip = need_new;
    }

    public boolean isNeedANewTip() {
        return this.needANewTip;
    }

    public void setNeedANewTip(boolean needANewTip2) {
        this.needANewTip = needANewTip2;
    }

    public String getTip_content() {
        return this.tip_content;
    }

    public void setTip_content(String tip_content2) {
        this.tip_content = tip_content2;
    }

    public int getTip_level() {
        return this.tip_level;
    }

    public void setTip_level(int tip_level2) {
        this.tip_level = tip_level2;
    }
}
