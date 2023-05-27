package com.testapp.testme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.testapp.testme.APItools.APICallback;
import com.testapp.testme.APItools.HitAPI;
import com.testapp.testme.Adapters.ExamSectionViewPagerAdapter;
import com.testapp.testme.Debug.Debug;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ExamSection extends AppCompatActivity {
    ViewPager2 viewPager2;
    boolean examCount = true;
    private int currentQuestion = 0;
    private String examId , mode;
    private Button submitBtn;
    ImageButton next, prev;
    LinearLayout question_btns;
    TextView questionLabel, testName;
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
        if (examId.equals("")) {
            Toast.makeText(this, "Test Id absent", Toast.LENGTH_SHORT).show();
            finish();
        }
        mode = getIntent().getStringExtra("mode");

        Debug.log("Starting Exam id " + examId);
        JSONObject jsn = new JSONObject();
        try {
            jsn.put("test_id", examId);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        Context context = this;
        question_btns = findViewById(R.id.question_btns);
        submitBtn = findViewById(R.id.submitTest);
        LoadingDialog loadingDialog = new LoadingDialog(ExamSection.this);
        loadingDialog.startLoadingDialog("Downloading Question Paper");
        new HitAPI().sendJsonPostRequest("QuestionPaper", jsn, new APICallback() {
            @Override
            public void response(JSONObject jsonobj) {
                loadingDialog.dismissDialog();
                try {
                    arrayList = jsonobj.getJSONArray("data");
                    String testTime = jsonobj.getJSONArray("testdetail").getJSONObject(0).getString("time");
                    String nameofTest = jsonobj.getJSONArray("testdetail").getJSONObject(0).getString("name");
                    testName.setText(nameofTest);
                    if(!"Solution".equals(mode)){
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Debug.log("calling time updater " + testTime);
                                TimeUpdater(Integer.parseInt(testTime), 0);
                            }
                        }).start();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                ExamSectionViewPagerAdapter myadapter = new ExamSectionViewPagerAdapter(context, arrayList, answer);
                if("Solution".equals(mode)){
                    myadapter.openInSolutionMode();
                }
                viewPager2.setAdapter(myadapter);
                buttonarr = new ArrayList<Button>();
                for (int i = 0; i < arrayList.length(); i++) {
                    Button v = new Button(context);
                    v.setText(String.valueOf(i + 1));
//                    v.setHeight(40);
//                    v.setWidth(40);
//                    v.setBackgroundResource(R.drawable.rounded);
                    buttonarr.add(v);
                    int count = i;
                    v.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setQuestion(count);
                        }
                    });
                    question_btns.addView(v, i);
                }
            }
        }, true);

        viewPager2 = findViewById(R.id.questionArea);
        next = findViewById(R.id.next);
        prev = findViewById(R.id.prev);
        questionLabel = findViewById(R.id.questionLabel);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setQuestion(currentQuestion + 1);
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setQuestion(currentQuestion - 1);
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingDialog.startLoadingDialog("Submitting Test");
                Debug.log(answer.getJSON().toString());
                JSONObject jsn = new JSONObject();
                try {
                    jsn.put("answer", answer.getJSON());
                    jsn.put("examId", examId);
                    jsn.put("timetaken", "120");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                new HitAPI().sendJsonPostRequest("submitTest", jsn, new APICallback() {
                    @Override
                    public void response(JSONObject jsonobj) {
                        loadingDialog.dismissDialog();
                        Intent intent = new Intent(context, ResultActivity.class);
                        try {
                            jsonobj.put("marks", jsonobj.get("marks"));
                            jsonobj.put("testname", jsonobj.get("name"));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                        intent.putExtra("result", jsonobj.toString());
                        startActivity(intent);
                        finish();
                    }
                }, true);
            }
        });
      viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
          @Override
          public void onPageSelected(int position) {
              super.onPageSelected(position);
              questionLabel.setText("Question No:-" + String.valueOf(position + 1));
              viewPager2.getAdapter();
          }
      });
    }

    private void setQuestion(int i) {
        Log.d("Selfdebug", "item value :- " + String.valueOf(i));
        if (i >= 0 && i <= arrayList.length() - 1) {
                currentQuestion = i;
                viewPager2.setCurrentItem(currentQuestion);
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

    private void setTextofTimer(String text) {
        testTimer.setText(text);
    }

    @SuppressLint("SetTextI18n")
    private void TimeUpdater(int minute, int seconds) {
        Debug.log("timer started for " + minute + " " + seconds);
        while (minute != -1) {
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
            minute = minute - 1;
            seconds = 60;

        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(examCount){
                    submitBtn.performClick();
                }

            }
        });


    }

    @Override
    public void onBackPressed() {
     examCount = false;
     super.onBackPressed();
    }
}

