package com.iwown.ble_module.proto.model;

import java.util.List;

public class ProtoBufHisGnssData extends ProtoBufParent {
    private int frequency;
    private List<Gnss> gnssList;
    private int seq;
    private int time_stamp;

    public static class Gnss {
        private float altitude;
        private float latitude;
        private float longitude;
        private float speed;

        public float getLongitude() {
            return this.longitude;
        }

        public void setLongitude(float longitude2) {
            this.longitude = longitude2;
        }

        public float getLatitude() {
            return this.latitude;
        }

        public void setLatitude(float latitude2) {
            this.latitude = latitude2;
        }

        public float getSpeed() {
            return this.speed;
        }

        public void setSpeed(float speed2) {
            this.speed = speed2;
        }

        public float getAltitude() {
            return this.altitude;
        }

        public void setAltitude(float altitude2) {
            this.altitude = altitude2;
        }
    }

    public int getTime_stamp() {
        return this.time_stamp;
    }

    public void setTime_stamp(int time_stamp2) {
        this.time_stamp = time_stamp2;
    }

    public int getFrequency() {
        return this.frequency;
    }

    public void setFrequency(int frequency2) {
        this.frequency = frequency2;
    }

    public List<Gnss> getGnssList() {
        return this.gnssList;
    }

    public void setGnssList(List<Gnss> gnssList2) {
        this.gnssList = gnssList2;
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

    public int getSeq() {
        return this.seq;
    }

    public void setSeq(int seq2) {
        this.seq = seq2;
    }
}
