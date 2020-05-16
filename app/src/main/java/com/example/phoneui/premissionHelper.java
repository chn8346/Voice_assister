package com.example.phoneui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;

public class premissionHelper {
    private Context context_;

    public premissionHelper(Context context)
    {
        context_ = context;
    }

    public void getPermission()
    {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
        {
            int permission = ActivityCompat.checkSelfPermission(context_.getApplicationContext(), Manifest.permission.READ_PHONE_STATE);
            if (permission != PackageManager.PERMISSION_GRANTED)
            {
                boolean is=ActivityCompat.shouldShowRequestPermissionRationale((Activity) context_,Manifest.permission.READ_PHONE_STATE);

            }
        }
    }

}
