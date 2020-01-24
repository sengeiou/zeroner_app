package com.sweetzpot.stravazpot.segment.request;

import com.sweetzpot.stravazpot.segment.api.SegmentAPI;
import com.sweetzpot.stravazpot.segment.model.Segment;
import com.sweetzpot.stravazpot.segment.rest.SegmentRest;

public class GetSegmentRequest {
    private final SegmentAPI api;
    private final SegmentRest restService;
    private final int segmentID;

    public GetSegmentRequest(int segmentID2, SegmentRest restService2, SegmentAPI api2) {
        this.segmentID = segmentID2;
        this.restService = restService2;
        this.api = api2;
    }

    public Segment execute() {
        return (Segment) this.api.execute(this.restService.getSegment(Integer.valueOf(this.segmentID)));
    }
}
