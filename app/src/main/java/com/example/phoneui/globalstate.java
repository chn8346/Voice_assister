package com.example.phoneui;
import android.app.Application;

import com.huawei.hiai.nlu.sdk.NLUAPIService;
import com.huawei.hiai.nlu.sdk.OnResultListener;
import com.iflytek.cloud.Setting;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
// 全局变量类

public class globalstate extends Application{

    // ***FILE*** X  --> 标记了是否需要文件持久化存储 X包括Y和N 表示是否已经进行了文件写入

    // 设置选项字段
    int SetPos = 4; // 四个菜单分页的当前位置
    public boolean globalWord = false; // ***FILE*** N
    public boolean forceSense = false; // ***FILE*** N
    public boolean soundAndVibration = false; // ***FILE*** N
    public int theme_ = 0; // ***FILE*** N
    public int button_style = 0; // ***FILE*** N

    // 屏幕参数
    public int heightSize = 1960;
    public int widthSize = 1080;

    // 状态参数
    public String user_mode = "normal"; // ***FILE*** Y
    public boolean first_blind = false; // ***FILE*** Y

    // 这个int变量不是使用次数，是初始化模型的次数
    public int HW_nlu_use_time = 0;  // ***FILE*** Y
    public boolean Hw_nlu_start = false;

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
        SpeechUtility.createUtility(this, "appid=5eb4d530");
    }


    // 参数初始化
        //private boolean global_state_init_finish = false;


        public void update_global_state (file_writer file_edit)
        {
            //global_state_init_finish = true;
            this.user_mode = file_edit.read(constr_share.user_mode, "null_");
            this.first_blind = file_edit.read(constr_share.first_use_blind, true);
            this.HW_nlu_use_time = file_edit.read(constr_share.huawei_nlu_api_use_time, 0);
        }

        public void re_write_global_state_file(file_writer file_edit)
        {
            file_edit.write(constr_share.user_mode, this.user_mode);
            file_edit.write(constr_share.first_use_blind, this.first_blind);
            file_edit.write(constr_share.huawei_nlu_api_use_time, this.HW_nlu_use_time);
        }

    }
