package com.chaturaloka.android.lockscreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.takwolf.android.lock9.Lock9View;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PatternUnlockActivity extends AppCompatActivity
        implements LockContract.View, Lock9View.GestureCallback {

    @BindView(R.id.lock_9_view)
    Lock9View mLock9View;
    @BindView(R.id.warning_message)
    TextView mWarningMessageView;
    private LockPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lock_screen);
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
        Toast.makeText(this, "Unlocked Successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showMismatchingPatternFailure() {
        Toast.makeText(this, "Failure, retry", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showAttemptFailure(int attemptCount) {
        Toast.makeText(this, "Failure, retry attempt: " + attemptCount, Toast.LENGTH_LONG).show();
    }

    @Override
    public void delayUnlock() {
        Toast.makeText(this, "Max. retry attempts reached, wait for  60 seconds to retry", Toast.LENGTH_LONG).show();
        mWarningMessageView.setVisibility(View.VISIBLE);
        mLock9View.setVisibility(View.GONE);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mWarningMessageView.setVisibility(View.GONE);
                mLock9View.setVisibility(View.VISIBLE);
            }
        }, 60000);
    }

    @Override
    public void showMinNumberWarning() {
        Toast.makeText(this, "Minimum of four dots required", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNodeConnected(@NonNull int[] numbers) {

    }

    @Override
    public void onGestureFinished(@NonNull int[] numbers) {
        mPresenter.validatePattern(buildPattern(numbers));
    }

    @NonNull
    private String buildPattern(@NonNull int[] numbers) {
        return Arrays.toString(numbers).replaceAll("\\[|]|,|\\s", "");
    }
}
