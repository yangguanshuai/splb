package com.splb.main.vip;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.splb.R;
import com.splb.main.item.GridDividerItemDecoration;
import com.splb.main.item.InfoBean;
import com.splb.main.item.RecycleAdapter;
import com.splb.mvp.MVPBaseFragment;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class VipFragment extends MVPBaseFragment<VipContract.View, VipPresenter> implements VipContract.View, PullLoadMoreRecyclerView.PullLoadMoreListener {


    @BindView(R.id.vipPmrv)
    PullLoadMoreRecyclerView vipPmrv;
    @BindView(R.id.vipTopBar)
    LinearLayout mTabLayout;

    ImageView mImageViewBack;
    TextView mTextViewTitle;
    Unbinder unbinder;


    private RecycleAdapter adapter;

    private List<InfoBean> lists;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vip, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mImageViewBack = mTabLayout.findViewById(R.id.back);
        mImageViewBack.setVisibility(View.GONE);
        mTextViewTitle = mTabLayout.findViewById(R.id.title);
        mTextViewTitle.setText("VIP视频");
        vipPmrv.setGridLayout(2);
        vipPmrv.addItemDecoration(new GridDividerItemDecoration(1, getResources().getColor(R.color.line_ee)));
        lists = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            lists.add(new InfoBean(0, ""));
        }
        adapter = new RecycleAdapter(getContext(), lists);
        vipPmrv.setAdapter(adapter);
        vipPmrv.setOnPullLoadMoreListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                vipPmrv.setPullLoadMoreCompleted();
            }
        }, 2000);
    }

    @Override
    public void onLoadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                vipPmrv.setPullLoadMoreCompleted();
            }
        }, 2000);
    }
}
