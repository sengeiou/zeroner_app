package com.sweetzpot.stravazpot.authenticaton.api;

public enum ApprovalPrompt {
    FORCE("force"),
    AUTO("auto");
    
    private String rawValue;

    private ApprovalPrompt(String rawValue2) {
        this.rawValue = rawValue2;
    }

    public String toString() {
        return this.rawValue;
    }
}
