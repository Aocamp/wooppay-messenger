package com.andrey.wooppaymessenger.activity.register_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.andrey.wooppaymessenger.Controller;
import com.andrey.wooppaymessenger.R;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class ActivateAccountActivity extends AppCompatActivity {
    public static final String EXTRA_USER_LOGIN = "user login from CreateAccountActivity";
    public static final String EXTRA_USER_EMAIL = "user email from CreateAccountActivity";

    private EditText mSmsCode;

    private Button mActivateAccount;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activate_account);

        bundle = getIntent().getExtras();

        mSmsCode = (EditText) findViewById(R.id.et_activation_code);

        mActivateAccount = (Button) findViewById(R.id.btn_activate_account);
        mActivateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                final String login = intent.getStringExtra(EXTRA_USER_LOGIN);
                final String email = intent.getStringExtra(EXTRA_USER_EMAIL);
                final String smsCode = mSmsCode.getText().toString();

                int codeLength = 6;

                if (smsCode.length()<codeLength || smsCode.length()>codeLength){
                    Toast.makeText(ActivateAccountActivity.this, "Код должен состоять из 6 цифр", Toast.LENGTH_SHORT)
                            .show();
                }
                else if(login.isEmpty()){
                    Toast.makeText(ActivateAccountActivity.this, "Поле смс код не должно быть пустым", Toast.LENGTH_SHORT)
                            .show();
                }else {
                Call<ResponseBody> callActivateAccount = Controller
                        .getInstance()
                        .getRegisterApi()
                        .activateAccount(login, email, smsCode);

                callActivateAccount.enqueue(new retrofit2.Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            Intent intent = new Intent(ActivateAccountActivity.this, SetPasswordActivity.class);
                            intent.putExtra(SetPasswordActivity.EXTRA_ACTIVATE_LOGIN, login);
                            intent.putExtra(SetPasswordActivity.EXTRA_ACTIVATE_EMAIL, email);
                            intent.putExtra(SetPasswordActivity.EXTRA_ACTIVATE_CODE, smsCode);
                            startActivity(intent);
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
