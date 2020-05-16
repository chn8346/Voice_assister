package com.example.phoneui;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.JsonToken;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.VoiceWakeuper;
import com.iflytek.cloud.WakeuperListener;
import com.iflytek.cloud.WakeuperResult;
import com.iflytek.cloud.util.ResourceUtil;

import com.huawei.hiai.nlu.model.ResponseResult; //huawei 接口返回的结果类
import com.huawei.hiai.nlu.sdk.NLUAPIService; //huawei 接口服务类
import com.huawei.hiai.nlu.sdk.NLUConstants; //huawei 接口常量类
import com.huawei.hiai.nlu.sdk.OnResultListener; //huawei 异步函数，执行成功的回调结果类

import com.alibaba.fastjson.*;


public class assistant {
    //上下文
    private Context context_;
    //封装的toast
    private Toast_ toast = new Toast_();
    //语音识别类
    private SpeechRecognizer mIat;
    //判断初始化状态的数值
    private int state = 0;
    //测试模式
    private boolean testMode = false;
    private boolean ivw_off = true;
    //听到的结果
    private StringBuffer listen_ = new StringBuffer();
    //对听到的结果的分类
    private String cls_str = "";
    //文字转语音类
    private speaker speech_speaker;
    //监听命令类
    private VoiceWakeuper mIvw = null;
    //所有谈话都存在这里面
    private StringBuffer talkList = new StringBuffer();
    //显示谈话的文本框
    private TextView view_;
    //文字转换的可信度
    private int confidence = -1;
    //命令执行器
    private executeMethod executor;
    //目前的场景
    private String scence;
    //命令的附加信息
    private JSONObject addition_json;

    // 构造函数
    public assistant(boolean is_init_utility, Context context, TextView view_)
    {
        context_ = context;
        talkList.setLength(0);
        this.view_ = view_;

        // 状态判断的变量，如果state后续中小于某个值就会无法执行
        state = 0;

        //清空场景
        scence = "null";

        executor = new executeMethod(context_);

        if(is_init_utility)
        {
            //toast.show(context, "init utility success", 1000);

            // 状态判断的变量，如果state后续中小于某个值就会无法执行
            state = state + 1;

            // 语音输出
            speech_speaker = new speaker(context_);

            // 唤醒器
            if(!ivw_off) {
                mIvw = VoiceWakeuper.createWakeuper(context_, null);
            }
            // NLU接口

        }
        else
        {
            toast.show(context, "init utility fail", 1000 );
        }
    }

    //改变语音栏位的文字
    public void load_talk(String talks, TextView view)
    {
        talkList.append("\n").append(talks);

        globalstate gl = (globalstate) context_.getApplicationContext();

        // 盲人界面不需要改栏位
        if(gl.user_mode.equals(constr_share.k_user_mode_Blind))
        {
            return;
        }

        if(!gl.appear_talk_list)
        {
            gl.appear_talk_list = true;
            // 改布局
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
            layoutParams.topMargin = (int) (gl.heightSize * 0.05);
        }
        view.setText(talkList.toString());
    }


    // 唤醒方法封装
    public void init_wake()
    {
        if(ivw_off)
            return;

        if(mIvw == null)
        {
            mIvw = VoiceWakeuper.createWakeuper(context_, null);
        }

        // 唤醒词启动
        mIvw = VoiceWakeuper.getWakeuper();
        if(mIvw != null) {

            int MAX = 3000;
            int MIN = 0;
            int curThresh = 1450; // 门限值
            String keep_alive = "1";
            String ivwNetMode = "0";

            // 清空参数
            mIvw.setParameter(SpeechConstant.PARAMS, null);
            Log.d("________TEST_______", "PARAMS");
            // 唤醒门限值，根据资源携带的唤醒词个数按照“id:门限;id:门限”的格式传入
            mIvw.setParameter(SpeechConstant.IVW_THRESHOLD, "0:"+ curThresh);
            Log.d("________TEST_______", "IVW_THRESHOLD");
            // 设置唤醒模式
            mIvw.setParameter(SpeechConstant.IVW_SST, "wakeup");
            Log.d("________TEST_______", "IVW_SST");
            // 设置持续进行唤醒
            mIvw.setParameter(SpeechConstant.KEEP_ALIVE, keep_alive);
            Log.d("________TEST_______", "KEEP_ALIVE");
            // 设置闭环优化网络模式
            mIvw.setParameter(SpeechConstant.IVW_NET_MODE, ivwNetMode);
            Log.d("________TEST_______", "IVW_NET_MODE");
            // 设置唤醒资源路径
            mIvw.setParameter(SpeechConstant.IVW_RES_PATH, getResource());
            Log.d("________TEST_______", "IVW_RES_PATH");
            // 设置唤醒录音保存路径，保存最近一分钟的音频
            mIvw.setParameter( SpeechConstant.IVW_AUDIO_PATH, Environment.getExternalStorageDirectory().getPath()+"/msc/ivw.wav" );
            Log.d("________TEST_______", "IVW_AUDIO_PATH");
            mIvw.setParameter( SpeechConstant.AUDIO_FORMAT, "wav" );
            Log.d("________TEST_______", "AUDIO_FORMAT");
            // 如有需要，设置 NOTIFY_RECORD_DATA 以实时通过 onEvent 返回录音音频流字节
            //mIvw.setParameter( SpeechConstant.NOTIFY_RECORD_DATA, "1" );
            // 启动唤醒
            /*	mIvw.setParameter(SpeechConstant.AUDIO_SOURCE, "-1");*/

            mIvw.startListening(mWakeuperListener);
				/*File file = new File(Environment.getExternalStorageDirectory().getPath() + "/msc/ivw1.wav");
				byte[] byetsFromFile = getByetsFromFile(file);
				mIvw.writeAudio(byetsFromFile,0,byetsFromFile.length);*/
            //	mIvw.stopListening();
        } else {
            toast.show(context_,"唤醒未初始化", toast.short_time_len);
        }
    }

