package com.sweetzpot.stravazpot.route.request;

import com.sweetzpot.stravazpot.route.api.RouteAPI;
import com.sweetzpot.stravazpot.route.model.Route;
import com.sweetzpot.stravazpot.route.rest.RouteRest;

public class GetRouteRequest {
    private final RouteAPI api;
    private final RouteRest restService;
    private final int routeID;

    public GetRouteRequest(int routeID2, RouteRest restService2, RouteAPI api2) {
        this.routeID = routeID2;
        this.restService = restService2;
        this.api = api2;
    }

    public Route execute() {
        return (Route) this.api.execute(this.restService.getRoute(Integer.valueOf(this.routeID)));
    }
}
