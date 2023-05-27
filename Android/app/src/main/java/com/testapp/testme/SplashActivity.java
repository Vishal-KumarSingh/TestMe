package com.testapp.testme;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.testapp.testme.PermanantStorage.Session;
import java.util.Objects;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash);
            Log.d("SelfDebug" , "Started SplashActivity");
            final Context context=this;


            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.d("SelfDebug" , "SplashActivity: Waiting for timeout");
                        Thread.sleep(150);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    if(!Objects.equals(Session.getToken(), "")){
                        Intent dashboard = new Intent(context, DashboardActivity.class);
                        Log.d("SelfDebug" , "Token found "+Session.getToken());
                        startActivity(dashboard);
                        finish();
                    }else{
                        Intent login = new Intent(context , LoginActivity.class);
                        Log.d("SelfDebug" , "Token not found");
                        startActivity(login);
                        finish();
                    }

                }
            }).start();
        }

    }