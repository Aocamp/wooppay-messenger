package com.andrey.wooppaymessenger;

import android.app.Application;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

public class ChatApplication extends Application {
    private Socket mSocket;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public Socket getSocket() {
        if (mSocket == null) {
            try {
//                IO.Options opts = new IO.Options();
//                opts.forceNew = true;
//                opts.reconnection = true;
                mSocket = IO.socket("http://192.168.43.220:8888");
            }catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }

        return mSocket;
    }
}
