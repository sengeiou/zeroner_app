package com.sweetzpot.stravazpot.route.api;

import com.sweetzpot.stravazpot.common.api.StravaAPI;
import com.sweetzpot.stravazpot.common.api.StravaConfig;
import com.sweetzpot.stravazpot.route.request.GetRouteRequest;
import com.sweetzpot.stravazpot.route.request.ListRoutesRequest;
import com.sweetzpot.stravazpot.route.rest.RouteRest;

public class RouteAPI extends StravaAPI {
    public RouteAPI(StravaConfig config) {
        super(config);
    }

    public GetRouteRequest getRoute(int routeID) {
        return new GetRouteRequest(routeID, (RouteRest) getAPI(RouteRest.class), this);
    }

    public ListRoutesRequest listRoutes(int athleteID) {
        return new ListRoutesRequest(athleteID, (RouteRest) getAPI(RouteRest.class), this);
    }
}
