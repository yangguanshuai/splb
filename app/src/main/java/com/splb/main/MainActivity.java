package com.splb.main;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.splb.R;
import com.splb.main.free.FreeFragment;
import com.splb.main.mine.MineFragment;
import com.splb.main.vip.VipFragment;
import com.splb.mvp.MVPBaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MainActivity extends MVPBaseActivity<MainContract.View, MainPresenter> implements MainContract.View {


    @BindView(R.id.main_frame)
    FrameLayout mainFrame;
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;


    private FreeFragment mFreeFragment;
    private VipFragment mVipFragment;
    private MineFragment mMineFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mPresenter.requestPermissions(MainActivity.this);
        initBottom();
        setDefaultFragment();//设置默认Fragment
    }

    private void setDefaultFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (mFreeFragment ==null){
            mFreeFragment = new FreeFragment();
        }
        transaction.add(R.id.main_frame,mFreeFragment);
        hideFragment(transaction);
        transaction.show(mFreeFragment);
        transaction.commit();

    }

    private void initBottom() {//初始化底部导航栏
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED)
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar.setActiveColor(R.color.yello);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.free,"免费"))
                .addItem(new BottomNavigationItem(R.drawable.vip,"VIP"))
                .addItem(new BottomNavigationItem(R.drawable.person,"个人中心"))
                .setFirstSelectedPosition(0)
                .initialise();
        bottomNavigationBar.setTabSelectedListener(tabSelected);
    }


    private void hideFragment(FragmentTransaction transaction){
        if (mFreeFragment !=null){
            transaction.hide(mFreeFragment);
        }
        if (mVipFragment !=null){
            transaction.hide(mVipFragment);
        }
        if (mMineFragment !=null){
            transaction.hide(mMineFragment);
        }
    }
    BottomNavigationBar.OnTabSelectedListener tabSelected = new BottomNavigationBar.OnTabSelectedListener() {
        @Override
        public void onTabSelected(int position) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            switch (position){
                case 0:
                        if(mFreeFragment ==null){
                            mFreeFragment = new FreeFragment();
                            transaction.add(R.id.main_frame,mFreeFragment);
                        }
                        hideFragment(transaction);
                        transaction.show(mFreeFragment);
                    break;
                case 1:
                    if(mVipFragment ==null){
                        mVipFragment = new VipFragment();
                        transaction.add(R.id.main_frame,mVipFragment);
                    }
                    hideFragment(transaction);
                    transaction.show(mVipFragment);
                    break;
                case 2:
                    if(mMineFragment ==null){
                        mMineFragment = new MineFragment();
                        transaction.add(R.id.main_frame,mMineFragment);
                    }
                    hideFragment(transaction);
                    transaction.show(mMineFragment);
                    break;
            }
            transaction.commit();
        }

        @Override
        public void onTabUnselected(int position) {

        }

        @Override
        public void onTabReselected(int position) {

        }
    };
}
