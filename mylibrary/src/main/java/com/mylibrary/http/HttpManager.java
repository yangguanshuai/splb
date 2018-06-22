package com.mylibrary.http;

import android.util.Log;

import com.mylibrary.Api.BaseApi;
import com.mylibrary.MyApplication;
import com.mylibrary.exception.RetryWhenNetworkException;
import com.mylibrary.http.cookie.CookieInterceptor;
import com.mylibrary.listener.HttpOnNextListener;
import com.mylibrary.subscriber.ProgressSubscriber;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.lang.ref.SoftReference;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * http交互类
 */
public class HttpManager {
    private volatile static HttpManager INSTANCE;
    private HttpManager(){}


    public static HttpManager getInstance(){
        if (INSTANCE == null){
            synchronized (HttpManager.class){
                if (INSTANCE ==null){
                    INSTANCE = new HttpManager();
                }
            }
        }
        return INSTANCE;
    }


    public void httpRequest(BaseApi pBaseApi){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(pBaseApi.getConnectionTime(), TimeUnit.SECONDS);
        //添加缓存拦截器
        Log.d("cookie","缓存链接："+pBaseApi.getUrl());
        builder.addInterceptor(new CookieInterceptor(pBaseApi.isCache(), pBaseApi.getUrl()));
        if (MyApplication.isDebug()){
            builder.addInterceptor(getHttpLoggingInterceptor());
        }

        /*创建retrofit对象*/
        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(pBaseApi.getBaseUrl())
                .build();


        //处理rx请求
        ProgressSubscriber subscriber = new ProgressSubscriber(pBaseApi);

        Observable observable = pBaseApi.getObservable(retrofit)
                //失败后重试
                .retryWhen(new RetryWhenNetworkException(pBaseApi.getRetryCount(),
                        pBaseApi.getRetryDelay(), pBaseApi.getRetryIncreaseDelay()))
                .compose(pBaseApi.getRxAppCompatActivity().bindUntilEvent(ActivityEvent.PAUSE))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(pBaseApi);

        SoftReference<HttpOnNextListener> httpOnNextListener = pBaseApi.getListener();
        if (httpOnNextListener!=null&&httpOnNextListener.get()!=null){
            httpOnNextListener.get().onNext(observable);
        }
        observable.subscribe(subscriber);

    }





    /**
     * 日志输出
     * @return
     */
    private HttpLoggingInterceptor getHttpLoggingInterceptor(){
        //日志显示级别
        HttpLoggingInterceptor.Level level= HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("RxRetrofit","Retrofit====Message:"+message);
            }
        });
        loggingInterceptor.setLevel(level);
        return loggingInterceptor;
    }

}
