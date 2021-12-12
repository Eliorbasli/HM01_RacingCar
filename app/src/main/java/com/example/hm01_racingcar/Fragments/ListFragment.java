package com.example.hm01_racingcar.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hm01_racingcar.List_CallBack;
import com.example.hm01_racingcar.Models.MyDb;
import com.example.hm01_racingcar.R;
import com.example.hm01_racingcar.Models.Record;
import com.example.hm01_racingcar.RecordAdapter;
import com.example.hm01_racingcar.Utiles.MSP;
import com.google.gson.Gson;

import java.util.ArrayList;


public class ListFragment extends Fragment {


    private RecyclerView list_RV_top10;
    private List_CallBack listCallBack;
    private ArrayList<Record> records;
    private AppCompatActivity activity;
    private RecyclerView list_RV_records;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);

        String js = MSP.getInstance(activity).getString("MY_DB", "");


        MyDb myDB;
        if (js.isEmpty())
            myDB = new MyDb();
        else
            myDB = new Gson().fromJson(js, MyDb.class);

        list_RV_records = view.findViewById(R.id.list_RV_top10);

        records = myDB.getRecords();

        RecordAdapter recordAdapter = new RecordAdapter(this, myDB.getRecords());

        list_RV_records.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        list_RV_records.setHasFixedSize(true);
        list_RV_records.setItemAnimator(new DefaultItemAnimator());
        list_RV_records.setAdapter(recordAdapter);

        recordAdapter.setRecordItemClickListener(new RecordAdapter.RecordItemClickListener() {
            @Override
            public void recordItemClick(Record record, int position) {
                if (listCallBack != null) {
                    double lat = record.getLat();
                    double lon = record.getLon();
                    listCallBack.RecordClicked(lat, lon);
                }
            }
        });

        return view;
    }

    public void setListCallBack(List_CallBack listCallBack) {
        this.listCallBack = listCallBack;
    }

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;

    }
}