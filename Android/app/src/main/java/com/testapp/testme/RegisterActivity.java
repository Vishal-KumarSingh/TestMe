package com.testapp.testme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.testapp.testme.APItools.APICallback;
import com.testapp.testme.APItools.HitAPI;
import com.testapp.testme.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private EditText email , password, rpassword;
    private Button registrtion;
    private TextView login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        rpassword= findViewById(R.id.rpassword);
        login = findViewById(R.id.launchlogin);
        registrtion = findViewById(R.id.register);
        final Context context = this;
        login.setOnClickListener(view -> {
            Intent logina = new Intent(context , LoginActivity.class);
            startActivity(logina);
            finish();
        });
        registrtion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailfield = email.getText().toString();
                String passwordfield = password.getText().toString();
                String rpasswordfield = rpassword.getText().toString();
                if(Objects.equals(emailfield , "")){
                    Toast.makeText(context, "Email Field is required", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Objects.equals(passwordfield , "")){
                    Toast.makeText(context, "Password Field is required", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!Objects.equals(rpasswordfield , passwordfield)){
                    Toast.makeText(context, "Both passwords must be same", Toast.LENGTH_SHORT).show();
                    return;
                }
                HitAPI h = new HitAPI();
                JSONObject jsn = new JSONObject();
                try {
                    jsn.put("username", emailfield);
                    jsn.put("password" , passwordfield);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                h.sendJsonPostRequest("registration",jsn, new APICallback() {
                    @Override
                    public void response(JSONObject jsonobj) {
                        try {
                            boolean status = (boolean)jsonobj.get("status");
                            if(status){
                                Intent dashboard = new Intent(context , DashboardActivity.class);
                                startActivity(dashboard);
                                finish();
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                } , false);
            }
        });

    }

}