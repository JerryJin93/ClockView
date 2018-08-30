package com.jerryjin.clockview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jerryjin.clockview.presenter.OnTimeChangedListenerImpl;
import com.jerryjin.clockview.view.AnalogClockView;
import com.jerryjin.fastandroid.utility.UiUtility;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((AnalogClockView)findViewById(R.id.clockView))
                .setOnTimeChangedListener(new OnTimeChangedListenerImpl() {
                    @Override
                    public void onChangeTimeString(String time) {
                        super.onChangeTimeString(time);
                        ((TextView)findViewById(R.id.time))
                            .setText(time);
                    }
                });



    }
}
