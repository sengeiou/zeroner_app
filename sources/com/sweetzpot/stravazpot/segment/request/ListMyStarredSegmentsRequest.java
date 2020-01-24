package com.sweetzpot.stravazpot.segment.request;

import com.sweetzpot.stravazpot.segment.api.SegmentAPI;
import com.sweetzpot.stravazpot.segment.model.Segment;
import com.sweetzpot.stravazpot.segment.rest.SegmentRest;
import java.util.List;

public class ListMyStarredSegmentsRequest {
    private final SegmentAPI api;
    private Integer page;
    private Integer perPage;
    private final SegmentRest restService;

    public ListMyStarredSegmentsRequest(SegmentRest restService2, SegmentAPI api2) {
        this.restService = restService2;
        this.api = api2;
    }

    public ListMyStarredSegmentsRequest inPage(int page2) {
        this.page = Integer.valueOf(page2);
        return this;
    }

    public ListMyStarredSegmentsRequest perPage(int perPage2) {
        this.perPage = Integer.valueOf(perPage2);
        return this;
    }

    public List<Segment> execute() {
        return (List) this.api.execute(this.restService.getMyStarredSegments(this.page, this.perPage));
    }
}
