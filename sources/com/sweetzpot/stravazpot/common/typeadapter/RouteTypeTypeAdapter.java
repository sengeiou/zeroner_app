package com.sweetzpot.stravazpot.common.typeadapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.sweetzpot.stravazpot.route.model.RouteType;
import java.io.IOException;

public class RouteTypeTypeAdapter extends TypeAdapter<RouteType> {
    public void write(JsonWriter out, RouteType routeType) throws IOException {
        out.value((long) routeType.getRawValue());
    }

    public RouteType read(JsonReader in) throws IOException {
        RouteType[] values;
        if (!in.peek().equals(JsonToken.NULL)) {
            int value = in.nextInt();
            for (RouteType routeType : RouteType.values()) {
                if (routeType.getRawValue() == value) {
                    return routeType;
                }
            }
        } else {
            in.nextNull();
        }
        return null;
    }
}
