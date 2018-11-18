package com.chaturaloka.android.lockscreen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.chaturaloka.android.numberlock.NumberLockView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NumberLockActivity extends AppCompatActivity
        implements NumberLockView.NumberLockCallback, LockContract.View {

    @BindView(R.id.number_lock_view)
    NumberLockView numberLockView;
    private LockPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_lock);
        ButterKnife.bind(this);

        mPresenter = new LockPresenter(this, new LockInteractor(getApplicationContext()));
        mPresenter.setMinLength(3);

        numberLockView.setNumberLockListener(this);
    }

    @Override
    public void onSequenceComplete(String sequence) {
        mPresenter.initializePattern(sequence);
    }

    @Override
    public void showInfo() {
        Toast.makeText(this, "Try the same sequence again", Toast.LENGTH_SHORT).show();
        numberLockView.resetAttempt();
    }

    @Override
    public void showSuccess() {
        Toast.makeText(this, "Your sequence has been saved", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void showMismatchingPatternFailure() {
        Toast.makeText(this, "Didn't match the previous sequence", Toast.LENGTH_LONG).show();
        numberLockView.resetAttempt();
    }

    @Override
    public void showAttemptFailure(int attemptCount) {
        //empty body
    }

    @Override
    public void delayUnlock() {
        //empty body
    }

    @Override
    public void showMinNumberWarning() {
        Toast.makeText(this, "Minimum of six numbers required", Toast.LENGTH_SHORT).show();
    }
}
