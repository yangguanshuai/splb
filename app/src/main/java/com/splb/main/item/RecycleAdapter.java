package com.splb.main.item;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mylibrary.widge.SquareImageView;
import com.splb.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018-3-29.
 */

public class RecycleAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<InfoBean> infoBeanList;
    private RecycleItemClickListener clickListener;

    public void setClickListener(RecycleItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public RecycleAdapter(Context context, List<InfoBean> infoBeanList) {
        this.context = context;
        this.infoBeanList = infoBeanList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (infoBeanList == null) {
            return;
        }

        InfoBean infoBean = infoBeanList.get(position);
        ((ViewHolder) holder).imsge.setBackgroundResource(R.drawable.free);
        ((ViewHolder) holder).tv.setText("测试内容");
        ((ViewHolder) holder).tv2.setText("测试内容");

    }

    @Override
    public int getItemCount() {
        return infoBeanList==null?0:infoBeanList.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.imsge)
        SquareImageView imsge;
        @BindView(R.id.tv)
        TextView tv;
        @BindView(R.id.tv_2)
        TextView tv2;
        @BindView(R.id.root)
        LinearLayout root;

        RecycleItemClickListener clickListener;

        public ViewHolder(View itemView, RecycleItemClickListener clickListener) {
            super(itemView);
            this.clickListener = clickListener;
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) {
                clickListener.onClick(view, getPosition());
            }
        }
    }
}
