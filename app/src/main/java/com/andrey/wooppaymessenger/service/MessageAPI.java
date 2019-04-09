package com.andrey.wooppaymessenger.service;

import com.andrey.wooppaymessenger.model.ChatMessage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MessageAPI {
    @GET("messages")
    Call<List<ChatMessage>> getAllMessages();

    @GET("messages/room/{roomId}")
    Call<List<ChatMessage>> getMessagesByRoomId(@Path("roomId") Long roomId);
}
