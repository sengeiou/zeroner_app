package com.iwown.my_module.model.response;

public class UserInfo {
    private String bind_email;
    private long bind_phone;
    private String birthday;
    private int cardio;
    private int daily_activity;
    private int gender;
    private float height;
    private String location;
    private String nickname;
    private int physical;
    private String portrait_url;
    private String register_date;
    private int retCode;
    private long uid;
    private float weight;
    private int working_type;

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

    public String getBirthday() {
        return this.birthday;
    }

    public void setBirthday(String birthday2) {
        this.birthday = birthday2;
    }

    public String getPortrait_url() {
        return this.portrait_url;
    }

    public void setPortrait_url(String portrait_url2) {
        this.portrait_url = portrait_url2;
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

    public int getWorking_type() {
        return this.working_type;
    }

    public void setWorking_type(int working_type2) {
        this.working_type = working_type2;
    }

    public int getPhysical() {
        return this.physical;
    }

    public void setPhysical(int physical2) {
        this.physical = physical2;
    }

    public long getBind_phone() {
        return this.bind_phone;
    }

    public void setBind_phone(long bind_phone2) {
        this.bind_phone = bind_phone2;
    }

    public String getBind_email() {
        return this.bind_email;
    }

    public void setBind_email(String bind_email2) {
        this.bind_email = bind_email2;
    }

    public int getRetCode() {
        return this.retCode;
    }

    public void setRetCode(int retCode2) {
        this.retCode = retCode2;
    }

    public int getCardio() {
        return this.cardio;
    }

    public void setCardio(int cardio2) {
        this.cardio = cardio2;
    }

    public int getDaily_activity() {
        return this.daily_activity;
    }

    public void setDaily_activity(int daily_activity2) {
        this.daily_activity = daily_activity2;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location2) {
        this.location = location2;
    }

    public String getRegister_date() {
        return this.register_date;
    }

    public void setRegister_date(String register_date2) {
        this.register_date = register_date2;
    }
}
