package com.chaturaloka.android.lockscreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chaturaloka.android.numberlock.NumberLockView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NumberUnlockActivity extends AppCompatActivity
        implements LockContract.View, NumberLockView.NumberLockCallback {

    @BindView(R.id.warning_message)
    TextView mWarningMessageView;

    @BindView(R.id.number_lock_view)
    NumberLockView mNumberLockView;

    private LockPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_unlock);
        ButterKnife.bind(this);

        mPresenter = new LockPresenter(this, new LockInteractor(getApplicationContext()));
        mPresenter.setMinLength(3);

        mNumberLockView.setNumberLockListener(this);
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
        mNumberLockView.resetAttempt();
    }

    @Override
    public void delayUnlock() {
        Toast.makeText(this, "Max. retry attempts reached, wait for 60 seconds to retry", Toast.LENGTH_LONG).show();
        mNumberLockView.resetAttempt();
        mWarningMessageView.setVisibility(View.VISIBLE);
        mNumberLockView.setVisibility(View.GONE);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mWarningMessageView.setVisibility(View.GONE);
                mNumberLockView.setVisibility(View.VISIBLE);
            }
        }, 60000);
    }

    @Override
    public void showMinNumberWarning() {
        Toast.makeText(this, "Minimum of six digits required", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSequenceComplete(String sequence) {
        mPresenter.validatePattern(sequence);
    }
}
