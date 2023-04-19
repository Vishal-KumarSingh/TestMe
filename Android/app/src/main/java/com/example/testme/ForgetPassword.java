package com.example.testme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class ForgetPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
    }

    public void forget_password(View view) {
    }

    public void create_account(View view) {
        Intent i = new Intent(this , RegisterActivity.class);
        startActivity(i);

    }
}