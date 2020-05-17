package com.example.phoneui;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Instrumentation;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.alibaba.fastjson.JSONObject;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

public class executeMethod {
    private StringBuffer buffer;
    private StringBuffer who;
    private StringBuffer when;
    private StringBuffer where;
    private StringBuffer what;

    private Context context_;

    // 联系人列表
    private JSONObject contactList = new JSONObject();
    private boolean import_contact = false;

    // APP列表
    private List<PackageInfo> app_list;
    private JSONObject quick_appinfo = new JSONObject();
    private boolean get_app_list = false;

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
        //str = str.substring(0, str.indexOf("_basic_function"));
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
        // 没读过联系人就读取一次到list里面
        if(!import_contact)
        {
            readContacts();
        }

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
                    String phone_number = contactList.getString(name);

                    //权限检查
                    if (ContextCompat.checkSelfPermission(context_,
                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        //申请权限
                        ActivityCompat.requestPermissions((Activity) context_,
                                new String[]{Manifest.permission.CALL_PHONE}, 1);
                    }

                    //创建打电话的意图
                    Intent intent_name = new Intent();
                    //设置拨打电话的动作
                    intent_name.setAction(Intent.ACTION_CALL);
                    //设置拨打电话的号码
                    intent_name.setData(Uri.parse("tel:" + phone_number));
                    //开启打电话的意图
                    context_.startActivity(intent_name);

                    //Intent intent0 = new Intent("android.intent.action.HISTORY");
                    //context_.startActivity(intent0);

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

        scene = constr_share.scene_official_call_phone;
    }

    // 听音乐
    private void openMusic()
    {

        scene = constr_share.scene_basic_open_music;
    }

    // 打开APP
    private void openAPP()
    {
        if(parament != null)
        {
            if(parament.containsKey("app_name")) {
                String app_name = parament.getString("app_name");
                // String package_name = quick_appinfo.getString(app_name);
                Log.d("___OPEN___APP___", "openAPP: " + app_name);
                ActivityManager activityManager = (ActivityManager) context_.getSystemService(ACTIVITY_SERVICE);
                Intent intent = context_.getPackageManager().getLaunchIntentForPackage(app_name);
                if (intent != null) {
                    //intent.putExtra("type", "110");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context_.startActivity(intent);
                }
                else
                {
                    Log.d("___OPEN___APP___", "openAPP: fail null intent");
                }
            }
        }

        scene = constr_share.scene_basic_open_app;
    }

    // 发短信
    private void sentMsg()
    {

        // 权限检查
        if (ContextCompat.checkSelfPermission(context_,
                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            //申请权限
            ActivityCompat.requestPermissions((Activity) context_,
                    new String[]{Manifest.permission.SEND_SMS}, 1);
        }

        // 编辑短信
        SmsManager sms = SmsManager.getDefault();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context_, 0, new Intent(), 0);

            //获取联系人
            String contactor = "我";
            String phoneNumber = "null";
            if(parament.containsKey("num"))
            {
                // 有号码直接复制号码
                phoneNumber = parament.getString("num");
            }
            else {
                // 确认有没有联系人，没有直接返回null
                if(parament.containsKey("contactor")) {
                    contactor = parament.getString("contactor");

                    // 有联系人信息找联系人
                    if (!import_contact) {
                        readContacts();
                    }

                    if (contactList.containsKey(contactor)) {
                        phoneNumber = contactList.getString(contactor);
                    }
                }
            }

            // 获取短信内容
            String MsgStr = "";
            if(parament.containsKey("msgStr"))
            {
                MsgStr = parament.getString("msgStr");
            }

        if(MsgStr.equals("") || phoneNumber.equals("null")) {
            Log.d("_CLASSIFY_MSM_", "NUM: " + phoneNumber + " STR: " + MsgStr);
        }
        else
        {
            sms.sendTextMessage(phoneNumber, null, MsgStr, pendingIntent, null);
            Log.d("_CLASSIFY_MSM_", "NUM: " + phoneNumber + " STR: " + MsgStr);
        }

        scene = constr_share.scene_basic_Message;
    }

    // 网络查阅资料
    private void search()
    {
        //Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        String search_content = "";
        if(parament.containsKey("search_content")) {
            search_content = parament.getString("search_content");
        }
        Log.d("_CLASSIFY_SEARCH_BAI_DU", "content: " + search_content);
        String website = constr_share.search_baidu;
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(website + search_content);
        intent.setData(content_url);
        intent.setClassName("com.android.browser","com.android.browser.BrowserActivity");
        context_.startActivity(intent);

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
    private void readContacts() {

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
                    contactList.put(displayName, number);
                }
            }
            import_contact = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

}
