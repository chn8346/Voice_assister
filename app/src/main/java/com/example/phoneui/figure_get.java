package com.example.phoneui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

// 头像采集
public class figure_get{
    private Uri imageUri;
    private static final int TAKE_PHOTO = 1;

    void get(Context context)
    {
        file_writer file_edit = new file_writer(context, "figure_1");


    }
}
