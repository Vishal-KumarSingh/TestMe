package com.testapp.testme;

import android.app.Application;

import com.testapp.testme.VariableHolder.ContextHolder;

public class App extends Application {
    public static final String googlePlayLink = "https://play.google.com/store/apps/details?id=com.testapp.testme&pli=1";
    public static final String feedbackgormLink = "https://forms.gle/srYBcDSVFTP6ZYuE9";
    public static final String shareText = "Hey Aspirants! There is a greetings from the \"Test and Peek\" App. Here you can take free mocks of SSC, RRB, GATE, CAT, Current Affairs and all competitive exams. You can also self prepare from test series and quizzes daily of any competitive exams for absolutely FREE. Our aim is to give the every test resources to the aspirant without any subscription. \n" +
            "Join us now and Get welcome bonus too. Just open the Play Store and install our app \"Test and Peek\" or click on the link:" + googlePlayLink;
    public static String shareTitle = "Test and Peek";

    public App()
    {
        super();

        // App and library initialisation are there
        ContextHolder.setContext(this);
    }
}
