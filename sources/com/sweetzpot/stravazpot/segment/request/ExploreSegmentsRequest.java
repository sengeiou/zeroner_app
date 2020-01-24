package com.sweetzpot.stravazpot.segment.request;

import com.sweetzpot.stravazpot.segment.api.SegmentAPI;
import com.sweetzpot.stravazpot.segment.model.Bounds;
import com.sweetzpot.stravazpot.segment.model.ExploreResult;
import com.sweetzpot.stravazpot.segment.model.ExploreType;
import com.sweetzpot.stravazpot.segment.model.Segment;
import com.sweetzpot.stravazpot.segment.rest.SegmentRest;
import java.util.List;

public class ExploreSegmentsRequest {
    private ExploreType activityType;
    private final SegmentAPI api;
    private final Bounds bounds;
    private Integer maxCategory;
    private Integer minCategory;
    private final SegmentRest restService;

    public ExploreSegmentsRequest(Bounds bounds2, SegmentRest restService2, SegmentAPI api2) {
        this.bounds = bounds2;
        this.restService = restService2;
        this.api = api2;
    }

    public ExploreSegmentsRequest forActivityType(ExploreType activityType2) {
        this.activityType = activityType2;
        return this;
    }

    public ExploreSegmentsRequest withMinimumClimbCategory(int minCategory2) {
        this.minCategory = Integer.valueOf(minCategory2);
        return this;
    }

    public ExploreSegmentsRequest withMaximumClimbCategory(int maxCategory2) {
        this.maxCategory = Integer.valueOf(maxCategory2);
        return this;
    }

    public List<Segment> execute() {
        return ((ExploreResult) this.api.execute(this.restService.exploreSegments(this.bounds.toString(), this.activityType, this.minCategory, this.maxCategory))).getSegments();
    }
}
