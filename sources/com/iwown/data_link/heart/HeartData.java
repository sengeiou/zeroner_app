package com.iwown.data_link.heart;

import java.util.ArrayList;
import java.util.List;

public class HeartData {
    private int avg;
    List<Integer> heInt = new ArrayList();
    private int maxHeart;
    private int max_bpm;
    private int min_bpm;
    int[] mins = new int[6];
    private int total51;

    public int getAvg() {
        return this.avg;
    }

    public void setAvg(int avg2) {
        this.avg = avg2;
    }

    public int getMin_bpm() {
        return this.min_bpm;
    }

    public void setMin_bpm(int min_bpm2) {
        this.min_bpm = min_bpm2;
    }

    public int getMax_bpm() {
        return this.max_bpm;
    }

    public void setMax_bpm(int max_bpm2) {
        this.max_bpm = max_bpm2;
    }

    public int getTotal51() {
        return this.total51;
    }

    public void setTotal51(int total512) {
        this.total51 = total512;
    }

    public int getMaxHeart() {
        return this.maxHeart;
    }

    public void setMaxHeart(int maxHeart2) {
        this.maxHeart = maxHeart2;
    }

    public List<Integer> getHeInt() {
        return this.heInt;
    }

    public void setHeInt(List<Integer> heInt2) {
        this.heInt = heInt2;
    }

    public int[] getMins() {
        return this.mins;
    }

    public void setMins(int[] mins2) {
        this.mins = mins2;
    }
}
