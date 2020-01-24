package com.iwown.sport_module.gps.data;

import org.litepal.crud.DataSupport;

public class TB_sport_correct_gps extends DataSupport {
    private float climb_distance;
    private int climb_duration;
    private int climb_times;
    private float cycle_distance;
    private int cycle_duration;
    private int cycle_times;
    private float run_distance;
    private int run_duration;
    private int run_times;
    private long uid;
    private float walk_distance;
    private int walk_duration;
    private int walk_times;

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public int getRun_times() {
        return this.run_times;
    }

    public void setRun_times(int run_times2) {
        this.run_times = run_times2;
    }

    public float getRun_distance() {
        return this.run_distance;
    }

    public void setRun_distance(float run_distance2) {
        this.run_distance = run_distance2;
    }

    public int getRun_duration() {
        return this.run_duration;
    }

    public void setRun_duration(int run_duration2) {
        this.run_duration = run_duration2;
    }

    public int getCycle_times() {
        return this.cycle_times;
    }

    public void setCycle_times(int cycle_times2) {
        this.cycle_times = cycle_times2;
    }

    public float getCycle_distance() {
        return this.cycle_distance;
    }

    public void setCycle_distance(float cycle_distance2) {
        this.cycle_distance = cycle_distance2;
    }

    public int getCycle_duration() {
        return this.cycle_duration;
    }

    public void setCycle_duration(int cycle_duration2) {
        this.cycle_duration = cycle_duration2;
    }

    public int getWalk_times() {
        return this.walk_times;
    }

    public void setWalk_times(int walk_times2) {
        this.walk_times = walk_times2;
    }

    public float getWalk_distance() {
        return this.walk_distance;
    }

    public void setWalk_distance(float walk_distance2) {
        this.walk_distance = walk_distance2;
    }

    public int getWalk_duration() {
        return this.walk_duration;
    }

    public void setWalk_duration(int walk_duration2) {
        this.walk_duration = walk_duration2;
    }

    public int getClimb_times() {
        return this.climb_times;
    }

    public void setClimb_times(int climb_times2) {
        this.climb_times = climb_times2;
    }

    public float getClimb_distance() {
        return this.climb_distance;
    }

    public void setClimb_distance(float climb_distance2) {
        this.climb_distance = climb_distance2;
    }

    public int getClimb_duration() {
        return this.climb_duration;
    }

    public void setClimb_duration(int climb_duration2) {
        this.climb_duration = climb_duration2;
    }
}
