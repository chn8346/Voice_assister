package com.example.phoneui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.math.MathUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;



public class inits extends AppCompatActivity {


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inits);

        // 全局变量
        final globalstate gl = (globalstate)this.getApplication();
        Toast_ toast = new Toast_();
        final file_writer file_edit = new file_writer(this);
        final Vibrator vbr = (Vibrator) inits.this.getSystemService(VIBRATOR_SERVICE);
        assert vbr != null;
        touch_num = 0;

        speaker speech_speaker = new speaker(inits.this);


        // 修改布局
        DisplayMetrics display = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(display);
        float scaledDensity = display.scaledDensity;
        gl.widthSize = display.widthPixels;
        gl.heightSize = display.heightPixels;

        Button bt1 = (Button)findViewById(R.id.init_bt1);
        Button bt2 = (Button)findViewById(R.id.init_bt2);
        Button bg_bt = (Button)findViewById(R.id.init_bt_bg);
        TextView textView = (TextView) findViewById(R.id.init_text);


        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) bt1.getLayoutParams();
        if(layoutParams != null)
        {
            layoutParams.width = (int) (gl.widthSize*0.8);
            layoutParams.height = (int) (gl.widthSize*0.8);
        }

        layoutParams = (RelativeLayout.LayoutParams) bt2.getLayoutParams();
        if(layoutParams != null)
        {
            layoutParams.width = (int) (gl.widthSize*0.5);
            layoutParams.height = (int) (gl.widthSize*0.5);
        }

        layoutParams = (RelativeLayout.LayoutParams) textView.getLayoutParams();
        if(layoutParams != null)
        {
            layoutParams.topMargin = (int) (gl.heightSize*0.07);
        }

        boolean init_not_finish = true;

        /*
        *   判断用户的方法:
        *
        *   1、直接点按成功，说明是正常人
        *   2、直接点击进入会询问是否可以听见语音提示，是否可以说话，以此判断为聋哑人群
        *   3、如果是长按进入，说明是失明或弱视群体。防止误触，正常人第一次进入这类主界面会提示长按可退出
        *
        * */

        // 触摸引导


            // 外层圈
        bt1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();

                switch (action)
                {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        if(!vbr_work) {
                            int pos_x = (int) event.getRawX();
                            int pos_y = (int) event.getRawY();
                            int heavy = vibe_simple(pos_x, pos_y, vbr);
                            touch_num++;
                            Log.d("________TOUCH_NUM", "______ "+touch_num+" ______");
                            if(heavy < prim_vbr*0.2)
                            {
                                Intent intent = new Intent("android.intent.action.MAIN");
                                startActivity(intent);
                                finish();
                            }
                        }
                }

                return true;
            }
        });

            // 内层圈
        bt2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();

                switch (action)
                {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        if(!vbr_work) {
                            int pos_x = (int) event.getRawX();
                            int pos_y = (int) event.getRawY();
                            int heavy = vibe_simple(pos_x, pos_y, vbr);
                            touch_num++;
                            Log.d("________TOUCH_NUM", "______ "+touch_num+" ______");
                            if(heavy < prim_vbr*0.2)
                            {
                                Intent intent = new Intent("android.intent.action.MAIN");
                                startActivity(intent);
                                finish();
                            }
                        }
                }

                return true;
            }
        });

            // 背景层
        bg_bt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();

                switch (action)
                {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        if(!vbr_work) {
                            int pos_x = (int) event.getRawX();
                            int pos_y = (int) event.getRawY();
                            int heavy = vibe_simple(pos_x, pos_y, vbr);
                            touch_num++;
                            Log.d("________TOUCH_NUM", "______ "+touch_num+" ______");
                            if(heavy < prim_vbr*0.2)
                            {

                                Intent intent = new Intent("android.intent.action.MAIN");
                                startActivity(intent);
                                finish();
                            }
                        }
                }

                return true;
            }
        });

        // TODO 优化BUG：2+以上手指进行干扰时只计算和观察距离近的那个

        // 语音引导
        speech_speaker.doSpeech("欢迎使用语音助手,您可以点击按钮以开始使用助手，如果无法看见屏幕，" +
                "请常按屏幕，从边缘向屏幕中央滑动，手机震动越大，距离按钮越近");
    }

    private int touch_num = 0;
    private int prim_vbr = 300;
    private boolean vbr_work = false;
    private char back_twice = '0';

    // 总配置方法
    private void init_all(String user_classify, globalstate gl, file_writer file_edit)
    {
        init_file(file_edit);
        init_user(user_classify, gl, file_edit);
    }

    // 配置文件初始化
    private void init_file(file_writer file_edit)
    {

    }

    // 根据反馈进行欢迎
    private void init_user(String user_classify, globalstate gl, file_writer file_edit)
    {
        switch (user_classify)
        {
            case "normal":
                file_edit.write(constr_share.user_mode, constr_share.k_user_mode_normal);
                gl.user_mode = constr_share.k_user_mode_normal;
                break;

            case "deaf":
                file_edit.write(constr_share.user_mode, constr_share.k_user_mode_deaf);
                gl.user_mode = constr_share.k_user_mode_deaf;
                break;

            case "can_not_speak":
                file_edit.write(constr_share.user_mode, constr_share.k_user_mode_mute);
                gl.user_mode = constr_share.k_user_mode_mute;
                break;

            case "blind":
            default:
                // 最保险的做法为 blind，可以修改到其他场合
                gl.user_mode = constr_share.k_user_mode_Blind;
                file_edit.write(constr_share.user_mode, constr_share.k_user_mode_Blind);
                break;
        }
    }

    // 震动控制封装
    private int vibe_simple(int x, int y, final Vibrator vbr)
    {
        vbr_work = true;
        globalstate gl = (globalstate) this.getApplication();
        final float xpro = (float) Math.abs((float) x/gl.widthSize - 0.5);
        final float ypro = (float) Math.abs((float) y/gl.heightSize - 0.5);
        final int heavy = (int) (prim_vbr*Math.sqrt(xpro*xpro + ypro*ypro));

        vibe_simple(heavy, vbr);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                vbr_work = false;
                //Log.d("POS________", "x " + xpro + ",  y "+ ypro + "  heavy " + heavy);
            }
        }, prim_vbr);

        return heavy;
    }

    private void vibe_simple(int weight_1to100, final Vibrator vbr)
    {
        int part = 5;
        final int part_prim = prim_vbr/part;
        int go_prim = part_prim - weight_1to100/part;

        for(int i = 0; i < part; i++) {

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    vbr.vibrate(part_prim);
                }
            }, part_prim * i);

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    vbr.cancel();
                }
            }, go_prim + part_prim * i);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onBackPressed() {
        if(back_twice == '0') {
            Toast_ toast1 = new Toast_();
            toast1.show(inits.this, "再次按返回键即可退出", 3000);
            speaker speaker1 = new speaker(inits.this);
            speaker1.doSpeech("再次按返回键即可退出");
            back_twice = '1';
        }
        else{
            // 1. 通过Context获取ActivityManager
            ActivityManager activityManager = (ActivityManager) this.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);

            // 2. 通过ActivityManager获取任务栈
            assert activityManager != null;
            List<ActivityManager.AppTask> appTaskList = activityManager.getAppTasks();

            // 3. 逐个关闭Activity
            for (ActivityManager.AppTask appTask : appTaskList) {
                appTask.finishAndRemoveTask();
            }
            // 4. 结束进程
            System.exit(0);
        }
    }

}
