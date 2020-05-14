package com.example.phoneui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class executeMethod {
    private StringBuffer buffer;
    private StringBuffer who;
    private StringBuffer when;
    private StringBuffer where;
    private StringBuffer what;

    private Context context_;

    // 联系人列表
    List<String> contactsList = new ArrayList<>();

    // 场景记录
    private String scene;

    // 参数
    private JSONObject parament;

    // 按照命令分类执行,返回助手工作的场景是什么（字符串）
    // 这个是完整的，第二个参数可以省略，下面有一个重载
    public String execute(String Classify_order, JSONObject para)
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
        return execute(Classify_order, null);
    }

    // 基础的几个命令集成
    private void basic_function(String str)
    {
        str = str.substring(0, str.indexOf("_basic_function"));
        switch (str)
        {
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

    // 官方设置的执行
    private void intention_execute(String str)
    {
        str = str.split("intentions_")[1];
        switch (str)
        {
            case constr_share.order_basic_call_phone:
                phone_call();
                scene = constr_share.scene_official_call_phone;
                Log.d("EXECUTE", "CALL");
                break;

            case constr_share.order_official_open4G:
                scene = constr_share.scene_official_setting;
                Log.d("EXECUTE", "open4G");
                break;

            case constr_share.order_official_close4G:
                scene = constr_share.scene_official_setting;
                Log.d("EXECUTE", "close4G");
                break;

            case constr_share.order_official_open_wlan:
                scene = constr_share.scene_official_setting;
                Log.d("EXECUTE", "openWlan");
                break;

            case constr_share.order_official_close_wlan:
                scene = constr_share.scene_official_setting;
                Log.d("EXECUTE", "closeWlan");
                break;

            case constr_share.order_official_set_wlan:
                scene = constr_share.scene_official_setting;
                Log.d("EXECUTE", "setWlan");
                break;

            case constr_share.order_official_disconnect_wlan:
                scene = constr_share.scene_official_setting;
                Log.d("EXECUTE", "disconnectWlan");
                break;

            case constr_share.order_official_openHotspot:
                scene = constr_share.scene_official_setting;
                Log.d("EXECUTE", "openHotspot");
                break;

            default:
                scene = constr_share.scene_official_setting;
                Log.d("EXECUTE", "NO_ACTION");
                break;
        }
    }

    private void phone_call()
    {
        if(parament == null)
        {
            // todo --> cope later
            Log.d("____CALL____", "_NO_JSON_INFO");
            return;
        }
        else
        {
            int type = 0;
            Log.d("____CALL____", "_JSON_INFO_DETECTED");
            // test call type
            if(parament.getString("name") == null)
            {
                type = 1;
            }

            Log.d("____CALL____", "TYPE : " + type);

            switch (type)
            {
                case 0:
                    Log.d("____CALL____", "CALL_IN_BOOK");

                    String name = parament.getString("name");
                    String name_type = parament.getString("nameType");
                    break;

                case 1:

                    Log.d("____CALL____", "NUMBER_CALL_ " + parament.getString("number"));

                    //权限检查
                    if (ContextCompat.checkSelfPermission(context_,
                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        //申请权限
                        ActivityCompat.requestPermissions((Activity) context_,
                                new String[]{Manifest.permission.CALL_PHONE}, 1);
                    }

                    // 号码
                    String number = parament.getString("number");
                    //创建打电话的意图
                    Intent intent = new Intent();
                    //设置拨打电话的动作
                    intent.setAction(Intent.ACTION_CALL);
                    //设置拨打电话的号码
                    intent.setData(Uri.parse("tel:" + number));
                    //开启打电话的意图
                    context_.startActivity(intent);
                    //Intent intent0 = new Intent("android.intent.action.HISTORY");
                    //context_.startActivity(intent0);
                    break;
            }

        }
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
    private void talk()
    {
        scene = constr_share.scene_basic_talk;
    }

    // 构造函数
    public executeMethod(Context context)
    {
        // 初始变量
        context_ = context;
        buffer = new StringBuffer();
        who = new StringBuffer();
        when = new StringBuffer();
        where = new StringBuffer();
        what = new StringBuffer();
        scene = "null";
    }

    // 读取联系人
    private String readContacts(String name) {

        Cursor cursor = null;

        try {
            // 查询联系人数据
            cursor = context_.getContentResolver().query(ContactsContract.CommonDataKinds.
                    Phone.CONTENT_URI, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    // 获取联系人姓名
                    String displayName = cursor.getString(cursor.getColumnIndex
                            (ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    // 获取联系人手机号
                    String number = cursor.getString(cursor.getColumnIndex
                            (ContactsContract.CommonDataKinds.Phone.NUMBER));
                    contactsList.add(displayName + "\n" + number);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return "1";
    }
}
