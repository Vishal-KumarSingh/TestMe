package com.testapp.testme;

import com.testapp.testme.Debug.Debug;

import org.json.JSONException;
import org.json.JSONObject;

public class AnswerKey {
    public AnswerKey() {
        jsn = new JSONObject();
    }

    private JSONObject jsn;
    public void setAnswer(int question_no , String optionNo){
        try {
            String answerNo = optionNo.substring(0 , 1);
            jsn.put(String.valueOf(question_no), answerNo);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    public JSONObject getJSON(){
        return jsn;
    }

    public String getAnswer(int questionNo) {
        Debug.log("Current state of answer key " + getJSON().toString());
        Debug.log("User tried to get question no " + questionNo);

        try {
            Debug.log(" We provided the value " + jsn.getString(String.valueOf(questionNo)));
            return jsn.getString(String.valueOf(questionNo));
        } catch (JSONException e) {
            Debug.log(" We provided the value -1");
            return "-1";
        }

    }
}