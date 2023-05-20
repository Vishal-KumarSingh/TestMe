package com.testapp.testme.Toast;

import android.content.Context;
import android.widget.Toast;

import com.testapp.testme.App;
import com.testapp.testme.VariableHolder.ContextHolder;

public class Toaster {
    private static Context context;
    public static void shortToast(String text){
        if(context == null ){
            context = ContextHolder.getContext();
        }
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
    public static void longToast(String text){
        if(context == null ){
            context = ContextHolder.getContext();
        }
        Toast.makeText(context , text , Toast.LENGTH_LONG).show();
    }
}
