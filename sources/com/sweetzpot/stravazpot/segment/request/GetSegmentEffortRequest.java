package com.sweetzpot.stravazpot.segment.request;

import com.sweetzpot.stravazpot.segment.api.SegmentEffortAPI;
import com.sweetzpot.stravazpot.segment.model.SegmentEffort;
import com.sweetzpot.stravazpot.segment.rest.SegmentEffortRest;

public class GetSegmentEffortRequest {
    private final SegmentEffortAPI api;
    private final SegmentEffortRest restService;
    private final long segmentEffortID;

    public GetSegmentEffortRequest(long segmentEffortID2, SegmentEffortRest restService2, SegmentEffortAPI api2) {
        this.segmentEffortID = segmentEffortID2;
        this.restService = restService2;
        this.api = api2;
    }

    public SegmentEffort execute() {
        return (SegmentEffort) this.api.execute(this.restService.getSegmentEffort(Long.valueOf(this.segmentEffortID)));
    }
}
