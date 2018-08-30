package com.jerryjin.clockview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.jerryjin.clockview.R;
import com.jerryjin.fastandroid.utility.UiUtility;

import java.util.Calendar;

public class AnalogClockView extends AbstractClockView {

    public static final int CLOCK_FACE_BLACK = 0x10;
    public static final int CLOCK_FACE_WHITE = 0x11;

    private float mPadding;
    private float mRadius;
    private float mTextSize;
    private float mHourPointerWidth;
    private float mMinutePointerWidth;
    private float mSecondPointerWidth;
    private int mPointerCornerRadius;
    private float mPointerTailLength;

    private int mClockFace;
    private int mClockFaceContourColor;
    private int mHourPointerColor;
    private int mMinutePointerColor;
    private int mSecondPointerColor;
    private int mScaleLongColor;
    private int mScaleShortColor;

    private int initial;
    private int core;

    public AnalogClockView(Context context) {
        super(context);
    }

    public AnalogClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AnalogClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AnalogClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void initStyle(Context context, AttributeSet attributeSet) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.AnalogClockView);

        mPadding = typedArray.getDimension(R.styleable.AnalogClockView_android_padding, UiUtility.dp2px(context, 10));
        mTextSize = typedArray.getDimension(R.styleable.AnalogClockView_android_textSize, UiUtility.sp2px(context, 16));
        mHourPointerWidth = typedArray.getDimension(R.styleable.AnalogClockView_hourPointerWidth, UiUtility.dp2px(context, 5));
        mMinutePointerWidth = typedArray.getDimension(R.styleable.AnalogClockView_minutePointerWidth, UiUtility.dp2px(context, 3));
        mSecondPointerWidth = typedArray.getDimension(R.styleable.AnalogClockView_secondPointerWidth, UiUtility.dp2px(context, 2));
        mPointerCornerRadius = (int) typedArray.getDimension(R.styleable.AnalogClockView_pointerCornerRadius, UiUtility.dp2px(context, 10));
        mPointerTailLength = typedArray.getDimension(R.styleable.AnalogClockView_pointerTailLength, UiUtility.dp2px(context, 10));


        mClockFace = typedArray.getInt(R.styleable.AnalogClockView_clockFace, CLOCK_FACE_WHITE);
        mHourPointerColor = typedArray.getColor(R.styleable.AnalogClockView_hourPointerColor, Color.BLACK);
        mMinutePointerColor = typedArray.getColor(R.styleable.AnalogClockView_minutePointerColor, Color.BLACK);
        mSecondPointerColor = typedArray.getColor(R.styleable.AnalogClockView_secondPointerColor, Color.RED);
        mScaleLongColor = typedArray.getColor(R.styleable.AnalogClockView_scaleLongColor, Color.argb(225, 0, 0, 0));
        mScaleShortColor = typedArray.getColor(R.styleable.AnalogClockView_scaleShortColor, Color.argb(125, 0, 0, 0));

        typedArray.recycle();
    }

    @Override
    protected void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = (int) UiUtility.dp2px(getContext(), 200);
        int height = width;

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            width = Math.min(width, heightSize);
        }

        //noinspection SuspiciousNameCombination
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRadius = (Math.min(w, h) - mPadding) / 2;
        mPointerTailLength = mRadius / 6;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        initial = canvas.save();
        canvas.translate(getWidth() / 2, getHeight() / 2);
        core = canvas.save();
        drawClockFace(canvas);
        drawScale(canvas);
        drawPointer(canvas);
        canvas.restoreToCount(initial);


        if (mode == MODE_AUTO) {
            postInvalidateDelayed(1000);
        }

    }

    private void drawClockFace(Canvas canvas) {
        drawCircle(canvas);
        drawClockFaceContour(canvas);
    }

    private void drawCircle(Canvas canvas) {
        switch (mClockFace) {
            case CLOCK_FACE_BLACK:
                mPaint.setColor(Color.LTGRAY);
                break;
            case CLOCK_FACE_WHITE:
                mPaint.setColor(Color.WHITE);
                break;
        }
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(0, 0, mRadius, mPaint);
    }

    private void drawClockFaceContour(Canvas canvas) {
        switch (mClockFace) {
            case CLOCK_FACE_BLACK:
                //mClockFaceContourColor;
                mPaint.setColor(Color.RED);
                break;
            case CLOCK_FACE_WHITE:
                mPaint.setColor(Color.BLACK);
                break;
        }
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(UiUtility.dp2px(getContext(), 1));
        RectF clockRectF = new RectF(-mRadius, -mRadius, mRadius, mRadius);
        canvas.drawArc(clockRectF, 0, 360, false, mPaint);
    }

    private void drawScale(Canvas canvas) {
        mPaint.setStrokeWidth(UiUtility.dp2px(getContext(), 1));
        int lineWidth;
        for (int i = 0; i < 60; i++) {
            if (i % 5 == 0) {
                mPaint.setStrokeWidth(UiUtility.dp2px(getContext(), 2));
                mPaint.setColor(mScaleLongColor);
                lineWidth = 50;
                mPaint.setTextSize(mTextSize);
                String text = ((i / 5) == 0 ? 12 : (i / 5)) + "";
                Rect textBound = new Rect();
                mPaint.getTextBounds(text, 0, text.length(), textBound);
                mPaint.setColor(Color.BLACK);
                canvas.save();
                canvas.translate(0, -mRadius + lineWidth + (textBound.bottom - textBound.top));
                canvas.rotate(-6 * i);
                mPaint.setStyle(Paint.Style.FILL);
                canvas.drawText(text, -(textBound.right - textBound.left) / 2, textBound.bottom, mPaint);
                canvas.restore();
            } else {
                lineWidth = 30;
                mPaint.setColor(mScaleShortColor);
                mPaint.setStrokeWidth(UiUtility.dp2px(getContext(), 1));
            }
            canvas.drawLine(0, -mRadius, 0, -mRadius + lineWidth, mPaint);
            canvas.rotate(6);
        }
        canvas.restore();
    }

    private void drawPointer(Canvas canvas) {
        Calendar calendar = Calendar.getInstance(); //
        canvas.restoreToCount(core);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        second = calendar.get(Calendar.SECOND);

        if (onTimeChangedListener != null) {
            onTimeChangedListener.onChange(hour, minute, second);
            setTime(hour, minute, second);
            onTimeChangedListener.onChangeTimeString(mTime);
        }

        int angleHour = (hour % 12) * 360 / 12;
        int angleMinute = minute * 360 / 60;
        int angleSecond = second * 360 / 60;

        canvas.save();
        canvas.rotate(angleHour);
        RectF rectFHour = new RectF(-mHourPointerWidth / 2, -mRadius * 3 / 5, mHourPointerWidth / 2, mPointerTailLength);
        mPaint.setColor(mHourPointerColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mHourPointerWidth);
        canvas.drawRoundRect(rectFHour, mPointerCornerRadius, mPointerCornerRadius, mPaint);
        canvas.restore();

        canvas.save();
        canvas.rotate(angleMinute);
        RectF rectFMinute = new RectF(-mMinutePointerWidth / 2, -mRadius * 3.5f / 5, mMinutePointerWidth / 2, mPointerTailLength);
        mPaint.setColor(mMinutePointerColor);
        mPaint.setStrokeWidth(mMinutePointerWidth);
        canvas.drawRoundRect(rectFMinute, mPointerCornerRadius, mPointerCornerRadius, mPaint);
        canvas.restore();

        canvas.save();
        canvas.rotate(angleSecond);
        RectF rectFSecond = new RectF(-mSecondPointerWidth / 2, -mRadius + 15, mSecondPointerWidth / 2, mPointerTailLength);
        mPaint.setColor(mSecondPointerColor);
        mPaint.setStrokeWidth(mSecondPointerWidth);
        canvas.drawRoundRect(rectFSecond, mPointerCornerRadius, mPointerCornerRadius, mPaint);

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mSecondPointerColor);
        canvas.drawCircle(0, 0, mSecondPointerWidth * 4, mPaint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE, super.onSaveInstanceState());
        bundle.putInt(KEY_HOUR, hour);
        bundle.putInt(KEY_MINUTE, minute);
        bundle.putInt(KEY_SECOND, second);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            super.onRestoreInstanceState(bundle.getBundle(INSTANCE));
            hour = bundle.getInt(KEY_HOUR);
            minute = bundle.getInt(KEY_MINUTE);
            second = bundle.getInt(KEY_SECOND);
            return;
        }
        super.onRestoreInstanceState(state);
    }

    public float getPadding() {
        return mPadding;
    }

    public void setPadding(float padding) {
        this.mPadding = padding;
    }

    public float getTextSize() {
        return mTextSize;
    }

    public void setTextSize(float textSize) {
        this.mTextSize = textSize;
    }

    public float getHourPointerWidth() {
        return mHourPointerWidth;
    }

    public void setHourPointerWidth(float hourPointerWidth) {
        this.mHourPointerWidth = hourPointerWidth;
    }

    public float getMinutePointerWidth() {
        return mMinutePointerWidth;
    }

    public void setMinutePointerWidth(float minutePointerWidth) {
        this.mMinutePointerWidth = minutePointerWidth;
    }

    public float getSecondPointerWidth() {
        return mSecondPointerWidth;
    }

    public void setSecondPointerWidth(float secondPointerWidth) {
        this.mSecondPointerWidth = secondPointerWidth;
    }

    public int getPointerCornerRadius() {
        return mPointerCornerRadius;
    }

    public void setPointerCornerRadius(int pointerCornerRadius) {
        this.mPointerCornerRadius = pointerCornerRadius;
    }

    public float getPointerTailLength() {
        return mPointerTailLength;
    }

    public void setPointerTailLength(float pointerTailLength) {
        this.mPointerTailLength = pointerTailLength;
    }

    public int getClockFaceColor() {
        return mClockFace;
    }

    public void setClockFaceColor(int clockFaceColor) {
        this.mClockFace = clockFaceColor;
    }

    public int getHourPointerColor() {
        return mHourPointerColor;
    }

    public void setHourPointerColor(int hourPointerColor) {
        this.mHourPointerColor = hourPointerColor;
    }

    public int getMinutePointerColor() {
        return mMinutePointerColor;
    }

    public void setMinutePointerColor(int minutePointerColor) {
        this.mMinutePointerColor = minutePointerColor;
    }

    public int getSecondPointerColor() {
        return mSecondPointerColor;
    }

    public void setSecondPointerColor(int secondPointerColor) {
        this.mSecondPointerColor = secondPointerColor;
    }

    public int getScaleLongColor() {
        return mScaleLongColor;
    }

    public void setScaleLongColor(int scaleLongColor) {
        this.mScaleLongColor = scaleLongColor;
    }

    public int getScaleShortColor() {
        return mScaleShortColor;
    }

    public void setScaleShortColor(int scaleShortColor) {
        this.mScaleShortColor = scaleShortColor;
    }
}
