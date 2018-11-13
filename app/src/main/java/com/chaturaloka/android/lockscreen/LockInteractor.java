package com.chaturaloka.android.lockscreen;

import android.content.Context;
import android.content.SharedPreferences;

public class LockInteractor implements LockContract.Interactor {

    private final SharedPreferences mSharedPref;

    LockInteractor(Context context) {
        mSharedPref = context.getSharedPreferences("lock_pref", Context.MODE_PRIVATE);
    }

    @Override
    public void storePattern(String pattern) {
        // FIXME: 11/13/18 Storing the pattern in plain text is dangerous; Encrypt the pattern and store more securely
        mSharedPref.edit().putString("lock_pattern", pattern).apply();
    }

    @Override
    public String retrievePattern() {
        return mSharedPref.getString("lock_pattern", "");
    }

    @Override
    public void resetPattern() {
        mSharedPref.edit().putString("lock_pattern", "").apply();
    }
}
