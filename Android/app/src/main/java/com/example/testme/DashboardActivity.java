package com.example.testme;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class DashboardActivity extends AppCompatActivity {
    private DrawerLayout drawer;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        RecyclerView recyclerView = findViewById(R.id.test_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Context context=this;
        new HitAPI().sendJsonPostRequest("ShowTest", this, new JSONObject(), new APICallback() {
            @Override
            public void response(JSONObject jsonobj) {
                try {
                    JSONArray arr = jsonobj.getJSONArray("data");
                    recyclerView.setAdapter(new TestAdapter(arr, context));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        } , true);
    }
}