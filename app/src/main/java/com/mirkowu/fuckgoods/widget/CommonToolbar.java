package com.mirkowu.fuckgoods.widget;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mirkowu.fuckgoods.R;
import com.mirkowu.fuckgoods.base.BaseActivity;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2016/10/10 0010.
 */

public class CommonToolbar extends Toolbar {
    private ImageView img_titlebar_back_button;
    private TextView tv_titlebar_menu_left;
    private AppCompatTextView tv_titlebar_title;
    private ImageView img_titlebar_menu_right;
    private TextView tv_titlebar_menu_right;

    public CommonToolbar(Context context) {
        super(context, null);
        initView(context);
    }

    public CommonToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public CommonToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.setContentInsetsAbsolute(0, 0);//toolbar默认marginleft ，所以定位到（0,0）

        View view = inflate(context, R.layout.view_common_toolbar, this);
        initializeView(view);

        img_titlebar_back_button = (ImageView) view.findViewById(R.id.img_titlebar_back_button);
        img_titlebar_menu_right = (ImageView) view.findViewById(R.id.img_titlebar_menu_right);
        tv_titlebar_menu_left = (TextView) view.findViewById(R.id.tv_titlebar_menu_left);
        tv_titlebar_menu_right = (TextView) view.findViewById(R.id.tv_titlebar_menu_right);
        tv_titlebar_title = (AppCompatTextView) view.findViewById(R.id.tv_titlebar_title);

        if (context instanceof Activity) {
            CharSequence title = ((Activity) context).getTitle();
            if (!TextUtils.isEmpty(title)) tv_titlebar_title.setText(title);
        }
    }


    private void initializeView(View view) {
        view.setBackgroundColor(getBackgroundColor());
        int statusBarHeight = getStatusBarHeight();
        view.setPadding(0, statusBarHeight, 0, 0);
    }


    public int getBackgroundColor() {
        return ContextCompat.getColor(getContext(), R.color.colorPrimary);
    }

    /**
     * 获取状态栏高度
     *
     * @return 状态栏高度
     */
    public int getStatusBarHeight() {
        try {
            Class c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            return 0;
        }
    }


    public static class Builder {
        private String title, leftStr, rightStr;
        private int titleResId, leftImgResId, leftStrResId, rightImgResId, rightStrResId;
        private OnClickListener leftOnClickListener;
        private OnClickListener rightOnClickListener;

        public Builder setBackButton(@DrawableRes int leftImgResId) {
            this.leftImgResId = leftImgResId;
            return this;
        }

        public Builder showImageRight(@DrawableRes int rightImgResId, OnClickListener rightOnClickListener) {
            this.rightImgResId = rightImgResId;
            this.rightOnClickListener = rightOnClickListener;
            return this;
        }

        public Builder showTextLeft(String leftStr, OnClickListener leftOnClickListener) {
            this.leftStr = leftStr;
            this.leftOnClickListener = leftOnClickListener;
            return this;
        }

        public Builder showTextLeft(@StringRes int leftStrResId, OnClickListener leftOnClickListener) {
            this.leftStrResId = leftStrResId;
            this.leftOnClickListener = leftOnClickListener;
            return this;
        }

        public Builder showTextRight(String rightStr, OnClickListener rightOnClickListener) {
            this.rightStr = rightStr;
            this.rightOnClickListener = rightOnClickListener;
            return this;
        }

        public Builder showTextRight(@StringRes int rightStrResId, OnClickListener rightOnClickListener) {
            this.rightStrResId = rightStrResId;
            this.rightOnClickListener = rightOnClickListener;
            return this;
        }


        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setTitle(@StringRes int titleResId) {
            this.titleResId = titleResId;
            return this;
        }

        public CommonToolbar build(Activity activity) {
            CommonToolbar toolbar = new CommonToolbar(activity);
            //default backbuttton
            toolbar.setBackButton(R.mipmap.back);
            //left
            if (TextUtils.isEmpty(leftStr)) {
                if (leftStrResId > 0) toolbar.showTextLeft(leftStrResId, leftOnClickListener);
            } else toolbar.showTextLeft(leftStr, leftOnClickListener);
            if (leftImgResId > 0) toolbar.setBackButton(leftImgResId);
            //right
            if (TextUtils.isEmpty(rightStr)) {
                if (rightStrResId > 0) toolbar.showTextRight(rightStrResId, rightOnClickListener);
            } else toolbar.showTextRight(rightStr, rightOnClickListener);
            if (rightImgResId > 0) toolbar.showImageRight(rightImgResId, rightOnClickListener);
            //title
            if (TextUtils.isEmpty(title)) {
                if (titleResId > 0) toolbar.setTitle(titleResId);
            } else toolbar.setTitle(title);

            System.out.println("CommonToolbar.Builder.build");
            return toolbar;
        }
    }


    /**
     * 设置返回按钮,image
     */
    public void setBackButton(int imageViewId) {
        if (imageViewId == 0) {
            img_titlebar_back_button.setVisibility(View.INVISIBLE);
            return;
        }
        img_titlebar_back_button.setVisibility(View.VISIBLE);
        img_titlebar_back_button.setImageResource(imageViewId);
        img_titlebar_back_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseActivity) getContext()).onBackPressedSupport();
            }
        });
    }

    /**
     * 显示文本菜单按钮
     */
    public void showTextLeft(String content, OnClickListener listener) {
        tv_titlebar_menu_left.setVisibility(View.VISIBLE);
        tv_titlebar_menu_left.setText(content);
        tv_titlebar_menu_left.setOnClickListener(listener);
    }

    public void showTextLeft(@StringRes int content, OnClickListener listener) {
        tv_titlebar_menu_left.setVisibility(View.VISIBLE);
        tv_titlebar_menu_left.setText(content);
        tv_titlebar_menu_left.setOnClickListener(listener);
    }

    /**
     * 显示文本菜单按钮
     *
     * @param content
     * @param listener
     */
    public void showTextRight(String content, OnClickListener listener) {
        tv_titlebar_menu_right.setVisibility(View.VISIBLE);
        tv_titlebar_menu_right.setText(content);
        tv_titlebar_menu_right.setOnClickListener(listener);
    }

    public void showTextRight(@StringRes int content, OnClickListener listener) {
        tv_titlebar_menu_right.setVisibility(View.VISIBLE);
        tv_titlebar_menu_right.setText(content);
        tv_titlebar_menu_right.setOnClickListener(listener);
    }

    /**
     * 显示图形菜单按钮,右边
     */
    public void showImageRight(int resId, OnClickListener listener) {
        img_titlebar_menu_right.setVisibility(View.VISIBLE);
        img_titlebar_menu_right.setImageResource(resId);
        img_titlebar_menu_right.setOnClickListener(listener);
    }

    /**
     * 隐藏返回按钮
     */
    public void hideBackButton() {
        img_titlebar_back_button.setVisibility(View.INVISIBLE);
    }

    /**
     * 隐藏文本菜单按钮
     */
    public void hideTextMenu_left() {
        tv_titlebar_menu_left.setVisibility(View.INVISIBLE);
    }

    /**
     * 隐藏文本菜单按钮
     */
    public void hideTextMenu_right() {
        tv_titlebar_menu_right.setVisibility(View.INVISIBLE);
    }

    /**
     * 隐藏图形菜单按钮
     */
    public void hideImageMenu_right() {
        img_titlebar_menu_right.setVisibility(View.INVISIBLE);
    }


    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(CharSequence title) {
        tv_titlebar_title.setText(title);
    }


}
