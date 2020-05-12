package com.example.phoneui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

//加载Frame类
import com.huawei.hiai.vision.visionkit.common.Frame;
//加载人脸比对类
import com.huawei.hiai.vision.face.FaceComparator;
//加载人脸比对结果类
import com.huawei.hiai.vision.visionkit.face.FaceCompareResult;
//加载连接服务的静态类
import com.huawei.hiai.vision.common.VisionBase;
//加载连接服务的回调函数
import com.huawei.hiai.vision.common.ConnectionCallback;

// 人脸识别的BitMap库
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.widget.ImageView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

public class sound_identify{
    private Context context_;
    private globalstate gl;
    private file_writer file_edit;

    protected sound_identify(Context context, globalstate gl_, file_writer file_edit_) {

        // 初始化变量
        context_ = context;
        this.gl = gl_;
        this.file_edit = file_edit_;


        // 人脸识别初始化
        VisionBase.init(context_, new ConnectionCallback() {
            @Override
            public void onServiceConnect() {
                Log.i("__HW_VISION__", "onServiceConnect: success");
            }
            @Override
            public void onServiceDisconnect() {
                Log.i("__WH_VISION__", "onServiceConnect: connect finish");
            }
        });
    }

    public boolean user_identify(Bitmap bitmap)
    {
        return false;
    }

    public Bitmap camera()
    {
        return null;
    }

    public boolean user_graph_identify(Bitmap bitmap)
    {
        FaceComparator faceComparator = new FaceComparator(context_);

        Frame frame1 = new Frame();
        Frame frame2 = new Frame();

        // TODO 未完工

        frame1.setBitmap(bitmap);
        frame2.setBitmap(bitmap);

        JSONObject jsonObject = faceComparator.faceCompare(frame1, frame2, null);
        FaceCompareResult result = faceComparator.convertResult(jsonObject);
        float score = result.getSocre();
        boolean isSamePerson = result.isSamePerson();


        return false;
    }


}
