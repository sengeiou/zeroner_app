package com.iwown.data_link.utils;

import com.iwown.data_link.sport_data.gps.FixType;
import com.iwown.data_link.sport_data.gps.GPX;
import com.iwown.data_link.sport_data.gps.Route;
import com.iwown.data_link.sport_data.gps.Track;
import com.iwown.data_link.sport_data.gps.Waypoint;
import com.tencent.open.SocialConstants;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.litepal.util.Const.TableSchema;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class GPXParser {
    private ArrayList<IExtensionParser> extensionParsers = new ArrayList<>();

    public void addExtensionParser(IExtensionParser paramIExtensionParser) {
        this.extensionParsers.add(paramIExtensionParser);
    }

    public void removeExtensionParser(IExtensionParser paramIExtensionParser) {
        this.extensionParsers.remove(paramIExtensionParser);
    }

    public GPX parseGPX(InputStream paramInputStream) throws ParserConfigurationException, SAXException, IOException {
        Node localNode1 = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(paramInputStream).getFirstChild();
        if (localNode1 == null || !"gpx".equals(localNode1.getNodeName())) {
            return null;
        }
        GPX localGPX = new GPX();
        NamedNodeMap localNamedNodeMap = localNode1.getAttributes();
        for (int i = 0; i < localNamedNodeMap.getLength(); i++) {
            Node localNode2 = localNamedNodeMap.item(i);
            if ("version".equals(localNode2.getNodeName())) {
                localGPX.setVersion(localNode2.getNodeValue());
            } else if ("creator".equals(localNode2.getNodeName())) {
                localGPX.setCreator(localNode2.getNodeValue());
            }
        }
        NodeList localNodeList = localNode1.getChildNodes();
        for (int j = 0; j < localNodeList.getLength(); j++) {
            Node localNode3 = localNodeList.item(j);
            if ("wpt".equals(localNode3.getNodeName())) {
                Waypoint localObject1 = parseWaypoint(localNode3);
                if (localObject1 != null) {
                    localGPX.addWaypoint(localObject1);
                }
            } else if ("trk".equals(localNode3.getNodeName())) {
                Track localObject12 = parseTrack(localNode3);
                if (localObject12 != null) {
                    localGPX.addTrack(localObject12);
                }
            } else if ("extensions".equals(localNode3.getNodeName())) {
                Iterator localObject13 = this.extensionParsers.iterator();
                while (localObject13.hasNext()) {
                    IExtensionParser localIExtensionParser = (IExtensionParser) localObject13.next();
                    localGPX.addExtensionData(localIExtensionParser.getId(), localIExtensionParser.parseGPXExtension(localNode3));
                }
            } else if ("rte".equals(localNode3.getNodeName())) {
                Route localObject14 = parseRoute(localNode3);
                if (localObject14 != null) {
                    localGPX.addRoute(localObject14);
                }
            }
        }
        return localGPX;
    }

    private Waypoint parseWaypoint(Node paramNode) {
        if (paramNode == null) {
            return null;
        }
        Waypoint localWaypoint = new Waypoint();
        NamedNodeMap localNamedNodeMap = paramNode.getAttributes();
        Node localNode1 = localNamedNodeMap.getNamedItem("lat");
        if (localNode1 != null) {
            Object localObject1 = null;
            try {
                localObject1 = Double.valueOf(Double.parseDouble(localNode1.getNodeValue()));
            } catch (NumberFormatException e) {
            }
            localWaypoint.setLatitude((Double) localObject1);
        }
        Node localObject12 = localNamedNodeMap.getNamedItem("lon");
        if (localObject12 != null) {
            Object localObject2 = null;
            try {
                localObject2 = Double.valueOf(Double.parseDouble(localObject12.getNodeValue()));
            } catch (NumberFormatException e2) {
            }
            localWaypoint.setLongitude((Double) localObject2);
        }
        NodeList localObject22 = paramNode.getChildNodes();
        if (localObject22 == null) {
            return localWaypoint;
        }
        for (int i = 0; i < localObject22.getLength(); i++) {
            Node localNode2 = localObject22.item(i);
            if ("ele".equals(localNode2.getNodeName())) {
                localWaypoint.setElevation(getNodeValueAsDouble(localNode2));
            } else if ("time".equals(localNode2.getNodeName())) {
                localWaypoint.setTime(getNodeValueAsDate(localNode2));
            } else if (TableSchema.COLUMN_NAME.equals(localNode2.getNodeName())) {
                localWaypoint.setName(getNodeValueAsString(localNode2));
            } else if ("cmt".equals(localNode2.getNodeName())) {
                localWaypoint.setComment(getNodeValueAsString(localNode2));
            } else if (SocialConstants.PARAM_APP_DESC.equals(localNode2.getNodeName())) {
                localWaypoint.setDescription(getNodeValueAsString(localNode2));
            } else if ("src".equals(localNode2.getNodeName())) {
                localWaypoint.setSrc(getNodeValueAsString(localNode2));
            } else if ("magvar".equals(localNode2.getNodeName())) {
                localWaypoint.setMagneticDeclination(getNodeValueAsDouble(localNode2));
            } else if ("geoidheight".equals(localNode2.getNodeName())) {
                localWaypoint.setGeoidHeight(getNodeValueAsDouble(localNode2));
            } else if (!"link".equals(localNode2.getNodeName())) {
                if ("sym".equals(localNode2.getNodeName())) {
                    localWaypoint.setSym(getNodeValueAsString(localNode2));
                } else if ("fix".equals(localNode2.getNodeName())) {
                    localWaypoint.setFix(getNodeValueAsFixType(localNode2));
                } else if ("type".equals(localNode2.getNodeName())) {
                    localWaypoint.setType(getNodeValueAsString(localNode2));
                } else if ("sat".equals(localNode2.getNodeName())) {
                    localWaypoint.setSat(getNodeValueAsInteger(localNode2));
                } else if ("hdop".equals(localNode2.getNodeName())) {
                    localWaypoint.setHdop(getNodeValueAsDouble(localNode2));
                } else if ("vdop".equals(localNode2.getNodeName())) {
                    localWaypoint.setVdop(getNodeValueAsDouble(localNode2));
                } else if ("pdop".equals(localNode2.getNodeName())) {
                    localWaypoint.setPdop(getNodeValueAsDouble(localNode2));
                } else if ("ageofdgpsdata".equals(localNode2.getNodeName())) {
                    localWaypoint.setAgeOfGPSData(getNodeValueAsDouble(localNode2));
                } else if ("dgpsid".equals(localNode2.getNodeName())) {
                    localWaypoint.setDgpsid(getNodeValueAsInteger(localNode2));
                } else if ("extensions".equals(localNode2.getNodeName())) {
                    Iterator localIterator = this.extensionParsers.iterator();
                    while (localIterator.hasNext()) {
                        IExtensionParser localIExtensionParser = (IExtensionParser) localIterator.next();
                        localWaypoint.addExtensionData(localIExtensionParser.getId(), localIExtensionParser.parseWaypointExtension(localNode2));
                    }
                }
            }
        }
        return localWaypoint;
    }

    private Track parseTrack(Node paramNode) {
        if (paramNode == null) {
            return null;
        }
        Track localTrack = new Track();
        NodeList localNodeList = paramNode.getChildNodes();
        if (localNodeList == null) {
            return localTrack;
        }
        for (int i = 0; i < localNodeList.getLength(); i++) {
            Node localNode = localNodeList.item(i);
            if (TableSchema.COLUMN_NAME.equals(localNode.getNodeName())) {
                localTrack.setName(getNodeValueAsString(localNode));
            } else if ("cmt".equals(localNode.getNodeName())) {
                localTrack.setComment(getNodeValueAsString(localNode));
            } else if (SocialConstants.PARAM_APP_DESC.equals(localNode.getNodeName())) {
                localTrack.setDescription(getNodeValueAsString(localNode));
            } else if ("src".equals(localNode.getNodeName())) {
                localTrack.setSrc(getNodeValueAsString(localNode));
            } else if (!"link".equals(localNode.getNodeName())) {
                if ("number".equals(localNode.getNodeName())) {
                    localTrack.setNumber(getNodeValueAsInteger(localNode));
                } else if ("type".equals(localNode.getNodeName())) {
                    localTrack.setType(getNodeValueAsString(localNode));
                } else if ("trkseg".equals(localNode.getNodeName())) {
                    localTrack.setTrackPoints(parseTrackSeg(localNode));
                } else if ("extensions".equals(localNode.getNodeName())) {
                    Iterator localIterator = this.extensionParsers.iterator();
                    while (localIterator.hasNext()) {
                        while (localIterator.hasNext()) {
                            IExtensionParser localIExtensionParser = (IExtensionParser) localIterator.next();
                            localTrack.addExtensionData(localIExtensionParser.getId(), localIExtensionParser.parseTrackExtension(localNode));
                        }
                    }
                }
            }
        }
        return localTrack;
    }

    private Route parseRoute(Node paramNode) {
        if (paramNode == null) {
            return null;
        }
        Route localRoute = new Route();
        NodeList localNodeList = paramNode.getChildNodes();
        if (localNodeList == null) {
            return localRoute;
        }
        for (int i = 0; i < localNodeList.getLength(); i++) {
            Node localNode = localNodeList.item(i);
            if (TableSchema.COLUMN_NAME.equals(localNode.getNodeName())) {
                localRoute.setName(getNodeValueAsString(localNode));
            } else if ("cmt".equals(localNode.getNodeName())) {
                localRoute.setComment(getNodeValueAsString(localNode));
            } else if (SocialConstants.PARAM_APP_DESC.equals(localNode.getNodeName())) {
                localRoute.setDescription(getNodeValueAsString(localNode));
            } else if ("src".equals(localNode.getNodeName())) {
                localRoute.setSrc(getNodeValueAsString(localNode));
            } else if (!"link".equals(localNode.getNodeName())) {
                if ("number".equals(localNode.getNodeName())) {
                    localRoute.setNumber(getNodeValueAsInteger(localNode));
                } else if ("type".equals(localNode.getNodeName())) {
                    localRoute.setType(getNodeValueAsString(localNode));
                } else if ("rtept".equals(localNode.getNodeName())) {
                    Waypoint localObject1 = parseWaypoint(localNode);
                    if (localObject1 != null) {
                        localRoute.addRoutePoint(localObject1);
                    }
                } else if ("extensions".equals(localNode.getNodeName())) {
                    Iterator localObject12 = this.extensionParsers.iterator();
                    while (localObject12.hasNext()) {
                        while (localObject12.hasNext()) {
                            IExtensionParser localIExtensionParser = (IExtensionParser) localObject12.next();
                            localRoute.addExtensionData(localIExtensionParser.getId(), localIExtensionParser.parseRouteExtension(localNode));
                        }
                    }
                }
            }
        }
        return localRoute;
    }

    private ArrayList<Waypoint> parseTrackSeg(Node paramNode) {
        if (paramNode == null) {
            return null;
        }
        ArrayList localArrayList = new ArrayList();
        NodeList localNodeList = paramNode.getChildNodes();
        if (localNodeList == null) {
            return localArrayList;
        }
        for (int i = 0; i < localNodeList.getLength(); i++) {
            Node localNode = localNodeList.item(i);
            if ("trkpt".equals(localNode.getNodeName())) {
                Waypoint localWaypoint = parseWaypoint(localNode);
                if (localWaypoint != null) {
                    localArrayList.add(localWaypoint);
                }
            } else if (!"extensions".equals(localNode.getNodeName())) {
            }
        }
        return localArrayList;
    }

    private Double getNodeValueAsDouble(Node paramNode) {
        Double localDouble = null;
        try {
            return Double.valueOf(Double.parseDouble(paramNode.getFirstChild().getNodeValue()));
        } catch (Exception e) {
            return localDouble;
        }
    }

    private Date getNodeValueAsDate(Node paramNode) {
        Date localDate = null;
        try {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(paramNode.getFirstChild().getNodeValue());
        } catch (Exception e) {
            return localDate;
        }
    }

    private String getNodeValueAsString(Node paramNode) {
        String str = null;
        try {
            return paramNode.getFirstChild().getNodeValue();
        } catch (Exception e) {
            return str;
        }
    }

    private FixType getNodeValueAsFixType(Node paramNode) {
        FixType localFixType = null;
        try {
            return FixType.returnType(paramNode.getFirstChild().getNodeValue());
        } catch (Exception e) {
            return localFixType;
        }
    }

    private Integer getNodeValueAsInteger(Node paramNode) {
        Integer localInteger = null;
        try {
            return Integer.valueOf(Integer.parseInt(paramNode.getFirstChild().getNodeValue()));
        } catch (Exception e) {
            return localInteger;
        }
    }

    public void writeGPX(GPX paramGPX, OutputStream paramOutputStream) throws ParserConfigurationException, TransformerException {
        Document localDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Element localElement = localDocument.createElement("gpx");
        addBasicGPXInfoToNode(paramGPX, localElement, localDocument);
        if (paramGPX.getWaypoints() != null) {
            Iterator localObject = paramGPX.getWaypoints().iterator();
            while (localObject.hasNext()) {
                addWaypointToGPXNode((Waypoint) localObject.next(), localElement, localDocument);
            }
        }
        if (paramGPX.getTracks() != null) {
            Iterator localObject2 = paramGPX.getTracks().iterator();
            while (localObject2.hasNext()) {
                addTrackToGPXNode((Track) localObject2.next(), localElement, localDocument);
            }
        }
        if (paramGPX.getRoutes() != null) {
            Iterator localObject3 = paramGPX.getRoutes().iterator();
            while (localObject3.hasNext()) {
                addRouteToGPXNode((Route) localObject3.next(), localElement, localDocument);
            }
        }
        localDocument.appendChild(localElement);
        TransformerFactory.newInstance().newTransformer().transform(new DOMSource(localDocument), new StreamResult(paramOutputStream));
    }

    private void addWaypointToGPXNode(Waypoint paramWaypoint, Node paramNode, Document paramDocument) {
        addGenericWaypointToGPXNode("wpt", paramWaypoint, paramNode, paramDocument);
    }

    private void addGenericWaypointToGPXNode(String paramString, Waypoint paramWaypoint, Node paramNode, Document paramDocument) {
        Element localElement = paramDocument.createElement(paramString);
        NamedNodeMap localNamedNodeMap = localElement.getAttributes();
        if (paramWaypoint.getLatitude() != null) {
            Attr localObject1 = paramDocument.createAttribute("lat");
            localObject1.setNodeValue(paramWaypoint.getLatitude().toString());
            localNamedNodeMap.setNamedItem(localObject1);
        }
        if (paramWaypoint.getLongitude() != null) {
            Attr localObject12 = paramDocument.createAttribute("lon");
            localObject12.setNodeValue(paramWaypoint.getLongitude().toString());
            localNamedNodeMap.setNamedItem(localObject12);
        }
        if (paramWaypoint.getElevation() != null) {
            Element localObject13 = paramDocument.createElement("ele");
            localObject13.appendChild(paramDocument.createTextNode(paramWaypoint.getElevation().toString()));
            localElement.appendChild(localObject13);
        }
        if (paramWaypoint.getTime() != null) {
            Element localObject14 = paramDocument.createElement("time");
            SimpleDateFormat localObject2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            localObject2.setTimeZone(TimeZone.getTimeZone("GMT"));
            localObject14.appendChild(paramDocument.createTextNode(localObject2.format(paramWaypoint.getTime())));
            localElement.appendChild(localObject14);
        }
        if (paramWaypoint.getMagneticDeclination() != null) {
            Element localObject15 = paramDocument.createElement("magvar");
            localObject15.appendChild(paramDocument.createTextNode(paramWaypoint.getMagneticDeclination().toString()));
            localElement.appendChild(localObject15);
        }
        if (paramWaypoint.getGeoidHeight() != null) {
            Element localObject16 = paramDocument.createElement("geoidheight");
            localObject16.appendChild(paramDocument.createTextNode(paramWaypoint.getGeoidHeight().toString()));
            localElement.appendChild(localObject16);
        }
        if (paramWaypoint.getName() != null) {
            Element localObject17 = paramDocument.createElement(TableSchema.COLUMN_NAME);
            localObject17.appendChild(paramDocument.createTextNode(paramWaypoint.getName()));
            localElement.appendChild(localObject17);
        }
        if (paramWaypoint.getComment() != null) {
            Element localObject18 = paramDocument.createElement("cmt");
            localObject18.appendChild(paramDocument.createTextNode(paramWaypoint.getComment()));
            localElement.appendChild(localObject18);
        }
        if (paramWaypoint.getDescription() != null) {
            Element localObject19 = paramDocument.createElement(SocialConstants.PARAM_APP_DESC);
            localObject19.appendChild(paramDocument.createTextNode(paramWaypoint.getDescription()));
            localElement.appendChild(localObject19);
        }
        if (paramWaypoint.getSrc() != null) {
            Element localObject110 = paramDocument.createElement("src");
            localObject110.appendChild(paramDocument.createTextNode(paramWaypoint.getSrc()));
            localElement.appendChild(localObject110);
        }
        if (paramWaypoint.getSym() != null) {
            Element localObject111 = paramDocument.createElement("sym");
            localObject111.appendChild(paramDocument.createTextNode(paramWaypoint.getSym()));
            localElement.appendChild(localObject111);
        }
        if (paramWaypoint.getType() != null) {
            Element localObject112 = paramDocument.createElement("type");
            localObject112.appendChild(paramDocument.createTextNode(paramWaypoint.getType()));
            localElement.appendChild(localObject112);
        }
        if (paramWaypoint.getFix() != null) {
            Element localObject113 = paramDocument.createElement("fix");
            localObject113.appendChild(paramDocument.createTextNode(paramWaypoint.getFix().toString()));
            localElement.appendChild(localObject113);
        }
        if (paramWaypoint.getSat() != null) {
            Element localObject114 = paramDocument.createElement("sat");
            localObject114.appendChild(paramDocument.createTextNode(paramWaypoint.getSat().toString()));
            localElement.appendChild(localObject114);
        }
        if (paramWaypoint.getHdop() != null) {
            Element localObject115 = paramDocument.createElement("hdop");
            localObject115.appendChild(paramDocument.createTextNode(paramWaypoint.getHdop().toString()));
            localElement.appendChild(localObject115);
        }
        if (paramWaypoint.getVdop() != null) {
            Element localObject116 = paramDocument.createElement("vdop");
            localObject116.appendChild(paramDocument.createTextNode(paramWaypoint.getVdop().toString()));
            localElement.appendChild(localObject116);
        }
        if (paramWaypoint.getPdop() != null) {
            Element localObject117 = paramDocument.createElement("pdop");
            localObject117.appendChild(paramDocument.createTextNode(paramWaypoint.getPdop().toString()));
            localElement.appendChild(localObject117);
        }
        if (paramWaypoint.getAgeOfGPSData() != null) {
            Element localObject118 = paramDocument.createElement("ageofdgpsdata");
            localObject118.appendChild(paramDocument.createTextNode(paramWaypoint.getAgeOfGPSData().toString()));
            localElement.appendChild(localObject118);
        }
        if (paramWaypoint.getDgpsid() != null) {
            Element localObject119 = paramDocument.createElement("dgpsid");
            localObject119.appendChild(paramDocument.createTextNode(paramWaypoint.getDgpsid().toString()));
            localElement.appendChild(localObject119);
        }
        if (paramWaypoint.getExtensionsParsed() > 0) {
            Element localObject120 = paramDocument.createElement("extensions");
            Iterator localObject22 = this.extensionParsers.iterator();
            while (localObject22.hasNext()) {
                ((IExtensionParser) localObject22.next()).writeWaypointExtensionData(localObject120, paramWaypoint, paramDocument);
            }
            localElement.appendChild(localObject120);
        }
        paramNode.appendChild(localElement);
    }

    private void addTrackToGPXNode(Track paramTrack, Node paramNode, Document paramDocument) {
        Element localElement1 = paramDocument.createElement("trk");
        if (paramTrack.getName() != null) {
            Element localElement2 = paramDocument.createElement(TableSchema.COLUMN_NAME);
            localElement2.appendChild(paramDocument.createTextNode(paramTrack.getName()));
            localElement1.appendChild(localElement2);
        }
        if (paramTrack.getComment() != null) {
            Element localElement22 = paramDocument.createElement("cmt");
            localElement22.appendChild(paramDocument.createTextNode(paramTrack.getComment()));
            localElement1.appendChild(localElement22);
        }
        if (paramTrack.getDescription() != null) {
            Element localElement23 = paramDocument.createElement(SocialConstants.PARAM_APP_DESC);
            localElement23.appendChild(paramDocument.createTextNode(paramTrack.getDescription()));
            localElement1.appendChild(localElement23);
        }
        if (paramTrack.getSrc() != null) {
            Element localElement24 = paramDocument.createElement("src");
            localElement24.appendChild(paramDocument.createTextNode(paramTrack.getSrc()));
            localElement1.appendChild(localElement24);
        }
        if (paramTrack.getNumber() != null) {
            Element localElement25 = paramDocument.createElement("number");
            localElement25.appendChild(paramDocument.createTextNode(paramTrack.getNumber().toString()));
            localElement1.appendChild(localElement25);
        }
        if (paramTrack.getType() != null) {
            Element localElement26 = paramDocument.createElement("type");
            localElement26.appendChild(paramDocument.createTextNode(paramTrack.getType()));
            localElement1.appendChild(localElement26);
        }
        if (paramTrack.getExtensionsParsed() > 0) {
            Element localElement27 = paramDocument.createElement("extensions");
            Iterator localIterator = this.extensionParsers.iterator();
            while (localIterator.hasNext()) {
                ((IExtensionParser) localIterator.next()).writeTrackExtensionData(localElement27, paramTrack, paramDocument);
            }
            localElement1.appendChild(localElement27);
        }
        if (paramTrack.getTrackPoints() != null) {
            Element localElement28 = paramDocument.createElement("trkseg");
            Iterator localIterator2 = paramTrack.getTrackPoints().iterator();
            while (localIterator2.hasNext()) {
                addGenericWaypointToGPXNode("trkpt", (Waypoint) localIterator2.next(), localElement28, paramDocument);
            }
            localElement1.appendChild(localElement28);
        }
        paramNode.appendChild(localElement1);
    }

    private void addRouteToGPXNode(Route paramRoute, Node paramNode, Document paramDocument) {
        Element localElement = paramDocument.createElement("rte");
        if (paramRoute.getName() != null) {
            Element localObject = paramDocument.createElement(TableSchema.COLUMN_NAME);
            localObject.appendChild(paramDocument.createTextNode(paramRoute.getName()));
            localElement.appendChild(localObject);
        }
        if (paramRoute.getComment() != null) {
            Element localObject2 = paramDocument.createElement("cmt");
            localObject2.appendChild(paramDocument.createTextNode(paramRoute.getComment()));
            localElement.appendChild(localObject2);
        }
        if (paramRoute.getDescription() != null) {
            Element localObject3 = paramDocument.createElement(SocialConstants.PARAM_APP_DESC);
            localObject3.appendChild(paramDocument.createTextNode(paramRoute.getDescription()));
            localElement.appendChild(localObject3);
        }
        if (paramRoute.getSrc() != null) {
            Element localObject4 = paramDocument.createElement("src");
            localObject4.appendChild(paramDocument.createTextNode(paramRoute.getSrc()));
            localElement.appendChild(localObject4);
        }
        if (paramRoute.getNumber() != null) {
            Element localObject5 = paramDocument.createElement("number");
            localObject5.appendChild(paramDocument.createTextNode(paramRoute.getNumber().toString()));
            localElement.appendChild(localObject5);
        }
        if (paramRoute.getType() != null) {
            Element localObject6 = paramDocument.createElement("type");
            localObject6.appendChild(paramDocument.createTextNode(paramRoute.getType()));
            localElement.appendChild(localObject6);
        }
        if (paramRoute.getExtensionsParsed() > 0) {
            Element localObject7 = paramDocument.createElement("extensions");
            Iterator localIterator = this.extensionParsers.iterator();
            while (localIterator.hasNext()) {
                ((IExtensionParser) localIterator.next()).writeRouteExtensionData(localObject7, paramRoute, paramDocument);
            }
            localElement.appendChild(localObject7);
        }
        if (paramRoute.getRoutePoints() != null) {
            Iterator localObject8 = paramRoute.getRoutePoints().iterator();
            while (localObject8.hasNext()) {
                addGenericWaypointToGPXNode("rtept", (Waypoint) localObject8.next(), localElement, paramDocument);
            }
        }
        paramNode.appendChild(localElement);
    }

    private void addBasicGPXInfoToNode(GPX paramGPX, Node paramNode, Document paramDocument) {
        NamedNodeMap localNamedNodeMap = paramNode.getAttributes();
        if (paramGPX.getVersion() != null) {
            Attr localObject = paramDocument.createAttribute("version");
            localObject.setNodeValue(paramGPX.getVersion());
            localNamedNodeMap.setNamedItem(localObject);
        }
        if (paramGPX.getCreator() != null) {
            Attr localObject2 = paramDocument.createAttribute("creator");
            localObject2.setNodeValue(paramGPX.getCreator());
            localNamedNodeMap.setNamedItem(localObject2);
        }
        if (paramGPX.getExtensionsParsed() > 0) {
            Element localObject3 = paramDocument.createElement("extensions");
            Iterator localIterator = this.extensionParsers.iterator();
            while (localIterator.hasNext()) {
                ((IExtensionParser) localIterator.next()).writeGPXExtensionData(localObject3, paramGPX, paramDocument);
            }
            paramNode.appendChild(localObject3);
        }
    }
}
