package com.andrey.wooppaymessenger.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ChatMessage {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("messageDate")
    @Expose
    private String messageDate;
    @SerializedName("messageText")
    @Expose
    private String messageText;
    @SerializedName("roomId")
    @Expose
    private String roomId;
    @SerializedName("userId")
    @Expose
    private String userId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public ChatMessage(String id, String messageDate, String messageText, String roomId, String userId) {
        this.messageText = messageText;
        this.id = id;
        this.userId = userId;
        this.messageDate = messageDate;
        this.roomId = roomId;

    }

    public ChatMessage(){

    }

}
