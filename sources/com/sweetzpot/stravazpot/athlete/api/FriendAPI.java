package com.sweetzpot.stravazpot.athlete.api;

import com.sweetzpot.stravazpot.athlete.request.GetAthleteFollowersRequest;
import com.sweetzpot.stravazpot.athlete.request.GetAthleteFriendsRequest;
import com.sweetzpot.stravazpot.athlete.request.GetBothFollowingRequest;
import com.sweetzpot.stravazpot.athlete.request.GetMyFollowersRequest;
import com.sweetzpot.stravazpot.athlete.request.GetMyFriendsRequest;
import com.sweetzpot.stravazpot.athlete.rest.FriendRest;
import com.sweetzpot.stravazpot.common.api.StravaAPI;
import com.sweetzpot.stravazpot.common.api.StravaConfig;

public class FriendAPI extends StravaAPI {
    public FriendAPI(StravaConfig config) {
        super(config);
    }

    public GetMyFriendsRequest getMyFriends() {
        return new GetMyFriendsRequest((FriendRest) getAPI(FriendRest.class), this);
    }

    public GetAthleteFriendsRequest getAthleteFriends(int athleteID) {
        return new GetAthleteFriendsRequest(athleteID, (FriendRest) getAPI(FriendRest.class), this);
    }

    public GetMyFollowersRequest getMyFollowers() {
        return new GetMyFollowersRequest((FriendRest) getAPI(FriendRest.class), this);
    }

    public GetAthleteFollowersRequest getAthleteFollowers(int athleteID) {
        return new GetAthleteFollowersRequest(athleteID, (FriendRest) getAPI(FriendRest.class), this);
    }

    public GetBothFollowingRequest getBothFollowing(int athleteID) {
        return new GetBothFollowingRequest(athleteID, (FriendRest) getAPI(FriendRest.class), this);
    }
}
