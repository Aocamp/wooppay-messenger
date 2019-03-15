package com.andrey.wooppaymessenger.service;

import com.andrey.wooppaymessenger.model.AuthData;
import com.andrey.wooppaymessenger.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AuthAPI {
    @POST("auth")
    Call<User> getAuth(@Body AuthData body);

    @Headers("Authorization: token")
    @GET("auth")
    void getUserInformation();
}
