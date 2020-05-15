package com.example.phoneui;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;

public class window extends Service {

    // 悬浮窗的控件对象和参数
    private boolean init_window_manager = false;
    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;
    private Button button;
    private StringBuffer msg;


    @Override
    public void onCreate() {
        super.onCreate();

        msg = new StringBuffer();
        msg.append("+");

        if (!init_window_manager) {
            init_window_manager = true;

            windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

            // 新建悬浮窗控件
            button = new Button(getApplicationContext());
            button.setText(msg.toString());
            button.setBackgroundColor(Color.BLUE);
            button.setBackgroundResource(R.drawable.bottom_1);

            globalstate gl = (globalstate) getApplication();
            // 设置LayoutParam
            layoutParams = new WindowManager.LayoutParams();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            } else {
                layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
            }
            layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
            layoutParams.format = PixelFormat.RGBA_8888;
            layoutParams.width = gl.widthSize;
            layoutParams.height = gl.heightSize;
            layoutParams.x = 0;
            layoutParams.y = 0;

            // 将悬浮窗控件添加到WindowManager
            //windowManager.addView(button, layoutParams);

            Log.d("_ACCESS_Event__", "START SERVICE: BLIND SERVICE START_ED");
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg.append("+");
                button.setText(msg.toString());
            }
        });
    }


    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        init_window_manager = false;
        windowManager.removeView(button);

        Log.d("_ACCESS_Event__", "STOP SERVICE: BLIND SERVICE STOP_ED");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
