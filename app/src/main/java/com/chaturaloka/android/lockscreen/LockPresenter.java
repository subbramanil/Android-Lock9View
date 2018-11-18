package com.chaturaloka.android.lockscreen;

import android.util.Log;

public class LockPresenter
        implements LockContract.Presenter {

    private final LockContract.View mView;
    private final LockInteractor mInteractor;
    private int mNumOfAttempts = 1;
    private int mMaxAttempts = 3;
    private int mMinLength = 3;

    LockPresenter(LockContract.View view, LockInteractor interactor) {
        this.mView = view;
        this.mInteractor = interactor;
    }

    void setMinLength(int minLength) {
        this.mMinLength = minLength;
    }

    @Override
    public void resetAttempts() {
        mNumOfAttempts = 0;
    }

    @Override
    public void setMaxAttempts(int maxAttempts) {
        mMaxAttempts = maxAttempts;
    }

    @Override
    public void validatePattern(String pattern) {

        if (mNumOfAttempts < mMaxAttempts) {
            if (pattern.length() < mMinLength) {
                mView.showMinNumberWarning();
                mNumOfAttempts = 1;
                return;
            }
            if (pattern.equalsIgnoreCase(mInteractor.retrievePattern())) {
                mView.showSuccess();
            } else {
                mView.showAttemptFailure(mNumOfAttempts);
            }
            mNumOfAttempts++;
        } else{
            mNumOfAttempts = 1;
            mView.delayUnlock();
        }
    }

    @Override
    public void initializePattern(String pattern) {

        Log.d("Test", "Received Pattern: " + pattern);

        if (mNumOfAttempts == 1) {
            if (pattern.length() < mMinLength) {
                mView.showMinNumberWarning();
                mNumOfAttempts = 1;
                return;
            }
            mNumOfAttempts++;
            mView.showInfo();
            mInteractor.storePattern(pattern);
        } else {
            mNumOfAttempts = 1;
            if (pattern.equalsIgnoreCase(mInteractor.retrievePattern())) {
                mView.showSuccess();
            } else {
                mInteractor.resetPattern();
                mView.showMismatchingPatternFailure();
            }
        }
    }
}
