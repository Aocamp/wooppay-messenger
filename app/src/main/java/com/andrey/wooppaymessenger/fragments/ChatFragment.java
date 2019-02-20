package com.andrey.wooppaymessenger.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.andrey.wooppaymessenger.R;
import com.andrey.wooppaymessenger.adapters.MessageAdapter;
import com.andrey.wooppaymessenger.models.ChatMessage;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class ChatFragment extends Fragment {
    private static final String TAG = "MainActivity";

    private List<ChatMessage> mMessages = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private MessageAdapter mAdapter;

    FloatingActionButton floatingActionButton;
    EditText mInput;

    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        // args.putString(ARG_PARAM1, param1);
        // args.putString(ARG_PARAM2, param2);
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
                String message = mInput.getText().toString();
                mInput.setText("");
                MyClientTask myClientTask = new MyClientTask(message);
                myClientTask.execute();
            }
        });
    }

    public class MyClientTask extends AsyncTask<Void, Void, Void> {
        String address = "192.168.43.220";
        int port = 8888;

        String response;
        String message;

        MyClientTask(String message){
            this.message = message;
        }

        @Override
        protected Void doInBackground(Void... params) {
            Socket socket = null;
            DataInputStream in = null;
            DataOutputStream out = null;
            try {
                socket = new Socket(address, port);
                out = new DataOutputStream(socket.getOutputStream());
                in = new DataInputStream(socket.getInputStream());
                out.writeUTF(message);
                out.flush();
                response = in.readUTF();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setMessageText(response);
            mMessages.add(chatMessage);
            mAdapter.notifyDataSetChanged();
        }

    }
}

