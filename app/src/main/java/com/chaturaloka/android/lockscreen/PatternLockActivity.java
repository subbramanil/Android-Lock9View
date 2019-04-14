package com.chaturaloka.android.lockscreen;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.takwolf.android.lock9.Lock9View;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PatternLockActivity extends AppCompatActivity
        implements LockContract.View, Lock9View.GestureCallback {

    @BindView(R.id.lock_9_view)
    Lock9View mLock9View;
    private LockPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lock_setup_screen);
        ButterKnife.bind(this);

        mPresenter = new LockPresenter(this, new LockInteractor(getApplicationContext()));
        mPresenter.setMinLength(4);
        mLock9View.setGestureCallback(this);
    }

    @Override
    public void showInfo() {
        Toast.makeText(this, "Try the same pattern again", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccess() {
        Toast.makeText(this, "Your pattern has been saved", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void showMismatchingPatternFailure() {
        Toast.makeText(this, "Didn't match the previous pattern", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showAttemptFailure(int attemptCount) {
//        Toast.makeText(this, "Failure, retry attempt: " + attemptCount, Toast.LENGTH_LONG).show();
    }

    @Override
    public void delayUnlock() {
        // do nothing
    }

    @Override
    public void showMinNumberWarning() {
        Toast.makeText(this, "Minimum of four dots required", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNodeConnected(@NonNull int[] numbers) {
        // empty activity
    }

    @Override
    public void onGestureFinished(@NonNull int[] numbers) {
        mPresenter.initializePattern(buildPattern(numbers));
    }

    @NonNull
    private String buildPattern(@NonNull int[] numbers) {
        return Arrays.toString(numbers).replaceAll("\\[|]|,|\\s", "");
    }
}
