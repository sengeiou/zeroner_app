package com.iwown.data_link.sport_data.gps;

import java.util.HashSet;

public class GPX extends Extension {
    private String creator;
    private HashSet<Route> routes;
    private HashSet<Track> tracks;
    private String version;
    private HashSet<Waypoint> waypoints;

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String paramString) {
        this.version = paramString;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String paramString) {
        this.creator = paramString;
    }

    public HashSet<Waypoint> getWaypoints() {
        return this.waypoints;
    }

    public void setWaypoints(HashSet<Waypoint> paramHashSet) {
        this.waypoints = paramHashSet;
    }

    public HashSet<Track> getTracks() {
        return this.tracks;
    }

    public void setTracks(HashSet<Track> paramHashSet) {
        this.tracks = paramHashSet;
    }

    public HashSet<Route> getRoutes() {
        return this.routes;
    }

    public void setRoutes(HashSet<Route> paramHashSet) {
        this.routes = paramHashSet;
    }

    public void addWaypoint(Waypoint paramWaypoint) {
        if (this.waypoints == null) {
            this.waypoints = new HashSet<>();
        }
        this.waypoints.add(paramWaypoint);
    }

    public void addTrack(Track paramTrack) {
        if (this.tracks == null) {
            this.tracks = new HashSet<>();
        }
        this.tracks.add(paramTrack);
    }

    public void addRoute(Route paramRoute) {
        if (this.routes == null) {
            this.routes = new HashSet<>();
        }
        this.routes.add(paramRoute);
    }
}
