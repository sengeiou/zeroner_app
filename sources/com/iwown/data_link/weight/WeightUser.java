package com.iwown.data_link.weight;

public class WeightUser {
    private int age;
    private float height;
    private boolean isMale;
    private String name;
    private long uid;

    public int getAge() {
        return this.age;
    }

    public void setAge(int age2) {
        this.age = age2;
    }

    public boolean isMale() {
        return this.isMale;
    }

    public void setMale(boolean male) {
        this.isMale = male;
    }

    public float getHeight() {
        return this.height;
    }

    public void setHeight(float height2) {
        this.height = height2;
    }

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }
}
