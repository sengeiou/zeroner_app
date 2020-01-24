package com.sweetzpot.stravazpot.segment.request;

import com.sweetzpot.stravazpot.common.model.Gender;
import com.sweetzpot.stravazpot.segment.api.SegmentAPI;
import com.sweetzpot.stravazpot.segment.model.AgeGroup;
import com.sweetzpot.stravazpot.segment.model.DateRange;
import com.sweetzpot.stravazpot.segment.model.Leaderboard;
import com.sweetzpot.stravazpot.segment.model.WeightClass;
import com.sweetzpot.stravazpot.segment.rest.SegmentRest;

public class GetSegmentLeaderboardRequest {
    private AgeGroup ageGroup;
    private final SegmentAPI api;
    private Integer clubID;
    private Integer contextEntries;
    private DateRange dateRange;
    private Boolean following;
    private Gender gender;
    private Integer page;
    private Integer perPage;
    private final SegmentRest restService;
    private final int segmentID;
    private WeightClass weightClass;

    public GetSegmentLeaderboardRequest(int segmentID2, SegmentRest restService2, SegmentAPI api2) {
        this.segmentID = segmentID2;
        this.restService = restService2;
        this.api = api2;
    }

    public GetSegmentLeaderboardRequest withGender(Gender gender2) {
        this.gender = gender2;
        return this;
    }

    public GetSegmentLeaderboardRequest inAgeGroup(AgeGroup ageGroup2) {
        this.ageGroup = ageGroup2;
        return this;
    }

    public GetSegmentLeaderboardRequest inWeightClass(WeightClass weightClass2) {
        this.weightClass = weightClass2;
        return this;
    }

    public GetSegmentLeaderboardRequest following(boolean following2) {
        this.following = Boolean.valueOf(following2);
        return this;
    }

    public GetSegmentLeaderboardRequest inClub(int clubID2) {
        this.clubID = Integer.valueOf(clubID2);
        return this;
    }

    public GetSegmentLeaderboardRequest inDateRange(DateRange dateRange2) {
        this.dateRange = dateRange2;
        return this;
    }

    public GetSegmentLeaderboardRequest withContextEntries(int contextEntries2) {
        this.contextEntries = Integer.valueOf(contextEntries2);
        return this;
    }

    public GetSegmentLeaderboardRequest inPage(int page2) {
        this.page = Integer.valueOf(page2);
        return this;
    }

    public GetSegmentLeaderboardRequest perPage(int perPage2) {
        this.perPage = Integer.valueOf(perPage2);
        return this;
    }

    public Leaderboard execute() {
        return (Leaderboard) this.api.execute(this.restService.getSegmentLeaderboard(Integer.valueOf(this.segmentID), this.gender, this.ageGroup, this.weightClass, this.following, this.clubID, this.dateRange, this.contextEntries, this.page, this.perPage));
    }
}
