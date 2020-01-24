package com.iwown.my_module.data;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

public class UserInfoEntity extends DataSupport {
    private String birthday;
    private int gender;
    private float height;
    private String location;
    private String nickname;
    private String portraitImgStr;
    private String portrait_url;
    private String register_date;
    @Column(unique = true)
    private long uid;
    private float weight;

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

    public String getPortraitImgStr() {
        return this.portraitImgStr;
    }

    public void setPortraitImgStr(String portraitImgStr2) {
        this.portraitImgStr = portraitImgStr2;
    }

    public String toString() {
        return "UserInfoEntity{uid=" + this.uid + ", nickname='" + this.nickname + '\'' + ", gender=" + this.gender + ", birthday='" + this.birthday + '\'' + ", portrait_url='" + this.portrait_url + '\'' + ", height=" + this.height + ", weight=" + this.weight + ", location='" + this.location + '\'' + ", register_date='" + this.register_date + '\'' + ", portraitImgStr='" + this.portraitImgStr + '\'' + '}';
    }
}
