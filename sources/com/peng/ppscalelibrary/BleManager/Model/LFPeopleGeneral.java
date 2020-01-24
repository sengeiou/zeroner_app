package com.peng.ppscalelibrary.BleManager.Model;

import com.github.mikephil.charting.utils.Utils;
import com.holtek.libHTBodyfat.HTPeopleGeneral;
import java.util.Hashtable;

public class LFPeopleGeneral {
    private int impedance;
    public int lfAge;
    public double lfBMI;
    public Hashtable<String, String> lfBMIRatingList = new Hashtable<>();
    public int lfBMR;
    public Hashtable<String, String> lfBMRRatingList = new Hashtable<>();
    public int lfBodyAge;
    public double lfBodyMuscleControlKg;
    public int lfBodyScore;
    public int lfBodyType;
    public double lfBodyfatKg;
    public double lfBodyfatPercentage;
    public Hashtable<String, String> lfBodyfatRatingList = new Hashtable<>();
    public double lfBoneKg;
    public double lfBonePercentage;
    public Hashtable<String, String> lfBoneRatingList = new Hashtable<>();
    public double lfControlWeightKg;
    public double lfFatControlKg;
    public String lfFatLevel;
    public String lfHealthLevel;
    public String lfHealthReport;
    public double lfHeightCm;
    private double lfHipLine;
    public double lfIdealWeightKg;
    public double lfLoseFatWeightKg;
    public double lfMuscleKg;
    public double lfMusclePercentage;
    public Hashtable<String, String> lfMuscleRatingList = new Hashtable<>();
    public double lfProteinPercentage;
    public Hashtable<String, String> lfProteinRatingList = new Hashtable<>();
    public int lfSex;
    public double lfStWeightKg;
    public int lfVFAL;
    public Hashtable<String, String> lfVFALRatingList = new Hashtable<>();
    public double lfVFPercentage;
    public double lfWHR;
    private double lfWaist;
    public double lfWaterPercentage;
    public Hashtable<String, String> lfWaterRatingList = new Hashtable<>();
    public double lfWeightKg;
    public double lfZTwoLegs;
    public String scaleName;
    public String scaleType;

    public LFPeopleGeneral() {
    }

    public LFPeopleGeneral(double lfWeightKg2, double lfHeightCm2, int lfSex2, int lfAge2, int impedance2, double lfWaist2, double lfHipLine2, String scaleType2, String scaleName2) {
        this.lfWeightKg = lfWeightKg2;
        this.lfHeightCm = lfHeightCm2;
        this.lfAge = lfAge2;
        this.lfSex = lfSex2;
        this.impedance = impedance2;
        this.lfWaist = lfWaist2;
        this.lfHipLine = lfHipLine2;
        this.scaleName = scaleName2;
        this.scaleType = scaleType2;
        getBodyfatParameters();
    }

    public int getBodyfatParameters() {
        HTPeopleGeneral bodyfat = new HTPeopleGeneral(this.lfWeightKg, this.lfHeightCm, this.lfSex, this.lfAge, this.impedance);
        int errorType = bodyfat.getBodyfatParameters();
        if (errorType == 0) {
            setLfZTwoLegs(bodyfat.htZTwoLegs);
            setLfBodyAge(bodyfat.htBodyAge);
            setLfIdealWeightKg(bodyfat.htIdealWeightKg);
            setLfBMR(bodyfat.htBMR);
            setLfBMI(bodyfat.htBMI);
            setLfVFAL(bodyfat.htVFAL);
            setLfBoneKg(bodyfat.htBoneKg);
            setLfBodyfatPercentage(bodyfat.htBodyfatPercentage);
            setLfWaterPercentage(bodyfat.htWaterPercentage);
            setLfMuscleKg(bodyfat.htMuscleKg);
            setLfProteinPercentage(bodyfat.htProteinPercentage);
            setLfBodyType(bodyfat.htBodyType);
            setLfBodyScore(bodyfat.htBodyScore);
            setLfMusclePercentage(bodyfat.htMusclePercentage);
            setLfBodyfatKg(bodyfat.htBodyfatKg);
            setLfBMIRatingList(bodyfat.htBMIRatingList);
            setLfBMRRatingList(bodyfat.htBMRRatingList);
            setLfVFALRatingList(bodyfat.htVFALRatingList);
            setLfBoneRatingList(bodyfat.htBoneRatingList);
            setLfBodyfatRatingList(bodyfat.htBodyfatRatingList);
            setLfWaterRatingList(bodyfat.htWaterRatingList);
            setLfMuscleRatingList(bodyfat.htMuscleRatingList);
            setLfProteinRatingList(bodyfat.htProteinRatingList);
            setLfStWeightKg(countStandWeightKg());
            setLfLoseFatWeightKg(countLoseFatWeightKg());
            setLfControlWeightKg(countControlWeightKg());
            setLfFatControlKg(countFatControlKg());
            setLfBonePercentage(countBonePercentage());
            setLfBodyMuscleControlKg(countBodyMuscleControlKg());
            setLfVFPercentage(countVFPercentage());
            setLfHealthLevel(countHealthLevel());
            setLfFatLevel(countFatLevel());
            setLfWHR(countHWR());
            setLfHealthReport(countHealthReport());
        } else {
            setLfBMI(bodyfat.htBMI);
        }
        return errorType;
    }

