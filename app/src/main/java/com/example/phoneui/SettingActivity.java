package com.example.phoneui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SettingActivity extends AppCompatActivity {

    private boolean figure = false;
    public static final int TAKE_PHOTO = 1;
    private Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_1);

        figure = false;

        final Toast_ toast = new Toast_();

        // global state
        final globalstate gl = (globalstate) this.getApplication();
        gl.SetPos = 4;

        // pos adjust

        Button button1 = (Button) findViewById(R.id.settingbtn_1);
        Button button2 = (Button) findViewById(R.id.settingbtn_2);
        Button button3 = (Button) findViewById(R.id.settingbtn_3);
        Button button4 = (Button) findViewById(R.id.settingbtn_4);
        Button button5 = (Button) findViewById(R.id.settingbtn_5);
        Button button6 = (Button) findViewById(R.id.settingbtn_6);
        Button button7 = (Button) findViewById(R.id.settingbtn_7);
        TextView title = (TextView) findViewById(R.id.set_title);
        TextView abouts = (TextView) findViewById(R.id.abouts);

        // 人像采集的UI模组
        final ImageView figure_background = (ImageView) findViewById(R.id.figure_get_background);
        final ImageView figure_img = (ImageView) findViewById(R.id.figure_get_img);
        final TextView figure_tip = (TextView) findViewById(R.id.figure_get_tip);
        final Button button_figure = (Button) findViewById(R.id.set_figure_get);
        final TextView next = (TextView) findViewById(R.id.figure_get_next);
        final ImageView img_finger_print = (ImageView) findViewById(R.id.img_finger_print);

        /*
        *
        *    布局文件
        *
        * */

        float ADV_height_property = (float) 0.2;

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) title.getLayoutParams();
        if(layoutParams != null)
        {
            layoutParams.topMargin = (int) (gl.heightSize * ADV_height_property);
        }
        title.setText("高级设置");


        ADV_height_property = (float) (ADV_height_property + 0.2);
        float size_up = (float) 0.8;
        layoutParams = (RelativeLayout.LayoutParams) button5.getLayoutParams();
        if(layoutParams != null)
        {
            layoutParams.width = (int) (gl.widthSize*size_up);
            layoutParams.height = (int) (gl.widthSize * size_up * 0.2);
            layoutParams.topMargin = (int) (gl.heightSize * ADV_height_property);
        }


        ADV_height_property = (float) (ADV_height_property + 0.03);
        layoutParams = (RelativeLayout.LayoutParams) button6.getLayoutParams();
        if(layoutParams != null)
        {
            layoutParams.width = (int) (gl.widthSize*size_up);
            layoutParams.height = (int) (gl.widthSize * size_up * 0.2);
            layoutParams.topMargin = (int) (gl.heightSize * ADV_height_property + layoutParams.height);

        }

        ADV_height_property = (float) (ADV_height_property + 0.03);
        layoutParams = (RelativeLayout.LayoutParams) button_figure.getLayoutParams();
        if(layoutParams != null)
        {
            layoutParams.width = (int) (gl.widthSize*size_up);
            layoutParams.height = (int) (gl.widthSize * size_up * 0.2);
            layoutParams.topMargin = (int) (gl.heightSize * ADV_height_property + layoutParams.height * 2);

        }


        float mh = (float) 0.83;
        float mw = (float) 0.07;
        float inw = (float) (1 - mw * 2)/4;
        float r = (float) 0.1;

        layoutParams = (RelativeLayout.LayoutParams) button1.getLayoutParams();
        if(layoutParams != null)
        {
            layoutParams.height = (int) (gl.heightSize * r);
            layoutParams.width = (int) (gl.heightSize * r);
            layoutParams.topMargin = (int) (gl.heightSize * mh);
            layoutParams.leftMargin = (int) (gl.widthSize * mw);
        }

        layoutParams = (RelativeLayout.LayoutParams) button2.getLayoutParams();
        if(layoutParams != null)
        {
            layoutParams.height = (int) (gl.heightSize * r);
            layoutParams.width = (int) (gl.heightSize * r);
            layoutParams.topMargin = (int) (gl.heightSize * (mh + 0));
            layoutParams.leftMargin = (int) (gl.widthSize * (mw + inw * 1));
        }

        layoutParams = (RelativeLayout.LayoutParams) button3.getLayoutParams();
        if(layoutParams != null)
        {
            layoutParams.height = (int) (gl.heightSize * r);
            layoutParams.width = (int) (gl.heightSize * r);
            layoutParams.topMargin = (int) (gl.heightSize * (mh + 0));
            layoutParams.leftMargin = (int) (gl.widthSize * (mw + inw * 2));
        }

        layoutParams = (RelativeLayout.LayoutParams) button4.getLayoutParams();
        if(layoutParams != null)
        {
            layoutParams.height = (int) (gl.heightSize * r);
            layoutParams.width = (int) (gl.heightSize * r);
            layoutParams.topMargin = (int) (gl.heightSize * (mh + 0));
            layoutParams.leftMargin = (int) (gl.widthSize * (mw + inw * 3));
        }

        float sizex = (float) 0.06;
        float sizey = (float) 0.1;

        layoutParams = (RelativeLayout.LayoutParams) abouts.getLayoutParams();
        if(layoutParams != null)
        {
            layoutParams.topMargin = (int) (gl.heightSize * sizex * 0.35);
            layoutParams.leftMargin = (int) (gl.widthSize * 0.8);
            //layoutParams.width = (int) (gl.widthSize * 0.8);
        }


        layoutParams = (RelativeLayout.LayoutParams) button7.getLayoutParams();
        if(layoutParams != null)
        {
            layoutParams.height = (int) (gl.heightSize * sizex);
            layoutParams.width = (int) (gl.heightSize * sizey);
            layoutParams.topMargin = (int) (gl.heightSize * sizex * 0.35);
            layoutParams.leftMargin = (int) (gl.heightSize * sizex * 0.25);
        }


        // 人像采集的布局
        layoutParams = (RelativeLayout.LayoutParams) figure_background.getLayoutParams();
        if(layoutParams != null)
        { ;}

        layoutParams = (RelativeLayout.LayoutParams) figure_img.getLayoutParams();
        if(layoutParams != null)
        {
            layoutParams.height = (int) (0.4 * gl.heightSize);
            layoutParams.width = (int) (0.55 * gl.widthSize);
            layoutParams.topMargin = (int) (0.22*gl.heightSize);
        }

        layoutParams = (RelativeLayout.LayoutParams) figure_tip.getLayoutParams();
        if(layoutParams != null)
        {
            layoutParams.topMargin = (int) (0.5*gl.heightSize);
        }

        layoutParams = (RelativeLayout.LayoutParams) next.getLayoutParams();
        if(layoutParams != null)
        {
            layoutParams.topMargin = (int) (0.65*gl.heightSize);
            next.setTextSize((float) (30 * (gl.widthSize / 1080.0)));
        }

        // 人像采集的图片暂时隐藏
        figure_background.setVisibility(View.GONE);
        figure_img.setVisibility(View.GONE);
        figure_tip.setVisibility(View.GONE);
        next.setVisibility(View.GONE);
        img_finger_print.setVisibility(View.GONE);

        //

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!figure) {
                Intent intent = new Intent("android.intent.action.SYNCSETTING");
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                }
            }
        });



        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!figure) {
                Intent intent = new Intent("android.intent.action.APPEARSETTING");
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                }
            }
        });


        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!figure) {
                    Intent intent = new Intent("android.intent.action.SOUNDSETTING");
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                }
            }
        });


        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!figure) {
                    // thoast
                    toast.show(SettingActivity.this, "已进入高级设置", toast.short_time_len);
                    //Toast.makeText(SettingActivity.this, "已进入高级设置", Toast.LENGTH_SHORT).show();
                }
            }
        });


        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!figure) {
                    if (gl.globalWord) {
                        toast.show(SettingActivity.this, "前后文识别已关闭", toast.short_time_len);
                        //Toast.makeText(SettingActivity.this, "前后文识别已关闭", Toast.LENGTH_SHORT).show();
                    } else {
                        toast.show(SettingActivity.this, "前后文识别已开启", toast.short_time_len);
                        //Toast.makeText(SettingActivity.this, "前后文识别已开启", Toast.LENGTH_SHORT).show();
                    }
                    // -------
                    gl.globalWord = !gl.globalWord;
                }
            }
        });


        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!figure) {
                    if (gl.forceSense) {
                        toast.show(SettingActivity.this, "压感识别已关闭，请确认有压感传感器", toast.short_time_len);
                        //Toast.makeText(SettingActivity.this, "压感识别已关闭，请确认有压感传感器", Toast.LENGTH_SHORT).show();
                    } else {
                        toast.show(SettingActivity.this, "压感识别已开启，请确认有压感传感器", toast.short_time_len);
                        //Toast.makeText(SettingActivity.this, "压感识别已开启，请确认有压感传感器", Toast.LENGTH_SHORT).show();
                    }
                    gl.forceSense = !gl.forceSense;
                }

                //测试截图功能
                /*
                v.setDrawingCacheEnabled(true);
                v.buildDrawingCache(true);

                Bitmap b = Bitmap.createBitmap(v.getDrawingCache());
                v.setDrawingCacheEnabled(false); // clear drawing cache
                img.setImageBitmap(b);
                img.setVisibility(View.VISIBLE);*/
            }
        });


        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!figure) {
                    finish();
                }
            }
        });


        abouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!figure) {
                    Intent intent = new Intent("android.intent.action.ABOUT");
                    startActivity(intent);
                }
            }
        });

        button_figure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                figure = true;

                figure_background.setVisibility(View.VISIBLE);
                figure_img.setVisibility(View.VISIBLE);
                figure_tip.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
                button_figure.setVisibility(View.GONE);

                next.setText("下一步");
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) next.getLayoutParams();
                if(layoutParams != null)
                {
                    layoutParams.topMargin = (int) (0.65*gl.heightSize);
                    next.setTextSize((float) (30 * (gl.widthSize / 1080.0)));
                }

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(next.getText().equals("下一步")) {
                    figure_tip.setVisibility(View.GONE);
                    figure_img.setVisibility(View.GONE);
                    img_finger_print.setVisibility(View.VISIBLE);

                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) img_finger_print.getLayoutParams();
                    if (layoutParams != null) {
                        layoutParams.width = (int) (0.5 * gl.widthSize);
                        layoutParams.width = (int) (0.5 * gl.widthSize);
                        layoutParams.topMargin = (int) (0.3 * gl.heightSize);
                    }

                    next.setText("指纹识别\n点此退出");
                    layoutParams = (RelativeLayout.LayoutParams) next.getLayoutParams();
                    if (layoutParams != null) {
                        layoutParams.topMargin = (int) (layoutParams.topMargin - 0.1 * gl.heightSize);
                    }
                }
                else
                {
                    img_finger_print.setVisibility(View.GONE);
                    figure_background.setVisibility(View.GONE);
                    figure_tip.setVisibility(View.GONE);
                    next.setVisibility(View.GONE);

                    button_figure.setVisibility(View.VISIBLE);

                    next.setText("下一步");

                    figure = false;
                }

            }
        });

        figure_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 拍照
                figure_tip.callOnClick();
            }
        });

        figure_tip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 拍照
                File outputImage = new File(SettingActivity.this.getExternalCacheDir(),
                        "output_image.jpg");
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT >= 24) {
                    imageUri = FileProvider.getUriForFile(SettingActivity.this,
                            "com.example.cameraalbumtest.fileprovider", outputImage);
                } else {
                    imageUri = Uri.fromFile(outputImage);
                }

                // 启动相机程序,
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                SettingActivity.this.startActivityForResult(intent, TAKE_PHOTO);
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(figure)
        {
            figure = false;

            ImageView figure_background = (ImageView) findViewById(R.id.figure_get_background);
            ImageView figure_img = (ImageView) findViewById(R.id.figure_get_img);
            TextView figure_tip = (TextView) findViewById(R.id.figure_get_tip);
            Button button_figure = (Button) findViewById(R.id.set_figure_get);
            ImageView img_finger_print = (ImageView) findViewById(R.id.img_finger_print);
            TextView next = (TextView) findViewById(R.id.figure_get_next);

            figure_background.setVisibility(View.GONE);
            figure_img.setVisibility(View.GONE);
            figure_tip.setVisibility(View.GONE);
            img_finger_print.setVisibility(View.GONE);
            next.setVisibility(View.GONE);


            button_figure.setVisibility(View.VISIBLE);

        }
        else
        {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ImageView picture = this.findViewById(R.id.figure_get_img);

        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        // 将拍摄的照片显示出来
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        picture.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }

}
