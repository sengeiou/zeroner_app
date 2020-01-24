package com.iwown.sport_module.pojo.data;

import com.tencent.tinker.android.dx.instruction.Opcodes;

public class BaseTraningItem {
    public static int DATA_TYPE_HISTORY = 0;
    public static int DATA_TYPE_TODAY = 1;
    public static final int SPORT_TYPE_BALL = 4;
    public static int SPORT_TYPE_BALLS = 3;
    public static int SPORT_TYPE_DS = 5;
    public static final int SPORT_TYPE_MOUNTAIN = 5;
    public static int SPORT_TYPE_OTHERS = 5;
    public static int SPORT_TYPE_R1_OTHER = 3;
    public static int SPORT_TYPE_RIDE = Opcodes.FLOAT_TO_LONG;
    public static int SPORT_TYPE_RIDE_GPS = 1;
    public static int SPORT_TYPE_RUN = 7;
    public static int SPORT_TYPE_RUN_GPS = 0;
    public static int SPORT_TYPE_SPEED_WALK = Opcodes.DIV_INT;
    public static int SPORT_TYPE_SPEED_WALK_GPS = 2;
    public static final int SPORT_TYPE_SWIM = 6;
    public static int SPORT_TYPE_WATERS = 4;
    public static int UI_TYPE_HISTORY_2_Layout = 0;
    public static int UI_TYPE_HISTORY_RIDE_Layout = 1;
    public static int UI_TYPE_TODAY_AF = 106;
    public static int UI_TYPE_TODAY_Blood = 105;
    public static int UI_TYPE_TODAY_ECG = 104;
    public static int UI_TYPE_TODAY_Fatigue = 103;
    public static int UI_TYPE_TODAY_Heart = 102;
    public static int UI_TYPE_TODAY_INFOS = 2;
    public static int UI_TYPE_TODAY_Sleep = 101;
    public static int UI_TYPE_TODAY_Weight = 100;
    protected int data_type = -1;
    protected int sport_type = -1;

    public int getData_type() {
        return this.data_type;
    }

    public void setData_type(int data_type2) {
        this.data_type = data_type2;
    }

    public int getSport_type() {
        return this.sport_type;
    }

    public void setSport_type(int sport_type2) {
        this.sport_type = sport_type2;
    }
}
