package com.iwown.data_link.data;

public class ZgWelcomeBlood {
    private int[] blood;
    private int gender;
    private int height;
    private boolean old;
    private int timeZone;
    private String welcome;

    public String getWelcome() {
        return this.welcome;
    }

    public void setWelcome(String welcome2) {
        this.welcome = welcome2;
    }

    public int getTimeZone() {
        return this.timeZone;
    }

    public void setTimeZone(int timeZone2) {
        this.timeZone = timeZone2;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height2) {
        this.height = height2;
    }

    public int getGender() {
        return this.gender;
    }

    public void setGender(int gender2) {
        this.gender = gender2;
    }

    public int[] getBlood() {
        return this.blood;
    }

    public void setBlood(int[] blood2) {
        this.blood = blood2;
    }

    public boolean isOld() {
        return this.old;
    }

    public void setOld(boolean old2) {
        this.old = old2;
    }
}
