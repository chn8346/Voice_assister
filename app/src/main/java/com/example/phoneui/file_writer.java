package com.example.phoneui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class file_writer {
    private Context context_;
    private String fileName = "vasConfigData";

    private SharedPreferences reader;
    private SharedPreferences.Editor writer;

    private boolean init_ok = true;

    public file_writer(Context context)
    {
        context_ = context;
        change_file(fileName);
    }

    public void change_file(String filename)
    {
        fileName = filename;
        reader = context_.getSharedPreferences(fileName, Context.MODE_PRIVATE);

        if(reader == null)
        {
            init_ok = false;
        }
    }

    public void write(String key, String value)
    {
        writer = reader.edit();
        writer.apply();
        if(!init_ok)
        {
            return;
        }
        writer.putString(key, value);
        writer.apply();
    }

    public void write(String key, int value)
    {
        writer = reader.edit();
        writer.apply();
        if(!init_ok)
        {
            return;
        }
        writer.putInt(key, value);
        writer.apply();
    }

    public void write(String key, boolean value)
    {
        writer = reader.edit();
        writer.apply();
        if(!init_ok)
        {
            return;
        }
        writer.putBoolean(key, value);
        writer.apply();
    }

    public String read(String key, String v)
    {
        if(!init_ok)
        {
            return "";
        }
        return reader.getString(key, v);
    }

    public int read(String key, int v)
    {
        if(!init_ok)
        {
            return -1;
        }
        return reader.getInt(key, v);
    }

    public boolean read(String key, boolean v)
    {
        if(!init_ok)
        {
            return false;
        }
        return reader.getBoolean(key, v);
    }
}
