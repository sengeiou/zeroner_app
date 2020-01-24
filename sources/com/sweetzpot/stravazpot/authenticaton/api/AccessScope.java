package com.sweetzpot.stravazpot.authenticaton.api;

public enum AccessScope {
    PUBLIC("public"),
    WRITE("write"),
    VIEW_PRIVATE("view_private"),
    VIEW_PRIVATE_WRITE("view_private,write");
    
    private String rawValue;

    private AccessScope(String rawValue2) {
        this.rawValue = rawValue2;
    }

    public String toString() {
        return this.rawValue;
    }
}
