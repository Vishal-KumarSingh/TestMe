package com.example.testme.PermanantStorage;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {

    public static void Store(String key , String value , Context context){
        SharedPreferences sh = context.getSharedPreferences("SelfDebug", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        editor.putString(key , value);
        editor.apply();
    }
    public static String get(String key, Context context){
        SharedPreferences sh = context.getSharedPreferences("SelfDebug", Context.MODE_PRIVATE);
        return sh.getString(key, "");
    }
    public static String getToken(Context context){
        SharedPreferences sh = context.getSharedPreferences("SelfDebug", Context.MODE_PRIVATE);
        return sh.getString("token", "");
    }
    public static void setToken(Context context, String token){
        SharedPreferences sh = context.getSharedPreferences("SelfDebug", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        editor.putString("token" , token);
        editor.apply();
    }
}
