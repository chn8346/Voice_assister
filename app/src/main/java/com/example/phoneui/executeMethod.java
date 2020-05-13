package com.example.phoneui;

import android.util.Log;

public class executeMethod {
    private StringBuffer buffer;
    private StringBuffer who;
    private StringBuffer when;
    private StringBuffer where;
    private StringBuffer what;

    // 场景区分
    private String scene;

    // 参数
    private String parament = "null";

    // 按照命令分类执行,返回助手工作的场景是什么（字符串）
    // 这个是完整的，第二个参数可以省略，下面有一个重载
    public String execute(String Classify_order, String para)
    {
        parament = para;

        if(Classify_order.contains("intentions_")){
            intention_execute(Classify_order);
            scene = constr_share.scene_official_setting;
            return scene;
        }
        else if(Classify_order.contains("_basic_function")){
            basic_function(Classify_order);
            return scene;
        }
        else{
            // 不知道就返回UNKNOWN
            return constr_share.order_UNKNOWN;
        }
    }

    // 按照命令分类执行,返回助手工作的场景是什么（字符串)
    // 这个是少了参量的重载函数
    public String execute(String Classify_order)
    {
        return execute(Classify_order, "null");
    }

    // 构造函数
    public executeMethod()
    {
        // 初始变量
        buffer = new StringBuffer();
        who = new StringBuffer();
        when = new StringBuffer();
        where = new StringBuffer();
        what = new StringBuffer();
        scene = "null";
    }

    // 基础的几个命令集成
    private void basic_function(String str)
    {
        str = str.substring(0, str.indexOf("_basic_function"));
        switch (str)
        {
            case constr_share.order_basic_call_phone:
                call_phone();
                break;
            case constr_share.order_basic_open_music:
                openMusic();
                break;
            case constr_share.order_basic_open_app:
                openAPP();
                break;
            case constr_share.order_basic_Message:
                sentMsg();
                break;
            case constr_share.order_basic_search:
                search();
                break;
            case constr_share.order_basic_talk:
                talk();
                break;
        }
        // 什么都不知道的情况下返回UNKNOWN
        scene = constr_share.order_UNKNOWN;
    }

    // 打电话
    private void call_phone()
    {
        scene = constr_share.scene_basic_call_phone;
    }

    // 听音乐
    private void openMusic()
    {
        scene = constr_share.scene_basic_open_music;
    }

    // 打开APP
    private void openAPP()
    {
        scene = constr_share.scene_basic_open_app;
    }

    // 发短信
    private void sentMsg()
    {
        scene = constr_share.scene_basic_Message;
    }

    // 网络查阅资料
    private void search()
    {
        scene = constr_share.scene_basic_search;
    }


    // 聊天对话
    private String talk()
    {
        scene = constr_share.scene_basic_talk;
        return scene;
    }


    // 官方设置的执行
    private void intention_execute(String str)
    {
        str = str.split("intentions_")[1];
        switch (str)
        {
            case "open4G":
                Log.d("EXECUTE", "open4G");
                break;

            case "close4G":
                Log.d("EXECUTE", "close4G");
                break;

            case "openWlan":
                Log.d("EXECUTE", "openWlan");
                break;

            case "closeWlan":
                Log.d("EXECUTE", "closeWlan");
                break;

            case "setWlan":
                Log.d("EXECUTE", "setWlan");
                break;

            case "disconnectWlan":
                Log.d("EXECUTE", "disconnectWlan");
                break;

            case "openHotspot":
                Log.d("EXECUTE", "openHotspot");
                break;

            default:
                break;
        }
    }
}
