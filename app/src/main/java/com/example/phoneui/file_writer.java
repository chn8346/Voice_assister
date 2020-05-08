package com.example.phoneui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class file_writer {
    private Context context_;
    private String fileName = "VoiceAssistantConfigData";

    private SharedPreferences reader = context_.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    private SharedPreferences.Editor writer = reader.edit();


    public void change_file(String filename)
    {
        fileName = filename;
        reader = context_.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        writer = reader.edit();
        writer.apply();
    }

    public void write(String key, String value)
    {
        writer.putString(key, value);
        writer.apply();
    }

    public void write(String key, int value)
    {
        writer.putInt(key, value);
        writer.apply();
    }

    public void write(String key, boolean value)
    {
        writer.putBoolean(key, value);
        writer.apply();
    }

    public String read(String key, String v)
    {
        return reader.getString(key, "");
    }

    public int read(String key, int v)
    {
        return reader.getInt(key, 0);
    }

    public boolean read(String key, boolean v)
    {
        return reader.getBoolean(key, false);
    }

}
