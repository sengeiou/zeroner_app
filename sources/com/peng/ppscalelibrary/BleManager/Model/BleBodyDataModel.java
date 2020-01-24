package com.peng.ppscalelibrary.BleManager.Model;

import com.holtek.libHTBodyfat.HTPeopleGeneral;

public class BleBodyDataModel extends HTPeopleGeneral {
    public double weightKg = this.htWeightKg;

    public BleBodyDataModel(double weightKg2, double heightCm, int sex, int age, int impedance) {
        super(weightKg2, heightCm, sex, age, impedance);
        getBodyfatParameters();
    }
}
