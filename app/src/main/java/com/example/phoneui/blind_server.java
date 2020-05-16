package com.example.phoneui;

import android.Manifest;
import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.List;


/**
 * This class demonstrates how an accessibility service can query
 * window content to improve the feedback given to the user.
 */

public class blind_server extends AccessibilityService{

    // 悬浮窗的控件对象和参数
    private boolean init_window_manager = false;
    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;
    private Button button;
    private StringBuffer info = new StringBuffer();


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onServiceConnected() {
        if (!init_window_manager) {
            init_window_manager = true;

            windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

            // 新建悬浮窗控件
            button = new Button(getApplicationContext());
            button.setText("Floating Window");
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
            //layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
            layoutParams.format = PixelFormat.RGBA_8888;
            layoutParams.width = gl.widthSize;
            layoutParams.height = gl.heightSize;
            layoutParams.x = 0;
            layoutParams.y = 0;

            // 将悬浮窗控件添加到WindowManager
            windowManager.addView(button, layoutParams);

            Log.d("_ACCESS_Event__", "START SERVICE: BLIND SERVICE START_ED");

            // 触摸感应
            button.setOnTouchListener(new View.OnTouchListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();

                    switch (action)
                    {
                        case MotionEvent.ACTION_DOWN:
                        case MotionEvent.ACTION_MOVE:
                            int pos_x = (int) event.getRawX();
                            int pos_y = (int) event.getRawY();
                            info.append("x: ").append(pos_x).append(" y: ").append(pos_y);
                    }

                    return true;
                }
            });
        }
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        JSONObject json = new JSONObject();
        json.put("type", event.getEventType());
        json.put("package", event.getPackageName().toString());

        info.append(event.toString());
        button.setText(info.toString());

        Log.d("_ACCESS_Event__", json.toJSONString());
    }

    @Override
    public void onInterrupt() {

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


    public static boolean isStartAccessibilityService(Context context, String name){
        AccessibilityManager am = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        assert am != null;
        List<AccessibilityServiceInfo> serviceInfos = am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_GENERIC);
        for (AccessibilityServiceInfo info : serviceInfos) {
            String id = info.getId();
            if (id.contains(name)) {
                return true;
            }
        }
        return false;
    }

}