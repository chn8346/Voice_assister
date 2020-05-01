package com.example.phoneui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SyncSetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_4);

        globalstate gl = (globalstate) this.getApplication();
        final Toast_ toast = new Toast_();
        gl.SetPos = 1;


        Button button1 = (Button) findViewById(R.id.settingbtn_1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast.show(SyncSetActivity.this,"已进入同步设置", toast.short_time_len);
                //Toast.makeText(SyncSetActivity.this, "已进入同步设置", Toast.LENGTH_SHORT).show();
            }
        });


        Button button2 = (Button) findViewById(R.id.settingbtn_2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.APPEARSETTING");
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        Button button3 = (Button) findViewById(R.id.settingbtn_3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.SOUNDSETTING");
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        Button button4 = (Button) findViewById(R.id.settingbtn_4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // thoast
                Intent intent = new Intent("android.intent.action.ADVSETTING");
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        Button button5 = (Button) findViewById(R.id.settingbtn_5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast.show(SyncSetActivity.this,"开始同步", toast.short_time_len);
                //Toast.makeText(SyncSetActivity.this, "开始同步", Toast.LENGTH_SHORT).show();
                // -------

            }
        });

        Button button7 = (Button) findViewById(R.id.settingbtn_7);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // pos adjust
        TextView title = (TextView) findViewById(R.id.set_title);

        float ADV_height_property = (float) 0.2;

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) title.getLayoutParams();
        if(layoutParams != null)
        {
            layoutParams.topMargin = (int) (gl.heightSize * ADV_height_property);
        }
        title.setText("同步设置");

        ADV_height_property = (float) (ADV_height_property + 0.2);
        float size_up = (float) 0.8;
        layoutParams = (RelativeLayout.LayoutParams) button5.getLayoutParams();
        if(layoutParams != null)
        {
            layoutParams.topMargin = (int) (gl.heightSize * ADV_height_property);
            layoutParams.width = (int) (gl.widthSize*size_up);
            layoutParams.height = (int) (gl.widthSize * size_up * 0.2);
        }

        float mh = (float) 0.83;
        float mw = (float) 0.07;
        float inw = (float) (1 - mw * 2)/4;
        float r = (float) 0.1;

        layoutParams = (RelativeLayout.LayoutParams) button1.getLayoutParams();
        if(layoutParams != null)
        {
            layoutParams.height = (int) (gl.heightSize * r);
            layoutParams.width = (int) (gl.heightSize * r);
            layoutParams.topMargin = (int) (gl.heightSize * mh);
            layoutParams.leftMargin = (int) (gl.widthSize * mw);
        }

        layoutParams = (RelativeLayout.LayoutParams) button2.getLayoutParams();
        if(layoutParams != null)
        {
            layoutParams.height = (int) (gl.heightSize * r);
            layoutParams.width = (int) (gl.heightSize * r);
            layoutParams.topMargin = (int) (gl.heightSize * (mh + 0));
            layoutParams.leftMargin = (int) (gl.widthSize * (mw + inw * 1));
        }

        layoutParams = (RelativeLayout.LayoutParams) button3.getLayoutParams();
        if(layoutParams != null)
        {
            layoutParams.height = (int) (gl.heightSize * r);
            layoutParams.width = (int) (gl.heightSize * r);
            layoutParams.topMargin = (int) (gl.heightSize * (mh + 0));
            layoutParams.leftMargin = (int) (gl.widthSize * (mw + inw * 2));
        }

        layoutParams = (RelativeLayout.LayoutParams) button4.getLayoutParams();
        if(layoutParams != null)
        {
            layoutParams.height = (int) (gl.heightSize * r);
            layoutParams.width = (int) (gl.heightSize * r);
            layoutParams.topMargin = (int) (gl.heightSize * (mh + 0));
            layoutParams.leftMargin = (int) (gl.widthSize * (mw + inw * 3));
        }

        float sizex = (float) 0.06;
        float sizey = (float) 0.1;

        TextView abouts = (TextView) findViewById(R.id.abouts);
        layoutParams = (RelativeLayout.LayoutParams) abouts.getLayoutParams();
        if(layoutParams != null)
        {
            layoutParams.topMargin = (int) (gl.heightSize * sizex * 0.35);
            layoutParams.leftMargin = (int) (gl.widthSize * 0.8);
            //layoutParams.width = (int) (gl.widthSize * 0.8);
        }

        abouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.ABOUT");
                startActivity(intent);
            }
        });

        layoutParams = (RelativeLayout.LayoutParams) button7.getLayoutParams();
        if(layoutParams != null)
        {
            layoutParams.height = (int) (gl.heightSize * sizex);
            layoutParams.width = (int) (gl.heightSize * sizey);
            layoutParams.topMargin = (int) (gl.heightSize * sizex * 0.35);
            layoutParams.leftMargin = (int) (gl.heightSize * sizex * 0.25);
        }
    }
}
