package com.iwown.ble_module.proto.model;

import com.iwown.ble_module.utils.JsonTool;
import com.iwown.ble_module.utils.Util;
import java.util.Calendar;
import java.util.List;

public class ProtoBufHisHealthData extends ProtoBufParent {
    private Bp bp;
    private int day;
    private HeartRate heartRate;
    private int hour;
    private HRV hrv;
    private int minute;
    private int month;
    private Pedo pedo;
    private int second;
    private int seq;
    private Sleep sleep;
    private int time;
    private int year;

    public static class Bp {
        private int dbp;
        private int sbp;
        private int time;

        public int getSbp() {
            return this.sbp;
        }

        public void setSbp(int sbp2) {
            this.sbp = sbp2;
        }

        public int getDbp() {
            return this.dbp;
        }

        public void setDbp(int dbp2) {
            this.dbp = dbp2;
        }

        public int getTime() {
            return this.time;
        }

        public void setTime(int time2) {
            this.time = time2;
        }
    }

    public static class HRV {
        private float MEAN;
        private float PNN50;
        private float RMSSD;
        private float SDNN;
        private float fatigue;

        public float getSDNN() {
            return this.SDNN;
        }

        public void setSDNN(float SDNN2) {
            this.SDNN = SDNN2;
        }

        public float getRMSSD() {
            return this.RMSSD;
        }

        public void setRMSSD(float RMSSD2) {
            this.RMSSD = RMSSD2;
        }

        public float getPNN50() {
            return this.PNN50;
        }

        public void setPNN50(float PNN502) {
            this.PNN50 = PNN502;
        }

        public float getMEAN() {
            return this.MEAN;
        }

        public void setMEAN(float MEAN2) {
            this.MEAN = MEAN2;
        }

        public float getFatigue() {
            return this.fatigue;
        }

        public void setFatigue(float fatigue2) {
            this.fatigue = fatigue2;
        }
    }

    public static class HeartRate {
        private int avg_bpm;
        private int max_bpm;
        private int min_bpm;

        public int getMin_bpm() {
            return this.min_bpm;
        }

        public void setMin_bpm(int min_bpm2) {
            this.min_bpm = min_bpm2;
        }

        public int getMax_bpm() {
            return this.max_bpm;
        }

        public void setMax_bpm(int max_bpm2) {
            this.max_bpm = max_bpm2;
        }

        public int getAvg_bpm() {
            return this.avg_bpm;
        }

        public void setAvg_bpm(int avg_bpm2) {
            this.avg_bpm = avg_bpm2;
        }
    }

    public static class Pedo {
        private int calorie;
        private int distance;
        private int state;
        private int step;
        private int type;

        public int getType() {
            return this.type;
        }

        public void setType(int type2) {
            this.type = type2;
        }

        public int getState() {
            return this.state;
        }

        public void setState(int state2) {
            this.state = state2;
        }

        public int getCalorie() {
            return this.calorie;
        }

        public void setCalorie(int calorie2) {
            this.calorie = calorie2;
        }

        public int getStep() {
            return this.step;
        }

        public void setStep(int step2) {
            this.step = step2;
        }

        public int getDistance() {
            return this.distance;
        }

        public void setDistance(int distance2) {
            this.distance = distance2;
        }
    }

    public static class Sleep {
        private boolean charge;
        private boolean shutdown;
        private int[] sleepData;

        public int[] getSleepData() {
            return this.sleepData;
        }

        public void setSleepData(int[] sleepData2) {
            this.sleepData = sleepData2;
        }

        public boolean isCharge() {
            return this.charge;
        }

        public void setCharge(boolean charge2) {
            this.charge = charge2;
        }

        public boolean isShutdown() {
            return this.shutdown;
        }

        public void setShutdown(boolean shutdown2) {
            this.shutdown = shutdown2;
        }

        public int[] parseData(List<Integer> list) {
            int[] a = new int[list.size()];
            for (int i = 0; i < list.size(); i++) {
                a[i] = ((Integer) list.get(i)).intValue();
            }
            return a;
        }

        public String toString() {
            return JsonTool.toJson(this.sleepData);
        }
    }

    public int[] parseTime(long timeStamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis((1000 * timeStamp) - ((long) ((Util.getTimeZone() * 3600) * 1000)));
        return new int[]{calendar.get(1), calendar.get(2) + 1, calendar.get(5), calendar.get(11), calendar.get(12), calendar.get(13)};
    }

    public Sleep getSleep() {
        return this.sleep;
    }

    public void setSleep(Sleep sleep2) {
        this.sleep = sleep2;
    }

    public Pedo getPedo() {
        return this.pedo;
    }

    public void setPedo(Pedo pedo2) {
        this.pedo = pedo2;
    }

    public HeartRate getHeartRate() {
        return this.heartRate;
    }

    public void setHeartRate(HeartRate heartRate2) {
        this.heartRate = heartRate2;
    }

    public HRV getHrv() {
        return this.hrv;
    }

    public void setHrv(HRV hrv2) {
        this.hrv = hrv2;
    }

    public int getHisDataType() {
        return this.hisDataType;
    }

    public void setHisDataType(int hisDataType) {
        this.hisDataType = hisDataType;
    }

    public int getHisDataCase() {
        return this.hisDataCase;
    }

    public void setHisDataCase(int hisDataCase) {
        this.hisDataCase = hisDataCase;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year2) {
        this.year = year2;
    }

    public int getMonth() {
        return this.month;
    }

    public void setMonth(int month2) {
        this.month = month2;
    }

    public int getDay() {
        return this.day;
    }

    public void setDay(int day2) {
        this.day = day2;
    }

    public int getHour() {
        return this.hour;
    }

    public void setHour(int hour2) {
        this.hour = hour2;
    }

    public int getMinute() {
        return this.minute;
    }

    public void setMinute(int minute2) {
        this.minute = minute2;
    }

    public int getSeq() {
        return this.seq;
    }

    public void setSeq(int seq2) {
        this.seq = seq2;
    }

    public Bp getBp() {
        return this.bp;
    }

    public void setBp(Bp bp2) {
        this.bp = bp2;
    }

    public int getSecond() {
        return this.second;
    }

    public void setSecond(int second2) {
        this.second = second2;
    }

    public int getTime() {
        return this.time;
    }

    public void setTime(int time2) {
        this.time = time2;
    }
}
