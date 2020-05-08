package com.example.phoneui;

import android.content.Context;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;

public class assistant {
    public Context context_;
    public Toast_ toast = new Toast_();

    public assistant(boolean is_init_utility, Context context)
    {
        context_ = context;

        if(is_init_utility)
        {
            toast.show(context, "init utility fail", 2000);
        }
        else
        {
            toast.show(context, "init utility success", 2000 );
        }
    }
    public String assistant_main()
    {
        init_listener();

        String result = listen_result();

        excute(result);

        return result;
    }

    public void init_listener()
    {
        InitListener mInitListener = new InitListener() {
            @Override
            public void onInit(int i) {
                
            }
        };
        SpeechRecognizer mIat = SpeechRecognizer.createRecognizer(context_, mInitListener);
        if(mIat == null)
        {
            toast.show(context_, "init recognizer fail", toast.short_time_len);
        }
    }

    public String listen_result()
    {
        return "default";
    }

    private String listen()
    {
        return "no_result";
    }

    private String classify(String words)
    {
        return "default";
    }

    public void excute(String classify)
    {
        switch (classify){
            case "default":
                break;
        }
        return;
    }
}
