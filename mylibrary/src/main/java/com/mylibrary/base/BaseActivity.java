package com.mylibrary.base;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.mylibrary.R;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

public class BaseActivity extends RxAppCompatActivity {


    /**
     * 简单的退出和跳转动画
     */
    private boolean animCon = true;
    //是否沉浸式状态栏
    private boolean isSetStatusBar = true;

    public boolean isAnimCon() {
        return animCon;
    }

    public void setAnimCon(boolean pAnimCon) {
        animCon = pAnimCon;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //本地记录报错信息
        Thread.setDefaultUncaughtExceptionHandler(new BaseExceptionHandler());
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        if (isSetStatusBar){
            steepStatusBar();
        }
    }


    //设置沉浸式
    private void steepStatusBar() {

//        if (true){
            //透明状态栏
            if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.KITKAT) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
//        }else{//设置状态栏的颜色
//            if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.KITKAT){
//                Window window = getWindow();
//                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//                ViewGroup decorViewGroup = (ViewGroup) window.getDecorView();
//                View statusBarView = new View(window.getContext());
//                int statusBarHeight = getStatusBarHeight(window.getContext());
//                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, statusBarHeight);
//                params.gravity = Gravity.TOP;
//                statusBarView.setLayoutParams(params);
//                statusBarView.setBackgroundColor(this.getResources().getColor(R.color.titlecolor));
//                decorViewGroup.addView(statusBarView);
//            }
//        }

    }

    //获取状态栏高度
    private static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }


    @Override
    public void finish() {
        if (animCon){
            overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
        }
        super.finish();
    }

    @Override
    public void startActivity(Intent intent) {
        if (animCon)
            overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
        super.startActivity(intent);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        if (animCon)
            overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    //是否隐藏软键盘
    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }



}
