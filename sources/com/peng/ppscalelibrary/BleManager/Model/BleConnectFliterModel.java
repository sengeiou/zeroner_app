package com.peng.ppscalelibrary.BleManager.Model;

import java.util.ArrayList;
import java.util.List;

public class BleConnectFliterModel {
    public static final String ADORE_SCALE = "ADORE";
    public static final String BM_SCALE = "BM Scale";
    public static final String BODYFAT_SCALE = "BodyFat Scale";
    public static final String ELECTRONIC_SCALE = "Electronic Scale";
    public static final String ENERGY_SCALE = "Energy Scale";
    public static final String HEALTH_SCALE = "Health Scale";
    public static final String HUMAN_SCALE = "Human Scale";
    public static final String LF_SCALE = "LFScale";
    private int advDataLength;
    private int advDataType;
    private int beaconBytesLenght;
    private int bmdjDataLength = 0;
    private String characteristicBMDJUUID = "";
    private String characteristicNofifyUUID;
    private String characteristicWriteUUID;
    private int historyDataLength;
    private int historyEndDataLength;
    private String scaleName;
    private String serviceUUID;

    public String getScaleName() {
        return this.scaleName;
    }

    public void setScaleName(String scaleName2) {
        this.scaleName = scaleName2;
    }

    public String getServiceUUID() {
        return this.serviceUUID;
    }

    public void setServiceUUID(String serviceUUID2) {
        this.serviceUUID = serviceUUID2;
    }

    public String getCharacteristicWriteUUID() {
        return this.characteristicWriteUUID;
    }

    public void setCharacteristicWriteUUID(String characteristicWriteUUID2) {
        this.characteristicWriteUUID = characteristicWriteUUID2;
    }

    public String getCharacteristicNofifyUUID() {
        return this.characteristicNofifyUUID;
    }

    public void setCharacteristicNofifyUUID(String characteristicNofifyUUID2) {
        this.characteristicNofifyUUID = characteristicNofifyUUID2;
    }

    public int getAdvDataLength() {
        return this.advDataLength;
    }

    public void setAdvDataLength(int advDataLength2) {
        this.advDataLength = advDataLength2;
    }

    public int getAdvDataType() {
        return this.advDataType;
    }

    public int getHistoryDataLength() {
        return this.historyDataLength;
    }

    public int getHistoryEndDataLength() {
        return this.historyEndDataLength;
    }

    public void setAdvDataType(int advDataType2) {
        this.advDataType = advDataType2;
    }

    public int getBeaconBytesLenght() {
        return this.beaconBytesLenght;
    }

    public void setBeaconBytesLenght(int beaconBytesLenght2) {
        this.beaconBytesLenght = beaconBytesLenght2;
    }

    public int getBmdjDataLength() {
        return this.bmdjDataLength;
    }

    public void setBmdjDataLength(int bmdjDataLength2) {
        this.bmdjDataLength = bmdjDataLength2;
    }

    public String getCharacteristicBMDJUUID() {
        return this.characteristicBMDJUUID;
    }

    public void setCharacteristicBMDJUUID(String characteristicBMDJUUID2) {
        this.characteristicBMDJUUID = characteristicBMDJUUID2;
    }

    public BleConnectFliterModel(String scaleName2, String serviceUUID2, String characteristicWriteUUID2, String characteristicNofifyUUID2, int advDataLength2, int advDataType2, int historyDataLength2, int historyEndDataLength2, int beaconBytesLenght2) {
        this.scaleName = scaleName2;
        this.serviceUUID = serviceUUID2;
        this.characteristicWriteUUID = characteristicWriteUUID2;
        this.characteristicNofifyUUID = characteristicNofifyUUID2;
        this.advDataLength = advDataLength2;
        this.advDataType = advDataType2;
        this.historyDataLength = historyDataLength2;
        this.historyEndDataLength = historyEndDataLength2;
        this.beaconBytesLenght = beaconBytesLenght2;
    }

    public static BleConnectFliterModel getLFScaleFliter() {
        return new BleConnectFliterModel(LF_SCALE, "0000fff0", "0000fff1", "0000fff4", 17, 255, 0, 0, 62);
    }

    public static List<BleConnectFliterModel> getFliterArr() {
        BleConnectFliterModel energyScaleFliter = new BleConnectFliterModel(ENERGY_SCALE, "0000fff0", "0000fff1", "0000fff4", 19, 255, 0, 0, 88);
        BleConnectFliterModel bodyFatScaleFliter = new BleConnectFliterModel(BODYFAT_SCALE, "0000fff0", "0000fff1", "0000fff4", 19, 255, 0, 0, 90);
        BleConnectFliterModel healthScaleFliter = new BleConnectFliterModel(HEALTH_SCALE, "0000fff0", "0000fff1", "0000fff4", 19, 255, 0, 0, 88);
        BleConnectFliterModel electronicScaleFliter = new BleConnectFliterModel(ELECTRONIC_SCALE, "0000fff0", "0000fff1", "0000fff4", 8, 255, 0, 0, 70);
        BleConnectFliterModel adoreScaleFliter = new BleConnectFliterModel(ADORE_SCALE, "0000fff0", "0000fff1", "0000fff4", 19, 255, 36, 4, 74);
        BleConnectFliterModel lfScaleFliter = new BleConnectFliterModel(LF_SCALE, "0000fff0", "0000fff1", "0000fff4", 17, 255, 0, 0, 62);
        BleConnectFliterModel humanScaleFliter = new BleConnectFliterModel(HUMAN_SCALE, "0000fff0", "0000fff1", "0000fff4", 19, 255, 0, 0, 86);
        BleConnectFliterModel bmScaleFliter = new BleConnectFliterModel(BM_SCALE, "0000fff0", "0000fff1", "0000fff4", 19, 255, 0, 0, 80);
        bmScaleFliter.setBmdjDataLength(12);
        bmScaleFliter.setCharacteristicBMDJUUID("0000fff5");
        ArrayList arrayList = new ArrayList();
        arrayList.add(energyScaleFliter);
        arrayList.add(bodyFatScaleFliter);
        arrayList.add(healthScaleFliter);
        arrayList.add(electronicScaleFliter);
        arrayList.add(adoreScaleFliter);
        arrayList.add(lfScaleFliter);
        arrayList.add(bmScaleFliter);
        arrayList.add(humanScaleFliter);
        return arrayList;
    }

    public String toString() {
        return "BleConnectFliterModel{scaleName='" + this.scaleName + '\'' + ", serviceUUID='" + this.serviceUUID + '\'' + ", characteristicWriteUUID='" + this.characteristicWriteUUID + '\'' + ", characteristicNofifyUUID='" + this.characteristicNofifyUUID + '\'' + ", advDataLength=" + this.advDataLength + ", advDataType=" + this.advDataType + ", historyDataLength=" + this.historyDataLength + ", historyEndDataLength=" + this.historyEndDataLength + ", beaconBytesLenght=" + this.beaconBytesLenght + ", bmdjDataLength=" + this.bmdjDataLength + ", characteristicBMDJUUID='" + this.characteristicBMDJUUID + '\'' + '}';
    }
}