    private double countStandWeightKg() {
        return 21.75d * getLfHeightCm() * 0.01d * getLfHeightCm() * 0.01d;
    }

    private double countLoseFatWeightKg() {
        return getLfWeightKg() * (1.0d - (getLfBodyfatPercentage() / 100.0d));
    }

    private double countControlWeightKg() {
        return getLfWeightKg() - getLfStWeightKg();
    }

    private double countFatControlKg() {
        return getLfSex() == 1 ? calMaleBodyFatControl() : calFemaleBodyFatControl();
    }

    private double countBonePercentage() {
        return getLfSex() == 1 ? (getLfLoseFatWeightKg() * 0.56d) / getLfWeightKg() : (getLfLoseFatWeightKg() * 0.54d) / getLfWeightKg();
    }

    private double countBodyMuscleControlKg() {
        return getLfSex() == 1 ? calMaleBodyMuscleControl() : calFemaleBodyMuscleControl();
    }

    private double countVFPercentage() {
        return getLfBodyfatPercentage() * 0.6666666666666666d;
    }

    private String countHealthLevel() {
        double BMI = getLfBMI();
        if (Utils.DOUBLE_EPSILON <= BMI && BMI < 18.5d) {
            return "偏瘦";
        }
        if (18.6d > BMI || BMI >= 24.9d) {
            return (25.0d > BMI || BMI >= 29.9d) ? "肥胖" : "超重";
        }
        return "标准";
    }

    private String countFatLevel() {
        double BMI = getLfBMI();
        if (30.0d <= BMI && BMI < 35.0d) {
            return "肥胖1级";
        }
        if (35.0d > BMI || BMI >= 40.0d) {
            return (40.0d > BMI || BMI >= 90.0d) ? "不确定" : "肥胖3级";
        }
        return "肥胖2级";
    }

    private double countHWR() {
        if (getLfWaist() == Utils.DOUBLE_EPSILON || getLfHipLine() == Utils.DOUBLE_EPSILON) {
            return -1.0d;
        }
        return getLfWaist() / getLfHipLine();
    }

    private String countHealthReport() {
        int score = getLfBodyScore();
        if (score >= 0 && score <= 60) {
            return "健康存在隐患";
        }
        if (60 < score && score <= 70) {
            return "亚健康";
        }
        if (70 < score && score <= 80) {
            return "一般";
        }
        if (80 >= score || score > 90) {
            return (90 >= score || score > 100) ? "不确定" : "非常好";
        }
        return "良好";
    }

    public double getLfHeightCm() {
        return this.lfHeightCm;
    }

    public double getLfWeightKg() {
        return this.lfWeightKg;
    }

    public void setLfWeightKg(double lfWeightKg2) {
        this.lfWeightKg = lfWeightKg2;
    }

    public double getLfBodyfatPercentage() {
        return this.lfBodyfatPercentage;
    }

    public void setLfBodyfatPercentage(double lfBodyfatPercentage2) {
        this.lfBodyfatPercentage = lfBodyfatPercentage2;
    }

    public double getLfStWeightKg() {
        return this.lfStWeightKg;
    }

    public int getLfSex() {
        return this.lfSex;
    }

