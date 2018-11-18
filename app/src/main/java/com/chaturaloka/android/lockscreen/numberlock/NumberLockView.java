package com.chaturaloka.android.lockscreen;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;

public class NumberLockView extends LinearLayout {

    private Context mContext;
    private int mNumViews;

    public NumberLockView(Context context) {
        super(context);
    }

    public NumberLockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NumberLockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs) {
        this.mContext = context;

        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER);

        final TypedArray styleAttributesArray = context.obtainStyledAttributes(attrs, R.styleable.NumberLockView);
        mNumViews = styleAttributesArray.getInteger(R.styleable.NumberLockView_numViews, mNumViews);
    }
}
