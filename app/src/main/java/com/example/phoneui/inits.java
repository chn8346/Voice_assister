package com.example.phoneui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class inits extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inits);


        // 全局变量
        globalstate gl = (globalstate)this.getApplication();
        Toast_ toast = new Toast_();
        file_writer file_edit = new file_writer(this);

        // 修改布局
        DisplayMetrics display = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(display);
        float scaledDensity = display.scaledDensity;
        gl.widthSize = display.widthPixels;
        gl.heightSize = display.heightPixels;
        Button bt1 = (Button)findViewById(R.id.init_bt1);
        Button bt2 = (Button)findViewById(R.id.init_bt2);
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
            layoutParams.topMargin = (int) (gl.heightSize*0.15);
        }

        // 语音引导
        speaker speech_speaker = new speaker(inits.this);


        // 震动引导

        // 触摸引导

        // 配置文件初始化

        // 根据反馈进行欢迎
    }
}
