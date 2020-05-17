package com.example.phoneui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;

import androidx.core.app.ActivityCompat;

import static android.content.Context.TELEPHONY_SERVICE;
import static com.huawei.hiai.pdk.utils.SystemUtil.getDeviceId;

public class premissionHelper {
    private Context context_;
    private int REQUEST_CODE_PRESSION_PHONE_STATE = 1;

    public premissionHelper(Context context) {
        context_ = context;
    }

    public void getPermission() {
        //版本号的判断
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {

        } else {
            //权限有三种状态（1、允许  2、提示  3、禁止）
            int permission = ActivityCompat.checkSelfPermission(context_.getApplicationContext(), Manifest.permission.READ_PHONE_STATE);

            // 如果是 2、提示  3、禁止
            if (permission != PackageManager.PERMISSION_GRANTED) {
                //如果设置中权限是禁止返回false;如果是提示返回true
                boolean is = ActivityCompat.shouldShowRequestPermissionRationale((Activity) context_,
                        Manifest.permission.READ_PHONE_STATE);

                if (is) {
                    ActivityCompat.requestPermissions((Activity) context_,
                            new String[]{Manifest.permission.READ_PHONE_STATE},
                            REQUEST_CODE_PRESSION_PHONE_STATE);
                } else {

                }

            } else {
                //1、允许

            }
        }
    }

}
