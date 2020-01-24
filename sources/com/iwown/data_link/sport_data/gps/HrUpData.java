package com.iwown.data_link.sport_data.gps;

import java.util.List;

public class HrUpData {
    private int ag;
    private Hr51 h1;
    private List<Integer> h3;
    private int[] sf;

    public static class Hr51 {
        private int r0;
        private int r1;
        private int r2;
        private int r3;
        private int r4;
        private int r5;

        public int getR0() {
            return this.r0;
        }

        public void setR0(int r02) {
            this.r0 = r02;
        }

        public int getR1() {
            return this.r1;
        }

        public void setR1(int r12) {
            this.r1 = r12;
        }

        public int getR2() {
            return this.r2;
        }

        public void setR2(int r22) {
            this.r2 = r22;
        }

        public int getR3() {
            return this.r3;
        }

        public void setR3(int r32) {
            this.r3 = r32;
        }

        public int getR4() {
            return this.r4;
        }

        public void setR4(int r42) {
            this.r4 = r42;
        }

        public int getR5() {
            return this.r5;
        }

        public void setR5(int r52) {
            this.r5 = r52;
        }
    }

    public int[] getSf() {
        return this.sf;
    }

    public void setSf(int[] sf2) {
        this.sf = sf2;
    }

    public List<Integer> getH3() {
        return this.h3;
    }

    public void setH3(List<Integer> h32) {
        this.h3 = h32;
    }

    public int getAg() {
        return this.ag;
    }

    public void setAg(int ag2) {
        this.ag = ag2;
    }

    public Hr51 getH1() {
        return this.h1;
    }

    public void setH1(Hr51 h12) {
        this.h1 = h12;
    }
}
