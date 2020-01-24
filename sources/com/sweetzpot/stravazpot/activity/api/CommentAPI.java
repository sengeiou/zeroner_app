package com.sweetzpot.stravazpot.activity.api;

import com.sweetzpot.stravazpot.activity.request.ListActivityCommentsRequest;
import com.sweetzpot.stravazpot.activity.rest.CommentsRest;
import com.sweetzpot.stravazpot.common.api.StravaAPI;
import com.sweetzpot.stravazpot.common.api.StravaConfig;

public class CommentAPI extends StravaAPI {
    public CommentAPI(StravaConfig config) {
        super(config);
    }

    public ListActivityCommentsRequest listActivityComments(int activityID) {
        return new ListActivityCommentsRequest(activityID, (CommentsRest) getAPI(CommentsRest.class), this);
    }
}
