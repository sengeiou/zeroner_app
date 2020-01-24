package com.iwown.device_module.device_operation.bean;

import java.util.List;

public class ModeItems {
    private List<DataBean> data;
    private int data_type;
    private int retCode = -1;

    public static class DataBean {
        private int categoryid;
        private String categoryname;
        private int classid;
        private String keyword;
        private int sdktype;

        public int getCategoryid() {
            return this.categoryid;
        }

        public void setCategoryid(int categoryid2) {
            this.categoryid = categoryid2;
        }

        public String getCategoryname() {
            return this.categoryname;
        }

        public void setCategoryname(String categoryname2) {
            this.categoryname = categoryname2;
        }

        public int getClassid() {
            return this.classid;
        }

        public void setClassid(int classid2) {
            this.classid = classid2;
        }

        public int getSdktype() {
            return this.sdktype;
        }

        public void setSdktype(int sdktype2) {
            this.sdktype = sdktype2;
        }

        public String getKeyword() {
            return this.keyword;
        }

        public void setKeyword(String keyword2) {
            this.keyword = keyword2;
        }

        public String toString() {
            return "DataBean{categoryid=" + this.categoryid + ", categoryname='" + this.categoryname + '\'' + ", classid=" + this.classid + ", sdktype=" + this.sdktype + ", keyword='" + this.keyword + '\'' + '}';
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
