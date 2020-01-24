package com.iwown.device_module.common.network.data.resp;

import java.util.List;

public class F1SleepData extends RetCode {
    private DataBean data;
    private int data_type;

    public static class DataBean {
        private int c;
        private List<SleepdataBean> d;
        private F1SleepTimeBean i;
        private F1SleepTimeBean o;
        private int s;
        private int t;

        public static class SleepdataBean {
            private F1SleepTimeBean a;
            private int m;
            private F1SleepTimeBean o;

            public F1SleepTimeBean getA() {
                return this.a;
            }

            public void setA(F1SleepTimeBean a2) {
                this.a = a2;
            }

            public F1SleepTimeBean getO() {
                return this.o;
            }

            public void setO(F1SleepTimeBean o2) {
                this.o = o2;
            }

            public int getM() {
                return this.m;
            }

            public void setM(int m2) {
                this.m = m2;
            }

            public String toString() {
                return "SleepdataBean{startTime=" + this.a + ", stopTime=" + this.o + ", sleepMode=" + this.m + '}';
            }
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

        public int getT() {
            return this.t;
        }

        public void setT(int t2) {
            this.t = t2;
        }

        public F1SleepTimeBean getI() {
            return this.i;
        }

        public void setI(F1SleepTimeBean i2) {
            this.i = i2;
        }

        public F1SleepTimeBean getO() {
            return this.o;
        }

        public void setO(F1SleepTimeBean o2) {
            this.o = o2;
        }

        public List<SleepdataBean> getD() {
            return this.d;
        }

        public void setD(List<SleepdataBean> d2) {
            this.d = d2;
        }
    }

    public DataBean getData() {
        return this.data;
    }

    public void setData(DataBean data2) {
        this.data = data2;
    }

    public int getData_type() {
        return this.data_type;
    }

    public void setData_type(int data_type2) {
        this.data_type = data_type2;
    }
}
