package com.andrey.wooppaymessenger.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.andrey.wooppaymessenger.database.DAO.MessageDao;
import com.andrey.wooppaymessenger.database.models.Message;

@Database(entities = {Message.class}, version = 1, exportSchema = false)
public abstract class ChatDatabase extends RoomDatabase {
    public abstract MessageDao getMessageDao();

    private static volatile ChatDatabase INSTANCE;

    static ChatDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ChatDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ChatDatabase.class, "chat_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
