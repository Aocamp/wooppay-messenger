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
import com.andrey.wooppaymessenger.activity.MainActivity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class SetPasswordActivity extends AppCompatActivity {
    public static final String EXTRA_ACTIVATE_LOGIN = "user login from ActivateAccountActivity";
    public static final String EXTRA_ACTIVATE_EMAIL = "user email from ActivateAccountActivity";
    public static final String EXTRA_ACTIVATE_CODE = "activation code from ActivateAccountActivity";

    private EditText mPassword;
    private EditText mCheckPassword;

    private Button mCreateAccount;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);

        bundle = getIntent().getExtras();

        mPassword = (EditText) findViewById(R.id.et_password);
        mCheckPassword = (EditText) findViewById(R.id.et_check_password);

        mCreateAccount = (Button) findViewById(R.id.btn_set_password);
        mCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();

                String login = intent.getStringExtra(EXTRA_ACTIVATE_LOGIN);
                String email = intent.getStringExtra(EXTRA_ACTIVATE_EMAIL);
                String smsCode = intent.getStringExtra(EXTRA_ACTIVATE_CODE);
                String password = mPassword.getText().toString();
                String checkPassword = mCheckPassword.getText().toString();

                int minPasswordLength = 6;

                if (password.isEmpty() && checkPassword.isEmpty()) {
                    Toast.makeText(SetPasswordActivity.this, "Поля должны быть заполнены", Toast.LENGTH_SHORT)
                            .show();
                } else if (password.isEmpty()) {
                    Toast.makeText(SetPasswordActivity.this, "Поле пароль должно быть заполнено", Toast.LENGTH_SHORT)
                            .show();
                } else if (checkPassword.isEmpty()) {
                    Toast.makeText(SetPasswordActivity.this, "Поле подтверждение пароля должно быть заполнено", Toast.LENGTH_SHORT)
                            .show();
                } else if (password.length() < minPasswordLength) {
                    Toast.makeText(SetPasswordActivity.this, "Пароль должен состоять не менее чем из 6 символов", Toast.LENGTH_LONG)
                            .show();
                } else if (!password.equals(checkPassword)) {
                    Toast.makeText(SetPasswordActivity.this, "Поля должны совпадать", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    Call<ResponseBody> callSetPassword = Controller
                            .getInstance()
                            .getRegisterApi()
                            .setPassword(login, email, smsCode, password);

                    callSetPassword.enqueue(new retrofit2.Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                Intent startMainActivityIntent = new Intent(SetPasswordActivity.this, MainActivity.class);
                                startActivity(startMainActivityIntent);
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
