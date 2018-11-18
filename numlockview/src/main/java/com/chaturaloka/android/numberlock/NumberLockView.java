package com.chaturaloka.android.numberlock;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;

public class NumberLockView extends LinearLayout {

    private Context mContext;
    private int mNumViews = 4;

    private StringBuilder mCurrentSequence;
    private final TextWatcher mNumberLockTxtWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            mCurrentSequence.append(s.toString());
        }
    };
    private NumberLockCallback mNumberLockListener;
    private final TextWatcher mFinalNumberLockTxtWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            mCurrentSequence.append(s.toString());
            mNumberLockListener.onSequenceComplete(mCurrentSequence.toString());
        }
    };

    public NumberLockView(Context context) {
        super(context);

        init(context, null);
    }

    public NumberLockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    public NumberLockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public NumberLockView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.mContext = context;

        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER);

        final TypedArray styleAttributesArray = context.obtainStyledAttributes(attrs, R.styleable.NumberLockView);
        mNumViews = styleAttributesArray.getInteger(R.styleable.NumberLockView_numViews, mNumViews);
        mCurrentSequence = new StringBuilder(mNumViews);

        styleAttributesArray.recycle();

        addNumLockViews();
    }

    private void addNumLockViews() {
        if (mNumViews != 0) {
            for (int i = 0; i < mNumViews - 1; i++) {
                addView(getNumLockView());
            }
        }

        addView(getFinalNumLockView());
    }

    private EditText getNumLockView() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        EditText editText = (EditText) inflater.inflate(R.layout.number_lock_item, this, false);
        editText.addTextChangedListener(mNumberLockTxtWatcher);
        return editText;
    }

    private EditText getFinalNumLockView() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        EditText editText = (EditText) inflater.inflate(R.layout.number_lock_item, this, false);
        editText.addTextChangedListener(mFinalNumberLockTxtWatcher);
        return editText;
    }

    public void resetAttempt() {
        mCurrentSequence.delete(0, mNumViews);
        removeTextWatchers();
        clearAllText();
        addTextWatchers();
        getChildAt(0).requestFocus();
    }

    private void addTextWatchers() {
        for (int i = 0; i < getChildCount() - 1; i++) {
            ((EditText) getChildAt(i)).addTextChangedListener(mNumberLockTxtWatcher);
        }

        ((EditText) getChildAt(mNumViews - 1)).addTextChangedListener(mFinalNumberLockTxtWatcher);
    }

    private void removeTextWatchers() {

        for (int i = 0; i < getChildCount() - 1; i++) {
            ((EditText) getChildAt(i)).removeTextChangedListener(mNumberLockTxtWatcher);
        }

        ((EditText) getChildAt(mNumViews - 1)).removeTextChangedListener(mFinalNumberLockTxtWatcher);
    }

    private void clearAllText() {
        for (int i = 0; i < getChildCount(); i++) {
            ((EditText) getChildAt(i)).getText().clear();
        }
    }

    public void setNumberLockListener(NumberLockCallback listener) {
        this.mNumberLockListener = listener;
    }

    public interface NumberLockCallback {
        void onSequenceComplete(String sequence);
    }
}
