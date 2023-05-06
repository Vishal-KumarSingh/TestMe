package com.example.testme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testme.APItools.APICallback;
import com.example.testme.APItools.HitAPI;
import com.example.testme.Adapters.ExamSectionViewPagerAdapter;
import com.example.testme.Debug.Debug;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ExamSection extends AppCompatActivity {
ViewPager2 viewPager2;
private int currentQuestion = 0;
    private String examId;
    private  Button submitBtn;
ImageButton next,prev;
LinearLayout question_btns;
TextView questionLabel , testName;
JSONArray arrayList;
private TextView testTimer;
public AnswerKey answer;
    ArrayList<Button> buttonarr;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_section);
        examId = getIntent().getStringExtra("id");
        testTimer = findViewById(R.id.testTimer);
        answer = new AnswerKey();
        testName = findViewById(R.id.testName);
        if(examId.equals("")){
            Toast.makeText(this, "Test Id absent", Toast.LENGTH_SHORT).show();
            finish();
        }
        Debug.log("Starting Exam id " + examId );
            JSONObject jsn = new JSONObject();
        try {
            jsn.put("test_id" , examId);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        Context context = this;
        question_btns = findViewById(R.id.question_btns);
        submitBtn = findViewById(R.id.submitTest);
        LoadingDialog loadingDialog = new LoadingDialog(ExamSection.this);
        loadingDialog.startLoadingDialog("Downloading Question Paper");
        new HitAPI().sendJsonPostRequest("QuestionPaper", this, jsn, new APICallback() {
            @Override
            public void response(JSONObject jsonobj) {
                loadingDialog.dismissDialog();
                try {
                    arrayList = jsonobj.getJSONArray("data");
                    String testTime = jsonobj.getJSONArray("testdetail").getJSONObject(0).getString("time");
                    String nameofTest = jsonobj.getJSONArray("testdetail").getJSONObject(0).getString("name");
                    testName.setText(nameofTest);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Debug.log("calling time updater " + testTime);
                            TimeUpdater(Integer.parseInt(testTime) , 0);
                        }
                    }).start();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                ExamSectionViewPagerAdapter myadapter = new ExamSectionViewPagerAdapter(context , arrayList , answer);
                viewPager2.setAdapter(myadapter);
                buttonarr  = new ArrayList<Button>();
                for(int i=0;i<arrayList.length();i++){
                    Button v = new Button(context);
                    v.setText(String.valueOf(i+1));
                    buttonarr.add(v);
                    int count = i;
                    v.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setQuestion(count);
                        }
                    });
                    question_btns.addView(v , i);
                }
            }
        } , true);

        viewPager2 = findViewById(R.id.questionArea);
        next = findViewById(R.id.next);
        prev = findViewById(R.id.prev);
        questionLabel=findViewById(R.id.questionLabel);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    setQuestion(currentQuestion+1);
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               setQuestion( currentQuestion-1);
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingDialog.startLoadingDialog("Submitting Test");
                Debug.log(answer.getJSON().toString());
                JSONObject jsn = new JSONObject();
                try {
                    jsn.put("answer" , answer.getJSON());
                    jsn.put("examId" , examId);
                    jsn.put("timetaken" , "120");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                new HitAPI().sendJsonPostRequest("submitTest", context, jsn, new APICallback() {
                    @Override
                    public void response(JSONObject jsonobj) {
                        loadingDialog.dismissDialog();
                        Intent intent = new Intent(context , ResultActivity.class);
                        try {
                            jsonobj.put("marks" , jsonobj.get("marks"));
                            jsonobj.put("testname" , jsonobj.get("name"));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                        intent.putExtra("result" , jsonobj.toString());
                        startActivity(intent);
                        finish();
                    }
                },true);
            }
        });

    }

    private void setQuestion(int i) {
        Log.d("Selfdebug" , "item value :- " + String.valueOf(i));
        if(i>=0 && i<=arrayList.length()-1){
            currentQuestion=i;
            viewPager2.setCurrentItem(currentQuestion);
            questionLabel.setText("Question No:-" + String.valueOf(i+1));
        }

//        if(i == arrayList.size()-1){
//              next.setEnabled(false);
//            prev.setEnabled(true);
//        }else if (i==0){
//            prev.setEnabled(false);
//            next.setEnabled(true);
//        }else{
//            next.setEnabled(true);
//            prev.setEnabled(true);
//        }
    }
   private void setTextofTimer(String text){
        testTimer.setText(text);
   }
    @SuppressLint("SetTextI18n")
    private void TimeUpdater(int minute , int seconds){
        Debug.log("timer started for " + minute + " " + seconds);
        while(minute!=-1) {
            while (seconds != 0) {
                seconds = seconds - 1;
                int finalMinute = minute;
                int finalSeconds = seconds;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setTextofTimer(finalMinute + ":" + finalSeconds);
                    }
                });

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            minute = minute-1;
            seconds = 60;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                submitBtn.performClick();
            }
        });


    }
}

