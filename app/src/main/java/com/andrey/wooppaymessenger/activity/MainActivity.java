package com.andrey.wooppaymessenger.activity;

import android.support.v4.app.Fragment;

import com.andrey.wooppaymessenger.fragment.ChatFragment;

public class MainActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        return new ChatFragment();
    }
}
