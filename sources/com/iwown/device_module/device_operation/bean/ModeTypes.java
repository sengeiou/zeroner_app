package com.iwown.device_module.device_operation.bean;

import java.util.List;

public class ModeTypes {
    private List<DataBean> data;
    private int data_type;
    private int retCode = -1;

    public static class DataBean {
        private int classid;
        private String classname;

        public int getClassid() {
            return this.classid;
        }

        public void setClassid(int classid2) {
            this.classid = classid2;
        }

        public String getClassname() {
            return this.classname;
        }

        public void setClassname(String classname2) {
            this.classname = classname2;
        }

        public String toString() {
            return "DataBean{classid=" + this.classid + ", classname='" + this.classname + '\'' + '}';
        }
    }

    public int getRetCode() {
        return this.retCode;
    }

    public void setRetCode(int retCode2) {
        this.retCode = retCode2;
    }

    public int getData_type() {
        return this.data_type;
    }

    public void setData_type(int data_type2) {
        this.data_type = data_type2;
    }

    public List<DataBean> getData() {
        return this.data;
    }

    public void setData(List<DataBean> data2) {
        this.data = data2;
    }
}
