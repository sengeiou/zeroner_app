package com.iwown.ble_module.zg_ble.data.model;

public class WelcomeBloodData {
    private BloodPressure blood;
    private int gender;
    private int height;
    private int old8D;
    private int timeZone;
    private String welcome;

    public static class BloodPressure {
        private int dstDbp_HB;
        private int dstDbp_LB;
        private int dstSbp_HB;
        private int dstSbp_LB;
        private int srcDbp_HB;
        private int srcDbp_LB;
        private int srcSbp_HB;
        private int srcSbp_LB;

        public int getSrcSbp_LB() {
            return this.srcSbp_LB;
        }

        public void setSrcSbp_LB(int srcSbp_LB2) {
            this.srcSbp_LB = srcSbp_LB2;
        }

        public int getSrcSbp_HB() {
            return this.srcSbp_HB;
        }

        public void setSrcSbp_HB(int srcSbp_HB2) {
            this.srcSbp_HB = srcSbp_HB2;
        }

        public int getSrcDbp_LB() {
            return this.srcDbp_LB;
        }

        public void setSrcDbp_LB(int srcDbp_LB2) {
            this.srcDbp_LB = srcDbp_LB2;
        }

        public int getSrcDbp_HB() {
            return this.srcDbp_HB;
        }

        public void setSrcDbp_HB(int srcDbp_HB2) {
            this.srcDbp_HB = srcDbp_HB2;
        }

        public int getDstSbp_LB() {
            return this.dstSbp_LB;
        }

        public void setDstSbp_LB(int dstSbp_LB2) {
            this.dstSbp_LB = dstSbp_LB2;
        }

        public int getDstSbp_HB() {
            return this.dstSbp_HB;
        }

        public void setDstSbp_HB(int dstSbp_HB2) {
            this.dstSbp_HB = dstSbp_HB2;
        }

        public int getDstDbp_LB() {
            return this.dstDbp_LB;
        }

        public void setDstDbp_LB(int dstDbp_LB2) {
            this.dstDbp_LB = dstDbp_LB2;
        }

        public int getDstDbp_HB() {
            return this.dstDbp_HB;
        }

        public void setDstDbp_HB(int dstDbp_HB2) {
            this.dstDbp_HB = dstDbp_HB2;
        }
    }

    public int getOld8D() {
        return this.old8D;
    }

    public void setOld8D(int old8D2) {
        this.old8D = old8D2;
    }

    public String getWelcome() {
        return this.welcome;
    }

    public void setWelcome(String welcome2) {
        this.welcome = welcome2;
    }

    public int getTimeZone() {
        return this.timeZone;
    }

    public void setTimeZone(int timeZone2) {
        this.timeZone = timeZone2;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height2) {
        this.height = height2;
    }

    public int getGender() {
        return this.gender;
    }

    public void setGender(int gender2) {
        this.gender = gender2;
    }

    public BloodPressure getBlood() {
        return this.blood;
    }

    public void setBlood(BloodPressure blood2) {
        this.blood = blood2;
    }

    public void setAllBloodPressure(int[] bloods) {
        if (bloods != null && bloods.length >= 8) {
            if (this.blood == null) {
                this.blood = new BloodPressure();
            }
            this.blood.setSrcSbp_LB(bloods[0]);
            this.blood.setSrcSbp_HB(bloods[1]);
            this.blood.setSrcDbp_LB(bloods[2]);
            this.blood.setSrcDbp_HB(bloods[3]);
            this.blood.setDstSbp_LB(bloods[4]);
            this.blood.setDstSbp_HB(bloods[5]);
            this.blood.setDstDbp_LB(bloods[6]);
            this.blood.setDstDbp_HB(bloods[7]);
        }
    }

    public void setUserTimeZone(int[] userTime) {
        this.gender = userTime[0];
        this.height = userTime[1];
        this.timeZone = userTime[2];
    }
}
