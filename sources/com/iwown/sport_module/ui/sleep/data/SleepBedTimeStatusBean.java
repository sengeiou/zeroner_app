package com.iwown.sport_module.ui.sleep.data;

public class SleepBedTimeStatusBean {
    public int icon0;
    public int icon1;
    public String name;
    public boolean selected;

    public SleepBedTimeStatusBean(String name2, int icon02, int icon12) {
        this.name = name2;
        this.icon0 = icon02;
        this.icon1 = icon12;
    }

    public String toString() {
        return "SleepBedTimeStatusBean{name='" + this.name + '\'' + ", icon0=" + this.icon0 + ", icon1=" + this.icon1 + ", selected=" + this.selected + '}';
    }
}
