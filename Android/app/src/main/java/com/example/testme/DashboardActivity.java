package com.example.testme;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Context;
import android.os.Bundle;

import com.example.testme.APItools.APICallback;
import com.example.testme.APItools.HitAPI;
import com.example.testme.Adapters.TestAdapter;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class DashboardActivity extends AppCompatActivity {
    private DrawerLayout drawer;
    private NavigationView navview;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this , drawer , toolbar , R.string.OpenNav , R.string.closeNav);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        RecyclerView recyclerView = findViewById(R.id.test_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LoadingDialog loadingDialog = new LoadingDialog(DashboardActivity.this);
        loadingDialog.startLoadingDialog("Downloading Test");
        Context context=this;
        new HitAPI().sendJsonPostRequest("ShowTest", this, new JSONObject(), new APICallback() {
            @Override
            public void response(JSONObject jsonobj) {
                try {
                    loadingDialog.dismissDialog();
                    JSONArray arr = jsonobj.getJSONArray("data");
                    recyclerView.setAdapter(new TestAdapter(arr, context));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        } , true);
    }
}