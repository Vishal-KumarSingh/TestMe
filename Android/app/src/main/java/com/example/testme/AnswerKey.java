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
            String answerNo = optionNo.substring(0 , 1);
            jsn.put(String.valueOf(question_no), answerNo);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    public JSONObject getJSON(){
        return jsn;
    }
}