package com.example.phoneui;

public class constr_share {

    // 持久保存的字段
    public static final String user_mode = "USER_MODE";
    public static final String first_use = "FIRST_USE";
    public static final String first_use_blind = "BLIND_FIRST_USE";
    public static final String switch_context_realize = "S_CONTEXT_REA";
    public static final String huawei_nlu_api_use_time = "HW_NLU_API_TIMES";

    // 用户模式的几个选项
    public static final String k_user_mode_normal = "NORMAL";
    public static final String k_user_mode_Blind = "BLIND";
    public static final String k_user_mode_deaf = "DEAF";
    public static final String k_user_mode_mute = "CAN_NOT_SPEAK";
    public static final String k_user_mode_deaf_no_mute = "HALF_DEAF";

    // 搜索的网页命令
    public static final String search_baidu = "https://www.baidu.com/s?ie=UTF-8&wd=";

    // 命令分类
        // 特殊情况反馈
    public static final String order_UNKNOWN = "UNKNOWN"; // 未知命令
        //基础命令
    public static final String order_basic_open_music = "open_music";
    public static final String order_basic_open_app = "open_app";
    public static final String order_basic_Message = "msg";
    public static final String order_basic_search = "search";
    public static final String order_basic_talk = "talk";
        //官方设置
    public static final String order_official_open4G = "open4G";
    public static final String order_basic_call_phone = "call";
    public static final String order_official_close4G = "close4G";
    public static final String order_official_open_wlan = "openWlan";
    public static final String order_official_close_wlan = "closeWlan";
    public static final String order_official_set_wlan = "setWlan";
    public static final String order_official_disconnect_wlan = "disconnectWlan";
    public static final String order_official_openHotspot = "openHotspot";

    // 命令场景
        //基础场景
    public static final String scene_basic_open_music = "scene_open_music";
    public static final String scene_basic_open_app = "scene_open_app";
    public static final String scene_basic_Message = "scene_msg";
    public static final String scene_basic_search = "scene_search";
    public static final String scene_basic_talk = "scene_talk";
        //官方设置场景
    public static final String scene_official_setting = "scene_setting_official";
    public static final String scene_official_call_phone = "scene_call_phone";


    // 信息分类的TAG
    public static final String tag_basic = "_basic_function";

    public constr_share() {

    }
}
