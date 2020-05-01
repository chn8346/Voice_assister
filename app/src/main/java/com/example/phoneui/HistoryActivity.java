package com.example.phoneui;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;

public class HistoryActivity extends AppCompatActivity {

    private VideoPlay vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainbackground);

        // global state
        final globalstate gl = (globalstate)this.getApplication();

        // MSC init
        // 讯飞接口初始化
        SpeechUtility su = SpeechUtility.getUtility();
        final Toast_ toast = new Toast_();
        //toast.show(HistoryActivity.this, "Utility Trying Start", toast.short_time_len);
        if(su == null)
            toast.show(HistoryActivity.this, "Utility Start Failed", toast.short_time_len);

        // 动态背景
        initView();



        //dynamic adjust
        //控件大小位置调整
        DisplayMetrics display = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(display);
        float scaledDensity = display.scaledDensity;
        gl.widthSize = display.widthPixels;
        gl.heightSize = display.heightPixels;

        TextView sets = (TextView) findViewById(R.id.setting);
        TextView help = (TextView) findViewById(R.id.help);
        TextView hello = (TextView) findViewById(R.id.Hello);
        Button talk = (Button) findViewById(R.id.groundtalk);
        Button set = (Button) findViewById(R.id.bottom_1);
        ImageView imag = (ImageView) findViewById(R.id.set_icon);


        // pos adjust

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) talk.getLayoutParams();
        if(layoutParams != null)
        {
            float bu_pro = (float) 0.15;

            layoutParams.width = (int) (gl.heightSize * bu_pro);
            layoutParams.height = (int) (gl.heightSize * bu_pro);
            layoutParams.topMargin = gl.heightSize/2 + layoutParams.height/2;
            layoutParams.leftMargin = (gl.widthSize - layoutParams.width)/2;
        }

        layoutParams = (RelativeLayout.LayoutParams) set.getLayoutParams();
        float high_pro_bu = (float) 0.12;
        if(layoutParams != null)
        {

            layoutParams.topMargin = (int) (gl.heightSize * (1 - high_pro_bu));
            layoutParams.height = (int) (gl.heightSize * high_pro_bu);
            layoutParams.width = (int) (gl.heightSize);

        }

        layoutParams = (RelativeLayout.LayoutParams) imag.getLayoutParams();
        if(layoutParams != null)
        {
            float high_pro = (float) 0.06;
            layoutParams.height = (int) (gl.heightSize * high_pro);
            layoutParams.width = (int) (gl.heightSize * high_pro);
            layoutParams.leftMargin = (gl.widthSize - layoutParams.width)/2;
            layoutParams.topMargin = (int) (gl.heightSize*(1 - high_pro * 2 + 0.01));
            //layoutParams.topMargin = (int) (gl.heightSize * (0.5));
        }

        layoutParams = (RelativeLayout.LayoutParams) imag.getLayoutParams();
        if(layoutParams != null)
        {
            float high_pro = (float) 0.06;
            layoutParams.height = (int) (gl.heightSize * high_pro);
            layoutParams.width = (int) (gl.heightSize * high_pro);
            layoutParams.leftMargin = (gl.widthSize - layoutParams.width)/2;
            layoutParams.topMargin = (int) (gl.heightSize*(1 - high_pro * 2 + 0.01));
            //layoutParams.topMargin = (int) (gl.heightSize * (0.5));
        }

        layoutParams = (RelativeLayout.LayoutParams) hello.getLayoutParams();
        if(layoutParams != null)
        {
            float high_pro = (float) 0.12;
            //layoutParams.height = (int) (gl.heightSize * high_pro);
            //layoutParams.width = (int) (gl.heightSize * high_pro);
            //layoutParams.leftMargin = (gl.widthSize - layoutParams.width)/2;
            layoutParams.topMargin = (int) (gl.heightSize*0.2);
            //layoutParams.topMargin = (int) (gl.heightSize * (0.5));
        }


        //控件设置
        set.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(gl.SetPos == 1)
                {
                    Intent intent = new Intent("android.intent.action.SYNCSETTING");
                    startActivity(intent);
                }
                else if(gl.SetPos == 2)
                {
                    Intent intent = new Intent("android.intent.action.APPEARSETTING");
                    startActivity(intent);
                }
                else if(gl.SetPos == 3)
                {
                    Intent intent = new Intent("android.intent.action.SOUNDSETTING");
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent("android.intent.action.ADVSETTING");
                    startActivity(intent);
                }
            }
        });

        imag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gl.SetPos == 1)
                {
                    Intent intent = new Intent("android.intent.action.SYNCSETTING");
                    startActivity(intent);
                }
                else if(gl.SetPos == 2)
                {
                    Intent intent = new Intent("android.intent.action.APPEARSETTING");
                    startActivity(intent);
                }
                else if(gl.SetPos == 3)
                {
                    Intent intent = new Intent("android.intent.action.SOUNDSETTING");
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent("android.intent.action.ADVSETTING");
                    startActivity(intent);
                }
            }
        });

/*
        //TextView sets = (TextView) findViewById(R.id.setting);
        sets.setClickable(true);
        sets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
                Intent intent = new Intent("android.intent.action.ADVSETTING");
                startActivity(intent);
            }
        });

        //TextView help = (TextView) findViewById(R.id.help);
        help.setClickable(true);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
                Intent intent = new Intent("android.intent.action.ABOUT");
                startActivity(intent);
            }
        });
*/

        talk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent("android.intent.action.HISTORY");
                //gl.startAssistant = true;
                //startActivity(intent);

                // 震动
                Vibrator vbr = (Vibrator) HistoryActivity.this.getSystemService(VIBRATOR_SERVICE);
                assert vbr != null;
                vbr.vibrate(300);
                toast.show(HistoryActivity.this, "未开放的功能", 1000);
                //finish();
            }
        });

        talk.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // voice assistant
                //Toast.makeText(HistoryActivity.this, "请说出你的命令", Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }

    private void initView() {
        //加载视频资源控件
        vp = (VideoPlay) findViewById(R.id.VideoPlayer);
        //设置播放加载路径
        vp.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.v2));
        //播放
        vp.start();
        //循环播放
        vp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                vp.start();
            }
        });
    }
    //返回重启加载
    @Override
    protected void onRestart() {
        initView();
        super.onRestart();
    }

    //防止锁屏或者切出的时候，音乐在播放
    @Override
    protected void onStop() {
        vp.stopPlayback();
        super.onStop();
    }
}
