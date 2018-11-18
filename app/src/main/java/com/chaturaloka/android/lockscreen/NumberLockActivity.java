package com.chaturaloka.android.lockscreen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;

public class NumberLockActivity extends AppCompatActivity
        implements LockContract.View {

    @BindViews({R.id.editText, R.id.editText2, R.id.editText3, R.id.editText4, R.id.editText5, R.id.editText6})
    List<EditText> mNumberLockViews;

    private NumberLockCallback numberLockBack;
    private StringBuilder mCurrentSequence = new StringBuilder(6);

    final TextWatcher mNumberLockTxtWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            TextView text = (TextView) getCurrentFocus();

            if (text != null && text.length() > 0) {
                View next = text.focusSearch(View.FOCUS_RIGHT); // or FOCUS_FORWARD
                if (next != null)
                    next.requestFocus();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            mCurrentSequence.append(s.toString());
        }
    };

    final TextWatcher mFinalNumberLockTxtWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            mCurrentSequence.append(s.toString());
            numberLockBack.onSequenceComplete(mCurrentSequence.toString());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_lock);
        ButterKnife.bind(this);

        LockContract.Presenter mPresenter = new LockSetupPresenter(this, new LockInteractor(getApplicationContext()));
        numberLockBack = mPresenter;
        addTextWatchers();
    }

    @Override
    public void showInfo() {
        Toast.makeText(this, "Try the same sequence again", Toast.LENGTH_SHORT).show();
        resetAttempt();
    }

    private void resetAttempt() {
        mCurrentSequence.delete(0, 6);
        removeTextWatchers();
        clearAllText();
        addTextWatchers();
        mNumberLockViews.get(0).requestFocus();
    }

    private void addTextWatchers() {
        for (int i = 0; i < mNumberLockViews.size() - 1; i++) {
            mNumberLockViews.get(i).addTextChangedListener(mNumberLockTxtWatcher);
        }

        mNumberLockViews.get(5).addTextChangedListener(mFinalNumberLockTxtWatcher);
    }

    private void removeTextWatchers() {
        for (int i = 0; i < mNumberLockViews.size() - 1; i++) {
            mNumberLockViews.get(i).removeTextChangedListener(mNumberLockTxtWatcher);
        }
        mNumberLockViews.get(5).removeTextChangedListener(mFinalNumberLockTxtWatcher);
    }

    private void clearAllText() {
        for (EditText editText : mNumberLockViews) {
            editText.getText().clear();
        }
    }

    @Override
    public void showSuccess() {
        Toast.makeText(this, "Your sequence has been saved", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void showMismatchingPatternFailure() {
        Toast.makeText(this, "Didn't match the previous pattern", Toast.LENGTH_LONG).show();
        resetAttempt();
    }

    @Override
    public void showAttemptFailure(int attemptCount) {
        // empty body
    }

    @Override
    public void delayUnlock() {
        // empty body
    }

    @Override
    public void showMinNumberWarning() {
        Toast.makeText(this, "Minimum of six numbers required", Toast.LENGTH_SHORT).show();
    }

    public interface NumberLockCallback {
        void onSequenceComplete(String sequence);
    }
}
