package com.sweetzpot.stravazpot.authenticaton.model;

public class Token {
    private String value;

    public Token(String value2) {
        this.value = value2;
    }

    public String toString() {
        return "Bearer " + this.value;
    }
}
