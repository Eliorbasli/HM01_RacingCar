package com.example.hm01_racingcar;

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

import com.google.gson.Gson;
import com.google.mlkit.common.sdkinternal.SharedPrefManager;

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

        list_RV_records = view.findViewById(R.id.list_RV_top10);


        //  MSP msp = MSP;

        //MSP msp = null;


        //  if(msp != null) {
        String js = MSP.getInstance(activity).getString("MY_DB", "");
        MyDb myDB;
        if (js.isEmpty())
            myDB = new MyDb();
        else
            myDB = new Gson().fromJson(js, MyDb.class);

        records = myDB.getRecords();

        //RecordAdapter recordAdapter = new RecordAdapter(this, myDB.getRecords());
        RecordAdapter recordAdapter = new RecordAdapter(this, myDB.getRecords());

        list_RV_records.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        list_RV_records.setHasFixedSize(true);
        list_RV_records.setItemAnimator(new DefaultItemAnimator());
        list_RV_records.setAdapter(recordAdapter);

        recordAdapter.setRecordItemClickListener(new RecordAdapter.RecordItemClickListener() {
            @Override
            public void recordItemClick(Record record, int position) {
                if (listCallBack != null) {
                    double lat = record.getMyLocation().getLatitube();
                    double lon = record.getMyLocation().getLongitube();
                    listCallBack.RecordClicked(lat, lon);
                }
            }
        });
        //   }

        return view;

    }

    public void setListCallBack(List_CallBack listCallBack) {
        this.listCallBack = listCallBack;
    }

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;

    }
}