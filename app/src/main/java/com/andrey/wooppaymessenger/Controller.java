package com.andrey.wooppaymessenger;

import com.andrey.wooppaymessenger.services.AuthAPI;
import com.andrey.wooppaymessenger.services.RegisterAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Controller {
    private static final String BASE_URL = "https://api.yii2.test.wooppay.com/v1/";

    private static Controller instance;

    private Retrofit retrofit;

    private Controller() {
        retrofit = new Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public AuthAPI getAuthApi(){
        return retrofit.create(AuthAPI.class);
    }

    public RegisterAPI getRegisterApi(){
        return retrofit.create(RegisterAPI.class);
    }
}
