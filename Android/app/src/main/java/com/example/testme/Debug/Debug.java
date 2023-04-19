package com.example.testme.Debug;

import android.util.Log;

public class Debug {
    private static final String TAG = "Selfdebug";
    public static void log(String text){
        Log.d(TAG , text);
    }
}
