package com.testapp.testme;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.testapp.testme.APItools.APICallback;
import com.testapp.testme.APItools.HitAPI;
import com.testapp.testme.R;
import com.testapp.testme.Debug.Debug;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class ResultActivity extends AppCompatActivity {
TextView marks,testname;
ImageView b;
JSONObject jsn;
Context context = this;
Button examSolution , resultAnalysis;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        testname = findViewById(R.id.testname);
        b = findViewById(R.id.backbtn);
        examSolution = findViewById(R.id.examSolution);
        resultAnalysis = findViewById(R.id.resultAnalysis);
        marks = findViewById(R.id.total);
        Context c = this;
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(c , DashboardActivity.class));
                finish();
            }
        });




        String result = getIntent().getStringExtra("result");

        if(result != null) {
            Debug.log(result);
            try {
                jsn = new JSONObject(result);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            setResult();
        }else{
            String reportId = getIntent().getStringExtra("id");
            JSONObject jsn = new JSONObject();
            try {
                jsn.put("reportId" , reportId);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            new HitAPI().sendJsonPostRequest("getResult", jsn, new APICallback() {
                @Override
                public void response(JSONObject jsonobj) {
                    try {
                        setResult(jsonobj.getJSONArray("report").getJSONObject(0));
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }
            } , true);
        }




    }

    private void getResult(int i) {
    }

    private void setResult(){

        examSolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context , ExamSection.class);
                try {
                    i.putExtra("id" , jsn.getString("test_id"));
                    i.putExtra("mode" , "Solution");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                startActivity(i);

            }
        });
        try {
            marks.setText(jsn.getString("marks"));
            testname.setText(jsn.getString("testname"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    private void setResult(JSONObject jsn){

        examSolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context , ExamSection.class);
                try {
                    i.putExtra("id" , jsn.getString("test_id"));
                    i.putExtra("mode" , "Solution");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                startActivity(i);

            }
        });
        try {
            marks.setText(jsn.getString("marks"));
            testname.setText(jsn.getString("name"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}