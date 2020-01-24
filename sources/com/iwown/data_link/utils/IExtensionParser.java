package com.iwown.data_link.utils;

import com.iwown.data_link.sport_data.gps.GPX;
import com.iwown.data_link.sport_data.gps.Route;
import com.iwown.data_link.sport_data.gps.Track;
import com.iwown.data_link.sport_data.gps.Waypoint;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public interface IExtensionParser {
    String getId();

    Object parseGPXExtension(Node node);

    Object parseRouteExtension(Node node);

    Object parseTrackExtension(Node node);

    Object parseWaypointExtension(Node node);

    void writeGPXExtensionData(Node node, GPX gpx, Document document);

    void writeRouteExtensionData(Node node, Route route, Document document);

    void writeTrackExtensionData(Node node, Track track, Document document);

    void writeWaypointExtensionData(Node node, Waypoint waypoint, Document document);
}
