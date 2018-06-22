package com.splb.main.mine;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.splb.R;
import com.splb.mvp.MVPBaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MineFragment extends MVPBaseFragment<MineContract.View, MinePresenter> implements MineContract.View {

    Unbinder unbinder;
    @BindView(R.id.mineTopBar)
    LinearLayout mTabLayout;

    ImageView mImageViewBack;
    TextView mTextViewTitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine,container,false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mImageViewBack = mTabLayout.findViewById(R.id.back);
        mTextViewTitle = mTabLayout.findViewById(R.id.title);
        mTextViewTitle.setText("个人中心");
        mImageViewBack.setVisibility(View.GONE);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
