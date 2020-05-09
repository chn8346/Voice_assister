package com.example.phoneui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class init2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init2);

        // 全局变量
        globalstate gl = (globalstate) this.getApplication();
        file_writer file_edit = new file_writer(this);
        speaker speech_speaker = new speaker(this);

        // 布局修改
        DisplayMetrics display = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(display);
        float scaledDensity = display.scaledDensity;
        gl.widthSize = display.widthPixels;
        gl.heightSize = display.heightPixels;

        Button hear_bt = (Button) findViewById(R.id.init2_hear_en);
        Button speak_bt = (Button) findViewById(R.id.init2_speak_en);
        Button next_bt = (Button) findViewById(R.id.next_button);
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
}
