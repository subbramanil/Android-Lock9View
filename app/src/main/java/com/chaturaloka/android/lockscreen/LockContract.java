package com.chaturaloka.android.lockscreen;

import com.takwolf.android.lock9.Lock9View;

interface LockContract {

    interface View {

        void showInfo();

        void showSuccess();

        void showMismatchingPatternFailure();

        void showAttemptFailure(int attemptCount);

        void delayUnlock();

        void showMinNumberWarning();
    }

    interface Presenter extends Lock9View.GestureCallback,
            NumberLockActivity.NumberLockCallback {
        boolean validatePattern(String prevPattern, String currentPattern);
        void resetAttempts();
        void setMaxAttempts(int maxAttempts);
    }

    interface Interactor {

        void storePattern(String pattern);

        String retrievePattern();

        void resetPattern();
    }
}
