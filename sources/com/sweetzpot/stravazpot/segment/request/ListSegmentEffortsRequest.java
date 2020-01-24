package com.sweetzpot.stravazpot.segment.request;

import com.sweetzpot.stravazpot.segment.api.SegmentAPI;
import com.sweetzpot.stravazpot.segment.model.SegmentEffort;
import com.sweetzpot.stravazpot.segment.rest.SegmentRest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ListSegmentEffortsRequest {
    private final SegmentAPI api;
    private Integer athleteID;
    private Date endDateLocal;
    private final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private Integer page;
    private Integer perPage;
    private final SegmentRest restService;
    private final int segmentID;
    private Date startDateLocal;

    public ListSegmentEffortsRequest(int segmentID2, SegmentRest restService2, SegmentAPI api2) {
        this.segmentID = segmentID2;
        this.restService = restService2;
        this.api = api2;
    }

    public ListSegmentEffortsRequest forAthlete(int athleteID2) {
        this.athleteID = Integer.valueOf(athleteID2);
        return this;
    }

    public ListSegmentEffortsRequest startingOn(Date startDateLocal2) {
        this.startDateLocal = startDateLocal2;
        return this;
    }

    public ListSegmentEffortsRequest endingOn(Date endDateLocal2) {
        this.endDateLocal = endDateLocal2;
        return this;
    }

    public ListSegmentEffortsRequest inPage(int page2) {
        this.page = Integer.valueOf(page2);
        return this;
    }

    public ListSegmentEffortsRequest perPage(int perPage2) {
        this.perPage = Integer.valueOf(perPage2);
        return this;
    }

    public List<SegmentEffort> execute() {
        return (List) this.api.execute(this.restService.getSegmentEfforts(Integer.valueOf(this.segmentID), this.athleteID, this.formatter.format(this.startDateLocal), this.formatter.format(this.endDateLocal), this.page, this.perPage));
    }
}
