package com.andrey.wooppaymessenger.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.andrey.wooppaymessenger.database.model.Message;

import java.util.List;

public class MessageViewModel extends AndroidViewModel {
    private MessageRepository mRepository;

    private LiveData<List<Message>> mAllMessages;

    public MessageViewModel (Application application) {
        super(application);
        mRepository = new MessageRepository(application);
        mAllMessages = mRepository.getAllMessages();
    }

    public LiveData<List<Message>> getAllMessages(){
        return mAllMessages;
    }

    public void insert(Message message) { mRepository.insert(message); }
}
