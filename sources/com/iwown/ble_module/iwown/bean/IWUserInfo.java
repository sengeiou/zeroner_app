package com.iwown.ble_module.iwown.bean;

import com.iwown.ble_module.iwown.utils.ByteUtil;
import java.util.Arrays;

public class IWUserInfo {
    private int age;
    private int gender;
    private int height;
    private int target;
    private int weight;

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height2) {
        this.height = height2;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int weight2) {
        this.weight = weight2;
    }

    public int getGender() {
        return this.gender;
    }

    public void setGender(int gender2) {
        this.gender = gender2;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age2) {
        this.age = age2;
    }

    public int getTarget() {
        return this.target;
    }

    public void setTarget(int target2) {
        this.target = target2;
    }

    public void parseData(byte[] datas) {
        setHeight(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 4, 5)));
        setWeight(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 5, 6)));
        setGender(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 6, 7)));
        setAge(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 7, 8)));
        setTarget(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 8, 10)));
    }
}
