package com.example.hm01_racingcar.Utiles;

import android.content.Context;
import android.content.SharedPreferences;

public class MSP {

    public static final String SP_FILE = "SharedPrefs";
    private SharedPreferences sharedPreferences;
    private static MSP msp;


    private MSP(Context context) {
        sharedPreferences = context.getApplicationContext().getSharedPreferences(SP_FILE, Context.MODE_PRIVATE);
    }


    public static MSP getInstance(Context context) {
        if (msp == null) {
            msp = new MSP(context);
        }
        return msp;
    }


    public static void init (Context context) {
        if (msp == null) {
            msp = new MSP(context);
        }
    }



//
//    public static MSP initMsp(Context context) {
//        if (msp == null) {
//            msp = new MSP(context);
//        }
//        return msp;
//    }

//    public void putDouble(String KEY, double defValue) {
//        putString(KEY, String.valueOf(defValue));
//    }
//
//    public double getDouble(String KEY, double defValue) {
//        return Double.parseDouble(getString(KEY, String.valueOf(defValue)));
//    }
//
//    public int getInt(String KEY, int defValue) {
//
//        return sharedPreferences.getInt(KEY, defValue);
//    }
//
//    public void putInt(String KEY, int value) {
//        sharedPreferences.edit().putInt(KEY, value).apply();
//    }

    public String getString(String KEY, String defValue) {
        if(sharedPreferences==null){
            return null;
        }else
            return sharedPreferences.getString(KEY, defValue);
    }

    public void putString(String Key, String value) {
        sharedPreferences.edit().putString(Key, value).apply();
    }
}
