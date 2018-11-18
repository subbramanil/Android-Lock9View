package com.chaturaloka.android.lockscreen;

import android.support.annotation.NonNull;

import java.util.Arrays;

public class LockScreenPresenter
        implements LockContract.Presenter {

    private final LockContract.View mView;
    private final LockInteractor mInteractor;
    private int mNumOfAttempts = 1;
    private int mMaxAttempts = 3;

    LockScreenPresenter(LockContract.View view, LockInteractor interactor) {
        this.mView = view;
        this.mInteractor = interactor;
    }

    @Override
    public boolean validatePattern(String prevPattern, String currentPattern) {
        return prevPattern.equals(currentPattern);
    }

    @Override
    public void resetAttempts() {
        mNumOfAttempts = 1;
    }

    @Override
    public void setMaxAttempts(int maxAttempts) {
        mMaxAttempts = maxAttempts;
    }

    @Override
    public void onNodeConnected(@NonNull int[] numbers) {
        // empty body
    }

    @Override
    public void onGestureFinished(@NonNull int[] numbers) {

        if (mNumOfAttempts < mMaxAttempts) {
            if (numbers.length < 3) {
                mView.showMinNumberWarning();
                mNumOfAttempts = 1;
                return;
            }
            if (validatePattern(mInteractor.retrievePattern(), Arrays.toString(numbers))) {
                mView.showSuccess();
            } else {
                mView.showAttemptFailure(mNumOfAttempts);
            }
            mNumOfAttempts++;
        } else {
            mNumOfAttempts = 1;
            mView.delayUnlock();
        }
    }

    @Override
    public void onSequenceComplete(String sequence) {
        // empty body
    }
}
