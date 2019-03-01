package com.andrey.wooppaymessenger.activities;

import android.support.v4.app.Fragment;

import com.andrey.wooppaymessenger.fragments.ChatFragment;

public class MainActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new ChatFragment();
    }


}
