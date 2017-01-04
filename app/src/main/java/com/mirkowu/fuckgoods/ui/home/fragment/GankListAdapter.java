package com.mirkowu.fuckgoods.ui.home.fragment;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mirkowu.fuckgoods.R;
import com.mirkowu.fuckgoods.base.BaseRCViewHolder;
import com.mirkowu.fuckgoods.base.BaseRecycleViewAdapter;
import com.mirkowu.fuckgoods.network.bean.GankBean;

/**
 * Created by Administrator on 2016/12/30 0030.
 */

public class GankListAdapter extends BaseRecycleViewAdapter<GankBean, GankListAdapter.ViewHolder> {
    public GankListAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public ViewHolder onCreateVh(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_article, parent, false));
    }

    @Override
    public void onBindVH(ViewHolder holder, GankBean data, int position) {
        holder.tv_title.setText(data.desc);
        holder.tv_source.setText(getContext().getString(R.string.source_s, data.source));
        holder.tv_who.setText(getContext().getString(R.string.who_s, data.who));
        holder.tv_publishedAt.setText(getContext().getString(R.string.publishedAt_s, data.publishedAt.split("T")[0]));
    }

    class ViewHolder extends BaseRCViewHolder {

        AppCompatTextView tv_title, tv_who, tv_source, tv_publishedAt;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = $(R.id.tv_title);
            tv_who = $(R.id.tv_who);
            tv_source = $(R.id.tv_source);
            tv_publishedAt = $(R.id.tv_publishedAt);
        }
    }
}
