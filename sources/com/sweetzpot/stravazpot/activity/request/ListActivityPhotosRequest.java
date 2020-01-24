package com.sweetzpot.stravazpot.activity.request;

import com.sweetzpot.stravazpot.activity.api.PhotoAPI;
import com.sweetzpot.stravazpot.activity.model.Photo;
import com.sweetzpot.stravazpot.activity.rest.PhotosRest;
import java.util.List;

public class ListActivityPhotosRequest {
    private final int activityID;
    private final PhotoAPI api;
    private final PhotosRest restService;

    public ListActivityPhotosRequest(int activityID2, PhotosRest restService2, PhotoAPI api2) {
        this.activityID = activityID2;
        this.restService = restService2;
        this.api = api2;
    }

    public List<Photo> execute() {
        return (List) this.api.execute(this.restService.getActivityPhotos(Integer.valueOf(this.activityID), Boolean.valueOf(true)));
    }
}
