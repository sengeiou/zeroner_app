package com.iwown.data_link.sport_data.gps;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class Waypoint extends Extension {
    private Double ageOfGPSData;
    private String comment;
    private String description;
    private Integer dgpsid;
    private Double elevation;
    private FixType fix;
    private Double geoidHeight;
    private Double hdop;
    private Double latitude;
    private Double longitude;
    private Double magneticDeclination;
    private String name;
    private Double pdop;
    private Integer sat;
    private String src;
    private String sym;
    private Date time;
    private String type;
    private Double vdop;

    public Double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(Double paramDouble) {
        this.latitude = paramDouble;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(Double paramDouble) {
        this.longitude = paramDouble;
    }

    public Double getElevation() {
        return this.elevation;
    }

    public void setElevation(Double paramDouble) {
        this.elevation = paramDouble;
    }

    public Date getTime() {
        return this.time;
    }

    public void setTime(Date paramDate) {
        this.time = paramDate;
    }

    public Double getMagneticDeclination() {
        return this.magneticDeclination;
    }

    public void setMagneticDeclination(Double paramDouble) {
        this.magneticDeclination = paramDouble;
    }

    public Double getGeoidHeight() {
        return this.geoidHeight;
    }

    public void setGeoidHeight(Double paramDouble) {
        this.geoidHeight = paramDouble;
    }

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

    public String getSym() {
        return this.sym;
    }

    public void setSym(String paramString) {
        this.sym = paramString;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String paramString) {
        this.type = paramString;
    }

    public FixType getFix() {
        return this.fix;
    }

    public void setFix(FixType paramFixType) {
        this.fix = paramFixType;
    }

    public Integer getSat() {
        return this.sat;
    }

    public void setSat(Integer paramInteger) {
        this.sat = paramInteger;
    }

    public Double getHdop() {
        return this.hdop;
    }

    public void setHdop(Double paramDouble) {
        this.hdop = paramDouble;
    }

    public Double getVdop() {
        return this.vdop;
    }

    public void setVdop(Double paramDouble) {
        this.vdop = paramDouble;
    }

    public Double getPdop() {
        return this.pdop;
    }

    public void setPdop(Double paramDouble) {
        this.pdop = paramDouble;
    }

    public Double getAgeOfGPSData() {
        return this.ageOfGPSData;
    }

    public void setAgeOfGPSData(Double paramDouble) {
        this.ageOfGPSData = paramDouble;
    }

    public Integer getDgpsid() {
        return this.dgpsid;
    }

    public void setDgpsid(Integer paramInteger) {
        this.dgpsid = paramInteger;
    }

    public String toString() {
        StringBuffer localStringBuffer = new StringBuffer();
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String str = "";
        if (this.time != null) {
            str = localSimpleDateFormat.format(this.time);
        }
        localStringBuffer.append("[");
        localStringBuffer.append("name:'" + this.name + "' ");
        localStringBuffer.append("lat:" + this.latitude + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
        localStringBuffer.append("lon:" + this.longitude + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
        localStringBuffer.append("elv:" + this.elevation + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
        localStringBuffer.append("time:" + str + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
        localStringBuffer.append("fix:" + this.fix + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
        if (this.extensionData != null) {
            localStringBuffer.append("extensions:{");
            Iterator localIterator = this.extensionData.keySet().iterator();
            while (localIterator.hasNext()) {
                localStringBuffer.append((String) localIterator.next());
                if (localIterator.hasNext()) {
                    localStringBuffer.append(",");
                }
            }
            localStringBuffer.append("}");
        }
        localStringBuffer.append("]");
        return localStringBuffer.toString();
    }
}