    // 唤醒暂停
    public void wake_pause()
    {
        if(ivw_off)
            return;
        if(mIvw != null){
            mIvw.stopListening();
        }
    }

    // 唤醒保持继续
    public void wake_go_on()
    {
        if(ivw_off)
            return;
        if(mIvw != null) {
            mIvw.startListening(mWakeuperListener);
        }
    }

    // 统一构造封装的听写方法
    public void assistant_listen()
    {
        init_listener();
        listen_result();
    }

    // 听写初始化
    public void init_listener()
    {
        //使得ivw直接中断
        if(!ivw_off){mIvw.stopListening();}

        if(state < 1) {
            toast.show(context_, "初始化错误", toast.short_time_len);
            return;
        }

        InitListener mInitListener = new InitListener() {
            @Override
            public void onInit(int i) {
                
            }
        };

        mIat = SpeechRecognizer.createRecognizer(context_, mInitListener);
        if(mIat == null)
        {
            toast.show(context_, "init recognizer fail", toast.short_time_len);
        }
        else
        {
            // 状态判断的变量，如果state后续中小于某个值就会无法执行
            state = state + 10;

            //设置语法ID和 SUBJECT 为空，以免因之前有语法调用而设置了此参数；或直接清空所有参数，具体可参考 DEMO 的示例。
            mIat.setParameter( SpeechConstant.CLOUD_GRAMMAR, null );
            mIat.setParameter( SpeechConstant.SUBJECT, null );
            //设置返回结果格式，目前支持json,xml以及plain 三种格式，其中plain为纯听写文本内容
            mIat.setParameter(SpeechConstant.RESULT_TYPE, "plain");
            //此处engineType为“cloud”
            mIat.setParameter( SpeechConstant.ENGINE_TYPE, "cloud");
            //设置语音输入语言，zh_cn为简体中文
            mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            //设置结果返回语言
            mIat.setParameter(SpeechConstant.ACCENT, "mandarin");
            // 设置语音前端点:静音超时时间，单位ms，即用户多长时间不说话则当做超时处理
            //取值范围{1000～10000}
            mIat.setParameter(SpeechConstant.VAD_BOS, "2000");
            //设置语音后端点:后端点静音检测时间，单位ms，即用户停止说话多长时间内即认为不再输入，
            //自动停止录音，范围{0~10000}
            mIat.setParameter(SpeechConstant.VAD_EOS, "1000");
            //设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
            mIat.setParameter(SpeechConstant.ASR_PTT,"1");
        }
    }

    // 开始听写，获取音频和文字、分类
    public void listen_result()
    {

        if(state < 11) {
            toast.show(context_, "初始化错误", toast.short_time_len);
            return;
        }

        listen_.setLength(0);
        //监听器初始化
        RecognizerListener mRecogListener = new RecognizerListener() {
            @Override
            public void onVolumeChanged(int i, byte[] bytes) {

            }

            @Override
            public void onBeginOfSpeech() {
                toast.show(context_, "请说话", toast.short_time_len);

            }

            @Override
            public void onEndOfSpeech() {
                if(testMode)
                {
                    if(listen_.length() > 1)
                    toast.show(context_, listen_.append(" -testMode").toString(), 2*toast.short_time_len);
                }
                else {
                    toast.show(context_, "录音结束", 300);
                }

                // 修改主界面的文字，将TextView变成对话框
                load_talk(listen_.toString(), view_);

                // 读出回应（测试模式下为读出使用者的命令）
                // TODO 加上非测试的输出
                speech_speaker.doSpeech(listen_.toString());

                // 对命令进行分类
                cls_str = classify(listen_.toString());

                // TODO 会返回场景，记得承接
                // 执行命令,scene获取返回的当前场景

                //scence = executor.execute(cls_str, addition_json);


                // 使得ivw（语音唤醒）恢复
                if(!ivw_off) {
                    mIvw.startListening(mWakeuperListener);
                }
            }

            @Override
            public void onResult(RecognizerResult recognizerResult, boolean b) {
                if(recognizerResult != null)
                {
                    if(b)
                    {
                        listen_.append(recognizerResult.getResultString());
                    }
                    else
                    {
                        listen_.append(recognizerResult.getResultString());
                    }
                }
                else
                {
                    ;
                }
            }

            @Override
            public void onError(SpeechError speechError) {
                toast.show(context_, "意外错误，请重试", toast.short_time_len);
            }

            @Override
            public void onEvent(int i, int i1, int i2, Bundle bundle) {

            }
        };

        //开始识别
        mIat.startListening(mRecogListener);
    }

