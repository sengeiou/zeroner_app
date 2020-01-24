package com.iwown.sport_module.pojo.active;

public class GalleryData {
    private int active_time_h;
    private int active_time_min;
    private int day;
    private float distance;
    private int galleryCal;
    private String galleryDay;
    private int galleryStand;
    private int galleryStep;
    private int month;
    private int year;

    public int getYear() {
        return this.year;
    }

    public void setYear(int year2) {
        this.year = year2;
    }

    public int getMonth() {
        return this.month;
    }

    public void setMonth(int month2) {
        this.month = month2;
    }

    public int getDay() {
        return this.day;
    }

    public void setDay(int day2) {
        this.day = day2;
    }

    public float getDistance() {
        return this.distance;
    }

    public void setDistance(float distance2) {
        this.distance = distance2;
    }

    public GalleryData() {
    }

    public GalleryData(int year2, int month2, int day2, int galleryCal2, int galleryStep2, int galleryStand2, int active_time_h2, int active_time_min2, int distance2) {
        this.year = year2;
        this.month = month2;
        this.day = day2;
        this.galleryDay = month2 + "/" + day2;
        this.galleryDay = this.galleryDay;
        this.galleryCal = galleryCal2;
        this.galleryStep = galleryStep2;
        this.galleryStand = galleryStand2;
        this.active_time_h = active_time_h2;
        this.active_time_min = active_time_min2;
        this.distance = (float) distance2;
    }

    public int getActive_time_h() {
        return this.active_time_h;
    }

    public void setActive_time_h(int active_time_h2) {
        this.active_time_h = active_time_h2;
    }

    public int getActive_time_min() {
        return this.active_time_min;
    }

    public void setActive_time_min(int active_time_min2) {
        this.active_time_min = active_time_min2;
    }

    public int getGalleryStand() {
        return this.galleryStand;
    }

    public void setGalleryStand(int galleryStand2) {
        this.galleryStand = galleryStand2;
    }

    public int getGalleryStep() {
        return this.galleryStep;
    }

    public void setGalleryStep(int galleryStep2) {
        this.galleryStep = galleryStep2;
    }

    public String getGalleryDay() {
        return this.galleryDay;
    }

    public void setGalleryDay(String galleryDay2) {
        this.galleryDay = galleryDay2;
    }

    public int getGalleryCal() {
        return this.galleryCal;
    }

    public void setGalleryCal(int galleryCal2) {
        this.galleryCal = galleryCal2;
    }
}
