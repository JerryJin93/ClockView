package com.jerryjin.clockview.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class DigitalClockView extends AbstractClockView {


    public DigitalClockView(Context context) {
        super(context);
    }

    public DigitalClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DigitalClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DigitalClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void initStyle(Context context, AttributeSet attributeSet) {

    }

    @Override
    protected void initPaint() {

    }
}
