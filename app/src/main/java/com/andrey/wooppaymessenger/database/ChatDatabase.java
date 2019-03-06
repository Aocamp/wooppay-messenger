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
    public abstract MessageDao messageDaoAccess();

    private static volatile ChatDatabase INSTANCE;

    static ChatDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ChatDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ChatDatabase.class, "chat_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final MessageDao mDao;

        PopulateDbAsync(ChatDatabase db) {
            mDao = db.messageDaoAccess();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            Message message = new Message("User", "Hello world" );
            return null;
        }
    }
}
