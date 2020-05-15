package com.example.phoneui;

import android.accessibilityservice.AccessibilityService;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
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


    @Override
    public void onCreate() {
        super.onCreate();

        if (!init_window_manager) {
            init_window_manager = true;

            windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

            // 新建悬浮窗控件
            button = new Button(getApplicationContext());
            button.setText("Floating Window");
            button.setBackgroundColor(Color.BLUE);
            button.setBackgroundResource(R.drawable.bottom_1);

            // 设置LayoutParam
            layoutParams = new WindowManager.LayoutParams();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            } else {
                layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
            }
            layoutParams.format = PixelFormat.RGBA_8888;
            layoutParams.width = 500;
            layoutParams.height = 100;
            layoutParams.x = 300;
            layoutParams.y = 300;

            // 将悬浮窗控件添加到WindowManager
            windowManager.addView(button, layoutParams);

            Log.d("_ACCESS_Event__", "START SERVICE: BLIND SERVICE START_ED");
        }
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
