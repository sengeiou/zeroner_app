package com.holtek.libHTBodyfat;

import com.github.mikephil.charting.utils.Utils;
import java.util.Hashtable;

public class HTPeopleGeneral {
    public int htAge;
    public double htBMI;
    public Hashtable<String, String> htBMIRatingList = new Hashtable<>();
    public int htBMR;
    public Hashtable<String, String> htBMRRatingList = new Hashtable<>();
    public int htBodyAge;
    public int htBodyScore;
    public int htBodyType;
    public double htBodyfatKg;
    public double htBodyfatPercentage;
    public Hashtable<String, String> htBodyfatRatingList = new Hashtable<>();
    public double htBoneKg;
    public Hashtable<String, String> htBoneRatingList = new Hashtable<>();
    public double htHeightCm;
    public double htIdealWeightKg;
    public double htMuscleKg;
    public double htMusclePercentage;
    public Hashtable<String, String> htMuscleRatingList = new Hashtable<>();
    public double htProteinPercentage;
    public Hashtable<String, String> htProteinRatingList = new Hashtable<>();
    public int htSex;
    public int htVFAL;
    public Hashtable<String, String> htVFALRatingList = new Hashtable<>();
    public double htWaterPercentage;
    public Hashtable<String, String> htWaterRatingList = new Hashtable<>();
    public double htWeightKg;
    public double htZTwoLegs;
    private int impedance;

    public HTPeopleGeneral(double weightKg, double heightCm, int sex, int age, int impedance2) {
        this.htWeightKg = weightKg;
        this.htHeightCm = heightCm;
        this.htAge = age;
        this.htSex = sex;
        this.impedance = impedance2;
    }

    public int getBodyfatForPeople(double weightKg, double heightCm, int sex, int age, int impedance2) {
        this.htWeightKg = weightKg;
        this.htHeightCm = heightCm;
        this.htAge = age;
        this.htSex = sex;
        this.impedance = impedance2;
        return getBodyfatParameters();
    }

    public int getBodyfatParameters() {
        int errorType = HTBodyfat.NN(this.htWeightKg, this.htHeightCm, this.htAge, this.htSex, this.impedance);
        if (errorType == 0) {
            this.htBodyfatPercentage = HTBodyfat.CC();
            this.htWaterPercentage = HTBodyfat.HH();
            this.htBoneKg = HTBodyfat.DD();
            this.htMuscleKg = HTBodyfat.FF();
            this.htVFAL = (int) HTBodyfat.GG();
            this.htBMR = (int) HTBodyfat.BB();
            this.htBMI = HTBodyfat.EE();
            this.htIdealWeightKg = HTBodyfat.QQ();
            this.htBodyAge = HTBodyfat.RR();
            this.htProteinPercentage = HTBodyfat.SS();
            double[] bmiList = HTBodyfat.II();
            this.htBMIRatingList.put(HTDataType.Standard3LevelA, String.valueOf(bmiList[0]));
            this.htBMIRatingList.put(HTDataType.Standard3LevelB, String.valueOf(bmiList[1]));
            this.htBMIRatingList.put("偏胖-肥胖", String.valueOf(bmiList[2]));
            double[] bmrList = HTBodyfat.JJ();
            this.htBMRRatingList = new Hashtable<>();
            this.htBMRRatingList.put(HTDataType.Standard1LevelA, String.valueOf(bmrList[0]));
            double[] bfList = HTBodyfat.KK();
            this.htBodyfatRatingList.put(HTDataType.Standard4LevelA, String.valueOf(bfList[0]));
            this.htBodyfatRatingList.put("标准-警惕", String.valueOf(bfList[1]));
            this.htBodyfatRatingList.put(HTDataType.Standard4LevelC, String.valueOf(bfList[2]));
            this.htBodyfatRatingList.put("偏胖-肥胖", String.valueOf(bfList[3]));
            double[] boneList = HTBodyfat.LL();
            this.htBoneRatingList.put(HTDataType.Standard2aLevelA, String.valueOf(boneList[0]));
            this.htBoneRatingList.put(HTDataType.Standard2aLevelB, String.valueOf(boneList[1]));
            double[] muscleList = HTBodyfat.MM();
            this.htMuscleRatingList.put(HTDataType.Standard2aLevelA, String.valueOf(muscleList[0]));
            this.htMuscleRatingList.put(HTDataType.Standard2aLevelB, String.valueOf(muscleList[1]));
            double[] vfalList = HTBodyfat.OO();
            this.htVFALRatingList.put("标准-警惕", String.valueOf(vfalList[0]));
            this.htVFALRatingList.put(HTDataType.Standard2bLevelB, String.valueOf(vfalList[1]));
            double[] waterList = HTBodyfat.PP();
            this.htWaterRatingList.put(HTDataType.Standard2aLevelA, String.valueOf(waterList[0]));
            this.htWaterRatingList.put(HTDataType.Standard2aLevelB, String.valueOf(waterList[1]));
            double[] proteinList = HTBodyfat.TT();
            this.htProteinRatingList.put(HTDataType.Standard2aLevelA, String.valueOf(proteinList[0]));
            this.htProteinRatingList.put(HTDataType.Standard2aLevelB, String.valueOf(proteinList[1]));
            this.htBodyType = HTBodyfat.UU();
            this.htBodyScore = HTBodyfat.VV();
            this.htMusclePercentage = HTBodyfat.WW();
            this.htBodyfatKg = HTBodyfat.XX();
        } else {
            if (errorType == 4 || errorType == 3) {
                this.htBMI = Utils.DOUBLE_EPSILON;
            } else {
                this.htBMI = HTBodyfat.EE();
                this.htIdealWeightKg = HTBodyfat.QQ();
            }
            this.htVFAL = 0;
            this.htBodyfatPercentage = Utils.DOUBLE_EPSILON;
            this.htWaterPercentage = Utils.DOUBLE_EPSILON;
            this.htMuscleKg = Utils.DOUBLE_EPSILON;
            this.htBoneKg = Utils.DOUBLE_EPSILON;
            this.htBMR = 0;
            this.htBodyAge = 0;
            this.htProteinPercentage = Utils.DOUBLE_EPSILON;
            this.htBodyType = 0;
            this.htBodyScore = 0;
            this.htMusclePercentage = Utils.DOUBLE_EPSILON;
            this.htBodyfatKg = Utils.DOUBLE_EPSILON;
        }
        this.htZTwoLegs = HTBodyfat.AA();
        return errorType;
    }
}
