package com.sweetzpot.stravazpot.segment.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Leaderboard {
    @SerializedName("entries")
    private List<LeaderboardEntry> entries;
    @SerializedName("entry_count")
    private int entryCount;

    public int getEntryCount() {
        return this.entryCount;
    }

    public List<LeaderboardEntry> getEntries() {
        return this.entries;
    }
}
