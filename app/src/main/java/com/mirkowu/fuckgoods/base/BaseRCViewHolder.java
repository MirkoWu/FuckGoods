package com.mirkowu.fuckgoods.base;

import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/9/5 0005.
 */
public class BaseRCViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    View itemView;
    public boolean isSelected;

    public <T extends View> T $(int resId) {
        return (T) itemView.findViewById(resId);
    }

    public <T extends View> T $(View view, int id) {
        return (T) view.findViewById(id);
    }

    public BaseRCViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setSelected() {
        itemView.setSelected(isSelected);
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            int posotion = getAdapterPosition();
            if (posotion == RecyclerView.NO_POSITION)
                return;//getAdapterPostion() 和 getLayoutPosition() 有区别  中间会有大概16ms差距
            onItemClickListener.onItemClickListener(itemView, posotion);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (onItemLongClickListener != null)
            onItemLongClickListener.onItemLongClickListener(itemView, getAdapterPosition());
        return false;
    }

    /**
     * onItemClick
     */
    public RecyclerViewOnItemClickListener onItemClickListener;

    public void setOnItemClickListener(RecyclerViewOnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


    public interface RecyclerViewOnItemClickListener {
        void onItemClickListener(View view, int position);
    }

    /**
     * onItemLongClick
     */
    public RecyclerViewOnItemLongClickListener onItemLongClickListener;

    public void setOnItemLongClickListener(RecyclerViewOnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }


    public interface RecyclerViewOnItemLongClickListener {
        void onItemLongClickListener(View view, int position);
    }

    /**
     * 待改进
     *
     * @param resId
     * @param str
     * @return
     */
    public BaseRCViewHolder setText(@IdRes int resId, String str) {
        ((TextView) $(resId)).setText(str);
        return this;
    }

    public BaseRCViewHolder setText(@IdRes int resId, @StringRes int strResId) {
        ((TextView) $(resId)).setText(strResId);
        return this;
    }

    public BaseRCViewHolder setImage(@IdRes int resId, @DrawableRes int strResId) {
        ((ImageView) $(resId)).setImageResource(strResId);
        return this;
    }


}

