package com.iwown.data_link.user_pre;

import com.github.mikephil.charting.utils.Utils;
import java.util.Calendar;
import org.apache.commons.cli.HelpFormatter;

public class UserInfo {
    public static int GENDER_FEMALE = 1;
    public static int GENDER_MALE = 0;
    public static int MEASURE_UNIT_Imperial = 1;
    public static int MEASURE_UNIT_Metric = 0;
    public static int TEMPERATURE_UNIT_Centigrade = 0;
    public static int TEMPERATURE_UNIT_Fahrenheit = 1;
    public int age = 25;
    public String birthday = "1995-01-01";
    public double height = 175.0d;
    public boolean isCentigrade = true;
    public boolean isMale = true;
    public boolean isMertric = true;
    public String nickName;
    public float target_cal = 320.0f;
    public int target_stand_hours = 12;
    public int target_step = 10000;
    public float target_weight = 0.0f;
    public long uid;
    public double weight = 70.0d;

    public void setBirthday(String birthday2) {
        this.birthday = birthday2;
        getAge();
    }

    public int getAge() {
        this.age = getBirthdayAge(this.birthday);
        return this.age;
    }

    public static int getBirthdayAge(String birthday2) {
        try {
            String[] ages = birthday2.split(HelpFormatter.DEFAULT_OPT_PREFIX);
            Calendar c = Calendar.getInstance();
            int curYear = c.get(1);
            int curMonth = c.get(2) + 1;
            int curDay = c.get(5);
            int age2 = curYear - Integer.parseInt(ages[0]);
            if (curMonth < Integer.parseInt(ages[1])) {
                age2--;
            } else if (curMonth == Integer.parseInt(ages[1]) && curDay < Integer.parseInt(ages[2])) {
                age2--;
            }
            if (age2 <= 0 || age2 > 150) {
                return 0;
            }
            return age2;
        } catch (Exception e) {
            return 25;
        }
    }

    public float getGoalCaloria() {
        boolean isLoses = false;
        if (this.target_weight > 0.0f && this.weight - ((double) this.target_weight) > Utils.DOUBLE_EPSILON) {
            isLoses = true;
        }
        if (!isLoses) {
            if (this.isMale) {
                this.target_cal = (float) ((int) Math.round((((66.0d + (13.7d * this.weight)) + (5.0d * this.height)) - (6.8d * ((double) this.age))) * 1.3d * 0.15d));
            } else {
                this.target_cal = (float) ((int) Math.round((((655.0d + (9.6d * this.weight)) + (1.7d * this.height)) - (4.7d * ((double) this.age))) * 1.3d * 0.15d));
            }
        } else if (this.isMale) {
            this.target_cal = (float) ((int) Math.round(((((66.0d + (13.7d * this.weight)) + (5.0d * this.height)) - (6.8d * ((double) this.age))) * 1.3d * 0.15d) + 256.0d));
        } else {
            this.target_cal = (float) ((int) Math.round(((((655.0d + (9.6d * this.weight)) + (1.7d * this.height)) - (4.7d * ((double) this.age))) * 1.3d * 0.15d) + 256.0d));
        }
        return this.target_cal;
    }

    public String toString() {
        return "UserInfo{uid=" + this.uid + ", target_step=" + this.target_step + ", target_weight=" + this.target_weight + ", target_stand_hours=" + this.target_stand_hours + ", isMertric=" + this.isMertric + ", isMale=" + this.isMale + ", age=" + this.age + ", weight=" + this.weight + ", height=" + this.height + ", birthday='" + this.birthday + '\'' + ", target_cal=" + this.target_cal + ", isCentigrade=" + this.isCentigrade + '}';
    }
}
