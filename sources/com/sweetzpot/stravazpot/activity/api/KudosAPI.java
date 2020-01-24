package com.sweetzpot.stravazpot.activity.api;

import com.sweetzpot.stravazpot.activity.request.ListActivityKudoersRequest;
import com.sweetzpot.stravazpot.activity.rest.KudosRest;
import com.sweetzpot.stravazpot.common.api.StravaAPI;
import com.sweetzpot.stravazpot.common.api.StravaConfig;

public class KudosAPI extends StravaAPI {
    public KudosAPI(StravaConfig config) {
        super(config);
    }

    public ListActivityKudoersRequest listActivityKudoers(int activityID) {
        return new ListActivityKudoersRequest(activityID, (KudosRest) getAPI(KudosRest.class), this);
    }
}
