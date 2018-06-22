package com.mylibrary.Api;

import android.util.Log;

import com.mylibrary.base.HttpResult;
import com.mylibrary.listener.HttpOnNextListener;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.lang.ref.SoftReference;

import retrofit2.Retrofit;
import rx.Observable;
import rx.functions.Func1;

public abstract class BaseApi<T> implements Func1<HttpResult<T>,T> {

    //rx生命周期管理
    private SoftReference<RxAppCompatActivity> mRxAppCompatActivity;
    /*基础url*/
    private String baseUrl = "http://app.lw-world.cc/";

    /*是否能取消加载框*/
    private boolean cancel;
    /*是否显示加载框*/
    private boolean showProgress;
    /*回调*/
    private SoftReference<HttpOnNextListener> listener;
    /*超时时间-默认6秒*/
    private int connectionTime = 6;
    /*有网情况下的本地缓存时间默认60秒*/
    private int cookieNetWorkTime = 60;
    /*无网络的情况下本地缓存时间默认30天*/
    private int cookieNoNetWorkTime = 24 * 60 * 60 * 30;
    /* 失败后retry次数*/
    private int retryCount = 1;
    /*失败后retry延迟*/
    private long retryDelay = 100;
    /*失败后retry叠加延迟*/
    private long retryIncreaseDelay = 10;
    /*是否需要缓存处理*/
    private boolean cache;
    /*方法-如果需要缓存必须设置这个参数；不需要不用設置*/
    private String method="";
    /*缓存url-可手动设置*/
    private String cacheUrl;


    public BaseApi(RxAppCompatActivity pRxAppCompatActivity, HttpOnNextListener pListener) {
        setRxAppCompatActivity(pRxAppCompatActivity);
        setListener(pListener);
        setShowProgress(true);//默认显示加载框
        setCache(false);//默认缓存
    }

    /**
     * 设置参数
     *
     * @param retrofit
     * @return
     */
    public abstract Observable getObservable(Retrofit retrofit);

    @Override
    public T call(HttpResult<T> pTHttpResult) {
        Log.d("response<<<<<",pTHttpResult.toString());
        return pTHttpResult.getResult();
    }
    //**************************************************get/set方法*********************************************


    public void setRxAppCompatActivity(RxAppCompatActivity pRxAppCompatActivity) {
        mRxAppCompatActivity = new SoftReference<RxAppCompatActivity>(pRxAppCompatActivity);
    }
    public RxAppCompatActivity getRxAppCompatActivity() {
        return mRxAppCompatActivity.get();
    }

    public String getBaseUrl() {
        return baseUrl;
    }
    public void setBaseUrl(String pBaseUrl) {
        baseUrl = pBaseUrl;
    }

    public boolean isCancel() {
        return cancel;
    }
    public void setCancel(boolean pCancel) {
        cancel = pCancel;
    }

    public boolean isShowProgress() {
        return showProgress;
    }
    public void setShowProgress(boolean pShowProgress) {
        showProgress = pShowProgress;
    }



    public String getUrl() {
        /*在没有手动设置url情况下，简单拼接*/
        if (getCacheUrl()==null||getCacheUrl().equals("")) {
            return getBaseUrl() + getMethod();
        }
        return getCacheUrl();
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String pMethod) {
        method = pMethod;
    }

    public String getCacheUrl() {
        return cacheUrl;
    }

    public void setCacheUrl(String pCacheUrl) {
        cacheUrl = pCacheUrl;
    }

    public boolean isCache() {
        return cache;
    }

    public void setCache(boolean pCache) {
        cache = pCache;
    }



    public SoftReference<HttpOnNextListener> getListener() {
        return listener;
    }

    public void setListener(HttpOnNextListener pListener) {
        listener = new SoftReference<HttpOnNextListener>(pListener);
    }





    public void setConnectionTime(int pConnectionTime) {
        connectionTime = pConnectionTime;
    }

    public void setCookieNetWorkTime(int pCookieNetWorkTime) {
        cookieNetWorkTime = pCookieNetWorkTime;
    }

    public void setCookieNoNetWorkTime(int pCookieNoNetWorkTime) {
        cookieNoNetWorkTime = pCookieNoNetWorkTime;
    }

    public void setRetryCount(int pRetryCount) {
        retryCount = pRetryCount;
    }

    public void setRetryDelay(long pRetryDelay) {
        retryDelay = pRetryDelay;
    }

    public void setRetryIncreaseDelay(long pRetryIncreaseDelay) {
        retryIncreaseDelay = pRetryIncreaseDelay;
    }

    public int getConnectionTime() {
        return connectionTime;
    }

    public int getCookieNetWorkTime() {
        return cookieNetWorkTime;
    }

    public int getCookieNoNetWorkTime() {
        return cookieNoNetWorkTime;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public long getRetryDelay() {
        return retryDelay;
    }

    public long getRetryIncreaseDelay() {
        return retryIncreaseDelay;
    }



}
