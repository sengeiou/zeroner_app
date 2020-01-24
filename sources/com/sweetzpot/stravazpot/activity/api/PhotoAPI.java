package com.sweetzpot.stravazpot.activity.api;

import com.sweetzpot.stravazpot.activity.request.ListActivityPhotosRequest;
import com.sweetzpot.stravazpot.activity.rest.PhotosRest;
import com.sweetzpot.stravazpot.common.api.StravaAPI;
import com.sweetzpot.stravazpot.common.api.StravaConfig;

public class PhotoAPI extends StravaAPI {
    public PhotoAPI(StravaConfig config) {
        super(config);
    }

    public ListActivityPhotosRequest listAcivityPhotos(int activityID) {
        return new ListActivityPhotosRequest(activityID, (PhotosRest) getAPI(PhotosRest.class), this);
    }
}
