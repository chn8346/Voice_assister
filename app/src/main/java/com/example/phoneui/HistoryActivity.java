package com.example.phoneui;

import android.Manifest;
import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.iflytek.cloud.SpeechUtility;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.huawei.hiai.nlu.sdk.NLUAPILocalService;
import com.huawei.hiai.nlu.sdk.NLUAPIService;
import com.huawei.hiai.nlu.model.ResponseResult; //huawei 接口返回的结果类
import com.huawei.hiai.nlu.sdk.NLUConstants; //huawei 接口常量类
import com.huawei.hiai.nlu.sdk.OnResultListener; //huawei 异步函数，执行成功的回调结果类
import com.tencent.soter.wrapper.SoterWrapperApi;
import com.tencent.soter.wrapper.wrap_callback.SoterProcessCallback;
import com.tencent.soter.wrapper.wrap_callback.SoterProcessNoExtResult;
import com.tencent.soter.wrapper.wrap_task.InitializeParam;


public class HistoryActivity extends AppCompatActivity {

    private VideoPlay vp;

    private boolean init_soter = false;

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
        * */

        //------------------------------------------------

        /*
        *
        *       初始化
        *
        * */

        // 全局变量初始化
        final globalstate gl = (globalstate)this.getApplication();
        final file_writer file_edit = new file_writer(this);
        speaker speech_speaker = new speaker(this);
        SpeechUtility su = SpeechUtility.getUtility();
        final Toast_ toast = new Toast_();

        // global state 初始化数据
        gl.update_global_state(file_edit);

        // 华为NLU服务启动
        boolean first_use_huawei_nlu_model = true;
        if(gl.HW_nlu_use_time < 1)
        {
            first_use_huawei_nlu_model = false;
            gl.HW_nlu_use_time = gl.HW_nlu_use_time + 1;
            gl.re_write_global_state_file(file_edit);// 重写文件
        }
        NLUAPIService.getInstance().init(HistoryActivity.this, new OnResultListener< Integer >() {
            @Override
            public void onResult(Integer result) {
                // 初始化成功回调，在服务初始化成功调用该函数
                gl.Hw_nlu_start = true;
                //toast.show(HistoryActivity.this, "NLU初始化成功", 1500);
                Log.d("_____NLU_____", "_____NLU_____OK_____");
            }
        }, first_use_huawei_nlu_model);

        // 由于打招呼的文本框还要作为对话框，这里提前声明，写入语音助手类
        TextView hello = (TextView) findViewById(R.id.Hello);
        // 语音助手类初始化，导入上一行的文本框
        final assistant ass = new assistant((su != null), HistoryActivity.this, hello);

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
        // 盲人模式下打开盲人无障碍服务
        if(gl.user_mode.equals(constr_share.k_user_mode_Blind))
        {
            Log.d("user_mode", "onCreate: user_mode blind");
        }
        // Log.d("_ACCESS_Event__", Manifest.permission.BIND_ACCESSIBILITY_SERVICE);
        Log.d("_ACCESS_Event__", "START BLIND SERVER IN MAIN PROCESS");
        Intent intent_ = new Intent(this, blind_server.class);
        startService(intent_);
        */

        Intent intent_ = new Intent(this, window.class);
        startService(intent_);

        if (!OpenAccessibilitySettingHelper.isAccessibilitySettingsOn(this,
                blind_server.class.getName())){// 判断服务是否开启
            Log.d("_ACCESS_Event__", "access_permission: BLIND SERVER NOT IN");
            OpenAccessibilitySettingHelper.jumpToSettingPage(this);// 跳转到开启页面
        } else {
            Toast.makeText(this, "服务已开启", Toast.LENGTH_SHORT).show();
            Log.d("_ACCESS_Event__", "access_permission: BLIND SERVER IS RUN");
        }

        Intent intent1 = new Intent(this, blind_server.class);
        startService(intent1);

        Log.d("_ACCESS_Event__", "access_permission: " + isStartAccessibilityService(this, "blind_server"));

        // soter 指纹验证模块
        if(!init_soter) {
            init_soter = false;

            InitializeParam param = new InitializeParam.InitializeParamBuilder()
                    .setScenes(0) // 场景值常量，后续使用该常量进行密钥生成或指纹认证
                    .build();
            SoterWrapperApi.init(HistoryActivity.this,
                    new SoterProcessCallback<SoterProcessNoExtResult>() {
                        @Override
                        public void onResult(@NonNull SoterProcessNoExtResult result) {

                        }
                    },
                    param);
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


        // log 输出应用信息
        /*
        PackageManager packageManager = HistoryActivity.this.getPackageManager();
        List<PackageInfo> app_list = packageManager.getInstalledPackages(0);

        int len = app_list.size();
        for(int i = 0; i < len; i++)
        {
            PackageInfo info = app_list.get(i);
            if(info.packageName.equals("com.example.phoneui"))
            {
                //Log.d("____APP___INFO___", info.sharedUserId.toString());
                Log.d("____APP___INFO___", packageManager.getApplicationLabel(info.applicationInfo).toString());
            }
        }
        */

        /*
         *
         *       控件点按设置
         *
         * */

        // 后台未完成，目前的提示语

        /*
        ass.wake_pause();
        speech_speaker.doSpeech("后台未完成，我暂时是一台复读机，可叫我语音助手把我唤醒");
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                ass.wake_go_on();
            }
        }, 7000);*/

        speech_speaker.doSpeech("我是复读机,嘤嘤嘤");

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

        // TODO 编写残疾人的终端区分  ---- 最重要
        // TODO 点击按钮多次会出现错误！！！！！！！！！！

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

                //toast.show(HistoryActivity.this, band.download_result(), 1000);
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
     *       特殊情况处理
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // NLU释放资源
        NLUAPIService.getInstance().onDestroy();

        // soter验证释放资源
        SoterWrapperApi.release();
    }

    private void restart()
    {
        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(getBaseContext().getPackageName());
        assert i != null;
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    private void set_window()
    {
        // 非盲人模式进行悬浮窗权限设置
        globalstate gl = (globalstate) this.getApplication();
        if(!gl.user_mode.equals(constr_share.k_user_mode_Blind)) {
            startActivity(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())));
        }
    }

    public boolean isStartAccessibilityService(Context context, String name){
        AccessibilityManager am = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        assert am != null;
        List<AccessibilityServiceInfo> serviceInfos = am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_GENERIC);
        serviceInfos = am.getInstalledAccessibilityServiceList();

        for (AccessibilityServiceInfo info : serviceInfos) {
            String id = info.getId();
            Log.d("_ACCESS_Event__", "access_id: " + id);
            if (id.contains(name)) {
                return true;
            }
        }
        return false;
    }

}
