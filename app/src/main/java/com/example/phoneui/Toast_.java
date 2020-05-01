package com.example.phoneui;

import android.content.Context;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class Toast_ {
    public void show(Context context, String s, int timeLen)
    {
        // limit time length
        if(timeLen > 3000)
            timeLen = 3000;

        // show
        Toast toast = Toast.makeText(context, s, Toast.LENGTH_LONG);
        toast.show();

        // break when time is up
        stop(toast, timeLen);
    }

    public int short_time_len = 700;

    private void stop(final Toast toast, int duration) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, duration);
    }
}
