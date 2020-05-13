package com.example.phoneui;

import com.example.phoneui.Toast_;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;

/*
* 此界面已被废弃
* HistoryActivity为主界面
* */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init toast 封装的Toast
        final Toast_ toast = new Toast_();

        // get global state 全局变量
        final globalstate gl = (globalstate)this.getApplication();

        // pos adjust
        float ADV_height_property = (float) 0.732;
        float ADV_wight_property = (float) 0.325;
        Button button1 = (Button) findViewById(R.id.button1);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) button1.getLayoutParams();
        if(layoutParams != null)
        {
            layoutParams.topMargin = (int) (gl.heightSize * ADV_height_property);
            layoutParams.leftMargin = (int) (gl.widthSize * ADV_wight_property);
        }



        // init speech recognizer
        final String engineType = "cloud";
        final String resultType = "json";

        InitListener mInitListener = new InitListener(){
            @Override
            public void onInit(int code) {
                String TAG = HistoryActivity.class.getSimpleName();
                Log.d(TAG, "SpeechRecognizer init() code = " + code);
                if (code != ErrorCode.SUCCESS) {
                    toast.show(MainActivity.this,"初始化失败，错误码：" + code+",请点击网址https://www.xfyun.cn/document/error-code查询解决方案", 300);
                }
                else
                {
                    toast.show(MainActivity.this,"初始完成", 300);
                }
            }
        };


        final SpeechUtility su = SpeechUtility.getUtility();
        if(su == null)
            toast.show(MainActivity.this,"SpeechUtility 初始化失败", 300);

        final SpeechRecognizer mIat = SpeechRecognizer.createRecognizer(MainActivity.this, mInitListener);
        if(mIat == null)
            toast.show(MainActivity.this,"SpeechRecognizer 初始化失败", 300);



        Button back_button = (Button) findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
                //Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                //startActivity(intent);
                finish();
            }
        });


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // voice assistant
                String temp = "0";
                if(gl.startAssistant)
                    temp = "1";
                if(mIat == null)
                    temp = temp + " + Iat_0";
                if(su == null)
                    temp = temp + " + Uti_0";
                toast.show(MainActivity.this, temp, toast.short_time_len);



                if(gl.startAssistant)
                {
                    gl.startAssistant = false;
                    // analyse

                    /*
                    if(mIat.isListening()){
                        mIat.cancel();
                    }*/

                    //Toast.makeText(MainActivity.this, "正在分析命令", Toast.LENGTH_LONG).show();
                }
                else
                {
                    // get sound
                    toast.show(MainActivity.this, "请说出你的命令", toast.short_time_len);

                    SpeechRecognizer iat = SpeechRecognizer.getRecognizer();

                    //iat.setParameter(SpeechConstant.RESULT_TYPE, resultType);

                    /*
                    //设置语法ID和 SUBJECT 为空，以免因之前有语法调用而设置了此参数；或直接清空所有参数，具体可参考 DEMO 的示例。
                    mIat.setParameter( SpeechConstant.CLOUD_GRAMMAR, null );
                    mIat.setParameter( SpeechConstant.SUBJECT, null );
                    //设置返回结果格式，目前支持json,xml以及plain 三种格式，其中plain为纯听写文本内容
                    mIat.setParameter(SpeechConstant.RESULT_TYPE, resultType);
                    //此处engineType为“cloud”
                    mIat.setParameter( SpeechConstant.ENGINE_TYPE, engineType );
                    //设置语音输入语言，zh_cn为简体中文
                    mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
                    //设置结果返回语言
                    mIat.setParameter(SpeechConstant.ACCENT, "mandarin");
                    // 设置语音前端点:静音超时时间，单位ms，即用户多长时间不说话则当做超时处理
                    //取值范围{1000～10000}
                    mIat.setParameter(SpeechConstant.VAD_BOS, "4000");
                    //设置语音后端点:后端点静音检测时间，单位ms，即用户停止说话多长时间内即认为不再输入，
                    //自动停止录音，范围{0~10000}
                    mIat.setParameter(SpeechConstant.VAD_EOS, "1000");
                    //设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
                    mIat.setParameter(SpeechConstant.ASR_PTT,"1");
*/
                    RecognizerListener mRecognizerListener = new RecognizerListener() {

                        @Override
                        public void onBeginOfSpeech() {
                            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
                            //showTip("开始说话");
                        }

                        @Override
                        public void onError(SpeechError error) {
                            // Tips：
                            // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
                            toast.show(MainActivity.this, "未知错误", toast.short_time_len);
                            //showTip(error.getPlainDescription(true));

                        }

                        @Override
                        public void onEvent(int i, int i1, int i2, Bundle bundle) {

                        }

                        @Override
                        public void onEndOfSpeech() {
                            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
                            //showTip("结束说话");
                        }

                        @Override
                        public void onResult(RecognizerResult results, boolean isLast) {
                            //Log.d(TAG, results.getResultString());
                            //System.out.println(flg++);

                            if(results == null)
                            {
                                toast.show(MainActivity.this,"未解析到识别结果", toast.short_time_len);
                                //Toast.makeText(MainActivity.this, "未解析到识别结果", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            String res_str = results.getResultString();
                            toast.show(MainActivity.this,res_str, toast.short_time_len);
                            //Toast.makeText(MainActivity.this, res_str, Toast.LENGTH_SHORT).show();
                            if (isLast) {
                                // TODO 最后的结果
                                toast.show(MainActivity.this,"识别结束", toast.short_time_len);
                                //Toast.makeText(MainActivity.this, "识别结束", Toast.LENGTH_SHORT).show();
                                gl.startAssistant = false;
                            }
                        }

                        @Override
                        public void onVolumeChanged(int volume, byte[] data) {

                        }
                    };

                    //开始识别，并设置监听器
                    //mIat.startListening(mRecognizerListener);
                    gl.startAssistant = true;
                    // get
                }
            }
        });


        if(gl.startAssistant){
            button1.callOnClick();
            String temp = "0";
            if(gl.startAssistant)
                temp = "1";
            toast.show(MainActivity.this,temp, toast.short_time_len);
            //Toast.makeText(MainActivity.this, temp, Toast.LENGTH_LONG).show();
        }



        Button set = (Button) findViewById(R.id.setting);
        set.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.ADVSETTING");
                startActivity(intent);
            }
        });

    }

}
