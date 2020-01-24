package com.sweetzpot.stravazpot.stream.api;

import com.sweetzpot.stravazpot.common.api.StravaAPI;
import com.sweetzpot.stravazpot.common.api.StravaConfig;
import com.sweetzpot.stravazpot.stream.request.GetActivityStreamsRequest;
import com.sweetzpot.stravazpot.stream.request.GetRouteStreamsRequest;
import com.sweetzpot.stravazpot.stream.request.GetSegmentEffortStreamsRequest;
import com.sweetzpot.stravazpot.stream.request.GetSegmentStreamsRequest;
import com.sweetzpot.stravazpot.stream.rest.StreamRest;

public class StreamAPI extends StravaAPI {
    public StreamAPI(StravaConfig config) {
        super(config);
    }

    public GetActivityStreamsRequest getActivityStreams(int activityID) {
        return new GetActivityStreamsRequest(activityID, (StreamRest) getAPI(StreamRest.class), this);
    }

    public GetSegmentEffortStreamsRequest getSegmentEffortStreams(long segmentEffortID) {
        return new GetSegmentEffortStreamsRequest(segmentEffortID, (StreamRest) getAPI(StreamRest.class), this);
    }

    public GetSegmentStreamsRequest getSegmentStreams(int segmentID) {
        return new GetSegmentStreamsRequest(segmentID, (StreamRest) getAPI(StreamRest.class), this);
    }

    public GetRouteStreamsRequest getRouteStreams(int routeID) {
        return new GetRouteStreamsRequest(routeID, (StreamRest) getAPI(StreamRest.class), this);
    }
}
