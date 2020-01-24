package com.sweetzpot.stravazpot.club.api;

import com.sweetzpot.stravazpot.club.request.GetClubRequest;
import com.sweetzpot.stravazpot.club.request.JoinClubRequest;
import com.sweetzpot.stravazpot.club.request.LeaveClubRequest;
import com.sweetzpot.stravazpot.club.request.ListClubActivitiesRequest;
import com.sweetzpot.stravazpot.club.request.ListClubAdminsRequest;
import com.sweetzpot.stravazpot.club.request.ListClubAnnouncementsRequest;
import com.sweetzpot.stravazpot.club.request.ListClubGroupEventsRequest;
import com.sweetzpot.stravazpot.club.request.ListClubMembersRequest;
import com.sweetzpot.stravazpot.club.request.ListMyClubsRequest;
import com.sweetzpot.stravazpot.club.rest.ClubRest;
import com.sweetzpot.stravazpot.common.api.StravaAPI;
import com.sweetzpot.stravazpot.common.api.StravaConfig;

public class ClubAPI extends StravaAPI {
    public ClubAPI(StravaConfig config) {
        super(config);
    }

    public GetClubRequest getClub(int clubID) {
        return new GetClubRequest(clubID, (ClubRest) getAPI(ClubRest.class), this);
    }

    public ListClubAnnouncementsRequest listClubAnnouncements(int clubID) {
        return new ListClubAnnouncementsRequest(clubID, (ClubRest) getAPI(ClubRest.class), this);
    }

    public ListClubGroupEventsRequest listClubGroupEvents(int clubID) {
        return new ListClubGroupEventsRequest(clubID, (ClubRest) getAPI(ClubRest.class), this);
    }

    public ListMyClubsRequest listMyClubs() {
        return new ListMyClubsRequest((ClubRest) getAPI(ClubRest.class), this);
    }

    public ListClubMembersRequest listClubMembers(int clubID) {
        return new ListClubMembersRequest(clubID, (ClubRest) getAPI(ClubRest.class), this);
    }

    public ListClubAdminsRequest listClubAdmins(int clubID) {
        return new ListClubAdminsRequest(clubID, (ClubRest) getAPI(ClubRest.class), this);
    }

    public ListClubActivitiesRequest listClubActivities(int clubID) {
        return new ListClubActivitiesRequest(clubID, (ClubRest) getAPI(ClubRest.class), this);
    }

    public JoinClubRequest joinClub(int clubID) {
        return new JoinClubRequest(clubID, (ClubRest) getAPI(ClubRest.class), this);
    }

    public LeaveClubRequest leaveClub(int clubID) {
        return new LeaveClubRequest(clubID, (ClubRest) getAPI(ClubRest.class), this);
    }
}
