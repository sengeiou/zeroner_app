package com.sweetzpot.stravazpot.stream.request;

import com.sweetzpot.stravazpot.stream.api.StreamAPI;
import com.sweetzpot.stravazpot.stream.model.Resolution;
import com.sweetzpot.stravazpot.stream.model.SeriesType;
import com.sweetzpot.stravazpot.stream.model.Stream;
import com.sweetzpot.stravazpot.stream.model.StreamType;
import com.sweetzpot.stravazpot.stream.rest.StreamRest;
import java.util.List;

public class GetSegmentEffortStreamsRequest {
    private final StreamAPI api;
    private Resolution resolution;
    private final StreamRest restService;
    private final long segmentEffortStreams;
    private SeriesType seriesType;
    private StreamType[] types;

    public GetSegmentEffortStreamsRequest(long segmentEffortStreams2, StreamRest restService2, StreamAPI api2) {
        this.segmentEffortStreams = segmentEffortStreams2;
        this.restService = restService2;
        this.api = api2;
    }

    public GetSegmentEffortStreamsRequest forTypes(StreamType... types2) {
        this.types = types2;
        return this;
    }

    public GetSegmentEffortStreamsRequest withResolution(Resolution resolution2) {
        this.resolution = resolution2;
        return this;
    }

    public GetSegmentEffortStreamsRequest withSeriesType(SeriesType seriesType2) {
        this.seriesType = seriesType2;
        return this;
    }

    public List<Stream> execute() {
        return (List) this.api.execute(this.restService.getSegmentEffortStreams(Long.valueOf(this.segmentEffortStreams), StreamType.getQueryString(this.types), this.resolution, this.seriesType));
    }
}
