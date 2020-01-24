package com.sweetzpot.stravazpot.authenticaton.model;

public class AppCredentials {
    private final int clientID;
    private final String clientSecret;

    public static AppCredentials with(int clientID2, String clientSecret2) {
        return new AppCredentials(clientID2, clientSecret2);
    }

    public AppCredentials(int clientID2, String clientSecret2) {
        this.clientID = clientID2;
        this.clientSecret = clientSecret2;
    }

    public int getClientID() {
        return this.clientID;
    }

    public String getClientSecret() {
        return this.clientSecret;
    }
}
