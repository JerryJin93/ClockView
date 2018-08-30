package com.jerryjin.clockview.presenter;

public interface IClockView {

    interface OnTimeChangedListener{
        void onChange(int hour, int minute, int second);
        void onChangeTimeString(String time);
    }

}
