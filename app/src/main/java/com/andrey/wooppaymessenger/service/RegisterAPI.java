package com.andrey.wooppaymessenger.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RegisterAPI {
    @FormUrlEncoded
    @POST("registration/create-account")
    Call<ResponseBody> createAccount(
            @Field("login") String login,
            @Field("email") String email);

    @FormUrlEncoded
    @POST("registration/activate-account")
    Call<ResponseBody> activateAccount(
            @Field("login") String login,
            @Field("email") String email,
            @Field("activation_code") String smsCode);

    @FormUrlEncoded
    @POST("registration/set-password")
    Call<ResponseBody> setPassword(
            @Field("login") String login,
            @Field("email") String email,
            @Field("activation_code") String smsCode,
            @Field("password") String password);
}