    // 对命令进行分类
    private String classify(String words)
    {
        Log.d("______CLASSIFY_________", "___WORD___: " + words);

        // TODO 两种命令识别方式分着用

        // 集成的华为命令类指令   --- >
        String texts = "{text:'" + words + "'";
        String category = ",category:'systemSetting,trip,contact'";
        String module = "}";
        String Json = texts + category + module;

        ResponseResult respResult = NLUAPIService.getInstance().getAssistantIntention(Json, NLUConstants.REQUEST_TYPE_LOCAL);

        Log.d("______CLASSIFY_________", "___JSON___: " + respResult.getJsonRes());
        Log.d("______CLASSIFY_________", "___CODE___: " + respResult.getCode());
        Log.d("______CLASSIFY_________", "___MSG___: " + respResult.getMessage());

        String msg = respResult.getJsonRes();

        JSONObject jsonObject = new JSONObject();

        // 如果这一步已经识别到了情景和命令，那就不用做后面的任务了，完成这一步处理就好
        if(msg.contains("intentions"))
        {
            jsonObject = JSON.parseObject(msg);
            JSONArray jsonArray = jsonObject.getJSONArray("intentions");
            jsonObject = jsonArray.getJSONObject(0);
            String name = jsonObject.getString("name");
            int confidence = jsonObject.getInteger("confidence");

            // 获取额外信息
            try {
                addition_json = jsonObject.getJSONArray("attributes").getJSONObject(0);
            } catch (Exception e) {
                addition_json = null;
                e.printStackTrace();
            }

            Log.d("______CLASSIFY_________", "___JSON_ANA___: " + name + " --> CONF: " + confidence);
            this.confidence = confidence;
            return "intentions_" + name;
        }
        else {

            // NLP 分词 --> 目前好像用不到
            /*
            Json = "{text:'"+words+"',type:1}";
            respResult = NLUAPIService.getInstance().getWordSegment(Json, NLUConstants.REQUEST_TYPE_LOCAL);

            Log.d("______CLASSIFY_________", "___SEG___: " + respResult.getJsonRes());
            */
            // 词性分析
            Json = "{text:'" + words + "',type:1}"; // TODO 调节细粒度  9223372036854775807
            respResult = NLUAPIService.getInstance().getWordPos(Json, NLUConstants.REQUEST_TYPE_LOCAL);

            Log.d("______CLASSIFY_________", "___WORD___: " + respResult.getJsonRes());
            // 词性分析结果
            String WordPos = respResult.getJsonRes();
            JSONObject word_json = JSON.parseObject(WordPos);


            // 实体分析
            Json = Json = "{text:'" + words + "'}";
            respResult = NLUAPIService.getInstance().getEntity(Json, NLUConstants.REQUEST_TYPE_LOCAL);

            Log.d("______CLASSIFY_________", "___MAIN___: " + respResult.getJsonRes());
            // 实体分析结果
            String Entity_ana = respResult.getJsonRes();
            JSONObject entity_json = JSON.parseObject(Entity_ana);

            // 识别是否是打开APP的命令
            String app_package = app_search(entity_json, word_json);
            if(!app_package.equals("null"))
            {
                Log.d("___OPEN___APP___", "___OPEN___: " + app_package);

                // 传参执行
                JSONObject sub_json = new JSONObject();
                sub_json.put("app_name", app_package);
                executor.execute(constr_share.order_basic_open_app, sub_json);
            }

            // 识别是否是发短信,如果是就直接发送
            // （这个由于读写是异步的原因，所以执行和判断放一起）
            send_message(entity_json, word_json);


            // 识别是否是是搜索命令
            String content = search_order(entity_json, word_json,listen_.toString());
            if(!content.equals("null")) {
                Log.d("_CLASSIFY_SEARCH_", "content: " + content);

                JSONObject json_search = new JSONObject();
                json_search.put("search_content", content);

                executor.execute(constr_share.order_basic_search, json_search);
            }
        }
        return "default";
    }

