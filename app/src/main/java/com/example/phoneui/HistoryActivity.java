package com.example.phoneui;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.VoiceWakeuper;
import com.iflytek.cloud.WakeuperListener;
import com.iflytek.cloud.WakeuperResult;
import com.iflytek.cloud.util.ResourceUtil;
import com.iflytek.cloud.util.ResourceUtil.RESOURCE_TYPE;

import org.json.JSONException;
import org.json.JSONObject;

public class HistoryActivity extends AppCompatActivity {

    private VideoPlay vp;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainbackground);

        /*
        *   主程序目录
        *
        *   1. 初始化
        *   2. 控件调整
        *   3. 控件点按设置
        *   4. 特殊情况的处理，重启
        *
        *
        * */

        //-------------------------------------------------------------------------

        /*
        *
        *       初始化
        *
        * */

        // 全局变量初始化
        final globalstate gl = (globalstate)this.getApplication();
        final file_writer file_edit = new file_writer(this);
        SpeechUtility su = SpeechUtility.getUtility();
        final Toast_ toast = new Toast_();
        final assistant ass = new assistant((su != null), HistoryActivity.this);


        // global state 初始化数据
        gl.update_global_state(file_edit);

        //测试模式加载
        if(gl.testMode)
        {
            gl.testMode = false;
            Intent intent = new Intent("android.intent.action.INIT");
            startActivity(intent);
        }
        // 测试参数设置,如果进行过testMode则不允许二次进入
        if(gl.testMode) {
            gl.testMode = false;
        }

        //判断是否需要第一次初始化
        if(file_edit.read(constr_share.first_use, true))
        {
            toast.show(this, "初次使用初始化", 2000);
            Intent intent = new Intent("android.intent.action.INIT");
            startActivity(intent);
        }
        //不需要初始化的情况下直接初始化唤醒即可
        else {
            ass.init_wake();
        }

        // 动态背景只在非盲人模式开启
        if(file_edit.read(constr_share.user_mode, "null_").equals(constr_share.k_user_mode_Blind))
        {
            initView(gl.user_mode);
        }
        else {
            initView(gl.user_mode);
        }


        /*
         *
         *       控件调整
         *
         * */

        //dynamic adjust
        //控件大小位置调整
        DisplayMetrics display = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(display);
        float scaledDensity = display.scaledDensity;
        gl.widthSize = display.widthPixels;
        gl.heightSize = display.heightPixels;

        TextView hello = (TextView) findViewById(R.id.Hello);
        Button blind_mode_back_tip = (Button) findViewById(R.id.blind_quit);
        final Button talk = (Button) findViewById(R.id.groundtalk);
        Button set = (Button) findViewById(R.id.bottom_1);
        Button back_ground_button = (Button) findViewById(R.id.main_background);
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

        //toast.show(this, gl.user_mode, 1000);
        // 盲人模式设置
        if(gl.user_mode.equals(constr_share.k_user_mode_Blind))
        {
            //toast.show(this, "blind mode", 1000);
            set.setVisibility(View.GONE);
            talk.setVisibility(View.GONE);
            hello.setText("盲人模式");
            imag.setVisibility(View.GONE);
            back_ground_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    talk.callOnClick();
                }
            });

            // TODO 下一版本只在第一次进入可退出盲人模式，其他情况需要进入设置或者语音修改
            blind_mode_back_tip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO 下一版本添加 “记录上次的用户模式” 功能
                    gl.user_mode = constr_share.k_user_mode_normal;
                    file_edit.write(constr_share.user_mode, constr_share.k_user_mode_normal);
                    restart();
                }
            });
        }
        else
        {
            blind_mode_back_tip.setVisibility(View.GONE);
        }




        /*
         *
         *       特殊情况的处理
         *
         * */

        //控件点按设置

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

        // TODO 写出分词功能         ----  次要
        // TODO 解决testMode不能用的问题  --- 最次
        // TODO 编写残疾人的终端区分  ---- 最重要

        talk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent("android.intent.action.HISTORY");
                //gl.startAssistant = true;
                //startActivity(intent);
                //toast.show(HistoryActivity.this, "go", 700);
                // 震动
                Vibrator vbr = (Vibrator) HistoryActivity.this.getSystemService(VIBRATOR_SERVICE);
                assert vbr != null;
                vbr.vibrate(300);
                //toast.show(HistoryActivity.this, "未开放的功能", 1000);
                //finish();

                ass.init_listener();
                //toast.show(HistoryActivity.this, "请说出你的命令", toast.short_time_len);

                // 执行接口
                ass.listen_result();
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

    /*
     *
     *       控件点按设置
     *
     * */


    private void initView(String user_mode) {
        //加载视频资源控件
        vp = (VideoPlay) findViewById(R.id.VideoPlayer);
        //设置播放加载路径
        if(user_mode.equals(constr_share.k_user_mode_Blind))
        {
            vp.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.v2_dark));
        }
        else {
            vp.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.v2));
        }
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
        globalstate gl = (globalstate) HistoryActivity.this.getApplication();
        initView(gl.user_mode);
        super.onRestart();
    }


    //防止锁屏或者切出的时候，音乐在播放
    @Override
    protected void onStop() {
        vp.stopPlayback();
        super.onStop();
    }

    private void restart()
    {
        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(getBaseContext().getPackageName());
        assert i != null;
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    // 唤醒词使用的路径寻求函数
    private String getResource() {
        final String resPath = ResourceUtil.generateResourcePath(HistoryActivity.this, RESOURCE_TYPE.assets, "ivw/"+getString(R.string.app_id)+".jet");
        Log.d( "TAG", "resPath: "+resPath );
        return resPath;
    }

}
