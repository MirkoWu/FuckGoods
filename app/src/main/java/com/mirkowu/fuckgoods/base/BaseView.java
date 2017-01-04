package com.mirkowu.fuckgoods.base;

/**
 * Created by Administrator on 2016/9/10 0010.
 */
public interface BaseView {
    void showEror(Throwable throwable);
    void showProgressDialog();
    void hideProgressDialog();
    void useNightMode(boolean isNight);
}
