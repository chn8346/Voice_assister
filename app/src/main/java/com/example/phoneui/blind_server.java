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
import android.util.MonthDisplayHelper;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;
import android.widget.Button;

import androidx.annotation.RequiresApi;

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
    private int ser_num = 0;
    private int pos_x;
    private int pos_y;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onServiceConnected() {

        globalstate gl = (globalstate) getApplication();
        if(!gl.user_mode.equals(constr_share.k_user_mode_Blind)) {
            disableSelf();
            Log.d("_ACCESS_Event__", "STOP : NOT BLIND SERVICE ==== SELF STOP ====");
        }
        if (!init_window_manager) {
            init_window_manager = true;

            windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

            // 新建悬浮窗控件
            button = new Button(getApplicationContext());
            button.setText("_MBS_");
            button.setBackgroundColor(Color.BLUE);
            button.setBackgroundResource(R.drawable.bottom_1);

            // 设置LayoutParam
            layoutParams = new WindowManager.LayoutParams();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            } else {
                layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
            }
            layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
            layoutParams.format = PixelFormat.RGBA_8888;
            layoutParams.width = (int) (gl.widthSize * 0.3);
            layoutParams.height = (int) (gl.heightSize * 0.3);
            layoutParams.x = 0;
            layoutParams.y = 0;

            // 将悬浮窗控件添加到WindowManager
            // windowManager.addView(button, layoutParams);

            Log.d("_ACCESS_Event__", "START SERVICE: BLIND SERVICE ==== START ====");

            // 触摸感应位置
            button.setOnTouchListener(new View.OnTouchListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();

                    switch (action)
                    {
                        case MotionEvent.ACTION_DOWN:
                        case MotionEvent.ACTION_MOVE:
                            pos_x = (int) event.getRawX();
                            pos_y = (int) event.getRawY();
                    }

                    return true;
                }
            });
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int event_type = event.getEventType();
        ser_num = (ser_num + 1) % 10000;
        if(event == null)
        {
            Log.d("_ACCESS_Event__", "event__null__" + ser_num);
        }
        else {
            Log.d("_ACCESS_Event__", "on event, ser num:" + ser_num + " , " + event_type);
        }

        switch (event_type)
        {
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                Log.d("__EVENT__", "onAccessibilityEvent: click " + ser_num);
                check_focus();
                break;
            case AccessibilityEvent.TYPE_VIEW_FOCUSED:
                Log.d("__EVENT__", "onAccessibilityEvent: focus " + ser_num);
                break;
            case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_END:
                Log.d("__EVENT__", "EXPLORATION_GESTURE_END" + ser_num);
                break;
            case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_START:
                Log.d("__EVENT__", "EXPLORATION_GESTURE_START" + ser_num);
                break;
            case AccessibilityEvent.TYPE_TOUCH_INTERACTION_END:
                Log.d("__EVENT__", "TOUCH_INTERACTION_END" + ser_num);
                break;
            case AccessibilityEvent.TYPE_TOUCH_INTERACTION_START:
                Log.d("__EVENT__", "TOUCH_INTERACTION_START" + ser_num);
                break;
            case AccessibilityEvent.TYPE_VIEW_SCROLLED:
                Log.d("__EVENT__", "SCROLLED" + ser_num);
                break;
            case AccessibilityEvent.TYPE_VIEW_SELECTED:
                Log.d("__EVENT__", "VIEW_SELECTED" + ser_num);
                break;
            case AccessibilityEvent.TYPE_VIEW_HOVER_ENTER:
                Log.d("__EVENT__", "HOVER_ENTER" + ser_num);
                break;
            case AccessibilityEvent.TYPE_ANNOUNCEMENT:
                Log.d("__EVENT__", "ANNOUNCEMENT" + ser_num);
                break;
            case AccessibilityEvent.TYPE_WINDOWS_CHANGED:
                Log.d("__EVENT__", "WINDOWS_CHANGED" + ser_num);
                break;
        }
    }

    private void check_focus() {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        Log.d("__EVENT__", "check_focus: " + nodeInfo.getPackageName().toString());

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
        //windowManager.removeView(button);

        Log.d("_ACCESS_Event__", "STOP SERVICE: BLIND SERVICE ==== STOP ====");
    }

}