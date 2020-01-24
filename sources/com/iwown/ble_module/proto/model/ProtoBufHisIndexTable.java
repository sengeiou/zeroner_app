package com.iwown.ble_module.proto.model;

import java.util.List;

public class ProtoBufHisIndexTable {
    private int hisDataCase;
    private int hisDataType;
    private List<Index> indexList;

    public static class Index {
        private int endSeq;
        private int second;
        private int startSeq;

        public int getStartSeq() {
            return this.startSeq;
        }

        public void setStartSeq(int startSeq2) {
            this.startSeq = startSeq2;
        }

        public int getEndSeq() {
            return this.endSeq;
        }

        public void setEndSeq(int endSeq2) {
            this.endSeq = endSeq2;
        }

        public int getSecond() {
            return this.second;
        }

        public void setSecond(int second2) {
            this.second = second2;
        }
    }

    public List<Index> getIndexList() {
        return this.indexList;
    }

    public void setIndexList(List<Index> indexList2) {
        this.indexList = indexList2;
    }

    public int getHisDataType() {
        return this.hisDataType;
    }

    public void setHisDataType(int hisDataType2) {
        this.hisDataType = hisDataType2;
    }

    public int getHisDataCase() {
        return this.hisDataCase;
    }

    public void setHisDataCase(int hisDataCase2) {
        this.hisDataCase = hisDataCase2;
    }
}
