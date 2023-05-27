package com.testapp.testme;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.testapp.testme.R;

public class LoadingDialog {
     private Activity activity;
     private AlertDialog dialog;
     private TextView loadingTextView;
    public LoadingDialog(Activity context){
         activity = context;
    }

    @SuppressLint("MissingInflatedId")
    void startLoadingDialog(String loadingText){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_loader , null);
        loadingTextView = view.findViewById(R.id.loadingText);
        if(loadingTextView != null ){
            loadingTextView.setText(loadingText);
        }
        builder.setView(view);
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }
    void dismissDialog(){
        dialog.dismiss();
    }
}
