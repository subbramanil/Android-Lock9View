package com.chaturaloka.android.lockscreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.takwolf.android.lock9.Lock9View;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LockScreenActivity extends AppCompatActivity
        implements LockContract.View {

    @BindView(R.id.lock_9_view)
    Lock9View lock9View;
    @BindView(R.id.warning_message)
    TextView warningMessageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lock_screen);
        ButterKnife.bind(this);

        LockContract.Presenter mPresenter = new LockPresenter(this, new LockInteractor(getApplicationContext()));

        lock9View.setGestureCallback(mPresenter);
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
        Toast.makeText(this, "Max. retry attempts reached, wait until 60 seconds to retry", Toast.LENGTH_LONG).show();
        warningMessageView.setVisibility(View.VISIBLE);
        lock9View.setVisibility(View.GONE);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                warningMessageView.setVisibility(View.GONE);
                lock9View.setVisibility(View.VISIBLE);
            }
        }, 60000);
    }

    @Override
    public void showMinNumberWarning() {
        Toast.makeText(this, "Minimum of three dots required", Toast.LENGTH_SHORT).show();
    }
}
