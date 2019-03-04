package com.andrey.wooppaymessenger.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.andrey.wooppaymessenger.fragments.ChatFragment;

public class MainActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new ChatFragment();
    }

}
