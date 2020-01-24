package com.sweetzpot.stravazpot.segment.request;

import com.sweetzpot.stravazpot.segment.api.SegmentAPI;
import com.sweetzpot.stravazpot.segment.model.Segment;
import com.sweetzpot.stravazpot.segment.rest.SegmentRest;
import java.util.List;

public class ListAthleteStarredSegmentsRequest {
    private final SegmentAPI api;
    private final int athleteID;
    private Integer page;
    private Integer perPage;
    private final SegmentRest restService;

    public ListAthleteStarredSegmentsRequest(int athleteID2, SegmentRest restService2, SegmentAPI api2) {
        this.athleteID = athleteID2;
        this.restService = restService2;
        this.api = api2;
    }

    public ListAthleteStarredSegmentsRequest inPage(int page2) {
        this.page = Integer.valueOf(page2);
        return this;
    }

    public ListAthleteStarredSegmentsRequest perPage(int perPage2) {
        this.perPage = Integer.valueOf(perPage2);
        return this;
    }

    public List<Segment> execute() {
        return (List) this.api.execute(this.restService.getAthleteStarredSegments(Integer.valueOf(this.athleteID), this.page, this.perPage));
    }
}
