package com.andrey.wooppaymessenger.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.andrey.wooppaymessenger.Controller;
import com.andrey.wooppaymessenger.R;
import com.andrey.wooppaymessenger.model.AuthData;
import com.andrey.wooppaymessenger.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthActivity extends AppCompatActivity {
    private static final String TAG = "myLogs";

    private EditText mLogin;
    private EditText mPassword;

    private Button mSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        mLogin = (EditText) findViewById(R.id.et_auth_login);
        mPassword = (EditText) findViewById(R.id.et_auth_password);

        Log.d(TAG, "кнопка войти");
        mSignIn = (Button) findViewById(R.id.btn_sign_in);
        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = mLogin.getText().toString();
                String password = mPassword.getText().toString();

                int loginMinLength = 11;
                int passwordMinLength = 6;

                if (login.isEmpty() && password.isEmpty()){
                    Toast.makeText(AuthActivity.this, "Поля логин и пароль должны быть заполнены", Toast.LENGTH_SHORT)
                            .show();
                }
                else if(login.isEmpty()){
                    Toast.makeText(AuthActivity.this, "Поле логин не должно быть пустым", Toast.LENGTH_SHORT)
                            .show();
                }
                else if(login.length()<loginMinLength){
                    Toast.makeText(AuthActivity.this, "Некорректная длина логина", Toast.LENGTH_SHORT)
                            .show();
                }
                else if (password.isEmpty()){
                    Toast.makeText(AuthActivity.this, "Поле пароль не должно быть пустым", Toast.LENGTH_SHORT)
                            .show();
                }
                else if (password.length()<passwordMinLength){
                    Toast.makeText(AuthActivity.this, "Минимальная длина пароля 6 символов", Toast.LENGTH_SHORT)
                            .show();
                }
                else{
                AuthData body = new AuthData(login, password);

                Call<User> callAuth = Controller
                        .getInstance()
                        .getAuthApi()
                        .getAuth(body);

                callAuth.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                        Log.i(TAG, "successful response");
                        if(response.isSuccessful()){
                            User user = response.body();
                            if(user !=null){
                                Toast.makeText(AuthActivity.this, user.toString(), Toast.LENGTH_SHORT)
                                        .show();
                                Intent intent = new Intent(AuthActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                Log.d(TAG, "not suc response");
                                Toast.makeText(AuthActivity.this, "Authorization failed", Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.d(TAG, "onFailure method");
                        Toast.makeText(AuthActivity.this, "Authorization failed", Toast.LENGTH_SHORT)
                                    .show();
                    }
                });
                }
            }
        });
    }
}
