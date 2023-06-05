package com.testapp.testme.PermanantStorage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.testapp.testme.APItools.APICallback;
import com.testapp.testme.APItools.HitAPI;
import com.testapp.testme.Debug.Debug;
import com.testapp.testme.VariableHolder.ContextHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class Session {
   private static Context context;
    public static void Store(String key , String value){
        initialiseContext();
        SharedPreferences sh = context.getSharedPreferences("SelfDebug", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        editor.putString(key , value);
        editor.apply();
    }

    private static void initialiseContext() {
        if(context == null){
            context = ContextHolder.getContext();
        }
    }

    public static String get(String key){
        initialiseContext();

        SharedPreferences sh = context.getSharedPreferences("SelfDebug", Context.MODE_PRIVATE);
        return sh.getString(key, "");
    }
    public static void set(String key , String value){
        initialiseContext();
        SharedPreferences sh = context.getSharedPreferences("SelfDebug", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        editor.putString(key , value);
        editor.apply();
    }
    public static String getToken(){
        initialiseContext();
        SharedPreferences sh = context.getSharedPreferences("SelfDebug", Context.MODE_PRIVATE);
        return sh.getString("token", "");
    }
    public static void setToken( String token){
        initialiseContext();
        SharedPreferences sh = context.getSharedPreferences("SelfDebug", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        editor.putString("token" , token);
        editor.apply();
    }

    public static String getUserName() {
        Debug.log("trying to get value " +  Session.get("username"));
        String user = Session.get("user");
        Debug.log(user);
        if(!Objects.equals(user , "")){
            try {
                JSONObject jsn = new JSONObject(user);
                return jsn.getString("email");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }
        return "";
    }

    @SuppressLint("ApplySharedPref")
    public static void flush() {
        SharedPreferences sh = context.getSharedPreferences("SelfDebug", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        editor.clear().commit();
    }

    public static String getPassword() {
        Debug.log("trying to get value " +  Session.get("password"));
        String user = Session.get("user");
        Debug.log(user);
        if(!Objects.equals(user , "")){
            try {
                JSONObject jsn = new JSONObject(user);
                return jsn.getString("password");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }
        return "";
    }

    public static String getExam() {
        Debug.log("trying to get value " +  Session.get("exam"));
        String user = Session.get("user");
        Debug.log(user);
        if(!Objects.equals(user , "")){
            try {
                JSONObject jsn = new JSONObject(user);
                return jsn.getString("exam");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }
        return "";
    }

    public static void flushAndUpdate() {
        new HitAPI().sendJsonPostRequest("getUserInfo", new JSONObject(), new APICallback() {
            @Override
            public void response(JSONObject jsonobj) {
                try {
                    String userifo = jsonobj.getJSONObject("user").toString();
                    Session.set("user" , userifo);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        } , true);
    }
}
