package com.andrey.wooppaymessenger.database.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.andrey.wooppaymessenger.database.models.Message;

import java.util.List;

@Dao
public interface MessageDao {
    @Insert
    void insertMessage (Message message);

    @Query("DELETE FROM message_table")
    void deleteAll();

    @Query("SELECT * from message_table")
    LiveData<List<Message>> getAllMessages();
}
