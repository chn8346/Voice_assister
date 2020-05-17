package com.example.phoneui;

import android.Manifest;
import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
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
    private globalstate gl;

    private StringBuffer notify_text;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onServiceConnected() {

        gl = (globalstate) getApplication();
        if(!gl.user_mode.equals(constr_share.k_user_mode_Blind)) {
            Log.d("_ACCESS_Event__", "STOP : NOT BLIND SERVICE ==== SELF STOP ====");
            disableSelf();
        }
        if (!init_window_manager) {

            init_window_manager = true;

            notify_text = new StringBuffer();

            // 目前发现好像用不到悬浮窗，暂时注释代码

            /*
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
            });*/
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if(!gl.user_mode.equals(constr_share.k_user_mode_Blind))
        {
            disableSelf();
        }

        int event_type = event.getEventType();
        ser_num = (ser_num + 1) % 10000;
        if(event == null)
        {
            Log.d("_ACCESS_Event__", "event__null__" + ser_num);
        }
        else {
            Log.d("_ACCESS_Event__", "on event, ser num:" + ser_num + " , " + event_type);
        }

        String package_name = (String) event.getPackageName();
        if(package_name != null)
        {
            Log.d("_ACCESS_Event__", "package_name : " + package_name );
        }

        switch (event_type)
        {
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                Log.d("_AC_EVENT__", "onAccessibilityEvent: click " + ser_num);
                process_click(event);
                break;
            case AccessibilityEvent.TYPE_VIEW_FOCUSED:
                Log.d("_AC_EVENT__", "onAccessibilityEvent: focus " + ser_num);
                process_focus(event);
                break;
            case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_END:
                Log.d("_AC_EVENT__", "EXPLORATION_GESTURE_END" + ser_num);
                process_gesture(event);
                break;
            case AccessibilityEvent.TYPE_VIEW_HOVER_ENTER:
                Log.d("_AC_EVENT__", "HOVER_ENTER" + ser_num);
                process_hove_enter(event);
                break;
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                Log.d("_AC_EVENT__", "ANNOUNCEMENT" + ser_num);
                process_announcement(event);
                break;
            case AccessibilityEvent.TYPE_WINDOWS_CHANGED:
                Log.d("_AC_EVENT__", "WINDOWS_CHANGED" + ser_num);
                process_window_change(event);
                break;
        }
    }


    // 检查聚焦点
    private void check_focus() {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        Log.d("__EVENT__", "check_focus: " + nodeInfo.getPackageName().toString());

    }

    // 点击事件的处理
    private void process_click(AccessibilityEvent event)
    {

    }

    // 聚焦事件的处理
    private void process_focus(AccessibilityEvent event)
    {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
    }

    // 手势事件的处理
    private void process_gesture(AccessibilityEvent event)
    {

    }

    // 指针选定的事件处理
    private void process_hove_enter(AccessibilityEvent event)
    {

    }

    // 通知的事件处理
    private void process_announcement(AccessibilityEvent event)
    {
        // 获取应用名称
        String app = (String) event.getPackageName();
        // 暂时读到log里面
        for (CharSequence text : event.getText()) {
            String t = text.toString();
            // 不包含之前信息的情况下再次读取
            if(!notify_text.toString().contains(t)) {
                Log.d("EVE__NOTIFY", "__" + t);
                notify_text.append(t);
                // todo 读出消息

            }
        }
    }

    // 更换界面的事件处理
    private void process_window_change(AccessibilityEvent event)
    {
        // todo 提示界面更换

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