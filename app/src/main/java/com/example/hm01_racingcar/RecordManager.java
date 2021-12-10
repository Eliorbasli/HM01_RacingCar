package com.example.hm01_racingcar;

import java.util.ArrayList;

public class RecordManager {
    public static ArrayList<Record> generateRecord(){
        ArrayList<Record> records = new ArrayList<>();

        records.add(new Record()
                .setScore(12)
                .setMyLocation(new MyLocation().
                        setLatitube(31.25331).
                        setLongitube(14.23553)));

        records.add(new Record()
                .setScore(55)
                .setMyLocation(new MyLocation().
                        setLatitube(15.25331).
                        setLongitube(25.23553)));
        return records;
    }
}
