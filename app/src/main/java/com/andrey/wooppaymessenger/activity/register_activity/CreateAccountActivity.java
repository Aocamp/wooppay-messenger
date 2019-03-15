package com.andrey.wooppaymessenger.activity.register_activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.andrey.wooppaymessenger.R;
import com.andrey.wooppaymessenger.Controller;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class CreateAccountActivity extends AppCompatActivity{
    private EditText mLogin;
    private EditText mEmail;

    private Button mCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mLogin = (EditText) findViewById(R.id.et_register_login);
        mEmail = (EditText) findViewById(R.id.et_register_email);

        mCreateAccount = (Button) findViewById(R.id.btn_create_account);
        mCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String login = mLogin.getText().toString();
                final String email = mEmail.getText().toString();

                int minLoginLength = 11;

                if (login.isEmpty() && email.isEmpty()){
                    Toast.makeText(CreateAccountActivity.this, "Поля логин и пароль должны быть заполнены", Toast.LENGTH_SHORT)
                            .show();
                }
                else if(login.isEmpty()){
                    Toast.makeText(CreateAccountActivity.this, "Поле логин не должно быть пустым", Toast.LENGTH_SHORT)
                            .show();
                }
                else if(login.length()<minLoginLength){
                    Toast.makeText(CreateAccountActivity.this, "Некорректная длина логина", Toast.LENGTH_SHORT)
                            .show();
                }
                else if (email.isEmpty()){
                    Toast.makeText(CreateAccountActivity.this, "Поле email не должно быть пустым", Toast.LENGTH_SHORT)
                            .show();
                } else{
                Call<ResponseBody> callCreateAccount = Controller
                        .getInstance()
                        .getRegisterApi()
                        .createAccount(login, email);

                callCreateAccount.enqueue(new retrofit2.Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                            Intent intent = new Intent(CreateAccountActivity.this, ActivateAccountActivity.class);
                            intent.putExtra(ActivateAccountActivity.EXTRA_USER_LOGIN, login);
                            intent.putExtra(ActivateAccountActivity.EXTRA_USER_EMAIL, email);
                            startActivity(intent);

                        if (response.code() == 202){
                            Toast.makeText(CreateAccountActivity.this, "СМС-код был отправлен ранее. Повторите попытку через некоторое время.", Toast.LENGTH_LONG)
                                    .show();
                        }
                        //get error message
                        else if (response.code() == 422){

                           // Toast.makeText(CreateAccountActivity.this, error.getErrorMessage() , Toast.LENGTH_SHORT)
                                    //.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                    }
                });
            }
            }
        });
    }

}
