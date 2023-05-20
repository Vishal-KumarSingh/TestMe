package com.testapp.testme;

import android.app.Application;

import com.testapp.testme.VariableHolder.ContextHolder;

public class App extends Application {
    public App()
    {
        super();

        // App and library initialisation are there
        ContextHolder.setContext(this);
    }
}