    // 唤醒词使用的路径寻求函数
    private String getResource() {
        //return "ivw/"+ context_.getString(R.string.app_id) +".jet";
        final String resPath = ResourceUtil.generateResourcePath(context_, ResourceUtil.RESOURCE_TYPE.assets,"5eb4d530.jet");
        return resPath;
    }

    // 唤醒监听器的定义
    private WakeuperListener mWakeuperListener = new WakeuperListener() {
        @Override
        public void onBeginOfSpeech() {

        }

        @Override
        public void onResult(WakeuperResult wakeuperResult) {
            // 唤醒成功先打报告，后面再根据置信度确定是否 CALL button 去开始录音
            Log.d("TAG", "onResult");
            String resultString;
            try {
                String text = wakeuperResult.getResultString();
                Log.d("__WEAK__UP__RESULT ", "__WEAK__UP__RESULT \n" + text);
                resultString = text;

            } catch (JSONException e) {
                resultString = "结果解析出错";
                e.printStackTrace();
            }

            Log.d("__WEAK__UP__RESULT ", "__WEAK__UP__RESULT \n" + resultString);

            // 先立一个条件，未来需要再改
            if(true)
            {
                if(!ivw_off) {
                    mIvw.stopListening();
                }
                assistant_listen();
            }

        }

        @Override
        public void onError(SpeechError speechError) {

        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }

        @Override
        public void onVolumeChanged(int i) {

        }
    };

    private String app_search(JSONObject entity_json, JSONObject word_json)
    {
        package_scaner app = new package_scaner(context_);

        // 实体的检验结果
        if(entity_json.containsKey("entity")) // 有entity结果才分析
        {
            JSONObject sub_json = entity_json.getJSONObject("entity");
            if(sub_json.containsKey("app")) // 含有app类目才继续
            {
                sub_json = sub_json.getJSONArray("app").getJSONObject(0);
                String package_name = app.find_package_name(sub_json.getString("name"));

                // 找不到APP返回 “Null”
                if(package_name == null)
                {
                    return "null";
                }
                Log.d("___OPEN___APP___", package_name);
                return package_name;
            }
        }

        return "null";
        // 词性的建议结果 todo 词性分析，暂时懒不写了
    }

    private String search_order(JSONObject entity_json, JSONObject word_json, String listen)
    {
        //  搜索内容
        String search_content = "";

        // 如果有实体，就搜索实体
        if(entity_json.containsKey("entity"))
        {
            // todo 由于大概率用不到，暂时空着
        }

        String[] key_word_arr = {"搜索", "查找", "查查", "查一下"};
        // 直接检测关键词
        for(int i = 0; i < key_word_arr.length; i++)
        {
            if(listen.contains(key_word_arr[i]))
            {
                return listen.substring((listen.indexOf(key_word_arr[i]) + key_word_arr[i].length()));
            }
        }


        // 词性检测
        if(word_json.containsKey("pos")) {

            JSONArray word_arr = word_json.getJSONArray("pos");
            JSONObject word = new JSONObject();

            int arr_size = word_arr.size();

            //String[] contain_word_tag = {"v","t","n"}

            for(int i = 0; i <arr_size; i++)
            {
                word = word_arr.getJSONObject(i);

            }

        }
        return "null";
    }

    private void send_message(JSONObject entity_json, JSONObject word_json)
    {
        String listen = listen_.toString();
        final JSONObject json_MSG = new JSONObject();

        // 直接检测 todo 测试功能使用，还要改
        if(listen.contains("短信")) {
            json_MSG.put("contactor", "我");

            //speech_speaker.doSpeech("告诉我短信内容");

            final StringBuffer Msgcontents = new StringBuffer();
            Msgcontents.setLength(0);

            if (!ivw_off) {
                if (mIvw.isListening()) {
                    // 如果ivw运行就关掉
                    mIvw.stopListening();
                }
            }

            mIat.startListening(new RecognizerListener() {
                @Override
                public void onVolumeChanged(int i, byte[] bytes) {

                }

                @Override
                public void onBeginOfSpeech() {

                }

                @Override
                public void onEndOfSpeech() {
                    json_MSG.put("msgStr", Msgcontents.toString());
                    scence = executor.execute(constr_share.order_basic_Message, json_MSG);
                }

                @Override
                public void onResult(RecognizerResult recognizerResult, boolean b) {
                    Msgcontents.append(recognizerResult.getResultString());
                }

                @Override
                public void onError(SpeechError speechError) {

                }

                @Override
                public void onEvent(int i, int i1, int i2, Bundle bundle) {

                }
            });
        }
    }

}
