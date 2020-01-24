package com.peng.ppscalelibrary.BleManager.Model;

import com.peng.ppscalelibrary.BleManager.Model.BleEnum.BleSex;
import com.peng.ppscalelibrary.BleManager.Model.BleEnum.BleUnit;

public class BleUserModel {
    public int age;
    public int groupNum;
    public BleSex sex;
    public BleUnit unit;
    public int userHeight;

    public BleUserModel(int userHeight2, int age2, BleSex sex2, BleUnit unit2, int groupNum2) {
        this.userHeight = userHeight2;
        this.age = age2;
        this.sex = sex2;
        this.unit = unit2;
        this.groupNum = groupNum2;
    }
}
