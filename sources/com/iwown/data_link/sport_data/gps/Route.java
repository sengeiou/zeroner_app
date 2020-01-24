package com.iwown.data_link.sport_data.gps;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import java.util.ArrayList;

public class Route extends Extension {
    private String comment;
    private String description;
    private String name;
    private Integer number;
    private ArrayList<Waypoint> routePoints;
    private String src;
    private String type;

    public String getName() {
        return this.name;
    }

    public void setName(String paramString) {
        this.name = paramString;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String paramString) {
        this.comment = paramString;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String paramString) {
        this.description = paramString;
    }

    public String getSrc() {
        return this.src;
    }

    public void setSrc(String paramString) {
        this.src = paramString;
    }

    public Integer getNumber() {
        return this.number;
    }

    public void setNumber(Integer paramInteger) {
        this.number = paramInteger;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String paramString) {
        this.type = paramString;
    }

    public ArrayList<Waypoint> getRoutePoints() {
        return this.routePoints;
    }

    public void setRoutePoints(ArrayList<Waypoint> paramArrayList) {
        this.routePoints = paramArrayList;
    }

    public void addRoutePoint(Waypoint paramWaypoint) {
        if (this.routePoints == null) {
            this.routePoints = new ArrayList<>();
        }
        this.routePoints.add(paramWaypoint);
    }

    public String toString() {
        StringBuffer localStringBuffer = new StringBuffer();
        localStringBuffer.append("rte[");
        localStringBuffer.append("name:" + this.name + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
        int i = 0;
        if (this.routePoints != null) {
            i = this.routePoints.size();
        }
        localStringBuffer.append("rtepts:" + i + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
        localStringBuffer.append("]");
        return localStringBuffer.toString();
    }
}