    private double calMaleBodyFatControl() {
        double BMI = getLfBMI();
        double FatWeight = getLfBodyfatKg();
        int Age = getLfAge();
        if (BMI <= 22.5d) {
            return FatWeight < 8.5d ? 9.0d - FatWeight : Utils.DOUBLE_EPSILON;
        }
        return (((double) Age) * -0.6282d) + 24.1589d + (-0.5865d * BMI) + ((((double) Age) * 9.8772d) / BMI) + (((-0.368d * BMI) * BMI) / ((double) Age));
    }

    private double calFemaleBodyFatControl() {
        double BMI = getLfBMI();
        double FatWeight = getLfBodyfatKg();
        int Age = getLfAge();
        if (BMI <= 21.0d) {
            return FatWeight < 10.5d ? 10.2d - FatWeight : Utils.DOUBLE_EPSILON;
        }
        return (((double) Age) * 0.6351d) + 67.2037d + (-2.6706d * BMI) + ((((double) Age) * -18.1146d) / BMI) + (((-0.2374d * BMI) * BMI) / ((double) Age));
    }

    public double getLfLoseFatWeightKg() {
        return this.lfLoseFatWeightKg;
    }

    private double calMaleBodyMuscleControl() {
        double BodyMuscleControl = (getLfStWeightKg() * 0.797d) - getLfBodyfatKg();
        return BodyMuscleControl < Utils.DOUBLE_EPSILON ? Utils.DOUBLE_EPSILON : BodyMuscleControl;
    }

    private double calFemaleBodyMuscleControl() {
        double BodyMuscleControl = (getLfStWeightKg() * 0.724d) - getLfBodyfatKg();
        return BodyMuscleControl < Utils.DOUBLE_EPSILON ? Utils.DOUBLE_EPSILON : BodyMuscleControl;
    }

    public double getLfBMI() {
        return this.lfBMI;
    }

    public double getLfWaist() {
        return this.lfWaist;
    }

    public void setLfWaist(double lfWaist2) {
        this.lfWaist = lfWaist2;
    }

    public double getLfHipLine() {
        return this.lfHipLine;
    }

    public void setLfHipLine(double lfHipLine2) {
        this.lfHipLine = lfHipLine2;
    }

    public int getLfBodyScore() {
        return this.lfBodyScore;
    }

    public void setLfBodyScore(int lfBodyScore2) {
        this.lfBodyScore = lfBodyScore2;
    }

    public double getLfBodyfatKg() {
        return this.lfBodyfatKg;
    }

    public int getLfAge() {
        return this.lfAge;
    }

    public void setLfAge(int lfAge2) {
        this.lfAge = lfAge2;
    }

    public void setLfBodyfatKg(double lfBodyfatKg2) {
        this.lfBodyfatKg = lfBodyfatKg2;
    }

    public void setLfBMI(double lfBMI2) {
        this.lfBMI = lfBMI2;
    }

    public void setLfLoseFatWeightKg(double lfLoseFatWeightKg2) {
        this.lfLoseFatWeightKg = lfLoseFatWeightKg2;
    }

    public void setLfSex(int lfSex2) {
        this.lfSex = lfSex2;
    }

    public void setLfStWeightKg(double lfStWeightKg2) {
        this.lfStWeightKg = lfStWeightKg2;
    }

    public void setLfHeightCm(double lfHeightCm2) {
        this.lfHeightCm = lfHeightCm2;
    }

    public int getImpedance() {
        return this.impedance;
    }

    public void setImpedance(int impedance2) {
        this.impedance = impedance2;
    }

    public double getLfZTwoLegs() {
        return this.lfZTwoLegs;
    }

    public void setLfZTwoLegs(double lfZTwoLegs2) {
        this.lfZTwoLegs = lfZTwoLegs2;
    }

    public int getLfBodyAge() {
        return this.lfBodyAge;
    }

    public void setLfBodyAge(int lfBodyAge2) {
        this.lfBodyAge = lfBodyAge2;
    }

    public double getLfIdealWeightKg() {
        return this.lfIdealWeightKg;
    }

    public void setLfIdealWeightKg(double lfIdealWeightKg2) {
        this.lfIdealWeightKg = lfIdealWeightKg2;
    }

    public int getLfBMR() {
        return this.lfBMR;
    }

    public void setLfBMR(int lfBMR2) {
        this.lfBMR = lfBMR2;
    }

