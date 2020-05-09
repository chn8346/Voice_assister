package com.example.phoneui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class init2 extends AppCompatActivity {

    private char back_twice = '0';
    private boolean hear_bt_v = true;
    private boolean speak_bt_v = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init2);

        // 全局变量
        final globalstate gl = (globalstate) this.getApplication();
        final file_writer file_edit = new file_writer(this);
        speaker speech_speaker = new speaker(this);

        // 防止init1的speaker继续阅读
        speech_speaker.doSpeech("");

        // 布局修改
        DisplayMetrics display = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(display);
        float scaledDensity = display.scaledDensity;
        gl.widthSize = display.widthPixels;
        gl.heightSize = display.heightPixels;

        final Button hear_bt = (Button) findViewById(R.id.init2_hear_en);
        final Button speak_bt = (Button) findViewById(R.id.init2_speak_en);
        final Button next_bt = (Button) findViewById(R.id.next_button);
        TextView title = (TextView) findViewById(R.id.init2_text);
        TextView hear_tip = (TextView) findViewById(R.id.init2_tips_hear);
        TextView speak_tip = (TextView) findViewById(R.id.init2_tips_speak);
        TextView next_tip = (TextView) findViewById(R.id.init2_next);

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) hear_bt.getLayoutParams();
        if(layoutParams != null)
        {
            layoutParams.width = (int) (gl.heightSize*0.15);
            layoutParams.height = (int) (gl.heightSize*0.15);
        }

        layoutParams = (RelativeLayout.LayoutParams) speak_bt.getLayoutParams();
        if(layoutParams != null)
        {
            layoutParams.width = (int) (gl.heightSize*0.15);
            layoutParams.height = (int) (gl.heightSize*0.15);
        }

        layoutParams = (RelativeLayout.LayoutParams) next_bt.getLayoutParams();
        if(layoutParams != null)
        {
            layoutParams.width = (int) (gl.widthSize*0.75);
            layoutParams.height = (int) (gl.heightSize*0.2);
        }

        layoutParams = (RelativeLayout.LayoutParams) title.getLayoutParams();
        if(layoutParams != null)
        {
            layoutParams.topMargin = (int) (gl.heightSize*0.07);
        }

        layoutParams = (RelativeLayout.LayoutParams) hear_tip.getLayoutParams();
        if(layoutParams != null)
        {
        }

        layoutParams = (RelativeLayout.LayoutParams) speak_tip.getLayoutParams();
        if(layoutParams != null)
        {
        }

        layoutParams = (RelativeLayout.LayoutParams) next_tip.getLayoutParams();
        if(layoutParams != null)
        {
            layoutParams.bottomMargin = (int) (gl.heightSize*0.03);
        }


        hear_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hear_bt_v = !hear_bt_v;
                if(hear_bt_v) {
                    hear_bt.setBackgroundResource(R.drawable.init_hear_enable);
                }else{
                    hear_bt.setBackgroundResource(R.drawable.init_hear_disable);
                }
            }
        });

        speak_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak_bt_v = !speak_bt_v;
                if(speak_bt_v) {
                    speak_bt.setBackgroundResource(R.drawable.init_speak_enable);
                }else{
                    speak_bt.setBackgroundResource(R.drawable.init_speak_disable);
                }
            }
        });

        next_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int states = (hear_bt_v ? 1 : 0) + (speak_bt_v ? 10 : 0);
                switch (states)
                {
                    case 0:
                        init_user("deaf", gl, file_edit);
                        break;
                    case 1:
                        init_user("can_not_speak", gl, file_edit);
                        break;
                    case 10:
                        init_user("half_deaf", gl, file_edit);
                        break;
                    case 11:
                        init_user("normal", gl, file_edit);
                        break;
                }

                Intent intent = new Intent("android.intent.action.MAIN");
                startActivity(intent);
                finish();
            }
        });





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

            case "half_deaf":
                file_edit.write(constr_share.user_mode, constr_share.k_user_mode_deaf_no_mute);
                gl.user_mode = constr_share.k_user_mode_deaf_no_mute;
                break;

            case "blind":
            default:
                // 最保险的做法为 blind，可以修改到其他场合
                gl.user_mode = constr_share.k_user_mode_Blind;
                file_edit.write(constr_share.user_mode, constr_share.k_user_mode_Blind);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if(back_twice == '0') {
            Toast_ toast1 = new Toast_();
            toast1.show(init2.this, "再次按返回键即可退出", 3000);
            speaker speaker1 = new speaker(init2.this);
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
