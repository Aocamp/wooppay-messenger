package com.andrey.wooppaymessenger.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.andrey.wooppaymessenger.ChatApplication;
import com.andrey.wooppaymessenger.R;
import com.andrey.wooppaymessenger.RestController;
import com.andrey.wooppaymessenger.adapter.MessageAdapter;
import com.andrey.wooppaymessenger.database.MessageViewModel;
import com.andrey.wooppaymessenger.database.model.Message;
import com.andrey.wooppaymessenger.model.ChatMessage;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatFragment extends Fragment {
    private static final String TAG = "MainActivity";
    private static final String ARG_PARAM1 = "room";

    private RecyclerView mRecyclerView;
    private MessageAdapter mAdapter;

    private MessageViewModel mMessageViewModel;

    private String mUsername = "User1";
    private String mRoom;
    private String mRoomId;

    Socket mSocket;

    FloatingActionButton floatingActionButton;
    EditText mInput;

   /* public static ChatFragment newInstance(List<ChatMessage> param1) {
        Bundle args = new Bundle();
        args.putString(MESSAGE_LIST, String.valueOf(param1));

        ChatFragment fragment = new ChatFragment();
        fragment.setArguments(args);
        return fragment;
    }*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAdapter = new MessageAdapter(context);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setRetainInstance(true);

        ChatApplication app = (ChatApplication) getActivity().getApplication();
        mSocket = app.getSocket();
        mSocket.on(Socket.EVENT_CONNECT, onConnect);
        mSocket.on("user disconnect", onDisconnect);
        mSocket.on("message", onNewMessage);
        mSocket.on("roomId", onRoomIdMessage);
        mSocket.connect();

        if(savedInstanceState != null){
            mRoom = savedInstanceState.getString(ARG_PARAM1);

        }else {
            UUID uuid = UUID.randomUUID();
            mRoom = uuid.toString();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        mMessageViewModel = ViewModelProviders.of(getActivity()).get(MessageViewModel.class);
//
//        mMessageViewModel.getAllMessages().observe(this, new Observer<List<Message>>() {
//            @Override
//            public void onChanged(@Nullable final List<Message> messages) {
//                mAdapter.setItem(messages);
//            }
//        });

        Call<List<ChatMessage>> messages = RestController
                .getInstance()
                .getMessageApi()
                .getMessageByRoomId(mRoomId);

        messages.enqueue(messageCallback);

        mRecyclerView = view.findViewById(R.id.recycler_view_messages);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);

        mInput = (EditText) view.findViewById(R.id.input);

        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = mInput.getText().toString().trim();
                if (TextUtils.isEmpty(message)) {
                    mInput.requestFocus();
                    return;
                }
                mInput.setText("");

                Date dt = new Date();
                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
                String currentTime = sdf.format(dt);
               // addMessage(mUsername, message);
                mSocket.emit("new message",mUsername, message, currentTime);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mSocket.disconnect();

        mSocket.off(Socket.EVENT_CONNECT, onConnect);
        mSocket.off("user disconnect", onDisconnect);;
        mSocket.off("message", onNewMessage);
        mSocket.off("roomId", onRoomIdMessage);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ARG_PARAM1, mRoom);
    }

//    public void addMessage(String userName, String message){
//        ChatMessage chatMessage = new ChatMessage(userName, message);
//        mMessages.add(chatMessage);
//        mAdapter.notifyDataSetChanged();
//    }

    Callback<List<ChatMessage>> messageCallback =  new Callback<List<ChatMessage>>() {
        @Override
        public void onResponse(@NonNull Call<List<ChatMessage>> call, @NonNull Response<List<ChatMessage>> response) {
            List<ChatMessage> message = response.body();
            mAdapter.setItem(message);
        }

        @Override
        public void onFailure(@NonNull Call<List<ChatMessage>> call, @NonNull Throwable t) {
            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                            mSocket.emit("connection", mUsername);
                            mSocket.emit("room", mRoom);
                        Toast.makeText(getActivity().getApplicationContext(),
                                "connect", Toast.LENGTH_LONG).show();


                }
            });
        }
    };

    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG, "disconnected");
                    Toast.makeText(getActivity().getApplicationContext(),
                            "disconnect", Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    private Emitter.Listener onRoomIdMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        mRoomId = data.getString("id");
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
            });
        }
    };

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String userName;
                    String message;
                    try {
                        userName = data.getString("user");
                        message = data.getString("message");
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                        return;
                    }
                    Message messages = new Message(userName, message);
//                    mMessageViewModel.insert(messages);

                    //addMessage(userName, message);
                    Call<List<ChatMessage>> newMessage = RestController
                            .getInstance()
                            .getMessageApi()
                            .getMessageByRoomId(mRoomId);

                    newMessage.enqueue(messageCallback);
                }
            });
        }
    };

}

