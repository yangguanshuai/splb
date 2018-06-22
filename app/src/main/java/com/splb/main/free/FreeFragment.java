package com.splb.main.free;


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

public class FreeFragment extends MVPBaseFragment<FreeContract.View, FreePresenter> implements PullLoadMoreRecyclerView.PullLoadMoreListener {


    Unbinder unbinder;
    @BindView(R.id.freePmrv)
    PullLoadMoreRecyclerView freePmrv;
    @BindView(R.id.tablayout)
    LinearLayout mTabLayout;

    ImageView mImageViewBack;
    TextView mTextViewTitle;
    private RecycleAdapter adapter;


    private int[] arrs = {R.drawable.a01, R.drawable.a02, R.drawable.a03, R.drawable.a04, R.drawable.a05,
            R.drawable.a06, R.drawable.a07, R.drawable.a08, R.drawable.a09, R.drawable.a10,
            R.drawable.a11, R.drawable.a12, R.drawable.a13, R.drawable.a14, R.drawable.a15,
            R.drawable.a16, R.drawable.a17, R.drawable.a18, R.drawable.a19, R.drawable.a20,
            R.drawable.a21, R.drawable.a22, R.drawable.a23, R.drawable.a24, R.drawable.a25,
            R.drawable.a26, R.drawable.a27, R.drawable.a28, R.drawable.a29, R.drawable.a30,
            R.drawable.a31, R.drawable.a32, R.drawable.a33, R.drawable.a34, R.drawable.a35,
            R.drawable.a36, R.drawable.a37, R.drawable.a38, R.drawable.a39, R.drawable.a40,
            R.drawable.a41, R.drawable.a42, R.drawable.a43, R.drawable.a44, R.drawable.a45,
            R.drawable.a46, R.drawable.a47, R.drawable.a48, R.drawable.a49, R.drawable.a50};
    private List<InfoBean> lists;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_free, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }


    private void initView() {
        mImageViewBack = mTabLayout.findViewById(R.id.back);
        mTextViewTitle = mTabLayout.findViewById(R.id.title);
        mTextViewTitle.setText("免费视频");
        mImageViewBack.setVisibility(View.GONE);
        freePmrv.setGridLayout(2);
        freePmrv.addItemDecoration(new GridDividerItemDecoration(1, getResources().getColor(R.color.line_ee)));
        lists = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            lists.add(new InfoBean(0, ""));
        }
        adapter = new RecycleAdapter(getContext(), lists);
        freePmrv.setAdapter(adapter);
        freePmrv.setOnPullLoadMoreListener(this);
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
                freePmrv.setPullLoadMoreCompleted();
            }
        }, 2000);
    }

    @Override
    public void onLoadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                freePmrv.setPullLoadMoreCompleted();
            }
        }, 2000);
    }
}
