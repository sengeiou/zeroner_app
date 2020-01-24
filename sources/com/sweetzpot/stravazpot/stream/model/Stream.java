package com.sweetzpot.stravazpot.stream.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Stream {
    @SerializedName("data")
    private List<Object> data;
    @SerializedName("original_size")
    private int originalSize;
    @SerializedName("resolution")
    private Resolution resolution;
    @SerializedName("series_type")
    private SeriesType seriesType;
    @SerializedName("type")
    private StreamType type;

    public StreamType getType() {
        return this.type;
    }

    public List<Object> getData() {
        return this.data;
    }

    public SeriesType getSeriesType() {
        return this.seriesType;
    }

    public int getOriginalSize() {
        return this.originalSize;
    }

    public Resolution getResolution() {
        return this.resolution;
    }
}
