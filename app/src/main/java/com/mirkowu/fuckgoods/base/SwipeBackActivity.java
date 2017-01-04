package com.mirkowu.fuckgoods.base;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mirkowu.fuckgoods.R;
import com.mirkowu.fuckgoods.util.StatusBarUtil;
import com.mirkowu.fuckgoods.widget.SwipeBackLayout;


/**
 * Created by Administrator on 2016/12/23 0023.
 */

public abstract class SwipeBackActivity<T extends BasePresenter> extends BaseActivity<T> {
    private SwipeBackLayout swipeBackLayout;
    private ImageView ivShadow;

    @Override
    public void setContentView(View view) {
        super.setContentView(getContainer());
        view.setBackgroundColor(getResources().getColor(R.color.activity_background));
        swipeBackLayout.addView(view);
        StatusBarUtil.setColor(this, R.color.colorPrimary);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(getContainer());
        View view = LayoutInflater.from(this).inflate(layoutResID, null);
        view.setBackgroundColor(getResources().getColor(R.color.activity_background));
        swipeBackLayout.addView(view);
        StatusBarUtil.setColor(this, R.color.colorPrimary);
    }

    private View getContainer() {
        RelativeLayout container = new RelativeLayout(this);
        swipeBackLayout = new SwipeBackLayout(this);
        swipeBackLayout.setDragEdge(SwipeBackLayout.DragEdge.LEFT);
        ivShadow = new ImageView(this);
        ivShadow.setBackgroundColor(getResources().getColor(R.color.shadow_background));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        container.addView(ivShadow, params);
        container.addView(swipeBackLayout);
        swipeBackLayout.setOnSwipeBackListener((fa, fs) -> ivShadow.setAlpha(1 - fs));
        return container;
    }
}
