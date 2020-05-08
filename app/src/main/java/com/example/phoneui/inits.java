package com.example.phoneui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class inits extends AppCompatActivity {

    // 全局变量
    globalstate gl = (globalstate)this.getApplication();
    Toast_ toast = new Toast_();
    file_writer file_edit = new file_writer();

    // 修改布局
    Button bt1 = (Button)findViewById(R.id.init_bt1);
    Button bt2 = (Button)findViewById(R.id.init_bt2);
    TextView textView = (TextView) findViewById(R.id.init_text);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inits);


    }
}
