package com.mylibrary.subscriber;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.mylibrary.Api.BaseApi;
import com.mylibrary.MyApplication;
import com.mylibrary.exception.HttpTimeException;
import com.mylibrary.http.cookie.CookieResulte;
import com.mylibrary.listener.HttpOnNextListener;
import com.mylibrary.utils.AppUtil;
import com.mylibrary.utils.CookieDbUtil;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.lang.ref.SoftReference;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Observable;
import rx.Subscriber;

public class ProgressSubscriber<T> extends Subscriber<T>{
    //是否弹出加载框
    private boolean showProgress = true;
    //软引用接口回调
    private SoftReference<HttpOnNextListener> mHttpOnNextListener;
    //软引用防止出现内存泄露
    private SoftReference<RxAppCompatActivity> mActivity;
    //自定义加载框
    private ProgressDialog pd;

    private BaseApi mBaseApi;


    /**
     * 构造
     *
     * @param api
     */
    public ProgressSubscriber(BaseApi api) {
        this.mBaseApi = api;
        this.mHttpOnNextListener = api.getListener();
        this.mActivity = new SoftReference<>(api.getRxAppCompatActivity());
        setShowProgress(api.isShowProgress());
        if (api.isShowProgress()) {
            initProgressDialog(api.isCancel());
        }
    }
    /**
     * 初始化加载框
     */
    private void initProgressDialog(boolean cancel) {
        Context context = mActivity.get();
        if (pd == null && context != null) {
            pd = new ProgressDialog(context);
            pd.setCancelable(cancel);
            if (cancel) {
                pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        if (mHttpOnNextListener.get() != null) {
                            mHttpOnNextListener.get().onCancel();
                        }
                        onCancelProgress();
                    }
                });
            }
        }
    }
    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }


    @Override
    public void onStart() {
        showProgressDialog();
        /*缓存并且有网*/
        if (mBaseApi.isCache() && AppUtil.isNetworkAvailable(MyApplication.getApplication())) {
            /*获取缓存数据*/
            CookieResulte cookieResulte = CookieDbUtil.getInstance().queryCookieBy(mBaseApi.getUrl());
            if (cookieResulte != null) {
                long time = (System.currentTimeMillis() - cookieResulte.getTime()) / 1000;
                if (time < mBaseApi.getCookieNetWorkTime()) {
                    if (mHttpOnNextListener.get() != null) {
                        mHttpOnNextListener.get().onCacheNext(cookieResulte.getResult());
                    }
                    onCompleted();
                    unsubscribe();
                }
            }
        }
    }

    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }

    @Override
    public void onError(Throwable e) {
        dismissProgressDialog();
        /*需要緩存并且本地有缓存才返回*/
        if (mBaseApi.isCache()) {
            Observable.just(mBaseApi.getBaseUrl()).subscribe(new Subscriber<String>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    errorDo(e);
                }

                @Override
                public void onNext(String s) {
                    /*获取缓存数据*/
                    CookieResulte cookieResulte = CookieDbUtil.getInstance().queryCookieBy(s);
                    if (cookieResulte == null) {
                        throw new HttpTimeException("网络错误");
                    }
                    long time = (System.currentTimeMillis() - cookieResulte.getTime()) / 1000;
                    if (time < mBaseApi.getCookieNoNetWorkTime()) {
                        if (mHttpOnNextListener.get() != null) {
                            mHttpOnNextListener.get().onCacheNext(cookieResulte.getResult());
                        }
                    } else {
                        CookieDbUtil.getInstance().deleteCookie(cookieResulte);
                        throw new HttpTimeException("网络错误");
                    }
                }
            });
        } else {
            errorDo(e);
        }
    }



    @Override
    public void onNext(T pT) {
        if (mHttpOnNextListener.get() != null) {
            mHttpOnNextListener.get().onNext(pT);
        }
    }


    /**
     * 显示加载框
     */
    private void showProgressDialog() {
        if (!isShowProgress()) return;
        Context context = mActivity.get();
        if (pd == null || context == null) return;
        if (!pd.isShowing()) {
            pd.show();
        }
    }




    /*错误统一处理*/
    private void errorDo(Throwable e) {
        Context context = mActivity.get();
        if (context == null) return;
        if (e instanceof SocketTimeoutException) {
            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else if (e instanceof ConnectException) {
            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "错误" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        if (mHttpOnNextListener.get() != null) {
            mHttpOnNextListener.get().onError(e);
        }
    }

    /**
     * 隐藏加载中
     */
    private void dismissProgressDialog() {
        if (!isShowProgress()) return;
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
    }


    public boolean isShowProgress() {
        return showProgress;
    }

    public void setShowProgress(boolean pShowProgress) {
        showProgress = pShowProgress;
    }







}
