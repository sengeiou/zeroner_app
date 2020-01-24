package com.iwown.data_link.sleep_data;

public class SleepHistoryData {
    public int deepTime;
    public long endTime;
    public int lightTime;
    public int score;
    public long startTime;
    public String time_str;
    public int totalTime;

    public String toString() {
        return "SleepHistoryData{time_str='" + this.time_str + '\'' + ", deepTime=" + this.deepTime + ", lightTime=" + this.lightTime + ", startTime=" + this.startTime + ", endTime=" + this.endTime + ", score=" + this.score + ", totalTime=" + this.totalTime + '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SleepHistoryData)) {
            return false;
        }
        SleepHistoryData data = (SleepHistoryData) o;
        if (this.deepTime == data.deepTime && this.lightTime == data.lightTime && this.startTime == data.startTime && this.endTime == data.endTime && this.score == data.score && this.totalTime == data.totalTime) {
            return this.time_str.equals(data.time_str);
        }
        return false;
    }

    public int hashCode() {
        return (((((((((((this.time_str.hashCode() * 31) + this.deepTime) * 31) + this.lightTime) * 31) + ((int) (this.startTime ^ (this.startTime >>> 32)))) * 31) + ((int) (this.endTime ^ (this.endTime >>> 32)))) * 31) + this.score) * 31) + this.totalTime;
    }
}
