package com.testapp.testme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.testapp.testme.Debug.Debug;
import com.google.android.material.navigation.NavigationView;
import com.testapp.testme.APItools.APICallback;
import com.testapp.testme.APItools.HitAPI;
import com.testapp.testme.Adapters.TestAdapter;
import com.testapp.testme.PermanantStorage.Session;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Objects;


public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private TextView navUserName;
    private NavigationView navview;
    private ImageView accountIcon;
    Toolbar toolbar;
    Context context;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        context = this;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.OpenNav, R.string.closeNav);
        drawer.addDrawerListener(toggle);

        toggle.syncState();
        recyclerView = findViewById(R.id.test_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        navview = findViewById(R.id.navigation_view);
        navview.setNavigationItemSelectedListener(this);
        View headerview = navview.getHeaderView(0);
        navUserName = headerview.findViewById(R.id.navUserName);
        accountIcon = headerview.findViewById(R.id.accountIcon);
        String username = Session.getUserName();
        if(Objects.equals(username, "")){
            loadUserName();
        }else{
            navUserName.setText(Session.getUserName());
        }
        showProfilePageOnNavUserNameClick();


        downloadTest();
    }

    private void showProfilePageOnNavUserNameClick() {
        navUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DashboardActivity.this  ,ProfileActivity.class);
                startActivity(i);
            }
        });
        accountIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DashboardActivity.this  ,ProfileActivity.class);
                startActivity(i);
            }
        });

    }

    private void loadUserName() {
        new HitAPI().sendJsonPostRequest("getUserInfo", new JSONObject(), new APICallback() {
            @Override
            public void response(JSONObject jsonobj) {
                try {
                    String userifo = jsonobj.getJSONObject("user").toString();
                    Session.set("user" , userifo);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                navUserName.setText(Session.getUserName());

            }
        } , true);
    }

    public void downloadTest(){
        LoadingDialog loadingDialog = new LoadingDialog(DashboardActivity.this);
        loadingDialog.startLoadingDialog("Downloading Test");
        Context context = this;
        new HitAPI().sendJsonPostRequest("ShowTest", new JSONObject(), new APICallback() {
            @Override
            public void response(JSONObject jsonobj) {
                try {
                    loadingDialog.dismissDialog();
                    JSONArray arr = jsonobj.getJSONArray("data");
                    recyclerView.setAdapter(new TestAdapter(arr, context , "Start Test" , ExamSection.class));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, true);
    }

    public void downloadReport(){

        LoadingDialog loadingDialog = new LoadingDialog(DashboardActivity.this);
        loadingDialog.startLoadingDialog("Downloading Report");
        Context context = this;
        new HitAPI().sendJsonPostRequest("showReport", new JSONObject(), new APICallback() {
            @Override
            public void response(JSONObject jsonobj) {
                try {
                    loadingDialog.dismissDialog();
                    JSONArray arr = jsonobj.getJSONArray("reports");
                    recyclerView.setAdapter(new TestAdapter(arr, context , "View Results" , ResultActivity.class));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, true);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        Uri uri;
        Debug.log("function called ");
        switch (item.getItemId()) {
            case R.id.dashboard:
                downloadTest();
                item.setChecked(true);
                break;
            case R.id.report:
                downloadReport();
                item.setChecked(true);
                break;
            case R.id.share:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT,App.shareText);
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, App.shareTitle);
                startActivity(Intent.createChooser(shareIntent, "Share..."));
                Toast.makeText(DashboardActivity.this, "Share the app with your friends", Toast.LENGTH_SHORT).show();
                break;
            case R.id.feedback:
                uri = Uri.parse(App.feedbackgormLink); // missing 'http://' will cause crashed
                intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                Toast.makeText(DashboardActivity.this, "Provide your valuable feedback to us", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rateUs:
                Toast.makeText(DashboardActivity.this, "Rate the app on the play store", Toast.LENGTH_SHORT).show();
                uri = Uri.parse(App.googlePlayLink); // missing 'http://' will cause crashed
                intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case R.id.logout:
                 logoutUser();

        }
        drawer.closeDrawer(GravityCompat.START , true);
        return true;
    }

    private void logoutUser() {
        Session.flush();
        Intent i = new Intent(this , SplashActivity.class);
        startActivity(i);
        finish();
    }


}