    public int getLfVFAL() {
        return this.lfVFAL;
    }

    public void setLfVFAL(int lfVFAL2) {
        this.lfVFAL = lfVFAL2;
    }

    public double getLfBoneKg() {
        return this.lfBoneKg;
    }

    public void setLfBoneKg(double lfBoneKg2) {
        this.lfBoneKg = lfBoneKg2;
    }

    public double getLfWaterPercentage() {
        return this.lfWaterPercentage;
    }

    public void setLfWaterPercentage(double lfWaterPercentage2) {
        this.lfWaterPercentage = lfWaterPercentage2;
    }

    public double getLfMuscleKg() {
        return this.lfMuscleKg;
    }

    public void setLfMuscleKg(double lfMuscleKg2) {
        this.lfMuscleKg = lfMuscleKg2;
    }

    public double getLfProteinPercentage() {
        return this.lfProteinPercentage;
    }

    public void setLfProteinPercentage(double lfProteinPercentage2) {
        this.lfProteinPercentage = lfProteinPercentage2;
    }

    public int getLfBodyType() {
        return this.lfBodyType;
    }

    public void setLfBodyType(int lfBodyType2) {
        this.lfBodyType = lfBodyType2;
    }

    public double getLfMusclePercentage() {
        return this.lfMusclePercentage;
    }

    public void setLfMusclePercentage(double lfMusclePercentage2) {
        this.lfMusclePercentage = lfMusclePercentage2;
    }

    public Hashtable<String, String> getLfBMIRatingList() {
        return this.lfBMIRatingList;
    }

    public void setLfBMIRatingList(Hashtable<String, String> lfBMIRatingList2) {
        this.lfBMIRatingList = lfBMIRatingList2;
    }

    public Hashtable<String, String> getLfBMRRatingList() {
        return this.lfBMRRatingList;
    }

    public void setLfBMRRatingList(Hashtable<String, String> lfBMRRatingList2) {
        this.lfBMRRatingList = lfBMRRatingList2;
    }

    public Hashtable<String, String> getLfVFALRatingList() {
        return this.lfVFALRatingList;
    }

    public void setLfVFALRatingList(Hashtable<String, String> lfVFALRatingList2) {
        this.lfVFALRatingList = lfVFALRatingList2;
    }

    public Hashtable<String, String> getLfBoneRatingList() {
        return this.lfBoneRatingList;
    }

    public void setLfBoneRatingList(Hashtable<String, String> lfBoneRatingList2) {
        this.lfBoneRatingList = lfBoneRatingList2;
    }

    public Hashtable<String, String> getLfBodyfatRatingList() {
        return this.lfBodyfatRatingList;
    }

    public void setLfBodyfatRatingList(Hashtable<String, String> lfBodyfatRatingList2) {
        this.lfBodyfatRatingList = lfBodyfatRatingList2;
    }

    public Hashtable<String, String> getLfWaterRatingList() {
        return this.lfWaterRatingList;
    }

    public void setLfWaterRatingList(Hashtable<String, String> lfWaterRatingList2) {
        this.lfWaterRatingList = lfWaterRatingList2;
    }

    public Hashtable<String, String> getLfMuscleRatingList() {
        return this.lfMuscleRatingList;
    }

    public void setLfMuscleRatingList(Hashtable<String, String> lfMuscleRatingList2) {
        this.lfMuscleRatingList = lfMuscleRatingList2;
    }

    public Hashtable<String, String> getLfProteinRatingList() {
        return this.lfProteinRatingList;
    }

    public void setLfProteinRatingList(Hashtable<String, String> lfProteinRatingList2) {
        this.lfProteinRatingList = lfProteinRatingList2;
    }

    public double getLfControlWeightKg() {
        return this.lfControlWeightKg;
    }

    public void setLfControlWeightKg(double lfControlWeightKg2) {
        this.lfControlWeightKg = lfControlWeightKg2;
    }

    public double getLfFatControlKg() {
        return this.lfFatControlKg;
    }

    public void setLfFatControlKg(double lfFatControlKg2) {
        this.lfFatControlKg = lfFatControlKg2;
    }

    public double getLfBonePercentage() {
        return this.lfBonePercentage;
    }

    public void setLfBonePercentage(double lfBonePercentage2) {
        this.lfBonePercentage = lfBonePercentage2;
    }

