package com.chaturaloka.android.lockscreen;

interface LockContract {

    interface View {

        void showInfo();

        void showSuccess();

        void showMismatchingPatternFailure();

        void showAttemptFailure(int attemptCount);

        void delayUnlock();

        void showMinNumberWarning();
    }

    interface Presenter {
        void validatePattern(String pattern);
        void initializePattern(String pattern);
        void resetAttempts();
        void setMaxAttempts(int maxAttempts);
    }

    interface Interactor {

        void storePattern(String pattern);

        String retrievePattern();

        void resetPattern();
    }
}
