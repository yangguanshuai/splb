package com.splb.mvp;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;

import com.mylibrary.MyApplication;
import com.splb.BuildConfig;
import com.splb.main.service.MyWorkService;
import com.xdandroid.hellodaemon.DaemonEnv;

public class BaseApplication extends Application{

    public static Context app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = getApplicationContext();
        MyApplication.init(this, BuildConfig.DEBUG);
        MultiDex.install(this);
        DaemonEnv.initialize(
                app,  //Application Context.
                MyWorkService.class, //刚才创建的 Service 对应的 Class 对象.
                60*1000);  //定时唤醒的时间间隔(ms), 默认 6 分钟.

        app.startService(new Intent(app, MyWorkService.class));
    }
}
