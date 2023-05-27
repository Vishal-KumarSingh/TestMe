package com.testapp.testme;

import android.app.Application;

import com.testapp.testme.VariableHolder.ContextHolder;

public class App extends Application {
    public static final String googlePlayLink = "https://play.google.com/store/apps/details?id=com.testapp.testme&pli=1";
    public static final String feedbackgormLink = "https://forms.gle/srYBcDSVFTP6ZYuE9";
    public static final String shareText = "Hey I found an amazing app where you can get free mock test of various examinations and believe me this is amazing. You can download this app from Google Play " + googlePlayLink;
    public static String shareTitle = "Test and Peek";

    public App()
    {
        super();

        // App and library initialisation are there
        ContextHolder.setContext(this);
    }
}
