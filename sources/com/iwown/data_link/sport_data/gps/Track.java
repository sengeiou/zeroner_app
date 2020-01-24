package com.iwown.data_link.sport_data.gps;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import java.util.ArrayList;

public class Track extends Extension {
    private String comment;
    private String description;
    private String name;
    private Integer number;
    private String src;
    private ArrayList<Waypoint> trackPoints;
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

    public ArrayList<Waypoint> getTrackPoints() {
        return this.trackPoints;
    }

    public void setTrackPoints(ArrayList<Waypoint> paramArrayList) {
        this.trackPoints = paramArrayList;
    }

    public String toString() {
        StringBuffer localStringBuffer = new StringBuffer();
        localStringBuffer.append("trk[");
        localStringBuffer.append("name:" + this.name + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
        int i = 0;
        if (this.trackPoints != null) {
            i = this.trackPoints.size();
        }
        localStringBuffer.append("trkseg:" + i + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
        localStringBuffer.append("]");
        return localStringBuffer.toString();
    }
}
