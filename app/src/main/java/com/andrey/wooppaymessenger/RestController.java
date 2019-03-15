package com.andrey.wooppaymessenger;

import com.andrey.wooppaymessenger.service.MessageAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestController {
    private static final String BASE_URL = "http://192.168.43.220:8080/com.api/rest/";

    private static RestController instance;

    private Retrofit retrofit;

    private RestController() {
        retrofit = new Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RestController getInstance() {
        if (instance == null) {
            instance = new RestController();
        }
        return instance;
    }

    public MessageAPI getMessageApi(){
        return retrofit.create(MessageAPI.class);
    }

}
