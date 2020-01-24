package com.sweetzpot.stravazpot.stream.request;

import com.sweetzpot.stravazpot.stream.api.StreamAPI;
import com.sweetzpot.stravazpot.stream.model.Resolution;
import com.sweetzpot.stravazpot.stream.model.SeriesType;
import com.sweetzpot.stravazpot.stream.model.Stream;
import com.sweetzpot.stravazpot.stream.model.StreamType;
import com.sweetzpot.stravazpot.stream.rest.StreamRest;
import java.util.List;

public class GetSegmentStreamsRequest {
    private final StreamAPI api;
    private Resolution resolution;
    private final StreamRest restService;
    private final int segmentID;
    private SeriesType seriesType;
    private StreamType[] types;

    public GetSegmentStreamsRequest(int segmentID2, StreamRest restService2, StreamAPI api2) {
        this.segmentID = segmentID2;
        this.restService = restService2;
        this.api = api2;
    }

    public GetSegmentStreamsRequest forTypes(StreamType... types2) {
        this.types = types2;
        return this;
    }

    public GetSegmentStreamsRequest withResolution(Resolution resolution2) {
        this.resolution = resolution2;
        return this;
    }

    public GetSegmentStreamsRequest withSeriesType(SeriesType seriesType2) {
        this.seriesType = seriesType2;
        return this;
    }

    public List<Stream> execute() {
        return (List) this.api.execute(this.restService.getSegmentStreams(Integer.valueOf(this.segmentID), StreamType.getQueryString(this.types), this.resolution, this.seriesType));
    }
}
