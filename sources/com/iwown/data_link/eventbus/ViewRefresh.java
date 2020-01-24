package com.iwown.data_link.eventbus;

public class ViewRefresh {
    private boolean flag;
    private int type;

    public boolean isFlag() {
        return this.flag;
    }

    public void setFlag(boolean flag2) {
        this.flag = flag2;
    }

    public ViewRefresh(boolean flag2, int type2) {
        this.flag = flag2;
        this.type = type2;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type2) {
        this.type = type2;
    }
}
