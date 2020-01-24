package com.iwown.device_module.common.sql;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

public class TB_UserProfile extends DataSupport {
    private String birthday;
    @Column(defaultValue = "0")
    private int gender;
    private float height;
    @Column(defaultValue = "1")
    private int is_kg = 1;
    private String nickname;
    private String portrait_url;
    @Column(defaultValue = "0")
    private int target_step;
    @Column(defaultValue = "0")
    private float target_weight;
    @Column(unique = true)
    private long uid;
    private float weight;
    @Column(defaultValue = "0")
    private int weight_lbs;

    public float getTarget_weight() {
        return this.target_weight;
    }

    public void setTarget_weight(float target_weight2) {
        this.target_weight = target_weight2;
    }

    public int getTarget_step() {
        return this.target_step;
    }

    public void setTarget_step(int target_step2) {
        this.target_step = target_step2;
    }

    public int getIs_kg() {
        return this.is_kg;
    }

    public void setIs_kg(int is_kg2) {
        this.is_kg = is_kg2;
    }

    public int getWeight_lbs() {
        return this.weight_lbs;
    }

    public void setWeight_lbs(int weight_lbs2) {
        this.weight_lbs = weight_lbs2;
    }

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname2) {
        this.nickname = nickname2;
    }

    public int getGender() {
        return this.gender;
    }

    public void setGender(int gender2) {
        this.gender = gender2;
    }

    public float getHeight() {
        return this.height;
    }

    public void setHeight(float height2) {
        this.height = height2;
    }

    public float getWeight() {
        return this.weight;
    }

    public void setWeight(float weight2) {
        this.weight = weight2;
    }

    public String getPortrait_url() {
        return this.portrait_url;
    }

    public void setPortrait_url(String portrait_url2) {
        this.portrait_url = portrait_url2;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public void setBirthday(String birthday2) {
        this.birthday = birthday2;
    }
}
