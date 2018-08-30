package com.jerryjin.clockview.view;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.jerryjin.clockview.presenter.IClockView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public abstract class AbstractClockView extends View {

    public static final int MODE_AUTO = 0x00;
    public static final int MODE_ADJUST = 0x01;

    protected static final String INSTANCE = "Instance";
    protected static final String KEY_HOUR = "Hour";
    protected static final String KEY_MINUTE = "Minute";
    protected static final String KEY_SECOND = "Second";

    protected Paint mPaint;

    protected int hour;
    protected int minute;
    protected int second;
    protected String mTime;

    protected int mode = MODE_AUTO;
    protected IClockView.OnTimeChangedListener onTimeChangedListener;

    public AbstractClockView(Context context) {
        super(context);
    }

    public AbstractClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initStyle(context, attrs);
        initPaint();
    }

    public AbstractClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initStyle(context, attrs);
        initPaint();
    }

    public AbstractClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initStyle(context, attrs);
        initPaint();
    }

    protected abstract void initStyle(Context context, AttributeSet attributeSet);

    protected abstract void initPaint();

    public String getTime() {
        return mTime;
    }

    public void setTime(int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss", Locale.getDefault());
        mTime = format.format(calendar.getTime());
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public IClockView.OnTimeChangedListener getOnTimeChangedListener() {
        return onTimeChangedListener;
    }

    public void setOnTimeChangedListener(IClockView.OnTimeChangedListener onTimeChangedListener) {
        this.onTimeChangedListener = onTimeChangedListener;
    }

}
