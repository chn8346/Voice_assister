package com.example.phoneui;
import android.app.Application;

import com.iflytek.cloud.Setting;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
// 全局变量类

public class globalstate extends Application{

    // 设置选项字段
    int SetPos = 4; // 四个菜单分页的当前位置
    public boolean globalWord = false;
    public boolean forceSense = false;
    public boolean soundAndVibration = false;
    public int theme_ = 0;
    public int button_style = 0;

    // 屏幕参数
    public int heightSize = 1960;
    public int widthSize = 1080;

    // 状态参数
    public String user_mode = "normal";

    // 测试使用参数
    public boolean testMode = false;

    // 废参数
    public boolean startAssistant = true;

    @Override
    public void onCreate(){
        super.onCreate();
        Setting.setShowLog(true);
    }

}
