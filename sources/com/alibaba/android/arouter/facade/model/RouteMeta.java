package com.alibaba.android.arouter.facade.model;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.enums.RouteType;
import java.util.Map;
import javax.lang.model.element.Element;

public class RouteMeta {
    private Class<?> destination;
    private int extra;
    private String group;
    private Map<String, Integer> paramsType;
    private String path;
    private int priority;
    private Element rawType;
    private RouteType type;

    public RouteMeta() {
        this.priority = -1;
    }

    public static RouteMeta build(RouteType type2, Class<?> destination2, String path2, String group2, int priority2, int extra2) {
        return new RouteMeta(type2, null, destination2, path2, group2, null, priority2, extra2);
    }

    public static RouteMeta build(RouteType type2, Class<?> destination2, String path2, String group2, Map<String, Integer> paramsType2, int priority2, int extra2) {
        return new RouteMeta(type2, null, destination2, path2, group2, paramsType2, priority2, extra2);
    }

    public RouteMeta(Route route, Class<?> destination2, RouteType type2) {
        this(type2, null, destination2, route.path(), route.group(), null, route.priority(), route.extras());
    }

    public RouteMeta(Route route, Element rawType2, RouteType type2, Map<String, Integer> paramsType2) {
        this(type2, rawType2, null, route.path(), route.group(), paramsType2, route.priority(), route.extras());
    }

    public RouteMeta(RouteType type2, Element rawType2, Class<?> destination2, String path2, String group2, Map<String, Integer> paramsType2, int priority2, int extra2) {
        this.priority = -1;
        this.type = type2;
        this.destination = destination2;
        this.rawType = rawType2;
        this.path = path2;
        this.group = group2;
        this.paramsType = paramsType2;
        this.priority = priority2;
        this.extra = extra2;
    }

    public Map<String, Integer> getParamsType() {
        return this.paramsType;
    }

    public RouteMeta setParamsType(Map<String, Integer> paramsType2) {
        this.paramsType = paramsType2;
        return this;
    }

    public Element getRawType() {
        return this.rawType;
    }

    public RouteMeta setRawType(Element rawType2) {
        this.rawType = rawType2;
        return this;
    }

    public RouteType getType() {
        return this.type;
    }

    public RouteMeta setType(RouteType type2) {
        this.type = type2;
        return this;
    }

    public Class<?> getDestination() {
        return this.destination;
    }

    public RouteMeta setDestination(Class<?> destination2) {
        this.destination = destination2;
        return this;
    }

    public String getPath() {
        return this.path;
    }

    public RouteMeta setPath(String path2) {
        this.path = path2;
        return this;
    }

    public String getGroup() {
        return this.group;
    }

    public RouteMeta setGroup(String group2) {
        this.group = group2;
        return this;
    }

    public int getPriority() {
        return this.priority;
    }

    public RouteMeta setPriority(int priority2) {
        this.priority = priority2;
        return this;
    }

    public int getExtra() {
        return this.extra;
    }

    public RouteMeta setExtra(int extra2) {
        this.extra = extra2;
        return this;
    }

    public String toString() {
        return "RouteMeta{type=" + this.type + ", rawType=" + this.rawType + ", destination=" + this.destination + ", path='" + this.path + '\'' + ", group='" + this.group + '\'' + ", priority=" + this.priority + ", extra=" + this.extra + '}';
    }
}
