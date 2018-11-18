package com.chaturaloka.android.lockscreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.lock_setup_btn)
    void setupLockPattern() {
        startActivity(new Intent(this, LockSetupActivity.class));
    }

    @OnClick(R.id.try_unlock_btn)
    void tryUnlockPattern() {

        String lockPattern = getSharedPreferences("lock_pref", Context.MODE_PRIVATE)
                .getString("lock_pattern", "");

        if (lockPattern.isEmpty()) {
            Toast.makeText(this, "Setup a pattern first", Toast.LENGTH_SHORT).show();
            return;
        }

        startActivity(new Intent(this, LockScreenActivity.class));
    }

    @OnClick(R.id.number_lock_setup_btn)
    void setupNumberLock() {
        startActivity(new Intent(this, NumberLockActivity.class));
    }

}
