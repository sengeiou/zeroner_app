package com.iwown.ble_module;

public class WristBand {
    private String dev_addr;
    private String dev_name;

    public WristBand(String dev_name2, String dev_addr2) {
        this.dev_name = dev_name2;
        this.dev_addr = dev_addr2;
    }

    public String getDev_name() {
        return this.dev_name;
    }

    public void setDev_name(String dev_name2) {
        this.dev_name = dev_name2;
    }

    public String getDev_addr() {
        return this.dev_addr;
    }

    public void setDev_addr(String dev_addr2) {
        this.dev_addr = dev_addr2;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WristBand)) {
            return false;
        }
        WristBand band = (WristBand) o;
        if (getDev_name().equals(band.getDev_name())) {
            return getDev_addr().equals(band.getDev_addr());
        }
        return false;
    }

    public int hashCode() {
        return (getDev_name().hashCode() * 31) + getDev_addr().hashCode();
    }

    public String toString() {
        return "WristBand{dev_name='" + this.dev_name + '\'' + ", dev_addr='" + this.dev_addr + '\'' + '}';
    }
}
