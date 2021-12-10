package com.example.hm01_racingcar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;


import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Timer;
import java.util.TimerTask;
import android.os.FileUtils;

public class ActivityGame<mediaPlayer> extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor sensor;
    private final int STONE = 10;
    private final int STAR = 5;
    private final int NOTHING = 0 ;
    private LocationManager locationManagaer;
    private LocationListener locationListener;
    private double lat;
    private double lon;
    private MSP msp;
    private Gson gson;

    private SensorEventListener accSensorEventListener =  new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {

            float x = event.values[0];
//           float y = event.values[0];
//           float z = event.values[0];

            if (x < -3){
                moveRight();
            }
            else if (x > 3 )
                moveLeft();
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    //Timer and Dealy
    private static final int DELAY = 800;
    private int clock = 10;
    private int liveNumber = 3;
    private Timer timer;

    private int colNumbers = 5;
    private int rowNumbers = 6;
    private int scores = 0 ;

    private int RandomNumber = 0 ;
    private int[] imageVies = new int[5];

    //View
    private ImageButton main_BTN_Right;
    private ImageButton main_BTN_Left;
    private ImageView[][] main_IMG_Stone;
    private ImageView[] main_IMG_Car;
    private ImageView[] main_IMG_Life;
    private int CarIndex = 1;
    private int row = 0 ;
    private int col = 0 ;
    private static MyDb myDB = new MyDb();

    private TextView main_score;

    //0 - Invisbale

    private int [][] boardVisible = {
            { 0, 0, 0 ,0 , 0},
            { 0, 0, 0 ,0 , 0},
            { 0, 0, 0 ,0 , 0},
            { 0, 0, 0 ,0 , 0},
            { 0, 0, 0 ,0 , 0},
            { 0, 0, 0 ,0 , 0},
    };

    public static final String SENSOR_TYPE = "SENSOR_TYPE";
    public static final String NAME = "NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        findViews();

        gson = new Gson();
        msp = MSP.getInstance();
        locationPermission();

        Intent intent = getIntent();

        main_score.setText(intent.getStringExtra(SENSOR_TYPE));

        initSensor(intent.getStringExtra(SENSOR_TYPE));

        main_BTN_Left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveLeft();
            }
        });
        main_BTN_Right.setOnClickListener(v-> {
            moveRight();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(accSensorEventListener , sensor , SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(accSensorEventListener);
    }

    private void initSensor(String typeSen) {
        sensorManager =(SensorManager) getSystemService(Context.SENSOR_SERVICE);

        Intent intent = getIntent();
        if (intent.getStringExtra(SENSOR_TYPE).equals("ACC"))
        {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            main_BTN_Right.setVisibility(View.INVISIBLE);
            main_BTN_Left.setVisibility(View.INVISIBLE);
        }
        else// (intent.getStringExtra(SENSOR_TYPE).equals("LIGHT"))
        {
            sensorManager.unregisterListener(accSensorEventListener);
        }
    }
//    public boolean isSensorExsits(int sensorType){
//        return (sensorManager.getDefaultSensor(sensorType) != null);
//    }

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

        imageVies[0] = R.drawable.star;
        imageVies[1] = R.drawable.stone;

        main_score = findViewById(R.id.main_LBL_score);


        main_BTN_Left = findViewById(R.id.main_IMG_BTN_Left);
        main_BTN_Right = findViewById(R.id.main_IMG_BTN_Right);

        main_IMG_Life = new ImageView[]{
                findViewById(R.id.main_IMG_life0),
                findViewById(R.id.main_IMG_life1),
                findViewById(R.id.main_IMG_life2)
        };

        main_IMG_Car = new ImageView[]{
                findViewById(R.id.main_IMG_Car0),
                findViewById(R.id.main_IMG_Car1),
                findViewById(R.id.main_IMG_Car2),
                findViewById(R.id.main_IMG_Car3),
                findViewById(R.id.main_IMG_Car4)
        };

        main_IMG_Stone = new ImageView[][]{
                {findViewById(R.id.main_IMG_0_0),
                        findViewById(R.id.main_IMG_0_1),
                        findViewById(R.id.main_IMG_0_2),
                        findViewById(R.id.main_IMG_0_3),
                        findViewById(R.id.main_IMG_0_4)},
                {
                        findViewById(R.id.main_IMG_1_0),
                        findViewById(R.id.main_IMG_1_1),
                        findViewById(R.id.main_IMG_1_2),
                        findViewById(R.id.main_IMG_1_3),
                        findViewById(R.id.main_IMG_1_4)
                },
                {
                        findViewById(R.id.main_IMG_2_0),
                        findViewById(R.id.main_IMG_2_1),
                        findViewById(R.id.main_IMG_2_2),
                        findViewById(R.id.main_IMG_2_3),
                        findViewById(R.id.main_IMG_2_4),
                },
                {
                        findViewById(R.id.main_IMG_3_0),
                        findViewById(R.id.main_IMG_3_1),
                        findViewById(R.id.main_IMG_3_2),
                        findViewById(R.id.main_IMG_3_3),
                        findViewById(R.id.main_IMG_3_4),
                },
                {
                        findViewById(R.id.main_IMG_4_0),
                        findViewById(R.id.main_IMG_4_1),
                        findViewById(R.id.main_IMG_4_2),
                        findViewById(R.id.main_IMG_4_3),
                        findViewById(R.id.main_IMG_4_4),
                },
                {
                        findViewById(R.id.main_IMG_5_0),
                        findViewById(R.id.main_IMG_5_1),
                        findViewById(R.id.main_IMG_5_2),
                        findViewById(R.id.main_IMG_5_3),
                        findViewById(R.id.main_IMG_5_4),
                }

        };

        main_IMG_Car[0].setVisibility(View.INVISIBLE);
        main_IMG_Car[1].setVisibility(View.INVISIBLE);
        main_IMG_Car[3].setVisibility(View.INVISIBLE);
        main_IMG_Car[4].setVisibility(View.INVISIBLE);
        CarIndex = 2;

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

        for (int i = main_IMG_Stone.length -2 ; i >= 0 ; i--){
            for (int j = 0 ; j < main_IMG_Stone[i].length ; j++){
                if(boardVisible[i][j] == STONE){
                    main_IMG_Stone[i][j].setVisibility(View.INVISIBLE);
                    boardVisible[i][j] = NOTHING;
                    main_IMG_Stone[i+1][j].setImageResource(imageVies[1]);
                    main_IMG_Stone[i+1][j].setVisibility(View.VISIBLE);
                    boardVisible[i+1][j] = STONE;
                }
                else if(boardVisible[i][j] == STAR){
                    //  Log.d("board","board = 5");
                    main_IMG_Stone[i][j].setVisibility(View.INVISIBLE);
                    boardVisible[i][j] = NOTHING;
                    main_IMG_Stone[i+1][j].setImageResource(imageVies[0]);
                    main_IMG_Stone[i+1][j].setVisibility(View.VISIBLE);
                    boardVisible[i+1][j] = STAR;
                }
                else{
                    main_IMG_Stone[i][j].setVisibility(View.INVISIBLE);
                    boardVisible[i+1][j] = NOTHING;
                }
            }
        }

        //create new stone in first row
        RandomNumber = (int)(Math.random() * (8));

        if(RandomNumber > 0 && RandomNumber < 5){
            createStone();
        }
        else if (RandomNumber > 5){
            createCoin();
        }
    }

    private void createCoin() {
        col = (int)(Math.random() * (5)); // random col
        if(col < 5)
        {
            main_IMG_Stone[0][col].setImageResource(imageVies[0]);
            main_IMG_Stone[0][col].setVisibility(View.VISIBLE);
            boardVisible[0][col] = STAR; // 5 == coin
        }
    }

    private void createStone() {
        col = (int)(Math.random() * (5));
        if(col < 5) // random col
        {
            main_IMG_Stone[0][col].setImageResource(imageVies[1]);
            main_IMG_Stone[0][col].setVisibility(View.VISIBLE);
            boardVisible[0][col] = STONE;          //update the visble table
        }
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
        for(int j = 0 ; j <= colNumbers -1  ; j++){

            if(main_IMG_Stone[rowNumbers -1][j].getVisibility() == View.VISIBLE && main_IMG_Car[j].getVisibility() == View.VISIBLE  ){
                if(boardVisible[rowNumbers -1][j] == 10){
                    toast("Ops");
                    reducelife();
                    final MediaPlayer music = MediaPlayer.create(ActivityGame.this, R.raw.music1);
                    music.start();
                }
                else{
                    toast("yes!!");
                    scores++ ;
                    main_score.setText("Scores: " + scores);
                }
                vibrate();

            }
            main_IMG_Stone[rowNumbers -1][j].setVisibility(View.INVISIBLE);
            main_IMG_Stone[5][j].setVisibility(View.INVISIBLE);
        }
    }



    private void reducelife(){
        if(liveNumber == 0) {
            gameOver();
        }
        if(liveNumber > 0 ){
            main_IMG_Life[liveNumber-1].setVisibility(View.INVISIBLE);
            liveNumber--;
        }


        // liveNumber =3 ;
//            for(int i = 0 ; i < 3 ; i ++)
//                main_IMG_Life[i].setVisibility(View.VISIBLE);

    }

    private void gameOver() {
        // toast("Game Over");

        timer.cancel();

        if (sensorManager != null)
            sensorManager.unregisterListener(accSensorEventListener);

        MSP msp1 = MSP.getInstance();
        if(msp1 != null) {
            String js = msp1.getString("MY_DB" , "");
            myDB = new Gson().fromJson(js, MyDb.class);
        }

        myDB.addRecord(new Record().setScore(scores).setMyLocation(new MyLocation((lat), (lon))));

        Log.e("String" , "gameOver: ");
        Intent intent = new Intent(this, ActivityTop10.class );
        startActivity(intent);
        finish();
    }

    private void moveLeft(){
        if(CarIndex != 0 ){
            main_IMG_Car[CarIndex].setVisibility(View.INVISIBLE);
            main_IMG_Car[CarIndex -1].setVisibility(View.VISIBLE);
            CarIndex = CarIndex -1 ;

        }
        else { // carIndex == 0
            //main_IMG_Car[1].setVisibility(View.INVISIBLE);
            main_IMG_Car[0].setVisibility(View.VISIBLE);
            CarIndex = 0;
        }
    }

    private void moveRight(){
        if(CarIndex != 4){ //Car is not Right Col
            main_IMG_Car[CarIndex].setVisibility(View.INVISIBLE);
            main_IMG_Car[CarIndex +1].setVisibility(View.VISIBLE);
            CarIndex = CarIndex +1;

        }else { // Car in Right Col
            main_IMG_Car[CarIndex].setVisibility(View.INVISIBLE);
            main_IMG_Car[4].setVisibility(View.VISIBLE);
            CarIndex =4 ;
        }
    }

    private void locationPermission(){
        locationManagaer = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                lat = location.getLatitude();
                lon = location.getLongitude();
            }
        };

        if(ContextCompat.checkSelfPermission(this , Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this , new String[]{Manifest.permission.ACCESS_FINE_LOCATION} , 1);
        }
        else{
            locationManagaer.requestLocationUpdates(locationManagaer.GPS_PROVIDER, 0 ,1, locationListener);
        }
    }

}