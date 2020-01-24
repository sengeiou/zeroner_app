package com.iwown.ble_module.proto.model;

public class ProtoBufFileUpdateInfo {
    private int crc32AtOffset;
    private int fileCrc32;
    private String fileName;
    private int fileOffset;
    private int fileSize;
    private int fuType;
    private int maxSize;
    private int mtu;
    private int status;
    private int type;
    private boolean valid;

    public int getType() {
        return this.type;
    }

    public void setType(int type2) {
        this.type = type2;
    }

    public int getMtu() {
        return this.mtu;
    }

    public void setMtu(int mtu2) {
        this.mtu = mtu2;
    }

    public int getMaxSize() {
        return this.maxSize;
    }

    public void setMaxSize(int maxSize2) {
        this.maxSize = maxSize2;
    }

    public boolean isValid() {
        return this.valid;
    }

    public void setValid(boolean valid2) {
        this.valid = valid2;
    }

    public int getFuType() {
        return this.fuType;
    }

    public void setFuType(int fuType2) {
        this.fuType = fuType2;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName2) {
        this.fileName = fileName2;
    }

    public int getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(int fileSize2) {
        this.fileSize = fileSize2;
    }

    public int getFileCrc32() {
        return this.fileCrc32;
    }

    public void setFileCrc32(int fileCrc322) {
        this.fileCrc32 = fileCrc322;
    }

    public int getFileOffset() {
        return this.fileOffset;
    }

    public void setFileOffset(int fileOffset2) {
        this.fileOffset = fileOffset2;
    }

    public int getCrc32AtOffset() {
        return this.crc32AtOffset;
    }

    public void setCrc32AtOffset(int crc32AtOffset2) {
        this.crc32AtOffset = crc32AtOffset2;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status2) {
        this.status = status2;
    }

    public String toString() {
        return "ProtoBufFileUpdateInfo{type=" + this.type + ", mtu=" + this.mtu + ", maxSize=" + this.maxSize + ", valid=" + this.valid + ", fuType=" + this.fuType + ", fileName='" + this.fileName + '\'' + ", fileSize=" + this.fileSize + ", fileCrc32=" + this.fileCrc32 + ", fileOffset=" + this.fileOffset + ", crc32AtOffset=" + this.crc32AtOffset + ", status=" + this.status + '}';
    }
}
