package com.sweetzpot.stravazpot.segment.api;

import com.sweetzpot.stravazpot.common.api.StravaAPI;
import com.sweetzpot.stravazpot.common.api.StravaConfig;
import com.sweetzpot.stravazpot.segment.model.Bounds;
import com.sweetzpot.stravazpot.segment.request.ExploreSegmentsRequest;
import com.sweetzpot.stravazpot.segment.request.GetSegmentLeaderboardRequest;
import com.sweetzpot.stravazpot.segment.request.GetSegmentRequest;
import com.sweetzpot.stravazpot.segment.request.ListAthleteStarredSegmentsRequest;
import com.sweetzpot.stravazpot.segment.request.ListMyStarredSegmentsRequest;
import com.sweetzpot.stravazpot.segment.request.ListSegmentEffortsRequest;
import com.sweetzpot.stravazpot.segment.request.StarSegmentRequest;
import com.sweetzpot.stravazpot.segment.rest.SegmentRest;

public class SegmentAPI extends StravaAPI {
    public SegmentAPI(StravaConfig config) {
        super(config);
    }

    public GetSegmentRequest getSegment(int segmentID) {
        return new GetSegmentRequest(segmentID, (SegmentRest) getAPI(SegmentRest.class), this);
    }

    public ListMyStarredSegmentsRequest listMyStarredSegments() {
        return new ListMyStarredSegmentsRequest((SegmentRest) getAPI(SegmentRest.class), this);
    }

    public ListAthleteStarredSegmentsRequest listStarredSegmentsByAthlete(int athleteID) {
        return new ListAthleteStarredSegmentsRequest(athleteID, (SegmentRest) getAPI(SegmentRest.class), this);
    }

    public StarSegmentRequest starSegment(int segmentID) {
        return new StarSegmentRequest(segmentID, true, (SegmentRest) getAPI(SegmentRest.class), this);
    }

    public StarSegmentRequest unstarSegment(int segmentID) {
        return new StarSegmentRequest(segmentID, false, (SegmentRest) getAPI(SegmentRest.class), this);
    }

    public ListSegmentEffortsRequest listSegmentEfforts(int segmentID) {
        return new ListSegmentEffortsRequest(segmentID, (SegmentRest) getAPI(SegmentRest.class), this);
    }

    public GetSegmentLeaderboardRequest getLeaderboardForSegment(int segmentID) {
        return new GetSegmentLeaderboardRequest(segmentID, (SegmentRest) getAPI(SegmentRest.class), this);
    }

    public ExploreSegmentsRequest exploreSegmentsInRegion(Bounds bounds) {
        return new ExploreSegmentsRequest(bounds, (SegmentRest) getAPI(SegmentRest.class), this);
    }
}
