package com.example.hm01_racingcar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;


import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    //Timer and Dealy
    private static final int DELAY = 1000;
    private int clock = 10;
    private int liveNumber = 3;
    private Timer timer;


    //View
    private ImageButton main_BTN_Right;
    private ImageButton main_BTN_Left;
    private ImageView[][] main_IMG_Stone;
    private ImageView[] main_IMG_Car;
    private ImageView[] main_IMG_Life;
    private int CarIndex = 1;
    private int row = 0 ;
    private int col = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViews();

        main_BTN_Left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CarIndex == 2){
                    main_IMG_Car[2].setVisibility(View.INVISIBLE);
                    main_IMG_Car[1].setVisibility(View.VISIBLE);
                    CarIndex = 1 ;

                }
                else {
                    main_IMG_Car[1].setVisibility(View.INVISIBLE);
                    main_IMG_Car[0].setVisibility(View.VISIBLE);
                    CarIndex = 0;
                }

            }
        });
        main_BTN_Right.setOnClickListener(v-> {
            if(CarIndex == 0){ //CarIndex in Left Col
                main_IMG_Car[0].setVisibility(View.INVISIBLE);
                main_IMG_Car[1].setVisibility(View.VISIBLE);
                CarIndex =1  ;

            }else { // CarIndex in Middle Col or CarIndex in Right Col
                main_IMG_Car[1].setVisibility(View.INVISIBLE);
                main_IMG_Car[2].setVisibility(View.VISIBLE);
                CarIndex =2 ;
            }

        });

    }



    @Override
    protected void onStart() {
        super.onStart();
        startRace();


    }
    @Override
    protected void onStop(){
        super.onStop();

    }

    private void findViews() {

        main_BTN_Left = findViewById(R.id.main_IMG_BTN_Left);
        main_BTN_Right = findViewById(R.id.main_IMG_BTN_Right);


        main_IMG_Life = new ImageView[]{
                findViewById(R.id.main_IMG_life0),
                findViewById(R.id.main_IMG_life1),
                findViewById(R.id.main_IMG_life2)
        };

        main_IMG_Car = new ImageView[]{
                findViewById(R.id.main_IMG_CarInLeft),
                findViewById(R.id.main_IMG_CarInMidle),
                findViewById(R.id.main_IMG_CarInRight)
        };

        main_IMG_Stone = new ImageView[][]{
                {findViewById(R.id.main_IMG_Left0),
                        findViewById(R.id.main_IMG_Middle0),
                        findViewById(R.id.main_IMG_Right0)},
                {
                        findViewById(R.id.main_IMG_Left1),
                        findViewById(R.id.main_IMG_Middle1),
                        findViewById(R.id.main_IMG_Right1)},
                {
                        findViewById(R.id.main_IMG_Left2),
                        findViewById(R.id.main_IMG_Middle2),
                        findViewById(R.id.main_IMG_Right2)},
                {
                        findViewById(R.id.main_IMG_Left3),
                        findViewById(R.id.main_IMG_Middle3),
                        findViewById(R.id.main_IMG_Right3)},
                {
                        findViewById(R.id.main_IMG_Left4),
                        findViewById(R.id.main_IMG_Middle4),
                        findViewById(R.id.main_IMG_Right4)},

        };

        main_IMG_Car[0].setVisibility(View.INVISIBLE);
        main_IMG_Car[2].setVisibility(View.INVISIBLE);
        CarIndex = 1;

        for (int i = 0; i < main_IMG_Stone.length; i++) {
            for (int j = 0; j < main_IMG_Stone[i].length; j++) {
                main_IMG_Stone[i][j].setVisibility(View.INVISIBLE);
            }
        }
        main_IMG_Stone[0][0].setVisibility(View.VISIBLE);

    }


    private void startRace() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                runOnUiThread(() -> { 
                    stoneDown();
                });
            }
        } , 0 , DELAY);
    }


    private void stoneDown() {

        checkCollision();

        // update the view
       for (int i = main_IMG_Stone.length -2 ; i >= 0 ; i--){
            for (int j = 0 ; j < main_IMG_Stone[i].length ; j++){
                if(main_IMG_Stone[i][j].getVisibility() == View.VISIBLE){
                    main_IMG_Stone[i][j].setVisibility(View.INVISIBLE);
                    main_IMG_Stone[i+1][j].setVisibility(View.VISIBLE);
                }
            }
        }
        //create new stone in first row
       col = (int)(Math.random() * (4) +1 );
       if(col < 3)
           main_IMG_Stone[0][col].setVisibility(View.VISIBLE);

    }

    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
// Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }
    }

    private void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();

    }

    private void checkCollision(){
        // check if we have collision
        for(int j = 0 ; j <= 2  ; j++){
            if(main_IMG_Stone[4][j].getVisibility() == View.VISIBLE && main_IMG_Car[j].getVisibility() == View.VISIBLE  ){
                main_IMG_Stone[4][j].setVisibility(View.INVISIBLE);
                vibrate();
                toast("Ops");
                reducelife();

            }
            main_IMG_Stone[4][j].setVisibility(View.INVISIBLE);
        }
    }



    private void reducelife(){
        main_IMG_Life[liveNumber-1].setVisibility(View.INVISIBLE);
        liveNumber--;
        if(liveNumber == 0){
            toast("update life");
            liveNumber =3 ;
            for(int i = 0 ; i < 3 ; i ++)
                main_IMG_Life[i].setVisibility(View.VISIBLE);

        }
    }



}