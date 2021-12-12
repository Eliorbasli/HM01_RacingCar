package com.example.hm01_racingcar.Activies;


import androidx.appcompat.app.AppCompatActivity;



import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.hm01_racingcar.Fragments.ListFragment;
import com.example.hm01_racingcar.List_CallBack;
import com.example.hm01_racingcar.Fragments.MapFragment;
import com.example.hm01_racingcar.R;


public class ActivityTop10 extends AppCompatActivity {
    private FrameLayout rec_LST_records;
    private ListFragment listFragment;
    private MapFragment mapFragment;
    private List_CallBack listCallBack;

    //private List_CallBack listCallBack;
    //private FrameLayout rec_top10_googleMap;
//    private ListFragment listFragment;
//    private List_CallBack listCallBack;
//    private MapFragment mapFragment;

//    Fragment fragment = new MapFragment();
    //GoogleMap map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top10);

        listFragment = new ListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.rec_LST_records , listFragment).commit();

//
        mapFragment = new MapFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.rec_top10_googleMap , mapFragment).commit();


        listCallBack = new List_CallBack() {
            @Override
            public void RecordClicked(double lat, double lon) {
                mapFragment.RecordClicked(lat, lon);
            }
        };

        listFragment.setActivity(this);

        listFragment.setListCallBack(listCallBack);

    }
}

