package com.example.hm01_racingcar;

public class MyLocation {
    private double latitube;
    private double longitube;

    public MyLocation () {
    }

    public MyLocation(double latitube , double longitube){
        this.latitube = latitube;
        this.longitube = longitube;
    }

    public double getLatitube() {
        return latitube;
    }

    public MyLocation setLatitube(double latitube) {
        this.latitube = latitube;
        return this;
    }

    public double getLongitube() {
        return longitube;
    }

    public MyLocation setLongitube(double longitube) {
        this.longitube = longitube;
        return this;
    }
}
