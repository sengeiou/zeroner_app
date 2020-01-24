package com.iwown.device_module.common.network.data.req;

public class FamilyNoAccountAddRequest {
    private String birthday;
    private long familyUid;
    private int gender;
    private float height;
    private String relation;
    private long uid;

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public String getRelation() {
        return this.relation;
    }

    public void setRelation(String relation2) {
        this.relation = relation2;
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

    public String getBirthday() {
        return this.birthday;
    }

    public void setBirthday(String birthday2) {
        this.birthday = birthday2;
    }

    public long getFamilyUid() {
        return this.familyUid;
    }

    public void setFamilyUid(long familyUid2) {
        this.familyUid = familyUid2;
    }
}
