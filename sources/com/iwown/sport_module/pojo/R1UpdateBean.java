package com.iwown.sport_module.pojo;

import java.util.List;

public class R1UpdateBean {
    private String avgFlight;
    private String avgGround;
    private String avgRate;
    private String avgVert;
    private String banlance;
    private List<BaseBean> ground;
    private List<BaseBean> pace;
    private List<BaseBean> rate;
    private List<BaseBean> vert;

    public static class BaseBean {
        private int x;
        private float y;

        public int getX() {
            return this.x;
        }

        public void setX(int x2) {
            this.x = x2;
        }

        public float getY() {
            return this.y;
        }

        public void setY(float y2) {
            this.y = y2;
        }
    }

    public String getAvgRate() {
        return this.avgRate;
    }

    public void setAvgRate(String avgRate2) {
        this.avgRate = avgRate2;
    }

    public String getAvgGround() {
        return this.avgGround;
    }

    public void setAvgGround(String avgGround2) {
        this.avgGround = avgGround2;
    }

    public String getAvgFlight() {
        return this.avgFlight;
    }

    public void setAvgFlight(String avgFlight2) {
        this.avgFlight = avgFlight2;
    }

    public String getAvgVert() {
        return this.avgVert;
    }

    public void setAvgVert(String avgVert2) {
        this.avgVert = avgVert2;
    }

    public String getBanlance() {
        return this.banlance;
    }

    public void setBanlance(String banlance2) {
        this.banlance = banlance2;
    }

    public List<BaseBean> getPace() {
        return this.pace;
    }

    public void setPace(List<BaseBean> pace2) {
        this.pace = pace2;
    }

    public List<BaseBean> getRate() {
        return this.rate;
    }

    public void setRate(List<BaseBean> rate2) {
        this.rate = rate2;
    }

    public List<BaseBean> getVert() {
        return this.vert;
    }

    public void setVert(List<BaseBean> vert2) {
        this.vert = vert2;
    }

    public List<BaseBean> getGround() {
        return this.ground;
    }

    public void setGround(List<BaseBean> ground2) {
        this.ground = ground2;
    }
}
