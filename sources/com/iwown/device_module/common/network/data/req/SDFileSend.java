package com.iwown.device_module.common.network.data.req;

public class SDFileSend {
    public static int APP = 1;
    public static int BLE_NOTIFY = 3;
    public static int BLE_WRITE = 2;
    private String date;
    private String filePath;
    private int fileType;
    private long uid;

    public String getDate() {
        return this.date;
    }

    public void setDate(String date2) {
        this.date = date2;
    }

    public int getFileType() {
        return this.fileType;
    }

    public void setFileType(int fileType2) {
        this.fileType = fileType2;
    }

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath2) {
        this.filePath = filePath2;
    }
}
