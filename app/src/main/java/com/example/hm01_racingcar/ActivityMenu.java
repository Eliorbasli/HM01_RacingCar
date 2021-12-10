package com.example.hm01_racingcar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;


import com.google.android.material.button.MaterialButton;

public class ActivityMenu extends AppCompatActivity {

    public MaterialButton menu_BTN_light;
    public MaterialButton menu_BTN_accelerometer;
    public MaterialButton menu_BTN_TOP10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        menu_BTN_accelerometer = findViewById(R.id.menu_BTN_accelermeter);
        menu_BTN_light          = findViewById(R.id.menu_BTN_light);
        menu_BTN_TOP10 = findViewById(R.id.menu_BTN_TOP10);

        menu_BTN_accelerometer.setOnClickListener(v -> startGame("ACC"));

        menu_BTN_light.setOnClickListener(v -> startGame("LIGHT"));

        menu_BTN_TOP10.setOnClickListener(v -> {openTop10();

        });
    }

    private void startGame(String sensor) {
        Intent intent = new Intent(this, ActivityGame.class);
//        Bundle bundle = new Bundle();
//        bundle.putString(ActivityGame.SENSOR_TYPE , sensor);
//        bundle.putString(ActivityGame.NAME , "sensor");

        //intent.putExtra("bundle", bundle);
        intent.putExtra(ActivityGame.SENSOR_TYPE , sensor);
        startActivity(intent);
        //finish();
    }

    private void openTop10(){
        Intent intent = new Intent(this, ActivityTop10.class );
        startActivity(intent);
    }


}