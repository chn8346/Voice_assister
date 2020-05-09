package com.example.phoneui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class init2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init2);
    }

    // 总配置方法
    private void init_all(String user_classify, globalstate gl, file_writer file_edit)
    {
        init_file(file_edit);
        init_user(user_classify, gl, file_edit);
    }

    // 配置文件初始化
    private void init_file(file_writer file_edit)
    {

    }

    // 根据反馈进行欢迎
    private void init_user(String user_classify, globalstate gl, file_writer file_edit)
    {
        switch (user_classify)
        {
            case "normal":
                file_edit.write(constr_share.user_mode, constr_share.k_user_mode_normal);
                gl.user_mode = constr_share.k_user_mode_normal;
                break;

            case "deaf":
                file_edit.write(constr_share.user_mode, constr_share.k_user_mode_deaf);
                gl.user_mode = constr_share.k_user_mode_deaf;
                break;

            case "can_not_speak":
                file_edit.write(constr_share.user_mode, constr_share.k_user_mode_mute);
                gl.user_mode = constr_share.k_user_mode_mute;
                break;

            case "blind":
            default:
                // 最保险的做法为 blind，可以修改到其他场合
                gl.user_mode = constr_share.k_user_mode_Blind;
                file_edit.write(constr_share.user_mode, constr_share.k_user_mode_Blind);
                break;
        }
    }
}
