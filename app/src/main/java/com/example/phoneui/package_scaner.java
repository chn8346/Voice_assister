package com.example.phoneui;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;
import com.alibaba.fastjson.JSONObject;

public class package_scaner {
    private boolean get_info = false;
    private Context context_;
    private PackageManager packageManager;
    private List<PackageInfo> app_list;
    private JSONObject quick_info = new com.alibaba.fastjson.JSONObject();
    private JSONObject quick_rank = new JSONObject();
    private JSONObject quick_rank_back = new JSONObject();
    private globalstate gl;

    public package_scaner(Context context)
    {
        get_info = true;
        context_ = context;
        gl = (globalstate) context_.getApplicationContext();
        flash();
    }

    public void flash()
    {
        packageManager = context_.getPackageManager();
        app_list = packageManager.getInstalledPackages(0);

        quick_rank.clear();
        quick_rank_back.clear();
        quick_info.clear();
        gl.package_name_finder.clear();
        gl.app_name_finder.clear();

        int len = app_list.size();
        for(int i = 0; i < len; i++)
        {
            PackageInfo info = app_list.get(i);
            String app_name = packageManager.getApplicationLabel(info.applicationInfo).toString();
            String package_name = info.packageName;
            quick_info.put(app_name, package_name);
            quick_rank_back.put(package_name, app_name);
            quick_rank.put(package_name, i);

            gl.app_name_finder.put(package_name, app_name);
            gl.package_name_finder.put(app_name, package_name);
        }
    }

    public JSONObject returnPackageJSON()
    {
        if(get_info)
        {
            return quick_info;
        }
        return null;
    }

    public JSONObject returnRankJSON()
    {
        if(get_info)
        {
            return quick_rank;
        }
        return null;
    }

    public String find_package_name(String app_name)
    {
        if(get_info)
        {
            return quick_info.getString(app_name);
        }
        return null;
    }

    public PackageInfo find_info(String package_name)
    {
        if(get_info)
        {
            return app_list.get(quick_rank.getInteger(package_name));
        }
        return null;
    }

    public String find_name(String package_name)
    {
        if(get_info)
        {
            return quick_rank_back.getString(package_name);
        }
        return null;
    }

}
