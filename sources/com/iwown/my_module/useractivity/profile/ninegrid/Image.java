package com.iwown.my_module.useractivity.profile.ninegrid;

public class Image {
    private int height;
    private int resId;
    private String url;
    private int width;

    public Image(String url2, int width2, int height2) {
        this.url = url2;
        this.width = width2;
        this.height = height2;
    }

    public Image(int resId2, int width2, int height2) {
        this.resId = resId2;
        this.width = width2;
        this.height = height2;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url2) {
        this.url = url2;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width2) {
        this.width = width2;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height2) {
        this.height = height2;
    }

    public int getResId() {
        return this.resId;
    }

    public void setResId(int resId2) {
        this.resId = resId2;
    }

    public String toString() {
        return "image---->>url=" + this.url + "width=" + this.width + "height" + this.height;
    }
}
