package com.andrey.wooppaymessenger.database.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity(tableName = "message_table")
public class Message {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "message_text")
    private String messageText;
    @ColumnInfo(name = "message_time")
    private Long messageTime;
    @ColumnInfo(name = "message_user")
    private String messageUser;

    public Message(){

    }

    @Ignore
    public Message(String messageUser, String messageText) {
        this.messageUser = messageUser;
        this.messageText = messageText;
        messageTime = new Date().getTime();
    }

    @Ignore
    public Message(String messageText) {
        this.messageText = messageText;
        messageTime = new Date().getTime();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(Long messageTime) {
        this.messageTime = messageTime;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }
}
