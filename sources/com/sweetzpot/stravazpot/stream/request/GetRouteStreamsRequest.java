package com.sweetzpot.stravazpot.stream.request;

import com.sweetzpot.stravazpot.stream.api.StreamAPI;
import com.sweetzpot.stravazpot.stream.model.Stream;
import com.sweetzpot.stravazpot.stream.rest.StreamRest;
import java.util.List;

public class GetRouteStreamsRequest {
    private final StreamAPI api;
    private final StreamRest restService;
    private final int routeID;

    public GetRouteStreamsRequest(int routeID2, StreamRest restService2, StreamAPI api2) {
        this.routeID = routeID2;
        this.restService = restService2;
        this.api = api2;
    }

    public List<Stream> execute() {
        return (List) this.api.execute(this.restService.getRouteStreams(Integer.valueOf(this.routeID)));
    }
}
