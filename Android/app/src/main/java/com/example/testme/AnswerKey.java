package com.example.testme;

import org.json.JSONException;
import org.json.JSONObject;

public class AnswerKey {
    public AnswerKey() {
        jsn = new JSONObject();
    }

    private JSONObject jsn;
    public void setAnswer(int question_no , String optionNo){
        try {
            jsn.put(String.valueOf(question_no), optionNo);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    public JSONObject getJSON(){
        return jsn;
    }
}