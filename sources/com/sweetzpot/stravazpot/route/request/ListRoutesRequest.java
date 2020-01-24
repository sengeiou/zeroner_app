package com.sweetzpot.stravazpot.route.request;

import com.sweetzpot.stravazpot.route.api.RouteAPI;
import com.sweetzpot.stravazpot.route.model.Route;
import com.sweetzpot.stravazpot.route.rest.RouteRest;
import java.util.List;

public class ListRoutesRequest {
    private final RouteAPI api;
    private final int athleteID;
    private final RouteRest restService;

    public ListRoutesRequest(int athleteID2, RouteRest restService2, RouteAPI api2) {
        this.athleteID = athleteID2;
        this.restService = restService2;
        this.api = api2;
    }

    public List<Route> execute() {
        return (List) this.api.execute(this.restService.getAthleteRoutes(Integer.valueOf(this.athleteID)));
    }
}
