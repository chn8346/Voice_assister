package com.example.phoneui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {

    protected static final float FLIP_DISTANCE = 50;
    GestureDetector mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_1);

        final Toast_ toast = new Toast_();

        // global state
        final globalstate gl = (globalstate) this.getApplication();
        gl.SetPos = 4;

        // pos adjust

        Button button1 = (Button) findViewById(R.id.settingbtn_1);
        Button button2 = (Button) findViewById(R.id.settingbtn_2);
        Button button3 = (Button) findViewById(R.id.settingbtn_3);
        Button button4 = (Button) findViewById(R.id.settingbtn_4);
        Button button5 = (Button) findViewById(R.id.settingbtn_5);
        Button button6 = (Button) findViewById(R.id.settingbtn_6);
        TextView title = (TextView) findViewById(R.id.set_title);
        final ImageView img = (ImageView) findViewById(R.id.img_bit_set_test);

        float ADV_height_property = (float) 0.2;

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) title.getLayoutParams();
        if(layoutParams != null)
        {
            layoutParams.topMargin = (int) (gl.heightSize * ADV_height_property);
        }
        title.setText("高级设置");

        // 测试的图片暂时隐藏
        img.setVisibility(View.GONE);


        ADV_height_property = (float) (ADV_height_property + 0.2);
        float size_up = (float) 0.8;
        layoutParams = (RelativeLayout.LayoutParams) button5.getLayoutParams();
        if(layoutParams != null)
        {
            layoutParams.width = (int) (gl.widthSize*size_up);
            layoutParams.height = (int) (gl.widthSize * size_up * 0.2);
            layoutParams.topMargin = (int) (gl.heightSize * ADV_height_property);
        }


        ADV_height_property = (float) (ADV_height_property + 0.03);
        layoutParams = (RelativeLayout.LayoutParams) button6.getLayoutParams();
        if(layoutParams != null)
        {
            layoutParams.width = (int) (gl.widthSize*size_up);
            layoutParams.height = (int) (gl.widthSize * size_up * 0.2);
            layoutParams.topMargin = (int) (gl.heightSize * ADV_height_property + layoutParams.height);

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

        //

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.SYNCSETTING");
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });



        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.APPEARSETTING");
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });


        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.SOUNDSETTING");
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });


        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // thoast
                toast.show(SettingActivity.this,"已进入高级设置", toast.short_time_len);
                //Toast.makeText(SettingActivity.this, "已进入高级设置", Toast.LENGTH_SHORT).show();
            }
        });


        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gl.globalWord) {
                    toast.show(SettingActivity.this,"前后文识别已关闭", toast.short_time_len);
                    //Toast.makeText(SettingActivity.this, "前后文识别已关闭", Toast.LENGTH_SHORT).show();
                }
                else {
                    toast.show(SettingActivity.this,"前后文识别已开启", toast.short_time_len);
                    //Toast.makeText(SettingActivity.this, "前后文识别已开启", Toast.LENGTH_SHORT).show();
                }
                // -------
                gl.globalWord = !gl.globalWord;
            }
        });


        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gl.forceSense)
                {
                    toast.show(SettingActivity.this,"压感识别已关闭，请确认有压感传感器", toast.short_time_len);
                    //Toast.makeText(SettingActivity.this, "压感识别已关闭，请确认有压感传感器", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    toast.show(SettingActivity.this,"压感识别已开启，请确认有压感传感器", toast.short_time_len);
                    //Toast.makeText(SettingActivity.this, "压感识别已开启，请确认有压感传感器", Toast.LENGTH_SHORT).show();
                }
                gl.forceSense = !gl.forceSense;

                //测试截图功能
                /*
                v.setDrawingCacheEnabled(true);
                v.buildDrawingCache(true);

                Bitmap b = Bitmap.createBitmap(v.getDrawingCache());
                v.setDrawingCacheEnabled(false); // clear drawing cache
                img.setImageBitmap(b);
                img.setVisibility(View.VISIBLE);*/
            }
        });

        Button button7 = (Button) findViewById(R.id.settingbtn_7);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView abouts = (TextView) findViewById(R.id.abouts);
        abouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.ABOUT");
                startActivity(intent);
            }
        });

        float sizex = (float) 0.06;
        float sizey = (float) 0.1;

        layoutParams = (RelativeLayout.LayoutParams) abouts.getLayoutParams();
        if(layoutParams != null)
        {
            layoutParams.topMargin = (int) (gl.heightSize * sizex * 0.35);
            layoutParams.leftMargin = (int) (gl.widthSize * 0.8);
            //layoutParams.width = (int) (gl.widthSize * 0.8);
        }


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
