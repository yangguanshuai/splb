package com.splb.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;

import com.mylibrary.base.BaseActivity;
import com.splb.utils.ActivityCollector;

import java.lang.reflect.ParameterizedType;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public abstract class MVPBaseActivity<V extends BaseView,T extends BasePresenterImpl<V>> extends BaseActivity implements BaseView{
    public T mPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter= getInstance(this,1);
        mPresenter.attachView((V) this);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter!=null)
        mPresenter.detachView();
        ActivityCollector.removeActivity(this);
    }

    @Override
    public Context getContext(){
        return this;
    }


    public  <T> T getInstance(Object o, int i) {
        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[i])
                    .newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK) {
            finish();
            return true;//不执行父类点击事件
        }
        return super.onKeyDown(keyCode, event);//继续执行父类其他点击事件
    }
}
