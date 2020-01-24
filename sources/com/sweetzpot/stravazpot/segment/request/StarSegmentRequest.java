package com.sweetzpot.stravazpot.segment.request;

import com.sweetzpot.stravazpot.segment.api.SegmentAPI;
import com.sweetzpot.stravazpot.segment.model.Segment;
import com.sweetzpot.stravazpot.segment.rest.SegmentRest;

public class StarSegmentRequest {
    private final SegmentAPI api;
    private final SegmentRest restService;
    private final int segmentID;
    private final boolean star;

    public StarSegmentRequest(int segmentID2, boolean star2, SegmentRest restService2, SegmentAPI api2) {
        this.segmentID = segmentID2;
        this.star = star2;
        this.restService = restService2;
        this.api = api2;
    }

    public Segment execute() {
        return (Segment) this.api.execute(this.restService.starSegment(Integer.valueOf(this.segmentID), Boolean.valueOf(this.star)));
    }
}
