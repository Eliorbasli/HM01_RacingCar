package com.example.hm01_racingcar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MyDb {

    public ArrayList<Record> records;
    public final static int MAX_RECORDS = 10;

    public MyDb() {

        records = new ArrayList<>();
    }

    public ArrayList<Record> getRecords() {
        return records;
    }

    public MyDb setRecords(ArrayList<Record> records) {
        this.records = records;
        return this;
    }

    public void addRecord(Record record) {
        if (records.size() < MAX_RECORDS) {
            records.add(record);
        } else {
            if (record.getScore() > records.get(MAX_RECORDS - 1).getScore()) {
                records.remove(MAX_RECORDS - 1);
                records.add(record);
            }
        }
        sortRecords();
    }

    public ArrayList<Record> sortRecords() {
        Collections.sort(records, new Comparator<Record>() {
            @Override
            public int compare(Record record1, Record record2) {
                return record2.getScore() - record1.getScore();
            }
        });
        return records;
    }



}
