package com.sweetzpot.stravazpot.common.model;

public class Time {
    private int seconds;

    public static Time seconds(int seconds2) {
        return new Time(seconds2);
    }

    public Time(int seconds2) {
        this.seconds = seconds2;
    }

    public int getSeconds() {
        return this.seconds;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (this.seconds != ((Time) o).seconds) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return this.seconds;
    }

    public String toString() {
        return String.valueOf(this.seconds);
    }
}
