package com.andrey.wooppaymessenger.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.andrey.wooppaymessenger.Controller;
import com.andrey.wooppaymessenger.R;
import com.andrey.wooppaymessenger.activities.register_activities.CreateAccountActivity;
import com.andrey.wooppaymessenger.models.AuthData;
import com.andrey.wooppaymessenger.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class StartPageActivity extends AppCompatActivity {
    private static final String TAG = "myLogs";
    private Button mRegisterActivity;
    private Button mAuthActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        mRegisterActivity = (Button) findViewById(R.id.btn_register_activity);
        mRegisterActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartPageActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        });

        mAuthActivity = (Button) findViewById(R.id.btn_auth_activity);
        mAuthActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartPageActivity.this, AuthActivity.class);
                startActivity(intent);

    }
        });
    }
}