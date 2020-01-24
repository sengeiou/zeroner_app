package com.sweetzpot.stravazpot.stream.request;

import com.sweetzpot.stravazpot.stream.api.StreamAPI;
import com.sweetzpot.stravazpot.stream.model.Resolution;
import com.sweetzpot.stravazpot.stream.model.SeriesType;
import com.sweetzpot.stravazpot.stream.model.Stream;
import com.sweetzpot.stravazpot.stream.model.StreamType;
import com.sweetzpot.stravazpot.stream.rest.StreamRest;
import java.util.List;

public class GetActivityStreamsRequest {
    private final int activityID;
    private final StreamAPI api;
    private Resolution resolution;
    private final StreamRest restService;
    private SeriesType seriesType;
    private StreamType[] types;

    public GetActivityStreamsRequest(int activityID2, StreamRest restService2, StreamAPI api2) {
        this.activityID = activityID2;
        this.restService = restService2;
        this.api = api2;
    }

    public GetActivityStreamsRequest forTypes(StreamType... types2) {
        this.types = types2;
        return this;
    }

    public GetActivityStreamsRequest withResolution(Resolution resolution2) {
        this.resolution = resolution2;
        return this;
    }

    public GetActivityStreamsRequest withSeriesType(SeriesType seriesType2) {
        this.seriesType = seriesType2;
        return this;
    }

    public List<Stream> execute() {
        return (List) this.api.execute(this.restService.getActivityStreams(Integer.valueOf(this.activityID), StreamType.getQueryString(this.types), this.resolution, this.seriesType));
    }
}
