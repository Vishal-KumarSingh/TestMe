package com.example.testme;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testme.APItools.HitAPI;
import com.example.testme.Debug.Debug;

import org.json.JSONException;
import org.json.JSONObject;

public class ResultActivity extends AppCompatActivity {
TextView marks,testname;
ImageView b;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        String result = getIntent().getStringExtra("result");
        Debug.log(result);
        JSONObject jsn;
        try {
            jsn = new JSONObject(result);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        marks = findViewById(R.id.total);
        testname = findViewById(R.id.testname);
        b = findViewById(R.id.backbtn);
        Context c = this;
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(c , DashboardActivity.class));
                finish();
            }
        });
        try {
            marks.setText(jsn.getString("marks"));
            testname.setText(jsn.getString("testname"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


    }
}