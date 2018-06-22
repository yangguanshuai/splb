package com.splb.main.service;

import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.xdandroid.hellodaemon.AbsWorkService;

public class MyWorkService extends AbsWorkService {


    /**
     * 是否 任务完成, 不再需要服务运行?
     * @return 应当停止服务, true; 应当启动服务, false; 无法判断, null.
     */
    @Override
    public Boolean shouldStopService(Intent intent, int flags, int startId) {
        return null;
    }

    @Override
    public void startWork(Intent intent, int flags, int startId) {
        Log.d("startWork","..................");
    }

    @Override
    public void stopWork(Intent intent, int flags, int startId) {
        Log.d("stopWork","..................");
    }

    /**
     * 任务是否正在运行?
     * @return 任务正在运行, true; 任务当前不在运行, false; 无法判断, null.
     */
    @Override
    public Boolean isWorkRunning(Intent intent, int flags, int startId) {
        return null;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent, Void alwaysNull) {
        return null;
    }

    //服务被杀时调用, 可以在这里面保存数据.
    @Override
    public void onServiceKilled(Intent rootIntent) {

    }
}
