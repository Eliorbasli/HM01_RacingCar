package com.example.hm01_racingcar;

public class Record {
    private int score;
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
}