    public double getLfBodyMuscleControlKg() {
        return this.lfBodyMuscleControlKg;
    }

    public void setLfBodyMuscleControlKg(double lfBodyMuscleControlKg2) {
        this.lfBodyMuscleControlKg = lfBodyMuscleControlKg2;
    }

    public double getLfVFPercentage() {
        return this.lfVFPercentage;
    }

    public void setLfVFPercentage(double lfVFPercentage2) {
        this.lfVFPercentage = lfVFPercentage2;
    }

    public String getLfHealthLevel() {
        return this.lfHealthLevel;
    }

    public void setLfHealthLevel(String lfHealthLevel2) {
        this.lfHealthLevel = lfHealthLevel2;
    }

    public String getLfFatLevel() {
        return this.lfFatLevel;
    }

    public void setLfFatLevel(String lfFatLevel2) {
        this.lfFatLevel = lfFatLevel2;
    }

    public double getLfWHR() {
        return this.lfWHR;
    }

    public void setLfWHR(double lfWHR2) {
        this.lfWHR = lfWHR2;
    }

    public String getLfHealthReport() {
        return this.lfHealthReport;
    }

    public void setLfHealthReport(String lfHealthReport2) {
        this.lfHealthReport = lfHealthReport2;
    }

    public String getScaleType() {
        return this.scaleType;
    }

    public void setScaleType(String scaleType2) {
        this.scaleType = scaleType2;
    }

    public String getScaleName() {
        return this.scaleName;
    }

    public void setScaleName(String scaleName2) {
        this.scaleName = scaleName2;
    }

    public String toString() {
        return "LFPeopleGeneral{lfWeightKg=" + this.lfWeightKg + ", lfHeightCm=" + this.lfHeightCm + ", lfAge=" + this.lfAge + ", lfSex=" + this.lfSex + ", lfZTwoLegs=" + this.lfZTwoLegs + ", lfBodyAge=" + this.lfBodyAge + ", lfIdealWeightKg=" + this.lfIdealWeightKg + ", lfBMI=" + this.lfBMI + ", lfBMR=" + this.lfBMR + ", lfVFAL=" + this.lfVFAL + ", lfBoneKg=" + this.lfBoneKg + ", lfBodyfatPercentage=" + this.lfBodyfatPercentage + ", lfWaterPercentage=" + this.lfWaterPercentage + ", lfMuscleKg=" + this.lfMuscleKg + ", lfProteinPercentage=" + this.lfProteinPercentage + ", lfBodyType=" + this.lfBodyType + ", lfBodyScore=" + this.lfBodyScore + ", lfMusclePercentage=" + this.lfMusclePercentage + ", lfBodyfatKg=" + this.lfBodyfatKg + ", lfBMIRatingList=" + this.lfBMIRatingList + ", lfBMRRatingList=" + this.lfBMRRatingList + ", lfVFALRatingList=" + this.lfVFALRatingList + ", lfBoneRatingList=" + this.lfBoneRatingList + ", lfBodyfatRatingList=" + this.lfBodyfatRatingList + ", lfWaterRatingList=" + this.lfWaterRatingList + ", lfMuscleRatingList=" + this.lfMuscleRatingList + ", lfProteinRatingList=" + this.lfProteinRatingList + ", lfStWeightKg=" + this.lfStWeightKg + ", lfLoseFatWeightKg=" + this.lfLoseFatWeightKg + ", lfControlWeightKg=" + this.lfControlWeightKg + ", lfFatControlKg=" + this.lfFatControlKg + ", lfBonePercentage=" + this.lfBonePercentage + ", lfBodyMuscleControlKg=" + this.lfBodyMuscleControlKg + ", lfVFPercentage=" + this.lfVFPercentage + ", lfHealthLevel='" + this.lfHealthLevel + '\'' + ", lfFatLevel='" + this.lfFatLevel + '\'' + ", lfWHR=" + this.lfWHR + ", lfHealthReport='" + this.lfHealthReport + '\'' + ", scaleType='" + this.scaleType + '\'' + ", scaleName='" + this.scaleName + '\'' + ", impedance=" + this.impedance + ", lfWaist=" + this.lfWaist + ", lfHipLine=" + this.lfHipLine + '}';
    }
}
