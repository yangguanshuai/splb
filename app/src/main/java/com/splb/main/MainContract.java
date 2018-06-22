package com.splb.main;

import android.content.Intent;

import com.mylibrary.base.BaseActivity;
import com.splb.mvp.BasePresenter;
import com.splb.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MainContract {
    interface View extends BaseView {
        void startActivity(Intent pIntent);
    }

    interface  Presenter extends BasePresenter<View> {
        void requestPermissions(BaseActivity pActivity);
        void getSMS();
    }
}
