package com.chaturaloka.android.lockscreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.takwolf.android.lock9.Lock9View;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LockSetupActivity extends AppCompatActivity
        implements LockContract.View {

    @BindView(R.id.lock_9_view)
    Lock9View mLock9View;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lock_setup_screen);
        ButterKnife.bind(this);

        LockContract.Presenter mPresenter = new LockSetupPresenter(this, new LockInteractor(getApplicationContext()));
        mLock9View.setGestureCallback(mPresenter);
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
        Toast.makeText(this, "Minimum of three dots required", Toast.LENGTH_SHORT).show();
    }
}
