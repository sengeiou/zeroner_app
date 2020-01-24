package com.sweetzpot.stravazpot.athlete.model;

import com.google.gson.annotations.SerializedName;
import com.sweetzpot.stravazpot.common.model.Distance;

public class Stats {
    @SerializedName("all_ride_totals")
    private Totals allRideTotals;
    @SerializedName("all_run_totals")
    private Totals allRunTotals;
    @SerializedName("all_swim_totals")
    private Totals allSwimTotals;
    @SerializedName("biggest_climb_elevation_gain")
    private Distance biggestClimbElevationGain;
    @SerializedName("biggest_ride_distance")
    private Distance biggestRideDistance;
    @SerializedName("recent_ride_totals")
    private Totals recentRideTotals;
    @SerializedName("recent_run_totals")
    private Totals recentRunTotals;
    @SerializedName("recent_swim_totals")
    private Totals recentSwimTotals;
    @SerializedName("ytd_ride_totals")
    private Totals yearToDateRideTotals;
    @SerializedName("ytd_run_totals")
    private Totals yearToDateRunTotals;
    @SerializedName("ytd_swim_totals")
    private Totals yearToDateSwimTotals;

    public Distance getBiggestRideDistance() {
        return this.biggestRideDistance;
    }

    public Distance getBiggestClimbElevationGain() {
        return this.biggestClimbElevationGain;
    }

    public Totals getRecentRideTotals() {
        return this.recentRideTotals;
    }

    public Totals getRecentRunTotals() {
        return this.recentRunTotals;
    }

    public Totals getRecentSwimTotals() {
        return this.recentSwimTotals;
    }

    public Totals getYearToDateRideTotals() {
        return this.yearToDateRideTotals;
    }

    public Totals getYearToDateRunTotals() {
        return this.yearToDateRunTotals;
    }

    public Totals getYearToDateSwimTotals() {
        return this.yearToDateSwimTotals;
    }

    public Totals getAllRideTotals() {
        return this.allRideTotals;
    }

    public Totals getAllRunTotals() {
        return this.allRunTotals;
    }

    public Totals getAllSwimTotals() {
        return this.allSwimTotals;
    }
}
