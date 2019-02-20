package com.andrey.wooppaymessenger.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.andrey.wooppaymessenger.database.DAO.MessageDao;
import com.andrey.wooppaymessenger.database.models.Message;

import java.util.List;

public class MessageRepository {
    private MessageDao mMessageDao;
    private LiveData<List<Message>> mAllMessages;

    MessageRepository(Application application) {
        ChatDatabase db = ChatDatabase.getDatabase(application);
        mMessageDao = db.messageDaoAccess();
        mAllMessages = mMessageDao.getAllMessages();
    }
    LiveData<List<Message>> getAllMessages() {
        return mAllMessages;
    }


    public void insert (Message message) {
        new insertAsyncTask(mMessageDao).execute(message);
    }

    private static class insertAsyncTask extends AsyncTask<Message, Void, Void> {

        private MessageDao mAsyncTaskDao;

        insertAsyncTask(MessageDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Message... params) {
            mAsyncTaskDao.insertMessage(params[0]);
            return null;
        }
    }

}
