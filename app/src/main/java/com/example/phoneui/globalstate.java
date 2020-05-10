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
    public boolean first_blind = false;

    // 测试使用参数
    public boolean testMode = false;
    //public int test_times = 0;

    // 废参数
    public boolean startAssistant = true;

    // 启动
    @Override
    public void onCreate(){
        super.onCreate();
        Setting.setShowLog(true);

        // 讯飞接口初始化
        SpeechUtility.createUtility(this, "appid=5dd651ed");
    }

    // 参数初始化
    //private boolean global_state_init_finish = false;

    public void update_global_state(file_writer file_edit)
    {
        //global_state_init_finish = true;
        this.user_mode = file_edit.read(constr_share.user_mode, "null_");
        this.first_blind = file_edit.read(constr_share.first_use_blind, true);
    }

}
