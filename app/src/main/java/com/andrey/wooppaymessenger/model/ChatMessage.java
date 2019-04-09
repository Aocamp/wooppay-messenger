package com.andrey.wooppaymessenger.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ChatMessage {
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("messageDate")
    @Expose
    private String messageDate;
    @SerializedName("messageText")
    @Expose
    private String messageText;
    @SerializedName("roomId")
    @Expose
    private long roomId;
    @SerializedName("userId")
    @Expose
    private long userId;
    @SerializedName("userLogin")
    @Expose
    private String userLogin;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(String messageDate) {
        this.messageDate = messageDate;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }
}
