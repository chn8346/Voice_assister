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

    public assistant(boolean is_init_utility, Context context)
    {
        context_ = context;
        Toast_ toast = new Toast_();
        if(is_init_utility)
        {
            toast.show(context, "init utility fail", 700);
        }
        else
        {
            toast.show(context, "init utility success", 1000);
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
        ;
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
