package com.sweetzpot.stravazpot.activity.request;

import com.sweetzpot.stravazpot.activity.api.CommentAPI;
import com.sweetzpot.stravazpot.activity.model.Comment;
import com.sweetzpot.stravazpot.activity.rest.CommentsRest;
import java.util.List;

public class ListActivityCommentsRequest {
    private final int activityID;
    private final CommentAPI api;
    private Integer page;
    private Integer perPage;
    private final CommentsRest restService;

    public ListActivityCommentsRequest(int activityID2, CommentsRest restService2, CommentAPI api2) {
        this.activityID = activityID2;
        this.restService = restService2;
        this.api = api2;
    }

    public ListActivityCommentsRequest inPage(int page2) {
        this.page = Integer.valueOf(page2);
        return this;
    }

    public ListActivityCommentsRequest perPage(int perPage2) {
        this.perPage = Integer.valueOf(perPage2);
        return this;
    }

    public List<Comment> execute() {
        return (List) this.api.execute(this.restService.getActivityComments(Integer.valueOf(this.activityID), this.page, this.perPage));
    }
}
