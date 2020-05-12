package com.example.phoneui;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class blind_server extends Service {
    public blind_server() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    // 服务启动时的操作
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    // 服务销毁时的操作
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
