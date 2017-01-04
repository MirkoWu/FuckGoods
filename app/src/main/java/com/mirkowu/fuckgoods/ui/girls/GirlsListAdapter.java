package com.mirkowu.fuckgoods.ui.girls;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mirkowu.fuckgoods.R;
import com.mirkowu.fuckgoods.base.BaseRCViewHolder;
import com.mirkowu.fuckgoods.base.BaseRecycleViewAdapter;
import com.mirkowu.fuckgoods.network.bean.GankBean;
import com.mirkowu.fuckgoods.util.ImageUtil;

/**
 * Created by Administrator on 2016/12/30 0030.
 */

public class GirlsListAdapter extends BaseRecycleViewAdapter<GankBean, GirlsListAdapter.ViewHolder> {
    public GirlsListAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public ViewHolder onCreateVh(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_girls, parent, false));
    }

    @Override
    public void onBindVH(ViewHolder holder, GankBean data, int position) {
        ImageUtil.load(holder.img_girls, data.url);
    }

    class ViewHolder extends BaseRCViewHolder {

        AppCompatImageView img_girls;

        public ViewHolder(View itemView) {
            super(itemView);
            img_girls = $(R.id.img_girls);
        }
    }
}
