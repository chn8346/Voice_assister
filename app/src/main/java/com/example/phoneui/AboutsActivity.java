package com.example.phoneui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AboutsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);

        globalstate gl = (globalstate) this.getApplication();

        TextView back = findViewById(R.id.back_main);
        back.setClickable(true);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final TextView about_str = findViewById(R.id.aboutString);
        final TextView about = findViewById(R.id.help);
        about.setText("关于作者");
        about_str.setText(R.string.user_help);
        about.setClickable(true);
        about.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(about.getText() == "帮助") {
                    about.setText("关于作者");
                    about_str.setText(R.string.user_help);
                }
                else {
                    about.setText("帮助");
                    about_str.setText(R.string.about_author);
                }

            }
        });

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) about.getLayoutParams();
        if(layoutParams != null)
        {
            //layoutParams.topMargin = (int) (gl.heightSize * 0.6);
            layoutParams.leftMargin = (int) (gl.widthSize * 0.3);
        }

    }
}
