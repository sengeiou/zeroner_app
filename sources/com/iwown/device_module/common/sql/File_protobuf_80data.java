package com.iwown.device_module.common.sql;

import com.alibaba.json.annotation.JSONField;

public class File_protobuf_80data {
    private Sleep E;
    private HeartRate H;
    private Pedo P;
    private int Q;
    private int[] T;
    private HRV V;

    public static class HRV {
        private float f;
        private float m;
        private float p;
        private float r;
        private float s;

        public float getS() {
            return this.s;
        }

        public void setS(float s2) {
            this.s = s2;
        }

        public float getR() {
            return this.r;
        }

        public void setR(float r2) {
            this.r = r2;
        }

        public float getP() {
            return this.p;
        }

        public void setP(float p2) {
            this.p = p2;
        }

        public float getM() {
            return this.m;
        }

        public void setM(float m2) {
            this.m = m2;
        }

        public float getF() {
            return this.f;
        }

        public void setF(float f2) {
            this.f = f2;
        }
    }

    public static class HeartRate {
        private int a;
        private int n;
        private int x;

        public int getN() {
            return this.n;
        }

        public void setN(int n2) {
            this.n = n2;
        }

        public int getX() {
            return this.x;
        }

        public void setX(int x2) {
            this.x = x2;
        }

        public int getA() {
            return this.a;
        }

        public void setA(int a2) {
            this.a = a2;
        }
    }

    public static class Pedo {
        private int a;
        private float c;
        private int d;
        private int s;
        private int t;

        public int getT() {
            return this.t;
        }

        public void setT(int t2) {
            this.t = t2;
        }

        public int getA() {
            return this.a;
        }

        public void setA(int a2) {
            this.a = a2;
        }

        public float getC() {
            return this.c;
        }

        public void setC(float c2) {
            this.c = c2;
        }

        public int getS() {
            return this.s;
        }

        public void setS(int s2) {
            this.s = s2;
        }

        public int getD() {
            return this.d;
        }

        public void setD(int d2) {
            this.d = d2;
        }
    }

    public static class Sleep {
        private int[] a;
        private int c;
        private int s;

        public int[] getA() {
            return this.a;
        }

        public void setA(int[] a2) {
            this.a = a2;
        }

        public int getC() {
            return this.c;
        }

        public void setC(int c2) {
            this.c = c2;
        }

        public int getS() {
            return this.s;
        }

        public void setS(int s2) {
            this.s = s2;
        }
    }

    public int[] parseTime(int hour, int minute) {
        return new int[]{hour, minute};
    }

    @JSONField(name = "Q")
    public int getQ() {
        return this.Q;
    }

    public void setQ(int q) {
        this.Q = q;
    }

    @JSONField(name = "T")
    public int[] getT() {
        return this.T;
    }

    public void setT(int[] t) {
        this.T = t;
    }

    @JSONField(name = "E")
    public Sleep getE() {
        return this.E;
    }

    public void setE(Sleep e) {
        this.E = e;
    }

    @JSONField(name = "P")
    public Pedo getP() {
        return this.P;
    }

    public void setP(Pedo p) {
        this.P = p;
    }

    @JSONField(name = "H")
    public HeartRate getH() {
        return this.H;
    }

    public void setH(HeartRate h) {
        this.H = h;
    }

    @JSONField(name = "V")
    public HRV getV() {
        return this.V;
    }

    public void setV(HRV v) {
        this.V = v;
    }
}
