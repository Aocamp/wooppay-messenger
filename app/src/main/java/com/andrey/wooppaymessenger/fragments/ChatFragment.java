package com.andrey.wooppaymessenger.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.andrey.wooppaymessenger.adapters.MessageAdapter;
import com.andrey.wooppaymessenger.models.ChatMessage;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChatFragment extends Fragment {
    private static final String TAG = "MainActivity";
    private static final String ARG_PARAM1 = "room";
    public static final String MESSAGE_LIST = "list of messages";

    private List<ChatMessage> mMessages = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private MessageAdapter mAdapter;

    private String mUsername = "User";
    private String mRoom;

    Socket mSocket;

    FloatingActionButton floatingActionButton;
    EditText mInput;

    public static ChatFragment newInstance(List<ChatMessage> param1) {
        Bundle args = new Bundle();
        args.putString(MESSAGE_LIST, String.valueOf(param1));

        ChatFragment fragment = new ChatFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAdapter = new MessageAdapter(context, mMessages);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        ChatApplication app = (ChatApplication) getActivity().getApplication();
        mSocket = app.getSocket();
        mSocket.on(Socket.EVENT_CONNECT, onConnect);
        mSocket.on("user disconnect", onDisconnect);
        mSocket.on("message", onNewMessage);
        mSocket.connect();

        if (savedInstanceState == null) {
            UUID uuid = UUID.randomUUID();
            mRoom = uuid.toString();
        } else {
            mRoom = savedInstanceState.getString(ARG_PARAM1);
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

               // addMessage(mUsername, message);
                mSocket.emit("new message",mUsername, mRoom, message);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();

        onSaveInstanceState(new Bundle());

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mSocket.disconnect();

        mSocket.off(Socket.EVENT_CONNECT, onConnect);
        mSocket.off("user disconnect", onDisconnect);;
        mSocket.off("message", onNewMessage);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ARG_PARAM1, mRoom);
    }

    public void addMessage(String userName, String message){
        ChatMessage chatMessage = new ChatMessage(userName, message);
        mMessages.add(chatMessage);
        mAdapter.notifyDataSetChanged();
    }

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
                    addMessage(userName, message);
                }
            });
        }
    };

}

