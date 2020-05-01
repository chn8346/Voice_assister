package com.example.phoneui;
import android.app.Application;

import com.iflytek.cloud.Setting;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
// 全局变量类

public class globalstate extends Application{
    public boolean globalWord = false;
    public boolean forceSense = false;
    public boolean soundAndVibration = false;
    public int theme_ = 0;
    public int button_style = 0;

    public int heightSize = 1960;
    public int widthSize = 1080;

    public boolean startAssistant = false;

    int SetPos = 4;

    @Override
    public void onCreate(){
        super.onCreate();
        Setting.setShowLog(true);
        SpeechUtility.createUtility(this, "appid=5dd651ed");
    }

}
