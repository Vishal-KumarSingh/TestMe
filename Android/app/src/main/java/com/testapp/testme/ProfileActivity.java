package com.testapp.testme;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.testapp.testme.APItools.APICallback;
import com.testapp.testme.APItools.HitAPI;
import com.testapp.testme.Debug.Debug;
import com.testapp.testme.PermanantStorage.Session;
import com.testapp.testme.Toast.Toaster;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class ProfileActivity extends AppCompatActivity {
   TextView examSelected;
    boolean[] selectedLanguage;
    ArrayList<Integer> langList = new ArrayList<>();
    EditText username , mail , password;
    ImageView backbtn;
    Button updateProfilebtn;
    String[] langArray = {"GATE" , "SSC"};
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // assign variable
        examSelected = findViewById(R.id.select);
        username = findViewById(R.id.name);
        mail = findViewById(R.id.username);
        password = findViewById(R.id.password);
        backbtn = findViewById(R.id.backbtn);
        updateProfilebtn = findViewById(R.id.updateProfile);
        allowMultiSelectOfExam();
        updateProfileValueFromSession();
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        // initialize selected language array
        updateProfilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfle();
            }
        });

    }

    private void updateProfileValueFromSession() {
        username.setText(Session.getUserName());
        mail.setText(Session.getUserName());
        password.setText("********");
        examSelected.setText(Session.getExam());
    }

    private void allowMultiSelectOfExam() {
        selectedLanguage = new boolean[langArray.length];

        examSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);

                // set title
                builder.setTitle("Select Exam");

                // set dialog non cancelable
                builder.setCancelable(false);

                builder.setMultiChoiceItems(langArray, selectedLanguage, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // check condition
                        if (b) {
                            // when checkbox selected
                            // Add position  in lang list
                            langList.add(i);
                            // Sort array list
                            Collections.sort(langList);
                        } else {
                            // when checkbox unselected
                            // Remove position from langList
                            langList.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();
                        // use for loop
                        for (int j = 0; j < langList.size(); j++) {
                            // concat array value
                            stringBuilder.append(langArray[langList.get(j)]);
                            // check condition
                            if (j != langList.size() - 1) {
                                // When j value  not equal
                                // to lang list size - 1
                                // add comma
                                stringBuilder.append("\n ");
                            }
                        }
                        // set text on textView
                        examSelected.setText(stringBuilder.toString());
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < selectedLanguage.length; j++) {
                            // remove all selection
                            selectedLanguage[j] = false;
                            // clear language list
                            langList.clear();
                            // clear text view value
                            examSelected.setText("");
                        }
                    }
                });
                // show dialog
                builder.show();
            }
        });
    }


    public void updateProfle(){
        String usernamestr = String.valueOf(username.getText());
        String mailstr = String.valueOf(mail.getText());
        String passwordstr = String.valueOf(password.getText());
        String exam = (String) examSelected.getText();
        JSONObject jsn = new JSONObject();
        try {
        if(!passwordstr.equals("********")){
            jsn.put("password" , passwordstr);
        }else{
            jsn.put("password" , Session.getPassword());
        }
        jsn.put("exam" , exam);
        jsn.put("username" , mailstr);
        jsn.put("name" , usernamestr);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        new HitAPI().sendJsonPostRequest("updateProfile", jsn, new APICallback() {
            @Override
            public void response(JSONObject jsonobj) {
                Toaster.shortToast("Profile Updated Successfully");
                Session.flushAndUpdate();
            }
        } , true);

    }
}