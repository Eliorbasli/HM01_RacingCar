package com.example.hm01_racingcar.Models;

public class Record {
    private int score;
    private double lat = 0.0;
    private double lon = 0.0;
    private MyLocation myLocation;

    public Record() {

    }

    public Record(int score, MyLocation myLocation) {
        this.score = score;
        this.myLocation = myLocation;
    }

    public int getScore() {
        return score;
    }

    public Record setScore(int score) {
        this.score = score;
        return this;
    }

    public MyLocation getMyLocation() {
        return myLocation;
    }

    public Record setMyLocation(MyLocation myLocation) {
        this.myLocation = myLocation;
        return this;
    }

    public double getLat() {
        return lat;
    }

    public Record setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public double getLon() {
        return lon;
    }

    public Record setLon(double lon) {
        this.lon = lon;
        return this;
    }
}