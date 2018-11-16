package com.chaturaloka.android.lockscreen;

import android.support.annotation.NonNull;

import java.util.Arrays;

public class LockSetupPresenter
        implements LockContract.Presenter {

    private final LockContract.View mView;
    private final LockInteractor mInteractor;
    private int mNumOfAttempts = 0;

    LockSetupPresenter(LockContract.View view, LockInteractor interactor) {
        this.mView = view;
        this.mInteractor = interactor;
    }

    @Override
    public boolean validatePattern(String prevPattern, String currentPattern) {
        return prevPattern.equals(currentPattern);
    }

    @Override
    public void resetAttempts() {
        mNumOfAttempts = 0;
    }

    @Override
    public void setMaxAttempts(int maxAttempts) {
        // empty body
    }

    @Override
    public void onNodeConnected(@NonNull int[] numbers) {
        // empty body
    }

    @Override
    public void onGestureFinished(@NonNull int[] numbers) {

        if (mNumOfAttempts == 0) {
            if (numbers.length < 3) {
                mView.showMinNumberWarning();
                mNumOfAttempts = 0;
                return;
            }
            mNumOfAttempts++;
            mView.showInfo();
            mInteractor.storePattern(Arrays.toString(numbers));
        } else {
            mNumOfAttempts = 0;
            if (validatePattern(mInteractor.retrievePattern(), Arrays.toString(numbers))) {
                mView.showSuccess();
            } else {
                mInteractor.resetPattern();
                mView.showMismatchingPatternFailure();
            }
        }
    }
}
