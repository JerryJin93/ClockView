package com.jerryjin.clockview.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public abstract class AbstractClockView extends View {

    public AbstractClockView(Context context) {
        super(context);
    }

    public AbstractClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AbstractClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AbstractClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public abstract void initStyle(Context context, AttributeSet attributeSet);

}
