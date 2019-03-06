package com.andrey.wooppaymessenger.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class ChatMessage implements Parcelable{
    private String messageText;
    private String messageUser;
    private long messageTime;

    public ChatMessage(String messageUser, String messageText) {
        this.messageText = messageText;
        this.messageUser = messageUser;

        messageTime = new Date().getTime();
    }

    public ChatMessage(){

    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(messageText);
        dest.writeString(messageUser);
        dest.writeValue(messageTime);
    }
}
